package com.mayurg.filemanager

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
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

    fun loadDirectory(directoryUri: Uri) {
        val documentsTree = DocumentFile.fromTreeUri(activity.application, directoryUri) ?: return
        val childDocuments = documentsTree.listFiles()

        Log.d("MG-loadDirectory",childDocuments.size.toString())
    }


}