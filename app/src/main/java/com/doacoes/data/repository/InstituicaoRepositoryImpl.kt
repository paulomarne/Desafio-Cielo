package com.doacoes.data.repository

import com.doacoes.data.datasource.InstituicaoDataSource
import com.doacoes.domain.model.Instituicao
import com.doacoes.domain.repository.InstituicaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InstituicaoRepositoryImpl @Inject constructor(
    private val dataSource: InstituicaoDataSource
) : InstituicaoRepository {
    
    override fun getInstituicoes(): Flow<List<Instituicao>> {
        return dataSource.getInstituicoes()
    }
    
    override suspend fun getInstituicaoById(id: String): Instituicao? {
        return dataSource.getInstituicaoById(id)
    }
}
