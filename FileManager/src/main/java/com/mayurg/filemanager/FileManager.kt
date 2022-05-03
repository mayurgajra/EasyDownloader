package com.mayurg.filemanager

import android.os.Build
import android.os.Environment
import java.io.File

class FileManager private constructor(
) {

     class Builder() {

        fun build() = run {
            FileManager()
        }
    }


    fun loadFilesFromPath(path: String): MutableList<File> {

        val file: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)?.absolutePath + path)
        } else {
            File(Environment.getExternalStorageDirectory().absolutePath + path)
        }

        if (!file.exists()) {
            file.mkdirs()
        }

        val files = mutableListOf<File>()

        file.listFiles()?.toMutableList()?.let { list ->
            list.sortByDescending { it.lastModified() }
            files.addAll(list)
        }


        return files
    }


}