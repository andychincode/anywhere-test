package com.test.anywhere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.anywhere.model.SearchResult
import com.test.anywhere.repository.SearchRepository

class MainViewModel : ViewModel() {
    var resultLiveData: MutableLiveData<SearchResult>? = null

    fun getResult() : LiveData<SearchResult>? {
        resultLiveData = SearchRepository.searchApiCall()
        return resultLiveData
    }
}