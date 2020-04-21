package com.example.movies.ui.single_page_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movies.inf.api.POSTER_BASE_URL
import com.example.movies.inf.api.dbClient
import com.example.movies.inf.api.dbinterface
import com.example.movies.inf.obj.DetailsMovie
import com.example.movies.inf.repository.state_network
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class single_movie : AppCompatActivity() {
    private lateinit var viewModel: singleviewmodel
    private lateinit var movieRepository: MovieDetailsRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.movies.R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id",1)

        val apiService : dbinterface = dbClient.getClient()
        movieRepository = MovieDetailsRepo(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == state_network.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == state_network.ERROR) View.VISIBLE else View.GONE

        })

    }

    fun bindUI( it: DetailsMovie){
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.rating.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_movie_poster);

    }
    private fun getViewModel(movieId:Int): singleviewmodel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return singleviewmodel(movieRepository,movieId) as T
            }
        })[singleviewmodel::class.java]
    }
}
