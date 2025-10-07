package com.doacoes.domain.repository

import com.doacoes.domain.model.PagamentoRequest
import com.doacoes.domain.model.ResultadoPagamento
import kotlinx.coroutines.flow.Flow

/**
 * Interface do repositório de pagamentos
 * Define o contrato para integração com Cielo Lio
 */
interface PagamentoRepository {
    
    /**
     * Processa um pagamento via Cielo Lio
     * @param request Dados do pagamento
     * @return Flow com o resultado do pagamento
     */
    fun processarPagamento(request: PagamentoRequest): Flow<ResultadoPagamento>
}
