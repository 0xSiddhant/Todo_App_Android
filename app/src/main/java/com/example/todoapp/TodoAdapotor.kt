package com.example.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapotor(val list: List<TodoModel>): RecyclerView.Adapter<TodoAdapotor.TodoViewHolder>() {

    class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(todoModel: TodoModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random().nextInt(colors.size)]

                viewColorTag.setBackgroundColor(randomColor)
                txtShowTitle.text = todoModel.title
                txtShowCategory.text = todoModel.category
                txtShowTask.text = todoModel.description
                updateTime(todoModel.time)
                updateDate(todoModel.date)
            }
        }

        private fun updateTime(time: Long) {
            val myFormat = "h:mm a"
            val sdf = SimpleDateFormat(myFormat)
            itemView.txtShowTime.setText(sdf.format(Date(time)))
        }

        private  fun updateDate(time: Long) {
            val myFormat = "EEE d MMM yyyy"
            val sdf = SimpleDateFormat(myFormat)
            itemView.txtShowDate.setText(sdf.format(Date(time)))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])

        // or
//        with(holder.itemView) {}

    }

    override fun getItemCount() = list.size
}