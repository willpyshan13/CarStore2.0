<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input clearable v-model="filter.caseName" placeholder="请输入案例名称"></el-input>
                </el-form-item>

                <el-form-item label="">
                    <el-date-picker
                        clearable
                        v-model="date"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="下单开始日期"
                        end-placeholder="下单结束日期"
                        value-format="yyyy-MM-dd"
                       >
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.orderSts" placeholder="请选择订单状态">
                        <el-option v-for="(item,index) in orderSts" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.evaluateSts" placeholder="请选择评价状态">
                        <el-option v-for="(item,index) in evaluateSts" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.orderNum" placeholder="请输入订单编号"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.carOwnerName" placeholder="请输入车主姓名"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.carOwnerMobile" placeholder="请输入车主手机号"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.technicianName" placeholder="请输入技师姓名"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.technicianMobile" placeholder="请输入技师手机号"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="operation">
            <el-button type="success" @click="handleExport">导出</el-button>
        </div>
        <div class="table-list">
            <el-table
                v-loading="loading"
                :data="tableData"
                border
                style="width: 100%">
                <el-table-column
                    type="index"
                    align="center"
                    width="50">
                </el-table-column>

                <el-table-column
                    label="商品信息"
                    width="240"
                >
                    <template slot-scope="{row,$index}">
                        <div class="product-info">
                            <div class="pic">
                                <el-image
                                    style="display:block;width: 65px; height: 65px"
                                    :src="row.caseImgUrl"
                                    fit="cover"></el-image>
                            </div>
                            <div class="con">
                                <div class="name">{{ row.caseName }}</div>
<!--                                <div class="num">数量：{{row.caseNum}}</div>-->
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="orderNum"
                    label="订单编号"
                    width="180"
                >
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="下单时间"
                    width="180"
                >
                </el-table-column>
                <el-table-column
                    prop="technicianName"
                    label="案例技师姓名"
                    min-width="120"
                >
                </el-table-column>
                <el-table-column
                    prop="technicianMobile"
                    label="案例技师手机号"
                    min-width="120"
                >
                </el-table-column>
                <el-table-column
                    prop="orderAmount"
                    label="实收金额">
                </el-table-column>
                <el-table-column
                    prop="carOwnerName"
                    label="联系人">
                </el-table-column>
                <el-table-column
                    prop="carOwnerMobile"
                    label="联系方式"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="payType"
                    label="支付方式"
                    min-width="120"
                    :formatter="(row) => {return formatterVal(row,'payType')}"
                >
                </el-table-column>
                <el-table-column
                    prop="orderSts"
                    label="订单状态"
                    :formatter="(row) => {return formatterVal(row,'orderSts')}"
                >
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    fixed="right"
                    width="120"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="success" size="mini" @click="handleReview(row,$index)">
                            查看详情
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
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
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import { queryOrderCaseList,exportOrderCaseList } from "@/api/order";
export default {
    name: 'CaseOrder',
    data() {
        return {
            loading:true,
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            date:[],
            filter: {
                caseName:'',
                orderSts:'',
                evaluateSts:'',
                orderNum:'',
                carOwnerName:'',
                carOwnerMobile:'',
                technicianName:'',
                technicianMobile:'',
            },
            tableData: [],
        }
    },
    computed: {
        ...mapGetters([
            'orderSts',
            'afterSaleSts',
            'evaluateSts',
            'payType',
        ])
    },
    created() {
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            let startDate = this.date[0];
            let endDate = this.date[1];
            queryOrderCaseList({
                ...this.filter,
                startDate,
                endDate,
                pageNum:this.pagination.page,
                pageSize:this.pagination.size
            }).then((res) => {
                let { data,total } = res;
                this.pagination.total = total;
                this.tableData = data || [];
                this.loading = false;
            }).catch((e) => {
                this.loading = false;
            })
        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleReview(row,index){
            this.$router.push({
                path:"/order/case/detail",
                query:{
                    id:row.uuid
                }
            })
        },
        handleExport(){
            exportOrderCaseList(this.filter).then((res) => {
                const fileName = '案例订单信息.xls';
                if ('download' in document.createElement('a')) { // 非IE下载
                    const blob = new Blob([res], {type: 'application/ms-excel'});
                    const elink = document.createElement('a');
                    elink.download = fileName;
                    elink.style.display = 'none';
                    elink.href = URL.createObjectURL(blob);
                    document.body.appendChild(elink);
                    elink.click();
                    URL.revokeObjectURL(elink.href); // 释放URL 对象
                    document.body.removeChild(elink);
                }
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
</style>
