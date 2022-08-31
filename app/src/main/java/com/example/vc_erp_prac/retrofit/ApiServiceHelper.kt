package com.example.vc_erp_prac.retrofit

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiServiceHelper {
    private var client: ApiService? = null

    init {
        if (client == null) {
            val okHttpClient = OkHttpClient.Builder()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(interceptor)

            okHttpClient.connectTimeout(5, TimeUnit.MINUTES)
            okHttpClient.readTimeout(5, TimeUnit.MINUTES)
            okHttpClient.writeTimeout(5, TimeUnit.MINUTES)
            okHttpClient.interceptors().add(Interceptor { chain ->

                val request: Request.Builder = chain.request()
                    .newBuilder()

                chain.proceed(request.build())
            })
            val retofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            client = retofitClient.create(ApiService::class.java);
        }
    }

    suspend fun getCityData() = client?.getCityData()
    suspend fun getDistData() = client?.getDistData()
    suspend fun getStateData() = client?.getStateData()
}