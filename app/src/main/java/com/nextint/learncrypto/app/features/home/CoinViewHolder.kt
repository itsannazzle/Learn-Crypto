package com.nextint.learncrypto.app.features.home

import android.graphics.Color
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
                findViewById<TextView>(R.id.label_coin_type).text = resources.getString(R.string.type, coinsResponseItem.type)
            if (coinsResponseItem.isNew) {
                findViewById<CardView>(R.id.status_new).findViewById<TextView>(R.id.textViewStatus).text = "New"
                findViewById<CardView>(R.id.status_new).setCardBackgroundColor(resources.getColor(R.color.teal_700))
            } else {
                findViewById<CardView>(R.id.status_new).findViewById<TextView>(R.id.textViewStatus).visibility = View.GONE
            }
            findViewById<CardView>(R.id.status_active).findViewById<TextView>(R.id.textViewStatus).text = if (coinsResponseItem.isActive) "active" else "inactive"
        }
    }

    fun setCoinAction(action : (position : Int) -> Unit){
        itemView.setOnClickListener {
            action.invoke(adapterPosition)
        }

    }
}