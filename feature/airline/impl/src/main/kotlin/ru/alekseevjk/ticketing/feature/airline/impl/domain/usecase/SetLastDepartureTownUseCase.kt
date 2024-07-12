package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.AirlineConfigRepository
import javax.inject.Inject

class SetLastDepartureTownUseCase @Inject constructor(
    private val airlineConfigRepository: AirlineConfigRepository
) {
    operator fun invoke(value: String) {
        airlineConfigRepository.lastDepartureTown = value
    }
}