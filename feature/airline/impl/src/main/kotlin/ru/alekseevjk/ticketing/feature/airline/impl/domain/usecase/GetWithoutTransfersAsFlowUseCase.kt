package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.AirlineConfigRepository
import javax.inject.Inject

class GetWithoutTransfersAsFlowUseCase @Inject constructor(
    private val airlineConfigRepository: AirlineConfigRepository
) {
    operator fun invoke(): Flow<Boolean> = airlineConfigRepository.withoutTransfersAsFlow
}