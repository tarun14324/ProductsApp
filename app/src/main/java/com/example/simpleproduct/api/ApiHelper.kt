package com.example.simpleproduct.api

import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.Products
import okhttp3.MultipartBody
import retrofit2.Response

/**
 *interface to fetch data using retrofit
 * */
interface ApiHelper {
    // get products from api
    suspend fun getProducts(): Response<Products>

    // add product to api
    suspend fun addProduct(
        productName: String,
        productType: String,
        productPrice: String,
        productTax: String,
        image: String
    ):Response<AddProductResponse>

}