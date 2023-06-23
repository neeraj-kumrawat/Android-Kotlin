package com.challenge.acronym.api

import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class RetrofitServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RetrofitService
    private lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RetrofitService::class.java)
    }


    @Test
    fun `get all abbreviation api test`() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("[]"))
            val response = apiService.getAbbreviations("abc")
            val request = mockWebServer.takeRequest()
            assertEquals("/acromine/dictionary.py?sf=abc",request.path)
            assertEquals(true, response.body()?.isEmpty() == true)
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

}