<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input clearable v-model="filter.userName" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.mobile" placeholder="请输入联系方式"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-date-picker
                        clearable
                        v-model="filter.startTime"
                        type="datetime"
                        placeholder="请选择发起时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.userRole" placeholder="请选择提现角色">
                        <el-option
                            v-for="(item,index) in userRole"
                            :key="item.uuid"
                            :label="item.lableDesc"
                            :value="item.uuid"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.checkSts" placeholder="请选择审核状态">
                        <el-option
                            v-for="(item,index) in checkSts"
                            :key="item.uuid"
                            :label="item.lableDesc"
                            :value="item.uuid"
                        ></el-option>
                    </el-select>
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
                    prop="userName"
                    label="姓名"
                    width="180"
                >
                </el-table-column>
                <el-table-column
                    prop="mobile"
                    label="联系方式"
                    width="180"
                >
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="发起时间">
                </el-table-column>
                <el-table-column
                    prop="withdrawalAmount"
                    label="提现金额">
                </el-table-column>
                <el-table-column
                    prop="lastUpdatedTime"
                    label="审核时间">
                </el-table-column>
                <el-table-column
                    label="审核状态">
                    <template slot-scope="{row,$index}">
                        {{checkSts.find((item) => item.uuid == row.checkSts) ? checkSts.find((item) => item.uuid == row.checkSts).lableDesc : ''}}
                    </template>
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="success" size="mini" @click="handleReview(row,$index)">
                            审核
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
import CountTo from 'vue-count-to'
import { queryWithdrawalList,exportWithdrawalList } from "@/api/withdraw";
export default {
    name: 'Withdraw',
    components: {
        CountTo
    },
    data() {
        return {
            loading:true,
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            filter: {
                userName: "",
                mobile: "",
                startTime: "",
                userRole:'',
                checkSts:''
            },
            tableData: []
        }
    },
    computed: {
        ...mapGetters([
            'checkSts',
            'userRole'
        ])
    },
    created() {
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            queryWithdrawalList({
                ...this.filter,
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
        handleReview(row,index){
            this.$router.push({
                path:"/withdraw/detail",
                query:{
                    id:row.uuid
                }
            })
        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleExport(){
            exportWithdrawalList(this.filter).then((res) => {
                const fileName = '提现信息.xls';
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
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
