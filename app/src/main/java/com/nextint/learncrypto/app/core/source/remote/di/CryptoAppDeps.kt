package com.nextint.learncrypto.app.core.source.remote.di

import com.nextint.learncrypto.app.core.source.remote.*

interface CryptoAppDeps {
    fun coinsService() : Coins
    fun cryptoExcangeService() : CryptoExchange
    fun peopleService() : People
    fun  searchService() : Search
    fun vocabularyService() : Vocabulary
}