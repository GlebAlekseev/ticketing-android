package ru.alekseevjk.ticketing.feature.airline.impl.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.SearchForCountryOfDepartureFragment

class AirlineFragmentFactory @AssistedInject constructor(
    @Assisted private val navigateToFilters: () -> Unit
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            SearchForCountryOfDepartureFragment::class.java -> SearchForCountryOfDepartureFragment(
                navigateToFilters = navigateToFilters
            )

            else -> super.instantiate(classLoader, className)
        }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(navigateToFilters: () -> Unit): AirlineFragmentFactory
    }
}