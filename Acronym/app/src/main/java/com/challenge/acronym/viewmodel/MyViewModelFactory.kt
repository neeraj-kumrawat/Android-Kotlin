package com.challenge.acronym.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.challenge.acronym.room.AcronymDatabase
import com.challenge.acronym.repository.MainRepository


class MyViewModelFactory constructor(
    private val repository: MainRepository,
    private val acronymDatabase: AcronymDatabase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository, this.acronymDatabase) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}