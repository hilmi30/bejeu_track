package com.vimark.bejeutrack.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var httpClient: OkHttpClient.Builder
        private lateinit var loggingInterceptor: HttpLoggingInterceptor
        private lateinit var headerAuthorizationInterceptor: Interceptor

        fun getInstance(baseUrl: String): Retrofit {

            val authToken = Credentials.basic("tamu", "tamu")

            loggingInterceptor = HttpLoggingInterceptor()
            headerAuthorizationInterceptor = Interceptor { chain ->
                var request: okhttp3.Request = chain.request()
                val headers = request.headers().newBuilder().add("Authorization", authToken).build()
                request = request.newBuilder().headers(headers).build()
                chain.proceed(request)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(headerAuthorizationInterceptor)
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()

            return retrofit
        }
    }
}
