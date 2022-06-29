package com.example.ukiddingme.model


import com.google.gson.annotations.SerializedName

data class JokesResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: List<Jokes>
)