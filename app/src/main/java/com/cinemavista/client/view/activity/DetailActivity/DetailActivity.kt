package com.cinemavista.client.view.activity.DetailActivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cinemavista.client.R
import com.cinemavista.client.databinding.ActivityDetailBinding
import com.cinemavista.client.model.Constants
import com.cinemavista.client.model.Constants.URL.Companion.TMDBIMAGE_URL
import com.cinemavista.client.model.Constants.URL.Companion.YOUTUBE_URL
import com.cinemavista.client.model.data_class.response.MovieInformation
import com.cinemavista.client.view.adapter.ItemUserReviewAdapter
import com.cinemavista.client.view.advanced_ui.PopUpDialogListener
import com.cinemavista.client.view.advanced_ui.showPopUpDialog
import com.cinemavista.client.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var binding: ActivityDetailBinding
    private lateinit var retrievedMovieInformation: MovieInformation
    private val detailViewModel by viewModels<DetailViewModel>()
    private var currentPage: Int? = null

    companion object{
        const val DELIVERED_MOVIE_INFORMATION = "DELIVERED_MOVIE_INFORMATION"
        fun newIntent(context: Context, inputMovie: MovieInformation): Intent = Intent(context, DetailActivity::class.java)
            .putExtra(DELIVERED_MOVIE_INFORMATION, inputMovie)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        actionBar?.hide()
    }

    private fun setUpView(){
        currentPage = 1
        retrievedMovieInformation = intent.extras!!.get(DELIVERED_MOVIE_INFORMATION) as MovieInformation
        Log.d(TAG, "retrieved movie information: ${retrievedMovieInformation}")

        binding.apply {
            setSupportActionBar(detailToolbar)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_white)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)

            binding.detailToolbar.findViewById<TextView>(R.id.tvToolbarTitle).text = getString(R.string.tvTitle_detailMovie)

            Glide.with(this@DetailActivity)
                .load(TMDBIMAGE_URL+retrievedMovieInformation.poster_path)
                .into(ivPosterMovie)

            tvMovieTitle.text = retrievedMovieInformation.title
            tvMovieOriginalTitle.visibility = if(retrievedMovieInformation.title.equals(retrievedMovieInformation.original_title)) View.GONE else View.VISIBLE
            tvMovieOriginalTitle.text = retrievedMovieInformation.original_title

            when(retrievedMovieInformation.adult){
                true ->{
                    tvAdult.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_adult_red, 0, 0, 0)
                    tvAdult.text = getString(R.string.tvDetail_AdultContent)
                }
                false ->{
                    tvAdult.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_family_yellow, 0, 0, 0)
                    tvAdult.text = getString(R.string.tvDetail_NotAdultContent)
                }
            }

            tvReleaseDate.text = retrievedMovieInformation.release_date
            val realGenres = convertGenres(retrievedMovieInformation.genre_ids!!)
            tvGenre.text = realGenres.joinToString(", ")


            tvOverviewMovie.text = retrievedMovieInformation.overview

            detailViewModel.getMovieVideoCollection(retrievedMovieInformation.id!!)

            detailViewModel.getMovieReviewCollection(retrievedMovieInformation.id!!, currentPage!!)

            Glide.with(this@DetailActivity)
                .load(TMDBIMAGE_URL+retrievedMovieInformation.backdrop_path)
                .into(ivMovieBackdrop)

            detailViewModel.isLoading.observe(this@DetailActivity, {
                if (it) pbDetail.visibility = View.VISIBLE else pbDetail.visibility = View.GONE
            })

            detailViewModel.isFail.observe(this@DetailActivity, {
                if(it){
                    this@DetailActivity.showPopUpDialog(
                        getString(R.string.tvPopUpDescription),
                        R.drawable.sadness,
                        object: PopUpDialogListener{
                            override fun onClickListener() {
                                closeOptionsMenu()
                            }
                        }
                    )
                }
            })

            detailViewModel.movieVideoResponse.observe(this@DetailActivity, {movieVideos->
                Log.d(TAG, "movie videos: ${movieVideos}")
                val sampleMovieTrailer = movieVideos.results!!.get(0)

                val trailerUrl = YOUTUBE_URL+"/watch?v="+sampleMovieTrailer.key

                ivMovieBackdrop.setOnClickListener {
                    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                    startActivity(appIntent)
                }

                ivPlayMovieTrailer.setOnClickListener {
                    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
                    startActivity(appIntent)
                }

            })

            detailViewModel.movieReviewCollection.observe(this@DetailActivity, {movieReviewCollection->
                Log.d(TAG, "retrieved movie Review: ${movieReviewCollection}")
                rvMovieUserReview.apply {
                    val movieReviewAdapter = ItemUserReviewAdapter(
                        movieReviewCollection.results!!.toMutableList()
                    )
                    val rvLayoutManager = LinearLayoutManager(this@DetailActivity)

                    adapter = movieReviewAdapter
                    layoutManager = rvLayoutManager
                    addOnScrollListener(object: RecyclerView.OnScrollListener(){
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            val visibleItemCount = rvLayoutManager.childCount
                            val totalItemCount = rvLayoutManager.itemCount
                            val firstVisibleItemPosition = rvLayoutManager.findFirstVisibleItemPosition()

                            if((visibleItemCount+firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= movieReviewCollection.results!!.size
                            ){
                                currentPage = currentPage?.plus(1)
                                detailViewModel.getMovieReviewCollectionMore(retrievedMovieInformation.id!!, currentPage!!)
                                detailViewModel.movieReviewCollection2.observe(this@DetailActivity, {neoMovieReviewCollection ->
                                    if(!neoMovieReviewCollection.results.isNullOrEmpty()){
                                        movieReviewAdapter.addItem(neoMovieReviewCollection.results!!)
                                    }
                                })
                            }
                        }
                    })
                }
            })

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun convertGenres(genres: List<Int>): List<String> {
        return genres.map { genre ->
            when (genre) {
                Constants.GENRE_CONSTANTS.ACTION -> "Action"
                Constants.GENRE_CONSTANTS.ADVENTURE -> "Adventure"
                Constants.GENRE_CONSTANTS.ANIMATION -> "Animation"
                Constants.GENRE_CONSTANTS.COMEDY -> "Comedy"
                Constants.GENRE_CONSTANTS.CRIME -> "Crime"
                Constants.GENRE_CONSTANTS.DOCUMENTARY -> "Documentary"
                Constants.GENRE_CONSTANTS.DRAMA -> "Drama"
                Constants.GENRE_CONSTANTS.FAMILY -> "Family"
                Constants.GENRE_CONSTANTS.FANTASY -> "Fantasy"
                Constants.GENRE_CONSTANTS.HISTORY -> "History"
                Constants.GENRE_CONSTANTS.MUSIC -> "Music"
                Constants.GENRE_CONSTANTS.MYSTERY -> "Mystery"
                Constants.GENRE_CONSTANTS.ROMANCE -> "Romance"
                Constants.GENRE_CONSTANTS.SCIENCE_FICTION -> "Science Fiction"
                Constants.GENRE_CONSTANTS.TV_MOVIE -> "TV Movie"
                Constants.GENRE_CONSTANTS.THRILLER -> "Thriller"
                Constants.GENRE_CONSTANTS.WAR -> "War"
                Constants.GENRE_CONSTANTS.WESTERN -> "Western"
                Constants.GENRE_CONSTANTS.HORROR -> "Horror"
                else -> "Unknown"
            }
        }
    }

}