package com.jgcoding.kotlin.commercelistapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import com.jgcoding.kotlin.commercelistapp.ui.common.NavArgs
import com.jgcoding.kotlin.commercelistapp.ui.common.NavScreen
import com.jgcoding.kotlin.commercelistapp.ui.detail.DetailScreen
import com.jgcoding.kotlin.commercelistapp.ui.main.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Home.route) {
        composable(NavScreen.Home.route) {

            MainScreen(
                onCommerceClick = { commerce ->
                    navController.navigate(NavScreen.Detail.createRoute(commerce.id))
                }
            )
        }
        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.CommerceId.key) { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getInt(NavArgs.CommerceId.key))
            DetailScreen(
//                onBack = { navController.popBackStack() }
            )
        }
    }
}