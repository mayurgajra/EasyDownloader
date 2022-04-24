package com.mayurg.instadownloader_presentation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun InstagramList(
    isPermissionAllowed: Boolean = false,
    onCountChange: (count: Int) -> Unit,
    onItemClick: (uri: Uri) -> Unit,
    viewModel: InstaViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    LaunchedEffect(key1 = isPermissionAllowed) {
        if (isPermissionAllowed) {
            viewModel.loadFiles()
        }
    }

    LaunchedEffect(key1 = state.list.count()) {
        onCountChange(state.list.count())
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        modifier = Modifier.fillMaxSize(),
        onRefresh = {
            viewModel.loadFiles()
        },
    ) {
        FilesList(list = state.list,onItemClick)
    }
}


