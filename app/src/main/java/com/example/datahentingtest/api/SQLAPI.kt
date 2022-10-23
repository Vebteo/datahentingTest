package com.example.datahentingtest.api

import com.example.datahentingtest.model.Kort
import com.example.datahentingtest.model.OverMappe
import com.example.datahentingtest.model.Prove
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SQLAPI {
    @GET("BrukerQuiz/GameOfThrones")
    suspend fun getPost(): Response<Kort>

    @GET("GameOfThrones/{SporsmalNr}")
    suspend fun getProve(@Path("SporsmalNr") SporsmalNr: Int): Response<Prove>

    @GET("BrukerQuiz")
    suspend fun getAlleProver(): Response<OverMappe>
}
