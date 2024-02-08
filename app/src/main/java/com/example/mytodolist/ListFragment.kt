package com.example.mytodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.data.Lists
import com.example.mytodolist.data.Tasks
import com.example.mytodolist.databinding.FragmentItemListBinding
import com.example.mytodolist.databinding.FragmentListsListBinding
import com.example.mytodolist.placeholder.PlaceholderContent
import com.example.shoppinglisthomework.DeleteDialogListFragment
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class ListFragment : Fragment(), ToDoListListener, DeleteDialogListFragment.OnDeleteListDialogInteractionListener {

    private lateinit var binding: FragmentListsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListsListBinding.inflate(inflater, container, false)
        binding.listsAddButton.setOnClickListener() { addButtonCLick() }
        // Set the adapter

        with(binding.listsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyListRecyclerViewAdapter(Lists.ITEMS, this@ListFragment) //tu inaczej sie nazywa
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Lists.saveListObjectToFile(requireContext())
        Lists.printFileContent(requireContext())
    }

    private fun addButtonCLick() {
        findNavController().navigate(R.id.action_listFragment_to_addListFragment)
    }

    override fun onItemClick(position: Int) {
        val actionListFragmentToTaskFragment =
            ListFragmentDirections.actionListFragmentToTaskFragment(Lists.ITEMS.get(position))
        findNavController().navigate(actionListFragmentToTaskFragment)
    }


    override fun onItemLongClick(position: Int) {
        showDeleteDialog(position)
    }

    private fun showDeleteDialog(position: Int) {
        val deleteDialog = DeleteDialogListFragment.newInstance(Lists.ITEMS.get(position).title, position, this)
        deleteDialog.show(requireActivity().supportFragmentManager, "DeleteDialog")
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Lists.ITEMS.removeAt(pos!!)
        Snackbar.make(requireView(), "List Deleted", Snackbar.LENGTH_LONG)
            .show()
        notifyDataSetChanged()
    }

    override fun onDialogNegativeClick(pos: Int?) {
        Snackbar.make(requireView(), "Delete cancelled", Snackbar.LENGTH_LONG)
            .setAction("Redo", View.OnClickListener { showDeleteDialog(pos!!) })
            .show()
    }

    private fun notifyDataSetChanged() {
        val rvAdapter = binding.listsList.adapter
        rvAdapter?.notifyDataSetChanged()
    }
}