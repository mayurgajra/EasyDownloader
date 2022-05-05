package com.mayurg.easydownloader.ui.tabsetup

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.net.toUri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mayurg.core_ui.components.ViewImage
import com.mayurg.core_ui.components.ViewVideo
import com.mayurg.fbdownloader_presentation.FbMediaList
import com.mayurg.instadownloader_presentation.InstaMediaList
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsSetup(
    isPermissionAllowed: Boolean = false,
) {


    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val instaTabCount = remember { mutableStateOf(0) }
    val fbTabCount = remember { mutableStateOf(0) }
    val navController = rememberNavController()

    val tabTitles = listOf("Instagram", "Facebook")
    val counts = listOf(instaTabCount, fbTabCount)


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
                0 -> {
                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable(route = "list") {
                            InstaMediaList(
                                isPermissionAllowed = isPermissionAllowed,
                                onItemClick = {
                                    val encodedUrl = URLEncoder.encode(
                                        it.toString(),
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    navController.navigate("view/$encodedUrl")
                                },
                                onCountChange = {
                                    instaTabCount.value = it
                                }
                            )
                        }

                        composable(
                            route = "view" + "/{uri}",
                            arguments = listOf(
                                navArgument("uri") {
                                    type = NavType.StringType
                                },
                            )
                        ) {
                            val uri = it.arguments?.getString("uri")!!.toUri()

                            if (uri.toString().contains(".mp4")) {
                                ViewVideo(uri)
                            } else {
                                ViewImage(uri)
                            }
                        }
                    }

                }
                1 -> {
                    NavHost(
                        navController = navController,
                        startDestination = "list"
                    ) {
                        composable(route = "list") {
                            FbMediaList(
                                isPermissionAllowed = isPermissionAllowed,
                                onItemClick = {
                                    val encodedUrl = URLEncoder.encode(
                                        it.toString(),
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    navController.navigate("view/$encodedUrl")
                                },
                                onCountChange = {
                                    fbTabCount.value = it
                                }
                            )
                        }

                        composable(
                            route = "view" + "/{uri}",
                            arguments = listOf(
                                navArgument("uri") {
                                    type = NavType.StringType
                                },
                            )
                        ) {
                            val uri = it.arguments?.getString("uri")!!.toUri()

                            if (uri.toString().contains(".mp4")) {
                                ViewVideo(uri)
                            } else {
                                ViewImage(uri)
                            }
                        }
                    }

                }
            }
        }

    }
}