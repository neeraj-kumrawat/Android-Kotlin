package com.challenge.acronym.repository

import com.challenge.acronym.room.AcronymDatabase
import com.challenge.acronym.api.RetrofitService

class MainRepository constructor(
    private val retrofitService: RetrofitService,
    private val acronymDatabase: AcronymDatabase
) {
    suspend fun getAbbreviations(sf: String) =
        retrofitService.getAbbreviations(sf)

    suspend fun getAbbreviationHistory() =
        acronymDatabase.acronymDao().getAbbreviationsFromDB()

}