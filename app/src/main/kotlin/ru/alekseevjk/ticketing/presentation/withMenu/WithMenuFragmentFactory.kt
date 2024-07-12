package ru.alekseevjk.ticketing.presentation.withMenu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.AirlineMainFragment

class WithMenuFragmentFactory @AssistedInject constructor(
    @Assisted private val navigateToFilters: () -> Unit
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            AirlineMainFragment::class.java -> AirlineMainFragment(
                navigateToFilters = navigateToFilters
            )

            else -> super.instantiate(classLoader, className)
        }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(navigateToFilters: () -> Unit): WithMenuFragmentFactory
    }
}