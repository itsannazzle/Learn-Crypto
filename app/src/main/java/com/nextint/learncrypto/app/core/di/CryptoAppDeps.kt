package com.nextint.learncrypto.app.core.di

import com.nextint.learncrypto.app.core.source.remote.service.*

interface CryptoAppDeps {
    fun coinsService() : CoinsService
    fun cryptoExchangeService() : CryptoExchangeService
    fun peopleService() : PeopleService
    fun searchService() : SearchService
    fun vocabularyService() : TagsService
}