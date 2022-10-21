package com.example.datahentingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.datahentingtest.repository.Repository
import com.example.datahentingtest.R
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datahentingtest.model.Post
import com.example.datahentingtest.model.proveListe
import com.example.datahentingtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)


        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = KortAdapter(proveListe)
        }

        var textView : TextView = findViewById(R.id.tekstTesting)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
        if(response.isSuccessful){
            Log.d("Response", response.body()?.brukerId.toString())
            textView.text = response.body()?.proveNavn!!
            val post1 = Post(
                response.body()?.brukerId!!,
                response.body()?.proveNavn!!
            )
            proveListe.add(post1)
        } else {
          Log.d("response", response.errorBody().toString())
            textView.text = response.code().toString()
        }
        })
    }
}