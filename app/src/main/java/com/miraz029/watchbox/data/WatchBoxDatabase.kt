package com.miraz029.watchbox.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miraz029.watchbox.data.dao.MovieDao
import com.miraz029.watchbox.data.model.Movie
import com.miraz029.watchbox.utilities.DATABASE_NAME

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class WatchBoxDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var instance: WatchBoxDatabase? = null

        fun getInstance(context: Context): WatchBoxDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WatchBoxDatabase {
            return Room.databaseBuilder(context, WatchBoxDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    }
                )
                .build()
        }
    }
}