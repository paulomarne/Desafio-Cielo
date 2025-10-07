package com.doacoes.domain.usecase

import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.repository.InstituicaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case para obter lista de instituições
 * Implementa a lógica de negócio para buscar instituições disponíveis
 */
class GetInstituicoesUseCase @Inject constructor(
    private val repository: InstituicaoRepository
) {
    
    /**
     * Executa o caso de uso para obter instituições
     * @return Flow com lista de instituições ordenadas por nome
     */
    operator fun invoke(): Flow<List<Instituicao>> {
        return repository.getInstituicoes()
    }
}
