package ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit

import retrofit2.Response
import retrofit2.http.GET
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.OffersResponse
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.TicketsOffersResponse
import ru.alekseevjk.ticketing.feature.airline.impl.data.retrofit.dto.TicketsResponse

interface GoogleDriveService {
    @GET("u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav")
    suspend fun getOffers(): Response<OffersResponse>

    @GET("u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta")
    suspend fun getTicketsOffers(): Response<TicketsOffersResponse>

    @GET("u/0/uc?id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
    suspend fun getTickets(): Response<TicketsResponse>
}