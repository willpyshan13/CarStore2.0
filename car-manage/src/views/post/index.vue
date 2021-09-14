<template>
	<div class="app-container">
		<div class="filter-search">
			<el-form :inline="true">
				<el-form-item label="">
					<el-input v-model="filter.orderName" placeholder="标题名称"></el-input>
				</el-form-item>
				<el-form-item label="">
					<el-select v-model="filter.orderType" placeholder="内容分类">
						<el-option v-for="(item,index) in orderType" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="">
					<el-input v-model="filter.mobile" placeholder="发布人手机号"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="initData">搜索</el-button>
				</el-form-item>
			</el-form>
		</div>
		<div class="table-list">
			<el-table v-loading="loading" :data="tableData" border style="width: 100%">
				<el-table-column type="index" align="center" width="50">
				</el-table-column>
				<el-table-column prop="orderName" label="标题" width="180">
				</el-table-column>
				<el-table-column label="分类" width="180">
					<template slot-scope="{row,$index}">
						{{orderType.find((item) => item.uuid == row.orderType) ? orderType.find((item) => item.uuid == row.orderType).lableDesc : ''}}
					</template>
				</el-table-column>
				<el-table-column label="内容">
					<template slot-scope="{row,$index}">
						<div class="contentDetail" v-html="row.contentDetail"></div>
					</template>
				</el-table-column>
				<el-table-column label="状态">
					<template slot-scope="{row,$index}">
						{{checkSts.find((item) => item.uuid == row.checkSts).lableDesc}}
					</template>
				</el-table-column>
				<el-table-column label="操作" align="center" width="200">
					<template slot-scope="{row,$index}">
						<el-button type="primary" size="mini" @click="handleReview(row,$index)">
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
			<el-pagination @current-change="handleCurrentChange" :current-page.sync="pagination.page" :page-size="pagination.size" background layout="total, prev, pager, next" :total="pagination.total">
			</el-pagination>
		</div>

		<!--审核弹出层-->
		<el-dialog title="审核" :visible.sync="dialogFormVisible">
			<div class="page-detail">
				<el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="100px">
					<div class="section">
						<el-row>
							<el-col :span="24">
								<el-form-item label="标题">
									<div class="exhibition-text">{{form.orderName}}</div>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="分类">
									<div class="exhibition-text">
										{{orderType.find((item) => item.uuid == form.orderType) ? orderType.find((item) => item.uuid == form.orderType).lableDesc : ''}}
									</div>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="内容">
									<!--<a href="form.contentDetail">{{form.contentDetail}}</a>-->
									<pdf ref="pdf" v-for="i in numPages" :key="i" :src="form.contentDetail" :page="i"></pdf>
									<!--<div class="exhibition-text contentDetail" v-html="form.contentDetail"></div>-->
									<div class="button_right">
										<el-button type="primary"><a :href="form.contentDetail" download="" title="下载"  target="_blank">新窗口查看</a> </el-button>
									</div>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="审核" prop="checkSts" ref="checkSts">
									<el-select clearable v-model="form.checkSts">
										<el-option v-for="(item,index) in checkSts" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row v-if="form.checkSts == 2">
							<el-col :span="12">
								<el-form-item label="驳回原因" prop="rejectDetail" ref="rejectDetail">
									<el-input clearable type="textarea" :rows="2" v-model="form.rejectDetail">
									</el-input>
								</el-form-item>
							</el-col>
						</el-row>
					</div>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click="handleCloseForm">取 消</el-button>
				<el-button type="primary" @click="handleSubmit">确 定</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex'
	import { queryContentList, checkContent, deleteContent } from "@/api/post";
	import pdf from 'vue-pdf'
	export default {
		name: 'Post',
		data() {
			return {
				loading: true,
				pagination: { //分页信息
					page: 1,
					size: 10,
					total: 0
				},
				filter: {
					orderName: '',
					orderType: '',
					mobile: '',
				},
				tableData: [],
				dialogFormVisible: false,
				form: {},
				rules: {
					checkSts: [{
						required: true,
						message: '请选择审核状态',
						trigger: 'change'
					}],
					rejectDetail: [{
						required: true,
						message: '请输入驳回原因',
						trigger: 'blur'
					}],
				},
				url: "",
				numPages: null
			}
		},
		computed: {
			...mapGetters([
				'checkSts',
				'orderType'
			])
		},
		created() {
			this.initData()
		},
		methods: {
			initData() {
				this.loading = true;
				queryContentList({
					...this.filter,
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
				}).catch((e) => {
					this.loading = false;
				})
			},
			getNumPages(pdfUrl) {
	      let loadingTask = pdf.createLoadingTask(pdfUrl)
	      loadingTask.promise.then(pdf => {
	        this.numPages = pdf.numPages
	      }).catch(err => {
	        console.error('pdf 加载失败', err);
	      })
	    },
			handleCurrentChange(page) {
				this.pagination.page = page;
				this.initData()
			},
			handleDel(row, index) {
				this.$confirm('此操作将删除该条数据, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					deleteContent(row.uuid).then((res) => {
						this.$message({
							message: '操作成功',
							type: 'success'
						});
						this.initData()
					})
				}).catch(() => {

				});
			},
			handleReview(row, index) {
				this.getNumPages(row.contentDetail)
				this.dialogFormVisible = true;
				this.$nextTick(() => {
					this.form = JSON.parse(JSON.stringify(row))
				})
			},
			handleSubmit() {
				this.$refs['form'].validate((valid, object) => {
					if(valid) {
						checkContent({
							checkSts: this.form.checkSts,
							rejectDetail: this.form.rejectDetail,
							uuid: this.form.uuid
						}).then((res) => {
							this.$message.success('提交成功');
							this.handleCloseForm();
							this.initData()
						})
					} else {
						return false
					}
				});
			},
			handleCloseForm() {
				this.dialogFormVisible = false;
				this.resetForm();
			},
			resetForm() {
				this.$refs['form'].resetFields();
			},
		},
		components: {
			pdf
		}
	}
</script>

<style lang="scss">
	.contentDetail {
		img {
			display: block!important;
			max-width: 100%!important;
		}
	}
	.button_right{
		text-align: right;
	}
</style>