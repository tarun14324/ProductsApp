package com.example.simpleproduct.model

import okhttp3.RequestBody

data class ProductDetails(
    val productName: RequestBody,
    val productType: RequestBody,
    val price: RequestBody,
    val tax: RequestBody,
)
