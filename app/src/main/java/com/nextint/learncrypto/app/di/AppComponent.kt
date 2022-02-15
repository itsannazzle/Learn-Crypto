package com.nextint.learncrypto.app.di

import com.nextint.learncrypto.app.core.di.CoreComponent
import com.nextint.learncrypto.app.features.ui.dialog.BottomSheetDialog
import com.nextint.learncrypto.app.features.ui.concept.ConceptFragment
import com.nextint.learncrypto.app.features.ui.coins.CoinsFragment
import com.nextint.learncrypto.app.features.ui.coins.CoinDetailFragment
import com.nextint.learncrypto.app.features.ui.home.HomeFragment
import com.nextint.learncrypto.app.features.ui.people.PeopleFragment
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
}