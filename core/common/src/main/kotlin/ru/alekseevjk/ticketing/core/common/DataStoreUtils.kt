package ru.alekseevjk.ticketing.core.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.alekseevjk.ticketing.api.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.PREFERENCES_STORAGE)

inline fun <reified T> getValue(context: Context, key: Preferences.Key<String>): T? {
    return runBlocking {
        val value = context.dataStore.data.map { prefs -> prefs[key] }.firstOrNull()
        value?.let {
            try {
                Json.decodeFromString<T>(it)
            } catch (e: Exception) {
                null
            }
        }
    }
}

inline fun <reified T> setValue(context: Context, key: Preferences.Key<String>, value: T) {
    runBlocking {
        context.dataStore.edit { prefs ->
            prefs[key] = Json.encodeToString(value)
        }
    }
}

inline fun <reified T> getValueAsFlow(context: Context, key: Preferences.Key<String>): Flow<T?> {
    return runBlocking {
        context.dataStore.data.map { prefs ->
            prefs[key]?.let {
                try {
                    Json.decodeFromString<T>(it)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}