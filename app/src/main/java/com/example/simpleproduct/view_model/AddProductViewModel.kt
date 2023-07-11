package com.example.simpleproduct.view_model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleproduct.api.ApiService
import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.ProductDetails
import com.example.simpleproduct.repository.MainRepository
import com.example.simpleproduct.utils.ConflatedChannel
import com.example.simpleproduct.utils.InternetHelper
import com.example.simpleproduct.utils.sendValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.URI


class AddProductViewModel(
    private val mainRepository: MainRepository,
    private val networkHelper: InternetHelper
) : ViewModel() {


    //flows for product name,type,price ,tax
    val ProductNameFlow = MutableStateFlow("")
    val ProductTypeFlow = MutableStateFlow("")
    val ProductPriceFlow = MutableStateFlow("")
    val ProductTaxFlow = MutableStateFlow("")
    val errorStringStateFlow = MutableStateFlow("")
    val errorMessage = MutableStateFlow("")
    val imageUri = MutableStateFlow<List<Uri>>(emptyList())

    // conflated channels used for event handling
    val moveToSelectImageEvent = ConflatedChannel<Unit>()

    val moveToShowProductEvent = ConflatedChannel<Unit>()


    /**
     * validation of fields when click on add button
     * */
    fun submitButtonClick() {
        viewModelScope.launch {
            val name = ProductNameFlow.value.trim()
            val price = ProductPriceFlow.value.trim()
            val type = ProductTypeFlow.value.trim()
            val tax = ProductTaxFlow.value.trim()
            val data = validateField(name, price, type, tax)
            if (data == null) {
                sample()
            } else {
                errorStringStateFlow.value = data
            }
        }
    }


    /**
     * calling when select image button clicked
     * */
    fun selectImageButtonClick() {
        moveToSelectImageEvent.sendValue(Unit)
    }

    /**
     * validating fields
     * */
    private fun validateField(name: String, price: String, type: String, tax: String): String? {
        if (name.isEmpty()) {
            return "Product Name Required"
        }
        if (price.isEmpty()) {
            return "Product Price Required"
        }
        if (type.isEmpty()) {
            return "Product Type Required"
        }
        if (tax.isEmpty()) {
            return "Product Tax Required"
        }

        return null
    }

    private fun updateProductData() {
        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val response = mainRepository.addProducts(
//                    productType = ProductTypeFlow.value,
//                    productName = ProductNameFlow.value,
//                    productPrice = ProductPriceFlow.value,
//                    productTax = ProductTaxFlow.value
//                )
//
//                if (response.) {
//                    moveToShowProductEvent.sendValue(Unit)
//                } else {
//                    Resource.error(response.message, null)
//                }
//            } catch (e: IOException) {
//                Resource.error(
//                    "Oops! couldn't reach server, check your internet connection.", null
//                )
//            } catch (e: HttpException) {
//                Resource.error(
//                    "Oops! something went wrong. Please try again", null
//                )
//            }

        }

    }

    /**
     * add product to the api
     * */
    private fun sample(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://app.getswipe.in/api/public/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)

        val param1: RequestBody = ProductNameFlow.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val param2: RequestBody = ProductTypeFlow.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val param3: RequestBody = ProductPriceFlow.value.toRequestBody("text/plain".toMediaTypeOrNull())
        val param4: RequestBody = ProductTaxFlow.value.toRequestBody("text/plain".toMediaTypeOrNull())
       // val filePart: MultipartBody.Part = createFilePart(File(pa),"sample")

        val call = apiService.uploadFormData(param1, param2,param3,param4)
        call.enqueue(object : retrofit2.Callback<AddProductResponse?> {
            override fun onResponse(
                call: retrofit2.Call<AddProductResponse?>,
                response: retrofit2.Response<AddProductResponse?>
            ) {
                val responseBody: ProductDetails? = response.body()?.product_details
                Log.e("TAG", "onResponse:${response.isSuccessful}+${response.body()} ", )
            }

            override fun onFailure(call: retrofit2.Call<AddProductResponse?>, t: Throwable) {
                Log.e("TAG", "onFailure:${t.localizedMessage} ", )
            }
        })

    }

    /**
     * function to get to convert the image file into Request body
     * */

    private fun createFilePart(file: File, partName: String): MultipartBody.Part {
        // Create RequestBody for the file
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        // Create MultipartBody.Part
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

}


