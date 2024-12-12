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
import com.example.lr_seven.entity.Processor

class ProcessorAdapter(context: Context?, resource: Int, private val processors: List<Processor>, private val callback: IUpdateRemove)
    : ArrayAdapter<Processor?>(context!!, resource, processors) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val layout = resource

    interface IUpdateRemove{
        fun updateProcessor(processorId: Int, processorName: String, processorSocket: String)
        fun removeProcessor(processorId: Int, processorName: String, processorSocket: String)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(this.layout, parent, false)

        val processorIdView = view.findViewById<TextView>(R.id.processorId)
        val processorNameView = view.findViewById<EditText>(R.id.processorName)
        val processorSocketView = view.findViewById<EditText>(R.id.processorSocket)

        val processor: Processor = processors[position]

        processorIdView.text = processor.pid.toString()
        processorNameView.setText(processor.processorName)
        processorSocketView.setText(processor.processorSocketName)

        val updateButton = view.findViewById<Button>(R.id.updateProcessorDate)
        updateButton.setOnClickListener {
            val id = processorIdView.text.toString().toInt()
            val name = processorNameView.text.toString()
            val socket = processorSocketView.text.toString()
            callback.updateProcessor(id, name, socket)
        }

        val removeButton = view.findViewById<Button>(R.id.removeProcessorDate)
        removeButton.setOnClickListener {
            val id = processorIdView.text.toString().toInt()
            val name = processorNameView.text.toString()
            val socket = processorSocketView.text.toString()
            callback.removeProcessor(id, name, socket)
        }

        return view
    }
}