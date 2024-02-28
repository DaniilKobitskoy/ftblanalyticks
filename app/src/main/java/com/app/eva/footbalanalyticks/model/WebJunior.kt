package com.app.eva.footbalanalyticks.model

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WebJunior(private val activity: Activity) : WebChromeClient() {

    private var fileUri: Uri? = null
    private var uploadMessage: ValueCallback<Array<Uri>>? = null

    companion object {
        const val FILECHOOSER_RESULTCODE = 1
    }

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        if (uploadMessage != null) {
            uploadMessage?.onReceiveValue(null)
            uploadMessage = null
        }

        uploadMessage = filePathCallback

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = createImageFile()
        if (photoFile != null) {
            fileUri = FileProvider.getUriForFile(
                activity,
                "${activity.packageName}.provider",
                photoFile
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        }

        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        chooserIntent.putExtra(Intent.EXTRA_INTENT, galleryIntent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Select from:")

        val intentArray: Array<Intent?>
        if (photoFile != null) {
            intentArray = arrayOf(cameraIntent)
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
        }

        activity.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
        return true
    }

    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }
}