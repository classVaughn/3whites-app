package com.wifighters.threewhites

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wifighters.threewhites.utils.FirebaseAuthHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuthHelper: FirebaseAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registerpage)

        firebaseAuthHelper = FirebaseAuthHelper()

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginLink = findViewById<TextView>(R.id.loginLink)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val result = firebaseAuthHelper.registerUser(email, password)
                        runOnUiThread {
                            result.fold(
                                onSuccess = {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Account created successfully! Now let's set up your preferences.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    // Add a small delay to ensure the Toast is visible
                                    CoroutineScope(Dispatchers.Main).launch {
                                        delay(1500) // 1.5 second delay for longer message
                                        val intent = Intent(this@RegisterActivity, ExperienceActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                },
                                onFailure = { exception ->
                                    Toast.makeText(this@RegisterActivity, "Registration failed: ${exception.message}", Toast.LENGTH_LONG).show()
                                }
                            )
                        }
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}