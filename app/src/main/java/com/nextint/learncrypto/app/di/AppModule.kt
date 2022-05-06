package com.nextint.learncrypto.app.di

import androidx.lifecycle.ViewModel
import com.nextint.learncrypto.app.features.coins.domain.CoinsUseCase
import com.nextint.learncrypto.app.features.coins.domain.CoinsUseCaseImpl
import com.nextint.learncrypto.app.features.coins.presentation.CoinsViewModel
import com.nextint.learncrypto.app.features.concept.domain.TagsUseCase
import com.nextint.learncrypto.app.features.concept.domain.TagsUseCaseImpl
import com.nextint.learncrypto.app.features.concept.presentation.TagsViewModel
import com.nextint.learncrypto.app.features.exchanges.data.ExchangeUseCase
import com.nextint.learncrypto.app.features.exchanges.data.ExchangeUseCaseImpl
import com.nextint.learncrypto.app.features.exchanges.presentation.ExchangeViewModel
import com.nextint.learncrypto.app.features.market.domain.MarketUseCase
import com.nextint.learncrypto.app.features.market.domain.MarketUseCaseImpl
import com.nextint.learncrypto.app.features.market.presentation.MarketViewModel
import com.nextint.learncrypto.app.features.overview.domain.OverviewUseCase
import com.nextint.learncrypto.app.features.overview.domain.OverviewUseCaseImpl
import com.nextint.learncrypto.app.features.overview.presentation.OverviewViewModel
import com.nextint.learncrypto.app.features.person.domain.PeopleUseCase
import com.nextint.learncrypto.app.features.person.domain.PeopleUseCaseImpl
import com.nextint.learncrypto.app.features.person.presentation.PeopleViewModel
import com.nextint.learncrypto.app.features.search.domain.SearchUseCase
import com.nextint.learncrypto.app.features.search.domain.SearchUseCaseImpl
import com.nextint.learncrypto.app.features.search.presentation.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppModule {
    @Binds
    abstract fun provideCoinsUseCase(coinsUseCaseImpl: CoinsUseCaseImpl) : CoinsUseCase

    @Binds
    abstract fun provideTagsUseCase(tagsUseCaseImpl: TagsUseCaseImpl) : TagsUseCase

    @Binds
    abstract fun provideSearchUseCase(searchUseCaseImpl: SearchUseCaseImpl) : SearchUseCase

    @Binds
    abstract fun provideOverviewUseCase(overviewUseCaseImpl: OverviewUseCaseImpl) : OverviewUseCase

    @Binds
    abstract fun providePeopleUseCase(peopleUseCaseImpl: PeopleUseCaseImpl) : PeopleUseCase

    @Binds
    abstract fun provideExchangeUseCase(exchangeUseCaseImpl: ExchangeUseCaseImpl) : ExchangeUseCase

    @Binds
    abstract fun provideMarketUseCase(marketUseCaseImpl: MarketUseCaseImpl) : MarketUseCase

    @Binds
    @IntoMap
    @ViewModelKey(MarketViewModel::class)
    abstract fun bindMarketViewModel(viewModel: MarketViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoinsViewModel::class)
    abstract fun bindCoinsViewModel(viewModel: CoinsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExchangeViewModel::class)
    abstract fun bindExchangeViewModel(viewModel: ExchangeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TagsViewModel::class)
    abstract fun bindTagsViewModel(viewModel: TagsViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeopleViewModel::class)
    abstract fun bindPeopleViewModel(viewModel: PeopleViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OverviewViewModel::class)
    abstract fun bindOverviewViewModel(viewModel: OverviewViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel) : ViewModel
}