package com.example.simpleproduct.utils

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


/**
     * show toast in activity or required view
     * */

    fun Activity.showToast(msg:String){
        Toast.makeText(this , msg , Toast.LENGTH_SHORT).show()
    }

/**
 * view binding to inflate the layout to activity or fragment or adapter
 * */

fun <T : ViewDataBinding> ViewGroup.inflate(
    @LayoutRes layoutRes: Int,
    attachToParent: Boolean = false
): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToParent)
}

fun createPartFromString(stringData: String): RequestBody {
    return stringData.toRequestBody("text/plain".toMediaTypeOrNull())
}

