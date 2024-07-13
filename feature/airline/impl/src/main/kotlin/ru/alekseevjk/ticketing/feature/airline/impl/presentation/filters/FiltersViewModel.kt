package ru.alekseevjk.ticketing.feature.airline.impl.presentation.filters

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    suspend fun getWithoutTransfers(): Boolean = withContext(Dispatchers.IO){
        getWithoutTransfersUseCase.invoke()
    }

    suspend fun getOnlyWithLuggage(): Boolean = withContext(Dispatchers.IO){
        getOnlyWithLuggageUseCase.invoke()
    }

    suspend fun setWithoutTransfers(value: Boolean) {
        withContext(Dispatchers.IO) {
            setWithoutTransfersUseCase.invoke(value)
        }
    }

    suspend fun setOnlyWithLuggage(value: Boolean) {
        withContext(Dispatchers.IO) {
            setOnlyWithLuggageUseCase.invoke(value)
        }
    }
}
