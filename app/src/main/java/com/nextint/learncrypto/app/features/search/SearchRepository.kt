package com.nextint.learncrypto.app.features.search

import com.nextint.learncrypto.app.core.source.remote.networksource.SearchNetwork
import com.nextint.learncrypto.app.core.source.remote.response.MarketOverviewResponse
import com.nextint.learncrypto.app.core.source.remote.service.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val searchNetwork: SearchNetwork) : ISearchRepository
{

}