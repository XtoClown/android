package com.example.sr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.sr.api.ApiService
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.time.times

class MainActivity : AppCompatActivity() {

    val currencyapi = ApiService()

    var from: String = "USA Dollar"
    var inputFromValue: String = ""

    var to: String = "Ukrainian Hryvnia"
    var toValue: Double? = 0.0
    var inputToValue: String = ""

    private var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dropdownFrom: Spinner = findViewById<Spinner>(R.id.dropdownFrom)
        val adapterForFrom = ArrayAdapter.createFromResource(this, R.array.currency_items, android.R.layout.simple_spinner_item)
        adapterForFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdownFrom.adapter = adapterForFrom
        dropdownFrom.setSelection(0)
        val onFromItemSelectedListener: AdapterView.OnItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                from = item
                useApi("From")
                Log.d("API Currency Value", "${toValue}")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        dropdownFrom.onItemSelectedListener = onFromItemSelectedListener

        val dropdownTo: Spinner = findViewById<Spinner>(R.id.dropdownTo)
        val adapterForTo = ArrayAdapter.createFromResource(this, R.array.currency_items, android.R.layout.simple_spinner_item)
        adapterForTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdownTo.adapter = adapterForTo
        dropdownTo.setSelection(7)
        val onToItemSelectedListener: AdapterView.OnItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position) as String
                to = item
                useApi("To")
                Log.d("API Currency Name", to)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        dropdownTo.onItemSelectedListener = onToItemSelectedListener

        val inputFrom = findViewById<TextInputEditText>(R.id.inputFrom)
        val textWatcherFrom: TextWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputFromValue = p0.toString()
                calculationFrom(inputFromValue)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        inputFrom.addTextChangedListener(textWatcherFrom)

        val inputTo = findViewById<TextInputEditText>(R.id.inputTo)
        val textWatcherTo: TextWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                inputToValue = p0.toString()
                calculationTo(inputToValue)
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        }
        inputTo.addTextChangedListener(textWatcherTo)
    }


    fun useApi(flag: String){

        val fromCode = when (from){
            "USA Dollar" -> "USD"
            "Euro" -> "EUR"
            "Swiss Franc" -> "CHF"
            "British Pound" -> "GBP"
            "Japanese Yen" -> "JPY"
            "Canadian Dollar" -> "CAD"
            "Australian Dollar" -> "AUD"
            "Ukrainian Hryvnia" -> "UAH"
            "Polish Zloty" -> "PLN"
            "Chinese Yuan" -> "CNY"
            "Долар США" -> "USD"
            "Євро" -> "EUR"
            "Швейцарьский Франк" -> "CHF"
            "Британський Фунт" -> "GBP"
            "Японська Єна" -> "JPY"
            "Канадський Долар" -> "CAD"
            "Австралійський Долар" -> "AUD"
            "Українська Гривня" -> "UAH"
            "Польский Злотий" -> "PLN"
            "Китайський Юань" -> "CNY"
            else -> "USD"
        }

        val toCode = when (to){
            "USA Dollar" -> "USD"
            "Euro" -> "EUR"
            "Swiss Franc" -> "CHF"
            "British Pound" -> "GBP"
            "Japanese Yen" -> "JPY"
            "Canadian Dollar" -> "CAD"
            "Australian Dollar" -> "AUD"
            "Ukrainian Hryvnia" -> "UAH"
            "Polish Zloty" -> "PLN"
            "Chinese Yuan" -> "CNY"
            "Долар США" -> "USD"
            "Євро" -> "EUR"
            "Швейцарьский Франк" -> "CHF"
            "Британський Фунт" -> "GBP"
            "Японська Єна" -> "JPY"
            "Канадський Долар" -> "CAD"
            "Австралійський Долар" -> "AUD"
            "Українська Гривня" -> "UAH"
            "Польский Злотий" -> "PLN"
            "Китайський Юань" -> "CNY"
            else -> "USD"
        }

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)
        val job = scopeIO.launch {
            try{
                withContext(Dispatchers.Main){
                    val apiRequest = currencyapi.convert(fromCode, toCode)
                    if(apiRequest != null){
                        toValue = apiRequest.data.get(toCode)?.value
                        if(flag == "From"){
                            calculationFrom(inputFromValue)
                        }
                        else if(flag == "To"){
                            calculationFrom(inputFromValue)
                        }
                        Log.d("toValue", "${toValue}")
                    }
                    else{
                        Log.d("API NULL", "Error, api request returns null")
                    }
                }
            }
            catch (ex: Exception){
                Log.d("API ERROR", "${ex.message}")
            }
        }
    }

    fun calculationFrom(amount: String){

        if(isUpdating)return

        var valueAmount: Double = 0.0
        try{
            valueAmount = amount.toDoubleOrNull() ?: 0.0
            val result = toValue?.times(valueAmount)
            isUpdating = true
            val inputTo = findViewById<TextInputEditText>(R.id.inputTo)
            val res = result?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_UP) }
            inputTo.setText(res.toString())
            isUpdating = false
        }
        catch (ex: Exception){
            Log.d("Parse Exception", "${ex.message}")
        }
    }

    fun calculationTo(amount: String){

        if(isUpdating)return

        var valueAmount: Double = 0.0
        try{
            valueAmount = amount.toDoubleOrNull() ?: 0.0
            val result = toValue?.let { valueAmount.div(it) }
            isUpdating = true
            val inputFrom = findViewById<TextInputEditText>(R.id.inputFrom)
            val res = result?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_UP) }
            inputFrom.setText(res.toString())
            isUpdating = false
        }
        catch (ex: Exception){
            Log.d("Parse Exception", "${ex.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currencyapi.close()
    }
}