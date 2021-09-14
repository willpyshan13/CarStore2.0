package com.yanxin.store.mvvm.entity


data class RespAccountClassifyEntity(
    val accountAmt: Float,
    val orderNum: Int,
    val profitStreamClassifies: List<ProfitStreamClassify>,
    val waitAmount: String
)

data class ProfitStreamClassify(
    val amt: String,
    val classify: Int,
    val withdrawAmt: String
)