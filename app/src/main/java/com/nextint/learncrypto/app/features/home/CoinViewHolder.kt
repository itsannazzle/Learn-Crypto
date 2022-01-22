package com.nextint.learncrypto.app.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.CoinsResponseItem

class CoinViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun inflate(parent  : ViewGroup) : CoinViewHolder {
            return CoinViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_coins,parent,false)
            )
        }
    }

    fun bind(coinsResponseItem: CoinsResponseItem){
        with(itemView){
            findViewById<TextView>(R.id.coin_name).text = coinsResponseItem.name
            findViewById<TextView>(R.id.coin_rank).text = coinsResponseItem.rank.toString()
            findViewById<TextView>(R.id.coin_symbol).text = coinsResponseItem.symbol
            findViewById<TextView>(R.id.coin_type).text = coinsResponseItem.type
            findViewById<CardView>(R.id.status_new).findViewById<TextView>(R.id.textViewStatus).text = if (coinsResponseItem.isNew) "new" else null
            findViewById<CardView>(R.id.status_active).findViewById<TextView>(R.id.textViewStatus).text = if (coinsResponseItem.isActive) "active" else null
        }
    }

    fun setCoinAction(action : (position : Int) -> Unit){
        itemView.setOnClickListener {
            action.invoke(adapterPosition)
        }

    }
}