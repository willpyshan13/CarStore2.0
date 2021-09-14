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
                                    <div slot="header" class="clearfix" style="border: none">
                                        <span class="box-card-title">订单状态：{{formatterVal(form,'orderSts')}}</span>
                                    </div>
<!--                                    <div class="box-card-opt" style="margin-top:0">-->
<!--                                        <el-button type="success">服务完成</el-button>-->
<!--                                        <el-button>取消订单</el-button>-->
<!--                                    </div>-->
                                </el-card>
                            </el-col>
                        </el-row>

                    </div>
                </div>


                <!--课程详情-->
                <div class="section">
                    <div class="title">
                        课程详情
                    </div>
                    <div class="content" style="margin-bottom:20px;">
                        <el-row>
                            <el-col :span="16">
                                <el-row>
                                    <el-col :span="8">
                                        <el-form-item label="课程名称">
                                            <div class="exhibition-text">{{ form.courseTitle }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="课程类型">
                                            <div class="exhibition-text">{{ form.courseType == 1 ? '图文 ' : '直播' }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="8">
                                        <el-form-item label="课程讲师">
                                            <div class="exhibition-text">{{ form.courseLecturer }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24">
                                        <el-form-item label="课程简介">
                                            <div class="exhibition-text" v-html="form.courseIntro"></div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24" v-if="form.courseType == 1">
                                        <el-form-item label="课程内容">
                                            <div class="exhibition-text" v-html="form.courseContent"></div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24" v-if="form.courseType == 0">
                                        <el-form-item label="课程链接">
                                            <div class="exhibition-text">{{ form.courseUrl }}</div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24">
                                        <el-form-item label="评分">
                                            <div class="exhibition-text">{{ form.score }}</div>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
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
import { courseOrderDetail } from "@/api/order";
export default {
    name: 'CourseOrderDetail',
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
            courseOrderDetail(this.uuid).then((res) => {
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
.page-detail{
    ::v-deep .el-card__body{
        padding:0!important;
    }
}
</style>
