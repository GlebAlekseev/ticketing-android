package ru.alekseevjk.ticketing.feature.airline.impl.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer

interface OfferRepository {
    fun getOffers(): Flow<Resource<List<Offer>>>
}