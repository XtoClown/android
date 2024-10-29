package com.example.lr8

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class Authorization : AppCompatActivity() {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authorization)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val registrationButton = findViewById<Button>(R.id.authorizationRegistrationButton)
        registrationButton.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.authorizationLoginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("Firebase", "Config params updated: $updated")
                    theme()
                    authorization()
                    buttonVisible()
                } else {
                    Log.w("Firebase", "Config params update error")
                }
            }
    }

    private fun theme(){
        try{
            val main = findViewById<View>(R.id.main)
            main.setBackgroundColor(Color.parseColor(remoteConfig.getString("theme")))

            val authorizationTextView = findViewById<TextView>(R.id.authorizationText)
            authorizationTextView.setTextColor(Color.parseColor(remoteConfig.getString("color")))

        }
        catch(ex: Exception){
            Log.w("Firebase", "Error", ex)
        }
    }

    private fun authorization(){
        try{
            val authorizationTextView = findViewById<TextView>(R.id.authorizationText)
            authorizationTextView.text = remoteConfig.getString("authorizationText")
        }
        catch(ex: Exception){
            Log.w("Firebase", "Error", ex)
        }
    }

    private fun buttonVisible(){
        try{
            val registrationButton = findViewById<Button>(R.id.authorizationRegistrationButton)
            registrationButton.isVisible = remoteConfig.getBoolean("registrationIsAvailable")

            val loginButton = findViewById<Button>(R.id.authorizationLoginButton)
            loginButton.isVisible = remoteConfig.getBoolean("loginIsAvailable")
        }
        catch(ex: Exception){
            Log.w("Firebase", "Error", ex)
        }
    }
}