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
                <!--订单信息-->
                <div class="section">
                    <div class="title">
                        订单信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="24">
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="订单状态">
                                            <div class="exhibition-text">{{formatterVal(form.orderInfoRes,'orderSts')}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="订单编号">
                                            <div class="exhibition-text">{{ form.orderInfoRes.orderNum }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="订单金额">
                                            <div class="exhibition-text">{{form.orderInfoRes.orderAmount}}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="订单备注">
                                            <div class="exhibition-text">{{form.orderInfoRes.orderRemark || '无'}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="开始时间">
                                            <div class="exhibition-text">{{ form.orderInfoRes.createdTime }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="结束时间">
                                            <div class="exhibition-text">{{form.orderInfoRes.endTime}}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="出发地">
                                            <div class="exhibition-text">{{form.orderInfoRes.startPlace}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="目的地">
                                            <div class="exhibition-text">{{form.orderInfoRes.endPlace}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="支付方式">
                                            <div class="exhibition-text">{{formatterVal(form.orderInfoRes,'payType')}}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <!--技师信息-->
                <div class="section">
                    <div class="title">
                        技师信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="24">
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="技师姓名">
                                            <div class="exhibition-text">{{ form.technicianInfoRes.technicianName }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="技师手机号">
                                            <div class="exhibition-text">{{ form.technicianInfoRes.technicianMobile }}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <!--车主信息-->
                <div class="section">
                    <div class="title">
                        车主信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="24">
                                <el-col :span="8">
                                    <el-form-item label="地区">
                                        <div class="exhibition-text">{{ form.carOwnerInfoRes.carOwnerArea }}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="车主姓名">
                                        <div class="exhibition-text">{{ form.carOwnerInfoRes.carOwnerName }}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="车主手机号">
                                        <div class="exhibition-text">{{ form.carOwnerInfoRes.carOwnerMobile }}</div>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="8">
                                    <el-form-item label="支付宝账号">
                                        <div class="exhibition-text">{{ form.carOwnerInfoRes.alipayAccount }}</div>
                                    </el-form-item>
                                </el-col>
                            </el-col>
                        </el-row>
                    </div>
                </div>

                <!--评价-->
                <div class="section">
                    <div class="title">
                        评价<span data-v-e2c91668="" style="padding: 0px 10px; color: red;">{{formatterVal(form,'evaluateSts')}}</span>
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="技师服务">
                                    <div class="star-rate">
                                        <el-rate
                                            :value="form.technicianScore"
                                            disabled
                                            show-score
                                            text-color="#ff9900"
                                            >
                                        </el-rate>
                                    </div>
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
import { queryOrderDrivingDetail } from "@/api/order";
export default {
    name: 'DrivingOrderDetail',
    data() {
        return {
            loading:false,
            uuid:'',
            form: {
                orderInfoRes:{},
                technicianInfoRes:{},
                carOwnerInfoRes:{},
            },
            rules: {},
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
        ])
    },
    created() {
        this.uuid = this.$route.query.id;
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            queryOrderDrivingDetail(this.uuid).then((res) => {
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
</style>
