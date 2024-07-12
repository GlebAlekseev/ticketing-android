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
import retrofit2.HttpException
import retrofit2.Response
import ru.alekseevjk.ticketing.api.Constants
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.core.common.ResourceException
import ru.alekseevjk.ticketing.core.network.NoInternetConnectionException
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.GoogleDriveService
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.OffersResponse
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.mapper.toDomain
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.dao.OfferDao
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper.toDbModel
import ru.alekseevjk.ticketing.feature.airline.impl.data.room.mapper.toDomain
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.OfferRepository
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val offerDao: OfferDao,
    private val googleDriveService: GoogleDriveService
) : OfferRepository {
    override fun getOffers(): Flow<Resource<List<Offer>>> = flow {
        emit(Resource.Loading)
        try {
            val dateLimit = getDateLimit()
            val cachedOffers = getCachedOffers(dateLimit)
            if (cachedOffers.isNotEmpty()) {
                emit(Resource.Success(cachedOffers))
            } else {
                val remoteResponse: Response<OffersResponse> = googleDriveService.getOffers()
                val body = remoteResponse.body()
                val remoteOffers = body?.offers?.map { it.toDomain() }

                if (remoteResponse.isSuccessful && remoteOffers != null) {
                    offerDao.insert(remoteOffers.map { it.toDbModel() })
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
            emit(Resource.Failure(mapExceptionToResourceException(e)))
        }
    }.flowOn(Dispatchers.IO)

    private fun getCachedOffers(dateLimit: LocalDateTime): List<Offer> {
        val cachedOffers = offerDao.getOffersCachedInLastThreeDays(dateLimit)
        return cachedOffers.map { it.toDomain() }
    }

    private fun getDateLimit(): LocalDateTime {
        val timeZone = TimeZone.currentSystemDefault()
        return Clock.System.now()
            .minus(Constants.CACHE_LIFETIME_IN_DAYS, DateTimeUnit.DAY, timeZone)
            .toLocalDateTime(timeZone)
    }

    private fun mapExceptionToResourceException(throwable: Throwable): ResourceException {
        throwable.printStackTrace()
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