package com.example.datahentingtest.model

var postListe = mutableListOf<Kort>()

data class Kort (
    val brukerId: Int,
    val proveNavn: String
)