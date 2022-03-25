package com.mayurg.filemanager

import androidx.activity.ComponentActivity

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


}