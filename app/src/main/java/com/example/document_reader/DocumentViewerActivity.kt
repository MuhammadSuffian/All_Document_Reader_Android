//package com.example.document_reader
//
//import android.graphics.Bitmap
//import android.graphics.pdf.PdfRenderer
//import android.net.Uri
//import android.os.Bundle
//import android.os.ParcelFileDescriptor
//import android.widget.ImageView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
////import com.github.ahmadullahpk.alldocumentreader.AllDocumentsReader
//
//class DocumentViewerActivity : AppCompatActivity() {
//
//    private lateinit var imageView: ImageView
////    private lateinit var allDocumentsReader: AllDocumentsReader
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_document_viewer)
//
////        imageView = findViewById(R.id.imageView)
////        allDocumentsReader = AllDocumentsReader()
//
//        // Get the document URI from intent
//        val documentUri: Uri? = intent.getParcelableExtra("documentUri")
//
//        documentUri?.let {
//            val mimeType = contentResolver.getType(it)
//
//            // Check the file type and open accordingly
//            when (mimeType) {
//                "application/pdf" -> openPdf(it)  // Open PDF using PdfRenderer
//                else -> openOtherDocument(it)     // Use AllDocumentsReader for other types
//            }
//        } ?: run {
//            Toast.makeText(this, "Invalid document URI", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    // Method to open PDF using PdfRenderer
//    private fun openPdf(uri: Uri) {
//        try {
//            val fileDescriptor: ParcelFileDescriptor? = contentResolver.openFileDescriptor(uri, "r")
//            fileDescriptor?.let {
//                val pdfRenderer = PdfRenderer(it)
//                val page = pdfRenderer.openPage(0)
//
//                val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
//                page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
//
//                imageView.setImageBitmap(bitmap)
//                page.close()
//                pdfRenderer.close()
//            }
//        } catch (e: Exception) {
//            Toast.makeText(this, "Error opening PDF", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // Method to open other documents using AllDocumentsReader
//    private fun openOtherDocument(uri: Uri) {
//        try {
//            allDocumentsReader.openDocument(this, uri)
//        } catch (e: Exception) {
//            Toast.makeText(this, "Error opening document", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
