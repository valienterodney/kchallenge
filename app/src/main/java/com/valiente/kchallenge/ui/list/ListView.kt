package com.valiente.kchallenge.ui.list

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.valiente.kchallenge.R
import com.valiente.kchallenge.model.Movie
import com.valiente.kchallenge.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_list.*

@AndroidEntryPoint
class ListView : AppCompatActivity() {

    private val list = ArrayList<Movie>()
    private val viewModel by viewModels<ListViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        init()
        subscribeUi()
    }

    private fun init() {
        title = "Movies"
        val layoutManager = LinearLayoutManager(this)
        movieList.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            movieList.context,
            layoutManager.orientation
        )

        movieList.addItemDecoration(dividerItemDecoration)
        moviesAdapter = MoviesAdapter(this, list)
        movieList.adapter = moviesAdapter
    }

    private fun subscribeUi() {
        viewModel.movieList.observe(this, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        moviesAdapter.updateData(list)
                    }
                    progressBar.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                    }
                    progressBar.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }

        })
    }

}