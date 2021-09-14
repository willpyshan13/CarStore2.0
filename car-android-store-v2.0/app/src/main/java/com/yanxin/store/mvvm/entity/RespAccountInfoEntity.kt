package com.yanxin.store.mvvm.entity


data class RespAccountInfoEntity(
    val accountAmt: Float,
    val headImage: String?,
    val score: String?,
    val name: String?,
    val orderNum: String,
    val withdrawAmount: Float,
    val frozenAmt: Float,
    val userInfoRes: UserInfoRes,
    val waitAmount: Float,
)

data class UserInfoRes(
    val headImage: String?,
    val name: Any,
    val type: String
)