package com.juliaralves.unhaverso.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.juliaralves.unhaverso.domain.model.AppThemeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ThemeDataStore(context: Context) {
    private companion object {
        const val THEME_PREFERENCES = "theme_preferences"
        val THEME_ENUM_KEY = intPreferencesKey("theme")
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = THEME_PREFERENCES)
    }

    private val mutex = Mutex()
    private val dataStore = context.dataStore

    suspend fun updateTheme(theme: AppThemeEnum) = mutex.withLock {
        dataStore.edit { preferences ->
            preferences[THEME_ENUM_KEY] = theme.ordinal
        }
    }

    fun observeTheme(): Flow<AppThemeEnum?> {
        return dataStore.data.map { preferences ->
            val themeOrdinal = preferences[THEME_ENUM_KEY]
            themeOrdinal?.let { AppThemeEnum.entries[it] }
        }
    }
}