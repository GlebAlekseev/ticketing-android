package ru.alekseevjk.ticketing.feature.airline.impl.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetLastDepartureTownUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.GetOffersUseCase
import ru.alekseevjk.ticketing.feature.airline.impl.domain.usecase.SetLastDepartureTownUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val getLastDepartureTownUseCase: GetLastDepartureTownUseCase,
    private val setLastDepartureTownUseCase: SetLastDepartureTownUseCase
) : ViewModel() {
    val offers by lazy {
        getOffersUseCase.invoke()
            .shareIn(scope = viewModelScope, started = SharingStarted.Lazily, replay = 1)
    }

    suspend fun getLastDepartureTownUseCase(): String? {
        return withContext(Dispatchers.IO) {
            getLastDepartureTownUseCase.invoke()
        }
    }

    fun setLastDepartureTown(value: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setLastDepartureTownUseCase.invoke(value)
            }
        }
    }
}