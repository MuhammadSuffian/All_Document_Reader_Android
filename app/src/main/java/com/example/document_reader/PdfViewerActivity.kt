package com.example.document_reader

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PdfViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        // Get the URI passed via Intent
        val documentUriString = intent.getStringExtra("documentUri")
        val documentUri = Uri.parse(documentUriString)

        if (documentUri != null) {
            openPdf(documentUri)
        } else {
            Log.e("PdfViewerActivity", "Document URI is null")
        }
    }

    private fun openPdf(uri: Uri) {
        try {
            val contentResolver = applicationContext.contentResolver
            val fileDescriptor = contentResolver.openFileDescriptor(uri, "r") ?: return

            val pdfRenderer = PdfRenderer(fileDescriptor)
            val pageCount = pdfRenderer.pageCount
            Log.d("PdfViewerActivity", "PDF has $pageCount pages")

            if (pageCount > 0) {
                // Open the first page as an example
                val currentPage = pdfRenderer.openPage(0)

                // Assuming you have an ImageView in the layout to show the page
                val imageView = findViewById<ImageView>(R.id.pdfImageView)
                val bitmap = Bitmap.createBitmap(
                    currentPage.width, currentPage.height, Bitmap.Config.ARGB_8888
                )

                currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                imageView.setImageBitmap(bitmap)

                currentPage.close()
            }

            pdfRenderer.close()
        } catch (e: Exception) {
            Log.e("PdfViewerActivity", "Error opening PDF: ${e.message}", e)
        }
    }
}
