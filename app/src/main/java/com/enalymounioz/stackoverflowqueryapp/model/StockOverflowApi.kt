package com.enalymounioz.stackoverflowqueryapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StockOverflowApi {
    @GET("/2.3/questions?order=desc&sort=votes&tagged=android&site=stackoverflow")
    fun getQuestions(@Query("page") page: Int): Call<ResponseWrapper<Question>>
}