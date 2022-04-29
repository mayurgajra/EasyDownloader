package com.mayurg.fbdownloader_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayurg.fbdownloader_domain.use_case.FbLoaderUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FbViewModel @Inject constructor(
    private val fbLoaderUseCases: FbLoaderUseCases
) : ViewModel() {


    var state by mutableStateOf(FbListState())
        private set

    val isRefreshing = MutableStateFlow(false)


    fun loadFiles() {
        viewModelScope.launch {
            isRefreshing.value = true
            val list = fbLoaderUseCases.fbLoadFiles()
            state = state.copy(list = list)
            isRefreshing.value = false
        }
    }


}