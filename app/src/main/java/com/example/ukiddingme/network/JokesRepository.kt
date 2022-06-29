package com.example.ukiddingme.network

import com.example.ukiddingme.model.JokesResponse
import retrofit2.Response
import javax.inject.Inject


interface JokesRepository {
    suspend fun getJokes(qty:Int?=null):Response<JokesResponse>
}

class JokesRepositoryImpl @Inject constructor(
    private val jokesService: JokesService

) : JokesRepository {
    override suspend fun getJokes(qty: Int?): Response<JokesResponse>  {
        return qty?.let {
            jokesService.getJokes(it)
        } ?: jokesService.getJokes()
}

}