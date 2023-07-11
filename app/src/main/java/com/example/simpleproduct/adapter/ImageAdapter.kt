package com.example.simpleproduct.adapter

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleproduct.R
import com.example.simpleproduct.databinding.ItemRecyclerViewImageBinding
import com.example.simpleproduct.utils.inflate

/**
 *image adapter to display the selected images
 * */
class ImageAdaptor(private val list: List<*>, val itemPosition: (Int) -> Unit) :
    RecyclerView.Adapter<ImageAdaptor.ViewHolder>() {
    lateinit var binding: ItemRecyclerViewImageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = parent.inflate(R.layout.item_recycler_view_image)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        binding.rvImage.setImageURI(list[position]as Uri?)
        itemPosition(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}

