package com.nextint.learncrypto.app.features.search.domain

import com.nextint.learncrypto.app.features.search.data.ISearchRepository
import javax.inject.Inject

interface SearchUseCase
{

}



class SearchUseCaseImpl @Inject constructor(private val iSearchRepository: ISearchRepository) :
    SearchUseCase
{

}
