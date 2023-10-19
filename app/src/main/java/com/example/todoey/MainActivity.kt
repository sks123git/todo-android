package com.example.todoey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    lateinit var addButton: Button
    lateinit var listView: ListView

    var itemList = ArrayList<String>()
    var fileHelper = FileHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.addItemText)
        addButton = findViewById(R.id.addToList)
        listView = findViewById(R.id.listItems)

        itemList =  fileHelper.readData(this)

        var arrayAdaptor = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        listView.adapter = arrayAdaptor

        addButton.setOnClickListener {

            var itemName : String = editText.text.toString()
            itemList.add(itemName)
            editText.setText("")
            fileHelper.writeData(itemList,applicationContext)
            arrayAdaptor.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adaptorView, view, position, l ->
         var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("DO you want to delete")
            alert.setCancelable(false)
            alert.setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.cancel()
            }
            alert.setPositiveButton("Yes") { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdaptor.notifyDataSetChanged()
                fileHelper.writeData(itemList, applicationContext)
            }

            alert.create().show()
        }
    }
}