<template>
	<div class="jishiRgister">
		<div class="title-wrapper">
			<van-row class="m-header">
				<van-col span="24">
					<div>
						<van-icon name="arrow-left" class="m-header-icon" @click="back" />
					</div>
					<div class="title">技师注册</div>
				</van-col>
			</van-row>
		</div>
		<!-- <dl-title btntxt=""></dl-title> -->
		<div class="gerenCont">
			<div class="cont">
				<div class="baseDiv required">
					<div class="baseDiv_l">姓名</div>
					<div class="baseDiv_r">
						<van-field v-model="name" placeholder="请输入" />
					</div>
				</div>
				<!--<div class="baseDiv required">
					<div class="baseDiv_l">技师等级</div>
					<div class="baseDiv_r">
						<van-radio-group v-model="cybAuth" direction="horizontal">
							<van-radio name="0">普通</van-radio>
							<van-radio name="1">专家</van-radio>
						</van-radio-group>
					</div>
				</div>-->
				<div class="baseDiv required">
					<div class="baseDiv_l">手机号</div>
					<div class="baseDiv_r">
						<van-field type="digit" maxlength="11" v-model="phone" placeholder="请输入" />
					</div>
				</div>
				<div class="baseDiv required">
					<div class="baseDiv_l">地区</div>
					<div class="baseDiv_r">
						<areaChoose :phdText="phdText" :addressProvince="addData.addressProvince" :addressCity="addData.addressCity" :addressCounty="addData.addressCounty" @allList="allList"></areaChoose>
					</div>
				</div>
				<div class="address">
					<div class="addTitle required">详细地址</div>
					<van-field v-model="address" autosize type="textarea" placeholder="请输入" />
				</div>
				<div class="baseDiv required" style="height: auto">
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
				<div class="baseDiv required">
					<van-cell is-link title="技术类型" :value="jishuVal" @click="jishuType()" />
					<van-action-sheet v-model="show" :actions="jsActions" @select="onSelect" />
				</div>
				<div class="baseDiv required">
					<div class="baseDiv_l">工龄</div>
					<div class="baseDiv_r">
						<van-field v-model="workingYear" type="digit" maxlength="2" placeholder="请输入" />
					</div>
				</div>
				<div class="baseDiv">
					<van-cell is-link title="国家技能鉴定" :value="guojiaVal" @click="openGuojia()" />
					<van-action-sheet v-model="guojiaShow" :actions="guojiaList" @select="guojiaSelect" />
				</div>
				<div class="loadDiv">
					<div class="loadTitle" style="height: 8px"></div>
					<div class="upload">
						<upload-img :max-count="3" :imgs="stateImgList" :imgType="1" @getAll="(v) => getStore(v, 'stateImgList')"></upload-img>
					</div>
				</div>
				<div class="baseDiv">
					<van-cell is-link title="主机厂认证" :value="zhujiVal" @click="openZhuji()" />
					<van-action-sheet v-model="zhujiShow" :actions="zhujiList" @select="zhujiSelect" />
				</div>
				<div class="loadDiv">
					<div class="loadTitle" style="height: 8px"></div>
					<div class="upload">
						<upload-img :max-count="3" :imgs="hostImgList" :imgType="1" @getAll="(v) => getStore(v, 'hostImgList')"></upload-img>
					</div>
				</div>
				<!-- <div class="baseDiv">
          <div class="baseDiv_l">问答金额</div>
          <div class="baseDiv_r">
            <van-field
              disabled
              type="number"
              v-model="answerAmt"
              placeholder="请输入"
              style="padding-right: 20px"
            />
            <span class="danwei">元</span>
          </div>
        </div> -->
				<div class="baseDiv">
					<div class="baseDiv_l">证件类型</div>
					<div class="baseDiv_r">中华人民共和国居民身份证</div>
				</div>
				<div class="baseDiv">
					<div class="baseDiv_l">证件号</div>
					<div class="baseDiv_r">
						<van-field type="digit" maxlength="18" v-model="idNumber" placeholder="请输入" />
					</div>
				</div>
				<!-- <div class="baseDiv">
          <van-field-checkbox
            label="多选checkbox"
            placeholder="请选择维修品牌"
            v-model="value2"
            :columns="columns"
            label-width="100"
            :option="{ label: 'name', value: 'code' }"
            @confirm="confirm"
          />
        </div> -->
				<div class="baseDiv">
					<div class="baseDiv_l">直系亲属手机号</div>
					<div class="baseDiv_r">
						<van-field type="digit" maxlength="11" v-model="tel" placeholder="请输入" />
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
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">驾驶证</div>
						<div class="baseDiv_r" style="color: #999999">上传驾驶证</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="driveFront" :imgType="1" @getAll="(v) => getStore(v, 'driveFront')"></upload-img>
						<upload-img :max-count="1" :imgs="driveSide" :imgType="1" @getAll="(v) => getStore(v, 'driveSide')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">身份证（正反面）</div>
						<div class="baseDiv_r" style="color: #999999">请上传</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="idFront" :imgType="1" @getAll="(v) => getStore(v, 'idFront')"></upload-img>
						<upload-img :max-count="1" :imgs="idSide" :imgType="1" @getAll="(v) => getStore(v, 'idSide')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">无犯罪记录证明</div>
						<div class="baseDiv_r">请上传</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="notGuilty" :imgType="1" @getAll="(v) => getStore(v, 'notGuilty')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">（健康检查证明）</div>
						<div class="baseDiv_r">请上传</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="health" :imgType="1" @getAll="(v) => getStore(v, 'health')"></upload-img>
					</div>
				</div>
				<div class="loadDiv">
					<div class="loadTitle">
						<div class="baseDiv_l">平台借记卡</div>
						<div class="baseDiv_r">请上传</div>
					</div>
					<div class="upload">
						<upload-img :max-count="1" :imgs="debitCard" :imgType="1" @getAll="(v) => getStore(v, 'debitCard')"></upload-img>
					</div>
				</div>
				<div style="height: 124px"></div>
				<div class="btn">
					<van-button type="info" @click="submit">下一步</van-button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import accountApi from "@/api/account";
	import managerApi from "@/api/manager";
	import areaChoose from "../../components/areaChoose.vue";
	import uploadImg from "../../components/upload-img.vue";
	import vanFieldCheckbox from "../../components/van-field-checkbox.vue";

	export default {
		name: "jishiRgister",
		components: {
			areaChoose,
			uploadImg,
			vanFieldCheckbox
		},
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
//				cybAuth:"0",
				name: "",
				phone: "",
				idNumber: "",
				answerAmt: 2,
				workingYear: "",
				jishuVal: "",
				jishuUuid: "",
				tel: "",
				driveFront: [], // 驾驶证正面
				driveSide: [], // 驾驶证反面
				idFront: [], // 身份证正面
				idSide: [], // 身份证反面
				notGuilty: [], // 无罪证明
				health: [], // 健康证明
				debitCard: [], // 平台借记卡
				receiptNum: "",
				show: false,
				jsActions: [],
				address: "",
				areaList: {}, //省市区列表
				itemCount: 7,
				showAddrPopup: false, //弹出层展示
				chooseValue: "",
				brand: "",
				pipaiShow: false,
				pipaiId: "",
				pinpai_type: [],
				Province: "",
				City: "",
				County: "",
				pinpaiList: [],
				value2: [], // checkbox选中的value
				columns: [],
				pinpais: "",
				addData: [],
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
				youUuid: "",
			};
		},
		created() {
			this.renType();
			this.guojia();
			this.zhuji();
			this.getJsType();
			this.getPinpai();
		},
		activated() {
			// if (this.youUuid && this.youUuid != "") {
			//   this.$router.push({
			//     path: "/relevancyShop",
			//     query: {
			//       urlName: this.$route.name,
			//       jsUuid: JSON.stringify(this.youUuid),
			//       urlId: (this.$route.params && this.$route.params.id) || "",
			//       flag: "1",
			//       from: "rgister",
			//     },
			//   });
			// }
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
		watch: {
			"$route.query": {
				handler(v) {
					// console.log(v, "----");
				},
				immediate: true,
			},
		},
		methods: {
			back() {
				this.finishAll();
			},
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
			// 获取品牌类型
			getPinpai() {
				accountApi.carType().then((res) => {
					if(res.code == "0000") {
						const pipaiData = res.data;
						const new_data = pipaiData.map((x) => {
							x.name = x.configName;
							return x;
						});
						const new_data1 = new_data.map((y) => {
							y.code = y.uuid;
							return y;
						});
						this.columns = new_data1;
						this.pinpai_type = new_data;
					}
				});
			},
			// 驾照正反面
			getStore(v, str) {
				const storeImgs = [];
				v.forEach((e) => {
					storeImgs.push(e.imgPath);
				});
				this[str] = storeImgs;
			},
			// 添加品牌选择
			addBrand() {
				this.pipaiShow = true;
			},
			onpipaiSelect(item) {
				this.pipaiId = item.uuid;
				this.pipaiShow = false;
				this.brand = item.name;
			},
			// 地区选择
			allList(a) {
				this.Province = a[0].uuid;
				this.City = a[1].uuid;
				this.County = a[2].uuid;
			},
			jishuType() {
				this.show = true;
			},
			onSelect(item) {
				console.log(item);
				this.show = false;
				this.jishuUuid = item.uuid;
				this.jishuVal = item.name;
			},
			// 多选
			confirm(data1, data2) {
				console.log(data1, data2);
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

			// 提交（下一步）
			submit() {
				if(this.name == "") {
					this.$toast.fail("请输入姓名");
					return;
				}
				if(this.phone == "") {
					this.$toast.fail("请输入手机号");
					return;
				}
				if(this.Province == "") {
					this.$toast.fail("请选择地区");
					return;
				}
				if(this.address == "") {
					this.$toast.fail("请输入详细地址");
					return;
				}
				if(!this.pinpais || !this.pinpais.length) {
					this.$toast.fail("请选择维修品牌");
					return;
				}
				if(this.jishuVal == "") {
					this.$toast.fail("请选择技术类型");
					return;
				}
				if(this.workingYear == "") {
					this.$toast.fail("请输入工龄");
					return;
				}
				if(
					(this.stateImgList.length < 1 || !this.guojiaUuid) &&
					(this.hostImgList.length < 1 || !this.zhujiUuid)
				) {
					this.$toast.fail("请填写国家技能鉴定或主机厂认证");
					return;
				}
				if(
					this.stateImgList.length &&
					this.guojiaUuid &&
					this.hostImgList.length &&
					!this.zhujiUuid
				) {
					this.$toast.fail("选择主机厂认证图片后必须选择主机厂认证等级");
					return;
				}
				if(
					this.stateImgList.length &&
					this.guojiaUuid &&
					!this.hostImgList.length &&
					this.zhujiUuid
				) {
					this.$toast.fail("选择主机厂认证等级必须选择主机厂认证图片");
					return;
				}
				if(
					this.stateImgList.length &&
					!this.guojiaUuid &&
					this.hostImgList.length &&
					this.zhujiUuid
				) {
					this.$toast.fail("选择国家技能鉴定图片后必须选择国家技能鉴定等级");
					return;
				}
				if(!this.stateImgList.length &&
					this.guojiaUuid &&
					this.hostImgList.length &&
					this.zhujiUuid
				) {
					this.$toast.fail("选择国家技能鉴定等级必须选择国家技能鉴定图片");
					return;
				}

				let params = {
//					cybAuth:this.cybAuth,
					
					stateImgList: this.stateImgList, //国家等级鉴定技师证书图片
					stateVerification: this.guojiaUuid, //技师等级:国家等级鉴定
					hostAuthentication: this.zhujiUuid, //技师等级:主机厂认证
					hostImgList: this.hostImgList, //主机厂认证技师证书图片

					addressCity: this.City,
					addressDetail: this.address,
					addressProvince: this.Province,
					addressCounty: this.County,
					brandUuidList: this.pinpaiList,
					certificateNum: this.idNumber,
					// answerAmt: Number(this.answerAmt),  // 字段作废  后台自己处理 
					certificateType: "706",
					checkSts: 0,
					driverLicenseBackUrl: this.driveSide[0],
					driverLicenseUrl: this.driveFront[0],
					healthCheckUrl: this.health[0],
					identityCardBackUrl: this.idSide[0],
					identityCardUrl: this.idFront[0],
					mobile: this.phone,
					noCrimeUrl: this.notGuilty[0],
					rejectDetail: "",
					relationStoreUuid: "",
					relativeMobile: this.tel,
					technicianAccount: {
						accountName: "",
						alipayAccount: this.zfbNum,
						weChatAccount: this.wxNum,
						cardNumbers: this.receiptNum,
						debitCardBackUrl: "",
						debitCardUrl: this.debitCard[0],
						depositBank: this.openBankUuid,
						subBranchName: "",
					},
					technologyType: this.jishuUuid,
					userName: this.name,
					uuid: "",
					workingYear: this.workingYear ? this.workingYear : 0,
				};
				accountApi.rgisterAdd(params).then((res) => {
					if(res.code == "0000") {
						this.$toast.success("注册成功了!");
						this.youUuid = res.data;
						this.$router.push({
							path: "/relevancyShop",
							query: {
								urlName: this.$route.name,
								jsUuid: JSON.stringify(res.data),
								urlId: (this.$route.params && this.$route.params.id) || "",
								flag: "1",
								from: "rgister",
							},
						});
					} else {
						this.$toast.fail(res.msg);
					}
				});
			},
		},
	};
</script>

<style lang="less" scoped>
	.title-wrapper {
		width: 100%;
		background: #fff;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1;
		.m-header {
			width: 100%;
			padding: 0 15px;
			height: 50px;
			line-height: 50px;
			background: #fff;
			color: #090909;
		}
		.m-header-icon {
			position: absolute;
			top: 12px;
			left: 6px;
			font-size: 26px;
		}
		.title {
			width: 100%;
			text-align: center;
			font-size: 18px;
			font-weight: 400;
			color: #090909;
		}
	}
	
	.required {
		position: relative;
		&:after {
			content: "*";
			position: absolute;
			left: -12px;
			color: red;
		}
	}
	
	.jishiRgister {
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
					position: relative;
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
					/deep/ .van-cell__value {
						span {
							color: #323233;
						}
					}
					.baseDiv_l {
						width: 35%;
						text-align: left;
						font-size: 16px;
						color: #666666;
					}
					.baseDiv_r {
						display: flex;
						align-items: center;
						justify-content: flex-end;
						position: relative;
						width: 65%;
						font-size: 16px;
						line-height: 1.2;
						color: #090909;
						text-align: right;
						.danwei {
							position: absolute;
							right: 0;
							top: 50%;
							transform: translateY(-50%);
						}
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
						justify-content: start;
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
					position: fixed;
					left: 15px;
					right: 15px;
					bottom: 0;
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