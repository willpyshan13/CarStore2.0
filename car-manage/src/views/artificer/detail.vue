<template>
    <div class="app-container">
        <div class="page-detail">
            <el-form
                v-loading="loading"
                label-position="right"
                :model="form"
                :rules="rules"
                ref="form"
                label-width="140px"
            >
                <!--基本信息-->
                <div class="section">
                    <div class="title">
                        基本信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="姓名" prop="userName" ref="userName">
                                    <el-input clearable v-model="form.userName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="手机号" prop="mobile" ref="mobile">
                                    <el-input clearable v-model="form.mobile"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="证件类型" prop="certificateType" ref="certificateType">
                                    <el-select clearable v-model="form.certificateType">
                                        <el-option v-for="(item,index) in certificateType" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="证件号码" prop="certificateNum" ref="certificateNum">
                                    <el-input clearable v-model="form.certificateNum"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="地区" prop="addressCity" ref="addressCity">
                                    <el-row>
                                        <el-col :span="11">
                                            <el-form-item label="">
                                                <el-select clearable v-model="form.addressProvince" placeholder="请选择省"
                                                           @change="handleProvinceChange">
                                                    <el-option v-for="(item,index) in provinceList" :key="index"
                                                               :label="item.areaName" :value="item.uuid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="11" :offset="2" >
                                            <el-form-item label="">
                                                <el-select clearable v-model="form.addressCity" placeholder="请选择市">
                                                    <el-option v-for="(item,index) in cityList" :key="index"
                                                               :label="item.areaName" :value="item.uuid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="详细地址" prop="addressDetail" ref="addressDetail">
                                    <el-input clearable v-model="form.addressDetail"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="维修品牌" prop="brandList" ref="brandList">
                                    <el-select clearable multiple v-model="form.brandList">
                                        <el-option v-for="(item,index) in repairBrand" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="技术类型" prop="technologyType" ref="technologyType">
                                    <el-select clearable v-model="form.technologyType">
                                        <el-option v-for="(item,index) in technicianType" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="工龄" prop="workingYear" ref="workingYear">
                                    <el-input-number :controls="false" :step="1" step-strictly v-model="form.workingYear"></el-input-number>
                                    <span class="warning-text" style="padding-left:10px;">年</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="直系亲属联系方式" prop="relativeMobile" ref="relativeMobile">
                                    <el-input clearable v-model="form.relativeMobile"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <!--**********************************************************-->
						<el-row>
                            <el-col :span="12">
                            	<!--{{form.cybAuth}}-->
                                <el-form-item label="技师等级">
                                	<el-radio-group v-model="cybAuth">
	                                    <el-radio label="0">普通</el-radio>
	  									<el-radio label="1">专家</el-radio>
  									</el-radio-group>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                
                            </el-col>
                        </el-row>
                        <!--**********************************************************-->
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="驾驶证主页" prop="driverLicenseUrl" ref="driverLicenseUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.driverLicenseUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="驾驶证副页" prop="driverLicenseBackUrl" ref="driverLicenseBackUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.driverLicenseBackUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="身份证正面" prop="identityCardUrl" ref="identityCardUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.identityCardUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="身份证反面" prop="identityCardBackUrl" ref="identityCardBackUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.identityCardBackUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="无犯罪记录证明" prop="noCrimeUrl" ref="noCrimeUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.noCrimeUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="健康证明" prop="healthCheckUrl" ref="healthCheckUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.healthCheckUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <!--主机厂认证-->
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="主机厂认证等级" prop="hostAuthentication" ref="hostAuthentication">
                                    <el-select clearable  v-model="form.hostAuthentication">
                                        <el-option v-for="(item,index) in hostAuthentication" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="主机厂认证图" prop="hostImgList" ref="hostImgList">
                                    <upload-pic
                                        :multiple="false"
                                        :file-list="form.hostImgList"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <!--国家等级鉴定-->
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="国家等级鉴定" prop="stateVerification" ref="stateVerification">
                                    <el-select clearable  v-model="form.stateVerification">
                                        <el-option v-for="(item,index) in stateVerification" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="国家等级鉴定图" prop="stateImgList" ref="stateImgList">
                                    <upload-pic
                                        :multiple="false"
                                        :file-list="form.stateImgList"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <!--账户信息-->
                <div class="section">
                    <div class="title">
                        账户信息(<span style="color:red;"> 账户余额：{{ form.technicianAccount.accountAmount || 0 }}</span> )
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="账户名称" prop="technicianAccount.accountName" ref="technicianAccount.accountName">
                                    <el-input clearable v-model="form.technicianAccount.accountName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="开户银行" prop="technicianAccount.depositBank" ref="technicianAccount.depositBank">
                                    <el-select clearable v-model="form.technicianAccount.depositBank">
                                        <el-option
                                            v-for="(item,index) in depositBank"
                                            :key="index"
                                            :label="item.lableDesc"
                                            :value="item.uuid"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="开户支行" prop="technicianAccount.subBranchName" ref="technicianAccount.subBranchName">
                                    <el-input clearable v-model="form.technicianAccount.subBranchName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="银行卡号" prop="technicianAccount.cardNumbers" ref="technicianAccount.cardNumbers">
                                    <el-input clearable v-model="form.technicianAccount.cardNumbers"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="支付宝账号" prop="technicianAccount.alipayAccount" ref="technicianAccount.alipayAccount">
                                    <el-input clearable v-model="form.technicianAccount.alipayAccount"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="微信账号" prop="technicianAccount.weChatAccount" ref="technicianAccount.weChatAccount">
                                    <el-input clearable v-model="form.technicianAccount.weChatAccount"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="平台借记卡正面" prop="technicianAccount.debitCardUrl" ref="technicianAccount.debitCardUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.technicianAccount.debitCardUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="平台借记卡反面" prop="technicianAccount.debitCardBackUrl" ref="technicianAccount.debitCardBackUrl">
                                    <upload-pic
                                        :multiple="false"
                                        :limit="1"
                                        :file-list="form.technicianAccount.debitCardBackUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <!--关联店铺-->
                <div class="section">
                    <div class="title">
                        关联店铺
                    </div>
                    <div class="content">
                        <el-row v-if="storeHistory.uuid">
                            <el-col :span="12">
                                <el-form-item label="最近店铺名称">
                                    <el-input disabled :value="storeHistory.storeName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="店铺地址">
                                    <el-input disabled :value="storeHistory.address"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
<!--                                <el-form-item label="店铺名称" prop="relationStoreUuid" ref="relationStoreUuid">-->
<!--                                    <el-select clearable v-model="form.relationStoreUuid">-->
<!--                                        <el-option-->
<!--                                            v-for="(item,index) in storeList"-->
<!--                                            :key="item.uuid"-->
<!--                                            :label="item.storeName"-->
<!--                                            :value="item.uuid"-->
<!--                                        ></el-option>-->
<!--                                    </el-select>-->
<!--                                </el-form-item>-->
                                <el-form-item label="店铺名称">
                                    <el-input disabled :value="storeInfo.storeName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="店铺地址">
                                    <el-input disabled :value="storeInfo.address"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <div class="section">
                    <div class="title">
                        完成订单 <span style="padding:0 10px;color:#409EFF">{{ form.orderCount }}</span>单 总资产<span
                        style="padding:0 10px;color:red">{{ form.totalAmount || 0 }}</span>已提现<span
                        style="padding:0 10px;color:red">{{ form.withdrawAmount || 0 }}</span>评价分数<span
                        style="padding:0 10px;color:orange">{{ form.score || 0 }}分</span>总评分次数<span
                        style="padding:0 10px;color:orange">{{ form.scoreCount || 0 }}次</span><a
                        style="font-size:10px;text-decoration:underline;color:rgb(64, 158, 255);" @click="handleLookWithdraw">点击查看提现记录</a>

                    </div>
                    <div class="content"></div>
                </div>
                <!--审核-->
                <div class="section" style="margin-top:20px;">
                    <div class="title">
                        信息审核
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="审核" prop="checkSts" ref="checkSts">
                                    <el-select clearable v-model="form.checkSts">
                                        <el-option
                                            v-for="(item,index) in checkSts"
                                            :key="index"
                                            :label="item.lableDesc"
                                            :value="item.uuid"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row v-if="form.checkSts == 2">
                            <el-col :span="12">
                                <el-form-item label="驳回原因" prop="rejectDetail" ref="rejectDetail">
                                    <el-input
                                        clearable
                                        type="textarea"
                                        :rows="2"
                                        v-model="form.rejectDetail"
                                    >
                                    </el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <div class="section section-btn-list">
                    <div class="content section-btn-content">
                        <el-button class="save-btn" size="lage" type="primary" @click="handleSubmit">提交</el-button>
                    </div>
                </div>
            </el-form>
        </div>


        <!--提现记录列表-->
        <el-dialog width="80%" title="提现记录" :visible.sync="dialogTableVisible">
            <el-table
                :data="tableData"
                border
                style="width: 100%">
                <el-table-column
                    prop="withdrawalAmount"
                    label="提现金额">
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="发起时间">
                </el-table-column>
                <el-table-column
                    label="审核状态">
                    <template slot-scope="{row,$index}">
                        {{checkSts.find((item) => item.uuid == row.checkSts) ? checkSts.find((item) => item.uuid == row.checkSts).lableDesc : ''}}
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page.sync="pagination.page"
                    :page-size="pagination.size"
                    background
                    layout="total, prev, pager, next"
                    :total="pagination.total"
                >
                </el-pagination>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import UploadPic from "@/components/UploadPic"
import {queryTechnicianDetail, updateTechnician, queryLastStoreHistory,queryBrandList} from "@/api/artificer";
import {queryAreaList} from "@/api/area";
import {queryStoreDetail, updateStore} from "@/api/store";
import {queryWithdrawalList} from "@/api/withdraw";

let rules = {
    userName: [
        {required: true, message: '请输入姓名', trigger: 'blur'}
    ],
    mobile: [
        {required: true, message: '请输入手机号', trigger: 'blur'}
    ],
    // certificateType: [
    //     {required: true, message: '请选择证件类型', trigger: 'change'}
    // ],
    // certificateNum: [
    //     {required: true, message: '请输入证件号码', trigger: 'blur'}
    // ],
    brandList: [
        {required: true, message: '请选择维修品牌', trigger: 'change'}
    ],
    addressProvince: [
        {required: true, message: '请选择省', trigger: 'change'}
    ],
    addressCity: [
        {required: true, message: '请选择省市', trigger: 'change'}
    ],
    addressDetail: [
        {required: true, message: '请输入详细地址', trigger: 'blur'}
    ],
    technologyType: [
        {required: true, message: '请选择技术类型', trigger: 'change'}
    ],
    workingYear: [
        {required: true, message: '请输入工龄', trigger: 'blur'}
    ],
    // relativeMobile: [
    //     {required: true, message: '请输入直系亲属联系方式', trigger: 'blur'}
    // ],
    // 'technicianAccount.accountName': [
    //     {required: true, message: '请输入账户名称', trigger: 'blur'}
    // ],
    // 'technicianAccount.depositBank': [
    //     {required: true, message: '请选择开户银行', trigger: 'change'}
    // ],
    // 'technicianAccount.subBranchName': [
    //     {required: true, message: '请输入开户支行', trigger: 'blur'}
    // ],
    // 'technicianAccount.cardNumbers': [
    //     {required: true, message: '请输入银行卡号', trigger: 'blur'}
    // ],
    // 'technicianAccount.alipayAccount': [
    //     {required: true, message: '请输入支付宝账号', trigger: 'blur'}
    // ],
    // driverLicenseUrl: [
    //     {type: 'array', required: true, message: '请上传驾驶证主页', trigger: 'change'}
    // ],
    // driverLicenseBackUrl: [
    //     {type: 'array', required: true, message: '请上传驾驶证副页', trigger: 'change'}
    // ],
    // identityCardUrl: [
    //     {type: 'array', required: true, message: '请上传身份证正面', trigger: 'change'}
    // ],
    // identityCardBackUrl: [
    //     {type: 'array', required: true, message: '请上传身份证反面', trigger: 'change'}
    // ],
    // noCrimeUrl: [
    //     {type: 'array', required: true, message: '请上传无犯罪记录证明', trigger: 'change'}
    // ],
    // healthCheckUrl: [
    //     {type: 'array', required: true, message: '请上传健康证明', trigger: 'change'}
    // // ],
    // 'technicianAccount.debitCardUrl': [
    //     {type: 'array', required: true, message: '请上传平台借记卡正面', trigger: 'change'}
    // ],
    // 'technicianAccount.debitCardBackUrl': [
    //     {type: 'array', required: true, message: '请上传平台借记卡反面', trigger: 'change'}
    // ],
    checkSts: [
        {required: true, message: '请选择审核状态', trigger: 'change'}
    ],
    rejectDetail: [
        {required: true, message: '请输入驳回原因', trigger: 'blur'}
    ],
}

export default {
    name: 'ArtificerDetail',
    components: {
        'upload-pic': UploadPic
    },
    data() {
        return {
            loading: true,
            uuid: '',
            provinceList: [],
            cityList: [],
            certificateType: [],
            repairBrand: [],
            depositBank: [],
            storeList: [],
            technicianType: [],
            hostAuthentication: [],
            stateVerification: [],
            cybAuth:"0",
            form: {
                technicianAccount:{}
            },
            dialogTableVisible: false,
            tableData: [],
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            storeHistory: {},
            storeInfo: {},
            rules:{}
        }
    },
    computed: {
        ...mapGetters([
            'checkSts',
        ])
    },
    async created() {
        this.uuid = this.$route.query.id;
        await this.initDict();
        await this._queryAreaList(-1, 'province');
        await this.initBrandList();
        this.initData();
        await this._queryLastStoreHistory();
    },
    methods: {
        initData() {
            this.loading = true;
            queryTechnicianDetail(this.uuid).then((res) => {
                let {data} = res;
                if (data.addressProvince) {
                    this._queryAreaList(data.addressProvince, 'city');
                }
                data.brandList = data.brandList.map((item) => item.brandUuid);
                data.driverLicenseUrl = data.driverLicenseUrl ? [{url: data.driverLicenseUrl}] : []
                data.driverLicenseBackUrl = data.driverLicenseBackUrl ? [{url: data.driverLicenseBackUrl}] : []
                data.identityCardUrl = data.identityCardUrl ? [{url: data.identityCardUrl}] : []
                data.identityCardBackUrl = data.identityCardBackUrl ? [{url: data.identityCardBackUrl}] : []
                data.noCrimeUrl = data.noCrimeUrl ? [{url: data.noCrimeUrl}] : []
                data.healthCheckUrl = data.healthCheckUrl ? [{url: data.healthCheckUrl}] : []
                data.technicianAccount = data.technicianAccount || {};
                data.technicianAccount.debitCardUrl = data.technicianAccount.debitCardUrl ? [{url: data.technicianAccount.debitCardUrl}] : []
                data.technicianAccount.debitCardBackUrl = data.technicianAccount.debitCardBackUrl ? [{url: data.technicianAccount.debitCardBackUrl}] : [];
                // 主机厂认证图
                data.hostImgList = data.hostImgList && data.hostImgList.length ? data.hostImgList.map(item => {return {url:item}}) : [];

                // 国家等级鉴定图
                data.stateImgList = data.stateImgList && data.stateImgList.length ? data.stateImgList.map(item => {return {url:item}}) : [];

                if (data.relationStoreUuid) {
                    queryStoreDetail(data.relationStoreUuid).then((res) => {
                        this.storeInfo = res.data;
                        this.storeInfo.address = this._computedStoreAddress(this.storeInfo);
                    })
                }
                this.$nextTick(() => {
                    this.form = data;
                    if(data.cybAuth){                    	
                    	this.cybAuth = data.cybAuth.toString();
                    }else{
                    	this.cybAuth = "0"
                    }
                    this.rules = rules;
                })
                this.loading = false;

            }).catch((e) => {
                console.log(e)
                this.loading = false;
            })
        },
        initBrandList(){
            return new Promise((resolve,reject) => {
                queryBrandList().then((res) => {
                    let {data} = res;
                    let repairBrand = [];
                    repairBrand = data.map((item) => {
                        return {
                            lableDesc:item.configName,
                            uuid:item.uuid,
                        }
                    })
                    this.repairBrand = repairBrand || [];
                    resolve()
                }).catch((e) => {
                    console.log(e)
                })
            })

        },
        async initDict() {
            this.certificateType = await this.$store.dispatch('dict/getDict', 'certificate_type');
            // this.repairBrand = await this.$store.dispatch('dict/getDict', 'repair_brand');
            this.depositBank = await this.$store.dispatch('dict/getDict', 'deposit_bank');
            this.technicianType = await this.$store.dispatch('dict/getDict', 'technician_type');
            this.hostAuthentication = await this.$store.dispatch('dict/getDict', 'host_authentication');
            this.stateVerification = await this.$store.dispatch('dict/getDict', 'state_verification');
        },
        async _queryAreaList(type, dataType) {
            let {data} = await queryAreaList(type);
            switch (dataType) {
                case 'province':
                    this.provinceList = data;
                    break
                case 'city':
                    this.cityList = data;
                    break
                case 'district':
                    this.districtList = data;
                    break
            }
        },
        async _queryLastStoreHistory() {
            let {data} = await queryLastStoreHistory(this.uuid);
            if (data && data.storeUuid) {
                queryStoreDetail(data.storeUuid).then((res) => {
                    this.storeHistory = res.data;
                    this.storeHistory.address = this._computedStoreAddress(this.storeHistory);
                });
            }
        },
        handleProvinceChange(val) {
            this.form.addressCity = '';
            this.cityList = [];
            if(val){
                this._queryAreaList(val, 'city')
            }
        },
        _computedStoreAddress(info) {
            return info.addressProvinceName + info.addressCityName + info.addressCountyName + info.companyAddressDetail
        },
        handleSubmit() {
            this.$refs['form'].validate((valid, object) => {
                if (valid) {
                    let driverLicenseUrl = this.form.driverLicenseUrl.length ? this.form.driverLicenseUrl[0].url : '';
                    let driverLicenseBackUrl = this.form.driverLicenseBackUrl.length ? this.form.driverLicenseBackUrl[0].url : '';
                    let identityCardUrl = this.form.identityCardUrl.length ? this.form.identityCardUrl[0].url : '';
                    let identityCardBackUrl = this.form.identityCardBackUrl.length ? this.form.identityCardBackUrl[0].url : '';
                    let noCrimeUrl = this.form.noCrimeUrl.length ? this.form.noCrimeUrl[0].url : '';
                    let healthCheckUrl = this.form.healthCheckUrl.length ? this.form.healthCheckUrl[0].url : '';
                    let debitCardUrl = this.form.technicianAccount.debitCardUrl.length ? this.form.technicianAccount.debitCardUrl[0].url : '';
                    let debitCardBackUrl = this.form.technicianAccount.debitCardBackUrl.length ? this.form.technicianAccount.debitCardBackUrl[0].url : '';

                    let hostImgList = this.form.hostImgList && this.form.hostImgList.length ? this.form.hostImgList.map(item => item.url) : []
                    let stateImgList = this.form.stateImgList && this.form.stateImgList.length ? this.form.stateImgList.map(item => item.url) : []
					this.form['cybAuth'] = this.cybAuth;
                    updateTechnician({
                        ...this.form,
                        driverLicenseUrl,
                        driverLicenseBackUrl,
                        identityCardUrl,
                        identityCardBackUrl,
                        noCrimeUrl,
                        healthCheckUrl,
                        hostImgList,
                        stateImgList,
                        brandUuidList:this.form.brandList,
                        technicianAccount:{
                            ...this.form.technicianAccount,
                            debitCardUrl,
                            debitCardBackUrl
                        }
                    }).then((res) => {
                        this.$message.success('提交成功');
                        this.$router.go(-1)
                    })
                } else {
                    let split = ''
                    for (let i in object) {
                        let dom = this.$refs[i]
                        if (Object.prototype.toString.call(dom) !== '[object Object]') {
                            dom = dom[0]
                            split = dom.prop
                            let index = split.indexOf('.')
                            let last = split.lastIndexOf('.')
                            this.activeName = Number(split.slice(index + 1, last))
                            break
                        }
                        dom.$el.scrollIntoView({
                            block: 'center', //值有start,center,end，nearest，当前显示在视图区域中间
                            behavior: 'smooth' //值有auto、instant,smooth，缓动动画（当前是慢速的）
                        })
                    }
                    return false
                }
            });
        },
        handleLookWithdraw(){
            this._initWithdrawList();
            this.dialogTableVisible = true;
        },
        _initWithdrawList(){
            queryWithdrawalList({
                userUuid:this.uuid,
                pageNum:this.pagination.page,
                pageSize:this.pagination.size
            }).then((res) => {
                let { data,total } = res;
                this.pagination.total = total;
                this.tableData = data || [];
            }).catch((e) => {

            })
        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this._initWithdrawList()
        },
    }
}
</script>

<style lang="scss" scoped>
.page-detail{
    .el-form-item{
        .el-form-item{
            margin-bottom:0!important;
        }
    }
}
</style>
