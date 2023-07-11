package com.example.simpleproduct.api

import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.Products
import com.example.simpleproduct.utils.createPartFromString
import okhttp3.MediaType
import retrofit2.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part

/**
 * class that extends with ApiHelper class to fetch the data
 * */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    // get products from api
    override suspend fun getProducts(): Response<Products> = apiService.getProducts()


    // add product to api
//    override suspend fun addProduct(productName: String,productType: String, productPrice: String, productTax:String): Call<AddProductResponse> {
//        val productNameRequestBody = createPartFromString(productName)
//        val productTypeRequestBody = createPartFromString(productType)
//        val productPriceRequestBody = createPartFromString(productPrice)
//        val productTaxRequestBody = createPartFromString(productTax)
//
//        return apiService.addProduct(
//            productName = productNameRequestBody,
//            price = productPriceRequestBody,
//            productType = productTypeRequestBody,
//            tax = productTaxRequestBody
//        )
//    }


}