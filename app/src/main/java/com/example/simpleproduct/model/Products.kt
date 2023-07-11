package com.example.simpleproduct.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("image") val image : String,
    @SerializedName("price") val price : Any,
    @SerializedName("product_name") val product_name : String,
    @SerializedName("product_type") val product_type : String,
    @SerializedName("tax") val tax : Any
)

class Products : ArrayList<ProductResponse>()