package com.doacoes.domain.usecase

import com.doacoes.domain.model.CategoriaInstituicao
import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.repository.InstituicaoRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetInstituicoesUseCaseTest {
    
    @Mock
    private lateinit var repository: InstituicaoRepository
    
    private lateinit var useCase: GetInstituicoesUseCase
    
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = GetInstituicoesUseCase(repository)
    }
    
    @Test
    fun `quando repository retorna lista de instituicoes, deve retornar a mesma lista`() = runTest {
        // Given
        val instituicoesEsperadas = listOf(
            Instituicao(
                id = "1",
                nome = "Casa de Apoio",
                descricao = "Descrição",
                categoria = CategoriaInstituicao.CRIANCAS_ADOLESCENTES,
                imagemUrl = "url",
                cnpj = "12.345.678/0001-90",
                telefone = "(11) 1234-5678",
                email = "contato@casa.org.br",
                website = "www.casa.org.br"
            )
        )
        
        `when`(repository.getInstituicoes()).thenReturn(flowOf(instituicoesEsperadas))
        
        // When
        val resultado = useCase().toList()
        
        // Then
        assertEquals(1, resultado.size)
        assertEquals(instituicoesEsperadas, resultado[0])
    }
}
