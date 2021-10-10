package com.miraz029.watchbox.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(var context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")

    val dataStore: DataStore<Preferences> = context.dataStore

    companion object {

        val SEARCH_KEY = stringPreferencesKey("SEARCH_KEY") // String key
    }


    suspend fun storeStringData(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getStringData(key: Preferences.Key<String>): Flow<String> = dataStore.data.map {
        it[key] ?: ""
    }
}