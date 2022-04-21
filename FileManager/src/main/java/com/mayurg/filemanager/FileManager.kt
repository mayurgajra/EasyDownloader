package com.mayurg.filemanager

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

class FileManager private constructor(
    private val context: Context,
) {

    data class Builder(
        var context: Context
    ) {

        fun build() = run {
            FileManager(context)
        }
    }


    fun loadInstaFiles(path: String): MutableList<File> {

        val file: File
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            file =
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)?.absolutePath + path)
        } else {
            file = File(Environment.getExternalStorageDirectory().absolutePath + path)
        }

        if (!file.exists()) {
            file.mkdirs()
        }

        return file.listFiles()?.toMutableList() ?: mutableListOf()
    }


}