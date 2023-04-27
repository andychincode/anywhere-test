package com.test.anywhere.retrofit

import com.test.anywhere.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/")
    fun search(@Query("q") q : String, @Query("format") format : String): Call<SearchResult>
}