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
                <!--订单信息-->
                <div class="section">
                    <div class="title">
                        订单信息
                    </div>
                    <div class="content" style="margin-bottom:20px;">
                        <el-row>
                            <el-col :span="16">
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="订单编号">
                                            <div class="exhibition-text">{{ form.orderNum }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="下单时间">
                                            <div class="exhibition-text">{{ form.createdTime }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="订单金额">
                                            <div class="exhibition-text">{{ form.totalAmount }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="姓名">
                                            <div class="exhibition-text">{{form.buyerName}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="手机">
                                            <div class="exhibition-text">{{form.buyerMobile}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="支付方式">
                                            <div class="exhibition-text">{{formatterVal(form,'payType')}}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                            <el-col :span="8">
                                <el-card class="box-card">
                                    <div slot="header" class="clearfix">
                                        <span class="box-card-title">订单状态：{{formatterVal(form,'orderSts')}}</span>
                                    </div>
                                    <div class="box-card-opt" style="margin-top:0" v-if="false">
                                        <el-button type="success" v-if="form.orderSts == 1" @click="handleServiceComplete">服务完成</el-button>
                                        <el-button v-if="form.orderSts == 3">退款中</el-button>
                                        <el-button v-if="form.orderSts == 4">退款成功</el-button>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row>

                    </div>
                </div>

                <!--现场支持信息-->
                <div class="section">
                    <div class="title">
                        现场支持信息
                    </div>
                    <div class="content" style="margin-bottom:20px;">
                        <el-row>
                            <el-col :span="6">
                                <el-form-item label="品牌">
                                    <div class="exhibition-text">{{ form.brandName }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="车型">
                                    <div class="exhibition-text">{{ form.carModelName }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="车款">
                                    <div class="exhibition-text">{{ form.carStyle }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="发动机排量">
                                    <div class="exhibition-text">{{ form.engineDisplacement }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="VIN码">
                                    <div class="exhibition-text">{{ form.vinCode }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="驱动方式">
                                    <div class="exhibition-text">{{ form.drivingMode }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="增压系统">
                                    <div class="exhibition-text">{{ form.boosterSystem }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="保修状态">
                                    <div class="exhibition-text">{{ form.warrantySts == 1 ? '不保修' : '保修' }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="变速器">
                                    <div class="exhibition-text">{{ form.transmissionOneLevel }}  {{form.transmissionTwoLevel}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="故障描述">
                                    <div class="exhibition-text">{{ form.faultDesc }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="维修类型">
                                    <div class="exhibition-text">{{ form.repairType }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="已检过程">
                                    <div class="exhibition-text">{{ form.alreadyInspect }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="是否有DTC故障代码">
                                    <div class="exhibition-text">{{ form.dtcCode ? '是' : '否' }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24" v-if="form.dtcImageList && form.dtcImageList.length">
                                <el-form-item label="DTC图片">
                                    <div class="pic-list">
                                        <div class="pic-item" v-for="(item,index) in form.dtcImageList" :key="index">
                                            <el-image
                                                style="width: 100px; height: 100px"
                                                :src="item"
                                                fit="cover"></el-image>
                                        </div>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="基本检查费用">
                                    <div class="exhibition-text">¥{{ form.basicInspectAmount }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="相关线路检查费">
                                    <div class="exhibition-text">¥{{ form.lineInspectAmount }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="诊断仪使用费">
                                    <div class="exhibition-text">¥{{ form.diagnosisInstrumentAmount }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="其他费用费">
                                    <div class="exhibition-text">¥{{ form.otherAmount }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="平台订单服务费">
                                    <div class="exhibition-text">¥{{ form.orderServiceAmount }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6">
                                <el-form-item label="总支付费用">
                                    <div class="exhibition-text">¥{{ form.totalAmount }}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>


            </el-form>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import { sceneOrderDetail,sceneOrderComplete } from "@/api/order";
export default {
    name: 'SceneOrderDetail',
    data() {
        return {
            loading:false,
            uuid:'',
            form: {
                consultRes:{}
            },
            rules: {},
            tableData:[
                {
                    field:'',
                    amount:10,
                    realAmount:10,
                },
                {
                    field:'服务费',
                    amount:10,
                    realAmount:10,
                },
                {
                    field:'总计',
                    amount:10,
                    realAmount:10,
                }
            ]
        }
    },
    computed: {
        ...mapGetters([
            'orderSts',
            'afterSaleSts',
            'evaluateSts',
            'payType',
            'deliveryMode',
            'refundType',
            'answerSts',
            'orderType',
        ])
    },
    created() {
        this.uuid = this.$route.query.id;
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            sceneOrderDetail(this.uuid).then((res) => {
                let { data } = res;
                this.form = data;
                this.loading = false;
            }).catch((e) => {
                this.loading = false;
            })
        },
        formatterVal(row,field) {
            let itemData = this[field].find(item => item.uuid == row[field])
            return itemData && itemData.lableDesc
        },
        handleServiceComplete(){
            const loading = this.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            sceneOrderComplete(this.uuid).then((res) => {
                this.$message({
                    message: '操作成功',
                    type: 'success'
                });
                loading.close();
            }).catch((e) => {
                loading.close();
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.box-card{
    .box-card-title{
        font-weight: bold;
    }
    .box-card-con{
        .desc{
            font-size:12px;
            color:#909399;
        }
    }
    .box-card-opt{
        margin-top:40px;
    }
}
.star-rate{
    margin-top:7px;
}

.product-info{
    display: flex;
    align-items: center;
    .pic{
        flex:0 0 65px;
    }
    .con{
        flex:1;
        min-width:0;
        line-height:1;
        padding-left:5px;
        .name{
            font-weight: 500;
            font-size:12px;
        }
        .num{
            font-size:12px;
            margin-top:4px;
        }
        .c_fee{
            font-size:12px;
            margin-top:4px;
        }
        .f_fee{
            font-size:12px;
            margin-top:4px;
        }
    }
}
.table-filed{
    font-weight: bold;
}
.pic-list{
      margin-top:20px;
      display: flex;
      flex-wrap: wrap;
      .pic-item{
          margin-right:20px;
      }
}

.page-detail{
    ::v-deep .el-card__body{
        padding:0!important;
    }
}
</style>
