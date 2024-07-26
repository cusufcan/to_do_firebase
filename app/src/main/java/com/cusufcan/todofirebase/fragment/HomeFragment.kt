package com.cusufcan.todofirebase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cusufcan.todofirebase.databinding.FragmentHomeBinding
import com.cusufcan.todofirebase.util.adapter.ToDoAdapter
import com.cusufcan.todofirebase.util.model.ToDoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment(), AddToDoPopupFragment.DialogNextButtonClickListener,
    ToDoAdapter.ToDoAdapterClickListener {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    private lateinit var navController: NavController

    private var popUpFragment: AddToDoPopupFragment? = null

    private lateinit var adapter: ToDoAdapter
    private lateinit var toDoList: MutableList<ToDoData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        getDataFromFirebase()
        registerEvents()
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = Firebase.database.reference.child("Tasks").child(auth.currentUser!!.uid)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        toDoList = mutableListOf()
        adapter = ToDoAdapter(toDoList)
        adapter.setListener(this)
        binding.recyclerView.adapter = adapter
    }

    private fun getDataFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                toDoList.clear()
                for (taskSnapshot in snapshot.children) {
                    val toDoTask = taskSnapshot.key?.let {
                        ToDoData(it, taskSnapshot.value.toString())
                    }
                    if (toDoTask != null) {
                        toDoList.add(toDoTask)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registerEvents() {
        binding.addBtnHome.setOnClickListener {
            if (popUpFragment != null) childFragmentManager.beginTransaction()
                .remove(popUpFragment!!).commit()
            popUpFragment = AddToDoPopupFragment()
            popUpFragment!!.setListener(this)
            popUpFragment!!.show(childFragmentManager, AddToDoPopupFragment.TAG)
        }
    }

    override fun onSaveTask(toDo: String, toDoEditText: TextInputEditText) {
        databaseRef.push().setValue(toDo).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "ToDo saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }

            toDoEditText.text?.clear()
            popUpFragment!!.dismiss()
        }
    }

    override fun onUpdateTask(toDoData: ToDoData, toDoEditText: TextInputEditText) {
        val map = HashMap<String, Any>()
        map[toDoData.taskId] = toDoData.task
        databaseRef.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }

            toDoEditText.text?.clear()
            popUpFragment!!.dismiss()
        }
    }

    override fun onDeleteToDoBtnClicked(toDoData: ToDoData) {
        databaseRef.child(toDoData.taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditToDoBtnClicked(toDoData: ToDoData) {
        if (popUpFragment != null) childFragmentManager.beginTransaction().remove(popUpFragment!!)
            .commit()

        popUpFragment = AddToDoPopupFragment.newInstance(toDoData.taskId, toDoData.task)
        popUpFragment!!.setListener(this)
        popUpFragment!!.show(childFragmentManager, AddToDoPopupFragment.TAG)
    }
}