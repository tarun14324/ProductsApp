package com.example.simpleproduct.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.example.simpleproduct.R
import com.example.simpleproduct.base.BaseAdapter
import com.example.simpleproduct.base.BaseHolder
import com.example.simpleproduct.base.BaseViewHolder
import com.example.simpleproduct.databinding.ItemProductBinding
import com.example.simpleproduct.model.ProductResponse
import com.example.simpleproduct.utils.inflate

/**
 *adapter class to display the list of products
 * */

class ProductAdapter(val onItemClick: (ProductResponse) -> Unit) : BaseAdapter<ProductResponse>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<ProductResponse> =
        ProductViewHolder(parent.inflate(R.layout.item_product))

    inner class ProductViewHolder(binding: ItemProductBinding) :
        BaseViewHolder<ItemProductBinding, ProductResponse>(binding) {

        init {
            binding.root.setOnClickListener {
                onItemClick(listItems[layoutPosition])
            }
        }

        override fun onBind(item: ProductResponse) {
            binding.item = item
        }

    }
    // method for filtering our recyclerview items.
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: ArrayList<ProductResponse>) {
        listItems = filterlist
        notifyDataSetChanged()
    }
}

