package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.AirlineConfigRepository
import javax.inject.Inject

class SetWithoutTransfersUseCase @Inject constructor(
    private val airlineConfigRepository: AirlineConfigRepository
) {
    operator fun invoke(value: Boolean) {
        airlineConfigRepository.withoutTransfers = value
    }
}