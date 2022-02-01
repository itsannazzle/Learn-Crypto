package com.nextint.learncrypto.app.core.source.remote.di

import com.nextint.learncrypto.app.core.source.remote.service.*

interface CryptoAppDeps {
    fun coinsService() : CoinsService
    fun cryptoExcangeService() : CryptoExchangeService
    fun peopleService() : PeopleService
    fun  searchService() : SearchService
    fun vocabularyService() : TagsService
}