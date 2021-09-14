<template>
	<div class="app-container">
		<!--<div class="filter-search">
            <el-form :inline="true">
                <el-form-item label="">
                    <el-input  v-model="filter.courseTitle" placeholder="请输入课程分类名称" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">搜索</el-button>
                </el-form-item>
            </el-form>
        </div>-->
		<div class="operation">
			<el-button type="success" @click="handleAdd">新增</el-button>
		</div>
		<div class="table-list">
			<el-table :data="tableData" v-loading="loading" border style="width: 100%">
				<el-table-column type="index" align="center" width="50">
				</el-table-column>
				<el-table-column prop="maintainTitle" label="养护名称" width="200">
				</el-table-column>
				<el-table-column label="封面图片" width="140" align="center">
					<template slot-scope="{row,$index}">
						<el-image style="width: 100px; height: 100px" :src="row.maintainCover" :preview-src-list="[row.maintainCover]" fit="cover"></el-image>
					</template>
				</el-table-column>
				<el-table-column label="描述">
					<template slot-scope="{row,$index}">
						<div v-html="row.maintainContent"></div>
					</template>
				</el-table-column>
				<el-table-column prop="createdTime" label="创建时间" width="140">
				</el-table-column>
				<el-table-column label="状态">
					<template slot-scope="scope">
						{{scope.row.maintainCheckSts | dtcFilter}}
					</template>
				</el-table-column>
				<el-table-column label="操作" align="center" width="180">
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
			<el-pagination @current-change="handleCurrentChange" :current-page.sync="pagination.page" :page-size="pagination.size" background layout="total, prev, pager, next" :total="pagination.total">
			</el-pagination>
		</div>

		<el-dialog class="dialog-form" append-to-body :title="optType == 'add' ? '新增课程类目' : '编辑课程类目'" :visible.sync="dialogFormVisible" @click="resetForm">
			<el-form v-loading="formLoading" :model="form" :rules="rules" ref="form" label-width="100px">
				<el-form-item label="养护标题" prop="maintainTitle">
					<el-input v-model="form.maintainTitle" placeholder="请输入养护标题" maxLength = '30'></el-input>
				</el-form-item>
				<el-form-item label="所属系统" prop="maintainTitle">
					<el-select clearable filterable v-model="form.attachSys">
						<el-option v-for="(item,index) in suoshuxitog" :key="index" :label="item.lableDesc" :value="item.uuid"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="审核类型" v-if="optType != 'add'">
					<el-radio v-model="form.maintainCheckSts" label="0">待审核</el-radio>
					<el-radio v-model="form.maintainCheckSts" label="1">审核完成</el-radio>
					<el-radio v-model="form.maintainCheckSts" label="2">审核驳回</el-radio>
				</el-form-item>
				<el-form-item label="驳回建议" v-if="form.maintainCheckSts == '2'">
					<editor :quill-index="0" placeholder="请输入审核驳回内容" :content="form.maintainRemarks" @change="editorChange($event,'maintainRemarks')"></editor>
				</el-form-item>
				<el-form-item label="品牌" prop="carModelUuid" ref="carModelUuid">
					<el-select v-model="form.brandName" @change="changeCx">
						<el-option v-for="(item,index) in repairBrand" :key="index" :label="item.configName" :value="item.uuid"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="车型" prop="dtcBrandUuid" ref="dtcBrandUuid">
					<el-select v-model="form.carModelName" @change="changeppCx">
						<el-option v-for="(item,index) in repairBrandcx" :key="index" :label="item.configName" :value="item.uuid"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="封面图片" prop="maintainCover">
					<upload-pic :limit="1" :file-list="form.maintainCover"></upload-pic>
				</el-form-item>
				<el-form-item label="养护内容" prop="maintainContent">
					<editor :quill-index="1" placeholder="请输入养护内容" :content="form.maintainContent" @change="editorChange($event,'maintainContent')"></editor>
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
	import { huliList, huliDetail, huliAdd, huliUpdate, huliDelete } from "@/api/huli";
	import { queryBrandList, queryBrandListCx } from "@/api/artificer"
	import { queryDictList } from "@/api/dict";
	export default {
		name: 'HuliOrder',
		components: {
			'upload-pic': UploadPic,
			'editor': Editor
		},
		data() {
			return {
				loading: true,
				pagination: { //分页信息
					page: 1,
					size: 10,
					total: 0
				},
				repairBrandcx: [],
				repairBrand: [],
				suoshuxitog: [],
				tableData: [],
				optType: '',
				formLoading: true,
				dialogFormVisible: false,
				form: {
					maintainTitle: '',
					maintainContent: '',
					maintainCheckSts: '0',
					maintainCover: [],
					attachSys:'',
					maintainRemarks:'',
				},
				rules: {
					maintainTitle: [{
						required: true,
						message: '请输入养护标题',
						trigger: 'blur'
					}],
					maintainContent: [{
						required: true,
						message: '请输入养护内容',
						trigger: 'blur'
					}],
					maintainCover: [{
						required: true,
						message: '请上传封面图片',
						trigger: 'blur'
					}],
					carModelUuid: [
                    	{required: false, message: '', trigger: 'change'}
                	],
					dtcBrandUuid: [
						{required: false, message: '', trigger: 'change'}
					],

				}
			}
		},
		created() {
			this.initData();
			this.queryDict();
			this.initBrandList();
		},
		filters: {
			dtcFilter(str) {
				if(str == 0) {
					return '待审核'
				} else if(str == 1) {
					return '审核完成'
				} else {
					return '审核驳回'
				}
			}
		},
		methods: {
			queryDict(){
				queryDictList("attach_sys").then((res) => {
					console.log(res)
					this.suoshuxitog = res.data;
				})
			},
			initData() {
				this.loading = true;
				huliList({
					pageNum: this.pagination.page,
					pageSize: this.pagination.size
				}).then((res) => {
					let {
						data,
						total
					} = res;
					this.pagination.total = total;
					this.tableData = data || [];
					this.loading = false;
				})
			},
			handleCurrentChange(page) {
				this.pagination.page = page;
				this.initData()
			},
			handleAdd() {
				this.optType = 'add';
				this.dialogFormVisible = true;
				this.formLoading = false;
				this.form.maintainCheckSts = "0";
				
				this.form.maintainTitle = "";
				this.form.maintainContent = "";
				this.form.maintainCover = [];
				
				this.form.brandName = "ARCFOX极狐";
				this.form.brandUuid = "ba0628f9d98a44b98698f80a05abe7d0";
				this.form.carModelName = "ARCFOX αT";
				this.form.carModelUuid = "2a54eaa436cb4f118fb749119cb3ff28";
				
				this.form.maintainRemarks = "";
				
				this.form.attachSys = "";
			},
			handleEdit(row, index) {
				console.log(row)
				this.optType = 'edit';
				this.formLoading = true;
				this.dialogFormVisible = true;
				this.$nextTick(() => {
					for(let i in this.form) {
						if(i == 'maintainCover') {
							this.form[i] = row[i] ? [{
								url: row[i]
							}] : []
						} else {
							this.form[i] = row[i]
						}
					}
					this.form.uuid = row.uuid;
					this.form.brandName = row.brandName;
					this.form.brandUuid = row.brandUuid;
					this.form.carModelName = row.carModelName;
					this.form.carModelUuid = row.carModelUuid;
					this.form.maintainRemarks = row.maintainRemarks;
					this.form.maintainCheckSts = row.maintainCheckSts.toString();
					this.formLoading = false;
					this.changeCxInit(row.brandUuid);
				})
			},
			handleDel(row, index) {
				this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					huliDelete(row.uuid).then((res) => {
						this.$message({
							message: '操作成功',
							type: 'success'
						});
						this.initData()
					})
				}).catch(() => {

				});
			},
			handleCloseForm() {
				this.dialogFormVisible = false;
				this.resetForm();
			},
			resetForm() {
				this.$refs['form'].resetFields();
			},
			editorChange(event,str) {
				this.form[str] = event
			},
			handleConfirm() {
				this.$refs['form'].validate((valid) => {
					if(valid) {
						this.handleSubmitOpt();
					} else {
						console.log('error submit!!');
						return false;
					}
				});
			},
			handleSubmitOpt() {
				let form = this.form;
				this.form.maintainCheckSts = parseInt(this.form.maintainCheckSts)
//				console.log(form);
				//				return
				let maintainCover = form.maintainCover[0].url;
				switch(this.optType) {
					case "add":
						huliAdd({ ...form,
							maintainCover
						}).then((res) => {
							this.$message({
								message: '操作成功',
								type: 'success'
							});
							this.handleCloseForm();
							this.initData()
						})
						break
					case "edit":
						huliUpdate({ ...form,
							maintainCover
						}, form.uuid).then((res) => {
							this.$message({
								message: '操作成功',
								type: 'success'
							});
							this.handleCloseForm();
							this.initData()
						})
						break
				}
			},
			initBrandList() {
				queryBrandList().then((res) => {
//					console.log(res);
					this.repairBrand = res.data;
					this.loading = false;
				}).catch((e) => {
					console.log(e)
				})
			},
			changeCx(e) {
				this.form.carModelName = "";
				this.form.brandUuid = e;
				this.$nextTick(() => {
					for(var i = 0; i < this.repairBrand.length; i++) {
						if(e == this.repairBrand[i]['uuid']) {
							this.form.brandName = this.repairBrand[i]['configName']
						}
					}
					this.$forceUpdate()
				})
				queryBrandListCx(e).then((res) => {
//					console.log(res);
					this.repairBrandcx = res.data;
					this.loading = false;
				}).catch((e) => {
					console.log(e)
				})
			},
			changeCxInit(e) {
				queryBrandListCx(e).then((res) => {
//					console.log(res);
					this.repairBrandcx = res.data;
					this.loading = false;
				}).catch((e) => {
					console.log(e)
				})
			},
			changeppCx(e) {
				this.form.carModelUuid = e;
				this.$nextTick(() => {
					for(var i = 0; i < this.repairBrandcx.length; i++) {
						if(e == this.repairBrandcx[i]['uuid']) {
							this.form.carModelName = this.repairBrandcx[i]['configName']
						}
					}
					this.$forceUpdate()
//					console.log(this.form.carModelName)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>