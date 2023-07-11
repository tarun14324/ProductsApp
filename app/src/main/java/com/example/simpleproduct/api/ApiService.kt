package com.example.simpleproduct.api

import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.Products
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

/**
 *api service class
 * */
interface ApiService {
    // get products from api
    @GET("get")
    suspend fun getProducts(): Response<Products>

    // add product to api
//    @Multipart
//    @POST("add")
//    suspend fun addProduct(
//        @Part("product_name") productName: RequestBody,
//        @Part("product_price") productPrice: RequestBody,
//        @Part("files") productImage: MultipartBody.Part
//    ): ApiResponse

    @Multipart
    @POST("add")
    fun addProduct(
        @PartMap() partMap: MutableMap<String,RequestBody>,
     //   @Part("files") productImage: MultipartBody.Part
    ): Call<AddProductResponse>

    fun uploadFile(
        @Part("product_name") productName: RequestBody?,
        @Part("product_type") productType: RequestBody?,
        @Part("price") price: RequestBody?,
        @Part("tax") tax: RequestBody?,
        @Part file: Part?
    ): Call<ResponseBody?>?

    @Multipart
    @POST("add")
    fun uploadFormData(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
      //  @Part filePart: MultipartBody.Part
    ): Call<AddProductResponse>

}