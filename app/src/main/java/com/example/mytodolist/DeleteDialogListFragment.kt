package com.example.shoppinglisthomework

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val LIST_NAME_PARAM = "list name"
// A constant for the list position argument
private const val LIST_POS_PARAM = "list pos"

/**

 * create an instance of this fragment.
 */
class DeleteDialogListFragment : DialogFragment() {
    // TODO: Rename and change types of parameters


    private var listNameParam: String? = null
    // A variable to store the list position argument
    private var listPosParam: Int? = null
    // A listener object to handle the dialog interaction events
    lateinit var mListener: OnDeleteListDialogInteractionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Get the arguments from the bundle
            listNameParam = it.getString(LIST_NAME_PARAM)
            listPosParam = it.getInt(LIST_POS_PARAM)
        }
    }


    // An interface to define the methods for the dialog interaction events
    interface OnDeleteListDialogInteractionListener{
        // A method to handle the positive click event of the dialog
        fun onDialogPositiveClick(pos:Int?)
        // A method to handle the negative click event of the dialog
        fun onDialogNegativeClick(pos:Int?)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a builder object for the alert dialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        // Set the message of the dialog with the list name argument
        builder.setMessage("Delete this entry? " + "\"$listNameParam\"")
        // Set the positive button of the dialog with a click listener
        builder.setPositiveButton("Confirm", DialogInterface.OnClickListener{ dialogInterface, i ->
            // Call the listener method for the positive click event with the list position argument
            mListener?.onDialogPositiveClick(listPosParam)
        } )
        // Set the negative button of the dialog with a click listener
        builder.setNegativeButton("Discard", DialogInterface.OnClickListener{ dialogInterface, i ->
            // Call the listener method for the negative click event with the list position argument
            mListener?.onDialogNegativeClick(listPosParam)
        } )


        // Return the created dialog object
        return builder.create()
    }

    companion object {
        @JvmStatic
        // A factory method to create an instance of this fragment with arguments and a listener object
        fun newInstance(name: String, pos: Int, interactionListener: OnDeleteListDialogInteractionListener) =
            DeleteDialogListFragment().apply {
                arguments = Bundle().apply {
                    // Put the arguments in the bundle
                    putString(LIST_NAME_PARAM, name)
                    putInt(LIST_POS_PARAM, pos)
                }
                // Set the listener object as a property of this fragment
                mListener = interactionListener
            }
    }
}