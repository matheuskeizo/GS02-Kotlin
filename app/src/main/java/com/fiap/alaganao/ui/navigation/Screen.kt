package com.fiap.alaganao.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Lista : Screen("lista")
    object Formulario : Screen("formulario/{id}") {
        // id = -1 significa que é um relato novo
        fun createRoute(id: Long = -1L): String = "formulario/$id"
    }
}
