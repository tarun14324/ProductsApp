package com.example.simpleproduct.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

/**
 *base class for fragments
 * */
abstract class BaseFragment<Binding : ViewDataBinding> : Fragment() {

    lateinit var dataBinding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        initObservers(viewLifecycleOwner)
    }

    abstract fun initObservers(viewLifecycleOwner: LifecycleOwner)

    abstract fun setUp()

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

}