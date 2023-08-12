package com.nextint.learncrypto.app.bases

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<Model, VH : RecyclerView.ViewHolder>
    (
    //ini untuk inflate layout
    private val onCreateViewHolder : (parent : ViewGroup, viewType : Int) -> VH,
    private val onBindViewHolder : (viewHolder : VH, position : Int, item : Model) -> Unit,
    private val onViewType : ((viewType : Int, item : List<Model>) -> Int)? = null,
    private val onDetachFromWindow : ((VH) -> Unit)? = null
    ) : RecyclerView.Adapter<VH>(), AdapterObserver<Model> {

    var item = mutableListOf<Model>()
    private var onGetItemViewType: ((position : Int) -> Int)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = onCreateViewHolder.invoke(parent,viewType)


    override fun onBindViewHolder(holder: VH, position: Int) {
        val item =item[position]
        onBindViewHolder.invoke(holder,position, item)
    }

    override fun getItemCount(): Int = item.size

    override fun addAll(collection: Collection<Model>) {
        item.addAll(collection)
        notifyDataSetChanged()
    }

    override fun safeAddAll(collection: Collection<Model>?) {
        collection?.let {
            item.addAll(collection)
            notifyDataSetChanged()
        }
    }

    override fun safeClearAndAddAll(collection: Collection<Model>) {
        collection.let {
            clear()
            addAll(collection)
        }
    }

    override fun add(list: List<Model>) {
        clear()
        addAll(list)
    }

    override fun clear() {
        val size = item.size
        item.clear()
        notifyItemRangeRemoved(0,size)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (onViewType != null) {
            onViewType.invoke(position, item)
        } else {
            val onGetItemViewType = onGetItemViewType
            onGetItemViewType?.invoke(position) ?: super.getItemViewType(position)
        }

    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        onDetachFromWindow?.invoke(holder)
    }
}

interface AdapterObserver<T> {

    fun addAll(collection: Collection<T>)

    fun safeAddAll(collection: Collection<T>?)

    fun safeClearAndAddAll(collection: Collection<T>)

    fun add(list: List<T>)

    fun clear()
}