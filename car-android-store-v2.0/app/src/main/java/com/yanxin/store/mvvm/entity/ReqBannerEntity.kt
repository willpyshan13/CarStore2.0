package com.yanxin.store.mvvm.entity

/**
 *
 * <p>
 * Date: 2021-07-26
 * Company: xmotion
 * Updater:
 * Update Time:
 * Update Comments:
 * @param bannerSts Int    bannerSts (integer, optional): 状态：0下架；1上架 ,
 * @param bannerType Int   bannerType (integer, optional): 显示端：0商家端；1客户端 ,
 * @constructor
 *
 * Author: will
 */
data class ReqBannerEntity (val bannerSts:Int=1,val bannerType:Int=1)

data class RespBannerEntity(
    val bannerSts: Int,
    val bannerType: Int,
    val cname: String,
    val createdTime: String,
    val img: String,
    val lastUpdatedTime: String,
    val url: String,
    val uuid: String
)