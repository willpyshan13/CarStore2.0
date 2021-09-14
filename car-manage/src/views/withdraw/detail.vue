<template>
    <div class="app-container">
        <div class="page-detail">
            <el-form
                v-loading="loading"
                label-position="right"
                :model="form"
                :rules="rules"
                ref="form"
                label-width="100px"
            >
                <!--基本信息-->
                <div class="section">
                    <div class="title">
                        基本信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="店铺名称">
                                    <div class="exhibition-text">{{form.storeName}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="店铺类型">
                                    <div class="exhibition-text">{{form.storeTypeName}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="姓名">
                                    <div class="exhibition-text">{{form.userName}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="手机号">
                                    <div class="exhibition-text">{{form.mobile}}</div>
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
                            <el-col :span="6">
                                <el-form-item label="账户余额">
                                    <div class="exhibition-text">{{form.totalAmount}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="账户名称">
                                    <div class="exhibition-text">{{form.depositBank}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="开户支行">
                                    <div class="exhibition-text">{{form.subBranchName}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="银行卡号">
                                    <div class="exhibition-text">{{form.cardNumbers}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <div class="section">
                    <div class="title">
                        提现金额 <span style="padding:0 10px;color:#409EFF">￥{{form.withdrawalAmount}}</span>提现后余额<span
                        style="padding:0 10px;color:red">￥{{form.balanceAmount}}</span>
                    </div>
                    <div class="content" v-for="(item,index) in form.withdrawalDetailResList" :key="index">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="代驾金额">
                                    <div class="exhibition-text">￥{{item.drivingAmount}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="维修金额">
                                    <div class="exhibition-text">￥{{item.serviceAmount}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="现场支持金额">
                                    <div class="exhibition-text">￥{{item.supportAmount}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="回答金额">
                                    <div class="exhibition-text">￥{{item.qaAmount}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <div class="section">
                    <div class="title">
                        待入账金额<span
                        style="padding:0 10px;color:red">￥{{form.waitAmount || 0}}</span>
                    </div>
<!--                    <div class="content">-->
<!--                        <el-row>-->
<!--                            <el-col :span="6">-->
<!--                                <el-form-item label="代驾金额">-->
<!--                                    <div class="exhibition-text">￥200</div>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                            <el-col :span="6">-->
<!--                                <el-form-item label="维修金额">-->
<!--                                    <div class="exhibition-text">￥500</div>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                            <el-col :span="6">-->
<!--                                <el-form-item label="现场支持金额">-->
<!--                                    <div class="exhibition-text">￥500</div>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                            <el-col :span="6">-->
<!--                                <el-form-item label="回答金额">-->
<!--                                    <div class="exhibition-text">￥500</div>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
<!--                        </el-row>-->
<!--                    </div>-->
                </div>
                <!--审核-->
                <div class="section" style="margin-top:20px;">
                    <div class="title">
                        信息审核
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="转账凭证" prop="voucherImgUrl" ref="voucherImgUrl">
                                    <upload-pic :limit="1"
                                        :file-list="form.voucherImgUrl"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
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
                <div class="section section-btn-list">
                    <div class="content section-btn-content">
                        <el-button class="save-btn" size="lage" type="primary" @click="handleSubmit">提交</el-button>
                    </div>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import UploadPic from "@/components/UploadPic/index";
import { queryWithdrawalDetail,checkWithdrawal } from "@/api/withdraw";

export default {
    name: 'WithdrawDetail',
    components:{
        'upload-pic':UploadPic
    },
    data() {
        return {
            loading:false,
            uuid:'',
            form: {
                voucherImgUrl:[]
            },
            rules: {
                voucherImgUrl:[
                    {required: true, message: '请上传转账凭证', trigger: 'change'}
                ],
                checkSts: [
                    {required: true, message: '请选择审核状态', trigger: 'change'}
                ],
                rejectDetail: [
                    {required: true, message: '请输入驳回原因', trigger: 'blur'}
                ],
            }
        }
    },
    async created() {
        this.uuid = this.$route.query.id;
        this.initData()
    },
    computed: {
        ...mapGetters([
            'checkSts',
        ])
    },
    methods: {
        initData() {
            this.loading = true;
            queryWithdrawalDetail(this.uuid).then((res) => {
                let { data } = res;
                data.voucherImgUrl = data.voucherImgUrl ? [{
                    url:data.voucherImgUrl
                }] : [],
                this.form = data;
                this.loading = false;
            }).catch((e) => {
                this.loading = false;
            })
        },
        handleSubmit(){
            this.$refs['form'].validate((valid,object) => {
                if (valid) {
                    let voucherImgUrl = this.form.voucherImgUrl.map(item => item.url)[0]
                    checkWithdrawal({
                        checkSts:this.form.checkSts,
                        rejectDetail:this.form.rejectDetail,
                        uuid:this.uuid,
                        voucherImgUrl
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

<style lang="scss" scoped>

</style>
