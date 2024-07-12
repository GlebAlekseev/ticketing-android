package ru.alekseevjk.ticketing.feature.airline.impl.di.module

import dagger.Binds
import dagger.Module
import ru.alekseevjk.ticketing.feature.airline.impl.data.repository.AirlineConfigRepositoryImpl
import ru.alekseevjk.ticketing.feature.airline.impl.data.repository.OfferRepositoryImpl
import ru.alekseevjk.ticketing.feature.airline.impl.data.repository.PopularDestinationRepositoryImpl
import ru.alekseevjk.ticketing.feature.airline.impl.data.repository.TicketRepositoryImpl
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.AirlineConfigRepository
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.OfferRepository
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.PopularDestinationRepository
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.TicketRepository

@Module
interface AirlineModule {
    @Binds
    fun bindLastDepartureTownRepository(impl: AirlineConfigRepositoryImpl): AirlineConfigRepository

    @Binds
    fun bindOfferRepository(impl: OfferRepositoryImpl): OfferRepository

    @Binds
    fun bindTicketRepository(impl: TicketRepositoryImpl): TicketRepository

    @Binds
    fun bindPopularDestinationRepository(impl: PopularDestinationRepositoryImpl): PopularDestinationRepository
}