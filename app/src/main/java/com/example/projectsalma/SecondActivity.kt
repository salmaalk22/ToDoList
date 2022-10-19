package com.example.projectsalma

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import api.RetrofitHelper
import api.TodoApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SecondActivity : AppCompatActivity() {
    lateinit var labelHeader: TextView
    lateinit var listTodo: ListView

    val apiKey =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFsY3l6dGRpZXhsZ2pkc3duZm1kIiwicm9sZSI6ImFub24iLCJpYXQiOjE2NjU3MzAzMDIsImV4cCI6MTk4MTMwNjMwMn0.OOR0S8FmRNDmUXTr0Q_XRdw9sgEiSUfH5jqvWp2JygY"
    val token = "Bearer $apiKey"

    val Items = ArrayList<Model>()
    val todoApi = RetrofitHelper.getInstance().create(TodoApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        labelHeader = findViewById(R.id.label_header)
        listTodo = findViewById(R.id.list_todo)

        val result = intent.getStringExtra("result")
        labelHeader.text = "What's up, $result?"

        CoroutineScope(Dispatchers.Main).launch {
            val response = todoApi.get(token = token, apiKey = apiKey)

            response.body()?.forEach {
                Items.add(
                    Model(
                        Id = it.id,
                        Title = it.title,
                        Description = it.description
                    )
                )
            }

            setList(Items)
        }

        listTodo.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as Model
            val title = item.Title

            Toast.makeText(
                applicationContext,
                "Kamu memilih $title",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun setList(Items: ArrayList<Model>) {
        val adapter = TodoAdapter(this, R.layout.todo_item, Items)
        listTodo.adapter = adapter
    }
}