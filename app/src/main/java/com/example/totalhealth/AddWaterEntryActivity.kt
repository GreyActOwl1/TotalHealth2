package com.example.totalhealth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddWaterEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_water_entry)

        val editTextWaterAmount = findViewById<EditText>(R.id.edit_text_water_amount)
        val buttonSubmitWater = findViewById<Button>(R.id.button_submit_water)

        buttonSubmitWater.setOnClickListener {
            val waterAmountStr = editTextWaterAmount.text.toString()
            if (waterAmountStr.isNotEmpty()) {
                //new water intake event
                val waterIntakeEvent = WaterIntakeEvent( amount = waterAmountStr.toInt(), timestamp = LocalDateTime.now() )
                // Save the water intake event to the database
                val db = (application as HealthApplication).db
                insertWaterIntake(db.waterIntakeEventDao(), waterIntakeEvent)

                finish()
            } else {
                Toast.makeText(this, "Please enter a water amount", Toast.LENGTH_LONG).show()
            }
        }


    }
    private fun insertWaterIntake(dao: WaterIntakeEventDao, waterIntakeEvent: WaterIntakeEvent) {
        // Launching a new coroutine to insert the item asynchronously
        lifecycleScope.launch(Dispatchers.IO) {
            dao.insert(waterIntakeEvent)
        }
    }
}

