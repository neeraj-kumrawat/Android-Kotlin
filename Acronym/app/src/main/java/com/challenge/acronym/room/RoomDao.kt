package com.challenge.acronym.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.challenge.acronym.data.AcronymsDetail

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAcronymDetail(acronymsDetailList: List<AcronymsDetail>)

    @Query("SELECT * FROM acronymsDetail")
    suspend fun getAbbreviationsFromDB() : List<AcronymsDetail>
}