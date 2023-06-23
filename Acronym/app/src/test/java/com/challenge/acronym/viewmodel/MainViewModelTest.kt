package com.challenge.acronym.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.acronym.api.RetrofitService
import com.challenge.acronym.data.AcronymsDetail
import com.challenge.acronym.data.Lfs
import com.challenge.acronym.getOrAwaitValue
import com.challenge.acronym.repository.MainRepository
import com.challenge.acronym.room.AcronymDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository

    @Mock
    lateinit var acronymDatabase: AcronymDatabase

    @Mock
    lateinit var apiService: RetrofitService

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainRepository = MainRepository(apiService, acronymDatabase)
        mainViewModel = MainViewModel(mainRepository, acronymDatabase)
    }

    @Test
    fun getAbbreviationsTest() {
        val response = listOf(
            AcronymsDetail(
                "ABC",
                listOf(Lfs("ATP-binding cassette", 1437, 1990))
            )
        )
        runBlocking {
            Mockito.`when`(mainRepository.getAbbreviations("abc"))
                .thenReturn(
                    Response.success(
                        response
                    )
                )

            mainViewModel.getAbbreviations("abc")
            val result = mainViewModel.acronymsDetailList.getOrAwaitValue()
            assertEquals(response, result)
        }
    }


    @Test
    fun `empty abbreviations list test`() {
        runBlocking {
            Mockito.`when`(mainRepository.getAbbreviations("abc"))
                .thenReturn(Response.success(listOf()))
            mainViewModel.getAbbreviations("abc")
            val result = mainViewModel.acronymsDetailList.getOrAwaitValue()
            assertEquals(listOf<AcronymsDetail>(), result)
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}