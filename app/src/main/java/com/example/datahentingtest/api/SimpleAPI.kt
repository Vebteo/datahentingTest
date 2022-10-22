package com.example.datahentingtest.api

import com.example.datahentingtest.ProveActivity
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.model.Prove
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpleAPI {
    @GET("BrukerQuiz/GameOfThrones")
    suspend fun getPost(): Response<Post>

    @GET("GameOfThrones/{SpørsmålNr}")
    suspend fun getProve(@Path("SpørsmålNr") spnr: String): Response<Prove>



}
