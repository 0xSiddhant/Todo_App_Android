package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    val db by lazy {
        AppDatabase.getDatabase(this)
    }
    var todoHistoryList = arrayListOf<TodoModel>()
    var adaptor = TodoAdapotor(todoHistoryList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        history_recycleview.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = this@HistoryActivity.adaptor
        }

        db.todoDao().getFinishedTask().observe(this ) {
            if(!it.isNullOrEmpty()) {
                todoHistoryList.clear()
                todoHistoryList.addAll(it)
                adaptor.notifyDataSetChanged()
            } else {
                todoHistoryList.clear()
                adaptor.notifyDataSetChanged()
            }
        }
    }
}