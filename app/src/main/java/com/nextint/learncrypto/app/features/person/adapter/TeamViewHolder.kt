package com.nextint.learncrypto.app.features.person.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.TeamItem
import com.nextint.learncrypto.app.features.utils.circleImage

class TeamViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    companion object
    {
        fun inflate(parent : ViewGroup) : TeamViewHolder
        {
            return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_team_layout, parent,false))
        }
    }

    fun bind(teamItem: TeamItem)
    {
        with(itemView)
        {
            findViewById<TextView>(R.id.textViewTeamName).text = teamItem.name
            findViewById<TextView>(R.id.textViewTeamRole).text = teamItem.position
            findViewById<ImageView>(R.id.imageViewTeam).circleImage("https://images.unsplash.com/photo-1570288685280-7802a8f8c4fa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1064&q=80")
        }
    }

    fun setTeamAction(action : (position : Int) -> Unit)
    {
        itemView.setOnClickListener()
        {
            action.invoke(adapterPosition)
        }
    }
}