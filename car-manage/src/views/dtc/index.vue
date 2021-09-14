<template>
    <div class="app-container">
        <div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.dtcCode" placeholder="请输入dtc故障代码"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="operation">
            <el-button type="success" @click="handleAdd">新增</el-button>
            <el-button type="success" @click="handleImport">批量导入</el-button>
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
                    prop="dtcCode"
                    label="故障代码"
                >
                </el-table-column>
                <el-table-column
                    prop="dtcDefinition"
                    label="故障名称"
                >
                </el-table-column>
                <el-table-column
                    prop="dtcTypeName"
                    label="DTC类型"
                >
                </el-table-column>
                <el-table-column
                    prop="configName"
                    label="车辆品牌"
                >
                </el-table-column>
				<!--<el-table-column prop="dtcCheckSts" label="审核状态" min-width="120">
					<template slot-scope="scope">
						<span class="cursorSpan">{{scope.row.dtcCheckSts | dtcFilter}}</span>
					</template>
				</el-table-column>-->
				<el-table-column
                    label="状态">
                    <template slot-scope="scope">
                        {{scope.row.dtcCheckSts | dtcFilter}}
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
        <el-dialog class="dialog-form" append-to-body title="批量导入" :visible.sync="dialogFormVisible" @close="resetForm">
            <el-form
                      v-loading="formLoading"
                      :model="form"
                      :rules="rules"
                      ref="form"
                      label-width="100px"
            >
                <el-form-item label="导入文件" prop="file">
                    <el-upload
                        :limit="1"
                        :before-upload="beforeUpload"
                        action="#"
                        :file-list="form.file"
                    >
                        <el-button size="small" type="primary">点击上传</el-button>
                    </el-upload>
                </el-form-item>
                <el-form-item label="品牌" prop="brandUuid">
                    <el-select clearable filterable  v-model="form.brandUuid">
                        <el-option v-for="(item,index) in repairBrand" :key="index"
                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="DTC类型" prop="dtcTypeUuid">
                    <el-select clearable v-model="form.dtcTypeUuid">
                        <el-option v-for="(item,index) in dtcType" :key="item.uuid"
                                   :label="item.lableDesc" :value="item.uuid"></el-option>
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
import {getToken} from '@/utils/auth'
import {dtcList,dtcAdd,dtcDelete,dtcUpdate,batchImport} from "@/api/dtc"
import {queryBrandList} from "@/api/artificer";
import {uploadPic} from "@/api/upload";
export default {
    name: 'Dtc',
    data() {
        return {
            loading:true,
            pagination: { //分页信息
                page: 1,
                size: 10,
                total: 0
            },
            filter: {
                dtcCode:''
            },
            tableData: [],
            formLoading:true,
            dialogFormVisible:false,
            form:{
                file:[],
                brandUuid:'',
                dtcTypeUuid:'',
            },
            rules:{
                file:[
                    { required: true, message: '请上传要导入的文件', trigger: 'blur' }
                ],
                brandUuid:[
                    { required: true, message: '请选择品牌', trigger: 'change' }
                ],
                dtcTypeUuid:[
                    { required: true, message: '请选择DTC类型', trigger: 'change' }
                ]
            },
            repairBrand: [],
            dtcType:[],
        }
    },
    computed:{
      action(){
          return process.env.VUE_APP_BASE_API + '/order/dtc/batchImport'
      },
      token(){
          return getToken()
      }
    },
    created() {
        this.initData()
    },
    filters: {
	    dtcFilter (str) {
	      if (str == 0) {
	        return '待审核'
	      } else if (str == 1) {
	        return '审核完成'
	      } else{
	      	return '审核驳回'
	      }
	    }
	},
    methods: {
        initData() {
            this.loading = true;
            dtcList({
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
        initBrandList(){
            return new Promise((resolve,reject) => {
                queryBrandList().then((res) => {
                    let {data} = res;
                    let repairBrand = [];
                    repairBrand = data.map((item) => {
                        return {
                            lableDesc:item.configName,
                            uuid:item.uuid,
                        }
                    })
                    this.repairBrand = repairBrand || [];
                    resolve()
                }).catch((e) => {
                    console.log(e)
                })
            })

        },
        handleCurrentChange(page) {
            this.pagination.page = page;
            this.initData()
        },
        handleAdd(){
            this.$router.push({
                path:'/dtc/detail',
            })
        },
        handleEdit(row,index){
            this.$router.push({
                path:'/dtc/detail',
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
                dtcDelete(row.uuid).then((res) => {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.initData()
                })
            }).catch(() => {

            });
        },
        async handleImport(){
            this.dialogFormVisible = true;
            this.dtcType = await this.$store.dispatch('dict/getDict','dtc_type');
            if(!this.repairBrand.length){
                await this.initBrandList()
            }
            this.formLoading = false;
        },
        handleCloseForm(){
            this.dialogFormVisible = false;
            this.resetForm();
        },
        resetForm() {
            this.$refs['form'].resetFields();
        },
        handleConfirm(){
            this.$refs['form'].validate((valid) => {
                let form = this.form;
                const data = new FormData();
                data.append('file', form.file[0]);
                if (valid) {
                    const loading = this.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    batchImport(`?brandUuid=${form.brandUuid}&dtcTypeUuid=${form.dtcTypeUuid}`,data).then((res) => {
                        this.$message.success('导入成功');
                        this.handleCloseForm();
                        loading.close();
                        this.initData();
                    }).catch(() => {
                        loading.close();
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        beforeUpload(file) {
            this.form.file = [file]
            return false;
        },
    }
}
</script>

<style lang="scss" scoped>

</style>
