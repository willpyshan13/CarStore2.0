package com.yanxin.store.req;

import java.util.List;

public class StoreRegisterReq {

    /**
     * brandUuidList : ["ba0628f9d98a44b98698f80a05abe7d0","98aea6bcd2674b3190e1dd71062ee96f","9a79a6b6436649ae8e22be368a27cc14","22f72f047035447db228cd05acec439e","b144fcd919de4154a289717dfe20a1c0"]
     * businessImgList : ["https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-13/fb2cee028c784247b0b40c554678240c.png"]
     * companyAddressProvince : 1
     * companyAddressCity : 2
     * companyAddressCounty : 3
     * companyAddressDetail : 城区
     * companyName : 测试店铺
     * otherImgList : []
     * shopImgList : ["https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-13/d02dd0fc9ac840d1806fe7a0761dde47.png","https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-13/c57e593af6b64e60bd8f0a6067a52cc4.png","https://car2021.oss-cn-beijing.aliyuncs.com/other/2021-07-13/c83e5226d5544d5998ffd4d02f866f7c.png"]
     * storeAccountReq : {"accountName":"对公账户","accountType":"301","alipayAccount":"13333333333","cardNumbers":"3222154879262323","depositBank":"601","subBranchName":"","weChatAccount":"13333333333","withdrawalWay":"201"}
     * storeName : 测试
     * storeType : 101
     * storeUserReq : [{"userName":"开发技术人员","idNum":"","idType":"","email":"","mobile":"15555555555","personType":"501","renType":"管理员"},{"userName":"测试人员","idNum":"","idType":"","email":"","mobile":"18888888888","personType":"504","renType":"零件"}]
     */

    private List<String> brandUuidList;
    private List<String> businessImgList;
    private String companyAddressProvince;
    private String companyAddressCity;
    private String companyAddressCounty;
    private String companyAddressDetail;
    private String companyName;
    private List<String> otherImgList;
    private List<String> shopImgList;
    private StoreAccountReqBean storeAccountReq;
    private String storeName;
    private String storeType;
    private List<StoreUserReqBean> storeUserReq;
    private String uuid;
    private String glyMobile;
    private String legalPersonFront;
    private String legalPersonReverse;

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

    public String getCompanyAddressProvince() {
        return companyAddressProvince;
    }

    public void setCompanyAddressProvince(String companyAddressProvince) {
        this.companyAddressProvince = companyAddressProvince;
    }

    public String getCompanyAddressCity() {
        return companyAddressCity;
    }

    public void setCompanyAddressCity(String companyAddressCity) {
        this.companyAddressCity = companyAddressCity;
    }

    public String getCompanyAddressCounty() {
        return companyAddressCounty;
    }

    public void setCompanyAddressCounty(String companyAddressCounty) {
        this.companyAddressCounty = companyAddressCounty;
    }

    public String getCompanyAddressDetail() {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail) {
        this.companyAddressDetail = companyAddressDetail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getOtherImgList() {
        return otherImgList;
    }

    public void setOtherImgList(List<String> otherImgList) {
        this.otherImgList = otherImgList;
    }

    public List<String> getShopImgList() {
        return shopImgList;
    }

    public void setShopImgList(List<String> shopImgList) {
        this.shopImgList = shopImgList;
    }

    public StoreAccountReqBean getStoreAccountReq() {
        return storeAccountReq;
    }

    public void setStoreAccountReq(StoreAccountReqBean storeAccountReq) {
        this.storeAccountReq = storeAccountReq;
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

    public List<StoreUserReqBean> getStoreUserReq() {
        return storeUserReq;
    }

    public void setStoreUserReq(List<StoreUserReqBean> storeUserReq) {
        this.storeUserReq = storeUserReq;
    }

    public static class StoreAccountReqBean {
        /**
         * accountName : 对公账户
         * accountType : 301
         * alipayAccount : 13333333333
         * cardNumbers : 3222154879262323
         * depositBank : 601
         * subBranchName : 
         * weChatAccount : 13333333333
         * withdrawalWay : 201
         */

        private String accountName;
        private String accountType;
        private String alipayAccount;
        private String cardNumbers;
        private String depositBank;
        private String subBranchName;
        private String weChatAccount;
        private String withdrawalWay;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getAlipayAccount() {
            return alipayAccount;
        }

        public void setAlipayAccount(String alipayAccount) {
            this.alipayAccount = alipayAccount;
        }

        public String getCardNumbers() {
            return cardNumbers;
        }

        public void setCardNumbers(String cardNumbers) {
            this.cardNumbers = cardNumbers;
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

        public String getWeChatAccount() {
            return weChatAccount;
        }

        public void setWeChatAccount(String weChatAccount) {
            this.weChatAccount = weChatAccount;
        }

        public String getWithdrawalWay() {
            return withdrawalWay;
        }

        public void setWithdrawalWay(String withdrawalWay) {
            this.withdrawalWay = withdrawalWay;
        }
    }

    public static class StoreUserReqBean {
        /**
         * userName : 开发技术人员
         * idNum : 
         * idType : 
         * email : 
         * mobile : 15555555555
         * personType : 501
         * renType : 管理员
         */

        private String userName;
        private String idNum;
        private String idType;
        private String email;
        private String mobile;
        private String personType;
        private String renType;
        private String uuid;

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

        public String getIdNum() {
            return idNum;
        }

        public void setIdNum(String idNum) {
            this.idNum = idNum;
        }

        public String getIdType() {
            return idType;
        }

        public void setIdType(String idType) {
            this.idType = idType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPersonType() {
            return personType;
        }

        public void setPersonType(String personType) {
            this.personType = personType;
        }

        public String getRenType() {
            return renType;
        }

        public void setRenType(String renType) {
            this.renType = renType;
        }
    }
}
