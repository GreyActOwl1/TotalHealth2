package com.example.totalhealth

import android.content.Intent
import android.os.Bundle
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
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var foodItemsRecyclerView: RecyclerView
    private lateinit var totalCaloriesTextView: TextView
    private lateinit var totalWaterTextView: TextView
    private lateinit var foodItems: MutableList<FoodItemEntity>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        totalCaloriesTextView = findViewById(R.id.total_caloric_intake_text)
        totalWaterTextView = findViewById(R.id.total_water_intake_text)
        totalCaloriesTextView.text = getString(R.string.calories, FoodItem.getTotalCalories())
        totalWaterTextView.text = getString(R.string.water, totalWaterIntake)

        lifecycleScope.launch {
            (application as HealthApplication).db.foodItemDao().getAll().collect { items ->
                foodItems = items.toMutableList()
            }
        }


        foodItemsRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_food_items).apply {
            adapter = FoodItemsAdapter(foodItems)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        findViewById<Button>(R.id.button_add_food).setOnClickListener {
            startActivity(Intent(this, AddFoodItemActivity::class.java))
        }

        findViewById<Button>(R.id.button_add_water).setOnClickListener {
//            Toast.makeText(this, "Add water entry functionality", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AddWaterEntryActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        foodItemsRecyclerView.adapter?.notifyItemRangeChanged(0, foodItems.size)
        totalCaloriesTextView.text = getString(R.string.calories, FoodItem.getTotalCalories())
        totalWaterTextView.text = getString(R.string.water, totalWaterIntake)


    }

    companion object {
//TODO: Replace with a database
//        var foodItems: MutableList<FoodItem> = FoodItem.getItems()
        var totalWaterIntake : Int =0
    }
}
