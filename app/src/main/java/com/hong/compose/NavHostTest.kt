package com.hong.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hong.compose.test.LoginScreen


@Composable
fun NavHostTest(context: Context) {
    val navHostController = rememberNavController()

    val statusBarHeightDp = with(LocalContext.current) {
        var height = 0
        val resourcesId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourcesId > 0) {
            height = resources.getDimensionPixelSize(resourcesId)
        }
        with(LocalDensity.current) {
            height.toDp()
        }
    }


    NavHost(navHostController, startDestination = "LoginPage") {

        composable("LoginPage") {
            LoginScreen(context, navHostController)
        }

        composable("MainPage") {
            MainScreen(statusBarHeightDp)
        }
    }

}

