package com.doacoes.domain.model

import java.math.BigDecimal
import java.util.Date

/**
 * Entidade de domínio representando uma doação
 */
data class Doacao(
    val id: String,
    val instituicao: Instituicao,
    val valor: BigDecimal,
    val dataHora: Date,
    val status: StatusDoacao,
    val transacaoId: String?,
    val comprovante: String?
)

enum class StatusDoacao {
    PENDENTE,
    PROCESSANDO,
    CONFIRMADA,
    CANCELADA,
    ERRO
}
