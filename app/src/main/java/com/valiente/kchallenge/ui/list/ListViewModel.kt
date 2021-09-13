package com.valiente.kchallenge.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valiente.kchallenge.data.repository.MovieRepository
import com.valiente.kchallenge.model.MovieResponse
import com.valiente.kchallenge.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val _movieList = MutableLiveData<Result<MovieResponse>>()
    val movieList = _movieList

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            movieRepository.fetchMovies().collect {
                _movieList.value = it
            }
        }
    }
}