<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.searchValue" placeholder="请输入关键字"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
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
                    prop="createdBy"
                    label="账号"
                    width="100"
                >
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="创建时间"
                    width="140"
                >
                </el-table-column>
                <el-table-column
                    prop="operIp"
                    label="请求IP"
                    width="120"
                >
                </el-table-column>
                <el-table-column
                    prop="operUri"
                    label="操作路径"
                >
                </el-table-column>
                <el-table-column
                    prop="operModul"
                    label="操作模块"
                >
                </el-table-column>
                <el-table-column
                    prop="operType"
                    label="操作类型"
                    width="80"
                >
                </el-table-column>
                <el-table-column
                    prop="operDesc"
                    label="操作描述"
                >
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    width="100"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="primary" size="mini" @click="handleLook(row,$index)">
                            查看
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

        <el-dialog class="page-detail dialog-form" append-to-body title="查看日志" :visible.sync="dialogFormVisible">
            <el-form  ref="form" label-width="100px">
                <el-form-item label="账号">
                    <div class="exhibition-text">{{form.createdBy}}</div>
                </el-form-item>
                <el-form-item label="创建时间">
                    <div class="exhibition-text">{{form.createdTime}}</div>
                </el-form-item>
                <el-form-item label="请求IP">
                    <div class="exhibition-text">{{form.operIp}}</div>
                </el-form-item>
                <el-form-item label="请求参数">
                    <el-popover
                        placement="bottom"
                        title="请求参数"
                        width="400"
                        trigger="hover"
                        :content="form.operRequParam">
                        <div slot="reference" class="ellipsis exhibition-text">{{form.operRequParam}}</div>
                    </el-popover>
                </el-form-item>
                <el-form-item label="返回参数">
                    <el-popover
                        placement="bottom"
                        title="返回参数"
                        width="400"
                        trigger="hover"
                        :content="form.operRespParam">
                        <div slot="reference" class="ellipsis exhibition-text">{{form.operRespParam}}</div>
                    </el-popover>
                </el-form-item>
                <el-form-item label="操作路径">
                    <div class="exhibition-text">{{form.operUri}}</div>
                </el-form-item>
                <el-form-item label="操作模块">
                    <div class="exhibition-text">{{form.operModul}}</div>
                </el-form-item>
                <el-form-item label="操作类型">
                    <div class="exhibition-text">{{form.operType}}</div>
                </el-form-item>
                <el-form-item label="操作描述">
                    <div class="exhibition-text">{{form.operDesc}}</div>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCloseForm">取 消</el-button>
                <el-button type="primary" @click="handleConfirm">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import {getLogList} from "@/api/system"
export default {
    name: 'Log',
    data() {
        return {
            loading:true,
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            filter: {
                searchValue:''
            },
            tableData: [],
            dialogFormVisible:false,
            form:{

            }
        }
    },
    created() {
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            getLogList({
                // ...this.filter,
                pageNum:this.pagination.page,
                pageSize:this.pagination.size
            }).then((res) => {
                let { data,total } = res;
                this.pagination.total = total;
                this.tableData = data || [];
                this.loading = false;
            })
        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleConfirm(){
            this.$refs['form'].validate((valid) => {
                if (valid) {

                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        handleCloseForm(){
            this.dialogFormVisible = false;
        },
        handleLook(row,index){
            this.form = row;
            this.$nextTick(() => {
                this.dialogFormVisible = true;
            })
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
