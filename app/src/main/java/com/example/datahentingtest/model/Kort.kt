package com.example.datahentingtest.model

var kortListe = mutableListOf<Kort>()

data class Kort (
    val brukerId: Int,
    val proveNavn: String
)