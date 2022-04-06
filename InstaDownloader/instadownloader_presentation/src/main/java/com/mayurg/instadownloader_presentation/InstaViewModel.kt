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


    fun loadFiles(uri: Uri? = null) {
        viewModelScope.launch {
            isRefreshing.value = true
            val callUri = uri ?: getDirectoryUri()
            callUri?.let {
                val list = instaLoaderUseCases.instaLoadFiles(it)
                state = state.copy(list = list)
            }
            isRefreshing.value = false
        }
    }

    fun saveDirectoryUri(uri: Uri) {
        viewModelScope.launch {
            instaLoaderUseCases.instaSaveDirectoryUri(uri)
        }
    }

    suspend fun getDirectoryUri(): Uri? {
        return instaLoaderUseCases.instaGetDirectoryUri()
    }


}