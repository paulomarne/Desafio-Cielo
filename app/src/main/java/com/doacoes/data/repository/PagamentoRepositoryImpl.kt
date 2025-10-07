package com.doacoes.data.repository

import com.doacoes.data.datasource.CieloLioDataSource
import com.doacoes.domain.model.PagamentoRequest
import com.doacoes.domain.model.ResultadoPagamento
import com.doacoes.domain.repository.PagamentoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagamentoRepositoryImpl @Inject constructor(
    private val cieloLioDataSource: CieloLioDataSource
) : PagamentoRepository {
    
    override fun processarPagamento(request: PagamentoRequest): Flow<ResultadoPagamento> {
        return cieloLioDataSource.processarPagamento(request)
    }
}
