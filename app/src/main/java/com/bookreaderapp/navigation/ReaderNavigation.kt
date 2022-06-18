package com.bookreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bookreaderapp.screens.details.ReaderBookDetailsScreen
import com.bookreaderapp.screens.home.ReaderHomeScreen
import com.bookreaderapp.screens.login.ReaderLoginScreen
import com.bookreaderapp.screens.search.ReaderBookSearch
import com.bookreaderapp.screens.splash.ReaderSplashScreen
import com.bookreaderapp.screens.stats.ReaderStatsScreen
import com.bookreaderapp.screens.update.ReaderUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {

        composable(route = ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController)
        }

        composable(route = ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController)
        }

        composable(route = ReaderScreens.ReaderHomeScreen.name){
            ReaderHomeScreen(navController)
        }

        composable(route = ReaderScreens.ReaderStatsScreen.name){
            ReaderStatsScreen(navController)
        }

        composable(route = ReaderScreens.SearchScreen.name){
            ReaderBookSearch(navController)
        }

        composable(route = ReaderScreens.DetailsScreen.name){
            ReaderBookDetailsScreen(navController)
        }

        composable(route = ReaderScreens.UpdateScreen.name){
            ReaderUpdateScreen(navController)
        }
    }
}