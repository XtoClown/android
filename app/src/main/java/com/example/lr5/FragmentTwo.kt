package com.example.lr5

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentTwo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentTwo : Fragment() {
    // TODO: Rename and change types of parameters
    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(ARG_PARAM1)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragment = inflater.inflate(R.layout.fragment_two, container, false)

        val textView: TextView = fragment.findViewById<TextView>(R.id.textView3)
        textView.setText("Welcome to the \"${this.name}\"")

        val button: Button = fragment.findViewById<Button>(R.id.button2)

        button.setOnClickListener {
            val intent = Intent(activity, MainActivity2::class.java)
            startActivity(intent)
        }

        return fragment
    }

    override fun onResume() {
        super.onResume()
        val currentFragment = activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container);
        if(currentFragment is FragmentOne || currentFragment is FragmentTwo){
            val textView: TextView? = activity?.findViewById(R.id.textView)
            textView?.setText("Current Fragment: ${currentFragment?.javaClass?.simpleName}");
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param name Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentTwo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(name: String) =
            FragmentTwo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, name)
                }
            }
    }
}