package com.challenge.acronym.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.challenge.acronym.data.AcronymsDetail
import com.challenge.acronym.data.Converters
import com.challenge.acronym.data.Lfs
import com.challenge.acronym.BuildConfig.APP_DB
import com.challenge.acronym.BuildConfig.DB_VERSION

@Database(entities = [AcronymsDetail::class, Lfs::class], version = DB_VERSION, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AcronymDatabase : RoomDatabase() {

    abstract fun acronymDao(): RoomDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var INSTANCE: AcronymDatabase? = null
        fun getDatabase(context: Context): AcronymDatabase {

            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, AcronymDatabase::class.java, APP_DB)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }
    }
}