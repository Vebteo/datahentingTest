package com.example.datahentingtest.model

var proveListe = mutableListOf<Post>()

data class Post (
    val brukerId: Int,
    val proveNavn: String
)