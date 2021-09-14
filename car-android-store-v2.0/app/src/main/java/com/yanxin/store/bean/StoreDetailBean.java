package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;

import java.util.List;

public class StoreDetailBean extends BaseBean {

    /**
     * code : 0000
     * msg : success
     * data : {"uuid":"22025d8777b144ec8a888109a93ffe8e","storeName":"加盟店","storeType":"101","companyName":"一家","companyAddressProvince":"1","addressProvinceName":"北京","companyAddressCity":"2","addressCityName":"北京市","companyAddressCounty":"11","addressCountyName":"通州区","companyAddressDetail":"地区","brandUuidList":["9a79a6b6436649ae8e22be368a27cc14","b144fcd919de4154a289717dfe20a1c0","ba0628f9d98a44b98698f80a05abe7d0"],"businessImgList":["https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-16/6283924d30454993be5999d5b47bf714.jpg"],"shopImgList":["https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-16/b791b4dec13e4943b9b717e62ff3c93a.jpg",null,"https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-16/b791b4dec13e4943b9b717e62ff3c93a.jpg",null],"storeUserResList":[{"uuid":"2d0c55bd42c949f085f173cc96946032","userName":"王大锤","mobile":"13566666666","personType":null,"email":null,"storeUuid":"22025d8777b144ec8a888109a93ffe8e"}],"otherImgList":[],"storeAccountRes":null,"checkSts":0,"rejectDetail":null,"onTimeArr":null,"commentStatics":{"totalNum":121,"score":4.78,"storeUuid":"22025d8777b144ec8a888109a93ffe8e","technologyScore":4.8,"serviceScore":4.6,"environmentScore":5},"latitude":39.910923,"longitude":116.41338,"platformFee":null}
     * success : true
     */

    private String code;
    private String msg;
    private DataBean data;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * uuid : 22025d8777b144ec8a888109a93ffe8e
         * brandName;
         * storeName : 加盟店
         * storeType : 101
         * companyName : 一家
         * companyAddressProvince : 1
         * addressProvinceName : 北京
         * companyAddressCity : 2
         * addressCityName : 北京市
         * companyAddressCounty : 11
         * addressCountyName : 通州区
         * companyAddressDetail : 地区
         * brandUuidList : ["9a79a6b6436649ae8e22be368a27cc14","b144fcd919de4154a289717dfe20a1c0","ba0628f9d98a44b98698f80a05abe7d0"]
         * businessImgList : ["https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-16/6283924d30454993be5999d5b47bf714.jpg"]
         * shopImgList : ["https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-16/b791b4dec13e4943b9b717e62ff3c93a.jpg",null,"https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-16/b791b4dec13e4943b9b717e62ff3c93a.jpg",null]
         * storeUserResList : [{"uuid":"2d0c55bd42c949f085f173cc96946032","userName":"王大锤","mobile":"13566666666","personType":null,"email":null,"storeUuid":"22025d8777b144ec8a888109a93ffe8e"}]
         * otherImgList : []
         * storeAccountRes : null
         * checkSts : 0
         * rejectDetail : null
         * onTimeArr : null
         * commentStatics : {"totalNum":121,"score":4.78,"storeUuid":"22025d8777b144ec8a888109a93ffe8e","technologyScore":4.8,"serviceScore":4.6,"environmentScore":5}
         * latitude : 39.910923
         * longitude : 116.41338
         * platformFee : null
         */

        private String uuid;
        private String storeName;
        private String brandName;
        private String storeType;
        private String companyName;
        private String companyAddressProvince;
        private String addressProvinceName;
        private String companyAddressCity;
        private String addressCityName;
        private String companyAddressCounty;
        private String addressCountyName;
        private String companyAddressDetail;
        private List<String> brandUuidList;
        private List<String> businessImgList;
        private List<String> shopImgList;
        private List<StoreUserResListBean> storeUserResList;
        private List<?> otherImgList;
        private StoreAccountReqBean storeAccountRes;
        private int checkSts;
        private String rejectDetail;
        private Object onTimeArr;
        private CommentStaticsBean commentStatics;
        private double latitude;
        private double longitude;
        private Object platformFee;
        private String glyMobile;
        private String legalPersonFront;
        private String legalPersonReverse;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getGlyMobile() {
            return glyMobile;
        }

        public void setGlyMobile(String glyMobile) {
            this.glyMobile = glyMobile;
        }

        public String getLegalPersonFront() {
            return legalPersonFront;
        }

        public void setLegalPersonFront(String legalPersonFront) {
            this.legalPersonFront = legalPersonFront;
        }

        public String getLegalPersonReverse() {
            return legalPersonReverse;
        }

        public void setLegalPersonReverse(String legalPersonReverse) {
            this.legalPersonReverse = legalPersonReverse;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreType() {
            return storeType;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyAddressProvince() {
            return companyAddressProvince;
        }

        public void setCompanyAddressProvince(String companyAddressProvince) {
            this.companyAddressProvince = companyAddressProvince;
        }

        public String getAddressProvinceName() {
            return addressProvinceName;
        }

        public void setAddressProvinceName(String addressProvinceName) {
            this.addressProvinceName = addressProvinceName;
        }

        public String getCompanyAddressCity() {
            return companyAddressCity;
        }

        public void setCompanyAddressCity(String companyAddressCity) {
            this.companyAddressCity = companyAddressCity;
        }

        public String getAddressCityName() {
            return addressCityName;
        }

        public void setAddressCityName(String addressCityName) {
            this.addressCityName = addressCityName;
        }

        public String getCompanyAddressCounty() {
            return companyAddressCounty;
        }

        public void setCompanyAddressCounty(String companyAddressCounty) {
            this.companyAddressCounty = companyAddressCounty;
        }

        public String getAddressCountyName() {
            return addressCountyName;
        }

        public void setAddressCountyName(String addressCountyName) {
            this.addressCountyName = addressCountyName;
        }

        public String getCompanyAddressDetail() {
            return companyAddressDetail;
        }

        public void setCompanyAddressDetail(String companyAddressDetail) {
            this.companyAddressDetail = companyAddressDetail;
        }

        public List<String> getBrandUuidList() {
            return brandUuidList;
        }

        public void setBrandUuidList(List<String> brandUuidList) {
            this.brandUuidList = brandUuidList;
        }

        public List<String> getBusinessImgList() {
            return businessImgList;
        }

        public void setBusinessImgList(List<String> businessImgList) {
            this.businessImgList = businessImgList;
        }

        public List<String> getShopImgList() {
            return shopImgList;
        }

        public void setShopImgList(List<String> shopImgList) {
            this.shopImgList = shopImgList;
        }

        public List<StoreUserResListBean> getStoreUserResList() {
            return storeUserResList;
        }

        public void setStoreUserResList(List<StoreUserResListBean> storeUserResList) {
            this.storeUserResList = storeUserResList;
        }

        public List<?> getOtherImgList() {
            return otherImgList;
        }

        public void setOtherImgList(List<?> otherImgList) {
            this.otherImgList = otherImgList;
        }

        public StoreAccountReqBean getStoreAccountRes() {
            return storeAccountRes;
        }

        public void setStoreAccountRes(StoreAccountReqBean storeAccountRes) {
            this.storeAccountRes = storeAccountRes;
        }

        public int getCheckSts() {
            return checkSts;
        }

        public void setCheckSts(int checkSts) {
            this.checkSts = checkSts;
        }

        public String getRejectDetail() {
            return rejectDetail;
        }

        public void setRejectDetail(String rejectDetail) {
            this.rejectDetail = rejectDetail;
        }

        public Object getOnTimeArr() {
            return onTimeArr;
        }

        public void setOnTimeArr(Object onTimeArr) {
            this.onTimeArr = onTimeArr;
        }

        public CommentStaticsBean getCommentStatics() {
            return commentStatics;
        }

        public void setCommentStatics(CommentStaticsBean commentStatics) {
            this.commentStatics = commentStatics;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public Object getPlatformFee() {
            return platformFee;
        }

        public void setPlatformFee(Object platformFee) {
            this.platformFee = platformFee;
        }

        public static class CommentStaticsBean {
            /**
             * totalNum : 121
             * score : 4.78
             * storeUuid : 22025d8777b144ec8a888109a93ffe8e
             * technologyScore : 4.8
             * serviceScore : 4.6
             * environmentScore : 5
             */

            private int totalNum;
            private double score;
            private String storeUuid;
            private double technologyScore;
            private double serviceScore;
            private int environmentScore;

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getStoreUuid() {
                return storeUuid;
            }

            public void setStoreUuid(String storeUuid) {
                this.storeUuid = storeUuid;
            }

            public double getTechnologyScore() {
                return technologyScore;
            }

            public void setTechnologyScore(double technologyScore) {
                this.technologyScore = technologyScore;
            }

            public double getServiceScore() {
                return serviceScore;
            }

            public void setServiceScore(double serviceScore) {
                this.serviceScore = serviceScore;
            }

            public int getEnvironmentScore() {
                return environmentScore;
            }

            public void setEnvironmentScore(int environmentScore) {
                this.environmentScore = environmentScore;
            }
        }

        public static class StoreAccountReqBean {
            /**
             * uuid : bb5d1b206cbf449d90be883951d21919
             * withdrawalWay : null
             * accountType : null
             * accountName : null
             * depositBank : 中国农业银行
             * subBranchName : null
             * cardNumbers : klonkj
             * alipayAccount : adel
             * weChatAccount : 5666552222
             */

            private String uuid;
            private String withdrawalWay;
            private String accountType;
            private String accountName;
            private String depositBank;
            private String subBranchName;
            private String cardNumbers;
            private String alipayAccount;
            private String weChatAccount;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getWithdrawalWay() {
                return withdrawalWay;
            }

            public void setWithdrawalWay(String withdrawalWay) {
                this.withdrawalWay = withdrawalWay;
            }

            public String getAccountType() {
                return accountType;
            }

            public void setAccountType(String accountType) {
                this.accountType = accountType;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getDepositBank() {
                return depositBank;
            }

            public void setDepositBank(String depositBank) {
                this.depositBank = depositBank;
            }

            public String getSubBranchName() {
                return subBranchName;
            }

            public void setSubBranchName(String subBranchName) {
                this.subBranchName = subBranchName;
            }

            public String getCardNumbers() {
                return cardNumbers;
            }

            public void setCardNumbers(String cardNumbers) {
                this.cardNumbers = cardNumbers;
            }

            public String getAlipayAccount() {
                return alipayAccount;
            }

            public void setAlipayAccount(String alipayAccount) {
                this.alipayAccount = alipayAccount;
            }

            public String getWeChatAccount() {
                return weChatAccount;
            }

            public void setWeChatAccount(String weChatAccount) {
                this.weChatAccount = weChatAccount;
            }
        }

        public static class StoreUserResListBean {
            /**
             * uuid : 2d0c55bd42c949f085f173cc96946032
             * userName : 王大锤
             * mobile : 13566666666
             * personType : null
             * email : null
             * storeUuid : 22025d8777b144ec8a888109a93ffe8e
             *
             */

            private String uuid;
            private String userName;
            private String mobile;
            private String renType;
            private Object personType;
            private Object email;
            private String storeUuid;
            private String position;

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getRenType() {
                return renType;
            }

            public void setRenType(String renType) {
                this.renType = renType;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public Object getPersonType() {
                return personType;
            }

            public void setPersonType(Object personType) {
                this.personType = personType;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getStoreUuid() {
                return storeUuid;
            }

            public void setStoreUuid(String storeUuid) {
                this.storeUuid = storeUuid;
            }
        }
    }
}
