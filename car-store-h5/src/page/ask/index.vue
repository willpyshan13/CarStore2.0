<template>
	<div class="page">
		<div class="container">
			<dl-title btntxt=""></dl-title>
			<div class="content">
				<div class="form">
					<div class="form-item">
						<van-field v-model="form.consultTitle" rows="1" autosize placeholder="请输入咨询标题" />
						<van-field label="车辆类型" readonly :value="vehicleTypeName" input-align="right" placeholder="请选择" @click="handleShowAction('vehicleType')" />
						<van-field label="品牌" readonly :value="vehicleBrandName" input-align="right" placeholder="请选择" @click="handleShowAction('vehicleBrand')" />
						<van-field label="车辆车型" label-width="2.14rem" readonly :value="vehicleModelName" input-align="right" placeholder="请选择" @click="handleShowAction('vehicleModel')" />
						<van-field v-model="form.consultContent" rows="1" autosize type="textarea" placeholder="详细描述你想咨询的问题，至少输入30字" /> {{form.vehicleModelName}}
					</div>
					<div class="form-item upload">
						<!--<van-uploader class="uploader" v-model="fileList" :after-read="afterRead"/>-->
						<!--<van-uploader v-model="fileList" multiple :after-read="afterRead" class="uploader" accept="video/*,image/*"/>-->
						<upload-img :max-count="8" :imgs="fileList" :imgType="1" @getAll="(v) => getStore(v, 'fileList')"></upload-img>
					</div>
					<div class="upload" v-for="(item,index) in videoArr" :key="index">
						<video webkit-playsinline style="width:100%;height: 2.8rem;" :src="item" controls="controls"></video>
					</div>
				</div>
			</div>
			<div class="fixed-bottom">
				<div class="submit-btn" @click="handleSubmit">提问</div>
				<!--<div class="tips">提问后等待技师回答，在“我的—我的提问”查看</div>-->
				<!--<div>
						<input type="file" name="" id="" value="" />
					</div>-->
			</div>
		</div>
		<!--支付方式选择-->
		<!--<van-popup class="pay-popup" v-model="show" position="bottom" close-on-popstate close-on-click-overlay safe-area-inset-bottom>
			<pay-model @close="handleClosePay" @confirm="handleConfirmPay"></pay-model>
		</van-popup>-->
		<van-popup v-model="showAction" position="bottom">
			<van-picker show-toolbar value-key="lableDesc" :default-index="defaultIndex" :columns="actions" @cancel="closeAction" @confirm="handleConfirmPicker" />
		</van-popup>
		<van-popup v-model="vehicleBrandSelect" position="bottom">
			<van-picker show-toolbar value-key="lableDesc" :default-index="defaultIndex" :columns="pinpaiType" @cancel="closeAction" @confirm="handleConfirmPicker">
				<template slot="columns-top">
					<slot name="columns-top">
						<div class="search">
							<van-search v-model="searVal" placeholder="请输入搜索关键词" />
						</div>
					</slot>
				</template>
			</van-picker>
		</van-popup>
	</div>
</template>
<script>
	//	import payModel from "@/components/pay-model"
	import accountApi from "@/api/account";
	import uploadImg from "../../components/upload-img.vue";
	import cartType from "../../components/cartType.vue"
	import orderApi from "@/api/order";
	import dispatchAppFn from "@/mixin/forPhone";
	//	import { addConsultOrder, queryCarBrandAllList } from "@/assets/js/api"
	//	import * as types from "@/store/mutation-types";
	export default {
		components: {
			//			payModel,
			cartType,
			uploadImg
		},
		computed: {
			pinpaiType() {
				return this.vehicleBrand.filter(
					(item) =>
					item.configName.includes(this.searVal)
				);
			},
			vehicleTypeName() {
				let name = '';
				if(this.vehicleType.length && this.form.vehicleType) {
					this.vehicleType.forEach((item) => {
						if(item.uuid == this.form.vehicleType) {
							name = item.lableDesc
						}
					})
				}
				return name;
			},
			vehicleBrandName() {
				let name = '';
				if(this.vehicleBrand.length && this.form.vehicleBrand) {
					this.vehicleBrand.forEach((item) => {
						if(item.uuid == this.form.vehicleBrand) {
							name = item.lableDesc
						}
					})
				}
				return name;
			},
			vehicleModelName() {
				let name = '';
				if(this.vehicleModel.length && this.form.vehicleModel) {
					this.vehicleModel.forEach((item) => {
						if(item.uuid == this.form.vehicleModel) {
							name = item.lableDesc
						}
					})
				}
				return name;
			},
			defaultIndex() {
				let index = 0;
				if(this.pickerField && this[this.pickerField].length) {
					index = this[this.pickerField].findIndex((item) => {
						return item.uuid == this.form[this.pickerField]
					})
				}
				return index;
			}
		},
		data() {
			return {
				showAction: false,
				vehicleBrandSelect: false,
				documentTitle: '技师问答',
				show: false,
				/*******************************/
				pickerField: '',
				actions: [],
				fileList: [],
				vehicleAllList: [],
				vehicleType: [],
				vehicleBrand: [],
				vehicleModel: [],
				videoArr: [],
				/*******************************/
				form: {
					/*******************************/
					vehicleType: '',
					vehicleBrand: '',
					vehicleModel: '',
					/*******************************/
					consultTitle: '',
					consultContent: '',
					technicianUuid: '',
					consultImgList: []
				},
				price: '',
				searVal: ''
			}
		},
		created() {
			this.initParams()
		},
		async created() {
			/*上个页面跳过来传 的价格he技师ID*/
			this.price = this.$route.query.price
			// this.technicianUuid = this.$route.query.technicianUuid
			await this._queryCarBrandAllList();
		},
		methods: {
			_transferField(list, originField, currentFiled) {
				if(list.length) {
					list.forEach((item) => {
						item[currentFiled] = item[originField]
					})
				}
				return list
			},
			_queryCarBrandAllList() {
				return new Promise((resolve, reject) => {
					accountApi.queryCarBrandAllList()
						.then((res) => {
							this.vehicleAllList = this._transferField(res.data, 'configName', 'lableDesc');
							let vehicleType = this.vehicleAllList.filter((item) => {
								return item.configType == 1
							})
							this.vehicleType = vehicleType;
							resolve()
						}).catch((e) => {
							reject()
							e.msg && this.$toast(e.msg)
						})
				})
			},
			// 选择品牌
			handleShowAction(field) {
				this.carActions = [];
				this.pickerField = field;
				switch(field) {
					case 'vehicleType':
						this.actions = this.vehicleType;
						break
					case 'vehicleBrand':
						this.actions = this.vehicleBrand;
						break
					case 'vehicleModel':
						this.actions = this.vehicleModel;
						break
					case 'fuelType':
						this.actions = this.fuelType;
						break
					case 'emissionLevels':
						this.actions = this.emissionLevels;
						break
					case 'displacement':
						this.actions = this.displacement;
						break
				}
				if(field === 'vehicleBrand') {
					this.$nextTick(() => {
						this.searVal = ''
						this.vehicleBrandSelect = true;
					})
				} else {
					this.$nextTick(() => {
						this.showAction = true;
					})
				}
			},
			getStore(v, str) {
				//				debugger
				
				console.log(v);
				this.videoArr = [];
				//  	debugger
				for(var i = 0; i < v.length; i++) {
					if(v[i]['imgPath'].indexOf("mp4") > -1 || v[i]['imgPath'].indexOf("mov") > -1) {
						this.videoArr.push(v[i]['imgPath'])
					}
				}
				console.log(this.videoArr)
			
				const storeImgs = [];
				v.forEach((e) => {
					storeImgs.push(e.imgPath);
				});
				this[str] = storeImgs;
			},
			_transferField(list, originField, currentFiled) {
				if(list.length) {
					list.forEach((item) => {
						item[currentFiled] = item[originField]
					})
				}
				return list
			},
			closeAction() {
				this.showAction = false;
				this.vehicleBrandSelect = false
				this.searVal = ''
			},
			handleConfirmPicker(options) {
				switch(this.pickerField) {
					case 'vehicleType':
						this.form.vehicleBrand = '';
						this.form.vehicleModel = '';
						this.vehicleModel = [];
						this.vehicleBrand = this.vehicleAllList.filter((item) => {
							return item.parentCode == options.uuid
						});
						break
					case 'vehicleBrand':
						console.log(options)
						this.form.vehicleModel = '';
						this.vehicleModel = this.vehicleAllList.filter((item) => {
							return item.parentCode == options.uuid
						});
						break
				}
				this.form[this.pickerField] = options.uuid;
				this.closeAction()
			},
			//          initDetail(){},
			/*********************************************************/
			handleClosePay() {
				this.show = false;
			},
			handleConfirmPay(options) {
				console.log(options)
				this.$router.push({
					path: '/pay/success'
				})
			},
			handleSubmit() {
				let form = this.form;
				let consultImgList = [];
				//				console.log(form)
				if(!form.consultTitle) {
					this.$toast('请输入咨询标题');
					return false;
				}
				if(!form.vehicleType) {
					this.$toast('请选择车辆类型');
					return false;
				}
				if(!form.vehicleBrand) {
					this.$toast('请选择品牌');
					return false;
				}
				if(!form.vehicleModel) {
					this.$toast('请选择车辆车型');
					return false;
				}
				if(!form.consultContent) {
					this.$toast('请输入咨询描述');
					return false;
				}
				let fileList = this.fileList;
				this.form.consultImgList = fileList
				this.$toast.loading({
					message: '加载中...',
					forbidClick: true,
					loadingType: 'spinner',
				})
				orderApi.myQuestSend(this.form).then((res) => {
					if(res.code == "0000") {
						this.$toast.success("提交成功了!");
						this.$toast.clear()
						if(this.price == 0) {
							this.$router.push({
								path: "/myConsult"
							})
						} else {
							this.$router.push({
								path: "/payOrderJy",
								query: {
									dtcAmount: this.courseAmount,
									oid: res.data,
									caseConsult: 'caseConsult'
								}
							})
						}
					} else {
						this.$toast.fail(res.msg);
					}
				});
			},
			initParams() {
				let u = navigator.userAgent;
				let isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1; //android终端或者uc浏览器
				let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
				if(isAndroid) {
					let params = dispatchAppFn({
						fn: 'getTechnicianUuid'
					})
					params = JSON.parse(params);
					this.form['technicianUuid'] = params.technicianUuid;
				} else {
					let params = this.$route.query;
					this.form['technicianUuid'] = params.technicianUuid;
				}
			}
		},
		watch: {},
	}
</script>
<style lang="less" scoped>
	.page {
		.container {
			.van-cell {
				margin-top: 10px;
			}
		}
	}
	
	.content {
		padding: 0 0.3rem 3.12rem;
	}
	
	.form {
		padding-top: 1.28rem;
		.van-cell {
			padding-left: 0;
			padding-right: 0;
			padding-top: 0;
		}
		textarea {
			min-height: 1.68rem;
			padding: 0.2rem 0;
		}
		&::-webkit-input-placeholder {
			font-size: 0.28rem;
			font-family: PingFangSC-Regular, PingFang SC;
			font-weight: 400;
			color: #999999;
			line-height: 0.4rem;
		}
		.van-cell::after {
			left: 0;
			right: 0;
		}
	}
	
	.uploader {
		.van-uploader__upload {
			margin-right: -0.2rem;
			background: rgba(237, 245, 251, 1);
			width: 1.575rem;
			height: 1.575rem;
			margin-bottom: 0.2rem;
			.van-uploader__upload-icon {
				color: rgba(171, 192, 208, 1);
			}
		}
		.van-uploader__preview-image {
			width: 1.575rem;
			height: 1.575rem;
		}
		.van-uploader__preview {
			margin: 0 0.2rem 0.2rem 0;
			&:nth-child(4n) {
				margin-right: 0;
			}
			&:last-child {
				margin-right: 0;
			}
		}
	}
	
	.fixed-bottom {
		position: fixed;
		left: 0;
		width: 100%;
		bottom: 0;
		padding: 0 0.3rem 1.66rem;
		z-index: 1000;
		background: #ffffff;
		.submit-btn {
			height: 0.88rem;
			width: 100%;
			line-height: 0.88rem;
			background: #1691E3;
			border-radius: 0.16rem;
			text-align: center;
			font-size: 0.32rem;
			font-family: PingFangSC-Medium, PingFang SC;
			font-weight: 500;
			color: #FFFFFF;
		}
		.tips {
			font-size: 0.24rem;
			color: #999999;
			line-height: 0.34rem;
			margin-top: 0.24rem;
			text-align: center;
		}
	}
</style>