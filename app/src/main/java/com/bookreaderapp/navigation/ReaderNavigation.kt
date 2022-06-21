package com.bookreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bookreaderapp.screens.details.ReaderBookDetailsScreen
import com.bookreaderapp.screens.home.HomeScreenViewModel
import com.bookreaderapp.screens.home.ReaderHomeScreen
import com.bookreaderapp.screens.login.ReaderLoginScreen
import com.bookreaderapp.screens.search.BookSearchViewModel
import com.bookreaderapp.screens.search.ReaderBookSearch
import com.bookreaderapp.screens.splash.ReaderSplashScreen
import com.bookreaderapp.screens.stats.ReaderStatsScreen
import com.bookreaderapp.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {

        composable(route = ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }

        composable(route = ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController)
        }

        composable(route = ReaderScreens.ReaderHomeScreen.name) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderHomeScreen(navController, viewModel)
        }

        composable(route = ReaderScreens.ReaderStatsScreen.name) {
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            ReaderStatsScreen(navController,homeViewModel)
        }

        composable(route = ReaderScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<BookSearchViewModel>()
            ReaderBookSearch(navController, viewModel)
        }

        composable(
            route = ReaderScreens.DetailsScreen.name + "/{bookId}", arguments = listOf(
                navArgument(name = "bookId") {
                    type = NavType.StringType
                })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                ReaderBookDetailsScreen(navController, bookId = it.toString())
            }

        }

        composable(
            route = ReaderScreens.UpdateScreen.name + "/{bookItemId}", arguments = listOf(
                navArgument(name = "bookItemId") {
                    type = NavType.StringType
                })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                ReaderUpdateScreen(navController, bookItemId = it.toString())
            }
        }
    }
}