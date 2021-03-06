package com.mayurg.core_ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import coil.compose.AsyncImage

@Composable
fun ViewImage(uri: Uri) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    shareVideo(context, uri)
                }) {
                Icon(imageVector = Icons.Filled.Share, contentDescription = "")
            }
        }
        AsyncImage(
            model = uri,
            modifier = Modifier
                .border(0.5.dp, Color.White)
                .aspectRatio(1f),
            contentDescription = "view",
            contentScale = ContentScale.Crop,
        )
    }

}

private fun shareVideo(context: Context, uri: Uri) {
    val photoURI: Uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName.toString() + ".provider",
        uri.toFile()
    )

    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "image/*"
    sharingIntent.setPackage("com.whatsapp")
    sharingIntent.putExtra(Intent.EXTRA_STREAM, photoURI)
    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivities(arrayOf(Intent.createChooser(sharingIntent, "Share with")))
}