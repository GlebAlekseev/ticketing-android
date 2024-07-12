package ru.alekseevjk.ticketing.feature.airline.impl.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.PopularDestination

interface PopularDestinationRepository {
    fun getPopularDestinations(
        town: String,
    ): Flow<Resource<List<PopularDestination>>>
}