package com.fiap.alaganao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fiap.alaganao.ui.navigation.AlagaNavHost
import com.fiap.alaganao.ui.theme.AlagaNaoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AlagaNaoTheme {
                AlagaNavHost()
            }
        }
    }
}
