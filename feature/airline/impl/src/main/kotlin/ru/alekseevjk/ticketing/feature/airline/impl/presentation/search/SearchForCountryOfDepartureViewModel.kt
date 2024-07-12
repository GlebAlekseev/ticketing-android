package ru.alekseevjk.ticketing.feature.airline.impl.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import ru.alekseevjk.ticketing.core.common.Resource
import ru.alekseevjk.ticketing.feature.airline.impl.domain.entity.TicketOffer
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetOnlyWithLuggageAsFlowUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetTicketsOffersUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetWithoutTransfersAsFlowUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.presentation.search.entity.SearchForCountryOfDeparture

class SearchForCountryOfDepartureViewModel @AssistedInject constructor(
    private val getOnlyWithLuggageAsFlowUseCase: GetOnlyWithLuggageAsFlowUseCase,
    private val getWithoutTransfersAsFlowUseCase: GetWithoutTransfersAsFlowUseCase,
    private val getTicketsOffersUseCase: GetTicketsOffersUseCase,
    @Assisted("departureTown") private val departureTown: String,
    @Assisted("arrivalTown") private val arrivalTown: String,
) : ViewModel() {
    private val _viewState = MutableStateFlow(
        SearchForCountryOfDeparture(
            departureTown = departureTown,
            arrivalTown = arrivalTown
        )
    )
    val viewState: StateFlow<SearchForCountryOfDeparture> get() = _viewState

    private val _ticketsOffersState =
        MutableStateFlow<Resource<List<TicketOffer>>>(Resource.Loading)
    val ticketsOffersState: StateFlow<Resource<List<TicketOffer>>> get() = _ticketsOffersState

    init {
        viewModelScope.launch {
            getOnlyWithLuggageAsFlowUseCase.invoke().collect {
                _viewState.value = _viewState.value.copy(
                    onlyWithLuggage = it
                )
            }
        }
        viewModelScope.launch {
            getWithoutTransfersAsFlowUseCase.invoke().collect {
                _viewState.value = _viewState.value.copy(
                    withoutTransfers = it
                )
            }
        }
        viewModelScope.launch {
            viewState.collectLatest { config ->
                _ticketsOffersState.value = Resource.Loading
                getTicketsOffersUseCase.invoke(
                    fromCity = config.departureTown,
                    toCity = config.arrivalTown,
                    departureDate = config.dateDeparture,
                    passengerCount = config.classType.split(",").first().trim().toInt(),
                    directOnly = config.withoutTransfers ?: false,
                    returnDate = config.dateReturnBack,
                    travelClass = config.classType.split(",").last().trim(),
                    withLuggageOnly = config.onlyWithLuggage ?: false,
                ).collect {
                    _ticketsOffersState.value = it
                }
            }
        }
    }

    fun changeTown() {
        viewModelScope.launch {
            val oldState = _viewState.value
            _viewState.value = oldState.copy(
                departureTown = oldState.arrivalTown,
                arrivalTown = oldState.departureTown
            )
        }
    }

    fun setDateReturnBack(date: LocalDate) {
        viewModelScope.launch {
            val oldState = _viewState.value
            _viewState.value = oldState.copy(
                dateReturnBack = date,
            )
        }
    }

    fun setDateDeparture(date: LocalDate) {
        viewModelScope.launch {
            val oldState = _viewState.value
            _viewState.value = oldState.copy(
                dateDeparture = date,
            )
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            @Assisted("departureTown") departureTown: String,
            @Assisted("arrivalTown") arrivalTown: String
        ): SearchForCountryOfDepartureViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            departureTown: String,
            arrivalTown: String,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(departureTown, arrivalTown) as T
            }
        }
    }
}