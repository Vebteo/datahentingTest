package com.example.datahentingtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datahentingtest.databinding.ProveCardLayoutBinding
import com.example.datahentingtest.model.Kort

class KortAdapter(private val prove: List<Kort>)
    : RecyclerView.Adapter<KortViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KortViewHolder {
        val fra = LayoutInflater.from(parent.context)
        val binding = ProveCardLayoutBinding.inflate(fra, parent, false)
        return KortViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KortViewHolder, position: Int) {
        holder.bindProve(prove[position])
    }

    override fun getItemCount(): Int = prove.size
}