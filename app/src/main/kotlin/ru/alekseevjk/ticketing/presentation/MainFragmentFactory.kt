package ru.alekseevjk.ticketing.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.alekseevjk.ticketing.presentation.withMenu.WithMenuFragment

class MainFragmentFactory @AssistedInject constructor(
    @Assisted private val navigateToFilters: () -> Unit
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (loadFragmentClass(classLoader, className)) {
            WithMenuFragment::class.java -> WithMenuFragment(
                navigateToFilters = navigateToFilters
            )

            else -> super.instantiate(classLoader, className)
        }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(navigateToFilters: () -> Unit): MainFragmentFactory
    }
}