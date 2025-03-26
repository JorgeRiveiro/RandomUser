package com.jriveiro.randomuser.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jriveiro.randomuser.ui.screens.detail.DetailScreen
import com.jriveiro.randomuser.ui.screens.home.HomeScreen
import com.jriveiro.randomuser.users

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onUserClick = { user ->
                navController.navigate("detail/${user.id}")
            })
        }
        composable(
            route = "detail/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId")
            DetailScreen(
                user = users.first { it.id == userId },
                onBack = { navController.popBackStack() })
        }
    }
}