package ru.alekseevjk.ticketing.feature.airline.impl.data.repository

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alekseevjk.ticketing.core.common.getValue
import ru.alekseevjk.ticketing.core.common.getValueAsFlow
import ru.alekseevjk.ticketing.core.common.setValue
import ru.alekseevjk.ticketing.core.di.ApplicationContext
import ru.alekseevjk.ticketing.feature.airline.impl.domain.repository.AirlineConfigRepository
import javax.inject.Inject

class AirlineConfigRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AirlineConfigRepository {
    private val lastDepartureTownKey = stringPreferencesKey("lastDepartureTownKey")
    private val withoutTransfersKey = stringPreferencesKey("withoutTransfersKey")
    private val onlyWithLuggageKey = stringPreferencesKey("onlyWithLuggageKey")

    override var lastDepartureTown: String?
        get() = getValue(context, lastDepartureTownKey)
        set(value) {
            setValue(context, lastDepartureTownKey, value)
        }
    override var withoutTransfers: Boolean
        get() = getValue(context, withoutTransfersKey) ?: false
        set(value) {
            setValue(context, withoutTransfersKey, value)
        }
    override var withoutTransfersAsFlow: Flow<Boolean> =
        getValueAsFlow<Boolean>(context, withoutTransfersKey)
            .map { it ?: false }

    override var onlyWithLuggage: Boolean
        get() = getValue(context, onlyWithLuggageKey) ?: false
        set(value) {
            setValue(context, onlyWithLuggageKey, value)
        }
    override var onlyWithLuggageAsFlow: Flow<Boolean> =
        getValueAsFlow<Boolean>(context, onlyWithLuggageKey)
            .map { it ?: false }
}