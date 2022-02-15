package com.nextint.learncrypto.app.features.exchanges.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.features.utils.convertToPercentage
import com.nextint.learncrypto.app.features.utils.convertToUSD

class ExchangeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun inflate(parent : ViewGroup) : ExchangeViewHolder {
            return ExchangeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exchanges_layout,parent,false))
        }
    }

    fun bind(exchangesResponseItem: ExchangesResponseItem)
    {
        with(itemView){
            exchangesResponseItem.apply {
                findViewById<TextView>(R.id.textViewExchangeName).text = name
                findViewById<TextView>(R.id.textViewVol24hValue).text = convertToUSD(quotes.adjustedVolume24h.toLong())
                findViewById<TextView>(R.id.textViewExchangeMarketValue).text = markets.toString()
                findViewById<TextView>(R.id.textViewExchangeConfidenceValue).text = convertToPercentage(confidenceScore.toDouble())
            }

        }
    }

    fun setAction(action : (position : Int) -> Unit) {
        itemView.setOnClickListener {
            action.invoke(adapterPosition)
        }
    }
}