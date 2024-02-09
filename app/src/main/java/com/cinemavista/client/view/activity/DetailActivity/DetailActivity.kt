package com.cinemavista.client.view.activity.DetailActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cinemavista.client.R
import com.cinemavista.client.databinding.ActivityDetailBinding
import com.cinemavista.client.model.data_class.response.MovieInformation

class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var binding: ActivityDetailBinding
    private lateinit var retrievedMovieInformation: MovieInformation

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
        retrievedMovieInformation = intent.extras!!.get(DELIVERED_MOVIE_INFORMATION) as MovieInformation

        Log.d(TAG, "retrieved movie information: ${retrievedMovieInformation}")
    }
}