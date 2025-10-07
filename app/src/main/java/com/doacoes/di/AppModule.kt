package com.doacoes.di

import com.doacoes.data.repository.InstituicaoRepositoryImpl
import com.doacoes.data.repository.PagamentoRepositoryImpl
import com.doacoes.domain.repository.InstituicaoRepository
import com.doacoes.domain.repository.PagamentoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    
    @Binds
    @Singleton
    abstract fun bindInstituicaoRepository(
        instituicaoRepositoryImpl: InstituicaoRepositoryImpl
    ): InstituicaoRepository
    
    @Binds
    @Singleton
    abstract fun bindPagamentoRepository(
        pagamentoRepositoryImpl: PagamentoRepositoryImpl
    ): PagamentoRepository
}
