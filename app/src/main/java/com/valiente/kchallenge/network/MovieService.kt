package com.valiente.kchallenge.network

import com.valiente.kchallenge.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET("/search?term=star&amp;country=au&amp;media=movie&amp;all")
    suspend fun getMovies() : Response<MovieResponse>

}