    package com.example.lr5

    import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import android.util.Log
    import android.widget.Button
    import android.widget.TextView
    import androidx.fragment.app.FragmentManager
    import androidx.fragment.app.FragmentTransaction

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
            Log.d("=== Life Cycle Methods ===", "onCreate() works...")

            val button: Button = findViewById<Button>(R.id.button)


            var currentFragmentView = "";

            button.setOnClickListener{
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                if(currentFragmentView == "1"){
                    val fragmentTwo: FragmentTwo = FragmentTwo.newInstance("FragmentTwo")
                    ft.replace(R.id.fragment_container, fragmentTwo);
                    currentFragmentView = "2";
                }
                else{
                    val fragmentOne: FragmentOne = FragmentOne.newInstance("FragmentOne")
                    ft.replace(R.id.fragment_container, fragmentOne);
                    currentFragmentView = "1";
                }
                ft.commit()
            }

        }


        override fun onStart() {
            super.onStart()
            Log.d("=== Life Cycle Methods ===", "onStart() works...")
        }

        override fun onResume() {
            super.onResume()
            Log.d("=== Life Cycle Methods ===", "onResume() works...")
        }

        override fun onPause() {
            super.onPause()
            Log.d("=== Life Cycle Methods ===", "onPause() works...")
        }

        override fun onRestart() {
            super.onRestart()
            Log.d("=== Life Cycle Methods ===", "onRestart() works...")
        }

        override fun onStop() {
            super.onStop()
            Log.d("=== Life Cycle Methods ===", "onStop() works...")
        }

        override fun onDestroy() {
            super.onDestroy()
            Log.d("=== Life Cycle Methods ===", "onDestroy() works...")
        }
    }
