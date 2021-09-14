<template>
	<div class="app-container">
		<div class="page-detail">
			<el-form v-loading="loading" label-position="right" :model="form" :rules="rules" ref="form" label-width="120px">
				<div class="section">
					<div class="title">
						{{uuid ? '编辑商品' : '新增商品'}}
					</div>
					<div class="content">
						<el-row>
							<el-col :span="12">
								<el-form-item label="商品类目" prop="parentType" ref="parentType">
									<el-cascader clearable v-model="form.levelTwoUuid" :options="goodsParent" :props="{label:'groupName',value:'uuid'}" :show-all-levels="true"></el-cascader>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="商品名称" prop="goodsName" ref="goodsName">
									<el-input clearable v-model="form.goodsName"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="商品类型" prop="goodsType" ref="goodsType">
									<el-select clearable v-model="form.goodsType">
										<el-option v-for="(item,index) in goodsType" :key="item.uuid" :label="item.lableDesc" :value="item.uuid"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>

						<!--物料管理-->
						<template v-if="form.detailList && form.detailList.length">
							<el-row v-for="(item,index) in form.detailList" :key="index">
								<el-col :span="12">
									<el-form-item label="物料名称">
										<el-input clearable v-model="item.name"></el-input>
									</el-form-item>
								</el-col>
								<el-col :span="12">
									<el-form-item label="物料价格">
										<el-input-number :controls="false" v-model="item.actAmt" :precision="2"></el-input-number>
										<span class="warning-text" style="padding-left:10px;">元</span>
									</el-form-item>
								</el-col>
							</el-row>
						</template>
						<el-row>
							<el-col :span="12">
								<el-form-item label="材料费" prop="materialsExpenses" ref="materialsExpenses">
									<el-input-number :controls="false" v-model="form.materialsExpenses" :precision="2"></el-input-number>
									<span class="warning-text" style="padding-left:10px;">元</span>
								</el-form-item>
							</el-col>
							<el-col :span="12">
								<el-form-item label="服务费" prop="manHourCost" ref="manHourCost">
									<el-input-number :controls="false" v-model="form.manHourCost" :precision="2"></el-input-number>
									<span class="warning-text" style="padding-left:10px;">元</span>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="12">
								<el-form-item label="库存量" prop="surplusNum" ref="surplusNum">
									<el-input-number :controls="false" v-model="form.surplusNum"></el-input-number>
									<span class="warning-text" style="padding-left:10px;">件</span>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<!--{{form.mainImg}}-->
							<el-col :span="24">
								<el-form-item label="商品主图" prop="mainImg" ref="mainImg">
									<upload-pic :limit="1" :file-list="form.mainImg"></upload-pic>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="商品图片">
									<upload-pic :file-list="form.otherImg"></upload-pic>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="商品描述" prop="goodsDescribe" ref="goodsDescribe">
									<!--                                    <editor :quill-index="0"  :placeholder="'请输入商品描述'" :content="form.goodsDescribe"  @change="editorChange($event)"></editor>-->
									<el-input rows="5" type="textarea" v-model="form.goodsDescribe" :placeholder="'请输入商品描述'"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row>
							<el-col :span="24">
								<el-form-item label="描述图片">
									<upload-pic @descImg="descImg" :file-list="form.descImg"></upload-pic>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row v-if="videoArr.length > 0">

							<el-col :span="24">
								<el-form-item label="视频">
									<!--{{videoArr}}-->
									<div class="videoArr" v-for="(item,index) in videoArr">
										<video webkit-playsinline style="width:100%;height: 100%;" :src="item" controls="controls"></video>
									</div>
								</el-form-item>
							</el-col>
						</el-row>

						<el-row>
							<el-col :span="12">
								<el-form-item label="上架时间" prop="sellSts" ref="sellSts">
									<el-radio v-model="form.sellSts" :label="1">立刻上架</el-radio>
									<el-radio v-model="form.sellSts" :label="0">放入仓库</el-radio>
								</el-form-item>
							</el-col>
						</el-row>
					</div>
				</div>
				<div class="section section-btn-list">
					<div class="content section-btn-content">
						<el-button class="save-btn" size="lage" type="primary" @click="handleSubmit">保存</el-button>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import { mapGetters } from 'vuex'
	import UploadPic from "@/components/UploadPic"
	import Editor from "@/components/Editor/Editor";
	import { queryGoodsDetail, updateGoods, goodsParentQueryList, addGoods } from "@/api/product";
	export default {
		name: 'ProductDetail',
		components: {
			'upload-pic': UploadPic,
			'editor': Editor
		},
		data() {
			return {
				loading: false,
				uuid: '',
				storeType: [],
				goodsType: [],
				goodsParent: [],
				videoArr: [],
				form: {
					mainImg: [], // 商品主图
					otherImg: [], // 商品其它图片
					descImg: [], // 描述图片
					detailList: [], //物料信息
				},
				rules: {
					levelTwoUuid: [{
						required: true,
						message: '请选择商品类目',
						trigger: 'change'
					}],
					goodsName: [{
						required: true,
						message: '请输入商品名称',
						trigger: 'blur'
					}],
					goodsType: [{
						required: true,
						message: '请选择商品类型',
						trigger: 'change'
					}],
					materialsExpenses: [{
						required: true,
						message: '请输入材料费',
						trigger: 'blur'
					}],
					manHourCost: [{
						required: true,
						message: '请输入服务费',
						trigger: 'blur'
					}],
					surplusNum: [{
						required: true,
						message: '请输入库存量',
						trigger: 'blur'
					}],
					mainImg: [{
						required: true,
						message: '请上传商品主图',
						trigger: 'change'
					}],
					goodsDescribe: [{
						required: true,
						message: '请输入商品描述',
						trigger: 'blur'
					}],
					sellSts: [{
						required: true,
						message: '请选择上架时间',
						trigger: 'change'
					}],
				}
			}
		},
		async created() {
			this.uuid = this.$route.query.id;
			await this._goodsParentQueryList();
			await this.initDict();
			if(this.uuid) {
				this.initData()
			}
		},
		methods: {
			descImg(e) {
//				debugger
//				console.log(e)
				this.videoArr = [];
				for(var i = 0; i < e.length; i++) {
					if(e[i]['url'].indexOf('mp4') > -1 || e[i]['url'].indexOf('mov') > -1 || e[i]['url'].indexOf('flv') > -1) {
						this.videoArr.push(e[i]['url'])
					}
				}
			},
			evDesc(e) {
				console.log(e)
			},
			async initDict() {
				this.storeType = await this.$store.dispatch('dict/getDict', 'store_type');
				this.goodsType = await this.$store.dispatch('dict/getDict', 'goods_type');
			},
			async _goodsParentQueryList() {
				let {
					data
				} = await goodsParentQueryList();
				let root = data.filter(item => item.parentId == -1);

				function combinedData(list) {
					if(list.length) {
						list.forEach((item) => {
							let children = data.filter(sub => sub.parentId == item.uuid);
							if(children.length) {
								item.children = children;
								combinedData(item.children)
							}
						})
					}
				}
				combinedData(root)
				this.goodsParent = root;
			},
			initData() {
				this.loading = true;
				queryGoodsDetail(this.uuid).then((res) => {
					let {
						data
					} = res;
					let goodsImgListRes = data.imgList.map((item) => {
						return {
							url: item.imgPath,
							imgType: item.imgType
						}
					})
					let mainImg = goodsImgListRes.filter((item) => item.imgType == 0);
					let otherImg = goodsImgListRes.filter((item) => item.imgType == 1);
					let descImg = goodsImgListRes.filter((item) => item.imgType == 2);
					for(var i = 0; i < descImg.length; i++) {
						//						debugger
						if(descImg[i]['url'].indexOf('mp4') > -1 || descImg[i]['url'].indexOf('mov') > -1 || descImg[i]['url'].indexOf('flv') > -1) {
							this.videoArr.push(descImg[i]['url'])
						}
					}

					data.mainImg = mainImg;
					data.otherImg = otherImg;
					data.descImg = descImg;
					this.form = data;
					this.loading = false;
				}).catch((e) => {
					this.loading = false;
				})
			},
			editorChange(event) {
				this.form['goodsDescribe'] = event
			},
			handleSubmit() {
				this.$refs['form'].validate((valid, object) => {
					if(valid) {
						let goodsImgListRes = [];
						if(this.form.mainImg.length) {
							let mainImg = this.form.mainImg.map((item) => {
								return {
									imgPath: item.url,
									imgType: 0,
								}
							})
							goodsImgListRes = goodsImgListRes.concat(mainImg)
						}

						if(this.form.otherImg.length) {
							let otherImg = this.form.otherImg.map((item) => {
								return {
									imgPath: item.url,
									imgType: 1,
								}
							})
							goodsImgListRes = goodsImgListRes.concat(otherImg)
						}
						if(this.form.descImg.length) {
							let descImg = this.form.descImg.map((item) => {
								return {
									imgPath: item.url,
									imgType: 2,
								}
							})
							goodsImgListRes = goodsImgListRes.concat(descImg)
						}
						let levelOneUuid = this.form.levelTwoUuid[0];
						let levelTwoUuid = this.form.levelTwoUuid[this.form.levelTwoUuid.length - 1];
						if(this.uuid) {
							console.log(this.form)
							updateGoods({
								...this.form,
								goodsImgListReq: goodsImgListRes,
								levelOneUuid,
								levelTwoUuid,
								goodsDetailReqList: this.form.detailList
							}).then((res) => {
								this.$message.success('提交成功');
								this.$router.go(-1)
							})
						} else {
							addGoods({
								...this.form,
								goodsImgListRes,
								levelOneUuid,
								levelTwoUuid,
								goodsDetailReqList: this.form.detailList
							}).then((res) => {
								this.$message.success('提交成功');
								this.$router.go(-1)
							})
						}
					} else {
						let split = ''
						for(let i in object) {
							let dom = this.$refs[i]
							if(Object.prototype.toString.call(dom) !== '[object Object]') {
								dom = dom[0]
								split = dom.prop
								let index = split.indexOf('.')
								let last = split.lastIndexOf('.')
								this.activeName = Number(split.slice(index + 1, last))
								break
							}
							dom.$el.scrollIntoView({
								block: 'center', //值有start,center,end，nearest，当前显示在视图区域中间
								behavior: 'smooth' //值有auto、instant,smooth，缓动动画（当前是慢速的）
							})
						}
						return false
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.videoArr {
		width: 70%;
		height: 300px;
		margin-bottom: 20px;
		video {
			width: 100%;
			height: 100%;
		}
	}
</style>