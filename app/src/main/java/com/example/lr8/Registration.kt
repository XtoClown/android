package com.example.lr8

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val remoteConfig: FirebaseRemoteConfig = com.google.firebase.Firebase.remoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val registrationButton = findViewById<Button>(R.id.registrationButton)
        registrationButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.registrationEmailText).text.toString()
            val password = findViewById<EditText>(R.id.registrationPasswordText).text.toString()
            createAccount(email, password)
        }

        val backButton = findViewById<Button>(R.id.registrationBack)
        backButton.setOnClickListener {
            val intent = Intent(this, Authorization::class.java)
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
                } else {
                    Log.w("Firebase", "Config params update error")
                }
            }
    }

    private fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful){
                    Log.d("Firebase", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                }
                else{
                    Log.w("Firebase", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Log.d("Firebase", "Error")
        }
    }

    private fun theme(){
        try{
            val main = findViewById<View>(R.id.main)
            main.setBackgroundColor(Color.parseColor(remoteConfig.getString("theme")))

            val loginTextView = findViewById<TextView>(R.id.registrationLabel)
            loginTextView.setTextColor(Color.parseColor(remoteConfig.getString("color")))

            val emailLabelView = findViewById<TextView>(R.id.registrationEmailLabel)
            emailLabelView.setTextColor(Color.parseColor(remoteConfig.getString("color")))

            val nameLabelView = findViewById<TextView>(R.id.registrationPasswordLabel)
            nameLabelView.setTextColor(Color.parseColor(remoteConfig.getString("color")))

            val emailInputView = findViewById<TextView>(R.id.registrationEmailText)
            emailInputView.setTextColor(Color.parseColor(remoteConfig.getString("color")))
            emailInputView.setHintTextColor(Color.parseColor(remoteConfig.getString("hint")))
            emailInputView.hint = remoteConfig.getString("emailHintText")

            val nameInputView = findViewById<TextView>(R.id.registrationPasswordText)
            nameInputView.setTextColor(Color.parseColor(remoteConfig.getString("color")))
            nameInputView.setHintTextColor(Color.parseColor(remoteConfig.getString("hint")))
            nameInputView.hint = remoteConfig.getString("passwordHintText")
        }
        catch(ex: Exception){
            Log.w("Firebase", "Error", ex)
        }
    }
}