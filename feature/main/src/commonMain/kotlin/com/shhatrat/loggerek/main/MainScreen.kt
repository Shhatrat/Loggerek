package com.shhatrat.loggerek.main

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.profile.ProfileScreen
import com.shhatrat.loggerek.profile.ProfileViewModel
import loggerek.feature.main.generated.resources.Res
import loggerek.feature.main.generated.resources.logoo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(calculateWindowSizeClass: WindowSizeCallback, mainUiState: MainUiState) {
    Scaffold {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                //TODO move getting list from MainViewModel
                repeat(4){
                    item(
                        icon = {
                            Icon(modifier = Modifier.size(40.dp), painter = painterResource(Res.drawable.logoo), contentDescription = "")
                        },
                        label = { Text("dddd") },
                        selected = false,
                        onClick = {
//                            currentDestination = it
//                            navController.navigateWithoutStack(it)
                        }
                    )

                }
            }
        ) {
//            Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)){
//                Column {
//                    Text(mainUiState.nickName)
//                    Button(onClick = { mainUiState.removeData() }, content = { Text("remove data") })
//                }
//            }

            val vm: ProfileViewModel = koinViewModel()
            LaunchedEffect(Unit) { vm.onStart() }
            ProfileScreen(calculateWindowSizeClass, vm.state.collectAsState().value)
//            AppNavigation(navController, Modifier)
        }
    }
}
