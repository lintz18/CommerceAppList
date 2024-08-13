package com.jgcoding.kotlin.commercelistapp.ui.common

sealed class NavScreen(val route: String) {
    data object Home : NavScreen("home")
    data object Detail : NavScreen("detail/{${NavArgs.CommerceId.key}}") {
        fun createRoute(commerceId: Int) = "detail/$commerceId"
    }
}

enum class NavArgs(val key: String) {
    CommerceId("commerceId")
}