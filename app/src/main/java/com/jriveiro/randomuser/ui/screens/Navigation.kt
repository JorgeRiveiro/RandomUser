package com.jriveiro.randomuser.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jriveiro.randomuser.ui.screens.detail.DetailScreen
import com.jriveiro.randomuser.ui.screens.detail.DetailViewModel
import com.jriveiro.randomuser.ui.screens.home.HomeScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Detail : Screen("detail/{userId}") {
        fun createRoute(userId: String) = "detail/$userId"
    }
}

enum class NavArguments(val key: String) {
    USER_ID("userId")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(onUserClick = { user ->
                navController.navigate(Screen.Detail.createRoute(user.id))
            })
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(NavArguments.USER_ID.key) { type = NavType.StringType })
        ) { backStackEntry ->
            val viewModel: DetailViewModel = hiltViewModel(backStackEntry)
            DetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}