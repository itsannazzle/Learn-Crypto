package com.nextint.learncrypto.app.features.market.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction

class MarketViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun inflate(parent: ViewGroup) : MarketViewHolder {
            return MarketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_market_crypto,parent,false))
        }
    }

    fun bind(marketsByCoinIdResponseItem: MarketsByCoinIdResponseItem)
    {
        with(itemView)
        {
            marketsByCoinIdResponseItem.apply {
                findViewById<TextView>(R.id.textViewPair).text = pair
                findViewById<TextView>(R.id.textViewTrustScoreValue).text = trustScore.uppercase()
                findViewById<TextView>(R.id.textViewMarketExchangeValue).text = exchangeName
                findViewById<TextView>(R.id.textViewMarket24hVolValue).text = UtilitiesFunction.convertToUSD(quotes.baseKeyPrice.volume24h.toLong()).take(6)
                findViewById<TextView>(R.id.textViewMarketPriceValue).text = UtilitiesFunction.convertToUSD(quotes.baseKeyPrice.price.toLong()).take(6)
            }
        }
    }

    fun setAction(action : (position : Int) -> Unit)
    {
        itemView.setOnClickListener {
            action.invoke(adapterPosition)
        }
    }
}