package com.example.datahentingtest.repository

import com.example.datahentingtest.api.RetrofitInstance
import com.example.datahentingtest.model.Kort
import com.example.datahentingtest.model.Prove
import com.example.datahentingtest.model.OverMappe
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<Kort> {
        return RetrofitInstance.api.getPost()
    }
    suspend fun getProve(verdi: Int): Response<Prove> {
        return RetrofitInstance.api.getProve(verdi)
    }

    suspend fun getAlleProver(): Response<OverMappe> {
        return RetrofitInstance.api.getAlleProver()
    }

}