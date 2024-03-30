package com.example.totalhealth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddWaterEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_water_entry)

        val editTextWaterAmount = findViewById<EditText>(R.id.edit_text_water_amount)
        val buttonSubmitWater = findViewById<Button>(R.id.button_submit_water)

        buttonSubmitWater.setOnClickListener {
            val waterAmountStr = editTextWaterAmount.text.toString()
            if (waterAmountStr.isNotEmpty()) {
                MainActivity.totalWaterIntake += waterAmountStr.toInt()
                finish()
            } else {
                Toast.makeText(this, "Please enter a water amount", Toast.LENGTH_LONG).show()
            }
        }
    }
}

