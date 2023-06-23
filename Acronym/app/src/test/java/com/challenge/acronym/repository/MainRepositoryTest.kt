package com.challenge.acronym.repository

import com.challenge.acronym.api.RetrofitService
import com.challenge.acronym.data.AcronymsDetail
import com.challenge.acronym.room.AcronymDatabase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@Suppress("DEPRECATION")
@RunWith(JUnit4::class)
class MainRepositoryTest {

    private lateinit var mainRepository: MainRepository

    @Mock
    lateinit var apiService: RetrofitService

    @Mock
    lateinit var acronymDatabase: AcronymDatabase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(apiService, acronymDatabase)
    }

    @Test
    fun `get all abbreviation test`() {
        runBlocking {
            Mockito.`when`(apiService.getAbbreviations("abc"))
                .thenReturn(Response.success(listOf()))
            val response = mainRepository.getAbbreviations("abc")
            assertEquals(listOf<AcronymsDetail>(), response.body())
        }

    }

}