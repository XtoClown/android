package com.example.lr8

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class ProcessorAdapter(context: Context?, resource: Int, private val processors: List<Processor>, private val callback: IUpdateRemove):
    ArrayAdapter<Processor?>(context!!, resource, processors) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val layout = resource

    interface IUpdateRemove{
        fun update(processorId: Int, processorName: String, processorSocket: String, processorCompany: String)
        fun remove(processorId: Int)
    }

    private var color: String = "#000000"
    private var hint: String = "#737373"

    fun theme(color: String, hint: String){
        this.color = color
        this.hint = hint
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(this.layout, parent, false)

        val processorIdView = view.findViewById<TextView>(R.id.processorId)
        val processorNameView = view.findViewById<EditText>(R.id.processorName)
        val processorSocketView = view.findViewById<EditText>(R.id.processorSocket)
        val processorCompanyView = view.findViewById<EditText>(R.id.processorCompany)

        val processor: Processor = processors[position]

        processorIdView.text = processor.id.toString()
        processorNameView.setText(processor.processorName)
        processorSocketView.setText(processor.processorSocket)
        processorCompanyView.setText(processor.processorCompany)

        val updateButton = view.findViewById<Button>(R.id.updateDate)
        updateButton.setOnClickListener {
            val id = processorIdView.text.toString().toInt()
            val name = processorNameView.text.toString()
            val socket = processorSocketView.text.toString()
            val company = processorCompanyView.text.toString()
            callback.update(id, name, socket, company)
        }

        val removeButton = view.findViewById<Button>(R.id.removeDate)
        removeButton.setOnClickListener {
            val id = processorIdView.text.toString().toInt()
            callback.remove(id)
        }

        processorIdView.setTextColor(Color.parseColor(color))
        processorNameView.setTextColor(Color.parseColor(color))
        processorSocketView.setTextColor(Color.parseColor(color))
        processorCompanyView.setTextColor(Color.parseColor(color))

        processorNameView.setHintTextColor(Color.parseColor(hint))
        processorSocketView.setHintTextColor(Color.parseColor(hint))
        processorCompanyView.setHintTextColor(Color.parseColor(hint))

        return view
    }
}