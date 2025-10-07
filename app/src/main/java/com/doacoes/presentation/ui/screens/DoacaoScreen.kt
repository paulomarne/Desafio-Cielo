package com.doacoes.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.model.ResultadoPagamento
import com.doacoes.presentation.viewmodel.DoacaoViewModel
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoacaoScreen(
    instituicao: Instituicao,
    onVoltar: () -> Unit,
    onDoacaoConfirmada: () -> Unit,
    viewModel: DoacaoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(instituicao) {
        viewModel.setInstituicao(instituicao)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fazer Doação") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Informações da instituição
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = instituicao.nome,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = instituicao.descricao,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Seleção de valor
            Text(
                text = "Escolha o valor da doação:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            
            ValorSelector(
                valorSelecionado = uiState.valor,
                onValorChange = { viewModel.setValor(it) },
                error = uiState.valorError
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Resultado do pagamento
            when (val resultado = uiState.resultadoPagamento) {
                is ResultadoPagamento.Processando -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Processando pagamento...")
                        }
                    }
                }
                
                is ResultadoPagamento.Sucesso -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "✅ Doação realizada com sucesso!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Transação: ${resultado.transacaoId}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onDoacaoConfirmada,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Finalizar")
                            }
                        }
                    }
                }
                
                is ResultadoPagamento.Erro -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "❌ Erro no pagamento",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = resultado.mensagem,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.processarDoacao() },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Tentar Novamente")
                            }
                        }
                    }
                }
                
                null -> {
                    Button(
                        onClick = { viewModel.processarDoacao() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = uiState.valor != null && uiState.valor!! > BigDecimal.ZERO
                    ) {
                        Text("Doar Agora")
                    }
                }
            }
        }
    }
}

@Composable
fun ValorSelector(
    valorSelecionado: BigDecimal?,
    onValorChange: (BigDecimal) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    val valoresPredefinidos = listOf(
        BigDecimal("10.00"),
        BigDecimal("25.00"),
        BigDecimal("50.00"),
        BigDecimal("100.00")
    )
    
    var valorPersonalizado by remember { mutableStateOf("") }
    var usandoValorPersonalizado by remember { mutableStateOf(false) }
    
    Column(modifier = modifier) {
        // Valores predefinidos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            valoresPredefinidos.forEach { valor ->
                FilterChip(
                    onClick = {
                        onValorChange(valor)
                        usandoValorPersonalizado = false
                        valorPersonalizado = ""
                    },
                    label = { Text("R$ ${String.format("%.0f", valor)}") },
                    selected = valorSelecionado == valor && !usandoValorPersonalizado,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Valor personalizado
        OutlinedTextField(
            value = valorPersonalizado,
            onValueChange = { novoValor ->
                valorPersonalizado = novoValor
                usandoValorPersonalizado = true
                
                val valor = novoValor.replace(",", ".").toBigDecimalOrNull()
                if (valor != null && valor > BigDecimal.ZERO) {
                    onValorChange(valor)
                }
            },
            label = { Text("Valor personalizado") },
            placeholder = { Text("Ex: 15,00") },
            prefix = { Text("R$ ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            isError = error != null,
            supportingText = if (error != null) {
                { Text(error, color = MaterialTheme.colorScheme.error) }
            } else null
        )
    }
}
