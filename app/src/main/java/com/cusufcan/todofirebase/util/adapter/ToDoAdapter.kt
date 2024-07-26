package com.cusufcan.todofirebase.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cusufcan.todofirebase.databinding.TodoItemBinding
import com.cusufcan.todofirebase.util.model.ToDoData

class ToDoAdapter(private val toDoList: MutableList<ToDoData>) :
    RecyclerView.Adapter<ToDoAdapter.ToDoHolder>() {
    private var listener: ToDoAdapterClickListener? = null
    fun setListener(listener: ToDoAdapterClickListener) {
        this.listener = listener
    }

    inner class ToDoHolder(val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoHolder(binding)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
        with(holder) {
            with(toDoList[position]) {
                binding.todoTask.text = task

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteToDoBtnClicked(this)
                }

                binding.editTask.setOnClickListener {
                    listener?.onEditToDoBtnClicked(this)
                }
            }
        }
    }

    interface ToDoAdapterClickListener {
        fun onDeleteToDoBtnClicked(toDoData: ToDoData)
        fun onEditToDoBtnClicked(toDoData: ToDoData)
    }
}