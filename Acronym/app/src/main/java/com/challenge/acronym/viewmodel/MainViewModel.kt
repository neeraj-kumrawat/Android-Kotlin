package com.challenge.acronym.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.acronym.data.AcronymsDetail
import com.challenge.acronym.repository.MainRepository
import com.challenge.acronym.room.AcronymDatabase
import kotlinx.coroutines.*


class MainViewModel constructor(
    private val repository: MainRepository,
    private val acronymDatabase: AcronymDatabase
) : ViewModel() {

    val acronymsDetailList = MutableLiveData<List<AcronymsDetail>>()
    val abbreviationHistory = MutableLiveData<List<AcronymsDetail>>()
    val errorMessage = MutableLiveData<String>()
    private var job: Job? = null
    val isLoading = MutableLiveData<Boolean>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAbbreviations(sf: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAbbreviations(sf)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    acronymsDetailList.postValue(response.body())
                    isLoading.value = false
                    if (!response.body().isNullOrEmpty()) acronymDatabase.acronymDao()
                        .insertAcronymDetail(response.body()!!)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        isLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getAbbreviationHistory() = viewModelScope.launch {
        abbreviationHistory.postValue(repository.getAbbreviationHistory())
    }

}