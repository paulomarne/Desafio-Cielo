package com.doacoes.domain.model

import java.math.BigDecimal

/**
 * Entidade de domínio representando um pagamento via Cielo Lio
 */
data class PagamentoRequest(
    val valor: BigDecimal,
    val descricao: String,
    val referencia: String
)

data class PagamentoResponse(
    val sucesso: Boolean,
    val transacaoId: String?,
    val mensagem: String,
    val comprovante: String?
)

sealed class ResultadoPagamento {
    object Processando : ResultadoPagamento()
    data class Sucesso(val transacaoId: String, val comprovante: String) : ResultadoPagamento()
    data class Erro(val mensagem: String) : ResultadoPagamento()
}
