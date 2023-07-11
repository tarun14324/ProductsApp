package com.example.simpleproduct.api

import com.example.simpleproduct.model.Products
import retrofit2.Response

/**
 *interface to fetch data using retrofit
 * */
interface ApiHelper {
    // get products from api
    suspend fun getProducts(): Response<Products>

    // add product to api
   // suspend fun addProduct(productName: String,productType: String, productPrice: String, productTax:String):Call<AddProductResponse>

    //suspend fun sample(map:Map<String?, RequestBody?>?,file: Part):Call<AddProductResponse?>?
}