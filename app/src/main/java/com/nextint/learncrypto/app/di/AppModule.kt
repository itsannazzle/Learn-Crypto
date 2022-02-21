package com.nextint.learncrypto.app.di

import androidx.lifecycle.ViewModel
import com.nextint.learncrypto.app.features.coins.data.CoinsUseCase
import com.nextint.learncrypto.app.features.coins.data.CoinsUseCaseImpl
import com.nextint.learncrypto.app.features.exchanges.data.ExchangeUseCase
import com.nextint.learncrypto.app.features.exchanges.data.ExchangeUseCaseImpl
import com.nextint.learncrypto.app.features.market.data.MarketUseCase
import com.nextint.learncrypto.app.features.market.data.MarketUseCaseImpl
import com.nextint.learncrypto.app.features.market.viewmodel.MarketViewModel
import com.nextint.learncrypto.app.features.tags.data.TagsUseCase
import com.nextint.learncrypto.app.features.tags.data.TagsUseCaseImpl
import com.nextint.learncrypto.app.features.overview.data.OverviewUseCase
import com.nextint.learncrypto.app.features.overview.data.OverviewUseCaseImpl
import com.nextint.learncrypto.app.features.person.data.PeopleUseCase
import com.nextint.learncrypto.app.features.person.data.PeopleUseCaseImpl
import com.nextint.learncrypto.app.features.search.SearchUseCase
import com.nextint.learncrypto.app.features.search.SearchUseCaseImpl
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
}