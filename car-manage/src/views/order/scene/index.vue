<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
<!--                <el-form-item label="">-->
<!--                    <el-date-picker-->
<!--                        clearable-->
<!--                        v-model="date"-->
<!--                        type="daterange"-->
<!--                        range-separator="至"-->
<!--                        start-placeholder="下单开始日期"-->
<!--                        end-placeholder="下单结束日期"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                       >-->
<!--                    </el-date-picker>-->
<!--                </el-form-item>-->
                <el-form-item label="">
                    <el-select clearable v-model="filter.queryType" placeholder="请选择订单类型">
                        <el-option v-for="(item,index) in queryType" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
<!--        <div class="operation">-->
<!--            <el-button type="success" @click="handleExport">导出</el-button>-->
<!--        </div>-->
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
                    prop="buyerName"
                    label="下单客户"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="buyerMobile"
                    label="联系电话"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="brandName"
                    label="品牌名称"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="carModelName"
                    label="车型"
                    width="200"
                >
                </el-table-column>
                <el-table-column
                    prop="distance"
                    label="距离"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="faultDesc"
                    label="故障描述"
                >
                </el-table-column>
                <el-table-column
                    label="抢单状态">
                    <template slot-scope="{row,$index}">
                        {{row.grabbingOrdersSts == 1 ? '已抢' : '未抢'}}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="totalAmount"
                    label="实收金额">
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
import { sceneOrderList,exportOrderDtcList } from "@/api/order";
export default {
    name: 'SceneOrder',
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
                queryType:'',
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
            'answerSts',
            'orderType',
            'queryType',
            'grabbingOrdersSts',
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
            sceneOrderList({
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
                path:"/order/scene/detail",
                query:{
                    id:row.uuid
                }
            })
        },
        handleExport(){
            exportOrderDtcList(this.filter).then((res) => {
                const fileName = 'DTC订单信息.xls';
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
