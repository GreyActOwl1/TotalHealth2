package com.example.totalhealth

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var totalCaloriesTextView: TextView
    private lateinit var totalWaterTextView: TextView
    private var totalWaterIntake: Int = 0
    private var totalCalories: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        updateTotalsDisplays()
        setNavigationBar()

        // TODO: Add graphs (Graph Fragment)
        // TODO: Refactor to view binding
        // TODO: Add Nav Graph
        // TODO: Refactor addFoodItem to fragment
        // TODO: Refactor addWaterEntry to fragment
        // TODO:(OPTIONAL) Highlight entry on submit & return to main activity
        // TODO: Add Edit and Delete on long press for food items and associated activity

    }

    private val notificationHelper by lazy { NotificationHelper(this) }
    private val handler = android.os.Handler()

    override fun onStop() {
        super.onStop()
        handler.postDelayed({
            notificationHelper.showNotification(
                this, "Daily Reminder", "Remember to log your food intake!"
            )
        }, 1000)
    }

    private fun setNavigationBar() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_log -> {
                    replaceFragment(FoodListFragment())
                    true
                }

                R.id.navigation_dashboard -> {
                    replaceFragment(OverviewFragment())
                    true
                }

                else -> false
            }
        }
        bottomNavigationView.selectedItemId = R.id.navigation_log
    }

    private fun setupUI() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {
        view, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
        // add action bar
        setSupportActionBar(findViewById(R.id.toolbar))

        totalCaloriesTextView = findViewById(R.id.total_caloric_intake_text)
        totalWaterTextView = findViewById(R.id.total_water_intake_text)
        totalCaloriesTextView.text = getString(R.string.calories, totalCalories)
        totalWaterTextView.text = getString(R.string.water, totalWaterIntake)

        replaceFragment(FoodListFragment())
    }

    private fun updateTotalsDisplays() {
        val db = (application as HealthApplication).db
        lifecycleScope.launch {
            launch {
                db.foodItemDao().getTotalCalories().collect {
                    totalCalories = it ?: 0
                    totalCaloriesTextView.text =
                        getString(R.string.calories, totalCalories)
                }
            }
            launch {
                // TODO: Refactor to use daily total
                db.waterIntakeEventDao().getTotalIntake().collect {
                    totalWaterIntake = it ?: 0
                    totalWaterTextView.text =
                        getString(R.string.water, totalWaterIntake)
                }
            }
        }
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.food_list_frame, fragment)
        fragmentTransaction.commit()
    }


}
