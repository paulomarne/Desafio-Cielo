package com.doacoes.domain.usecase

import com.doacoes.domain.model.CategoriaInstituicao
import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.model.ResultadoPagamento
import com.doacoes.domain.repository.PagamentoRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import java.math.BigDecimal

class ProcessarDoacaoUseCaseTest {
    
    @Mock
    private lateinit var pagamentoRepository: PagamentoRepository
    
    private lateinit var useCase: ProcessarDoacaoUseCase
    
    private val instituicaoTeste = Instituicao(
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
    
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = ProcessarDoacaoUseCase(pagamentoRepository)
    }
    
    @Test
    fun `quando valor e valido, deve processar doacao com sucesso`() = runTest {
        // Given
        val valor = BigDecimal("25.00")
        val resultadoEsperado = ResultadoPagamento.Sucesso("TXN123", "Comprovante")
        
        `when`(pagamentoRepository.processarPagamento(any()))
            .thenReturn(flowOf(resultadoEsperado))
        
        // When
        val resultado = useCase(instituicaoTeste, valor).toList()
        
        // Then
        assertEquals(1, resultado.size)
        assertEquals(resultadoEsperado, resultado[0])
    }
    
    @Test(expected = IllegalArgumentException::class)
    fun `quando valor e menor que minimo, deve lancar excecao`() = runTest {
        // Given
        val valorInvalido = BigDecimal("3.00")
        
        // When/Then
        useCase(instituicaoTeste, valorInvalido).toList()
    }
}
