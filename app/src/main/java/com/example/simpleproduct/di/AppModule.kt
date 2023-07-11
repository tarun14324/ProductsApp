package com.example.simpleproduct.di

import android.content.Context
import com.example.simpleproduct.AppConstants
import com.example.simpleproduct.api.ApiHelperImpl
import com.example.simpleproduct.api.ApiService
import com.example.simpleproduct.repository.MainRepository
import com.example.simpleproduct.utils.InternetHelper
import com.example.simpleproduct.view_model.AddProductViewModel
import com.example.simpleproduct.view_model.ProductListViewModel
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * all modules that are gives the instance to all the classes in application
 * */
val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), AppConstants.BaseUrl) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
  //  single { provideDataBase(androidContext()) }
    single {
        ApiHelperImpl(get())
    }
    single { MainRepository(get()) }

}

/**
 * all the viewmodels instance will be created from this modules.
 * */
val viewModelModule = module {
    // ViewModel for Detail View with id as parameter injection
    // ViewModel for Product List
    viewModel { ProductListViewModel(get()) }
    // ViewModel for Add Products
    viewModel { AddProductViewModel(get(), get()) }
}

/**
 * provides instance to check the is internet conncected or not
 * */
private fun provideNetworkHelper(context: Context) = InternetHelper(context)

/**
 * provides instance to  reuse it for all of your HTTP calls.
 * */

private fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
} else OkHttpClient
    .Builder()
    .build()

/**
 * provides Retrofit  instances
 * */

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit {

    val gson = GsonBuilder()
        .setLenient()
        .create()
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
}
/**
 * provides Retrofit  instances
 * */

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)


//private fun provideDataBase(context: Context): androidx.room.RoomDatabase {
//    val instance: androidx.room.RoomDatabase? = null
//    return instance ?: synchronized(context) {
//        val instance = Room.databaseBuilder(
//            context.applicationContext,
//            androidx.room.RoomDatabase::class.java,
//            "offline_product_database"
//        ).build()
//        instance
//    }
//}





