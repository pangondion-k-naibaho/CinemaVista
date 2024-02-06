package com.cinemavista.client.view.activity.LoadScreenActivity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cinemavista.client.R
import com.cinemavista.client.databinding.ActivityLoadscreenBinding
import com.cinemavista.client.view.activity.HomeActivity.HomeActivity

class LoadScreenActivity : Activity() {
    private val TAG = LoadScreenActivity::class.java.simpleName
    private lateinit var binding: ActivityLoadscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition{false}
        actionBar?.hide()
        setUpView()
    }

    private fun setUpView(){
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            startActivity(
                HomeActivity.newIntent(this@LoadScreenActivity)
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        },4000)
    }
}