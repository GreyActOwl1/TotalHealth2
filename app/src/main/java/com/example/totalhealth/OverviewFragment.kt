package com.example.totalhealth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

    private lateinit var caloriesMinMaxTextview: TextView
    private lateinit var averageCaloriesTextview: TextView
    private lateinit var averageWaterIntakeTextview: TextView
    private lateinit var waterIntakeMinMaxTextview: TextView
    private lateinit var dailyAverageWaterIntakeTextview: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: Refactor to use view binding
        return inflater.inflate(
            R.layout.fragment_overview, container, false
        ).also { view ->
            caloriesMinMaxTextview = view.findViewById<TextView>(
                R.id
                    .calories_min_max_textview
            )
            averageCaloriesTextview = view.findViewById<TextView>(
                R.id
                    .average_calories_textview
            )
            averageWaterIntakeTextview = view.findViewById<TextView>(
                R.id
                    .average_water_intake_textview
            )
            waterIntakeMinMaxTextview = view.findViewById<TextView>(
                R.id
                    .water_intake_min_max_textview
            )
            dailyAverageWaterIntakeTextview = view.findViewById<TextView>(
                R.id.daily_average_water_intake_textview
            )

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchAndUpdateStats()
    }


    private fun fetchAndUpdateStats() {
        val db = (requireActivity().application as HealthApplication).db

        // get water stats
        lifecycleScope.launch {
            val minWaterIntake = async {
                db.waterIntakeEventDao()
                    .getMinWaterIntake().firstOrNull() ?: 0
            }.await()
            val maxWaterIntake = async {
                db.waterIntakeEventDao()
                    .getMaxWaterIntake().firstOrNull() ?: 0
            }.await()
            val avgWaterIntake = async {
                db.waterIntakeEventDao()
                    .getTotalAverageWaterIntake().firstOrNull() ?: 0
            }.await()
            val dailyAvgWaterIntake = async {
                db.waterIntakeEventDao()
                    .getDailyAverageWaterIntake().firstOrNull() ?: 0
            }.await()

            //get food stats
//            val minCalories = async {
//                db.foodItemDao()
//                    .getMinCalories().firstOrNull() ?: 0
//            }.await()


            //update display on main thread
            withContext(Dispatchers.Main) {
                waterIntakeMinMaxTextview.text = getString(
                    R.string
                        .water_intake_min_max, minWaterIntake, maxWaterIntake
                )
                averageWaterIntakeTextview.text = getString(
                    R.string
                        .average_total_water_intake, avgWaterIntake
                )
                dailyAverageWaterIntakeTextview.text = getString(
                    R.string
                        .average_daily_water_intake, dailyAvgWaterIntake
                )

            }
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




