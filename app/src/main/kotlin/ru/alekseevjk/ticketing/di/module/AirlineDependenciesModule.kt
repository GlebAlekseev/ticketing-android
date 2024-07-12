package ru.alekseevjk.ticketing.di.module

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.alekseevjk.ticketing.core.di.Dependencies
import ru.alekseevjk.ticketing.core.di.DependenciesKey
import ru.alekseevjk.ticketing.di.AppComponent
import ru.alekseevjk.ticketing.feature.airline.impl.AirlineDependencies

@Module
interface AirlineDependenciesModule {
    @Binds
    @IntoMap
    @DependenciesKey(AirlineDependencies::class)
    fun bindAirlineDependencies(impl: AppComponent): Dependencies
}