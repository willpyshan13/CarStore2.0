<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.courseTitle" placeholder="请输入课程分类名称" clearable></el-input>
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
                    label="课程分类名称"
                    width="200"
                >
                </el-table-column>
                <el-table-column
                    label="封面图片"
                    width="140"
                    align="center"
                >
                    <template slot-scope="{row,$index}">
                        <el-image
                            style="width: 100px; height: 100px"
                            :src="row.courseCover"
                            :preview-src-list="[row.courseCover]"
                            fit="cover"></el-image>

                    </template>
                </el-table-column>
                <el-table-column
                    label="描述"
                >
                    <template slot-scope="{row,$index}">
                        <div v-html="row.courseDesc"></div>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="createdTime"
                    label="创建时间"
                    width="140"
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


        <el-dialog class="dialog-form" append-to-body :title="optType == 'add' ? '新增课程类目' : '编辑课程类目'" :visible.sync="dialogFormVisible" @click="resetForm">
            <el-form  v-loading="formLoading" :model="form" :rules="rules" ref="form" label-width="100px">
                <el-form-item label="类目名称" prop="courseTitle">
                    <el-input v-model="form.courseTitle" placeholder="请输入课程类目名称"></el-input>
                </el-form-item>
                <el-form-item label="封面图片" prop="courseCover">
                    <upload-pic
                        :limit="1"
                        :file-list="form.courseCover"
                    ></upload-pic>
                </el-form-item>
                <el-form-item label="课程描述" prop="courseDesc">
                    <editor :quill-index="0" placeholder="请输入课程描述" :content="form.courseDesc"  @change="editorChange($event)"></editor>
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
import UploadPic from "@/components/UploadPic"
import Editor from "@/components/Editor/Editor";
import {courseParentList,courseParentDelete,courseParentAdd,courseParentUpdate} from "@/api/edu"
export default {
    name: 'EduClassify',
    components:{
        'upload-pic':UploadPic,
        'editor':Editor
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
                courseTitle:''
            },
            tableData: [],
            optType:'',
            formLoading:true,
            dialogFormVisible:false,
            form:{
                courseTitle:'',
                courseDesc:'',
                courseCover:[],
            },
            rules:{
                courseTitle:[
                    { required: true, message: '请输入课程类目名称', trigger: 'blur' }
                ],
                courseDesc:[
                    { required: true, message: '请输入课程描述', trigger: 'blur' }
                ],
                courseCover:[
                    { required: true, message: '请上传封面图片', trigger: 'blur' }
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
            courseParentList({
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
            this.optType = 'add';
            this.dialogFormVisible = true;
            this.formLoading = false;
        },
        handleEdit(row,index){
            this.optType = 'edit';
            this.formLoading = true;
            this.dialogFormVisible = true;
            this.$nextTick(() => {
                for(let i in this.form){
                    if(i == 'courseCover'){
                        this.form[i] = row[i] ? [{url:row[i]}] : []
                    }else{
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
                courseParentDelete(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        },
        handleCloseForm(){
            this.dialogFormVisible = false;
            this.resetForm();
        },
        resetForm() {
            this.$refs['form'].resetFields();
        },
        editorChange(event){
            this.form['courseDesc'] = event
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
        handleSubmitOpt(){
            let form = this.form;
            let courseCover = form.courseCover[0].url;
            switch (this.optType){
                case "add":
                    courseParentAdd({...form,courseCover}).then((res) => {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.handleCloseForm();
                        this.initData()
                    })
                    break
                case "edit":
                    courseParentUpdate({...form,courseCover},form.uuid).then((res) => {
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
