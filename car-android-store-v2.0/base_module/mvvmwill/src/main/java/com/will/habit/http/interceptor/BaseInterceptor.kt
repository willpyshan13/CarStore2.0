package com.will.habit.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import com.blankj.utilcode.util.SPUtils

/**
 * @author will
 */
class BaseInterceptor(private val headers: Map<String, String>?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
            .newBuilder()
        if (headers != null && headers.isNotEmpty()) {
            val keys = headers.keys
            for (headerKey in keys) {
                headers[headerKey]?.let { builder.addHeader(headerKey, it).build() }
            }
        }
        builder.addHeader(
            "token",
            SPUtils.getInstance().getString("user_token")
        )
        //请求信息
        val response = chain.proceed(builder.build())

        return response
    }

}