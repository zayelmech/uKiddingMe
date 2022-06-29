package com.example.ukiddingme.network

import com.example.ukiddingme.model.JokesResponse
import retrofit2.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface JokesService {

    @GET(RANDOM_PATH)
   suspend fun getJokes(
        @Path("qty") qty : Int? = null
    ) : Response<JokesResponse>

    companion object{
        const val BASE_URL="http://api.icndb.com/jokes/"
        private const val RANDOM_PATH = "random/{qty}"

    }
}