package com.mayurg.filemanager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile

class FileManager private constructor(
    private val activity: ComponentActivity,
) {

    companion object {
        private const val OPEN_DIRECTORY_REQUEST_CODE = 1001
    }


     data class Builder(
        var activity: ComponentActivity
    ) {
        fun setActivity(activity: ComponentActivity) = apply { this.activity = activity }

        fun build() = run {
            FileManager(activity)
        }
    }

    fun readFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openDirectory()
        }
    }

    private fun openDirectory() {
        val resultLauncher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val directoryUri = data?.data ?: return@registerForActivityResult

                    activity.contentResolver.takePersistableUriPermission(
                        directoryUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    loadDirectory(directoryUri)
                }
            }


        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        resultLauncher.launch(intent)
    }

    fun loadDirectory(directoryUri: Uri) {
        val documentsTree = DocumentFile.fromTreeUri(activity.application, directoryUri) ?: return
        val childDocuments = documentsTree.listFiles()

        Log.d("MG-loadDirectory",childDocuments.size.toString())
    }
}