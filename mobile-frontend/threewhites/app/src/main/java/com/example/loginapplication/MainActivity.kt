package com.example.loginapplication

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        val emailInput = findViewById<EditText>(R.id.emailInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val continueButton = findViewById<Button>(R.id.continueButton)
        val registerLink = findViewById<TextView>(R.id.registerLink)

        continueButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val url = URL("http://10.0.2.2:3000/api/auth/login")
                        val conn = url.openConnection() as HttpURLConnection
                        conn.requestMethod = "POST"
                        conn.setRequestProperty("Content-Type", "application/json")
                        conn.setRequestProperty("Accept", "application/json")
                        conn.doOutput = true
                        val jsonInputString = "{\"email\":\"$email\",\"password\":\"$password\"}"
                        conn.outputStream.use { os ->
                            os.write(jsonInputString.toByteArray(Charsets.UTF_8))
                        }
                        val responseCode = conn.responseCode
                        runOnUiThread {
                            if (responseCode == 200) {
                                Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                                // TODO: Navigate to main app screen
                            } else {
                                val errorMsg = conn.errorStream?.bufferedReader()?.readText()
                                val userMessage = try {
                                    val json = org.json.JSONObject(errorMsg ?: "")
                                    json.optString("message", errorMsg)
                                } catch (e: Exception) {
                                    errorMsg
                                }
                                Toast.makeText(this@MainActivity, "Login failed: $userMessage", Toast.LENGTH_LONG).show()
                            }
                        }
                        conn.disconnect()
                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Network error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        registerLink.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
