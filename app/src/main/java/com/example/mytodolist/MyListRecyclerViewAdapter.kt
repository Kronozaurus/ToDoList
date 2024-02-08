package com.example.mytodolist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mytodolist.data.IMPORTANCE
import com.example.mytodolist.data.ListItem
import com.example.mytodolist.data.TaskItem

import com.example.mytodolist.placeholder.PlaceholderContent.PlaceholderItem
import com.example.mytodolist.databinding.FragmentListsBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyListRecyclerViewAdapter(
    private val values: List<ListItem>,
    private val eventListener: ToDoListListener
) : RecyclerView.Adapter<MyListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentListsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.title
        holder.itemContainer.setOnClickListener {
            eventListener.onItemClick(position)
        }
        holder.itemContainer.setOnLongClickListener {
            eventListener.onItemLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentListsBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgView: ImageView = binding.itemImgLists
        val contentView: TextView = binding.contentLists
        val itemContainer: View = binding.root

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}