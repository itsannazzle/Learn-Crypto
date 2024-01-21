package com.nextint.learncrypto.app.features.coins.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem
import com.nextint.learncrypto.app.features.utils.UtilitiesFunction
import com.nextint.learncrypto.app.util.EnumConstants

class CoinViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    companion object
    {
        fun inflate(parent  : ViewGroup) : CoinViewHolder
        {
            return CoinViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_coins_layout,parent,false)
            )
        }

        val differCallback = object : DiffUtil.ItemCallback<CoinsResponseItem>(){
            override fun areItemsTheSame(oldItem: CoinsResponseItem, newItem: CoinsResponseItem): Boolean
            {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CoinsResponseItem, newItem: CoinsResponseItem): Boolean
            {
                return oldItem == newItem
            }
        }
    }

    fun bind(coinsResponseItem: CoinsResponseItem, coinView: EnumConstants.ENUM_SOURCE_VIEW)
    {
        with(itemView)
        {
            coinsResponseItem.apply()
            {
                if (coinView == EnumConstants.ENUM_SOURCE_VIEW.SEARCH)
                {
                    findViewById<TextView>(R.id.coin_name).setEms(5)
                    findViewById<TextView>(R.id.coin_name).text = name
                }
                else
                {
                    findViewById<TextView>(R.id.coin_name).text = name
                }

                findViewById<TextView>(R.id.coin_rank).text = rank.toString()
                findViewById<TextView>(R.id.coin_symbol).text = symbol
                findViewById<TextView>(R.id.label_coin_type).text = resources.getString(R.string.type, type)
                findViewById<CardView>(R.id.status_active).findViewById<TextView>(R.id.textViewStatus).text = resources.getString(UtilitiesFunction.convertBooleanToActiveOrNotActive(isActive))
                if (isNew)
                {
                    findViewById<CardView>(R.id.status_new).findViewById<TextView>(R.id.textViewStatus).text = resources.getString(UtilitiesFunction.convertBooleanToNew(isNew))
                    findViewById<CardView>(R.id.status_new).setCardBackgroundColor(resources.getColor(R.color.teal_700))
                }
                else
                {
                    findViewById<CardView>(R.id.status_new).findViewById<TextView>(R.id.textViewStatus).visibility = View.GONE
                }
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