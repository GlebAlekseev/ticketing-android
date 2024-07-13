package ru.alekseevjk.ticketing.feature.airline.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.threeten.bp.LocalDate
import retrofit2.HttpException
import ru.alekseevjk.ticketing.api.Constants
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.common.ResourceException
import ru.alekseevjk.ticketing.core.network.NoInternetConnectionException
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.GoogleDriveService
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.mapper.toDomain
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.TicketOfferDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper.toDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper.toDomain
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Ticket
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.TicketRepository
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val googleDriveService: GoogleDriveService,
    private val ticketDao: TicketDao,
    private val ticketOfferDao: TicketOfferDao
) : TicketRepository {

    override fun getTicketsOffers(
        fromCity: String,
        toCity: String,
        directOnly: Boolean,
        withLuggageOnly: Boolean,
        departureDate: LocalDate,
        returnDate: LocalDate?,
        travelClass: String?,
        passengerCount: Int?
    ): Flow<Resource<List<TicketOffer>>> = flow {
        emit(Resource.Loading)
        try {
            val dateLimit = getDateLimit()
            val cachedOffers = getCachedTicketOffers(dateLimit)
            if (cachedOffers.isNotEmpty()) {
                emit(Resource.Success(cachedOffers))
            } else {
                emit(fetchRemoteTicketOffers())
            }
        } catch (e: Exception) {
            emit(Resource.Failure(mapExceptionToResourceException(e)))
        }
    }.flowOn(Dispatchers.IO)

    override fun getTickets(
        fromCity: String,
        toCity: String,
        directOnly: Boolean,
        withLuggageOnly: Boolean,
        departureDate: LocalDate,
        returnDate: LocalDate?,
        travelClass: String?,
        passengerCount: Int?
    ): Flow<Resource<List<Ticket>>> = flow {
        emit(Resource.Loading)
        try {
            val dateLimit = getDateLimit()
            val cachedTickets = getCachedTickets(dateLimit)
            if (cachedTickets.isNotEmpty()) {
                emit(Resource.Success(cachedTickets))
            } else {
                emit(fetchRemoteTickets())
            }
        } catch (e: Exception) {
            emit(Resource.Failure(mapExceptionToResourceException(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun getCachedTicketOffers(dateLimit: LocalDateTime): List<TicketOffer> {
        return ticketOfferDao.getTicketsOffersCachedInLastThreeDays(dateLimit).map { it.toDomain() }
    }

    private fun getCachedTickets(dateLimit: LocalDateTime): List<Ticket> {
        return ticketDao.getTicketsCachedInLastThreeDays(dateLimit).map { it.toDomain() }
    }

    private fun getDateLimit(): LocalDateTime {
        val timeZone = TimeZone.currentSystemDefault()
        return Clock.System.now()
            .minus(Constants.CACHE_LIFETIME_IN_DAYS, DateTimeUnit.DAY, timeZone)
            .toLocalDateTime(timeZone)
    }

    private suspend fun fetchRemoteTicketOffers(): Resource<List<TicketOffer>> {
        return try {
            val response = googleDriveService.getTicketsOffers()
            if (response.isSuccessful && response.body()?.ticketsOffers != null) {
                val offers = response.body()!!.ticketsOffers.map { it.toDomain() }
                ticketOfferDao.insert(offers.map { it.toDbModel() })
                Resource.Success(offers)
            } else {
                Resource.Failure(
                    ResourceException.HTTPException(HttpException(response))
                )
            }
        } catch (e: Exception) {
            Resource.Failure(mapExceptionToResourceException(e))
        }
    }

    private suspend fun fetchRemoteTickets(): Resource<List<Ticket>> {
        return try {
            val response = googleDriveService.getTickets()
            if (response.isSuccessful && response.body()?.tickets != null) {
                val tickets = response.body()!!.tickets.map { it.toDomain() }
                ticketDao.insert(tickets.map { it.toDbModel() })
                Resource.Success(tickets)
            } else {
                Resource.Failure(
                    ResourceException.HTTPException(HttpException(response))
                )
            }
        } catch (e: Exception) {
            Resource.Failure(mapExceptionToResourceException(e))
        }
    }

    private fun mapExceptionToResourceException(throwable: Throwable): ResourceException {
        throwable.printStackTrace()
        return when (throwable) {
            is SocketTimeoutException -> ResourceException.SocketTimeoutException(throwable)
            is IOException -> ResourceException.IOException(throwable)
            is HttpException -> ResourceException.HTTPException(throwable)
            is NoInternetConnectionException -> ResourceException.NoInternetConnectionException(throwable)
            else -> ResourceException.UnknownException(throwable)
        }
    }
}
