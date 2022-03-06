package com.nextint.learncrypto.app.features.search.data

import com.nextint.learncrypto.app.core.source.remote.network.SearchNetwork
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val searchNetwork: SearchNetwork) :
    ISearchRepository
{

}

interface ISearchRepository
{

}