package com.cusufcan.todofirebase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cusufcan.todofirebase.databinding.FragmentAddToDoPopupBinding
import com.cusufcan.todofirebase.util.model.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddToDoPopupFragment : DialogFragment() {
    private lateinit var binding: FragmentAddToDoPopupBinding
    private lateinit var listener: DialogNextButtonClickListener

    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextButtonClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDoPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddToDoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddToDoPopupBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            toDoData = ToDoData(
                it.getString("taskId").toString(),
                it.getString("task").toString(),
            )

            binding.toDoEditText.setText(toDoData?.task)
        }

        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val toDoTask = binding.toDoEditText.text.toString().trim()
            if (toDoTask.isNotEmpty()) {
                if (toDoData == null) {
                    listener.onSaveTask(toDoTask, binding.toDoEditText)
                } else {
                    toDoData?.task = toDoTask
                    listener.onUpdateTask(toDoData!!, binding.toDoEditText)
                }
            } else {
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextButtonClickListener {
        fun onSaveTask(toDo: String, toDoEditText: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, toDoEditText: TextInputEditText)
    }
}