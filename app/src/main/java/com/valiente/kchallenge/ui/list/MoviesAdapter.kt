package com.valiente.kchallenge.ui.list

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.valiente.kchallenge.R
import com.valiente.kchallenge.model.Movie
import com.valiente.kchallenge.ui.details.DetailsView
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

class MoviesAdapter(private val context: Context, private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    class MovieViewHolder(private val context: Context, itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            Log.i("m123", movie.toString())

            if (movie.seenDate != null) {
                Log.i("m123", "not null")
                val d = Date(movie.seenDate!!)
                val dateStr = SimpleDateFormat("yyyy.MM.dd HH:mm").format(d)
                itemView.txtSeen.text = "Seen: " + dateStr
            }

            itemView.txtTrackName.text = movie.trackName
            itemView.txtPrice.text = "$" + movie.trackPrice
            itemView.txtGenre.text = movie.primaryGenreName


            itemView.setOnClickListener {
                val intent = Intent(context, DetailsView::class.java)
                intent.putExtra(DetailsView.EXTRAS_MOVIE_ID, movie.trackId)
                context.startActivity(intent)
            }
            itemView.imgMovie.load(movie.artworkUrl100) {
                crossfade(true)
                placeholder(R.mipmap.ic_placeholder_image)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(context, view)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun updateData(newList: List<Movie>) {
        list.clear()
        val filteredList =
            newList.filter { movie -> !movie.trackName.isNullOrBlank() && !movie.trackPrice.isNullOrBlank() }
        val sortedList = filteredList.sortedBy { movie -> movie.trackName }
        list.addAll(sortedList)
        notifyDataSetChanged()
    }


}