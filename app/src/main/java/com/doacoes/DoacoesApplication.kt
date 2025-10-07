package com.doacoes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe Application configurada com Hilt
 * Ponto de entrada da aplicação para injeção de dependências
 */
@HiltAndroidApp
class DoacoesApplication : Application()
