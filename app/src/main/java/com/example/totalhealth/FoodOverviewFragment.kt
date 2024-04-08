package com.example.totalhealth

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

/**
 * A simple [Fragment] subclass.
 * Use the [FoodOverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodOverviewFragment : Fragment() {

    private lateinit var chartCalorieIntake: BarChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartCalorieIntake = view.findViewById(R.id.chart_calorie_intake)

        // Mock data, replace with actual data fetching logic
        val mockFoodItems = listOf(
            FoodItemEntity(1, "Apple", 95),
            FoodItemEntity(2, "Banana", 105),
            // Add more items
        )
        showBarChart(mockFoodItems)
    }

    private fun showBarChart(foodItems: List<FoodItemEntity>) {
        val entries = ArrayList<BarEntry>()

        foodItems.forEachIndexed { index, foodItem ->
            entries.add(BarEntry(index.toFloat(), foodItem.calories.toFloat()))
        }

        val dataSet = BarDataSet(entries, "Calorie Intake").apply {
            // Customize dataset appearance
            valueTextColor = Color.BLACK
            color = ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.material_blue_grey_900)
        }

        val barData = BarData(dataSet)
        chartCalorieIntake.data = barData
        chartCalorieIntake.description.text = "Calories per Food Item"

        // Setting the x-axis labels to food names
        chartCalorieIntake.xAxis.valueFormatter = IndexAxisValueFormatter(foodItems.map { it.name })
        chartCalorieIntake.xAxis.granularity = 1f
        chartCalorieIntake.xAxis.setCenterAxisLabels(false)
        chartCalorieIntake.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartCalorieIntake.xAxis.setDrawGridLines(false)

        // Customize chart appearance and animate
        chartCalorieIntake.setFitBars(true)
        chartCalorieIntake.animateY(500)
        chartCalorieIntake.invalidate() // Refresh chart
    }

    companion object {
        @JvmStatic
        fun newInstance() = FoodOverviewFragment()
    }
}


