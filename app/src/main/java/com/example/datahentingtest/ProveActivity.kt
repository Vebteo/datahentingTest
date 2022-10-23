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
import com.example.datahentingtest.model.Prove
import com.example.datahentingtest.repository.Repository

class ProveActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityProveBinding
    lateinit var hamburgerIkon: ActionBarDrawerToggle // Hamburger ikon
    private var tallSpm = 1
    var poengsum = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prove)
        hentProveData(tallSpm)
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
    private fun hentProveData(testVerdi: Int ){

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProve(testVerdi)
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
                //proveListe.add(prove1)

                binding.radiogruppeTekst!!.text = prove1.OppgaveTekst
                binding.radio1.text = prove1.RiktigSvar
                binding.radio2.text = prove1.Svar2
                binding.radio3.text = prove1.Svar3
                binding.radio4.text = prove1.Svar4
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
            when(binding.contactgroup.checkedRadioButtonId) {
                R.id.radio1 -> poengsum = poengsum + 1
            }

        if(poengsum == 3) {
            binding.txtPoengsum!!.text = poengsum.toString()
            binding.contactgroup.visibility = View.GONE
            binding.antSpm.visibility = View.GONE
            binding.resultatSkjerm.visibility = View.VISIBLE
            binding.progresjonBar.visibility = View.GONE
            binding.button2.visibility = View.GONE
        }

        binding.contactgroup.clearCheck()
        tallSpm += 1
        hentProveData(tallSpm)
        binding.progresjonBar.setProgress(tallSpm)
        }
    }

    fun avsluttProve(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}