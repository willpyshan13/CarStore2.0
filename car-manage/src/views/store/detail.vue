<template>
    <div class="app-container">
        <div class="page-detail">
            <el-form
                v-loading="loading"
                label-position="right"
                :model="form"
                :rules="rules"
                ref="form"
                label-width="120px"
            >
                <!--帐号信息-->
                <div class="section">
                    <div class="title">
                        帐号信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="店铺名称" prop="storeName" ref="storeName">
                                    <el-input clearable v-model="form.storeName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="店铺类型" prop="storeType" ref="storeType">
                                    <el-select clearable v-model="form.storeType">
                                        <el-option v-for="(item,index) in storeType" :key="item.uuid"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <!--企业信息-->
                <div class="section">
                    <div class="title">
                        企业信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="公司名称" prop="companyName" ref="companyName">
                                    <el-input clearable v-model="form.companyName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="公司地址">
                                    <el-row>
                                        <el-col :span="7">
                                            <el-form-item label="" prop="companyAddressProvince" ref="companyAddressProvince">
                                                <el-select clearable v-model="form.companyAddressProvince" placeholder="请选择省"
                                                           @change="handleProvinceChange">
                                                    <el-option v-for="(item,index) in provinceList" :key="item.uuid"
                                                               :label="item.areaName" :value="item.uuid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="7" :offset="1">
                                            <el-form-item label="" prop="companyAddressCity" ref="companyAddressCity">
                                                <el-select clearable v-model="form.companyAddressCity" placeholder="请选择市"
                                                           @change="handleCityChange"
                                                >
                                                    <el-option v-for="(item,index) in cityList" :key="item.uuid"
                                                               :label="item.areaName" :value="item.uuid"></el-option>
                                                </el-select>
                                            </el-form-item>
                                        </el-col>
<!--                                        <el-col :span="7" :offset="1">-->
<!--                                            <el-form-item label="" prop="companyAddressCounty" ref="companyAddressCounty">-->
<!--                                                <el-select clearable v-model="form.companyAddressCounty" placeholder="请选择区">-->
<!--                                                    <el-option v-for="(item,index) in districtList" :key="item.uuid"-->
<!--                                                               :label="item.areaName" :value="item.uuid"></el-option>-->
<!--                                                </el-select>-->
<!--                                            </el-form-item>-->
<!--                                        </el-col>-->
                                    </el-row>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="详细地址" prop="companyAddressDetail" ref="companyAddressDetail">
                                    <el-input clearable v-model="form.companyAddressDetail"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="营业执照" prop="businessImgList" ref="businessImgList">
                                    <upload-pic :limit="3"
                                                :file-list="form.businessImgList"
                                    >
                                    </upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="门店图片" prop="shopImgList" ref="shopImgList">
                                    <upload-pic :file-list="form.shopImgList"></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
<!--                        <el-row>-->
<!--                            <el-col :span="24">-->
<!--                                <el-form-item label="类型图片">-->
<!--                                    <upload-pic></upload-pic>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                        </el-row>-->
                    </div>
                </div>

                <!--联系人信息-->
<!--                <div class="section" v-if="form.storeUserResList && form.storeUserResList.length">-->
<!--                    <div class="title">-->
<!--                        联系人信息-->
<!--                    </div>-->
<!--                    <div class="content"-->
<!--                         v-for="(storeUser,storeIndex) in form.storeUserResList"-->
<!--                         :key="storeIndex"-->
<!--                    >-->
<!--                        <el-row>-->
<!--                            <el-col :span="12">-->
<!--                                <el-form-item-->
<!--                                    label="姓名"-->
<!--                                    :prop="'storeUserResList.' + storeIndex + '.userName'"-->
<!--                                    :ref="'storeUserResList.' + storeIndex + '.userName'"-->
<!--                                    :rules="{-->
<!--                                        required: true, message: '请输入姓名', trigger: 'blur'-->
<!--                                    }"-->
<!--                                >-->
<!--                                    <el-input clearable v-model="storeUser.userName"></el-input>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                            <el-col :span="12">-->
<!--                                <el-form-item-->
<!--                                    label="手机号"-->
<!--                                    :prop="'storeUserResList.' + storeIndex + '.mobile'"-->
<!--                                    :ref="'storeUserResList.' + storeIndex + '.mobile'"-->
<!--                                    :rules="{-->
<!--                                        required: true, message: '请输入手机号', trigger: 'blur'-->
<!--                                    }"-->
<!--                                >-->
<!--                                    <el-input clearable v-model="storeUser.mobile"></el-input>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                        </el-row>-->
<!--                        <el-row>-->
<!--                            <el-col :span="12">-->
<!--                                <el-form-item-->
<!--                                    label="人员类型"-->
<!--                                    :prop="'storeUserResList.' + storeIndex + '.personType'"-->
<!--                                    :ref="'storeUserResList.' + storeIndex + '.personType'"-->
<!--                                    :rules="{-->
<!--                                        required: true, message: '请选择人员类型', trigger: 'change'-->
<!--                                    }"-->
<!--                                >-->
<!--                                    <el-select clearable v-model="storeUser.personType">-->
<!--                                        <el-option-->
<!--                                            v-for="(item,index) in personType"-->
<!--                                            :key="item.uuid"-->
<!--                                            :label="item.lableDesc"-->
<!--                                            :value="item.uuid"-->
<!--                                        ></el-option>-->
<!--                                    </el-select>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                            <el-col :span="12">-->
<!--                                <el-form-item label="邮箱">-->
<!--                                    <el-input clearable v-model="storeUser.email"></el-input>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                        </el-row>-->
<!--                    </div>-->
<!--                </div>-->


                <div class="section"  style="margin-bottom: 20px;" v-if="form.storeUserResList && form.storeUserResList.length">
                    <div class="title">
                        联系人信息  <el-button  icon="el-icon-plus" circle size="mini" @click="handleAddPerson"></el-button>
                    </div>
                    <div class="content">
                        <el-table
                            class="concateTable"
                            :data="form.storeUserResList"
                            border
                            style="width: 100%">
                            <el-table-column
                                label="姓名"
                            >
                                <template slot-scope="scope">
                                    <el-form-item
                                        :prop="'storeUserResList.' + scope.$index + '.userName'"
                                        :ref="'storeUserResList.' + scope.$index + '.userName'"
                                        :rules="{
                                        required: true, message: '请输入姓名', trigger: 'blur'
                                    }"
                                    >
                                        <el-input clearable v-model="scope.row.userName" placeholder="请输入姓名"></el-input>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="手机号"
                            >
                                <template slot-scope="scope">
                                    <el-form-item
                                        :prop="'storeUserResList.' + scope.$index + '.mobile'"
                                        :ref="'storeUserResList.' + scope.$index + '.mobile'"
                                        :rules="{
                                        required: true, message: '请输入手机号', trigger: 'blur'
                                    }"
                                    >
                                        <el-input clearable v-model="scope.row.mobile" placeholder="请输入手机号"></el-input>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="人员类型"
                            >
                                <template slot-scope="scope">
                                    <el-form-item
                                        :prop="'storeUserResList.' + scope.$index + '.personType'"
                                        :ref="'storeUserResList.' + scope.$index + '.personType'"
                                        :rules="{
                                        required: true, message: '请选择人员类型', trigger: 'change'
                                    }"
                                    >
                                        <el-select clearable v-model="scope.row.personType" placeholder="请选择人员类型">
                                            <el-option
                                                v-for="(item,index) in personType"
                                                :key="item.uuid"
                                                :label="item.lableDesc"
                                                :value="item.uuid"
                                            ></el-option>
                                        </el-select>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column
                                label="邮箱"
                            >
                                <template slot-scope="scope">
                                    <el-form-item >
                                        <el-input clearable v-model="scope.row.email" placeholder="请输入邮箱"></el-input>
                                    </el-form-item>
                                </template>
                            </el-table-column>
                            <el-table-column
                                width="80"
                            >
                                <template slot-scope="scope">
                                    <el-button type="danger" size="mini" @click="handleDelPerson(scope.$index)">
                                        删除
                                    </el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </div>

                <!--收款账号信息-->
                <div class="section" v-if="form.storeAccountRes">
                    <div class="title">
                        收款账号信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="提现方式" prop="storeAccountRes.withdrawalWay" ref="storeAccountRes.withdrawalWay">
                                    <el-select clearable v-model="form.storeAccountRes.withdrawalWay">
                                        <el-option
                                            v-for="(item,index) in withdrawalWay"
                                            :key="item.uuid"
                                            :label="item.lableDesc"
                                            :value="item.uuid"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="账户类型" prop="storeAccountRes.accountType" ref="storeAccountRes.accountType">
                                    <el-radio v-for="(item,index) in accountType" v-model="form.storeAccountRes.accountType" :label="item.uuid">
                                        {{ item.lableDesc }}
                                    </el-radio>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="12">
                                <el-form-item label="开户名" prop="storeAccountRes.accountName" ref="storeAccountRes.accountName">
                                    <el-input clearable v-model="form.storeAccountRes.accountName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <div class="warning-text">开户名必须是收款账号对应的开户名，否则将不能提现</div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="开户银行" prop="storeAccountRes.depositBank" ref="storeAccountRes.depositBank">
                                    <el-select clearable v-model="form.storeAccountRes.depositBank">
                                        <el-option
                                            v-for="(item,index) in depositBank"
                                            :key="item.uuid"
                                            :label="item.lableDesc"
                                            :value="item.uuid"
                                        ></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="支行名称" prop="storeAccountRes.subBranchName" ref="storeAccountRes.subBranchName">
                                    <el-input clearable v-model="form.storeAccountRes.subBranchName"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="收款账号" prop="storeAccountRes.cardNumbers" ref="storeAccountRes.cardNumbers">
                                    <el-input clearable v-model="form.storeAccountRes.cardNumbers"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="支付宝账号" prop="storeAccountRes.alipayAccount" ref="storeAccountRes.alipayAccount">
                                    <el-input clearable v-model="form.storeAccountRes.alipayAccount"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="微信账号" prop="storeAccountRes.weChatAccount" ref="storeAccountRes.weChatAccount">
                                    <el-input clearable v-model="form.storeAccountRes.weChatAccount"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
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
                                            :key="item.uuid"
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
                <div class="section" style="margin-top:20px;">
                	<div class="title">
                        平台服务费收取比例配置项
                  </div>
                  <el-form-item label="服务费收取比例" prop="platformFee">
                      <el-input v-model="form.platformFee" style="width:200px;" @input="platformServiceMoneyChange($event)"></el-input>
                  </el-form-item>
                </div>
                <div class="section section-btn-list">
                    <div class="content section-btn-content">
                        <el-button class="save-btn" size="lage" type="primary" @click="handleSubmit">保存</el-button>
                    </div>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import UploadPic from "@/components/UploadPic"
import {queryStoreDetail,updateStore} from "@/api/store";
import {queryAreaList} from "@/api/area";

export default {
    name: 'StoreDetail',
    components: {
        'upload-pic': UploadPic
    },
    data() {
        return {
            loading:false,
            uuid:'',
            storeType:[],
            provinceList: [],
            cityList: [],
            districtList:[],
            personType:[],
            accountType:[],
            depositBank:[],
            withdrawalWay:[],
            form: {
                storeAccountRes:{}
            },
            rules: {
                storeName: [
                    {required: true, message: '请输入店铺名称', trigger: 'blur'}
                ],
                storeType: [
                    {required: true, message: '请选择店铺类型', trigger: 'change'}
                ],
                companyName: [
                    {required: true, message: '请输入公司名称', trigger: 'blur'}
                ],
                companyAddressProvince: [
                    {required: true, message: '请选择省', trigger: 'change'}
                ],
                companyAddressCity: [
                    {required: true, message: '请选择市', trigger: 'change'}
                ],
                companyAddressCounty: [
                    {required: true, message: '请选择区', trigger: 'change'}
                ],
                companyAddressDetail: [
                    {required: true, message: '请输入详细地址', trigger: 'blur'}
                ],
                businessImgList:[
                    { type: 'array', required: true, message: '请上传营业执照', trigger: 'change' }
                ],
                shopImgList:[
                    { type: 'array', required: true, message: '请上传门店图片', trigger: 'change' }
                ],
                'storeAccountRes.withdrawalWay': [
                    {required: true, message: '请选择提现方式', trigger: 'change'}
                ],
                'storeAccountRes.accountType': [
                    {required: true, message: '请选择账户类型', trigger: 'change'}
                ],
                'storeAccountRes.accountName': [
                    {required: true, message: '请输入开户名', trigger: 'blur'}
                ],
                'storeAccountRes.depositBank': [
                    {required: true, message: '请选择开户银行', trigger: 'change'}
                ],
                // 'storeAccountRes.subBranchName': [
                //     {required: true, message: '请输入支行名称', trigger: 'blur'}
                // ],
                'storeAccountRes.cardNumbers': [
                    {required: true, message: '请输入收款账号', trigger: 'blur'}
                ],
                checkSts: [
                    {required: true, message: '请选择审核状态', trigger: 'change'}
                ],
                rejectDetail: [
                    {required: true, message: '请输入驳回原因', trigger: 'blur'}
                ],
                platformFee: [
                	{required: true, message: '请输入平台服务费收取比例', trigger: 'blur'}
                ]
            },
        }
    },
    computed: {
        ...mapGetters([
            'checkSts',
        ])
    },
    async created() {
    	console.log('created')
        this.uuid = this.$route.query.id;
        await this.initDict();
        await this._queryAreaList(-1,'province');
        this.initData()
    },
    methods: {
	    	platformServiceMoneyChange (value) {
	    		this.form.platformFee = value.replace(/^\.+|[^\d.]/g,'')
	    		if (value > 500) {
						this.form.platformFee = 500
					}
	    	},
        async initDict(){
            this.storeType = await this.$store.dispatch('dict/getDict','store_type');
            this.personType = await this.$store.dispatch('dict/getDict','person_type');
            this.accountType = await this.$store.dispatch('dict/getDict','account_type');
            this.depositBank = await this.$store.dispatch('dict/getDict','deposit_bank');
            this.withdrawalWay = await this.$store.dispatch('dict/getDict','withdrawal_way');
        },
        initData() {
            this.loading = true;
            queryStoreDetail(this.uuid).then((res) => {
                let { data } = res;
                if (data.companyAddressProvince) {
                    this._queryAreaList(data.companyAddressProvince,'city');
                }
                if(data.companyAddressCity){
                    this._queryAreaList(data.companyAddressCity,'district');
                }
                if(data.businessImgList.length){
                    data.businessImgList = data.businessImgList.map((item) => {
                        return {
                            url:item
                        }
                    })
                }
               if(data.shopImgList.length){
                   data.shopImgList = data.shopImgList.map((item) => {
                       return {
                           url:item
                       }
                   })
               }
                this.form = data;
                this.loading = false;
            }).catch((e) => {
                this.loading = false;
            })
        },
        async _queryAreaList(type,dataType) {
            let {data} = await queryAreaList(type);
            switch (dataType){
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
        handleProvinceChange(val) {
            this.form.companyAddressCity = '';
            this.form.companyAddressCounty = '';
            this.cityList = [];
            this.districtList = [];
            if(val){
                this._queryAreaList(val,'city')
            }
        },
        handleCityChange(val) {
            this.form.companyAddressCounty = '';
            this.districtList = [];
            // this._queryAreaList(val,'district')
        },
        handleDelPerson(index){
            this.form.storeUserResList.splice(index,1)
        },
        handleAddPerson(){
            this.form.storeUserResList.push({
                userName:'',
                mobile:'',
                personType:'',
                email:'',
            })
        },
        handleSubmit(){
        	this.form.platformFee = this.form.platformFee
            this.$refs['form'].validate((valid,object) => {
                if (valid) {
                    let businessImgList = this.form.businessImgList.map(item => item.url)
                    let shopImgList = this.form.shopImgList.map(item => item.url)
                    updateStore({
                        ...this.form,
                        businessImgList,
                        shopImgList,
                        storeUserReqList:this.form.storeUserResList,
                        storeAccountReq:this.form.storeAccountRes,
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
    }
}
</script>

<style lang="scss">
.concateTable{
    .el-form-item__content{
        margin-left:0!important;
    }
    .el-form-item{
        margin-bottom:0!important;
    }
}
</style>
