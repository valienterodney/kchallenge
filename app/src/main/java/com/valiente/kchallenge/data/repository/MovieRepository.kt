package com.valiente.kchallenge.data.repository

import android.util.Log
import com.valiente.kchallenge.data.local.MovieDao
import com.valiente.kchallenge.data.remote.MovieRemoteNetwork
import com.valiente.kchallenge.model.Movie
import com.valiente.kchallenge.model.MovieResponse
import com.valiente.kchallenge.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteNetwork,
    private val movieDao: MovieDao
) {

    suspend fun fetchMovies(): Flow<Result<MovieResponse>?> {
        return flow {
            emit(getCachedMovies())
            emit(Result.loading())
            val result = movieRemoteDataSource.getMovies()

            //Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.results?.let { it ->
                    movieDao.insertAll(it)
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetails(trackId: Int): Flow<Result<Movie>> {
        return flow {
            emit(Result.loading())
            val movie = getCachedMovie(trackId).data
            Log.w("getmovie: " , movie.toString())
            if (movie != null) {
                movie.seenDate = Calendar.getInstance().time.time
                Log.w("getmovie2: " , movie.toString())
                movieDao.insert(movie)
                fetchMovies()
            }
            emit(getCachedMovie(trackId))
        }.flowOn(Dispatchers.IO)
    }

    private fun getCachedMovies(): Result<MovieResponse>? =
        movieDao.getAll()?.let {
            Result.success(MovieResponse(it))
        }

    private fun getCachedMovie(trackId: Int): Result<Movie> =
        movieDao.get(trackId).let {
           Result.success(it)
        }



}