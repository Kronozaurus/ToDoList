package com.example.mytodolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodolist.data.IMPORTANCE
import com.example.mytodolist.data.TaskItem
import com.example.mytodolist.data.Tasks
import com.example.mytodolist.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment() {
    val args: AddTaskFragmentArgs by navArgs() //znowu jakas wygenerowana klasa?
    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener{ saveTask() }
        binding.titleInput.setText(args.taskToEdit?.title)
        binding.descriptionInput.setText(args.taskToEdit?.description)
        when (args.taskToEdit?.importance) {
            IMPORTANCE.LOW -> binding.importanceGroup.check(R.id.low_radioButton)
            IMPORTANCE.NORMAL -> binding.importanceGroup.check(R.id.normal_radioButton)
            IMPORTANCE.HIGH -> binding.importanceGroup.check(R.id.high_radioButton)
            null -> binding.importanceGroup.check(R.id.normal_radioButton)
        }
    }

    private fun saveTask() {
        var title: String = binding.titleInput.text.toString()
        var description: String = binding.descriptionInput.text.toString()
        val importance = when(binding.importanceGroup.checkedRadioButtonId) {
            R.id.low_radioButton -> IMPORTANCE.LOW
            R.id.normal_radioButton -> IMPORTANCE.NORMAL
            R.id.high_radioButton -> IMPORTANCE.HIGH
            else -> IMPORTANCE.NORMAL
        }
        if(title.isEmpty()) title =  getString(R.string.Title) + "${Tasks.ITEMS.size + 1}" //default task title? !!
        if(description.isEmpty()) description = getString(R.string.Description) //no desc msg?? !!!

        val taskItem = TaskItem (
            {title + description}.hashCode().toString(),
            title,
            description,
            importance
        )
        if(!args.edit) {
            Tasks.addTask(taskItem)
        } else {
            Tasks.updateTask(args.taskToEdit, taskItem)
        }


        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        findNavController().popBackStack(R.id.taskFragment,false)
    }
}