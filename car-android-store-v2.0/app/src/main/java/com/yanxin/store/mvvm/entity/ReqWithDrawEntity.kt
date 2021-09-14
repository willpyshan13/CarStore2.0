package com.yanxin.store.mvvm.entity

data class ReqWithDrawEntity(
    val cardNumbers: String,
    val depositBank: String,
    val subBranchName: String,
    val withdrawalAmount: String
)