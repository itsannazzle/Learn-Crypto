package com.nextint.learncrypto.app.features.market.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.MarketsByCoinIdResponseItem
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction

class MarketViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun inflate(parent: ViewGroup) : MarketViewHolder {
            return MarketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_market_crypto,parent,false))
        }

        val differCallback = object : DiffUtil.ItemCallback<MarketsByCoinIdResponseItem>(){
            override fun areItemsTheSame(oldItem: MarketsByCoinIdResponseItem, newItem: MarketsByCoinIdResponseItem): Boolean {
                return oldItem.exchangeId == newItem.exchangeId
            }

            override fun areContentsTheSame(oldItem: MarketsByCoinIdResponseItem, newItem: MarketsByCoinIdResponseItem): Boolean {
                return oldItem == newItem
            }
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
                findViewById<TextView>(R.id.textViewFeeTypeValue).text = feeType
                val stringPrice = quotes.baseKeyPrice.price.toString()
                if (stringPrice.first() == '0')
                {
                    findViewById<TextView>(R.id.textViewMarketPriceValue).text = context.getString(R.string.dollar,stringPrice.take(5))

                } else
                {
                    findViewById<TextView>(R.id.textViewMarketPriceValue).text = UtilitiesFunction.convertToUSD(quotes.baseKeyPrice.price.toLong()).take(8)
                }

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