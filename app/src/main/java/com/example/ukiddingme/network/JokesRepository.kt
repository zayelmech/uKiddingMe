package com.example.ukiddingme.network

import com.example.ukiddingme.model.JokesResponse
import com.example.ukiddingme.model.SingleJoke
import retrofit2.Response
import javax.inject.Inject


interface JokesRepository {
    suspend fun getJokes(qty:Int?=null):Response<JokesResponse>
    suspend fun getCustomJoke(firstName: String, lastName: String): Response<SingleJoke>
    suspend fun getRandomJoke(): Response<SingleJoke>
}

class JokesRepositoryImpl @Inject constructor(
    private val jokesService: JokesService

) : JokesRepository {
    override suspend fun getJokes(qty: Int?): Response<JokesResponse>  {
        return qty?.let {
            jokesService.getJokes(it)
        } ?: jokesService.getJokes()
}

    override suspend fun getCustomJoke(
        firstName: String,
        lastName: String
    ): Response<SingleJoke> {
        return jokesService.getPersonalizedJoke(firstName,lastName)
    }

    override suspend fun getRandomJoke(): Response<SingleJoke> {
        return jokesService.getRandomJoke()
    }

}