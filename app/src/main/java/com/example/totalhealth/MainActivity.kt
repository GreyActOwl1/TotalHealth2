package com.example.totalhealth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val foodEntriesRecyclerView = findViewById<RecyclerView>(R.id.recycler_view_food_items)
        val foodEntries = FoodEntry.getItems()
        foodEntriesRecyclerView.apply {
            adapter = FoodEntriesAdapter(foodEntries)
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        val addFoodEntryButton = findViewById<Button>(R.id.button_add_food)

        addFoodEntryButton.setOnClickListener {
            Toast.makeText(this, "Add food entry functionality", Toast.LENGTH_SHORT).show()
//            TODO("Add food entry functionality")
//            val intent = Intent(this, AddFoodEntryActivity::class.java)
//            startActivity(intent)
        }

        val addWaterEntryButton = findViewById<Button>(R.id.button_add_water)

        addWaterEntryButton.setOnClickListener {
            Toast.makeText(this, "Add water entry functionality", Toast.LENGTH_SHORT).show()
//            TODO("Add water entry functionality")
//            val intent = Intent(this, AddWaterEntryActivity::class.java)
//            startActivity(intent)
        }




    }
}