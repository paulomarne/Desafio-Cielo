package com.doacoes.data.datasource

import com.doacoes.domain.model.CategoriaInstituicao
import com.doacoes.domain.model.Instituicao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source mock para instituições
 * Simula uma API REST com dados de instituições beneficentes
 */
@Singleton
class InstituicaoDataSource @Inject constructor() {
    
    private val instituicoesMock = listOf(
        Instituicao(
            id = "1",
            nome = "Casa de Apoio à Criança Carente",
            descricao = "Instituição dedicada ao cuidado e educação de crianças em situação de vulnerabilidade social.",
            categoria = CategoriaInstituicao.CRIANCAS_ADOLESCENTES,
            imagemUrl = "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?w=400&h=300&fit=crop",
            cnpj = "12.345.678/0001-90",
            telefone = "(11) 1234-5678",
            email = "contato@casaapoio.org.br",
            website = "www.casaapoio.org.br"
        ),
        Instituicao(
            id = "2",
            nome = "Lar dos Idosos São Vicente",
            descricao = "Casa de repouso que oferece cuidado especializado para idosos.",
            categoria = CategoriaInstituicao.IDOSOS,
            imagemUrl = "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=400&h=300&fit=crop",
            cnpj = "23.456.789/0001-01",
            telefone = "(11) 2345-6789",
            email = "contato@laridosos.org.br",
            website = "www.laridosos.org.br"
        ),
        Instituicao(
            id = "3",
            nome = "ONG Proteção Animal",
            descricao = "Organização focada no resgate e adoção responsável de animais abandonados.",
            categoria = CategoriaInstituicao.ANIMAIS,
            imagemUrl = "https://images.unsplash.com/photo-1601758228041-f3b2795255f1?w=400&h=300&fit=crop",
            cnpj = "34.567.890/0001-12",
            telefone = "(11) 3456-7890",
            email = "contato@protecaoanimal.org.br",
            website = "www.protecaoanimal.org.br"
        )
    )
    
    /**
     * Simula busca de instituições com delay de rede
     */
    fun getInstituicoes(): Flow<List<Instituicao>> = flow {
        delay(1000) // Simula latência de rede
        emit(instituicoesMock.sortedBy { it.nome })
    }
    
    /**
     * Busca instituição por ID
     */
    suspend fun getInstituicaoById(id: String): Instituicao? {
        delay(500) // Simula latência de rede
        return instituicoesMock.find { it.id == id }
    }
}
