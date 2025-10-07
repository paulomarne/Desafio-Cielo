package com.doacoes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.doacoes.presentation.ui.screens.InstituicoesScreen
import com.doacoes.presentation.ui.screens.DoacaoScreen
import com.doacoes.presentation.ui.theme.DoacoesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoacoesAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    NavHost(
                        navController = navController,
                        startDestination = "instituicoes"
                    ) {
                        composable("instituicoes") {
                            InstituicoesScreen(
                                onInstituicaoClick = { instituicao ->
                                    navController.currentBackStackEntry?.savedStateHandle?.set("instituicao", instituicao)
                                    navController.navigate("doacao")
                                }
                            )
                        }
                        
                        composable("doacao") {
                            val instituicao = navController.previousBackStackEntry?.savedStateHandle?.get<com.doacoes.domain.model.Instituicao>("instituicao")
                            if (instituicao != null) {
                                DoacaoScreen(
                                    instituicao = instituicao,
                                    onVoltar = { navController.popBackStack() },
                                    onDoacaoConfirmada = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
