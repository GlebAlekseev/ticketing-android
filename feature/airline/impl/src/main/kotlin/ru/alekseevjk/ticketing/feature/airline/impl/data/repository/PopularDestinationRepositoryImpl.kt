package ru.alekseevjk.ticketing.feature.airline.impl.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.PopularDestination
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.PopularDestinationRepository
import javax.inject.Inject

class PopularDestinationRepositoryImpl @Inject constructor() : PopularDestinationRepository {
    override fun getPopularDestinations(town: String): Flow<Resource<List<PopularDestination>>> =
        flow {
            emit(Resource.Loading)
            emit(Resource.Success(mockData))
        }.flowOn(Dispatchers.IO)

    companion object {
        private var mockData = listOf(
            PopularDestination(
                destinationName = "Стамбул",
                description = "Популярное направление"
            ),
            PopularDestination(
                destinationName = "Сочи",
                description = "Популярное направление"
            ),
            PopularDestination(
                destinationName = "Пхукет",
                description = "Популярное направление"
            ),
        )
    }
}