package com.example.simpleproduct.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * base holder class for adapters
 * */
abstract class BaseHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBind(item: Item)

    open fun onRecycled() {
        // override
    }

}
