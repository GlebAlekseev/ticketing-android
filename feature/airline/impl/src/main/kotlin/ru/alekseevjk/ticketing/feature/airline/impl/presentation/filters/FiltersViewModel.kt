package ru.alekseevjk.ticketing.feature.airline.impl.presentation.filters

import androidx.lifecycle.ViewModel
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetOnlyWithLuggageUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetWithoutTransfersUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.SetOnlyWithLuggageUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.SetWithoutTransfersUseCase
import javax.inject.Inject

class FiltersViewModel @Inject constructor(
    private val getWithoutTransfersUseCase: GetWithoutTransfersUseCase,
    private val setWithoutTransfersUseCase: SetWithoutTransfersUseCase,
    private val getOnlyWithLuggageUseCase: GetOnlyWithLuggageUseCase,
    private val setOnlyWithLuggageUseCase: SetOnlyWithLuggageUseCase,
) : ViewModel() {
    var withoutTransfers: Boolean
        get() = getWithoutTransfersUseCase.invoke()
        set(value) = setWithoutTransfersUseCase.invoke(value)

    var onlyWithLuggage: Boolean
        get() = getOnlyWithLuggageUseCase.invoke()
        set(value) = setOnlyWithLuggageUseCase.invoke(value)
}