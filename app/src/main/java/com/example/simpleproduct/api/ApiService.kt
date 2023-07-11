package com.example.simpleproduct.api

import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.ProductDetails
import com.example.simpleproduct.model.Products
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap

/**
 *api service class
 * */
interface ApiService {
    // get products from api
    @GET("get")
    suspend fun getProducts(): Response<Products>

    @POST("add")
    @FormUrlEncoded
    suspend fun addProduct(@FieldMap params: Map<String, String>): Response<AddProductResponse>

}