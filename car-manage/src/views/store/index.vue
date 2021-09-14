<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input clearable v-model="filter.storeName" placeholder="店铺名称"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.storeType" placeholder="店铺类型">
                        <el-option v-for="(item,index) in storeType" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.contactsName" placeholder="联系人姓名"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable maxlength="11" v-model="filter.mobile" placeholder="联系人手机号"></el-input>
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
                :data="tableData"
                v-loading="loading"
                border
                style="width: 100%">
                <el-table-column
                    type="index"
                    align="center"
                    width="50">
                </el-table-column>
                <el-table-column
                    prop="storeName"
                    label="店铺名称"
                    width="180"
                >
                </el-table-column>
                <el-table-column
                    label="店铺类型"
                    width="180"
                >
                    <template slot-scope="{row,$index}">
                        {{storeType.find((item) => item.uuid == row.storeType).lableDesc}}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="addressProvinceName"
                    label="省份">
                </el-table-column>
                <el-table-column
                    prop="addressCityName"
                    label="城市">
                </el-table-column>
                <el-table-column
                    prop="userName"
                    label="联系人姓名">
                </el-table-column>
                <el-table-column
                    prop="mobile"
                    label="联系人手机号">
                </el-table-column>
                <el-table-column
                    width="150"
                    prop="createdTime"
                    label="注册时间">
                </el-table-column>
                <el-table-column
                    label="状态">
                    <template slot-scope="{row,$index}">
                        {{checkSts.find((item) => item.uuid == row.checkSts).lableDesc}}
                    </template>
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    width="160"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="primary" size="mini" @click="handleReview(row,$index)">
                            审核
                        </el-button>
                        <el-button type="danger" size="mini" @click="handleDel(row,$index)">
                            删除
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
import { queryDictList,exportStoreList,deleteStore } from "@/api/store";

export default {
    name: 'Store',
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
                storeName:'',
                storeType:'',
                contactsName:'',
                mobile:'',
            },
            tableData: [],
            storeType:[]
        }
    },
    computed: {
        ...mapGetters([
            'checkSts',
        ])
    },
    created() {
        this.initDict();
        this.initData();
    },
    methods: {
        async initDict(){
            this.storeType = await this.$store.dispatch('dict/getDict','store_type');
        },
        initData() {
            this.loading = true;
            queryDictList({
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
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleReview(row,index){
            this.$router.push({
                path:'/store/detail',
                query:{
                    id:row.uuid
                }
            })
        },
        handleDel(row,index){
            this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteStore(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        },
        handleExport(){
            exportStoreList(this.filter).then((res) => {
                const fileName = '店铺信息.xls';
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
