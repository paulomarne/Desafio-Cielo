package com.doacoes.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doacoes.domain.model.Instituicao
import com.doacoes.presentation.ui.components.InstituicaoCard
import com.doacoes.presentation.viewmodel.InstituicoesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituicoesScreen(
    onInstituicaoClick: (Instituicao) -> Unit,
    viewModel: InstituicoesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Escolha uma Instituição",
                        fontWeight = FontWeight.Bold
                    ) 
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Erro: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.recarregar() }) {
                            Text("Tentar Novamente")
                        }
                    }
                }
                
                uiState.instituicoes.isEmpty() -> {
                    Text(
                        text = "Nenhuma instituição encontrada",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.instituicoes) { instituicao ->
                            InstituicaoCard(
                                instituicao = instituicao,
                                onClick = { onInstituicaoClick(instituicao) }
                            )
                        }
                    }
                }
            }
        }
    }
}
