package com.example.lr_seven.custom_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.lr_seven.R
import com.example.lr_seven.entity.Socket

class SocketAdapter(context: Context?, resource: Int, private val processors: List<Socket>, private val callback: IUpdateRemove)
    : ArrayAdapter<Socket?>(context!!, resource, processors) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val layout = resource

    interface IUpdateRemove{
        fun updateSocket(socketName: String, socketCompany: String)
        fun removeSocket(socketName: String, socketCompany: String)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(this.layout, parent, false)

        val socketNameView = view.findViewById<TextView>(R.id.socketName)
        val socketCompanyView = view.findViewById<EditText>(R.id.socketCompany)

        val socket: Socket = processors[position]

        socketNameView.text = socket.socketName.toString()
        socketCompanyView.setText(socket.companyName)

        val updateButton = view.findViewById<Button>(R.id.updateSocketDate)
        updateButton.setOnClickListener {
            val name = socketNameView.text.toString()
            val company = socketCompanyView.text.toString()
            callback.updateSocket(name, company)
        }

        val removeButton = view.findViewById<Button>(R.id.removeSocketDate)
        removeButton.setOnClickListener {
            val name = socketNameView.text.toString()
            val company = socketCompanyView.text.toString()
            callback.removeSocket(name, company)
        }

        return view
    }
}