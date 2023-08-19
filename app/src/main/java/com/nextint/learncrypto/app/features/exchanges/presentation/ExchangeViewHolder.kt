package com.nextint.learncrypto.app.features.exchanges.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction

class ExchangeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    companion object
    {
        fun inflate(parent : ViewGroup) : ExchangeViewHolder
        {
            return ExchangeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exchanges_layout,parent,false))
        }
        val differCallback = object : DiffUtil.ItemCallback<ExchangesResponseItem>(){
            override fun areItemsTheSame(oldItem: ExchangesResponseItem, newItem: ExchangesResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ExchangesResponseItem, newItem: ExchangesResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun bind(exchangesResponseItem: ExchangesResponseItem)
    {
        with(itemView)
        {
            exchangesResponseItem.apply()
            {
                findViewById<TextView>(R.id.textViewExchangeName).text = name
                findViewById<TextView>(R.id.textViewVol24hValue).text = UtilitiesFunction.convertToUSD(quotes?.adjustedVolume24h?.toLong() ?: 0)
                findViewById<TextView>(R.id.textViewExchangeMarketValue).text = markets.toString()
                findViewById<TextView>(R.id.textViewExchangeConfidenceValue).text = UtilitiesFunction.convertToPercentage(confidenceScore ?: 0.0).take(3)
            }

        }
    }

    fun setAction(action : (position : Int) -> Unit)
    {
        itemView.setOnClickListener()
        {
            action.invoke(adapterPosition)
        }
    }
}