package com.mayurg.instadownloader_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InstaViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(InstaListState())
        private set

    init {
        loadFiles()
    }

    private fun loadFiles() {

    }
}