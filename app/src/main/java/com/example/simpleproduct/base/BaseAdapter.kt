package com.example.simpleproduct.base

import androidx.recyclerview.widget.RecyclerView

/**
 * base class for adapter
 * */
abstract class BaseAdapter<Item> : RecyclerView.Adapter<BaseHolder<Item>>() {

    var listItems: ArrayList<Item> = ArrayList()

    /**
     *submit the data to adapter
     * */
    open fun submitList(data: List<Item>?) {

        listItems.clear()

        if (data != null) {
            listItems.addAll(ArrayList(data))
        }

        notifyDataSetChanged()
    }

    /**
     *add new the data to adapter
     * */
    fun addNewData(newData: List<Item>?) {
        if (newData.isNullOrEmpty()) return

        listItems.addAll(ArrayList(newData))
        notifyDataSetChanged()
    }

    /**
     *bind the view to holder in adapter class
     * */
    override fun onBindViewHolder(viewHolder: BaseHolder<Item>, position: Int) {

        val item = getItem(position)

        if (item != null) {
            viewHolder.onBind(item)
        }

    }
    /**
     *recycler the view holder
     * */
    override fun onViewRecycled(holder: BaseHolder<Item>) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }
    /**
     *returns the item at specific position
     *  */
    open fun getItem(position: Int): Item {
        return listItems[position]
    }
    /**
     *returns the size of list
     * */
    override fun getItemCount(): Int {
        return listItems.size
    }

}
