package com.will.habit.http

/**
 *
 */
class BaseResponse<T> {
    @JvmField
    var code = 0
    @JvmField
    var status=0
    val pages: Int=0
    val size: Int=0
    val total: Int=0
    @JvmField
    var msg: String? = null

    var data: T? = null
        private set

    var success:Boolean = false
    val isOk: Boolean
        get() = success

}