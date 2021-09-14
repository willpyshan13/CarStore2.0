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
                <!--买家信息-->
                <div class="section">
                    <div class="title">
                        买家信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="16">
                                <el-row>
                                    <el-col :span="12">
                                        <el-form-item label="订单编号">
                                            <div class="exhibition-text">{{ form.orderNum }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="下单时间">
                                            <div class="exhibition-text">{{ form.createdTime }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="联系人">
                                            <div class="exhibition-text">{{form.contacts}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="联系方式">
                                            <div class="exhibition-text">{{form.mobile}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="服务单号">
                                            <div class="exhibition-text">{{form.serviceNum}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="服务地区">
                                            <div class="exhibition-text">{{form.serviceArea}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="配送方式">
                                            <div class="exhibition-text">{{formatterVal(form,'deliveryMode')}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="支付方式">
                                            <div class="exhibition-text">{{formatterVal(form,'payType')}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="实收金额">
                                            <div class="exhibition-text">{{ form.actualAmount }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-form-item label="备注信息">
                                            <div class="exhibition-text">{{form.orderRemark}}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                            <el-col :span="8">
                                <el-card class="box-card">
                                    <div slot="header" class="clearfix">
                                        <span class="box-card-title">订单状态：{{formatterVal(form,'orderSts')}}</span>
                                    </div>
                                    <div class="box-card-con" v-if="false">
                                        <div class="desc">买家已完成付款，请尽快发货。</div>
                                    </div>
                                    <div class="box-card-opt" style="margin-top:0" v-if="false">
                                        <el-button type="success">服务完成</el-button>
                                        <el-button>取消订单</el-button>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row>

                    </div>
                </div>


                <!--商品-->
                <div class="section" style="margin-bottom:20px;">
                    <div class="title">
                        商品
                    </div>
                    <div class="content">
                        <el-table
                            :data="form.orderGoodsDetailListRes"
                            border
                            style="width: 100%">
                            <el-table-column
                                prop="amount"
                                label="商品图片"
                                width="90"
                                align="center"
                            >
                                <template slot-scope="{row,$index}">
                                    <el-image
                                        style="display:block;width: 65px; height: 65px"
                                        src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
                                        fit="cover"></el-image>
                                </template>
                            </el-table-column>
                            <el-table-column
                                prop="goodsName"
                                label="商品名称">
                            </el-table-column>
                            <el-table-column
                                prop="goodsNum"
                                label="商品数量">
                            </el-table-column>
                            <el-table-column
                                prop="materialsExpenses"
                                label="材料费">
                            </el-table-column>
                            <el-table-column
                                prop="manHourCost"
                                label="工时费">
                            </el-table-column>
                        </el-table>


<!--                        <el-table-->
<!--                            :data="tableData"-->
<!--                            border-->
<!--                            style="width: 100%">-->
<!--                            <el-table-column-->
<!--                                label="商品"-->
<!--                            >-->
<!--                                <template slot-scope="{row,$index}">-->
<!--                                    <div class="product-info" v-if="$index == 0">-->
<!--                                        <div class="pic">-->
<!--                                            <el-image-->
<!--                                                style="display:block;width: 65px; height: 65px"-->
<!--                                                src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"-->
<!--                                                fit="cover"></el-image>-->
<!--                                        </div>-->
<!--                                        <div class="con">-->
<!--                                            <div class="name">五座轿五座轿车车</div>-->
<!--                                            <div class="num">数量：1</div>-->
<!--                                            <div class="c_fee">材料费：￥15.90</div>-->
<!--                                            <div class="f_fee">服务费：￥100</div>-->
<!--                                        </div>-->
<!--                                    </div>-->
<!--                                    <div v-else>-->
<!--                                        <div class="table-filed">{{row.field}}</div>-->
<!--                                    </div>-->
<!--                                </template>-->
<!--                            </el-table-column>-->
<!--                            <el-table-column-->
<!--                                prop="amount"-->
<!--                                label="应收金额">-->
<!--                            </el-table-column>-->
<!--                            <el-table-column-->
<!--                                prop="realAmount"-->
<!--                                label="实收金额">-->
<!--                            </el-table-column>-->
<!--                        </el-table>-->
                    </div>
                </div>
                <!--售后信息-->
                <div class="section" v-if="form.afterSaleSts">
                    <div class="title">
                        售后信息
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="16">
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="售后原因">
                                            <div class="exhibition-text">{{form.afterSaleCause}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="售后说明">
                                            <div class="exhibition-text">{{form.afterSaleRemark}}</div>
                                        </el-form-item>
                                    </el-col>

                                </el-row>
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="退款类型">
                                            <div class="exhibition-text">{{formatterVal(form,'refundType')}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="16">
                                        <el-form-item label="退款金额">
                                            <div class="exhibition-text">{{ form.refundAmount }}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                            </el-col>
                            <el-col :span="8">
                                <el-card class="box-card">
                                    <div slot="header" class="clearfix">
                                        <span class="box-card-title">售后状态：{{formatterVal(form,'afterSaleSts')}}。</span>
                                    </div>
                                    <div class="box-card-con">
                                        <div class="desc" style="color:red">订单将在4天11小时30分后自动拒绝收货，请及时处理。</div>
                                    </div>
                                    <div class="box-card-opt">
                                        <el-button type="success">同意退款</el-button>
                                        <el-button type="warning">拒绝退款</el-button>
                                        <el-button>取消退款</el-button>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row>
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
                                <el-form-item label="店铺服务">
                                    <div class="star-rate">
                                        <el-rate
                                            :value="form.storeScore || ''"
                                            disabled
                                            show-score
                                            text-color="#ff9900"
                                            >
                                        </el-rate>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="技师服务">
                                    <div class="star-rate">
                                        <el-rate
                                            :value="form.technicianScore || ''"
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
import { queryOrderGoodsDetail } from "@/api/order";
export default {
    name: 'GoodsOrderDetail',
    data() {
        return {
            loading:false,
            uuid:'',
            form: {},
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
        ])
    },
    created() {
        this.uuid = this.$route.query.id;
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            queryOrderGoodsDetail(this.uuid).then((res) => {
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
.page-detail{
    ::v-deep .el-card__body{
        padding:0!important;
    }
}
</style>
