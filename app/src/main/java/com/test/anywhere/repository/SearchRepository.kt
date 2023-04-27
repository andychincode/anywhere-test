package com.test.anywhere.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.test.anywhere.BuildConfig
import com.test.anywhere.model.SearchResult
import com.test.anywhere.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchRepository {

    val searchResult = MutableLiveData<SearchResult>()

    fun searchApiCall(): MutableLiveData<SearchResult> {
        val call = RetrofitClient.apiInterface.search(BuildConfig.QUERY, "json")

        call.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                Log.v("DEBUG : ", response.body().toString())
                searchResult.value = response.body()
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                t.localizedMessage?.let { Log.v("DEBUG : ", it) }
            }
        })

        return searchResult
    }
}