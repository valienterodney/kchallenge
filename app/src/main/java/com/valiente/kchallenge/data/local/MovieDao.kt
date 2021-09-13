package com.valiente.kchallenge.data.local

import androidx.room.*
import com.valiente.kchallenge.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE trackId = :trackId")
    fun get(trackId: Int): Movie

    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: Movie)

    @Delete
    fun delete(movie: Movie)

    @Delete
    fun deleteAll(movie: List<Movie>)

}