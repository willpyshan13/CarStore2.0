<template>
	<div class="dtcAddOrEdit">
		<dl-title btntxt=""></dl-title>
		<div class="cont">
			<ul>
				<li>
					<div class="title">DTC代码</div>
					<div class="content">
						<van-field v-model="dtcCode" placeholder="请输入" />
					</div>
				</li>
				<li>
					<van-cell is-link title="DTC类型" :value="dtc_type" @click="typeClick()" />
					<van-action-sheet v-model="typeShow" :actions="typeActions" @select="typeSelect" />
				</li>
				<li v-if="this.flag == '2'">
					<van-radio-group v-model="dtcCheckSts" direction="horizontal">
						<van-radio name="0">待审核</van-radio>
						<van-radio name="1">审核完成</van-radio>
						<van-radio name="2">审核驳回</van-radio>
					</van-radio-group>
				</li>
				<li v-if="dtcCheckSts == '2'">
					<div class="title">驳回信息：</div>
					<div class="content">
						<rich-text :content="dtcRemarks" @ideaProcess="(v) => ideaProcess(v, 'dtcRemarks')"></rich-text>
					</div>
				</li>
				<li>
					<van-cell is-link title="维修品牌：" :value="brandVal" @click="brandClick()" />
					<cartType @brandSelect="brandSelect" :brandShow="brandShow"></cartType>
				</li>
				<li>
					<div class="title">定义：</div>
					<div class="content">
						<van-field v-model="dtcDefinition" placeholder="请输入" />
					</div>
				</li>
				<li>
					<div class="title">说明：</div>
					<div class="content">
						<rich-text :content="dtcExplain" @ideaProcess="(v) => ideaProcess(v, 'dtcExplain')"></rich-text>
					</div>
				</li>
				<li>
					<div class="title">可能原因：</div>
					<div class="content">
						<rich-text :content="dtcReasons" @ideaProcess="(v) => ideaProcess(v, 'dtcReasons')"></rich-text>
					</div>
				</li>
				<li>
					<div class="title">辅助诊断：</div>
					<div class="content">
						<rich-text :content="dtcDiagnose" @ideaProcess="(v) => ideaProcess(v, 'dtcDiagnose')"></rich-text>
					</div>
				</li>

			</ul>
			<div class="btn">
				<van-button type="info" @click="submit">提交</van-button>
			</div>
		</div>
	</div>
</template>

<script>
	import accountApi from "@/api/account";
	import managerApi from "@/api/manager";
	import orderApi from "@/api/order";
	import richText from "../../components/rich-text.vue";
	import cartType from "../../components/cartType.vue"
	export default {
		name: "dtcAddOrEdit",
		components: {
			richText,
			cartType
		},
		data() {
			return {
				dtcRemarks:"",
				dtcCheckSts:"0",
				dtcCode: "",
				dtcDefinition: "",
				pinpais: [],
				dtcExplain: "",
				dtcReasons: "",
				dtcDiagnose: "",
				dtcBrandUuid: "",
				brandVal: "",
				brandShow: false,
				brandActions: [],
				uuid: "",
				resUuid: "",
				flag: 1,
				dtc_type: "",
				dtc_type_uuid: "",
				typeShow: false,
				typeActions: []
			};
		},
		created() {
			this.getDtcType();
			if(this.getUuid() && this.getUuid() != "") {
				this.getDetail();
			}
		},
		methods: {
			// dtc类型相关
			getDtcType() {
				managerApi.zidian("dtc_type").then((res) => {
					if(res.code == "0000") {
						const resData = res.data;
						const newData = resData.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
						this.typeActions = newData;
					}
				});
			},
			typeClick() {
				this.typeShow = true;
			},
			typeSelect(item) {
				this.dtc_type = item.name;
				this.dtc_type_uuid = item.uuid;
				this.typeShow = false;
			},

			ideaProcess(v, str) {
				this[str] = v;
				console.log(this[str]);
			},
			// 选择品牌
			brandClick() {
				this.brandShow = true;
			},
			brandSelect(item) {
				this.brandVal = item.name;
				this.dtcBrandUuid = item.uuid;
				this.brandShow = false;
			},
			// 添加品牌选择
			addBrand() {
				this.pipaiShow = true;
			},
			// 获取详情
			getDetail() {
				this.flag = "2";
				// let par = "16b67622d19c49ddb33187d5669078ee";
				let par = this.getUuid();
				orderApi.dtcDetail(par).then((res) => {
					if(res.code == "0000") {
						let resData = res.data;
						this.dtcCode = resData.dtcCode ? resData.dtcCode : "";
						this.dtcDefinition = resData.dtcDefinition ?
							resData.dtcDefinition :
							"";
						this.dtcExplain = resData.dtcExplain ? resData.dtcExplain : "";
						/*************************************************************/
						this.dtcCheckSts = resData.dtcCheckSts.toString() ? resData.dtcCheckSts.toString() : "";
						this.dtcRemarks = resData.dtcRemarks ? resData.dtcRemarks : "";
						/*************************************************************/
						this.dtcReasons = resData.dtcReasons ? resData.dtcReasons : "";
						this.dtcDiagnose = resData.dtcDiagnose ? resData.dtcDiagnose : "";
						this.dtcBrandUuid = resData.dtcBrandUuid;
						this.dtc_type_uuid = resData.dtcType;
						this.typeActions.forEach((e) => {
							if(e.uuid == resData.dtcType) {
								this.dtc_type = e.name;
							}
						});
						// 品牌
						accountApi.carType().then((res) => {
							if(res.code == "0000") {
								const pipaiData = res.data;
								const new_data = pipaiData.map((x) => {
									x.name = x.configName;
									return x;
								});
								new_data.forEach((e) => {
									if(e.uuid == resData.dtcBrandUuid) {
										this.brandVal = e.name;
									}
								});
							}
						});
					} else {
						this.$toast.fail(res.msg);
					}
				});
			},
			// 提交
			submit() {
				let params = {
					dtcCheckSts : parseInt(this.dtcCheckSts),
					dtcRemarks : this.dtcRemarks,
					dtcAmount: "",
					dtcBrandUuid: this.dtcBrandUuid,
					dtcCode: this.dtcCode ? this.dtcCode : "",
					dtcDefinition: this.dtcDefinition ? this.dtcDefinition : "",
					dtcDiagnose: this.dtcDiagnose ? this.dtcDiagnose : "",
					dtcExplain: this.dtcExplain ? this.dtcExplain : "",
					dtcReasons: this.dtcReasons ? this.dtcReasons : "",
					dtcType: this.dtc_type_uuid ? this.dtc_type_uuid : "",
				};
				if(this.flag == "1") {
					orderApi.dtcAdd(params).then((res) => {
						if(res.code == "0000") {
							this.$toast.success("提交成功了!");
							this.resUuid = res.data;
							this.dtcCode = "";
							this.dtcDefinition = "";
							this.dtcDiagnose = "";
							this.dtcExplain = "";
							this.dtcReasons = "";
							this.brandVal = "";
							this.dtc_type = "";
							this.dtcRemarks = "";
							this.dtcCheckSts = "0";
							setTimeout(() => {
								this.finishAll();
							}, 1000);
						} else {
							this.$toast.fail(res.msg);
						}
					});
				} else if(this.flag == "2") {
					// let uuid = "cbd370a5e45b4ecd9a46b5ea98dfcd86";
					let uuid = this.getUuid();
					orderApi.dtcEdit(params, uuid).then((res) => {
						if(res.code == "0000") {
							this.$toast.success("提交成功了!");
							this.resUuid = res.data;
							this.dtcCode = "";
							this.dtcDefinition = "";
							this.dtcDiagnose = "";
							this.dtcExplain = "";
							this.dtcReasons = "";
							this.brandVal = "";
							this.dtc_type = "";
							this.dtcRemarks = "";
							this.dtcCheckSts = "0";
							setTimeout(() => {
								this.finishAll();
							}, 1000);
						} else {
							this.$toast.fail(res.msg);
						}
					});
				}
			},
		},
		computed: {}
	};
</script>

<style lang="less" scoped>
	.dtcAddOrEdit {
		height: 100%;
		width: 100%;
		margin-top: 50px;
		.cont {
			width: 100%;
			border-top: 1px solid #f1f1f1;
			ul {
				width: 100%;
				padding: 0 15px;
				box-sizing: border-box;
				li {
					width: 100%;
					border-bottom: 1px solid #f1f1f1;
					padding: 15px 0;
					.title {
						height: 22px;
						font-size: 16px;
						font-family: PingFangSC-Regular, PingFang SC;
						font-weight: 400;
						color: #333333;
						line-height: 22px;
						margin-bottom: 8px;
					}
					.content {
						font-size: 14px;
						font-family: PingFangSC-Regular, PingFang SC;
						font-weight: 400;
						color: #666666;
						line-height: 20px;
						/deep/ .van-cell {
							padding: 0;
						}
						.van-search__content {
							.van-cell {
								padding: 5px 8px 5px 0;
							}
						}
					}
					/deep/ .van-cell {
						padding: 0;
						color: #333333;
						&::after {
							border: none;
						}
					}
					/deep/ .van-cell__value {
						span {
							color: #333333;
						}
					}
				}
			}
			.btn {
				width: 100%;
				padding: 0 15px;
				margin-top: 20px;
				/deep/ .van-button {
					margin-bottom: 40px;
					width: 100%;
					line-height: 44px;
					height: 44px;
					background: #1691e3;
					border-radius: 8px;
					font-size: 16px;
					&::after {
						content: "";
						position: absolute;
						left: 0;
						right: 0;
						top: 42px;
						height: 42px;
						background: #fff;
					}
				}
			}
		}
	}
</style>