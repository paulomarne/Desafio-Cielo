package com.doacoes.data.datasource

import com.doacoes.domain.model.PagamentoRequest
import com.doacoes.domain.model.ResultadoPagamento
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

/**
 * Data source mock para integração com Cielo Lio
 * Simula o comportamento da API de pagamentos da Cielo
 */
@Singleton
class CieloLioDataSource @Inject constructor() {
    
    /**
     * Simula processamento de pagamento via Cielo Lio
     */
    fun processarPagamento(request: PagamentoRequest): Flow<ResultadoPagamento> = flow {
        // Emite estado de processamento
        emit(ResultadoPagamento.Processando)
        
        // Simula tempo de processamento (2-3 segundos)
        delay(2000 + Random.nextLong(1000))
        
        // Simula taxa de sucesso de 90%
        val sucesso = Random.nextFloat() < 0.9f
        
        if (sucesso) {
            val transacaoId = "TXN_${System.currentTimeMillis()}"
            val comprovante = gerarComprovante(request, transacaoId)
            emit(ResultadoPagamento.Sucesso(transacaoId, comprovante))
        } else {
            val mensagensErro = listOf(
                "Cartão recusado pela operadora",
                "Saldo insuficiente",
                "Erro de comunicação com o banco"
            )
            emit(ResultadoPagamento.Erro(mensagensErro.random()))
        }
    }
    
    private fun gerarComprovante(request: PagamentoRequest, transacaoId: String): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val agora = dateFormat.format(Date())
        
        return """
            COMPROVANTE DE DOAÇÃO
            ═══════════════════════
            Transação: $transacaoId
            Data/Hora: $agora
            Descrição: ${request.descricao}
            Valor: R$ ${String.format("%.2f", request.valor)}
            Status: APROVADA
            ═══════════════════════
            Obrigado por sua doação!
        """.trimIndent()
    }
}
