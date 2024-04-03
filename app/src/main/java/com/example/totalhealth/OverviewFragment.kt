package com.example.totalhealth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class OverviewFragment : Fragment() {
    //TODO: Refactor textviews
    private lateinit var calories_min_max_textview: TextView
    private lateinit var average_calories_textview: TextView
    private lateinit var average_water_intake_textview: TextView
    private lateinit var water_intake_min_max_textview: TextView
    private lateinit var daily_average_water_intake_textview: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: Refactor to use view binding
        return inflater.inflate(
            R.layout.fragment_overview, container, false
        ).also { view ->
            calories_min_max_textview = view.findViewById<TextView>(
                R.id
                    .calories_min_max_textview
            )
            average_calories_textview = view.findViewById<TextView>(
                R.id
                    .average_calories_textview
            )
            average_water_intake_textview = view.findViewById<TextView>(
                R.id
                    .average_water_intake_textview
            )
            water_intake_min_max_textview = view.findViewById<TextView>(
                R.id
                    .water_intake_min_max_textview
            )
            daily_average_water_intake_textview = view.findViewById<TextView>(
                R.id.daily_average_water_intake_textview
            )

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAndUpdateStats()
    }

    private fun fetchAndUpdateStats() {
        val db by lazy {
            (requireActivity().application as HealthApplication).db

        }
        var minCalories: Int
        var maxCalories: Int
        var avgCalories: Double
        var minWaterIntake: Int
        var maxWaterIntake: Int
        var avgWaterIntake: Double
        var dailyAvgWaterIntake: Int


        lifecycleScope.launch(IO) {
            // Fetch  and update calories stats
            // Fetch and update water intake stats
            Log.d("OverviewFragment", "fetchAndDisplayStats")
           //TODO: NOT YET IMPLEMENTED



        }
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




