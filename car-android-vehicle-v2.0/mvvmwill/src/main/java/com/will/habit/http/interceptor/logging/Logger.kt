package com.will.habit.http.interceptor.logging

import okhttp3.internal.platform.Platform

/**
 * @author ihsan on 11/07/2017.
 */
interface Logger {
    fun log(level: Int, tag: String?, msg: String?)

    companion object {
        val DEFAULT: Logger = object : Logger {
            override fun log(level: Int, tag: String?, message: String?) {
                message?.let { Platform.get().log(level = level,message = it) }
            }
        }
    }
}