package com.trial.bucketlist

sealed class Screen (val route : String){
    object HomeScreen : Screen("home_screen")
    object AddScreen : Screen("add_screen")
    object LoginScreen : Screen("login")
    object SignUpScreen : Screen("signup")
}