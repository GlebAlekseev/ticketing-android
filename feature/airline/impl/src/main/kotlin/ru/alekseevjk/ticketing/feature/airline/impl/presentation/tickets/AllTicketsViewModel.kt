package ru.alekseevjk.ticketing.feature.airline.impl.presentation.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetTicketsUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.entity.SearchForCountryOfDeparture

class AllTicketsViewModel @AssistedInject constructor(
    private val getTicketsUseCase: GetTicketsUseCase,
    @Assisted("searchForCountryOfDeparture") private val searchForCountryOfDeparture: SearchForCountryOfDeparture,
) : ViewModel() {
    val viewState: StateFlow<SearchForCountryOfDeparture> get() = MutableStateFlow(searchForCountryOfDeparture)

    val tickets by lazy {
        val config = viewState.value
        getTicketsUseCase.invoke(
            fromCity = config.departureTown,
            toCity = config.arrivalTown,
            departureDate = config.dateDeparture,
            passengerCount = config.classType.split(",").first().trim().toInt(),
            directOnly = config.withoutTransfers ?: false,
            returnDate = config.dateReturnBack,
            travelClass = config.classType.split(",").last().trim(),
            withLuggageOnly = config.onlyWithLuggage ?: false,
        ).shareIn(scope = viewModelScope, started = SharingStarted.Lazily, replay = 1)
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            @Assisted("searchForCountryOfDeparture") searchForCountryOfDeparture: SearchForCountryOfDeparture,
        ): AllTicketsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            searchForCountryOfDeparture: SearchForCountryOfDeparture,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(searchForCountryOfDeparture) as T
            }
        }
    }
}