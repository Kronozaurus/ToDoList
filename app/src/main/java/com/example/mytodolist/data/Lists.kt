package com.example.mytodolist.data

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.ArrayList

object Lists {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<ListItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    //val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private val COUNT = 1

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addList(createListItem(i))
        }
    }


    // A function to save the carts object to a file using Gson library
    fun saveListObjectToFile(context: Context) {
        // A file name for the data file
        val fullPath = "data.json"
        // A file object with the file name and the context
        val file = File(context.filesDir, fullPath)
        // A Gson object to convert the carts object to JSON format
        val gson = Gson()
        // A JSON string representation of the carts object
        val toJson = gson.toJson(ITEMS)
        // A file writer object to write the JSON string to the file
        val writer = FileWriter(file)
        writer.write(toJson)
        writer.close()
    }


    // A function to restore the carts object from a file using Gson library
    fun restoreListObjectFromFile(context: Context) {
        // A file name for the data file
        val fullPath = "data.json"
        // A file object with the file name and the context
        val file = File(context.filesDir, fullPath)
        // A Gson object to convert the JSON format to carts object
        val gson = Gson()
        // A restored array of cart items from the JSON string in the file
        val restoredItemList = try {
            gson.fromJson(FileReader(file), Array<ListItem>::class.java)
        } catch (e: java.lang.Exception) {
            Log.e("restoreShopItem", "error", e)
            null
        }
        // If the restored array is not null, clear the current carts list and add all the restored items
        restoredItemList?.let { ITEMS.clear(); ITEMS.addAll(restoredItemList) }
    }


    //#####################################
    // A function to print the file content to the log for debugging purposes
    fun printFileContent(context: Context) {
        // A file name for the data file
        val fullPath = "data.json"

        try {
            // A file object with the file name and the context
            val file = File(context.filesDir, fullPath)
            // A string variable with the contents of the file
            val fileContent = file.readText() // read the contents of the file into a String variable
            Log.d("data.json's content: ", fileContent) // print the contents of the file to the debug console
        } catch(e: java.lang.Exception){
            Log.d("data.json's content: ", "catched an error. maybe does not exist") // print an error message to the debug console
            null
        }
    }


    fun addList(item: ListItem) {
        ITEMS.add(item)
        //ITEM_MAP.put(item.id, item)
    }

    fun updateList(listToEdit: ListItem?, newList: ListItem) {
        listToEdit?.let { oldList ->
            val indexOfOldList = ITEMS.indexOf(oldList)
            ITEMS.add(indexOfOldList, newList)
            ITEMS.removeAt(indexOfOldList+1)
        }
    }

    private fun createListItem(position: Int): ListItem {
        val itemsInList: MutableList<TaskItem> = ArrayList()
        return ListItem(position.toString(), "Item " + position, makeDetails(position), list = itemsInList)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A placeholder item representing a piece of content.
     */

}

data class ListItem(
    val id: String, val title: String, val description: String, var list: List<TaskItem>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(TaskItem)!!
    ) {
    }

    override fun toString(): String = title
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description) }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListItem> {
        override fun createFromParcel(parcel: Parcel): ListItem {
            return ListItem(parcel)
        }

        override fun newArray(size: Int): Array<ListItem?> {
            return arrayOfNulls(size)
        }
    }
}