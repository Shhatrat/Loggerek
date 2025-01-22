package com.shhatrat.loggerek.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.MoveToIntro
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.get
import com.shhatrat.loggerek.main.NavigationHeader.LOG
import com.shhatrat.loggerek.main.NavigationHeader.PROFILE
import com.shhatrat.loggerek.main.NavigationHeader.SETTINGS
import com.shhatrat.loggerek.main.NavigationHeader.WATCH
import com.shhatrat.loggerek.profile.ProfileScreen
import com.shhatrat.loggerek.profile.ProfileViewModel
import com.shhatrat.loggerek.settings.SettingsViewModel
import com.shhatrat.loggerek.settings.SettinsScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen(calculateWindowSizeClass: WindowSizeCallback, mainUiState: MainUiState) {

    var currentScreen by remember { mutableStateOf(PROFILE) }

    Scaffold {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                mainUiState.navigationTabs.forEach {
                    item(
                        icon = {
                            Icon(modifier = Modifier.size(40.dp), painter = painterResource(it.icon), contentDescription = "")
                        },
                        label = { Text(it.nameRes.get()) },
                        selected = currentScreen == it,
                        onClick = { currentScreen = it }
                    )
                }
            }
        ) {
            handleChangeScreen(calculateWindowSizeClass, currentScreen, mainUiState.moveToIntro)
        }
    }
}

@Composable
private fun handleChangeScreen(calculateWindowSizeClass: WindowSizeCallback, navigationHeader: NavigationHeader,
                               moveToMain: MoveToIntro){
    when(navigationHeader){
        PROFILE -> { openProfileScreen(calculateWindowSizeClass) }
        LOG -> {}
        SETTINGS -> { openSettingsScreen(calculateWindowSizeClass, moveToMain) }
        WATCH -> {}
    }
}

@Composable
private fun openProfileScreen(calculateWindowSizeClass: WindowSizeCallback){
    val vm: ProfileViewModel = koinViewModel()
    LaunchedEffect(Unit) { vm.onStart() }
    ProfileScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
}

@Composable
private fun openSettingsScreen(calculateWindowSizeClass: WindowSizeCallback, moveToIntro: MoveToIntro){
    val vm: SettingsViewModel = koinViewModel { parametersOf(moveToIntro) }
    LaunchedEffect(Unit) { vm.onStart() }
    SettinsScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
}

