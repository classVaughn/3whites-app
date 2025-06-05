package com.wifighters.threewhites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wifighters.threewhites.databinding.TrainingDaysPageBinding

class TrainingDaysActivity : AppCompatActivity() {

    private lateinit var binding: TrainingDaysPageBinding
    private var selectedDays: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = TrainingDaysPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            selectedDays = 3
            showToast("Selected: 3 Days")
        }

        binding.button4.setOnClickListener {
            selectedDays = 4
            showToast("Selected: 4 Days")
        }

        binding.button5.setOnClickListener {
            selectedDays = 5
            showToast("Selected: 5 Days")
        }

        binding.button6.setOnClickListener {
            selectedDays = 6
            showToast("Selected: 6 Days")
        }

        binding.buttonContinue.setOnClickListener {
            if (selectedDays == null) {
                showToast("Please select the number of training days before continuing.")
            } else {
                navigateToUnitsSelection()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToUnitsSelection() {
        val intent = Intent(this, UnitsSelectionActivity::class.java)
        startActivity(intent)
        finish()
    }
}
