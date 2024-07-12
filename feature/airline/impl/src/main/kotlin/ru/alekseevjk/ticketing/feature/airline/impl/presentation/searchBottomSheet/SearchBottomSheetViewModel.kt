package ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import ru.alekseevjk.ticketing.core.common.map
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetPopularDestinationsUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.searchBottomSheet.mapper.toPopularDestinationWithImage

class SearchBottomSheetViewModel @AssistedInject constructor(
    private val getPopularDestinationsUseCase: GetPopularDestinationsUseCase,
    @Assisted private val departureTown: String,
) : ViewModel() {
    val popularDestinationsWithImage by lazy {
        getPopularDestinationsUseCase.invoke(departureTown)
            .map {
                it.map { it.map { it.toPopularDestinationWithImage() } }
            }
            .shareIn(scope = viewModelScope, started = SharingStarted.Lazily, replay = 1)
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(departureTown: String): SearchBottomSheetViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            departureTown: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(departureTown) as T
            }
        }
    }
}