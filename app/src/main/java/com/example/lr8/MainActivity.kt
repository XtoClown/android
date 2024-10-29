package com.example.lr8

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class MainActivity : AppCompatActivity(), ProcessorAdapter.IUpdateRemove {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private var processors: ArrayList<Processor> = ArrayList<Processor>()
    private var dataBaseDataView: ListView? = null
    private var processorAdapter: ProcessorAdapter? = null
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val userName = findViewById<TextView>(R.id.userName)
        val user = auth.currentUser
        user?.let{
            val email = it.email
            userName.text = email
        }

        dataBaseDataView = findViewById(R.id.dbData)

        processorAdapter = ProcessorAdapter(this, R.layout.list_item, processors, this)
        dataBaseDataView?.adapter = processorAdapter
        readAll()

        val buttonAddRow = findViewById<Button>(R.id.addNewRow)
        buttonAddRow.setOnClickListener {
            val idView = findViewById<EditText>(R.id.processorIdInput)
            val nameView = findViewById<EditText>(R.id.processorNameInput)
            val socketView = findViewById<EditText>(R.id.processorSocketInput)
            val companyView = findViewById<EditText>(R.id.processorCompanyInput)

            val id = idView.text.toString().toInt()
            val name = nameView.text.toString()
            val socket = socketView.text.toString()
            val company = companyView.text.toString()

            try{
                insert(Processor(id, name, socket, company))
                idView.setText("")
                nameView.setText("")
                socketView.setText("")
                companyView.setText("")
            }
            catch (ex: Exception){
                Log.w("Firebase", "Insert Error", ex)
            }
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
                    crashButtonSetText()
                } else {
                    Log.w("Firebase", "Config params update error")
                }
            }

        val crashButton = findViewById<Button>(R.id.crashButton)
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash")
        }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, Authorization::class.java)
            startActivity(intent)
        }

        val logoutButton = findViewById<Button>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Authorization::class.java)
            startActivity(intent)
        }

    }

    private fun readAll(){
        db.collection("processors")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val processor = document.toObject<Processor>()
                    processors.add(processor)
                    processorAdapter?.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Firebase", "Error getting documents", exception)
            }
    }

    private fun insert(processor: Processor){
        db.collection("processors").document("${processor.id}")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if(!documentSnapshot.exists()){
                    db.collection("processors").document("${processor.id}")
                        .set(processor)
                        .addOnSuccessListener { documentReference ->
                            processorAdapter?.notifyDataSetChanged()
                            processors.clear()
                            readAll()
                            Log.d("Firebase", "DocumentSnapshot added with ID: ${documentReference}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firebase", "Error adding document", e)
                        }
                }
                else{
                    Log.w("Firebase", "Document already exist")
                }
            }
    }

    override fun update(processorId: Int, processorName: String, processorSocket: String, processorCompany: String) {
        db.collection("processors").document("${processorId}")
            .update("processorName", processorName, "processorSocket", processorSocket, "processorCompany", processorCompany)
            .addOnSuccessListener {
                processorAdapter?.notifyDataSetChanged()
                processors.clear()
                readAll()
                Log.d("Firebase", "DocumentSnapshot updated!")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Error updating document", e)
            }
    }

    override fun remove(processorId: Int) {
        db.collection("processors").document("${processorId}")
            .delete()
            .addOnSuccessListener {
                processorAdapter?.notifyDataSetChanged()
                processors.clear()
                readAll()
                Log.d("Firebase", "Document successfully deleted!")
            }
            .addOnFailureListener { e -> Log.w("Firebase", "Error document deleting", e) }
    }

    private fun theme(){
        try{
            val main = findViewById<View>(R.id.main)
            val userNameView = findViewById<TextView>(R.id.userName)
            val idView = findViewById<EditText>(R.id.processorIdInput)
            val nameView = findViewById<EditText>(R.id.processorNameInput)
            val socketView = findViewById<EditText>(R.id.processorSocketInput)
            val companyView = findViewById<EditText>(R.id.processorCompanyInput)

            main.setBackgroundColor(Color.parseColor(remoteConfig.getString("theme")))

            userNameView.setTextColor(Color.parseColor(remoteConfig.getString("color")))
            idView.setTextColor(Color.parseColor(remoteConfig.getString("color")))
            nameView.setTextColor(Color.parseColor(remoteConfig.getString("color")))
            socketView.setTextColor(Color.parseColor(remoteConfig.getString("color")))
            companyView.setTextColor(Color.parseColor(remoteConfig.getString("color")))

            idView.setHintTextColor(Color.parseColor(remoteConfig.getString("hint")))
            nameView.setHintTextColor(Color.parseColor(remoteConfig.getString("hint")))
            socketView.setHintTextColor(Color.parseColor(remoteConfig.getString("hint")))
            companyView.setHintTextColor(Color.parseColor(remoteConfig.getString("hint")))

            processorAdapter?.theme(remoteConfig.getString("color"), remoteConfig.getString("hint"))
        }
        catch(ex: Exception){
            Log.w("Firebase", "Error", ex)
        }
    }

    private fun crashButtonSetText(){
        try{
            val crashButton = findViewById<Button>(R.id.crashButton)
            crashButton.text = "Laboratory number \"${remoteConfig.getLong("laboratory")}\" Crash"
        }
        catch(ex: Exception){
            Log.w("Firebase", "Error", ex)
        }
    }
}