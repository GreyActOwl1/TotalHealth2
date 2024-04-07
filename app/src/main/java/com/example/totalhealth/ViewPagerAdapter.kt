package com.example.totalhealth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
//          TODO: Add additional fragments
            0 -> StatsFragment()
            1 -> FoodOverviewFragment()
            else -> StatsFragment()
        }
    }
}
