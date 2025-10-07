package com.doacoes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.model.ResultadoPagamento
import com.doacoes.domain.usecase.ProcessarDoacaoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class DoacaoViewModel @Inject constructor(
    private val processarDoacaoUseCase: ProcessarDoacaoUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DoacaoUiState())
    val uiState: StateFlow<DoacaoUiState> = _uiState.asStateFlow()
    
    fun setInstituicao(instituicao: Instituicao) {
        _uiState.value = _uiState.value.copy(instituicao = instituicao)
    }
    
    fun setValor(valor: BigDecimal) {
        _uiState.value = _uiState.value.copy(valor = valor, valorError = null)
    }
    
    fun processarDoacao() {
        val estado = _uiState.value
        val instituicao = estado.instituicao ?: return
        val valor = estado.valor ?: return
        
        viewModelScope.launch {
            processarDoacaoUseCase(instituicao, valor)
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        valorError = exception.message
                    )
                }
                .collect { resultado ->
                    _uiState.value = _uiState.value.copy(
                        resultadoPagamento = resultado
                    )
                }
        }
    }
    
    fun limparEstado() {
        _uiState.value = DoacaoUiState()
    }
}

data class DoacaoUiState(
    val instituicao: Instituicao? = null,
    val valor: BigDecimal? = null,
    val valorError: String? = null,
    val resultadoPagamento: ResultadoPagamento? = null
)
