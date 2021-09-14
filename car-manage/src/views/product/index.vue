<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input v-model="filter.goodsName" placeholder="请输入商品名称"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-select v-model="filter.goodsType" placeholder="请选择商品类型">
                        <el-option v-for="(item,index) in goodsType" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-select v-model="filter.storeType" placeholder="请选择店铺类型">
                        <el-option v-for="(item,index) in storeType" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-input v-model="filter.storeName" placeholder="请输入店铺名称"></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-select v-model="filter.sellSts" placeholder="请选择商品状态">
                        <el-option v-for="(item,index) in sellSts" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="">
                    <el-input-number :controls="false" v-model="filter.minPrice" :precision="2" placeholder="商品价格最小值"></el-input-number>
                    <span style="padding:0 10px;">~</span>
                    <el-input-number :controls="false" v-model="filter.maxPrice" :precision="2" placeholder="商品价格最大值"></el-input-number>
                </el-form-item>
                <el-form-item label="">
                    <el-input-number :controls="false" v-model="filter.minSalesNum" placeholder="销量最小值"></el-input-number>
                    <span style="padding:0 10px;">~</span>
                    <el-input-number :controls="false" v-model="filter.maxSalesNum" placeholder="销量最大值"></el-input-number>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="operation">
            <el-button type="success" @click="handleExport">导出</el-button>
<!--            <el-button type="primary" @click="handleAdd">新增</el-button>-->
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
                    prop="goodsName"
                    label="商品名称"
                    width="200"
                >
                </el-table-column>
                <el-table-column
                    prop="storeName"
                    label="店铺名称"
                    width="180"
                >
                </el-table-column>
                <el-table-column
                    prop="manHourCost"
                    label="工时费"
                    width="80"
                >
                </el-table-column>
                <el-table-column
                    prop="materialsExpenses"
                    label="材料费"
                    width="80"
                >
                </el-table-column>
                <el-table-column
                    prop="surplusNum"
                    label="库存">
                </el-table-column>
                <el-table-column
                    prop="salesNum"
                    label="销量">
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="创建时间">
                </el-table-column>
                <el-table-column
                    prop="status"
                    label="销售状态"
                >
                    <template slot-scope="{row,$index}">
                        {{sellSts.find((item) => item.uuid == row.sellSts).lableDesc}}
                    </template>
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    width="200"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="primary" size="mini" @click="handleEdit(row,$index)">
                            编辑商品
                        </el-button>
                        <el-button type="danger" size="mini" @click="handleDel(row,$index)">
                            立即下架
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
import { queryGoodsList,exportGoodsList,deleteGoods } from "@/api/product"
import {deleteRole} from "@/api/system";
export default {
    name: 'Product',
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
                goodsName: "",
                goodsType: "",
                maxPrice: undefined,
                minPrice: undefined,
                maxSalesNum: undefined,
                minSalesNum: undefined,
                storeName: "",
                storeType: "",
                sellSts: "",
            },
            storeType:[],
            goodsType:[],
            tableData: []
        }
    },
    computed: {
        ...mapGetters([
            'sellSts',
        ])
    },
    created() {
        this.initDict();
        this.initData()
    },
    methods: {
        async initDict(){
            this.storeType = await this.$store.dispatch('dict/getDict','store_type');
            this.goodsType = await this.$store.dispatch('dict/getDict','goods_type');
        },
        initData() {
            this.loading = true;
            queryGoodsList({
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
        handleEdit(row,index){
            this.$router.push({
                path:'/product/detail',
                query:{
                    id:row.uuid
                }
            })
        },
        handleAdd(){
            this.$router.push({
                path:'/product/detail',
            })
        },
        handleExport(){
            exportGoodsList(this.filter).then((res) => {
                const fileName = '商品信息.xls';
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
                deleteGoods(row.uuid).then((res) => {
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
