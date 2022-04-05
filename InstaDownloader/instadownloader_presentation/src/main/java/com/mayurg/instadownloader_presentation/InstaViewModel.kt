package com.mayurg.instadownloader_presentation

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayurg.instadownloader_domain.use_case.InstaLoaderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstaViewModel @Inject constructor(
    private val instaLoaderUseCases: InstaLoaderUseCases
) : ViewModel() {


    var state by mutableStateOf(InstaListState())
        private set

    val isRefreshing = MutableStateFlow(false)


    fun loadFiles(uri: Uri) {
        viewModelScope.launch {
            isRefreshing.value = true
            val list = instaLoaderUseCases.instaLoadFiles(uri)
            state = state.copy(list = list)
            isRefreshing.value = false
        }
    }
}