package com.doacoes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.usecase.GetInstituicoesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstituicoesViewModel @Inject constructor(
    private val getInstituicoesUseCase: GetInstituicoesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(InstituicoesUiState())
    val uiState: StateFlow<InstituicoesUiState> = _uiState.asStateFlow()
    
    init {
        carregarInstituicoes()
    }
    
    fun recarregar() {
        carregarInstituicoes()
    }
    
    private fun carregarInstituicoes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            getInstituicoesUseCase()
                .catch { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Erro desconhecido"
                    )
                }
                .collect { instituicoes ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        instituicoes = instituicoes,
                        error = null
                    )
                }
        }
    }
}

data class InstituicoesUiState(
    val isLoading: Boolean = false,
    val instituicoes: List<Instituicao> = emptyList(),
    val error: String? = null
)
