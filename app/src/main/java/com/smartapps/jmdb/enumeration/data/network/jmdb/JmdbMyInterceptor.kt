package com.smartapps.jmdb.enumeration.data.network.jmdb


import com.smartapps.jmdb.enumeration.data.repository.JmdbRepository
import com.smartapps.jmdb.enumeration.data.repository.TOKENN


import okhttp3.Interceptor
import okhttp3.Response

class JmdbMyInterceptor: Interceptor {







    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization","Bearer " + TOKENN)
            .build()
        return chain.proceed(request)
    }





}