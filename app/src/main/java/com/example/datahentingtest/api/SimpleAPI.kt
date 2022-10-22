package com.example.datahentingtest.api

import com.example.datahentingtest.model.Post
import retrofit2.Response
import retrofit2.http.GET
interface SimpleAPI {
    @GET("BrukerQuiz/GameOfThrones")
    suspend fun getPost(): Response<Post>
}