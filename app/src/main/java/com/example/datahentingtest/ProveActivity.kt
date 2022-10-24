package com.example.datahentingtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.datahentingtest.databinding.ActivityProveBinding
import com.example.datahentingtest.model.*
import com.example.datahentingtest.repository.Repository
import com.squareup.moshi.Types.arrayOf
import java.util.Arrays
import java.util.Random

class ProveActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityProveBinding
    lateinit var hamburgerIkon: ActionBarDrawerToggle // Hamburger ikon
    private var tallSpm = 1
    private lateinit var proveNavnet: String
    var poengsum = 0
    lateinit var riktigSvaret: String
    var returVerdi = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prove)



        val kortID = intent.getStringExtra(KORT_ID)
        val kortet = kortFraID(kortID!!)
        proveNavnet = kortet!!.proveNavn
        hentProveData(tallSpm, proveNavnet)
        hamburgerIkon = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(hamburgerIkon)
        hamburgerIkon.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.hjemItem->{ velgSide(binding.drawerLayout,1) }
                    R.id.profilItem->{ velgSide(binding.drawerLayout,2) }
                    R.id.loginItem->{ velgSide(binding.drawerLayout,3) }
                }
                true
            }
    }

    fun getAntallSporsmal(proveNavnet: String): Int {
        val repository2 = Repository()
        val viewModelFactory2 = MainViewModelFactory(repository2)
        viewModel = ViewModelProvider(this, viewModelFactory2).get(MainViewModel::class.java)
        viewModel.getStorrelse(proveNavnet)
        viewModel.mutableStorrelseResponse.observe(this) { response2 ->
            if (response2.isSuccessful) {
                returVerdi =  response2.body()!!.records.size
            }
        }
        return returVerdi
    }


    private fun hentProveData(testVerdi: Int, testVerdi2: String){
        var ant = getAntallSporsmal(testVerdi2)
        binding.antSpm.text = "Spørsmål: $tallSpm/$ant"

        binding.progresjonBar.max = ant
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProve(testVerdi, proveNavnet)
        viewModel.mutableProveResponse.observe(this, Observer { response ->
            if(response.isSuccessful){
                val prove1 = Prove(
                    response.body()?.SpørsmålNr!!,
                    response.body()?.OppgaveTekst!!,
                    response.body()?.RiktigSvar!!,
                    response.body()?.Svar2!!,
                    response.body()?.Svar3!!,
                    response.body()?.Svar4!!,
                    response.body()?.Brukernavn!!
                )
                riktigSvaret = prove1.RiktigSvar


                val svarListe = listOf(prove1.RiktigSvar,prove1.Svar2,prove1.Svar3,prove1.Svar4)
                var randomListe = svarListe.shuffled()
                binding.radiogruppeTekst!!.text = prove1.OppgaveTekst
                binding.radio1.text = randomListe[0]
                binding.radio2.text = randomListe[1]
                binding.radio3.text = randomListe[2]
                binding.radio4.text = randomListe[3]

                /**
                //proveListe.add(prove1)
                binding.radiogruppeTekst!!.text = prove1.OppgaveTekst
                binding.radio1.text = prove1.RiktigSvar
                binding.radio2.text = prove1.Svar2
                binding.radio3.text = prove1.Svar3
                binding.radio4.text = prove1.Svar4
                */
                //proveListe.add(prove1)


            } else {
                Log.d("response", response.errorBody().toString())
            }
        })

    }

    private fun kortFraID(kortID: String): Kort? {
        for(kort in kortListe) {
            if(kort.proveNavn == kortID)
                return kort
        }
        return null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(hamburgerIkon.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun velgSide(view: View, tall: Int) {
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

    fun visResultat(view: View) {
        if(binding.contactgroup.checkedRadioButtonId == -1) {
         //   binding.txtBesvar.text = "Du må velge et svar"
        }
        else {
            /**
            when(binding.contactgroup.checkedRadioButtonId) {
            R.id.radio1 -> poengsum = poengsum + 1
            }
             */
            when (binding.contactgroup.checkedRadioButtonId) {
                R.id.radio1 -> {
                    if (binding.radio1.text == riktigSvaret) {
                        poengsum = poengsum + 1
                    }
                }
                R.id.radio2 -> {
                    if (binding.radio2.text == riktigSvaret) {
                        poengsum = poengsum + 1
                    }
                }
                R.id.radio3 -> {
                    if (binding.radio3.text == riktigSvaret) {
                        poengsum = poengsum + 1
                    }
                }
                R.id.radio4 -> {
                    if (binding.radio4.text == riktigSvaret) {
                        poengsum = poengsum + 1
                    }
                }
            }
            binding.contactgroup.clearCheck()
            binding.progresjonBar.setProgress(tallSpm)
            tallSpm += 1
            hentProveData(tallSpm, proveNavnet)

        }

            if(tallSpm > returVerdi) {
                binding.txtPoengsum!!.text = "Poengsum: $poengsum/$returVerdi"
                binding.contactgroup.visibility = View.GONE
                binding.antSpm.visibility = View.GONE
                binding.progresjonBar.visibility = View.GONE
                binding.button2.visibility = View.GONE
                binding.resultatSkjerm.visibility = View.VISIBLE
            }
    }

    fun avsluttProve(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}