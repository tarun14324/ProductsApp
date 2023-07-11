package com.example.simpleproduct.repository

import com.example.simpleproduct.api.ApiHelperImpl


/**
 * respository that maintains the data for the app
 * */
class MainRepository (private val apiHelper: ApiHelperImpl) {

    suspend fun getProducts() =  apiHelper.getProducts()
//    suspend fun addProducts(productName: String,productType: String, productPrice: String, productTax:String) =  apiHelper.addProduct(
//        productName,productType,productPrice,productTax
//    )

}

