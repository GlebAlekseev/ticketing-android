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
import retrofit2.Response
import ru.alekseevjk.ticketing.api.Constants
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.common.ResourceException
import ru.alekseevjk.ticketing.core.network.NoInternetConnectionException
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.GoogleDriveService
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.TicketsOffersResponse
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.TicketsResponse
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
            val cachedTicketsOffers = getCachedTicketsOffers(dateLimit)
            if (cachedTicketsOffers.isNotEmpty()) {
                emit(Resource.Success(cachedTicketsOffers))
            } else {
                val remoteResponse: Response<TicketsOffersResponse> =
                    googleDriveService.getTicketsOffers()
                val body = remoteResponse.body()
                val remoteOffers = body?.ticketsOffers?.map { it.toDomain() }

                if (remoteResponse.isSuccessful && remoteOffers != null) {
                    ticketOfferDao.insert(remoteOffers.map { it.toDbModel() })
                    emit(Resource.Success(remoteOffers))
                } else {
                    emit(
                        Resource.Failure(
                            ResourceException.HTTPException(
                                HttpException(
                                    remoteResponse
                                )
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
                val remoteResponse: Response<TicketsResponse> =
                    googleDriveService.getTickets()
                val body = remoteResponse.body()
                val remoteOffers = body?.tickets?.map { it.toDomain() }

                if (remoteResponse.isSuccessful && remoteOffers != null) {
                    ticketDao.insert(remoteOffers.map { it.toDbModel() })
                    emit(Resource.Success(remoteOffers))
                } else {
                    emit(
                        Resource.Failure(
                            ResourceException.HTTPException(
                                HttpException(
                                    remoteResponse
                                )
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Failure(mapExceptionToResourceException(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun getCachedTicketsOffers(dateLimit: LocalDateTime): List<TicketOffer> {
        val cachedTicketsOffers = ticketOfferDao.getTicketsOffersCachedInLastThreeDays(dateLimit)
        return cachedTicketsOffers.map { it.toDomain() }
    }

    private fun getCachedTickets(dateLimit: LocalDateTime): List<Ticket> {
        val cachedTickets = ticketDao.getTicketsCachedInLastThreeDays(dateLimit)
        return cachedTickets.map { it.toDomain() }
    }

    private fun getDateLimit(): LocalDateTime {
        val timeZone = TimeZone.currentSystemDefault()
        return Clock.System.now()
            .minus(Constants.CACHE_LIFETIME_IN_DAYS, DateTimeUnit.DAY, timeZone)
            .toLocalDateTime(timeZone)
    }

    private fun mapExceptionToResourceException(throwable: Throwable): ResourceException {
        return when (throwable) {
            is SocketTimeoutException -> ResourceException.SocketTimeoutException(throwable)
            is IOException -> ResourceException.IOException(throwable)
            is HttpException -> ResourceException.HTTPException(throwable)
            is NoInternetConnectionException -> ResourceException.NoInternetConnectionException(
                throwable
            )

            else -> ResourceException.UnknownException(throwable)
        }
    }
}