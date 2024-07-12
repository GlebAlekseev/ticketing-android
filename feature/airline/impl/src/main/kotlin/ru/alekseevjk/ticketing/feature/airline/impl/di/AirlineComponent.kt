package ru.alekseevjk.ticketing.feature.airline.impl.di

import dagger.Component
import ru.alekseevjk.ticketing.feature.airline.impl.AirlineDependencies
import ru.alekseevjk.ticketing.feature.airline.impl.di.module.AirlineModule
import ru.alekseevjk.ticketing.feature.airline.impl.di.module.ViewModelModule
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.AirlineMainFragment
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.filters.FiltersFragment
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.MainFragment
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.SearchForCountryOfDepartureFragment
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.SearchBottomSheetDialogFragment
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets.AllTicketsFragment


@Component(
    dependencies = [
        AirlineDependencies::class
    ],
    modules = [
        ViewModelModule::class,
        AirlineModule::class
    ]
)
interface AirlineComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: SearchBottomSheetDialogFragment)
    fun inject(fragment: AirlineMainFragment)
    fun inject(fragment: FiltersFragment)
    fun inject(fragment: SearchForCountryOfDepartureFragment)
    fun inject(fragment: AllTicketsFragment)

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: AirlineDependencies,
        ): AirlineComponent
    }
}