package com.mayurg.instadownloader_presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.documentfile.provider.DocumentFile
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun InstagramTab(
    isPermissionAllowed: Boolean = false,
    viewModel: InstaViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val directoryUri = data?.data ?: return@rememberLauncherForActivityResult

                context.contentResolver.takePersistableUriPermission(
                    directoryUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                context.getSharedPreferences("test", Context.MODE_PRIVATE).edit()
                    .putString("uri", directoryUri.toString()).apply()
                viewModel.loadFiles(directoryUri)
            }
        }

    LaunchedEffect(key1 = isPermissionAllowed) {
        if (isPermissionAllowed) {
            val uri = context.getSharedPreferences("test", Context.MODE_PRIVATE)
                .getString("uri", null)
                ?.toUri()

            if (uri != null) {
                viewModel.loadFiles(uri)
            } else {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                launcher.launch(intent)
            }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        modifier = Modifier.fillMaxSize(),
        onRefresh = {
            val uri = context.getSharedPreferences("test", Context.MODE_PRIVATE)
                .getString("uri", null)
                ?.toUri()

            if (uri != null) {
                viewModel.loadFiles(uri)
            }
        },
    ) {
        FilesList(list = state.list)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilesList(list: List<DocumentFile>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(list) { item ->
                AsyncImage(
                    model = item.uri,
                    modifier = Modifier
                        .border(0.5.dp, Color.White)
                        .aspectRatio(1f),
                    contentDescription = "test",
                    contentScale = ContentScale.Crop
                )

            }
        }
    )
}