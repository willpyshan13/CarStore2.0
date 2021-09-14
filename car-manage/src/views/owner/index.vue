<template>
    <div class="app-container">
        <div class="statistics">
            <el-row :gutter="40" class="panel-group">
                <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            注册用户：
                        </div>
                        <count-to :start-val="0" :end-val="vehicleUserCount.registerCount" :duration="2600" class="card-panel-num" />
                    </el-card>
                </el-col>
                <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
                    <el-card shadow="hover" class="card-panel">
                        <div class="card-panel-text">
                            车主：
                        </div>
                        <count-to :start-val="0" :end-val="vehicleUserCount.ownerCount" :duration="2000" class="card-panel-num" />
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
                    prop="userName"
                    label="姓名"
                    width="80"
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
                    label="绑定车数量"
                    min-width="100"
                >
                    <template slot-scope="{row,$index}">
                        {{row.vehicleList.length}}
                    </template>
                </el-table-column>
                <el-table-column
                    label="车牌号"
                    min-width="160"
                >
                    <template slot-scope="{row,$index}">
                        <span v-for="(item,index) in row.vehicleList">
                            {{item.plateNumber}} <template v-if="index != (row.vehicleList.length -1)">/</template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="车辆品牌"
                    min-width="160"
                >
                    <template slot-scope="{row,$index}">
                        <span v-for="(item,index) in row.vehicleList">
                            {{item.vehicleBrandName}} <template v-if="index != (row.vehicleList.length -1)">/</template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="车型"
                    min-width="160"
                >
                    <template slot-scope="{row,$index}">
                        <span v-for="(item,index) in row.vehicleList">
                            {{item.vehicleModelName}} <template v-if="index != (row.vehicleList.length -1)">/</template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="行驶证登记日期"
                    min-width="160"
                >
                    <template slot-scope="{row,$index}">
                        <span v-for="(item,index) in row.vehicleList">
                            {{item.licenseRegisterDate}} <template v-if="index != (row.vehicleList.length -1)">/</template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="排量"
                    min-width="100"
                >
                    <template slot-scope="{row,$index}">
                        <span v-for="(item,index) in row.vehicleList">
                            {{item.displacementName}} <template v-if="index != (row.vehicleList.length -1)">/</template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="燃油类型"
                    min-width="100"
                >
                    <template slot-scope="{row,$index}">
                        <span v-for="(item,index) in row.vehicleList">
                            {{item.fuelTypeName}} <template v-if="index != (row.vehicleList.length -1)">/</template>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column
                    width="150"
                    prop="createdTime"
                    label="注册时间">
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    fixed="right"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="primary" size="mini" @click="handleEdit(row,$index)">
                            编辑
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
import { queryVehicleUserCount,queryList,exportVehicleList} from "@/api/owner"
import { queryAreaList } from '@/api/area'

export default {
    name: 'Owner',
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
            vehicleUserCount:{
                ownerCount: 0,
                registerCount:0
            },
            provinceList:[],
            cityList:[]
        }
    },
    created() {
        this._queryVehicleUserCount();
        this._queryAreaList(-1)
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            queryList({
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
        async _queryVehicleUserCount(){
            let { data } = await queryVehicleUserCount();
            this.vehicleUserCount = data;
        },
        async _queryAreaList(type){
            let { data } = await queryAreaList(type);
            if(type == -1){
                this.provinceList = data;
            }else{
                this.cityList = data;
            }
        },
        handleProvinceChange(val){
            this.filter.cityUuid = '';
            this.cityList = [];
            if(val){
                this._queryAreaList(val)
            }
        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleEdit(row,index){
            this.$router.push({
                path:'/owner/detail',
                query:{
                    id:row.uuid
                }
            })
        },
        handleExport(){
            exportVehicleList(this.filter).then((res) => {
                const fileName = '车主信息.xls';
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
