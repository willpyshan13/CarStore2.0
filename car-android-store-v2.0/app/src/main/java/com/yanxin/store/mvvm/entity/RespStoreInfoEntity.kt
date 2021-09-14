package com.yanxin.store.mvvm.entity

data class Brand(
    val brandName: String,
    val brandUuid: String,
    val technicianUuid: String,
    val uuid: String
)

data class TechnicianAccount(
    val accountAmount: Float = 0f,
    val accountName: String = "",
    var alipayAccount: String? = "",
    var cardNumbers: String?="",
    val debitCardBackUrl: String="",
    val debitCardUrl: String="",
    var depositBank: String?="",
    val subBranchName: String="",
    val technicianUuid: String="",
    val totalAmount: Float=0f,
    val uuid: String="",
    val waitAmount: Float=0f,
    var weChatAccount: String?="",
    val withdrawAmount: Float = 0f
)

data class RespStoreInfoEntity(
    val addressCityName: String,
    val addressCountyName: String,
    val addressProvinceName: String,
    var brandUuidList: List<String>?,
    val businessImgList: List<String>,
    var checkSts: Int,
    val commentStatics: CommentStatics,
    val storeBrandResList: List<StoreBrandResList>,
    val companyAddressCity: String,
    val companyAddressCounty: String,
    val companyAddressDetail: String,
    val companyAddressProvince: String,
    val companyName: String,
    val glyMobile: String,
    val latitude: String,
    val legalPersonFront: String,
    val legalPersonReverse: String,
    val longitude: String,
    val onTimeArr: String,
    val otherImgList: List<String>,
    val platformFee: Double,
    val renType: String,
    val shopImgList: List<String>?,
    var storeAccountRes: StoreAccountRes?,
    val storeBrandUuidList: List<StoreBrandUuid>,
    val storeName: String,
    val storeType: String,
    var storeUserReq: List<StoreUserRes>?,
    var storeUserResList: List<StoreUserRes>?,
    var addressCity: String?,
    var addressCounty: String?,
    var addressDetail: String?,
    val addressLatitude: Double,
    val addressLongitude: Double,
    var addressProvince: String?,
    val answerAmt: Float,
    val brandList: List<Brand>,
    val caseCount: Int,
    var certificateTypeName: String?,
    var certificateNum: String?,
    var certificateType: String?,
    val cybAuth: Int,
    val driverLicenseBackUrl: String,
    val driverLicenseUrl: String,
    val healthCheckUrl: String,
    val hostAuthentication: String,
    val hostImgList: List<String>,
    val identityCardBackUrl: String,
    val identityCardUrl: String,
    val mobile: String,
    val noCrimeUrl: String,
    val orderCount: Int,
    val photoImgUrl: String,
    val platformMoney: Float,
    val qaCount: Int,
    val rejectDetail: String,
    val relationStoreUuid: String,
    var relativeMobile: String?,
    val score: String,
    val scoreCount: String,
    val shareNum: Int,
    val stateImgList: List<String>,
    val stateVerification: String,
    val supportCount: Int,
    var technicianAccount: TechnicianAccount,
    val technologyType: String,
    var technologyTypeName: String?,
    val userName: String,
    val uuid: String,
    var workingYear: String?
)

data class StoreBrandResList(
        val uuid: String,
        val configName: String
)

data class CommentStatics(
    val environmentScore: String,
    val score: String,
    val serviceScore: String,
    val storeUuid: String,
    val technologyScore: String,
    val totalNum: Int
)

data class StoreAccountRes(
    var accountName: String?,
    var accountType: String?,
    var alipayAccount: String?,
    var cardNumbers: String?,
    var configName: String?,
    var depositBank: String?,
    var subBranchName: String?,
    var uuid: String?,
    var weChatAccount: String?,
    var withdrawalWay: String?
)

data class StoreBrandUuid(
    val accountName: String,
    val accountType: String,
    val alipayAccount: String,
    val cardNumbers: String,
    val configName: String,
    val depositBank: String,
    val subBranchName: String,
    val uuid: String,
    val weChatAccount: String,
    val withdrawalWay: String
)

data class StoreUserRes(
    var email: String,
    var mobile: String?,
    var personType: String?,
    var storeUuid: String?,
    var position: String?,
    var userName: String?,
    var uuid: String?
)