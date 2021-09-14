package com.yanxin.store.mvvm.entity

data class RespOnSiteSupportEntity(
    val amountList: List<RespOnSiteSupportAmountEntity>?,
    val numList: List<RespOnSiteSupportAmountEntity>?,
    val total: Int,
    val totalAmount: Int
)

data class RespOnSiteSupportAmountEntity(
    val dayTime: String,
    val totalNum: Int
)