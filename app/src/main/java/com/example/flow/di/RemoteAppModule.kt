package com.example.flow.di

import com.example.flow.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteAppModule {
    @Singleton
    @Provides
    fun getGson(): Gson {
        val gson = GsonBuilder().create()
        return gson }

    fun getStatus():OkHttpClient{
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder().addInterceptor(logger)
            .build()
        return client
    }

    @Singleton
    @Provides
    fun getRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/")
            .client(client)
            .build()
        return retrofit }

    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        val productsApiService = retrofit.create(ApiService::class.java)
        return productsApiService
    }

}