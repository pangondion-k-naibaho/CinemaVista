package com.cinemavista.client.view.activity.HomeActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import com.cinemavista.client.R
import com.cinemavista.client.databinding.ActivityHomeBinding
import com.cinemavista.client.view.activity.HomeActivity.fragment.DiscoverMovieFragment
import com.cinemavista.client.view.activity.HomeActivity.fragment.NowPlayingFragment
import com.cinemavista.client.view.activity.HomeActivity.fragment.PopularFragment
import com.cinemavista.client.view.activity.HomeActivity.fragment.TopRatedFragment
import com.cinemavista.client.view.activity.HomeActivity.fragment.UpcomingFragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity(), HomeCommunicator {
    private val TAG = HomeActivity::class.java.simpleName
    private lateinit var binding: ActivityHomeBinding

    private lateinit var adapterFragmentHome: AdapterFragmentHome
    private var currentPageIndex = 0

    companion object{
        fun newIntent(context: Context):Intent = Intent(context, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.hide()
        setUpView()
    }

    private fun setUpProgressBarForLoading(visible: Boolean){
        if(visible) binding.pbHome.visibility = View.VISIBLE else binding.pbHome.visibility = View.GONE
    }
    private fun setUpView(){
        adapterFragmentHome = AdapterFragmentHome(
            supportFragmentManager,
            lifecycle
        )

        val nowPlayingFragment = NowPlayingFragment.newInstance("Now Playing")
        val popularFragment = PopularFragment.newInstance("Popular")
        val discoverMovieFragment = DiscoverMovieFragment.newInstance("Discover Movie")
        val topRatedFragment = TopRatedFragment.newInstance("TopRated")
        val upcomingFragment = UpcomingFragment.newInstance("Upcoming")

        val listFragment = listOf(nowPlayingFragment, popularFragment, discoverMovieFragment, topRatedFragment, upcomingFragment)

        adapterFragmentHome.fragmentList.addAll(listFragment)

        binding.vpFragment.apply {
            offscreenPageLimit = listFragment.size
            adapter = adapterFragmentHome
            currentItem = 0
            val onPageChangeCallback = object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    currentPageIndex = position
                }

                override fun onPageScrollStateChanged(state: Int) {
                    if(state == SCROLL_STATE_IDLE){
                        when(currentPageIndex){
                            0 ->{
                                binding.bnvFragment.selectedItemId = R.id.menu_nowplaying
                            }
                            1 ->{
                                binding.bnvFragment.selectedItemId = R.id.menu_popular
                            }
                            2 ->{
                                binding.bnvFragment.selectedItemId = R.id.menu_discover
                            }
                            3 ->{
                                binding.bnvFragment.selectedItemId = R.id.menu_toprated
                            }
                            else ->{
                                binding.bnvFragment.selectedItemId = R.id.menu_upcoming
                            }
                        }
                    }
                }
            }
            registerOnPageChangeCallback(onPageChangeCallback)
        }

        binding.bnvFragment.apply {
            selectedItemId = R.id.menu_nowplaying
            setOnItemSelectedListener(object: NavigationBarView.OnItemSelectedListener{
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when(item.itemId){
                        R.id.menu_nowplaying ->{
                            binding.vpFragment.setCurrentItem(0, false)
                        }
                        R.id.menu_popular ->{
                            binding.vpFragment.setCurrentItem(1, false)
                        }
                        R.id.menu_discover ->{
                            binding.vpFragment.setCurrentItem(2, false)
                        }
                        R.id.menu_toprated ->{
                            binding.vpFragment.setCurrentItem(3, false)
                        }
                        R.id.menu_upcoming ->{
                            binding.vpFragment.setCurrentItem(4, false)
                        }
                    }
                    return true
                }

            })
        }
    }

    override fun onStartLoading() {
        setUpProgressBarForLoading(true)
    }

    override fun onStopLoading() {
        setUpProgressBarForLoading(false)
    }
}