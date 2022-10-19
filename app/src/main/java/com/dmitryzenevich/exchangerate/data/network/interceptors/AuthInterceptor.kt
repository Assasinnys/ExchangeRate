package com.dmitryzenevich.exchangerate.data.network.interceptors

import com.dmitryzenevich.exchangerate.data.network.API_KEY
import com.dmitryzenevich.exchangerate.data.network.HEADER_API
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(HEADER_API, API_KEY)
            .build()

        return chain.proceed(request)
    }
}