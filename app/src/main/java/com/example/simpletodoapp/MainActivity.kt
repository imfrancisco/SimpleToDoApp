package com.example.simpletodoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

             saveItems()
            }
        }

        loadItems()


        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerReview)

        // Create Adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onClickListener)

        // Attach the adapter to the recyclerView to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position items
        recyclerView.layoutManager = LinearLayoutManager(this)


        // set up the button and input field, so that the user can enter a task and add it to the list
        // Get a reference to the button
        // And then set onClickListener
        val inputTextField = findViewById<EditText>(R.id.editTextTextPersonName)

        findViewById<Button>(R.id.button2).setOnClickListener {
            // 1. Grab the text the user has inputted into the @+id/editTextTextPersonName
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that the data has been updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            // 3. Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }


    // Save the data that the user has inputted
    // Save data by writing and reading from a file

    // Get file we need
    fun getDataFile() : File {

        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Create a method to get the file we need

    // Load the items by reading every line in the data file
    fun loadItems(){
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file

    fun saveItems(){
        try{

           org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
        }
        catch (ioExecption: IOException){
            ioExecption.printStackTrace()
        }
    }
}