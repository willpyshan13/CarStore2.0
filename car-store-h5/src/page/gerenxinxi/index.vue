<template>
	<div class="gerenxinxi">
		<dl-title btntxt=""></dl-title>
		<div class="gerenCont">
			<div class="cont">
				<div class="baseDiv">
					<div class="baseDiv_l">审核状态</div>
					<div class="baseDiv_r baseHasBtn">
						<span :style="{ color: statusColor }">{{ status }}</span>
						<van-button v-if="addData.checkSts != 1" type="info" size="small" class="sbtn" @click="getDetail(1)">刷新</van-button>
					</div>
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">姓名</div>
					<div class="baseDiv_r">
						<van-field v-model="userName" placeholder="请输入姓名" />
					</div>
				</div>
				<!--<div class="baseDiv required">
					<div class="baseDiv_l">技师等级</div>
					<div class="baseDiv_r" style="padding-top: 15px;text-align: right;clear: both;">
						<van-radio-group v-model="cybAuth" direction="horizontal" style="float: right;">
							<van-radio name="0">普通</van-radio>
							<van-radio name="1">专家</van-radio>
						</van-radio-group>
					</div>
				</div>-->
				<div class="baseDiv">
					<div class="baseDiv_l">手机号</div>
					<van-field maxlength="11" v-model="mobile" type="tel" placeholder="请输入手机号" />
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">证件类型</div>
					<div class="baseDiv_r">中华人民共和国居民身份证</div>
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">证件号</div>
					<div class="baseDiv_r">
						<van-field v-model="certificateNum" placeholder="请输入证件号" />
					</div>
				</div>
				<div class="baseDiv">
					<van-cell is-link title="开户银行" :value="openBank" @click="open_bank()" />
					<van-action-sheet v-model="openBankShow" :actions="openBankActions" @select="openBankSelect" />
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">收款账号</div>
					<div class="baseDiv_r">
						<van-field v-model="receiptNum" type="digit" placeholder="请输入" />
					</div>
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">工龄</div>
					<div class="baseDiv_r">
						<van-field v-model="workingYear" type="digit" placeholder="请输入" maxlength="2" />
					</div>
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">支付宝账号</div>
					<div class="baseDiv_r">
						<van-field v-model="zfbNum" type="digit" placeholder="请输入" />
					</div>
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">微信账号</div>
					<div class="baseDiv_r">
						<van-field v-model="wxNum" type="digit" placeholder="请输入" />
					</div>
				</div>
				<div class="baseDiv">
					<van-cell is-link title="国家等级鉴定" :value="guojiaVal" @click="openGuojia()" />
					<van-action-sheet v-model="guojiaShow" :actions="guojiaList" @select="guojiaSelect" />
				</div>
				<div class="baseDiv">
					<van-cell is-link title="主机厂认证" :value="zhujiVal" @click="openZhuji()" />
					<van-action-sheet v-model="zhujiShow" :actions="zhujiList" @select="zhujiSelect" />
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">地区</div>
					<div class="baseDiv_r">
						<areaChoose :phdText="phdText" :addressProvince="addData.addressProvince" :addressCity="addData.addressCity" @allList="allList"></areaChoose>
					</div>
				</div>

				<div class="address">
					<div class="addTitle">详细地址</div>
					<van-field v-model="addressDetail" autosize type="textarea" placeholder="请输入" />
				</div>

				<div class="baseDiv" style="height: auto">
					<div class="baseDiv_l">维修品牌</div>
					<div class="baseDiv_r" style="
              color: #323233;
              line-height: 1.2;
              display: flex;
              align-items: center;
              padding: 5px 0;
              justify-content: flex-end;
            " @click="choice">
						<div v-if="pinpais.length == 0">请选择维修品牌</div>
						<div v-else>{{ pinpais }}</div>
					</div>
				</div>
				<div class="baseDiv">
					<van-cell is-link title="技术类型" :value="jishuVal" @click="jishuType()" />
					<van-action-sheet v-model="jsShow" :actions="jsActions" @select="jsOnSelect" />
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">直系亲属手机号</div>
					<div class="baseDiv_r">
						<van-field v-model="relativeMobile" type="tel" maxlength="11" placeholder="请输入" />
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">驾驶证</div>
						<div class="baseDiv_r" style="color: #999999">上传驾驶证</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="driverLicenseUrl" :imgType="1" @getAll="(v) => getStore(v, 'driverLicenseUrl')"></upload-img>
						<upload-img :max-count="1" :imgs="driverLicenseBackUrl" :imgType="1" @getAll="(v) => getStore(v, 'driverLicenseBackUrl')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">国家等级鉴定</div>
						<div class="baseDiv_r" style="color: #999999">上传证书</div>
					</div>
					<div class="upload">
						<upload-img :max-count="3" :imgs="stateImgList" :imgType="1" @getAll="(v) => getStore(v, 'stateImgList')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">主机厂认证</div>
						<div class="baseDiv_r" style="color: #999999">上传证书</div>
					</div>
					<div class="upload">
						<upload-img :max-count="3" :imgs="hostImgList" :imgType="1" @getAll="(v) => getStore(v, 'hostImgList')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">身份证（正反面）</div>
						<div class="baseDiv_r" style="color: #999999">上传身份证</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="identityCardUrl" :imgType="1" @getAll="(v) => getStore(v, 'identityCardUrl')"></upload-img>
						<upload-img :max-count="1" :imgs="identityCardBackUrl" :imgType="1" @getAll="(v) => getStore(v, 'identityCardBackUrl')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">无犯罪记录证明</div>
						<div class="baseDiv_r">上传无犯罪记录证明</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="noCrimeUrl" :imgType="1" @getAll="(v) => getStore(v, 'noCrimeUrl')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">（健康检查证明）</div>
						<div class="baseDiv_r">上传健康检查证明</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="healthCheckUrl" :imgType="1" @getAll="(v) => getStore(v, 'healthCheckUrl')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">平台借记卡</div>
						<div class="baseDiv_r">上传平台借记卡</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="debitCardUrl" :imgType="1" @getAll="(v) => getStore(v, 'debitCardUrl')"></upload-img>
						<upload-img :max-count="1" :imgs="debitCardBackUrl" :imgType="1" @getAll="(v) => getStore(v, 'debitCardBackUrl')"></upload-img>
					</div>
				</div>
				<div class="btn">
					<van-button type="info" @click="putSubmit()">提交</van-button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import managerApi from "@/api/manager";
	import accountApi from "@/api/account";
	import uploadImg from "../../components/upload-img.vue";
	import areaChoose from "../../components/areaChoose.vue";
	import dispatchAppFn from "@/mixin/forPhone";

	export default {
		//   components: { uploadImg },
		components: {
			uploadImg,
			areaChoose
		},
		name: "gerenxinxi",
		props: {
			itemName: {
				type: String, //按钮名称
				default: "地区",
			},
			phdText: {
				type: String, //按钮名称
				default: "请选择地区",
			},
			showUnderline: {
				type: Boolean,
				default: true,
			},
		},
		data() {
			return {
				userName: "",
				mobile: "",
				certificateNum: "",
				relativeMobile: "",
				driverLicenseUrl: [],
				driverLicenseBackUrl: [],
				identityCardUrl: [],
				identityCardBackUrl: [],
				idCardImg: [],
				noCrimeUrl: [],
				healthCheckUrl: [],
				debitCardUrl: [],
				debitCardBackUrl: [],
				addressDetail: "",
				areaList: {}, //省市区列表
				itemCount: 7,
				showAddrPopup: false, //弹出层展示
				chooseValue: "",
				cybAuth: 0,
				jishuVal: "",
				jishuUuid: "",
				jsActions: [],
				jsShow: false,
				pinpais: "",
				Province: "",
				City: "",
				County: "",
				addData: [],
				sheng: "",
				shi: "",
				qu: "",
				resUuid: "",
				workingYear: "",
				receiptNum: "",
				openBank: "",
				openBankUuid: "",
				openBankShow: false,
				openBankActions: [],
				zfbNum: "",
				wxNum: "",
				guojiaList: [],
				guojiaShow: false,
				guojiaVal: "",
				guojiaUuid: "",
				zhujiList: [],
				zhujiShow: false,
				zhujiVal: "",
				zhujiUuid: "",
				stateImgList: [],
				hostImgList: [],
				checkSts1: "",
			};
		},
		created() {
			this.renType();
			this.guojia();
			this.zhuji();
			this.getDetail();
			this.getJsType();
		},
		computed: {
			status() {
				if(this.addData.checkSts == 0) {
					return "待审核";
				} else if(this.addData.checkSts == 1) {
					return "审核通过";
				} else if(this.addData.checkSts == 2) {
					return "审核驳回";
				}
			},
			statusColor() {
				if(this.addData.checkSts == 0) {
					return "red";
				} else if(this.addData.checkSts == 1) {
					return "green";
				} else if(this.addData.checkSts == 2) {
					return "red";
				}
			},
		},
		methods: {
			// 国家等级鉴定
			guojia() {
				managerApi.zidian("state_verification").then((res) => {
					if(res.code == "0000") {
						let resDa = res.data;
						this.guojiaList = resDa.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
					}
				});
			},
			openGuojia() {
				this.guojiaShow = true;
			},
			guojiaSelect(i) {
				this.guojiaShow = false;
				this.guojiaVal = i.name;
				this.guojiaUuid = i.uuid;
			},
			//  主机厂认证:
			zhuji() {
				managerApi.zidian("host_authentication").then((res) => {
					if(res.code == "0000") {
						let resDa = res.data;
						this.zhujiList = resDa.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
					}
				});
			},
			openZhuji() {
				this.zhujiShow = true;
			},
			zhujiSelect(i) {
				this.zhujiShow = false;
				this.zhujiVal = i.name;
				this.zhujiUuid = i.uuid;
			},
			// 开户行
			renType() {
				managerApi.zidian("deposit_bank").then((res) => {
					if(res.code == "0000") {
						let resDa = res.data;
						this.openBankActions = resDa.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
					}
				});
			},
			open_bank() {
				this.openBankShow = true;
			},
			openBankSelect(item) {
				this.openBankShow = false;
				this.openBank = item.name;
				this.openBankUuid = item.uuid;
				if(this.openBank !== "中国招商银行") {
					this.$toast("平台与中国招商银行有深度合作，请优先选择中国招商银行");
				}
			},
			// 地区选择
			allList(a) {
				this.Province = a[0].uuid;
				this.City = a[1].uuid;
				this.County = a[2].uuid;
			},
			// 获取技术类型
			getJsType() {
				managerApi.zidian("technician_type").then((res) => {
					if(res.code == "0000") {
						const pipaiData = res.data;
						const new_data = pipaiData.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
						const new_data1 = new_data.map((y) => {
							y.code = y.code;
							return y;
						});
						this.jsActions = new_data1;
					}
				});
			},
			// 选择品牌
			choice() {
				const newPinpais = this.pinpais.split("/");
				console.log(this.pinpais, newPinpais);
				this.$router.push({
					path: "/weixiuBrand",
					query: {
						newPinpais: JSON.stringify(newPinpais),
						urlName: this.$route.name,
						urlId: (this.$route.params && this.$route.params.id) || "",
					},
				});
			},
			// 上传图片
			getStore(v, str) {
				const storeImgs = [];
				v.forEach((e) => {
					storeImgs.push(e.imgPath);
				});
				this[str] = storeImgs;
			},

			// 删除图片
			deleteFile(v) {
				console.log(v, "qqqqqqqqqqq");
			},

			// 技术类型
			jishuType() {
				this.jsShow = true;
			},
			jsOnSelect(item) {
				this.jsShow = false;
				this.jishuUuid = item.uuid;
				this.jishuVal = item.name;
			},

			// 获取详情
			getDetail(type) {
				accountApi.personalDetail().then((res) => {
					if(res.code == "0000") {
						console.log(res.data, "--+++++---");
						this.jishuUuid = res.data.technologyType;
						
						this.checkSts1 = res.data.checkSts;
						this.resUuid = res.data.uuid;
						this.addData = res.data;
						let resData = res.data;
						this.receiptNum = resData.technicianAccount.cardNumbers;
						this.workingYear = res.data.workingYear;
						this.userName = resData.userName;
						this.mobile = resData.mobile;
//						this.cybAuth = resData.cybAuth.toString();
						this.cybAuth = resData.cybAuth
						this.certificateNum = resData.certificateNum;
						this.Province = resData.addressProvince;
						this.City = resData.addressCity;
						this.County = resData.addressCounty;
						this.addressDetail = resData.addressDetail;
						this.relativeMobile = resData.relativeMobile;
						this.driverLicenseUrl = [resData.driverLicenseUrl];
						this.driverLicenseBackUrl = [resData.driverLicenseBackUrl];
						this.identityCardUrl = [resData.identityCardUrl];
						this.identityCardBackUrl = [resData.identityCardBackUrl];
						this.noCrimeUrl = [resData.noCrimeUrl];
						this.healthCheckUrl = [resData.healthCheckUrl];
						this.debitCardUrl = [resData.technicianAccount.debitCardUrl];
						this.debitCardBackUrl = [resData.technicianAccount.debitCardBackUrl];
						this.openBankUuid = resData.technicianAccount.depositBank;
						this.zfbNum = resData.technicianAccount.alipayAccount;
						this.wxNum = resData.technicianAccount.weChatAccount;
						this.stateImgList = resData.stateImgList; //国家等级鉴定技师证书图片 ,
						this.hostImgList = resData.hostImgList; //主机厂认证技师证书图片
						// stateVerification:技师等级://国家等级鉴定 对应字典表uuid ,
						this.guojiaUuid = resData.stateVerification;
						if(this.addData.checkSts == 1) {
							dispatchAppFn({
								fn: "setCheckSts"
							});
						}
						this.guojiaList.forEach((e) => {
							// 国家等级鉴定
							if(e.uuid == resData.stateVerification) {
								this.guojiaVal = e.lableDesc;
							}
						});
						this.zhujiUuid = resData.hostAuthentication;
						this.zhujiList.forEach((e) => {
							// 国家等级鉴定
							if(e.uuid == resData.hostAuthentication) {
								this.zhujiVal = e.lableDesc;
							}
						});
						// hostAuthentication://技师等级:主机厂认证 对应字典表uuid ,
						this.openBankActions.forEach((e) => {
							if(this.openBankUuid == e.uuid) {
								this.openBank = e.lableDesc;
							}
						});
						this.pinpais = resData.brandList
							.map((x) => {
								return x.brandName;
							})
							.join("/");
						this.pinpaiList = resData.brandList.map((x) => {
							return x.brandUuid;
						});

						managerApi.zidian("technician_type").then((res) => {
							if(res.code == "0000") {
								const pipaiData = res.data;
								const new_data = pipaiData.map((x) => {
									x.name = x.lableDesc;
									return x;
								});
								const new_data1 = new_data.map((y) => {
									y.code = y.code;
									return y;
								});
								new_data1.forEach((e) => {
									// 技师类型
									if(e.uuid == resData.technologyType) {
										this.jishuVal = e.lableDesc;
									}
								});
							}
						});
						//   this.jsActions.forEach((e) => {
						//     // 技师类型
						//     if (e.uuid == resData.technologyType) {
						//       this.jishuVal = e.lableDesc;
						//     }
						//   });
					}
				});
			},
			// 提交审核
			putSubmit() {
				if(this.receiptNum == "") {
					this.$toast.fail("请输入收款账号");
					return;
				}
				if(this.stateImgList.length < 1 && this.hostImgList.length < 1) {
					this.$toast.fail("主机厂认证与国家等级鉴定两者必须至少上传其一");
					return;
				}
				if(this.openBank !== "中国招商银行") {
					this.$toast("平台与中国招商银行有深度合作，请优先选择中国招商银行");
				}
				let par = {
					stateImgList: this.stateImgList, //国家等级鉴定技师证书图片
					stateVerification: this.guojiaUuid, //技师等级:国家等级鉴定
					hostAuthentication: this.zhujiUuid, //技师等级:主机厂认证
					hostImgList: this.hostImgList, //主机厂认证技师证书图片
					cybAuth : this.cybAuth,
					addressCity: this.City,
					addressDetail: this.addressDetail,
					addressProvince: this.Province,
					answerAmt: 2,
					answerSortWeight: 0,
					brandUuidList: this.pinpaiList,
					certificateNum: this.certificateNum,
					// certificateType: "1", //证件类型
					checkSts: this.checkSts1,
					driverLicenseBackUrl: this.driverLicenseBackUrl[0] ?
						this.driverLicenseBackUrl[0] :
						"",
					driverLicenseUrl: this.driverLicenseUrl[0] ?
						this.driverLicenseUrl[0] :
						"",
					healthCheckUrl: this.healthCheckUrl[0] ? this.healthCheckUrl[0] : "",
					identityCardBackUrl: this.identityCardBackUrl[0] ?
						this.identityCardBackUrl[0] :
						"",
					identityCardUrl: this.identityCardUrl[0] ? this.identityCardUrl[0] : "",
					mobile: this.mobile,
					noCrimeUrl: this.noCrimeUrl[0] ? this.noCrimeUrl[0] : "",
					photoImgUrl: "", //头像图片地址
					rejectDetail: "", //驳回详情
					relationStoreUuid: "", //关联店铺
					relativeMobile: this.relativeMobile,
					technicianAccount: {
						accountName: "", //开户名
						alipayAccount: this.zfbNum,
						weChatAccount: this.wxNum,
						cardNumbers: this.receiptNum, //银行卡号
						debitCardBackUrl: this.debitCardBackUrl[0] ?
							this.debitCardBackUrl[0] :
							"",
						debitCardUrl: this.debitCardUrl[0] ? this.debitCardUrl[0] : "",
						depositBank: this.openBankUuid, //开户银行
						subBranchName: "", //支行名称
					},
					technologyType: this.jishuUuid,
					userName: this.userName,
					uuid: this.resUuid,
					workingYear: this.workingYear ? this.workingYear : 0,
				};
				accountApi.personalSubmit(par).then((res) => {
					if(res.code == "0000") {
						console.log(res.data);
						this.$toast.success("提交成功了!");
						setTimeout(() => {
							this.finishAll();
						}, 1000);
					} else {
						this.$toast.fail(res.msg);
					}
				});
			},
		},
		activated() {
			// console.log(this.$route.query.addList, "选择品牌");
			if(
				this.$route.query &&
				this.$route.query.addList &&
				this.$route.query.addList.length &&
				this.$route.query.from === "clickbtn"
			) {
				this.pinpais = JSON.parse(this.$route.query.addList)
					.map((x) => {
						return x.name;
					})
					.join("/");
				this.pinpaiList = JSON.parse(this.$route.query.addList).map((x) => {
					return x.uuid;
				});
			} else if(
				this.$route.query &&
				this.$route.query.addList &&
				this.$route.query.addList.length === 0 &&
				this.$route.query.from === "clickbtn"
			) {
				this.pinpais = "";
			}
		},
	};
</script>

<style lang="less" scoped>
	.baseHasBtn {
		display: flex;
		align-items: center;
		justify-content: flex-end;
		.sbtn {
			margin-left: 6px;
			border-radius: 6px;
		}
	}
	
	.gerenxinxi {
		width: 100%;
		margin-top: 50px;
		.gerenCont {
			width: 100%;
			height: 100%;
			border-top: 1px solid #f1f1f1;
			.cont {
				width: 100%;
				padding: 0 15px;
				box-sizing: border-box;
				.baseDiv {
					height: 50px;
					line-height: 50px;
					width: 100%;
					display: flex;
					justify-content: space-between;
					border-bottom: 1px solid #f1f1f1;
					/deep/ .van-cell {
						padding: 0;
						height: 49px;
						line-height: 49px;
						&::after {
							border: none !important;
						}
						span {
							color: #666666;
						}
						.van-cell__left-icon,
						.van-cell__right-icon {
							line-height: 49px;
						}
					}
					.baseDiv_l {
						width: 35%;
						text-align: left;
						font-size: 16px;
						color: #666666;
					}
					/deep/ .van-cell {
						&::after {
							border: none !important;
						}
						.van-field__control {
							text-align: right;
						}
					}
					.baseDiv_r {
						width: 65%;
						font-size: 16px;
						color: #090909;
						text-align: right;
						&>div {
							text-align: right;
						}
						/deep/ .tx-input {
							text-align: right;
							input {
								text-align: right;
								border: none;
								height: 48px;
							}
						}
						/deep/ .van-cell {
							padding: 0;
							height: 49px;
							line-height: 49px;
							&::after {
								border: none !important;
							}
							.van-field__control {
								text-align: right;
								&::placeholder {
									color: #999999;
								}
							}
						}
					}
				}
				.loadTitle {
					height: 50px;
					line-height: 50px;
					width: 100%;
					border: none;
					display: flex;
					justify-content: space-between;
					.baseDiv_l {
						color: #666666;
					}
					.baseDiv_r {
						color: #999999;
					}
				}
				.loadDiv {
					border-bottom: 1px solid #f1f1f1;
					.upload {
						display: flex;
						/deep/ .van-uploader__upload {
							width: 116px;
							background: #edf5fb;
						}
						/deep/ .van-uploader__preview-image {
							width: 116px;
							height: 84px;
						}
					}
				}
				.btn {
					margin-top: 40px;
					width: 100%;
					/deep/ .van-button {
						width: 100%;
						line-height: 44px;
						height: 44px;
						background: #1691e3;
						border-radius: 8px;
						font-size: 16px;
						margin-bottom: 40px;
					}
				}
				.address {
					width: 100%;
					height: 122px;
					border-bottom: 1px solid #f1f1f1;
					.addTitle {
						width: 100%;
						height: 50px;
						line-height: 50px;
						font-size: 16px;
						color: #666666;
					}
					/deep/ .van-cell {
						padding: 0;
						height: 70px;
					}
				}
			}
		}
	}
</style>