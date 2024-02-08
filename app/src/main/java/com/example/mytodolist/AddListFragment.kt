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
import com.example.mytodolist.data.ListItem
import com.example.mytodolist.data.Lists
import com.example.mytodolist.data.TaskItem
import com.example.mytodolist.data.Tasks
import com.example.mytodolist.databinding.FragmentAddListBinding
import com.example.mytodolist.databinding.FragmentAddTaskBinding

class AddListFragment : Fragment() {
    val args: AddListFragmentArgs by navArgs() //znowu jakas wygenerowana klasa?
    private lateinit var binding: FragmentAddListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener{ saveList() }
        binding.titleInput.setText(args.listToEdit?.title)
        binding.descriptionInput.setText(args.listToEdit?.description)
    }

    private fun saveList() {
        var title: String = binding.titleInput.text.toString()
        var description: String = binding.descriptionInput.text.toString()

        if(title.isEmpty()) title =  getString(R.string.Title) + "${Lists.ITEMS.size + 1}" //default task title? !!
        if(description.isEmpty()) description = getString(R.string.Description) //no desc msg?? !!!

        val itemsInList: MutableList<TaskItem> = ArrayList()
        val listItem = ListItem (
            {title + description}.hashCode().toString(),
            title,
            description,
            itemsInList
        )
        Lists.addList(listItem)
        /*
        if(!args.edit) {
            Lists.addList(listItem)
        } else {
            Lists.updateList(args.listToEdit, listItem)
        }
        */

        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        findNavController().popBackStack(R.id.listFragment ,false)
    }
}