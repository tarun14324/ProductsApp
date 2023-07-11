package com.example.simpleproduct.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleproduct.model.ProductResponse
import com.example.simpleproduct.repository.MainRepository
import com.example.simpleproduct.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.launch

/**
 * provides Retrofit  instances
 * */
class ProductListViewModel constructor(
    private val mainRepository: MainRepository,
) : ViewModel() {

    /**
     * live data to collect the fetch the products from api
     * */
    private val _products = MutableLiveData<Resource<ArrayList<ProductResponse>>>()
    val products: LiveData<Resource<ArrayList<ProductResponse>>>
        get() = _products

    init {
        _products.postValue(Resource.error("NO Data", null))
        fetchProducts()
    }

    /**
     * fetch products from api
     * */
    private fun fetchProducts() {
        viewModelScope.launch {
            _products.postValue(Resource.loading(null))
                mainRepository.getProducts().let {
                    if (it.isSuccessful) {
                        _products.postValue(Resource.success(it.body()))
                    } else _products.postValue(Resource.error(it.errorBody().toString(), null))
                }
            }

        }
}