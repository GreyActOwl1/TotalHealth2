package com.example.totalhealth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class OverviewFragment : Fragment() {
    private  lateinit var viewPager: ViewPager2
    private lateinit var  viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         viewPager = view.findViewById(R.id.view_pager)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Stats"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_leaderboard_24)
                }
                1 -> {
                    tab.text = "Food"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_timeline_24)
                }
                2 -> {
                    tab.text = "Water"
                    tab.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_timeline_24)
                }
            }
        }.attach()

}





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment OverviewFragment.
         */

        @JvmStatic
        fun newInstance(): OverviewFragment {
            Log.d("OverviewFragment", "newInstance")
            return OverviewFragment()
        }
    }
}




