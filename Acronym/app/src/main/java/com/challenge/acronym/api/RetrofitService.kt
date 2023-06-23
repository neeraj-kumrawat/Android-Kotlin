package com.challenge.acronym.api

import com.challenge.acronym.BuildConfig.BASE_URL
import com.challenge.acronym.data.AcronymsDetail
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("acromine/dictionary.py")
    suspend fun getAbbreviations(@Query("sf") sf: String): Response<List<AcronymsDetail>>

    companion object {

        private var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {

            if (retrofitService != null) return retrofitService!!
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
            return retrofitService!!
        }
    }
}