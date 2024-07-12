package ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.Offer
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.OfferRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {
    operator fun invoke(): Flow<Resource<List<Offer>>> = offerRepository.getOffers()
}