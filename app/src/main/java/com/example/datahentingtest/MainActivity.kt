package com.example.datahentingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
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
    lateinit var hamburgerIkon: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        hamburgerIkon= ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = KortAdapter(proveListe)
        }

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.hjemItem->{ velgSide(binding.drawerLayout,1) }
                R.id.profilItem->{ velgSide(binding.drawerLayout,2) }
                R.id.loginItem->{ velgSide(binding.drawerLayout,3) }
            }
            true
        }

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
        if(response.isSuccessful){
            Log.d("Response", response.body()?.brukerId.toString())
            val post1 = Post(
                response.body()?.brukerId!!,
                response.body()?.proveNavn!!
            )
            proveListe.add(post1)
        } else {
          Log.d("response", response.errorBody().toString())
        }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    fun velgSide(view: View, tall: Int) {
        when(tall) {
            1 -> {
                val startIntent = Intent(this, MainActivity::class.java)
                startActivity(startIntent)
            }
            2 -> {
                val startIntent = Intent(this, ProfilActivity::class.java)
                startActivity(startIntent)
            }
            3 -> {
                val startIntent = Intent(this, LoginActivity::class.java)
                startActivity(startIntent)
            }
        }
    }
    fun startProve(view: View) {
        val startIntent = Intent(this, ProveActivity::class.java)
        startActivity(startIntent)
    }
}