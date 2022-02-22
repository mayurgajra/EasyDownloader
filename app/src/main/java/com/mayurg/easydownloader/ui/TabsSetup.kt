package com.mayurg.easydownloader.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mayurg.easydownloader.MainTab
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsSetup() {

    val tabTitles = listOf(
        "Instagram",
        "Facebook"
    )
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                MainTab(selected = pagerState.currentPage == index,
                    totalCount = 6,
                    title = title,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    })
            }
        }

        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
        ) { pageIndex ->
            when(pageIndex) {
                0 -> InstagramTab()
                1 -> FacebookTab()
            }
        }

    }
}