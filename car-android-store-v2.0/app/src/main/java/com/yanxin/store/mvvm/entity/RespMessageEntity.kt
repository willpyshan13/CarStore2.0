package com.yanxin.store.mvvm.entity


data class RespMessageEntity(
    val clientType: String,
    val content: String,
    val createdBy: String,
    val createdTime: String,
    val lastUpdatedBy: String,
    val lastUpdatedTime: String,
    val status: Int,
    val technicianUuid: String,
    val orderUuid: String,
    val type: String,
    val uuid: String
)