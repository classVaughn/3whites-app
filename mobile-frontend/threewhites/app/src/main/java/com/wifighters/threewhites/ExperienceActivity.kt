package com.wifighters.threewhites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wifighters.threewhites.databinding.ExperiencePageBinding

class ExperienceActivity : AppCompatActivity() {

    private lateinit var binding: ExperiencePageBinding
    private var selectedExperience: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ExperiencePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBeginner.setOnClickListener {
            selectedExperience = "Beginner"
            showToast("Selected: Beginner")
        }

        binding.buttonNovice.setOnClickListener {
            selectedExperience = "Novice"
            showToast("Selected: Novice")
        }

        binding.buttonIntermediate.setOnClickListener {
            selectedExperience = "Intermediate"
            showToast("Selected: Intermediate")
        }

        binding.buttonContinue.setOnClickListener {
            if (selectedExperience == null) {
                showToast("Please select an experience level before continuing.")
            } else {
                navigateToTrainingDays()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToTrainingDays() {
        val intent = Intent(this, TrainingDaysActivity::class.java)
        startActivity(intent)
        finish()
    }
}
