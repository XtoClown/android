package com.example.lr_four

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textView: TextView = findViewById<TextView>(R.id.text1)
        registerForContextMenu(textView)

        val textView1: TextView = findViewById(R.id.textView1)

        textView1.setOnClickListener{
            val dialog = CustomDialogFragment()
            dialog.show(supportFragmentManager, "custom")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.color_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val main = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        if(item.title == "Black"){
            main.setBackgroundColor(getColor(R.color.black))
        }
        if(item.title == "White"){
            main.setBackgroundColor(getColor(R.color.white))
        }
        if(item.title == "Khaki"){
            main.setBackgroundColor(getColor(R.color.khaki))
        }
        if(item.title == "Indigo"){
            main.setBackgroundColor(getColor(R.color.indigo))
        }
        if(item.title == "Crimson"){
            main.setBackgroundColor(getColor(R.color.crimson))
        }
        if(item.title == "Turquoise"){
            main.setBackgroundColor(getColor(R.color.turquoise))
        }
        if(item.title == "Brown"){
            main.setBackgroundColor(getColor(R.color.brown))
        }
        if(item.title == "Ocean"){
            main.setBackgroundColor(getColor(R.color.ocean))
        }
        if(item.title == "Gray"){
            main.setBackgroundColor(getColor(R.color.gray))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Black")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "White")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Khaki")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Indigo")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Crimson")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Turquoise")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Brown")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Ocean")
        menu?.add(Menu.NONE, Menu.NONE, Menu.NONE, "Gray")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val textView: TextView = findViewById<TextView>(R.id.text1)
        when(item.title){
            "Black" -> textView.setBackgroundColor(getColor(R.color.black))
            "White" -> textView.setBackgroundColor(getColor(R.color.white))
            "Khaki" -> textView.setBackgroundColor(getColor(R.color.khaki))
            "Indigo" -> textView.setBackgroundColor(getColor(R.color.indigo))
            "Crimson" -> textView.setBackgroundColor(getColor(R.color.crimson))
            "Turquoise" -> textView.setBackgroundColor(getColor(R.color.turquoise))
            "Brown" -> textView.setBackgroundColor(getColor(R.color.brown))
            "Ocean" -> textView.setBackgroundColor(getColor(R.color.ocean))
            "Gray" -> textView.setBackgroundColor(getColor(R.color.gray))
            else -> println("gg")
        }

        return super.onContextItemSelected(item)
    }

}

class CustomDialogFragment: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity)


        builder.setPositiveButton("ok" ){ _, _ ->
            val intent = Intent(activity, MainActivity2::class.java)
            startActivity(intent)
        }

        return builder.setTitle("Test").create()
    }

}