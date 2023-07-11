package com.example.simpleproduct.view_model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleproduct.api.ApiService
import com.example.simpleproduct.model.AddProductResponse
import com.example.simpleproduct.model.ProductDetails
import com.example.simpleproduct.model.ProductResponse
import com.example.simpleproduct.repository.MainRepository
import com.example.simpleproduct.utils.ConflatedChannel
import com.example.simpleproduct.utils.InternetHelper
import com.example.simpleproduct.utils.Resource
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
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
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
    val imageUri = MutableStateFlow<String>("")

    private val _addProducts = MutableLiveData<Resource<ArrayList<AddProductResponse>>>()
    val addProducts: LiveData<Resource<ArrayList<AddProductResponse>>>
        get() = _addProducts

    // conflated channels used for event handling
    val moveToSelectImageEvent = ConflatedChannel<Unit>()


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
                updateProductData()
            } else {
                errorStringStateFlow.value = data
                _addProducts.postValue(Resource.error(msg = data,null))
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
            return "Select Product Type"
        }
        if (tax.isEmpty()) {
            return "Product Tax Required"
        }

        return null
    }

    private fun updateProductData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = mainRepository.addProducts(
                    productType = ProductTypeFlow.value,
                    productName = ProductNameFlow.value,
                    productPrice = ProductPriceFlow.value,
                    productTax = ProductTaxFlow.value,
                    image=imageUri.value
                )

                if (response.isSuccessful) {
                    _addProducts.postValue(Resource.success(null))
                } else {
                    _addProducts.postValue(Resource.error(response.message(), null))
                }
            } catch (e: IOException) {
                _addProducts.postValue(Resource.error(
                    "Oops! couldn't reach server, check your internet connection.", null
                )
                )
            } catch (e: HttpException) {
                _addProducts.postValue(Resource.error(
                    "Oops! something went wrong. Please try again", null
                )
                )
            }

        }

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


