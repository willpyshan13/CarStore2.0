package com.yanxin.store.req;

import java.util.ArrayList;
import java.util.List;

public class TechnicianRegisterReq {

    /**
     * addressCity : string
     * addressCounty : string
     * addressDetail : string
     * addressProvince : string
     * answerAmt : 0
     * answerSortWeight : 0
     * brandUuidArrayList : ["string"]
     * certificateNum : string
     * certificateType : string
     * checkSts : 0
     * cybAuth : 0
     * driverLicenseBackUrl : string
     * driverLicenseUrl : string
     * healthCheckUrl : string
     * hostAuthentication : string
     * hostImgArrayList : ["string"]
     * identityCardBackUrl : string
     * identityCardUrl : string
     * mobile : string
     * noCrimeUrl : string
     * photoImgUrl : string
     * rejectDetail : string
     * relationStoreUuid : string
     * relativeMobile : string
     * stateImgArrayList : ["string"]
     * stateVerification : string
     * technicianAccount : {"accountName":"string","alipayAccount":"string","cardNumbers":"string","debitCardBackUrl":"string","debitCardUrl":"string","depositBank":"string","subBranchName":"string","weChatAccount":"string"}
     * technologyType : string
     * userName : string
     * uuid : string
     * workingYear : 0
     */

    private String addressCity;
    private String addressCounty;
    private String addressDetail;
    private String addressProvince;
    private int answerAmt;
    private int answerSortWeight;
    private List<String> brandUuidList;
    private String certificateNum;
    private String certificateType;
    private int checkSts;
    private int cybAuth;
    private String driverLicenseBackUrl;
    private String driverLicenseUrl;
    private String healthCheckUrl;
    private String hostAuthentication;
    private ArrayList<String> hostImgList;
    private String identityCardBackUrl;
    private String identityCardUrl;
    private String mobile;
    private String noCrimeUrl;
    private String photoImgUrl;
    private String rejectDetail;
    private String relationStoreUuid;
    private String electricianCertificateUrl;
    private String electricianCertificateBackUrl;
    private String relativeMobile;
    private ArrayList<String> stateImgList;
    private String stateVerification;
    private TechnicianAccountBean technicianAccount;
    private String technologyType;
    private String userName;
    private String uuid;
    private int workingYear;

    public String getElectricianCertificateUrl() {
        return electricianCertificateUrl;
    }

    public void setElectricianCertificateUrl(String electricianCertificateUrl) {
        this.electricianCertificateUrl = electricianCertificateUrl;
    }

    public String getElectricianCertificateBackUrl() {
        return electricianCertificateBackUrl;
    }

    public void setElectricianCertificateBackUrl(String electricianCertificateBackUrl) {
        this.electricianCertificateBackUrl = electricianCertificateBackUrl;
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

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public int getAnswerAmt() {
        return answerAmt;
    }

    public void setAnswerAmt(int answerAmt) {
        this.answerAmt = answerAmt;
    }

    public int getAnswerSortWeight() {
        return answerSortWeight;
    }

    public void setAnswerSortWeight(int answerSortWeight) {
        this.answerSortWeight = answerSortWeight;
    }

    public List<String> getBrandUuidList() {
        return brandUuidList;
    }

    public void setBrandUuidArrayList(List<String> brandUuidArrayList) {
        this.brandUuidList = brandUuidArrayList;
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

    public ArrayList<String> getHostImgList() {
        return hostImgList;
    }

    public void setHostImgList(ArrayList<String> hostImgList) {
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

    public String getPhotoImgUrl() {
        return photoImgUrl;
    }

    public void setPhotoImgUrl(String photoImgUrl) {
        this.photoImgUrl = photoImgUrl;
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

    public ArrayList<String> getStateImgList() {
        return stateImgList;
    }

    public void setStateImgList(ArrayList<String> stateImgList) {
        this.stateImgList = stateImgList;
    }

    public String getStateVerification() {
        return stateVerification;
    }

    public void setStateVerification(String stateVerification) {
        this.stateVerification = stateVerification;
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
         * accountName : string
         * alipayAccount : string
         * cardNumbers : string
         * debitCardBackUrl : string
         * debitCardUrl : string
         * depositBank : string
         * subBranchName : string
         * weChatAccount : string
         */

        private String accountName;
        private String alipayAccount;
        private String cardNumbers;
        private String debitCardBackUrl;
        private String debitCardUrl;
        private String depositBank;
        private String subBranchName;
        private String weChatAccount;

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

        public String getWeChatAccount() {
            return weChatAccount;
        }

        public void setWeChatAccount(String weChatAccount) {
            this.weChatAccount = weChatAccount;
        }
    }
}
