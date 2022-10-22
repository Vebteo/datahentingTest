package com.example.datahentingtest.model


var proveListe = mutableListOf<Prove>()

data class Prove (
    val spørsmålNr: Int,
    val OppgaveTekst: String,
    val RiktigSvar: String,
    val Svar2: String,
    val Svar3: String,
    val Svar4: String,
    val Brukernavn: Int
)