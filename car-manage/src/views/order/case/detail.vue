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
                            <el-col :span="8">
                                <el-form-item label="订单状态">
                                    <div class="exhibition-text">{{formatterVal(form.orderDetailRes,'orderSts')}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="订单编号">
                                    <div class="exhibition-text">{{ form.orderDetailRes.orderNum }}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="订单金额">
                                    <div class="exhibition-text">{{form.orderDetailRes.orderAmount}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="订单备注">
                                    <div class="exhibition-text">{{form.orderDetailRes.orderRemark || '无'}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="支付方式">
                                    <div class="exhibition-text">{{formatterVal(form.orderDetailRes,'payType')}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="案例技师姓名">
                                    <div class="exhibition-text">{{form.technicianInfoRes.technicianName}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="案例技师手机">
                                    <div class="exhibition-text">{{form.technicianInfoRes.technicianMobile}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="联系人">
                                    <div class="exhibition-text">{{form.carOwnerInfoRes.carOwnerName}}</div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="联系人手机">
                                    <div class="exhibition-text">{{form.carOwnerInfoRes.carOwnerMobile}}</div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <!--案例信息-->
                <div class="section" style="margin-bottom:20px;">
                    <div class="title">
                        案例信息
                    </div>
                    <div class="content">
                        <el-table
                            :data="form.caseInfoListRes"
                            border
                            style="width: 100%">
                            <el-table-column
                                prop="amount"
                                label="案例图片"
                                width="90"
                                align="center"
                            >
                                <template slot-scope="{row,$index}">
                                    <el-image
                                        style="display:block;width: 65px; height: 65px"
                                        :src="row.caseImgUrl"
                                        fit="cover"></el-image>
                                </template>
                            </el-table-column>
                            <el-table-column
                                prop="caseName"
                                label="案例名称">
                            </el-table-column>
                            <el-table-column
                                prop="caseNum"
                                label="案例数量">
                            </el-table-column>
                            <el-table-column
                                prop="materialsExpenses"
                                label="案例价格">
                            </el-table-column>
                        </el-table>
                    </div>
                </div>


                <!--评价-->
                <div class="section" v-if="false">
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
import { queryOrderCaseDetail } from "@/api/order";
export default {
    name: 'CaseOrderDetail',
    data() {
        return {
            loading:false,
            uuid:'',
            form: {
                orderDetailRes:{},
                caseInfoListRes:[],
                technicianInfoRes:{},
                afterSaleInfoRes:{},
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
            queryOrderCaseDetail(this.uuid).then((res) => {
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
