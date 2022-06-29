package com.example.ukiddingme.utils

import com.example.ukiddingme.model.JokesResponse
import com.example.ukiddingme.model.SingleJoke

/**
 * This is a sealed class known as an enum with superpowers
 * since you are able to pass dynamic parameters to each child
 * */
sealed class UIState{

    object LOADING : UIState()
    data class SUCCESS<T>(val response: T): UIState()
    data class ERROR(val error: Exception): UIState()
}

/**
 * This is a enum class with static fields for the state of the response
 * */
enum class STATE(val response: String){
    LOADING(""),
    ERROR(""),
    SUCCESS("")
}