package com.mayurg.easydownloader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainTab(
    selected: Boolean,
    totalCount: Int,
    title: String,
    onClick: () -> Unit
) {
    Tab(
        selected = selected,
        unselectedContentColor = Color.LightGray,
        onClick = onClick
    ) {
        Row(
            Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp),
            )
            Box(
                modifier = Modifier
                    .background(Color.Red, RoundedCornerShape(32.dp))
                    .padding(horizontal = 6.dp, vertical = 1.dp)
            ) {
                Text(
                    text = totalCount.toString(),
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.overline.fontSize,
                )
            }
        }
    }
}