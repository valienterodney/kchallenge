package com.valiente.kchallenge.ui.details

import androidx.lifecycle.*
import com.valiente.kchallenge.data.repository.MovieRepository
import com.valiente.kchallenge.model.Movie
import com.valiente.kchallenge.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _movie: LiveData<Result<Movie>> = _id.distinctUntilChanged().switchMap {
        liveData {
            movieRepository.getMovieDetails(it).collect {
                emit(it)
            }
        }
    }

    val movie = _movie

    fun getMovieDetail(id: Int) {
        _id.value = id
    }
}