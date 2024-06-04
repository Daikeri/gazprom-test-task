package com.example.gazpromtask.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gazpromtask.ui.template.CitiesList
import com.example.gazpromtask.ui.template.LoadScreen
import com.example.gazpromtask.ui.template.RetryNetworkRequest
import com.example.gazpromtask.ui.template.WeatherScreen
import com.example.gazpromtask.viewmodel.CitiesListViewModel


object Route {
    const val LOAD = "LoadedScreen"
    const val RETRY = "RetryNetworkRequestScreen"
    const val CITIES = "CityListScreen"
    const val WEATHER = "WeatherCityScreen"
}

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = Route.WEATHER,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(Route.LOAD) {
                LoadScreen(
                    onSuccessful = { navController.navigate(Route.CITIES) },
                    onFailure = { message ->
                        navController.navigate("${Route.RETRY}/$message")
                    }
                )
            }

            composable(
                route = "${Route.RETRY}/{errorMessage}",
                arguments = listOf(navArgument("errorMessage") { type = NavType.StringType })
            ) {
                val errorMessage = it.arguments?.getString("errorMessage") ?: ""
                RetryNetworkRequest(errorMessage) { navController.navigate(Route.LOAD) }
            }

            composable(Route.CITIES) {
                val previousStack = remember(it) {
                    navController.getBackStackEntry(Route.LOAD)
                }
                val existViewModel: CitiesListViewModel = viewModel(previousStack)
                CitiesList(existViewModel)
            }

            composable(
                route = "${Route.WEATHER}/{city}",
                arguments = listOf(navArgument("city") { type = NavType.IntType })
            ) {
                val city = it.arguments?.getInt("errorMessage") ?: 0
                WeatherScreen()
            }

            composable(Route.WEATHER) {
                WeatherScreen()
            }
        }
    }
}