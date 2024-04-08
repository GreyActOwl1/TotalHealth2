package com.example.totalhealth

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class WaterOverviewFragment : Fragment() {

    private lateinit var chartWaterIntake: LineChart

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_water_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartWaterIntake = view.findViewById(R.id.chart_water_intake)

        // Mock data, replace with actual data fetching logic
        val mockWaterIntakes = listOf(
            WaterIntakeEvent(timestamp = LocalDateTime.now().minusDays(5), amount =  8),
            WaterIntakeEvent(timestamp = LocalDateTime.now().minusDays(3), amount = 24),
            WaterIntakeEvent(timestamp = LocalDateTime.now().minusDays(2), amount = 32),
            WaterIntakeEvent(timestamp = LocalDateTime.now().minusDays(4), amount = 16),
            WaterIntakeEvent(timestamp = LocalDateTime.now().minusDays(1), amount = 40),
            WaterIntakeEvent(timestamp = LocalDateTime.now(), amount = 48)
            // Add more records
        )
        showLineGraph(mockWaterIntakes)
    }

    private fun showLineGraph(waterIntakes: List<WaterIntakeEvent>) {
        val entries = ArrayList<Entry>()

        waterIntakes.forEachIndexed { index, record ->
            // Convert LocalDateTime to a float or long value for the graph. Here we're just using the index.
            entries.add(Entry(index.toFloat(), record.amount.toFloat()))
        }

        val dataSet = LineDataSet(entries, "Water Intake").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
        }

        chartWaterIntake.data = LineData(dataSet)

        // Formatting the X-axis to display the LocalDateTime as a readable date.
        chartWaterIntake.xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd")

            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return dateFormatter.format(waterIntakes.getOrNull(index)?.timestamp ?: LocalDateTime.now())
            }
        }
        chartWaterIntake.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartWaterIntake.xAxis.setDrawGridLines(false)
        chartWaterIntake.xAxis.granularity = 1f // Show only one entry per day

        chartWaterIntake.invalidate() // Refresh chart
    }
}
