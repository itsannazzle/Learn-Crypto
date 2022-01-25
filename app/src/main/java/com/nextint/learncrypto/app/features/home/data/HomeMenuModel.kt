package com.nextint.learncrypto.app.features.home.data

data class HomeMenuModel (
    val number : Int? = 0,
    val title : String? = ""
        )

object dummy_data
{
    fun generateHomeMenu() : ArrayList<HomeMenuModel>
    {
        val menuModel = ArrayList<HomeMenuModel>()
        menuModel.add(
            HomeMenuModel(1, "Concept")
        )
        menuModel.add(HomeMenuModel(2,"Exchange"))
        menuModel.add(HomeMenuModel(3,"Market"))
        menuModel.add(HomeMenuModel(4,"Coin"))
        return menuModel
    }
}