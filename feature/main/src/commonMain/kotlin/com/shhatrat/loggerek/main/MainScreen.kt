package com.shhatrat.loggerek.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import loggerek.feature.main.generated.resources.Res
import loggerek.feature.main.generated.resources.introLogo
import loggerek.feature.main.generated.resources.logoo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(mainUiState: MainUiState) {
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
            Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)){
                Column {
                    Text(mainUiState.nickName)
                    Button(onClick = { mainUiState.removeData() }, content = { Text("remove data") })
                }
            }
//            AppNavigation(navController, Modifier)
        }
    }
}
