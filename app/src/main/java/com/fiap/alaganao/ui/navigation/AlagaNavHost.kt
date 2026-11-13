package com.fiap.alaganao.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fiap.alaganao.ui.formulario.FormularioScreen
import com.fiap.alaganao.ui.home.HomeScreen
import com.fiap.alaganao.ui.lista.ListaScreen

private val telasPrincipais = listOf(Screen.Home, Screen.Lista)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlagaNavHost() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route
    val ehTelaPrincipal = telasPrincipais.any { it.route == rotaAtual }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = tituloDaTela(rotaAtual, backStackEntry)) },
                navigationIcon = {
                    if (!ehTelaPrincipal && navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        bottomBar = {
            if (ehTelaPrincipal) {
                NavigationBar {
                    telasPrincipais.forEach { tela ->
                        NavigationBarItem(
                            selected = rotaAtual == tela.route,
                            onClick = {
                                if (rotaAtual != tela.route) {
                                    navController.navigate(tela.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = { Icon(iconeDaTela(tela), contentDescription = null) },
                            label = { Text(nomeDaTela(tela)) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNovoRelato = { navController.navigate(Screen.Formulario.createRoute()) },
                    onVerRelatos = {
                        navController.navigate(Screen.Lista.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Lista.route) {
                ListaScreen(
                    onNovoRelato = { navController.navigate(Screen.Formulario.createRoute()) },
                    onEditar = { id -> navController.navigate(Screen.Formulario.createRoute(id)) }
                )
            }
            composable(
                route = Screen.Formulario.route,
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                FormularioScreen(
                    onVoltar = { navController.popBackStack() }
                )
            }
        }
    }
}

private fun nomeDaTela(tela: Screen): String = when (tela) {
    Screen.Home -> "Início"
    Screen.Lista -> "Relatos"
    else -> ""
}

private fun iconeDaTela(tela: Screen) = when (tela) {
    Screen.Lista -> Icons.Filled.Warning
    else -> Icons.Filled.Home
}

private fun tituloDaTela(rota: String?, backStackEntry: NavBackStackEntry?): String = when (rota) {
    Screen.Home.route -> "Alaga Não"
    Screen.Lista.route -> "Relatos de alagamento"
    Screen.Formulario.route -> {
        val id = backStackEntry?.arguments?.getLong("id") ?: -1L
        if (id == -1L) "Novo relato" else "Editar relato"
    }
    else -> "Alaga Não"
}
