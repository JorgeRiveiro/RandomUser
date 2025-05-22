package com.jriveiro.randomuser.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jriveiro.randomuser.ui.screens.detail.DetailScreen
import com.jriveiro.randomuser.ui.screens.detail.DetailViewModel
import com.jriveiro.randomuser.ui.screens.home.HomeScreen
import kotlinx.serialization.Serializable

sealed class Screen() {
    @Serializable
    object Home
    @Serializable
    data class Detail(val id: String)
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home) {
        composable<Screen.Home> {
            HomeScreen(onUserClick = { user ->
                navController.navigate(Screen.Detail(user))
            })
        }
        composable<Screen.Detail> {
            backStackEntry ->
            val viewModel: DetailViewModel = hiltViewModel(backStackEntry)
            DetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}