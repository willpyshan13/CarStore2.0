<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.roleName" placeholder="请输入角色名称"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="operation">
            <el-button type="success" @click="handleAdd">新增角色</el-button>
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
                    prop="roleName"
                    label="角色名"
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
                    prop="lastUpdatedTime"
                    label="最后编辑创建时间"
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

        <el-dialog class="dialog-form" append-to-body :title="optType == 'add' ? '新增角色' : '编辑角色'" :visible.sync="dialogFormVisible">
            <el-form  v-loading="formLoading" :model="form" :rules="rules" ref="form" label-width="100px">
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="form.roleName" placeholder="请输入角色名称"></el-input>
                </el-form-item>
                <el-form-item label="权限设置" prop="menuList">
                    <el-tree
                        style="margin-top:4px;"
                        ref="tree"
                        :data="menuList"
                        show-checkbox
                        node-key="uuid"
                        default-expand-all
                        :default-checked-keys="form.menuList"
                        :props="defaultProps"
                    >
                    </el-tree>
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
import {getRoleList,queryAllMenuList, addRole,updateRole,queryRoleDetail,deleteRole} from "@/api/system"
export default {
    name: 'Role',
    data() {
        return {
            loading:true,
            formLoading:true,
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            filter: {
                roleName:''
            },
            tableData: [],
            optType:'',
            dialogFormVisible:false,
            form:{
                roleName:'',
                menuList:[]
            },
            menuList: [],
            defaultProps:{
                children: 'childList',
                label: 'menuName'
            },
            rules:{
                roleName:[
                    { required: true, message: '请输入角色名称', trigger: 'blur' }
                ]
            }
        }
    },
    created() {
        this.initData()
    },
    methods: {
        initData() {
            this.loading = true;
            getRoleList({
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
            this.formLoading = true;
            this.dialogFormVisible = true;
            if(!this.menuList.length){
                this.menuList = await this._queryAllMenuList();
            }
            this.formLoading = false;
        },
        async handleEdit(row,index){
            this.optType = 'edit';
            this.formLoading = true;
            this.dialogFormVisible = true;
            if(!this.menuList.length){
                this.menuList = await this._queryAllMenuList();
            }
            this.form.menuList = await this._queryRoleDetail(row);
            this.$nextTick(() => {
                for(let i in this.form){
                    if(i != 'menuList'){
                        this.form[i] = row[i]
                    }
                }
                this.form.uuid = row.uuid;
                this.formLoading = false;
            })
        },
        handleDel(row,index){
            this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteRole(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        },
        _queryRoleDetail(row){
            return  new Promise((resolve) => {
                queryRoleDetail(row.uuid).then((res) => {
                    let {data} = res;
                    resolve(data.menuList)
                })
            })
        },
        _queryAllMenuList(){
            return  new Promise((resolve) => {
                queryAllMenuList().then((res) => {
                    let {data} = res;
                    console.log(data)
                    resolve(data)
                })
            })
        },
        handleConfirm(){
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    console.log(this.$refs.tree.getCheckedNodes());
                    let menuList = this.$refs.tree.getCheckedNodes();
                    if(!menuList.length){
                        this.$message({
                            message: '请选择权限',
                            type: 'error'
                        });
                        return false;
                    }else{
                        this.form.menuList = menuList.map(item => item.uuid);
                        this.handleSubmitOpt();
                    }
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
                    addRole({...form}).then((res) => {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.handleCloseForm();
                        this.initData()
                    })
                    break
                case "edit":
                    updateRole({...form}).then((res) => {
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
