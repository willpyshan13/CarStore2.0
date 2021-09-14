<template>
    <div class="app-container">
        <div class="statistics">
            <el-row :gutter="40" class="panel-group">
                <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            注册用户：
                        </div>
                        <count-to :start-val="0" :end-val="Number(technicianCount.registerCount)" :duration="2600" class="card-panel-num" />
                    </el-card>
                </el-col>
                <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            订单总数：
                        </div>
                        <count-to :start-val="0" :end-val="Number(technicianCount.orderCount)" :duration="2000" class="card-panel-num" />
                    </el-card>
                </el-col>
                <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            上架案例：
                        </div>
                        <count-to :start-val="0" :end-val="Number(technicianCount.caseCount)" :duration="2000" class="card-panel-num" />
                    </el-card>
                </el-col>
                <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            回答问题：
                        </div>
                        <count-to :start-val="0" :end-val="Number(technicianCount.qaCount)" :duration="2000" class="card-panel-num" />
                    </el-card>
                </el-col>
                <el-col :xs="12" :sm="12" :lg="4" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            现场支持：
                        </div>
                        <count-to :start-val="0" :end-val="Number(technicianCount.supportCount)" :duration="2000" class="card-panel-num" />
                    </el-card>
                </el-col>
            </el-row>
        </div>
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input clearable v-model="filter.userName" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input clearable v-model="filter.mobile" placeholder="请输入联系方式"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.provinceUuid" placeholder="请选择省" @change="handleProvinceChange">
                        <el-option v-for="(item,index) in provinceList" :key="item.uuid" :label="item.areaName" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-select clearable v-model="filter.cityUuid" placeholder="请选择市">
                        <el-option v-for="(item,index) in cityList" :key="item.uuid" :label="item.areaName" :value="item.uuid"></el-option>
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
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="mobile"
                    label="联系方式"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    label="地区"
                    min-width="100"
                >
                    <template slot-scope="{row,$index}">
                        {{row.addressProvinceName}} - {{row.addressCityName}}
                    </template>
                </el-table-column>
                <el-table-column
                    prop="technologyTypeName"
                    label="技师类型">
                </el-table-column>
                <el-table-column
                    prop="technicianBrandName"
                    label="维修品牌">
                </el-table-column>
                <el-table-column
                    prop="orderCount"
                    label="订单总数">
                </el-table-column>
                <el-table-column
                    prop="caseCount"
                    label="上传案例">
                </el-table-column>
                <el-table-column
                    prop="qaCount"
                    label="回答问题">
                </el-table-column>
                <el-table-column
                    prop="supportCount"
                    label="现场支持">
                </el-table-column>
                <el-table-column
                    width="150"
                    prop="createdTime"
                    label="注册时间">
                </el-table-column>
                <el-table-column
                    label="审核状态">
                    <template slot-scope="{row,$index}">
                        {{checkSts.find((item) => item.uuid == row.checkSts).lableDesc}}
                    </template>
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    width="180"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="success" size="mini" @click="handleReview(row,$index)">
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
import { queryTechnicianList,queryTechnicianCount,exportTechnicianList} from "@/api/artificer"
import { queryAreaList } from '@/api/area'
import {deleteTechnician} from "@/api/artificer";
export default {
    name: 'Artificer',
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
                provinceUuid: "",
                cityUuid: "",
            },
            tableData: [],
            technicianCount:{
                caseCount: 0,
                orderCount: 0,
                qaCount: 0,
                registerCount:0,
                supportCount:0
            },
            provinceList:[],
            cityList:[]
        }
    },
    computed: {
        ...mapGetters([
            'checkSts',
        ])
    },
    created() {
        this._queryTechnicianCount()
        this._queryAreaList(-1,'province')
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            queryTechnicianList({
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
        async _queryTechnicianCount(){
            let { data } = await queryTechnicianCount();
            this.technicianCount = data;
        },
        async _queryAreaList(type,dataType) {
            let {data} = await queryAreaList(type);
            switch (dataType){
                case 'province':
                    this.provinceList = data;
                    break
                case 'city':
                    this.cityList = data;
                    break
                case 'district':
                    this.districtList = data;
                    break
            }
        },
        handleProvinceChange(val){
            this.filter.cityUuid = '';
            this.cityList = [];
            if(val){
                this._queryAreaList(val,'city')
            }
        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleReview(row,index){
            this.$router.push({
                path:"/artificer/detail",
                query:{
                    id:row.uuid
                }
            })
        },
        handleExport(){
            exportTechnicianList(this.filter).then((res) => {
                const fileName = '技师信息.xls';
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
        handleDel(row,index){
            this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteTechnician(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        },
    }
}
</script>

<style lang="scss" scoped>

</style>
