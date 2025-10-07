package com.doacoes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Entidade de domínio representando uma instituição beneficente
 */
@Parcelize
data class Instituicao(
    val id: String,
    val nome: String,
    val descricao: String,
    val categoria: CategoriaInstituicao,
    val imagemUrl: String,
    val cnpj: String,
    val telefone: String?,
    val email: String?,
    val website: String?
) : Parcelable

enum class CategoriaInstituicao {
    CRIANCAS_ADOLESCENTES,
    IDOSOS,
    ANIMAIS,
    MEIO_AMBIENTE,
    SAUDE,
    EDUCACAO,
    ASSISTENCIA_SOCIAL
}
