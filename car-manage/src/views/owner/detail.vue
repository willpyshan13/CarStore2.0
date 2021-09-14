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
                                    <el-input maxlength="11" clearable v-model="form.mobile"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="证件类型" prop="certificateType" ref="certificateType">
                                    <el-select clearable v-model="form.certificateType">
                                        <el-option v-for="(item,index) in certificateType" :key="item.uuid"
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
                                <el-form-item label="地区">
                                    <el-row>
                                        <el-col :span="11">
                                            <el-select clearable v-model="form.addressProvince" placeholder="请选择省"
                                                       @change="handleProvinceChange">
                                                <el-option v-for="(item,index) in provinceList" :key="item.uuid"
                                                           :label="item.areaName" :value="item.uuid"></el-option>
                                            </el-select>
                                        </el-col>
                                        <el-col :span="11" :offset="2">
                                            <el-select clearable v-model="form.addressCity" placeholder="请选择市">
                                                <el-option v-for="(item,index) in cityList" :key="item.uuid"
                                                           :label="item.areaName" :value="item.uuid"></el-option>
                                            </el-select>
                                        </el-col>
                                    </el-row>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="详细地址">
                                    <el-input clearable v-model="form.addressDetail"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <!--账户信息-->
                <div class="section">
                    <div class="title">
                        账户信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="账户名称" prop="accountName" ref="accountName">
                                    <el-input clearable v-model="form.accountName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="开户银行" prop="depositBank" ref="depositBank">
                                    <el-select clearable v-model="form.depositBank">
                                        <el-option v-for="(item,index) in depositBank" :key="item.uuid"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="开户支行" prop="subBranchName" ref="subBranchName">
                                    <el-input clearable v-model="form.subBranchName"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="银行卡号" prop="cardNumbers" ref="cardNumbers">
                                    <el-input clearable v-model="form.cardNumbers"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="支付宝账号" prop="alipayAccount" ref="alipayAccount">
                                    <el-input clearable v-model="form.alipayAccount"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <!--绑定车数量-->
                <div class="section" v-if="form.vehicleList && form.vehicleList.length">
                    <div class="title">
                        绑定车数量<span style="padding:0 10px;color:#409EFF">{{ form.vehicleList.length }}</span>辆
                    </div>
                    <div class="content" v-for="(vehicle,vehicleIndex) in form.vehicleList" :key="vehicleIndex">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item
                                    label="行驶证登记日期"
                                    :prop="'vehicleList.' + vehicleIndex + '.licenseRegisterDate'"
                                    :ref="'vehicleList.' + vehicleIndex + '.licenseRegisterDate'"
                                    :rules="{
                                        required: true, message: '请选择行驶证登记日期', trigger: 'change'
                                    }"
                                >
                                    <el-date-picker
                                        clearable
                                        v-model="vehicle.licenseRegisterDate"
                                        type="date"
                                    >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item
                                    label="行驶证编号"
                                    :prop="'vehicleList.' + vehicleIndex + '.licenseNumber'"
                                    :ref="'vehicleList.' + vehicleIndex + '.licenseNumber'"
                                    :rules="{
                                        required: true, message: '请输入行驶证编号', trigger: 'blur'
                                    }"
                                >
                                    <el-input clearable v-model="vehicle.licenseNumber"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item
                                    label="车牌号"
                                    :prop="'vehicleList.' + vehicleIndex + '.plateNumber'"
                                    :ref="'vehicleList.' + vehicleIndex + '.plateNumber'"
                                    :rules="{
                                        required: true, message: '请输入车牌号', trigger: 'blur'
                                    }"
                                >
                                    <el-input clearable v-model="vehicle.plateNumber"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="车辆类型">
                                    <el-select clearable
                                               v-model="vehicle.vehicleType"
                                               @change="(val) => {handleVehicleTypeChange(val,vehicleIndex)}"
                                    >
                                        <el-option
                                            v-for="(item,index) in vehicleType"
                                            :key="item.uuid"
                                            :label="item.configName"
                                            :value="item.uuid"
                                        >
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="VIN编号">
                                    <el-input clearable v-model="vehicle.vinCode"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-row>
                                    <el-col :span="12">
                                        <el-form-item label="车辆品牌">
                                            <el-select clearable
                                                       v-model="vehicle.vehicleBrand"
                                                       @change="(val) => {handleVehicleBrandChange(val,vehicleIndex)}"
                                            >
                                                <el-option
                                                    v-for="(item,index) in vehicle.vehicleBrandList"
                                                    :key="item.uuid"
                                                    :label="item.configName"
                                                    :value="item.uuid"
                                                >
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="车型">
                                            <el-select clearable v-model="vehicle.vehicleModel">
                                                <el-option
                                                    v-for="(item,index) in vehicle.vehicleModelList"
                                                    :key="item.uuid"
                                                    :label="item.configName"
                                                    :value="item.uuid"
                                                >
                                                </el-option>
                                            </el-select>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="购入年月">
                                    <el-date-picker
                                        clearable
                                        v-model="vehicle.buyDate"
                                        type="date"
                                    >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="燃油类型">
                                    <el-select clearable v-model="vehicle.fuelType">
                                        <el-option v-for="(item,index) in fuelType" :key="item.uuid"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="车辆排放等级">
                                    <el-select clearable v-model="vehicle.emissionLevels">
                                        <el-option v-for="(item,index) in emissionLevels" :key="item.uuid"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="排量/L">
                                    <el-select clearable v-model="vehicle.displacement">
                                        <el-option v-for="(item,index) in displacement" :key="item.uuid"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="发动机型号">
                                    <el-input clearable v-model="vehicle.engineType"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="后处理系统">
                                    <el-input clearable v-model="vehicle.afterTreatmentSystem"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <div class="section">
                    <div class="title">
                        完成订单 <span style="padding:0 10px;color:#409EFF">{{ orderInfo.orderCount }}</span>单 支出总金额<span
                        style="padding:0 10px;color:red">￥{{ orderInfo.payTotalMoney }}</span> 剩余<span
                        style="padding:0 10px;color:red">￥{{ orderInfo.remainingMoney }}</span>
                    </div>
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
import {queryDetail, queryOrderAmount,editVehicleUser,queryAllList} from "@/api/owner"
import {queryAreaList} from '@/api/area'

export default {
    name: 'OwnerDetail',
    data() {
        return {
            uuid: '',
            loading: true,
            provinceList: [],
            cityList: [],
            certificateType: [],
            depositBank: [],
            fuelType: [],
            emissionLevels: [],
            displacement: [],
            vehicleType: [],
            vehicleAllList:[],
            orderInfo: {
                orderCount: 0,
                payTotalMoney: 0,
                remainingMoney: 0
            },
            form: {},
            rules: {
                userName: [
                    {required: true, message: '请输入姓名', trigger: 'blur'}
                ],
                mobile: [
                    {required: true, message: '请输入手机号', trigger: 'blur'}
                ],
                certificateType: [
                    {required: true, message: '请选择证件类型', trigger: 'change'}
                ],
                certificateNum: [
                    {required: true, message: '请输入证件号码', trigger: 'blur'}
                ],
                accountName: [
                    {required: true, message: '请输入账户名称', trigger: 'blur'}
                ],
                depositBank: [
                    {required: true, message: '请选择开户银行', trigger: 'change'}
                ],
                subBranchName: [
                    {required: true, message: '请输入开户支行', trigger: 'blur'}
                ],
                cardNumbers: [
                    {required: true, message: '请输入银行卡号', trigger: 'blur'}
                ],
                alipayAccount: [
                    {required: true, message: '请输入支付宝账号', trigger: 'blur'}
                ],
            }
        }
    },
    async created() {
        this.uuid = this.$route.query.id;
        await this.initDict();
        await this._queryAreaList(-1);
        await this._queryAllList();
        await this._queryOrderAmount();
        await this.initData();
    },
    methods: {
        initData() {
            this.loading = true;
            queryDetail(this.uuid).then((res) => {
                let {data} = res;
                if (data.addressProvince) {
                    this._queryAreaList(data.addressProvince);
                }
                if(data.vehicleList && data.vehicleList.length){
                    data.vehicleList.forEach((vehicle,vehicleIndex) => {
                        vehicle.vehicleBrandList = this.vehicleAllList.filter((item) => {
                            return item.parentCode == vehicle.vehicleType
                        })
                        vehicle.vehicleModelList = this.vehicleAllList.filter((item) => {
                            return item.parentCode == vehicle.vehicleBrand
                        })
                    })
                }
                console.log(data)
                this.$nextTick(() => {
                    this.form = data;
                })
                this.loading = false;
            }).catch((e) => {
                this.loading = false;
            })
        },
        _queryOrderAmount() {
            queryOrderAmount(this.uuid).then((res) => {
                let {data} = res;
                this.orderInfo = data;
            });
        },
        async initDict() {
            this.certificateType = await this.$store.dispatch('dict/getDict', 'certificate_type');
            this.depositBank = await this.$store.dispatch('dict/getDict', 'deposit_bank');
            this.fuelType = await this.$store.dispatch('dict/getDict', 'fuel_type');
            this.emissionLevels = await this.$store.dispatch('dict/getDict', 'emission_levels');
            this.displacement = await this.$store.dispatch('dict/getDict', 'displacement');
        },
        async _queryAreaList(type) {
            let {data} = await queryAreaList(type);
            if (type == -1) {
                this.provinceList = data;
            } else {
                this.cityList = data;
            }
        },
        async _queryAllList(params){
            let { data } = await queryAllList(params);
            this.vehicleAllList = data;
            this.vehicleType = data.filter((item) => {
                return item.configType == 1
            })
        },
        handleProvinceChange(val) {
            this.form.addressCity = '';
            this.cityList = [];
            if(val){
                this._queryAreaList(val)
            }
        },
        handleSubmit(){
            this.$refs['form'].validate((valid,object) => {
                if (valid) {
                    editVehicleUser({
                        ...this.form
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
        handleVehicleTypeChange(val,vehicleIndex){
            this.form.vehicleList[vehicleIndex].vehicleBrand = '';
            this.form.vehicleList[vehicleIndex].vehicleModel = '';
            this.form.vehicleList[vehicleIndex].vehicleModelList = [];
            this.form.vehicleList[vehicleIndex].vehicleBrandList = this.vehicleAllList.filter((item) => {
                return item.parentCode == val
            });
        },
        handleVehicleBrandChange(val,vehicleIndex){
            this.form.vehicleList[vehicleIndex].vehicleModel = '';
            this.form.vehicleList[vehicleIndex].vehicleModelList = this.vehicleAllList.filter((item) => {
                return item.parentCode == val
            });
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
