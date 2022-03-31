package com.mayurg.filemanager

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile

class FileManager private constructor(
    private val context: Context,
) {

    companion object {
        private const val OPEN_DIRECTORY_REQUEST_CODE = 1001
    }


    data class Builder(
        var context: Context
    ) {

        fun build() = run {
            FileManager(context)
        }
    }

    fun loadDirectory(directoryUri: Uri): List<DocumentFile> {
        val documentsTree = DocumentFile.fromTreeUri(context, directoryUri) ?: return emptyList()
        val childDocuments = documentsTree.listFiles()

        Log.d("MG-loadDirectory", childDocuments.size.toString())

        return childDocuments.asList()
    }


}