package com.nick_sib.repository.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

object BaseInterceptor: Interceptor {

    private var responseCode: Int = 0

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        responseCode = response.code
        return response
    }
}