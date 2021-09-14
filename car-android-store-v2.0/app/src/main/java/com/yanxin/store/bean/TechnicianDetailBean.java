package com.yanxin.store.bean;

import com.yanxin.store.base.BaseBean;
import com.yanxin.store.utils.BasicUtils;

import java.util.List;

public class TechnicianDetailBean extends BaseBean {

    /**
     * code : 0000
     * data : {"addressCity":"string","addressCounty":"string","addressDetail":"string","addressLatitude":0,"addressLongitude":0,"addressProvince":"string","answerAmt":0,"brandList":[{"brandName":"string","brandUuid":"string","technicianUuid":"string","uuid":"string"}],"caseCount":0,"certificateNum":"string","certificateType":"string","checkSts":0,"cybAuth":0,"driverLicenseBackUrl":"string","driverLicenseUrl":"string","healthCheckUrl":"string","hostAuthentication":"string","hostImgList":["string"],"identityCardBackUrl":"string","identityCardUrl":"string","mobile":"string","noCrimeUrl":"string","orderCount":0,"photoImgUrl":"string","platformMoney":0,"qaCount":0,"rejectDetail":"string","relationStoreUuid":"string","relativeMobile":"string","score":0,"scoreCount":0,"shareNum":0,"stateImgList":["string"],"stateVerification":"string","supportCount":0,"technicianAccount":{"accountAmount":0,"accountName":"string","alipayAccount":"string","cardNumbers":"string","debitCardBackUrl":"string","debitCardUrl":"string","depositBank":"string","subBranchName":"string","technicianUuid":"string","totalAmount":0,"uuid":"string","waitAmount":0,"weChatAccount":"string","withdrawAmount":0},"technologyType":"string","technologyTypeName":"string","userName":"string","uuid":"string","workingYear":0}
     * msg : 操作成功
     * success : true
     */

    private String code;
    private DataBean data;
    private String msg;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * addressCity : string
         * addressCounty : string
         * addressDetail : string
         * addressLatitude : 0
         * addressLongitude : 0
         * addressProvince : string
         * answerAmt : 0
         * brandList : [{"brandName":"string","brandUuid":"string","technicianUuid":"string","uuid":"string"}]
         * caseCount : 0
         * certificateNum : string
         * certificateType : string
         * checkSts : 0
         * cybAuth : 0
         * driverLicenseBackUrl : string
         * driverLicenseUrl : string
         * healthCheckUrl : string
         * hostAuthentication : string
         * hostImgList : ["string"]
         * identityCardBackUrl : string
         * identityCardUrl : string
         * mobile : string
         * noCrimeUrl : string
         * orderCount : 0
         * photoImgUrl : string
         * platformMoney : 0
         * qaCount : 0
         * rejectDetail : string
         * relationStoreUuid : string
         * relativeMobile : string
         * score : 0
         * scoreCount : 0
         * shareNum : 0
         * stateImgList : ["string"]
         * stateVerification : string
         * supportCount : 0
         * technicianAccount : {"accountAmount":0,"accountName":"string","alipayAccount":"string","cardNumbers":"string","debitCardBackUrl":"string","debitCardUrl":"string","depositBank":"string","subBranchName":"string","technicianUuid":"string","totalAmount":0,"uuid":"string","waitAmount":0,"weChatAccount":"string","withdrawAmount":0}
         * technologyType : string
         * technologyTypeName : string
         * userName : string
         * uuid : string
         * workingYear : 0
         */

        private String addressCity;
        private String addressCounty;
        private String addressDetail;
        private double addressLatitude;
        private double addressLongitude;
        private String addressProvince;
        private String addressCountyName;
        private String addressCityName;
        private String addressProvinceName;
        private float answerAmt;
        private List<BrandListBean> brandList;
        private int caseCount;
        private String certificateNum;
        private String certificateType;
        private int checkSts;
        private int cybAuth;
        private String driverLicenseBackUrl;
        private String driverLicenseUrl;
        private String healthCheckUrl;
        private String hostAuthentication;
        private List<String> hostImgList;
        private String identityCardBackUrl;
        private String identityCardUrl;
        private String mobile;
        private String noCrimeUrl;
        private int orderCount;
        private String photoImgUrl;
        private float platformMoney;
        private int qaCount;
        private String rejectDetail;
        private String relationStoreUuid;
        private String relativeMobile;
        private float score;
        private int scoreCount;
        private int shareNum;
        private List<String> stateImgList;
        private String stateVerification;
        private int supportCount;
        private TechnicianAccountBean technicianAccount;
        private String technologyType;
        private String electricianCertificateBackUrl;
        private String electricianCertificateUrl ;
        private String technologyTypeName;
        private String userName;
        private String uuid;
        private int workingYear;

        public String getAddressCountyName() {
            return addressCountyName;
        }

        public void setAddressCountyName(String addressCountyName) {
            this.addressCountyName = addressCountyName;
        }

        public String getAddressCityName() {
            return addressCityName;
        }

        public void setAddressCityName(String addressCityName) {
            this.addressCityName = addressCityName;
        }

        public String getAddressProvinceName() {
            return addressProvinceName;
        }

        public void setAddressProvinceName(String addressProvinceName) {
            this.addressProvinceName = addressProvinceName;
        }

        public String getElectricianCertificateBackUrl() {
            return electricianCertificateBackUrl;
        }

        public void setElectricianCertificateBackUrl(String electricianCertificateBackUrl) {
            this.electricianCertificateBackUrl = electricianCertificateBackUrl;
        }

        public String getElectricianCertificateUrl() {
            return electricianCertificateUrl;
        }

        public void setElectricianCertificateUrl(String electricianCertificateUrl) {
            this.electricianCertificateUrl = electricianCertificateUrl;
        }

        public String getAddressCity() {
            return addressCity;
        }

        public void setAddressCity(String addressCity) {
            this.addressCity = addressCity;
        }

        public String getAddressCounty() {
            return addressCounty;
        }

        public void setAddressCounty(String addressCounty) {
            this.addressCounty = addressCounty;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public double getAddressLatitude() {
            return addressLatitude;
        }

        public void setAddressLatitude(double addressLatitude) {
            this.addressLatitude = addressLatitude;
        }

        public double getAddressLongitude() {
            return addressLongitude;
        }

        public void setAddressLongitude(double addressLongitude) {
            this.addressLongitude = addressLongitude;
        }

        public String getAddressProvince() {
            return addressProvince;
        }

        public void setAddressProvince(String addressProvince) {
            this.addressProvince = addressProvince;
        }

        public String getAnswerAmt() {
            return BasicUtils.floatDecimalFormat(answerAmt);
        }

        public void setAnswerAmt(float answerAmt) {
            this.answerAmt = answerAmt;
        }

        public List<BrandListBean> getBrandList() {
            return brandList;
        }

        public void setBrandList(List<BrandListBean> brandList) {
            this.brandList = brandList;
        }

        public int getCaseCount() {
            return caseCount;
        }

        public void setCaseCount(int caseCount) {
            this.caseCount = caseCount;
        }

        public String getCertificateNum() {
            return certificateNum;
        }

        public void setCertificateNum(String certificateNum) {
            this.certificateNum = certificateNum;
        }

        public String getCertificateType() {
            return certificateType;
        }

        public void setCertificateType(String certificateType) {
            this.certificateType = certificateType;
        }

        public int getCheckSts() {
            return checkSts;
        }

        public void setCheckSts(int checkSts) {
            this.checkSts = checkSts;
        }

        public int getCybAuth() {
            return cybAuth;
        }

        public void setCybAuth(int cybAuth) {
            this.cybAuth = cybAuth;
        }

        public String getDriverLicenseBackUrl() {
            return driverLicenseBackUrl;
        }

        public void setDriverLicenseBackUrl(String driverLicenseBackUrl) {
            this.driverLicenseBackUrl = driverLicenseBackUrl;
        }

        public String getDriverLicenseUrl() {
            return driverLicenseUrl;
        }

        public void setDriverLicenseUrl(String driverLicenseUrl) {
            this.driverLicenseUrl = driverLicenseUrl;
        }

        public String getHealthCheckUrl() {
            return healthCheckUrl;
        }

        public void setHealthCheckUrl(String healthCheckUrl) {
            this.healthCheckUrl = healthCheckUrl;
        }

        public String getHostAuthentication() {
            return hostAuthentication;
        }

        public void setHostAuthentication(String hostAuthentication) {
            this.hostAuthentication = hostAuthentication;
        }

        public List<String> getHostImgList() {
            return hostImgList;
        }

        public void setHostImgList(List<String> hostImgList) {
            this.hostImgList = hostImgList;
        }

        public String getIdentityCardBackUrl() {
            return identityCardBackUrl;
        }

        public void setIdentityCardBackUrl(String identityCardBackUrl) {
            this.identityCardBackUrl = identityCardBackUrl;
        }

        public String getIdentityCardUrl() {
            return identityCardUrl;
        }

        public void setIdentityCardUrl(String identityCardUrl) {
            this.identityCardUrl = identityCardUrl;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNoCrimeUrl() {
            return noCrimeUrl;
        }

        public void setNoCrimeUrl(String noCrimeUrl) {
            this.noCrimeUrl = noCrimeUrl;
        }

        public int getOrderCount() {
            return orderCount;
        }

        public void setOrderCount(int orderCount) {
            this.orderCount = orderCount;
        }

        public String getPhotoImgUrl() {
            return photoImgUrl;
        }

        public void setPhotoImgUrl(String photoImgUrl) {
            this.photoImgUrl = photoImgUrl;
        }

        public String getPlatformMoney() {
            return BasicUtils.floatDecimalFormat(platformMoney);
        }

        public void setPlatformMoney(float platformMoney) {
            this.platformMoney = platformMoney;
        }

        public int getQaCount() {
            return qaCount;
        }

        public void setQaCount(int qaCount) {
            this.qaCount = qaCount;
        }

        public String getRejectDetail() {
            return rejectDetail;
        }

        public void setRejectDetail(String rejectDetail) {
            this.rejectDetail = rejectDetail;
        }

        public String getRelationStoreUuid() {
            return relationStoreUuid;
        }

        public void setRelationStoreUuid(String relationStoreUuid) {
            this.relationStoreUuid = relationStoreUuid;
        }

        public String getRelativeMobile() {
            return relativeMobile;
        }

        public void setRelativeMobile(String relativeMobile) {
            this.relativeMobile = relativeMobile;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        public int getScoreCount() {
            return scoreCount;
        }

        public void setScoreCount(int scoreCount) {
            this.scoreCount = scoreCount;
        }

        public int getShareNum() {
            return shareNum;
        }

        public void setShareNum(int shareNum) {
            this.shareNum = shareNum;
        }

        public List<String> getStateImgList() {
            return stateImgList;
        }

        public void setStateImgList(List<String> stateImgList) {
            this.stateImgList = stateImgList;
        }

        public String getStateVerification() {
            return stateVerification;
        }

        public void setStateVerification(String stateVerification) {
            this.stateVerification = stateVerification;
        }

        public int getSupportCount() {
            return supportCount;
        }

        public void setSupportCount(int supportCount) {
            this.supportCount = supportCount;
        }

        public TechnicianAccountBean getTechnicianAccount() {
            return technicianAccount;
        }

        public void setTechnicianAccount(TechnicianAccountBean technicianAccount) {
            this.technicianAccount = technicianAccount;
        }

        public String getTechnologyType() {
            return technologyType;
        }

        public void setTechnologyType(String technologyType) {
            this.technologyType = technologyType;
        }

        public String getTechnologyTypeName() {
            return technologyTypeName;
        }

        public void setTechnologyTypeName(String technologyTypeName) {
            this.technologyTypeName = technologyTypeName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getWorkingYear() {
            return workingYear;
        }

        public void setWorkingYear(int workingYear) {
            this.workingYear = workingYear;
        }

        public static class TechnicianAccountBean {
            /**
             * accountAmount : 0
             * accountName : string
             * alipayAccount : string
             * cardNumbers : string
             * debitCardBackUrl : string
             * debitCardUrl : string
             * depositBank : string
             * subBranchName : string
             * technicianUuid : string
             * totalAmount : 0
             * uuid : string
             * waitAmount : 0
             * weChatAccount : string
             * withdrawAmount : 0
             */

            private int accountAmount;
            private String accountName;
            private String alipayAccount;
            private String cardNumbers;
            private String debitCardBackUrl;
            private String debitCardUrl;
            private String depositBank;
            private String subBranchName;
            private String technicianUuid;
            private int totalAmount;
            private String uuid;
            private int waitAmount;
            private String weChatAccount;
            private int withdrawAmount;

            public int getAccountAmount() {
                return accountAmount;
            }

            public void setAccountAmount(int accountAmount) {
                this.accountAmount = accountAmount;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
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

            public String getDebitCardBackUrl() {
                return debitCardBackUrl;
            }

            public void setDebitCardBackUrl(String debitCardBackUrl) {
                this.debitCardBackUrl = debitCardBackUrl;
            }

            public String getDebitCardUrl() {
                return debitCardUrl;
            }

            public void setDebitCardUrl(String debitCardUrl) {
                this.debitCardUrl = debitCardUrl;
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

            public String getTechnicianUuid() {
                return technicianUuid;
            }

            public void setTechnicianUuid(String technicianUuid) {
                this.technicianUuid = technicianUuid;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public int getWaitAmount() {
                return waitAmount;
            }

            public void setWaitAmount(int waitAmount) {
                this.waitAmount = waitAmount;
            }

            public String getWeChatAccount() {
                return weChatAccount;
            }

            public void setWeChatAccount(String weChatAccount) {
                this.weChatAccount = weChatAccount;
            }

            public int getWithdrawAmount() {
                return withdrawAmount;
            }

            public void setWithdrawAmount(int withdrawAmount) {
                this.withdrawAmount = withdrawAmount;
            }
        }

        public static class BrandListBean {
            /**
             * brandName : string
             * brandUuid : string
             * technicianUuid : string
             * uuid : string
             */

            private String brandName;
            private String brandUuid;
            private String technicianUuid;
            private String uuid;

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getBrandUuid() {
                return brandUuid;
            }

            public void setBrandUuid(String brandUuid) {
                this.brandUuid = brandUuid;
            }

            public String getTechnicianUuid() {
                return technicianUuid;
            }

            public void setTechnicianUuid(String technicianUuid) {
                this.technicianUuid = technicianUuid;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
