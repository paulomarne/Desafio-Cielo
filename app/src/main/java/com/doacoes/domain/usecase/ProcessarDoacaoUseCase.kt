package com.doacoes.domain.usecase

import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.model.PagamentoRequest
import com.doacoes.domain.model.ResultadoPagamento
import com.doacoes.domain.repository.PagamentoRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Use case para processar uma doação
 * Implementa as regras de negócio para validação e processamento de doações
 */
class ProcessarDoacaoUseCase @Inject constructor(
    private val pagamentoRepository: PagamentoRepository
) {
    
    companion object {
        val VALOR_MINIMO = BigDecimal("5.00")
    }
    
    /**
     * Processa uma doação para uma instituição
     * @param instituicao Instituição beneficiária
     * @param valor Valor da doação
     * @return Flow com resultado do processamento
     * @throws IllegalArgumentException se valor for inválido
     */
    operator fun invoke(
        instituicao: Instituicao,
        valor: BigDecimal
    ): Flow<ResultadoPagamento> {
        
        // Validação do valor mínimo
        if (valor < VALOR_MINIMO) {
            throw IllegalArgumentException("Valor mínimo para doação é R$ ${VALOR_MINIMO}")
        }
        
        // Validação de valor positivo
        if (valor <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Valor deve ser maior que zero")
        }
        
        // Criar request de pagamento
        val request = PagamentoRequest(
            valor = valor,
            descricao = "Doação para ${instituicao.nome}",
            referencia = "DOA_${System.currentTimeMillis()}"
        )
        
        return pagamentoRepository.processarPagamento(request)
    }
}
