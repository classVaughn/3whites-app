package com.wifighters.threewhites

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wifighters.threewhites.RecommendedProgramsActivity

class UnitsSelectionActivity : AppCompatActivity() {
    private var selectedUnit: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.units_selection_activity) // use your actual layout file name here

        val buttonKg = findViewById<Button>(R.id.buttonKg)
        val buttonLbs = findViewById<Button>(R.id.buttonLbs)
        val buttonContinue = findViewById<Button>(R.id.buttonContinue)

        buttonKg.setOnClickListener {
            selectedUnit = "Kg"
            Toast.makeText(this, "Kg button clicked", Toast.LENGTH_SHORT).show()
        }

        buttonLbs.setOnClickListener {
            selectedUnit = "Lbs"
            Toast.makeText(this, "Lbs button clicked", Toast.LENGTH_SHORT).show()
        }

        buttonContinue.setOnClickListener {
            if (selectedUnit == null) {
                Toast.makeText(this, "Please select a unit before continuing.", Toast.LENGTH_SHORT).show()
            } else {
                navigateToRecommendedPrograms()
            }
        }
    }

    private fun navigateToRecommendedPrograms() {
        val intent = Intent(this, RecommendedProgramsActivity::class.java)
        startActivity(intent)
        finish()
    }
}
