package com.miraz029.watchbox.di

import android.content.Context
import com.miraz029.watchbox.data.DataStoreManager
import com.miraz029.watchbox.data.dao.MovieDao
import com.miraz029.watchbox.data.WatchBoxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideWatchBoxDatabase(@ApplicationContext context: Context): WatchBoxDatabase {
        return WatchBoxDatabase.getInstance(context)
    }

    @Provides
    fun provideMovieDao(watchBoxDatabase: WatchBoxDatabase): MovieDao {
        return watchBoxDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext appContext: Context): DataStoreManager {
        return DataStoreManager(appContext)
    }
}
