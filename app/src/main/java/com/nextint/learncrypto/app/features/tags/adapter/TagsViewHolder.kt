package com.nextint.learncrypto.app.features.tags.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nextint.learncrypto.app.R
import com.nextint.learncrypto.app.core.source.remote.response.TagByIdResponse

class TagsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    companion object
    {
        fun inflate(parent : ViewGroup) : TagsViewHolder
        {
            return TagsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tags_layout,parent,false))
        }
    }

    fun bind(tagsItem: TagByIdResponse)
    {
        with(itemView)
        {
            findViewById<TextView>(R.id.textViewTagValue).text = tagsItem.name
        }
    }

    fun setTagAction(action : (position : Int) -> Unit)
    {
        itemView.setOnClickListener()
        {
            action.invoke(adapterPosition)
        }
    }
}