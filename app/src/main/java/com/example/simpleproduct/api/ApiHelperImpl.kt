package com.example.simpleproduct.api

import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.Products
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody
import retrofit2.Response


/**
 * class that extends with ApiHelper class to fetch the data
 * */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    // get products from api
    override suspend fun getProducts(): Response<Products> = apiService.getProducts()


    // add product to api

    override suspend fun addProduct(
        productName: String,
        productType: String,
        productPrice: String,
        productTax: String,
        image: String
    ): Response<AddProductResponse> {
        val params: MutableMap<String, String> = HashMap()
        params["product_name"] = productName
        params["product_type"] = productType
        params["price"] = productPrice
        params["tax"] = productTax
        params["image"] = image

        return apiService.addProduct(params)
    }


}