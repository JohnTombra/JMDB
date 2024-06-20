package com.novaapps.jmdb.data.repository


import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor: Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization","Bearer " + TOKEN)
            .addHeader("Cache-Control","no-cache")
            .addHeader("Accept","*/*")
            .addHeader("Accept-Encoding","gzip, deflate, br")
            .addHeader("Connection","keep-alive")
            .build()
        return chain.proceed(request)
    }





}