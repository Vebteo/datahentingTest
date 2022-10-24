package com.example.datahentingtest

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.repository.Repository
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.GridLayoutManager
import com.example.datahentingtest.databinding.ActivityMainBinding
import com.example.datahentingtest.model.kortListe
import com.example.datahentingtest.model.Kort
import com.example.datahentingtest.KortViewHolder
import com.example.datahentingtest.model.KORT_ID
import okhttp3.internal.notify

class MainActivity : AppCompatActivity(), KortClickListener {

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

        hentKortData()
        val mainActivity = this
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext,1)
            adapter = KortAdapter(kortListe,mainActivity)
        }

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.hjemItem->{ velgSide(binding.drawerLayout,1) }
                R.id.profilItem->{ velgSide(binding.drawerLayout,2) }
                R.id.loginItem->{ velgSide(binding.drawerLayout,3) }
            }
            true
        }
    }

    override fun onClick(kort: Kort) {
        val intent = Intent(this,ProveActivity::class.java)
        intent.putExtra(KORT_ID,kort.proveNavn)
        startActivity(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hentKortData(){
        /**
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost()
        viewModel.mutablePostResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                Log.d("Response", response.body()?.brukerId.toString())
                val post1 = Kort(
                    response.body()?.brukerId!!,
                    response.body()?.proveNavn!!
                )
                kortListe.add(post1)
            } else {
                Log.d("response", response.errorBody().toString())
            }
        })
        */

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAlleProver()
        viewModel.mutableAlleProverResponse.observe(this) { response ->
            if (response.isSuccessful) {
               //teksten.text = response.body()!!.records[0].proveNavn
               var i = 0
               while(i < response.body()!!.records.size-1) {
                   val post1 = Kort(
                    response.body()?.records!![i].brukerId!!,
                    response.body()?.records!![i].proveNavn!!
                    )
                kortListe.add(post1)
                i++
               }
            }
        }
    }


    private fun velgSide(view: View, tall: Int) {
        when(tall) {
            1 -> {
                finish()
                val startIntent = Intent(this, MainActivity::class.java)
                startActivity(startIntent)
            }
            2 -> {
                finish()
                val startIntent = Intent(this, ProfilActivity::class.java)
                startActivity(startIntent)
            }
            3 -> {
                finish()
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