package com.example.lr_seven

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lr_seven.dao.ProcessorDao
import com.example.lr_seven.dao.SocketDao
import com.example.lr_seven.dao.SocketProcessorDao
import com.example.lr_seven.entity.Processor
import com.example.lr_seven.entity.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val socketTableView = findViewById<TextView>(R.id.socketsTable)
        val processorTableView = findViewById<TextView>(R.id.processorsTable)
        val unitedDataView = findViewById<TextView>(R.id.unitedData)
        val particalDataView = findViewById<TextView>(R.id.particalData)
        val oneToManyView = findViewById<TextView>(R.id.oneToMany)

        val socket1: Socket = Socket("AM5", "AMD")
        val socket2: Socket = Socket("1700", "Intel")
        val socket3: Socket = Socket("AM4", "AMD")
        val socket4: Socket = Socket("1200", "Intel")

        val processor1: Processor = Processor(1, "Ryzen 5 7500F", "AM5")
        val processor2: Processor = Processor(2, "I5-12600K", "1700")
        val processor3: Processor = Processor(3, "Ryzen 7 7800X3D", "AM5")
        val processor4: Processor = Processor(4, "I7-13700K", "1700")

        val db = App.instance?.database

        val socketDao: SocketDao? = db?.socketDao()
        val processorDao: ProcessorDao? = db?.processorDao()
        val socketProcessorsDao: SocketProcessorDao? = db?.socketProcessorsDao()

        val button = findViewById<Button>(R.id.button1)
        button.setOnClickListener {
            val intent = Intent(this, dataBaseCRUD::class.java)
            startActivity(intent)
        }

        val scopeIO = CoroutineScope(Job() + Dispatchers.IO)

        val job = scopeIO.launch {


            val unitedData = socketDao?.getSocketAndProcessorsOnHim();
            val oneToMany = socketProcessorsDao?.readBySocketName("AM5");
            val particalData = processorDao?.getProcessorAndSocketNames();

            val socketsData = socketDao?.getAll();
            val processorsData = processorDao?.getAll();

            withContext(Dispatchers.Main){
                val socketsText = socketsData?.joinToString(separator = "\n"){ socket ->
                    "Socket Name: ${socket.socketName} Company: ${socket.companyName}"
                }
                socketTableView.text = socketsText ?: "Sockets not found";

                val processorsText = processorsData?.joinToString(separator = "\n") { processor ->
                    "ID ${processor.pid}. Processor Name: ${processor.processorName} Socket: ${processor.processorSocketName}"
                }
                processorTableView.text = processorsText ?: "Processors not found";

                val particalDataText = particalData?.joinToString(separator = "\n")  { tuple ->
                    "Partical | Processor name: ${tuple.processorName} processor socket name: ${tuple.processorSocketName}"
                }
                particalDataView.text = particalDataText ?: "ParticalData not found";

                var unitedDataText = StringBuilder();
                unitedData?.forEach { (socket, processors) ->
                    val unitedText = processors.joinToString(separator = "\n") { processor ->
                        "=== SOCKET ${socket.socketName?.uppercase()} COMPANY ${socket.companyName?.uppercase()} === ID ${processor.pid}. Processor Name: ${processor.processorName}"
                    }
                    unitedDataText.append(unitedText);
                }
                unitedDataView.text = unitedDataText;

                val oneToManyText = oneToMany?.joinToString(separator = "\n") {  socketAndProcessor ->

                    val resultText = socketAndProcessor.processors?.joinToString(separator = "\n") { processor ->
                        "=== SOCKET: ${socketAndProcessor.socket.socketName} === Processor: ${processor.processorName}"
                    }
                    "${resultText}";
                }
                oneToManyView.text = oneToManyText ?: "OneToMany not found";
            }
        }
    }
}