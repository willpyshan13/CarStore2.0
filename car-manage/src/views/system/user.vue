<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.searchValue" placeholder="请输入人员名称或用户名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="operation">
            <el-button type="success" @click="handleAdd">新增账号</el-button>
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
                    prop="name"
                    label="姓名"
                >
                </el-table-column>
                <el-table-column
                    prop="username"
                    label="用户名"
                >
                </el-table-column>
                <el-table-column
                    prop="roleName"
                    label="所属角色"
                >
                </el-table-column>
                <el-table-column
                    prop="createdBy"
                    label="账号创建人"
                >
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="创建时间"
                >
                </el-table-column>
                <el-table-column
                    label="状态"
                    align="center"
                    width="120"
                >
                    <template slot-scope="{row,$index}">
                        <el-switch
                            v-model="row.status"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            :active-value="0"
                            :inactive-value="1"
                            :active-text="row.status ? '禁用' : '开启'"
                            @change="handleStatusChange(row)"
                        >
                        </el-switch>
                    </template>
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

        <el-dialog class="dialog-form" append-to-body :title="optType == 'add' ? '新增账号' : '编辑账号'" :visible.sync="dialogFormVisible">
            <el-form :model="form" :rules="rules" ref="form" label-width="100px">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
                    <div class="tips">用于登录使用</div>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="form.password" placeholder="请输入密码"></el-input>
                    <div class="tips">若不设置，则默认初始密码123456</div>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input v-model="form.confirmPassword" placeholder="请重新输入密码"></el-input>
                </el-form-item>
                <el-form-item label="所属角色" prop="roleUuid">
                    <el-select v-model="form.roleUuid">
                        <el-option
                            v-for="item in roleList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="账号状态" prop="status">
                    <el-select v-model="form.status">
                        <el-option
                            label="开启"
                            :value="0">
                        </el-option>
                        <el-option
                            label="禁用"
                            :value="1">
                        </el-option>
                    </el-select>
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
import {getUserList,getRoleList,addUser,updateUser,deleteUser,updateUserStatus} from "@/api/system"
export default {
    name: 'User',
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
            optType:'',
            dialogFormVisible:false,
            form:{
                name:'',
                username:'',
                password:'',
                confirmPassword:'',
                roleUuid:'',
                status:0
            },
            roleList: [],
            rules:{
                username:[
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                roleUuid: [
                    { required: true, message: '请选择所属角色', trigger: 'change' }
                ],
            }
        }
    },
    created() {
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            getUserList({
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
        async handleAdd(){
            this.optType = 'add';
            this.dialogFormVisible = true;
            if(!this.roleList.length){
                this.roleList = await this._getRoleList();
            }
        },
        async handleEdit(row,index){
            this.optType = 'edit';
            this.dialogFormVisible = true;
            if(!this.roleList.length){
                this.roleList = await this._getRoleList();
            }
            this.$nextTick(() => {
                for(let i in this.form){
                    this.form[i] = row[i]
                }
                this.form.uuid = row.uuid;
            })
        },
        handleDel(row,index){
            this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteUser(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        },
        _getRoleList(){
            return  new Promise((resolve) => {
                getRoleList({
                    pageNum:1,
                    pageSize:100
                }).then((res) => {
                    let {data} = res;
                    let roleList = [];
                    if(data.length){
                        roleList = data.map((item) => {
                            return {
                                label:item.roleName,
                                value:item.uuid
                            }
                        })
                    }
                    resolve(roleList)
                })
            })

        },
        handleConfirm(){
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    this.handleSubmitOpt();
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        handleCloseForm(){
            this.dialogFormVisible = false;
            this.resetForm();
        },
        resetForm() {
            this.$refs['form'].resetFields();
        },
        handleStatusChange(row){
            updateUserStatus({uuid:row.uuid,status:row.status}).then((res) => {
                this.$message({
                    message: '操作成功',
                    type: 'success'
                });
                this.initData()
            })
        },
        handleSubmitOpt(){
            let form = this.form;
            switch (this.optType){
                case "add":
                    addUser({...form}).then((res) => {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.handleCloseForm();
                        this.initData()
                    })
                    break
                case "edit":
                    updateUser({...form}).then((res) => {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.handleCloseForm();
                        this.initData()
                    })
                    break
            }
        }
    }
}
</script>

<style lang="scss" scoped>

</style>
