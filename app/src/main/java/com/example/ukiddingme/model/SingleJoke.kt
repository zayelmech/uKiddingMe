package com.example.ukiddingme.model


import com.google.gson.annotations.SerializedName

data class SingleJoke(
    @SerializedName("type")
    val type: String,
    @SerializedName("value")
    val value: Joke
)