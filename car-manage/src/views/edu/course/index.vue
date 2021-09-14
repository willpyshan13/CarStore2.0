<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.courseTitle" placeholder="请输入课程名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="">
                    <el-input  v-model="filter.courseLecturer" placeholder="请输入主讲技师" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="operation">
            <el-button type="success" @click="handleAdd">新增</el-button>
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
                    prop="courseTitle"
                    label="课程名称"
                >
                </el-table-column>
                <el-table-column
                    prop="courseLecturer"
                    label="主讲技师"
                >
                </el-table-column>
                <el-table-column
                    prop="courseSalesVolume"
                    label="销售量"
                >
                </el-table-column>
                <el-table-column
                    prop="courseParentUuidName"
                    label="教程类型"
                >
                </el-table-column>
                <el-table-column
                    label="操作"
                    align="center"
                    width="180"
                >
                    <template slot-scope="{row,$index}">
                        <el-button type="primary" size="mini" @click="handleEdit(row,$index)">
                            编辑
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
import {courseList,courseDelete} from "@/api/edu"
export default {
    name: 'EduCourse',
    data() {
        return {
            loading:true,
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            filter: {
                courseTitle:'',
                courseLecturer:'',
            },
            tableData: [],
        }
    },
    created() {
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            courseList({
                ...this.filter,
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
        handleAdd(){
            this.$router.push({
                path:'/edu/course/detail',
            })
        },
        handleEdit(row,index){
            this.$router.push({
                path:'/edu/course/detail',
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
                courseDelete(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
