package com.example.totalhealth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var foodItemsRecyclerView: RecyclerView
    private lateinit var totalCaloriesTextView: TextView
    private lateinit var totalWaterTextView: TextView
    private val foodItems = mutableListOf<FoodItemEntity>()
    private var totalWaterIntake: Int = 0
    private var totalCalories: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        updateDisplays()
        addButtonListeners()
    }

    private fun setupUI() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //add action bar
        setSupportActionBar(findViewById(R.id.toolbar))

        totalCaloriesTextView = findViewById(R.id.total_caloric_intake_text)
        totalWaterTextView = findViewById(R.id.total_water_intake_text)
        totalCaloriesTextView.text = getString(R.string.calories, totalCalories)
        totalWaterTextView.text = getString(R.string.water, totalWaterIntake)

        foodItemsRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_food_items).apply {
            adapter = FoodItemsAdapter(foodItems)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }


    private fun updateDisplays() {
        val db = (application as HealthApplication).db
        lifecycleScope.launch {
            launch {
                db.foodItemDao().getAll().collect { items ->
                    foodItems.clear()
                    foodItems.addAll(items)
                    foodItemsRecyclerView.adapter?.notifyItemRangeChanged(0, items.size)
                }
            }
            launch {   db.foodItemDao().getTotalCalories().collect {
                totalCalories = it ?: 0
                totalCaloriesTextView.text = getString(R.string.calories, totalCalories)
            }}
            launch {  db.waterIntakeEventDao().getTotalIntake().collect {
                totalWaterIntake = it ?: 0
                totalWaterTextView.text = getString(R.string.water, totalWaterIntake)
            } }
        }
    }





    private fun addButtonListeners() {
        findViewById<Button>(R.id.button_add_food).setOnClickListener {
            startActivity(Intent(this, AddFoodItemActivity::class.java))
        }
        findViewById<Button>(R.id.button_add_water).setOnClickListener {
            startActivity(Intent(this, AddWaterEntryActivity::class.java))
        }
    }
}
