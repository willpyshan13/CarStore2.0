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
                                            <div class="exhibition-text">{{ form.orderAmount }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="咨询者姓名">
                                            <div class="exhibition-text">{{form.carOwnerName}}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="咨询者手机">
                                            <div class="exhibition-text">{{form.carOwnerMobile}}</div>
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
                                    <div v-if="typeof form.acceptResult == 'number'">
                                        <div class="box-card-con">
                                            <div class="desc">请留意是否满意。</div>
                                        </div>
                                        <div class="box-card-opt" style="margin-top:20px">
                                            <el-button type="success" v-if="form.acceptResult == 0">满意</el-button>
                                            <el-button type="danger" v-if="form.acceptResult == 1">不满意</el-button>
                                        </div>
                                    </div>
                                    <div v-else class="box-card-con">
                                        <div class="desc">请留意是否满意。</div>
                                    </div>
                                </el-card>
                            </el-col>
                        </el-row>

                    </div>
                </div>


                <!--问答内容-->
                <div class="section" style="margin-bottom:20px;">
                    <div class="title">
                        咨询信息
                    </div>
                    <div class="content">
                        <el-form
                            label-position="left"
                            label-width="60px"
                        >
                            <div class="qa">
                                <div class="l">
                                    <div class="avatar">
                                        <el-avatar
                                            :size="45"
                                            fit="contain"
                                            :src="form.consultRes.carOwnerImgUrl"></el-avatar>
                                    </div>
                                </div>
                                <div class="r">
                                    <el-form-item label="提问者:">
                                        <div class="exhibition-text">{{ form.consultRes.carOwnerName }}</div>
                                    </el-form-item>
                                    <el-form-item label="标题:">
                                        <div class="exhibition-text">{{ form.consultRes.title }}</div>
                                    </el-form-item>
                                    <el-form-item label="问题:">
                                        <div class="exhibition-text">{{ form.consultRes.consultDesc }}</div>
                                    </el-form-item>
                                    <div class="pic-list" v-if="form.consultRes.consultImgList && form.consultRes.consultImgList.length">
                                        <div class="pic-item" v-for="(img,index) in form.consultRes.consultImgList">
                                            <el-image style="width: 160px; height: 160px"
                                                      :src="img"
                                                      fit="cover"
                                                      :preview-src-list="form.consultRes.consultImgList"
                                            ></el-image>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="qa" v-if="form.consultRes.technicianName">
                                <div class="l">
                                    <div class="avatar">
                                        <el-avatar
                                            :size="45"
                                            fit="contain"
                                            :src="form.consultRes.technicianImgUrl"></el-avatar>
                                    </div>
                                </div>
                                <div class="r">
                                    <el-form-item label="问答者:">
                                        <div class="exhibition-text">{{ form.consultRes.technicianName }}</div>
                                    </el-form-item>
                                    <el-form-item label="答案:">
                                        <div class="exhibition-text">{{ form.consultRes.answerDesc }}</div>
                                    </el-form-item>
                                    <div class="pic-list" v-if="form.consultRes.answerImgList && form.consultRes.answerImgList.length">
                                        <div class="pic-item" v-for="(img,index) in form.consultRes.answerImgList">
                                            <el-image style="width: 160px; height: 160px"
                                                      :src="img"
                                                      fit="cover"
                                                      :preview-src-list="form.consultRes.answerImgList"
                                            ></el-image>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </el-form>
                    </div>
                </div>


                <!--评价-->
                <div class="section">
                    <div class="title">
                        评价<span data-v-e2c91668="" style="padding: 0px 10px; color: red;">{{formatterVal(form,'evaluateSts')}}</span>
                    </div>
                    <div class="content" v-if="form.technicianScore">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="答主评分">
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
import { queryOrderConsultDetail } from "@/api/order";
export default {
    name: 'ConsultOrderDetail',
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
            queryOrderConsultDetail(this.uuid).then((res) => {
                let { data } = res;
                this.form = data || {
                    consultRes:{}
                };
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
.qa{
    padding-left:30px;
    display: flex;
    margin-bottom:30px;
    .r{
        flex:1;
        margin-left:20px;
        .el-form-item{
            margin-bottom:0;
        }
    }
    .pic-list{
        margin-top:20px;
        display: flex;
        flex-wrap: wrap;
        .pic-item{
            margin-right:20px;
        }
    }
}
</style>
