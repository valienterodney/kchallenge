package com.valiente.kchallenge.data.remote

import com.valiente.kchallenge.model.MovieResponse
import com.valiente.kchallenge.model.Result
import com.valiente.kchallenge.network.MovieService
import com.valiente.kchallenge.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MovieRemoteNetwork @Inject constructor(private val retrofit: Retrofit){

    suspend fun getMovies(): Result<MovieResponse > {
        val movieService = retrofit.create(MovieService::class.java);
        return getResponse(
            request = { movieService.getMovies()},
            defaultErrorMessage = "Error fetching Movie list")
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {

                val errorResponse = ErrorUtils.parseError(result, retrofit)
                println("Error response $errorResponse")
                Result.error(errorResponse?.message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            println("Catch ${e.message}")
            Result.error("Unknown Error", null)
        }
    }
}