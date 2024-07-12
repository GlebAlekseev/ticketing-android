package ru.alekseevjk.ticketing.feature.airline.impl.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.alekseevjk.ticketing.core.common.ViewModelFactory
import ru.alekseevjk.ticketing.core.common.ViewModelKey
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.filters.FiltersViewModel
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.main.MainViewModel

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(FiltersViewModel::class)]
    fun bindFiltersViewModel(impl: FiltersViewModel): ViewModel
}