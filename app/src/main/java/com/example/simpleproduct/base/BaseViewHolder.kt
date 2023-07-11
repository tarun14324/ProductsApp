package com.example.simpleproduct.base

import androidx.databinding.ViewDataBinding

/**
 *base view holder class for adapters
 * */
abstract class BaseViewHolder<Binding : ViewDataBinding, Item>(val binding: Binding) :
    BaseHolder<Item>(binding.root)
