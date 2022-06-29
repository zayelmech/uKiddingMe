package com.example.ukiddingme.network

import com.example.ukiddingme.model.JokesResponse
import com.example.ukiddingme.model.SingleJoke
import com.example.ukiddingme.network.JokesService.Companion.CHARACTER_PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokesService {

    @GET(RANDOM_SET_PATH)
    suspend fun getJokes(
        @Path("qty") qty: Int? = null
    ): Response<JokesResponse>

    @GET(CHARACTER_PATH)
    suspend fun getPersonalizedJoke(
        @Query("firstName") firstName: String = "John",
        @Query("lastName") lastName: String = "Doe"

    ): Response<SingleJoke>

    @GET(CHARACTER_PATH)
    suspend fun getRandomJoke():  Response<SingleJoke>

    companion object {
        const val BASE_URL = "http://api.icndb.com/jokes/"

        private const val CHARACTER_PATH = "random"
        private const val RANDOM_SET_PATH = "random/{qty}"

    }
}