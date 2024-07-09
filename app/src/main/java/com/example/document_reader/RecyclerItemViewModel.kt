package com.example.document_reader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        //holder.imageView.setImageURI(arrContact[position].uri)
        holder.textView.text=arrContact[position].name
    }
}
