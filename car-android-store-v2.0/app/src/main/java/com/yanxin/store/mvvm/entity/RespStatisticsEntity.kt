package com.yanxin.store.mvvm.entity


data class RespStatisticsEntity(
        val count: Float,
        val list: List<RespStatisticsDataEntity>
)

data class RespStatisticsDataEntity(val data: Double,
                                    val name: String)