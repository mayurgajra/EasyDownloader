package com.mayurg.filemanager

import android.app.Activity
import android.content.Intent
import android.os.Build

class FileManager private constructor(
    private val activity: Activity,
) {

    companion object {
        private const val OPEN_DIRECTORY_REQUEST_CODE = 1001
    }

    data class Builder(
        var activity: Activity
    ) {
        fun setActivity(activity: Activity) = apply { this.activity = activity }

        fun build() = FileManager(activity)
    }

    fun readFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openDirectory()
        }
    }

    private fun openDirectory() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        activity.startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
    }
}