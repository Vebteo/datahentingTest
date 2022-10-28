package com.example.datahentingtest.model

var overBrukerListe = mutableListOf<OverBruker>()

data class OverBruker(
    val records: List<Bruker>
)