@file:OptIn(ExperimentalResourceApi::class)

package com.shhatrat.loggerek.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.MoveToIntro
import com.shhatrat.loggerek.base.MoveToLogCache
import com.shhatrat.loggerek.base.OnBack
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.di.LogScope
import com.shhatrat.loggerek.base.get
import com.shhatrat.loggerek.log.LogScreen
import com.shhatrat.loggerek.log.LogViewModel
import com.shhatrat.loggerek.search.SearchScreen
import com.shhatrat.loggerek.search.SearchViewModel
import com.shhatrat.loggerek.settings.ProfileScreen
import com.shhatrat.loggerek.settings.ProfileViewModel
import com.shhatrat.loggerek.settings.SettingsViewModel
import com.shhatrat.loggerek.settings.SettinsScreen
import loggerek.feature.main.generated.resources.Res
import loggerek.feature.main.generated.resources.back
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun MainScreen(calculateWindowSizeClass: WindowSizeCallback, mainUiState: MainUiState) {

    val screenStack =
        remember { mutableStateListOf<NavigationHeader>(NavigationHeader.Main.PROFILE) }

    Scaffold {
        NavigationSuiteScaffold(
            navigationSuiteColors = NavigationSuiteDefaults.colors(
                navigationBarContainerColor = MaterialTheme.colors.primary,
                navigationRailContainerColor = MaterialTheme.colors.primary,
            ),
            modifier = Modifier.fillMaxSize(),
            navigationSuiteItems = {
                mainUiState.navigationTabs.forEach {
                    val selected = screenStack.lastOrNull() == it
                    item(

                        icon = {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                painter = rememberAsyncImagePainter(Res.getUri(it.imagePath)),
                                contentDescription = "image ${it.imagePath}",
                                tint = if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.background
                            )
                        },
                        label = {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                color = MaterialTheme.colors.background,
                                text = it.nameRes.get()
                            )
                        },
                        selected = selected,
                        onClick = { screenStack.add(it) })

                }
            }
        ) {
            handleChangeScreen(calculateWindowSizeClass = calculateWindowSizeClass,
                navigationHeader = screenStack.last(),
                moveToMain = mainUiState.moveToIntro,
                moveToOtherScreen = {
                    if (it is NavigationHeader.Main) {
                        screenStack.clear()
                    }
                    screenStack.add(it)
                },
                onBack = {
                    screenStack.removeLast()
                }
            )
        }
    }
}

@Composable
private fun handleChangeScreen(
    calculateWindowSizeClass: WindowSizeCallback, navigationHeader: NavigationHeader,
    moveToMain: MoveToIntro,
    moveToOtherScreen: (NavigationHeader) -> Unit,
    onBack: OnBack
) {
    Column {
        if (navigationHeader is NavigationHeader.Specific) {
            HeaderContent(onBack, navigationHeader)
        }
        when (navigationHeader) {
            NavigationHeader.Main.PROFILE -> {
                openProfileScreen(calculateWindowSizeClass)
            }

            NavigationHeader.Main.SETTINGS -> {
                openSettingsScreen(calculateWindowSizeClass, moveToMain)
            }

            NavigationHeader.Main.CACHES -> {
                openSearch(calculateWindowSizeClass, moveToOtherScreen, onBack)
            }

            is NavigationHeader.Specific.LOG -> {
                openLogScreen(calculateWindowSizeClass, navigationHeader.cacheId)
            }
        }
    }
}

@Composable
private fun HeaderContent(
    onBack: OnBack,
    navigationHeader: NavigationHeader
) {
    Row(
        modifier = Modifier.height(60.dp).fillMaxWidth()
            .background(MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.fillMaxHeight().clickable { onBack() },
            painter = rememberAsyncImagePainter(Res.getUri("drawable/back.svg")),
            colorFilter = ColorFilter.tint(MaterialTheme.colors.background),
            contentDescription = "back arrow"
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.background, text = navigationHeader.nameRes.get()
        )
    }
}

@Preview
@Composable
fun HeaderContentPreview(){
    LoggerekTheme {
        HeaderContent({}, NavigationHeader.Specific.LOG({}, ""))
    }
}

@Composable
private fun openSearch(
    calculateWindowSizeClass: WindowSizeCallback,
    moveToOtherScreen: (NavigationHeader) -> Unit,
    onBack: OnBack
) {
    val moveToLogCache: MoveToLogCache =
        { cache: String -> moveToOtherScreen(NavigationHeader.Specific.LOG(onBack, cache)) }
    val vm: SearchViewModel = koinViewModel { parametersOf(moveToLogCache) }
    LaunchedEffect(Unit) { vm.onStart() }
    SearchScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
}

@Composable
private fun openProfileScreen(calculateWindowSizeClass: WindowSizeCallback) {
    val vm: ProfileViewModel = koinViewModel()
    LaunchedEffect(Unit) { vm.onStart() }
    ProfileScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
}

@Composable
private fun openLogScreen(calculateWindowSizeClass: WindowSizeCallback, cacheId: String) {
    val scope = remember { getKoin().getOrCreateScope(cacheId, named(LogScope)) }
    val vm: LogViewModel = scope.get { parametersOf(cacheId) }
    LaunchedEffect(Unit) { vm.onStart() }
    LogScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
}

@Composable
private fun openSettingsScreen(
    calculateWindowSizeClass: WindowSizeCallback,
    moveToIntro: MoveToIntro
) {
    val vm: SettingsViewModel = koinViewModel { parametersOf(moveToIntro) }
    LaunchedEffect(Unit) { vm.onStart() }
    SettinsScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
}

