package com.example.datahentingtest.repository

import com.example.datahentingtest.api.RetrofitInstance
import com.example.datahentingtest.model.Post
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<Post> {
        return RetrofitInstance.api.getpost()
    }

}