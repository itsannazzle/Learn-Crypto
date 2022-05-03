package com.nextint.learncrypto.app.di

import com.nextint.learncrypto.app.core.di.CoreComponent
import com.nextint.learncrypto.app.features.coins.CoinDetailFragment
import com.nextint.learncrypto.app.features.coins.CoinsFragment
import com.nextint.learncrypto.app.features.concept.ConceptFragment
import com.nextint.learncrypto.app.features.exchanges.ExchangeDetailFragment
import com.nextint.learncrypto.app.features.exchanges.ExchangesFragment
import com.nextint.learncrypto.app.features.market.MarketDetailFragment
import com.nextint.learncrypto.app.features.market.MarketFragment
import com.nextint.learncrypto.app.features.overview.HomeFragment
import com.nextint.learncrypto.app.features.person.PeopleFragment
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class]
)
interface AppComponent
{

    @Component.Factory
    interface Factory
    {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: HomeFragment)

    fun inject(fragment : ConceptFragment)

    fun inject(fragment : CoinsFragment)

    fun inject(fragment : CoinDetailFragment)

    fun inject(fragment : BottomSheetDialog)

    fun inject(fragment : PeopleFragment)

    fun inject(fragment : ExchangesFragment)

    fun inject(fragment : ExchangeDetailFragment)

    fun inject(fragment : MarketFragment)

    fun inject(fragment : MarketDetailFragment)
}