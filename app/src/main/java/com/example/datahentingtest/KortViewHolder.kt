package com.example.datahentingtest

import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.databinding.ProveCardLayoutBinding
import com.example.datahentingtest.model.Kort

 class KortViewHolder(
    private val cardCellBinding: ProveCardLayoutBinding
) : RecyclerView.ViewHolder(cardCellBinding.root) {

    fun bindProve(prove: Kort) {
        cardCellBinding.cardImage.setImageResource(R.drawable.blyant)
        cardCellBinding.cardTittel.text = prove.proveNavn
        cardCellBinding.cardBruker.text = prove.brukerId.toString()

    }
}