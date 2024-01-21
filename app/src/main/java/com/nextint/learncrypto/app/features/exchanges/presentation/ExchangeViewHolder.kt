package com.nextint.learncrypto.app.features.exchanges.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.ExchangesResponseItem
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.util.EnumConstants

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

    fun bind(exchangesResponseItem: ExchangesResponseItem, sourceView: EnumConstants.ENUM_SOURCE_VIEW)
    {
        with(itemView)
        {
            exchangesResponseItem.apply()
            {
                if (sourceView == EnumConstants.ENUM_SOURCE_VIEW.SEARCH)
                {
                    findViewById<TextView>(R.id.textViewRankValue).text = rank.toString()
                }
                else
                {
                    findViewById<TextView>(R.id.textViewRankValue).text = reportedRank.toString()
                }

                findViewById<TextView>(R.id.textViewExchangeName).text = name
                findViewById<TextView>(R.id.textViewExchangeMarketValue).text =
                    markets?.toString() ?: itemView.context.getString(R.string.dash)
                findViewById<TextView>(R.id.textViewExchangeConfidenceValue).text =
                    if (confidenceScore != null) UtilitiesFunction.convertToPercentage(confidenceScore) else itemView.context.getString(R.string.dash)
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