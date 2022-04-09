package com.mayurg.easydownloader.ui.tabsetup

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mayurg.fbdownloader_presentation.FacebookTab
import com.mayurg.instadownloader_presentation.InstagramTab
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsSetup(
    isPermissionAllowed: Boolean = false,
) {


    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val instaTabCount = remember { mutableStateOf(0) }
    val fbTabCount = remember { mutableStateOf(0) }

    val tabTitles = listOf(
        "Instagram",
        "Facebook"
    )
    val counts = listOf(
        instaTabCount,
        fbTabCount
    )
    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary
        ) {
            tabTitles.forEachIndexed { index, title ->
                MainTab(
                    selected = pagerState.currentPage == index,
                    totalCount = counts[index],
                    title = title
                ) {
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            }
        }

        HorizontalPager(
            count = tabTitles.size,
            state = pagerState,
        ) { pageIndex ->
            when (pageIndex) {
                0 -> InstagramTab(isPermissionAllowed, {
                    instaTabCount.value = it
                })
                1 -> FacebookTab()
            }
        }

    }
}