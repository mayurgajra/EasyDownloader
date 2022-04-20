package com.mayurg.instadownloader_presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val directoryUri = data?.data ?: return@rememberLauncherForActivityResult

            context.contentResolver.takePersistableUriPermission(
                directoryUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            viewModel.saveDirectoryUri(directoryUri)
            viewModel.loadFiles()
        }
    }

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


