package com.doacoes.domain.repository

import com.doacoes.domain.model.Instituicao
import kotlinx.coroutines.flow.Flow

/**
 * Interface do repositório de instituições seguindo o padrão Repository
 * Define o contrato para acesso aos dados de instituições
 */
interface InstituicaoRepository {
    
    /**
     * Obtém todas as instituições disponíveis
     * @return Flow com lista de instituições
     */
    fun getInstituicoes(): Flow<List<Instituicao>>
    
    /**
     * Obtém uma instituição específica pelo ID
     * @param id ID da instituição
     * @return Instituição encontrada ou null
     */
    suspend fun getInstituicaoById(id: String): Instituicao?
}
