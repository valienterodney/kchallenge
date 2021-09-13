package com.valiente.kchallenge.ui.details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.load
import com.valiente.kchallenge.R
import com.valiente.kchallenge.model.Movie
import com.valiente.kchallenge.model.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsView : AppCompatActivity() {

    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.getIntExtra(EXTRAS_MOVIE_ID,0)?.let { id ->
            viewModel.getMovieDetail(id)
            subscribeUi()
        } ?: Log.i("test","test")
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun subscribeUi() {
        viewModel.movie.observe(this, Observer { result ->

            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        updateUi(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
//                        showError(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
            }
        })
    }

//    private fun showError(msg: String) {
//        Snackbar.make(vParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
//        }.show()
//    }

    private fun updateUi(movie: Movie) {
        title = movie.trackName
        textTrackName.text = movie.trackName
        textGenre.text = movie.primaryGenreName
        textLongDesc.text = movie.longDescription
        textPrice.text = "$" + movie.trackPrice
        ivCover.load(movie.artworkUrl100) {
            crossfade(true)
            placeholder(R.mipmap.ic_placeholder_image)
        }
    }

    companion object {
        const val EXTRAS_MOVIE_ID = "track_id"
    }
}