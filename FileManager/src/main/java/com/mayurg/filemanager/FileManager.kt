package com.mayurg.filemanager

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import java.io.File

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

    public fun loadDirectory(directoryUri: Uri): List<DocumentFile> {
        val documentsTree = DocumentFile.fromTreeUri(context, directoryUri) ?: return emptyList()
        val childDocuments = documentsTree.listFiles()

        Log.d("MG-loadDirectory", childDocuments.size.toString())

        return childDocuments.asList()
    }

    fun loadInstaFiles(path: String): MutableList<File> {

        val file: File
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)?.absolutePath + path)
        } else {
            file = File(Environment.getExternalStorageDirectory().absolutePath + path)
        }

        if (!file.exists()) {
            file.mkdirs()
        }

        return file.listFiles()?.toMutableList() ?: mutableListOf()
    }


}