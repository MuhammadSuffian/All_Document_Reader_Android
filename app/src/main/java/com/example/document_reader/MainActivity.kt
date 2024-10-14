package com.example.document_reader

import android.Manifest
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

val dataobject = ArrayList<itemViewModel>()
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycleview)
        if (checkPermission()) {
            Log.d("TAG", "Permission True ")
            fetchAllPdfAndDocFiles()
            Log.d("TAG", "Permission True ")
            val recyclerAdapter = RecyclerItemViewModel(this, dataobject)
            recyclerView.layoutManager = GridLayoutManager(this, 2)
            recyclerView.adapter = recyclerAdapter
            if (dataobject.isNotEmpty()) {
                Toast.makeText(this, "Item  fetched: ${dataobject[0].name}", Toast.LENGTH_SHORT).show()
            }
            //Log.d("MainActivity", "DOC URIs: $")
        } else {
            requestPermission()
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkPermission(): Boolean {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            val pem = Environment.isExternalStorageManager()
            Toast.makeText(this, "Permission:$pem", Toast.LENGTH_SHORT).show()
            return Environment.isExternalStorageManager()
        } else {
            return false
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    addCategory("android.intent.category.DEFAULT")
                    data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                }
                startActivityForResult(intent, 2296)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, 2296)
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                100
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun fetchAllPdfAndDocFiles() {
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.MIME_TYPE
        )

        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
        val selectionArgs = arrayOf("application/pdf")

        val queryUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        val cursor: Cursor? = contentResolver.query(queryUri, projection, selection, selectionArgs, null)

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID))
                val name = it.getString(it.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME))
                val uri = ContentUris.withAppendedId(queryUri, id)
                val model = itemViewModel(id, name, uri)
                dataobject.add(model)
            }
        }
    }


}