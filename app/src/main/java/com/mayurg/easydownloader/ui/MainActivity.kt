package com.mayurg.easydownloader.ui

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mayurg.easydownloader.R
import com.mayurg.easydownloader.services.DownloadService
import com.mayurg.easydownloader.ui.tabsetup.TabsSetup
import com.mayurg.easydownloader.ui.theme.EasyDownloaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EasyDownloaderTheme {

                val permissions = mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = permissions
                )

                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(
                    key1 = lifecycleOwner,
                    effect = {
                        val observer = LifecycleEventObserver { _, event ->
                            if (event == Lifecycle.Event.ON_START) {
                                permissionsState.launchMultiplePermissionRequest()
                            }
                        }
                        lifecycleOwner.lifecycle.addObserver(observer)

                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                    }
                )

                LaunchedEffect(key1 = permissionsState.allPermissionsGranted) {
                    if (permissionsState.allPermissionsGranted) {
                        startDownloadService()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(id = R.string.app_name))
                            },
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            elevation = 12.dp
                        )
                    }
                ) {
                    TabsSetup(
                        isPermissionAllowed = permissionsState.allPermissionsGranted
                    )
                }
            }
        }
    }

    private fun startDownloadService() {
        val intent = Intent(this, DownloadService::class.java)
        startService(intent)
    }

}


