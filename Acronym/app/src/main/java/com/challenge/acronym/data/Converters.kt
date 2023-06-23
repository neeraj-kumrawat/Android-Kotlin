package com.challenge.acronym.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromList(countryLang: List<Lfs?>?): String? {
        val type = object : TypeToken<List<Lfs?>?>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toList(countryLangString: String?): List<Lfs>? {
        val type = object : TypeToken<List<Lfs?>?>() {}.type
        return Gson().fromJson<List<Lfs>>(countryLangString, type)
    }
}