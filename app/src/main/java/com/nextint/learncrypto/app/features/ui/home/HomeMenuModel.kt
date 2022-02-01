package com.nextint.learncrypto.app.features.ui.home

data class HomeMenuModel (
    var number : Int? = 0,
    var title : String? = ""
        )
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

object MenuData
{
    private val contentNumber = arrayOf(
        1, 2, 3, 4
    )

    private val contentTitle = arrayOf(
        "Concept","Exchange","Market","Coin")
}

