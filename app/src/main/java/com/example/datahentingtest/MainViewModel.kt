package com.example.datahentingtest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datahentingtest.model.Kort
import com.example.datahentingtest.model.OverMappe
import com.example.datahentingtest.model.Prove
import com.example.datahentingtest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    private val mutablePostResponse: MutableLiveData<Response<Kort>> = MutableLiveData()
    val mutableProveResponse: MutableLiveData<Response<Prove>> = MutableLiveData()
    val mutableAlleProverResponse: MutableLiveData<Response<OverMappe>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val responsePost :Response<Kort> = repository.getPost()
            mutablePostResponse.value = responsePost

        }
    }
    fun getProve(verdien: Int, verdien2: String) {
        viewModelScope.launch {
            val responseProve :Response<Prove> = repository.getProve(verdien, verdien2)
            mutableProveResponse.value = responseProve

        }
    }

    fun getAlleProver() {
        viewModelScope.launch {
            val responseAlleProver : Response<OverMappe> = repository.getAlleProver()
            mutableAlleProverResponse.value = responseAlleProver
        }
    }


}