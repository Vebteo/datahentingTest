package com.example.datahentingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.repository.Repository

class TestActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val teksten : TextView = findViewById(R.id.testerTekstingen)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAlleProver()
        viewModel.mutableAlleProverResponse.observe(this) { response ->
            if (response.isSuccessful) {
               teksten.text = response.body()!!.records[0].proveNavn

            }
        }
    }
}