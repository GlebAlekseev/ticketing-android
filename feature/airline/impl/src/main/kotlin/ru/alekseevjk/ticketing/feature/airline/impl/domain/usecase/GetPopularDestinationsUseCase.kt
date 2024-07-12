package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.PopularDestination
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.PopularDestinationRepository
import javax.inject.Inject

class GetPopularDestinationsUseCase @Inject constructor(
    private val popularDestinationRepository: PopularDestinationRepository
) {
    operator fun invoke(
        town: String
    ): Flow<Resource<List<PopularDestination>>> {
        return popularDestinationRepository.getPopularDestinations(
            town = town
        )
    }
}