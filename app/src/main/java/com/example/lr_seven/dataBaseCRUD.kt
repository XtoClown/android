package com.example.lr_seven

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lr_seven.custom_adapters.ProcessorAdapter
import com.example.lr_seven.custom_adapters.SocketAdapter
import com.example.lr_seven.dao.ProcessorDao
import com.example.lr_seven.dao.SocketDao
import com.example.lr_seven.entity.Processor
import com.example.lr_seven.entity.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class dataBaseCRUD : AppCompatActivity(), ProcessorAdapter.IUpdateRemove, SocketAdapter.IUpdateRemove {

    private var processors: ArrayList<Processor> = ArrayList<Processor>()
    private var processorAdapter: ProcessorAdapter? = null
    private var processorTableView: ListView? = null

    private var sockets: ArrayList<Socket> = ArrayList<Socket>()
    private var socketAdapter: SocketAdapter? = null
    private var socketTableView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_data_base_crud)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = App.instance?.database

        processorTableView = findViewById(R.id.processorsTableView)
        processorAdapter = ProcessorAdapter(this, R.layout.processor_list_item, processors, this)
        processorTableView?.adapter = processorAdapter

        socketTableView = findViewById(R.id.socketsTableView)
        socketAdapter = SocketAdapter(this, R.layout.socket_list_item, sockets, this)
        socketTableView?.adapter = socketAdapter


        val processorDao: ProcessorDao? = db?.processorDao()
        val socketDao: SocketDao? = db?.socketDao()

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)

        val job = scopeIO.launch {

            val processorsFromDB = processorDao?.getAll() ?: emptyList()
            val socketsFoDB = socketDao?.getAll() ?: emptyList()

            withContext(Dispatchers.Main) {
                processors.clear()
                processors.addAll(processorsFromDB)
                processorAdapter?.notifyDataSetChanged()

                sockets.clear()
                sockets.addAll(socketsFoDB)
                socketAdapter?.notifyDataSetChanged()
            }
        }

        val enteredProcessorName = findViewById<EditText>(R.id.enterProcessorName)
        val enteredProcessorSocket = findViewById<EditText>(R.id.enterProcessorSocket)

        val addNewProcessorButton = findViewById<Button>(R.id.addNewDBRowProcessor)
        addNewProcessorButton.setOnClickListener{
            val processorName = enteredProcessorName.text.toString()
            val processorSocket = enteredProcessorSocket.text.toString()

            val processor = Processor(pid = 0, processorName = processorName, processorSocketName = processorSocket)

            val job = scopeIO.launch {
                processorDao?.insert(processor)
                withContext(Dispatchers.Main) {
                    processors.add(processor)
                    processorAdapter?.notifyDataSetChanged()
                }
            }
        }

        val enteredSocketName = findViewById<EditText>(R.id.enterSocketName)
        val enteredSocketCompany = findViewById<EditText>(R.id.enterSocketCompany)

        val addNewSocketButton = findViewById<Button>(R.id.addNewDBRowSocket)
        addNewSocketButton.setOnClickListener{
            val socketName = enteredSocketName.text.toString()
            val socketCompany = enteredSocketCompany.text.toString()

            val socket = Socket(socketName = socketName, companyName = socketCompany)

            val job = scopeIO.launch {
                socketDao?.insert(socket)
                withContext(Dispatchers.Main) {
                    sockets.add(socket)
                    socketAdapter?.notifyDataSetChanged()
                }
            }
        }

    }

    override fun updateProcessor(processorId: Int, processorName: String, processorSocket: String) {
        val processorDao: ProcessorDao? = App.instance?.database?.processorDao()
        val updatedProcessor = Processor(pid = processorId, processorName = processorName, processorSocketName = processorSocket)

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)

        val job = scopeIO.launch {
            processorDao?.update(updatedProcessor)
            withContext(Dispatchers.Main) {
                val index = processors.indexOfFirst { it.pid == processorId }
                if (index != -1) {
                    processors[index] = updatedProcessor
                    processorAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun removeProcessor(processorId: Int, processorName: String, processorSocket: String) {
        val processorDao: ProcessorDao? = App.instance?.database?.processorDao()
        val removedProcessor = Processor(pid = processorId, processorName = processorName, processorSocketName = processorSocket)

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)

        val job = scopeIO.launch {
            processorDao?.delete(removedProcessor)
            withContext(Dispatchers.Main) {
                val index = processors.indexOfFirst { it.pid == processorId }
                if (index != -1) {
                    processors.remove(removedProcessor)
                    processorAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun updateSocket(socketName: String, socketCompany: String) {
        val socketDao: SocketDao? = App.instance?.database?.socketDao()
        val updatedSocket = Socket(socketName = socketName, companyName = socketCompany)

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)

        val job = scopeIO.launch {
            socketDao?.update(updatedSocket)
            withContext(Dispatchers.Main) {
                val index = sockets.indexOfFirst { it.socketName == socketCompany }
                if (index != -1) {
                    sockets[index] = updatedSocket
                    socketAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun removeSocket(socketName: String, socketCompany: String) {
        val socketDao: SocketDao? = App.instance?.database?.socketDao()
        val removedSocket = Socket(socketName = socketName, companyName = socketCompany)

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)

        val job = scopeIO.launch {
            socketDao?.delete(removedSocket)
            withContext(Dispatchers.Main) {
                val index = sockets.indexOfFirst { it.socketName == socketCompany }
                if (index != -1) {
                    sockets.remove(removedSocket)
                    socketAdapter?.notifyDataSetChanged()
                }
            }
        }
    }
}