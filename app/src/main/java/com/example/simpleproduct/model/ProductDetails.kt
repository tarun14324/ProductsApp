package com.example.simpleproduct.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductDetails(
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("tax")
    val tax: Double
): Parcelable
