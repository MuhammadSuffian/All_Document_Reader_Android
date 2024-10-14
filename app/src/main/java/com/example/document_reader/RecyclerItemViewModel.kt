package com.example.document_reader

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class RecyclerItemViewModel(val context: Context, val arrContact: ArrayList<itemViewModel>) : RecyclerView.Adapter<RecyclerItemViewModel.ViewHolder>() {

    class ViewHolder(itemViewModel: View) : RecyclerView.ViewHolder(itemViewModel) {
        val imageView: ImageView = itemViewModel.findViewById(R.id.Image_View)
        val textView: TextView = itemViewModel.findViewById(R.id.text_View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("Tag", "Holder Created")
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        Log.d("Tag", "Got item Count")
        return arrContact.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("Tag", "Hello Suffian in Bind View Holder")
        holder.textView.text = arrContact[position].name

        // Handle click on the item
        holder.itemView.setOnClickListener {
            val uri: Uri? = arrContact[position].uri
            if (uri != null) {
                openDocumentInApp(uri)
            } else {
                Log.e("Tag", "Uri is null for position $position")
            }
        }
    }

    // Function to open the document using an intent
    private fun openDocumentInApp(uri: Uri) {
//        val intent = Intent(context, DocumentViewerActivity::class.java).apply {
//            putExtra("documentUri", uri)
//        }
//        context.startActivity(intent)
    }


    // Helper function to get the MIME type from the URI
    private fun getMimeType(uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }
}
