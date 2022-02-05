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
import com.nextint.learncrypto.app.util.STRING_URL_AVATAR_APE

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
            findViewById<ImageView>(R.id.imageViewTeam).circleImage(STRING_URL_AVATAR_APE)
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