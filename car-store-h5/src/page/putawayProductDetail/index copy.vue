<template>
	<div class="putawayProductDetail">
		<dl-title btntxt=""></dl-title>
		<div class="cont">
			<ul>
				<li>
					<van-cell is-link title="一级分类" :value="one" @click="oneType()" />
					<van-action-sheet v-model="show" :actions="actions" @select="onSelect" />
				</li>
				<li>
					<van-cell is-link title="二级分类" :value="two" @click="twoType()" />
					<van-action-sheet v-model="show2" :actions="actions2" @select="onSelect2" />
				</li>
				<li>
					<van-cell is-link title="商品类型" :value="shopVal" @click="shop_type()" />
					<van-action-sheet v-model="shopShow" :actions="shopActions" @select="onSelectShop" />
				</li>
				<li>
					<div class="item">
						<div class="div_l">标题</div>
						<div class="div_r">
							<van-field v-model="title" placeholder="请填写标题" />
						</div>
					</div>
				</li>

				<li style="height: auto">
					<div v-for="(item, index) in dynamicItem" :key="index" class="aboutwuliao">
						<div class="item">
							<div class="div_l">物料名称</div>
							<div class="div_r">
								<van-field v-model="item.name" placeholder="请填写物料名称" />
							</div>
						</div>
						<div class="item">
							<div class="div_l">物料价格</div>
							<div class="div_r">
								<van-field v-model="item.actAmt" type="number" placeholder="请填写价格" />
							</div>
							<div>(元)</div>
						</div>
						<div style="width: 100%; text-aline: center">
							<van-button type="info" v-if="index !== 0" @click="deleteItem(item, index)">删除当前物料信息</van-button>
						</div>
						<div class="baseTitle">
							<div v-if="index + 1 == dynamicItem.length" @click="addItem" class="addWuliao">
								+添加物料
							</div>
						</div>
					</div>
				</li>

				<li>
					<div class="item">
						<div class="div_l">服务费</div>
						<div class="div_r">
							<van-field v-model="fuwu" type="number" placeholder="请填写价格" />
						</div>
						<div>(元)</div>
					</div>
				</li>

				<li>
					<div class="item">
						<div class="div_l">平台补贴</div>
						<div class="div_r">
							<van-field v-model="kuaidi" type="number" placeholder="请填写价格" />
						</div>
						<div>(元)</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">配送方式</div>
						<div class="div_radio">
							<van-radio-group v-model="receiveMethod" direction="horizontal">
								<van-radio name="0">快递</van-radio>
								<van-radio name="1">到店服务</van-radio>
							</van-radio-group>
						</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">库存量</div>
						<div class="div_r">
							<van-field v-model="kucun" type="digit" placeholder="请填写库存量" />
						</div>
						<div>(件)</div>
					</div>
				</li>
				<li class="imgTxtLi">
					<div class="item">
						<div class="div_l">商品描述</div>
					</div>
					<div class="imgTxt">
						<van-field v-model="shopMiaoshu" autosize type="textarea" placeholder="描述一下产品概况" />
						<div class="upload">
							<upload-img :max-count="12" :imgType="2" :imgs="miaoshuImgs" @getAll="getMiaoshu"></upload-img>
						</div>
					</div>
				</li>
				<li class="imgTxtLi">
					<div class="item">
						<div class="div_l">商品主图</div>
					</div>
					<div class="upload">
						<upload-img :max-count="2" :imgType="0" :imgs="zhuImg" @getAll="getZhu"></upload-img>
					</div>
				</li>
				<li class="imgTxtLi">
					<div class="item">
						<div class="div_l">商品图片</div>
					</div>
					<div class="upload">
						<upload-img :max-count="12" :imgs="shopImg" :imgType="1" @getAll="getShop"></upload-img>
					</div>
				</li>
			</ul>
			<div class="radio">
				<van-radio-group v-model="radio" direction="horizontal" @change="change()">
					<van-radio name="1" shape="square">立即上架</van-radio>
					<van-radio name="0" shape="square">放入仓库(下架)</van-radio>
				</van-radio-group>
			</div>
			<div class="btn">
				<van-button type="info" @click="sure()">确认</van-button>
				<van-button type="default" @click="goBack()">返回</van-button>
			</div>
		</div>
	</div>
</template>

<script>
	import accountApi from "@/api/account";
	import managerApi from "@/api/manager";
	import utilityApi from "@/api/utility";
	import uploadImg from "../../components/upload-img.vue";
	export default {
		components: {
			uploadImg
		},
		name: "putawayProductDetail",
		data() {
			return {
				dynamicItem: [{
					actAmt: "",
					name: "",
				}, ],
				receiveMethod:"0",
				miaoshuImgs: [],
				zhuImg: [],
				shopImg: [],
				show: false,
				show2: false,
				one: "",
				two: "",
				shopShow: false,
				shopVal: "",
				actions: [],
				actions2: [],
				shopActions: [],
				addressee: "",
				message: "",
				fileList: [],
				radio: "1",
				flag: -1,
				title: "",
				fuwu: "",
				kuaidi: "",
				kucun: "",
				shopMiaoshu: "",
				zhutu2: [],
				zhutu5: [],
				oneUuid: "",
				twoUuid: "",
				shopTypeId: "",
				uploadimg: "",
				mark: 1,
				allImg: [],
				resUuid: "",
				storeUuid: "",
			};
		},
		created() {
			this.getDetail(); //编辑
			this.getShop_type();
		},
		methods: {
			addItem() {
				this.dynamicItem.push({
					actAmt: "",
					name: "",
				});
			},
			deleteItem(item, index) {
				this.dynamicItem.splice(index, 1);
				console.log(this.dynamicItem, "删除");
			},

			getMiaoshu(v) {
				this.miaoshuImgs = v;
			},
			getZhu(v) {
				this.zhuImg = v;
			},
			getShop(v) {
				this.shopImg = v;
			},
			deleteFile(v) {
				console.log(v, "qqqqqqqqqqq");
			},
			getType(flag) {
				let params = this.flag;
				accountApi.getOneType(params).then((res) => {
					if(res.data && res.data.length) {
						if(res.code == "0000") {
							const resData = res.data;
							const newData = resData.map((x) => {
								x.name = x.groupName;
								return x;
							});
							this.actions = newData;
						}
					}
				});
			},
			oneType() {
				this.show = true;
				this.flag = -1;
				this.getType(this.flag);
			},
			onSelect(item) {
				this.flag = item.uuid;
				this.oneUuid = item.uuid;
				console.log(item, item.uuid);
				this.show = false;
				this.one = item.name;
			},
			twoType() {
				if(this.flag != "") {
					this.show2 = true;
					let params = this.flag;
					accountApi.getOneType(params).then((res) => {
						if(res.data && res.data.length) {
							if(res.code == "0000") {
								const resData = res.data;
								const newData = resData.map((x) => {
									x.name = x.groupName;
									return x;
								});
								this.actions2 = newData;
							}
						}
					});
				} else {
					alert("请先选择一级分类");
				}
			},
			onSelect2(item) {
				console.log(item.uuid);
				this.twoUuid = item.uuid;
				this.show2 = false;
				this.two = item.name;
			},
			// 获取商品分类
			getShop_type() {
				managerApi.getShopType("goods_type").then((res) => {
					if(res.data && res.data.length) {
						if(res.code == "0000") {
							const shopData = res.data;
							const newShopData = shopData.map((x) => {
								x.name = x.lableDesc;
								return x;
							});
							this.shopActions = newShopData;
						}
					}
				});
			},
			// 商品类型接口
			shop_type() {
				this.shopShow = true;
			},
			onSelectShop(item) {
				this.shopTypeId = item.uuid;
				this.shopShow = false;
				this.shopVal = item.name;
			},

			// 图片上传
			afterRead(file, type) {
				file.status = "uploading";
				file.message = "上传中...";
				let formdata = new FormData();
				formdata.append("file", file.file);
				setTimeout(() => {
					utilityApi.upload(formdata).then((res) => {
						if(res.data && res.data.length) {
							file.status = "done";
							this.$toast.success("上传成功了!");
							this.allImg.push({
								imgPath: res.data,
								imgType: type,
							});
							console.log(this.allImg, "allImg");
						}
					});
				}, 2000);
			},
			change() {
				console.log(this.radio);
			},
			// 获取详情
			getDetail() {
				//   let params = "001d6f14b258491c9438d2d492bfa85e";
				let params = this.getUuid();
				accountApi.getDetail(params).then((res) => {
					if(res.code == "0000") {
						this.dynamicItem = res.data.detailList;
						this.receiveMethod = res.data.receiveMethod.toString();
						this.resUuid = res.data.uuid;
						this.kuaidi = res.data.materialsExpenses;
						this.storeUuid = res.data.storeUuid;
						this.title = res.data.goodsName;
						this.kucun = res.data.surplusNum;
						this.shopMiaoshu = res.data.goodsDescribe;
						this.fuwu = res.data.manHourCost;
						this.radio = res.data.sellSts.toString();
						this.oneUuid = res.data.levelOneUuid; // 一级分类uuid
						this.one = res.data.levelOne; // 一级分类uuid
						this.two = res.data.levelTwo; // 二级分类uuid
						this.twoUuid = res.data.levelTwoUuid; // 二级分类uuid
						this.shopTypeId = res.data.goodsTypeUuid; // 商品分类uuid
						this.shopActions.forEach((e) => {
							if(e.uuid == res.data.goodsTypeUuid) {
								//   console.log(e.uuid);
								this.shopVal = e.lableDesc;
							}
						});
						const imgs = res.data.imgList || [];
						this.miaoshuImgs = imgs
							.filter((x) => x.imgType === 2)
							.map((x) => x.imgPath);
						this.zhuImg = imgs
							.filter((x) => x.imgType === 0)
							.map((x) => x.imgPath);
						this.shopImg = imgs
							.filter((x) => x.imgType === 1)
							.map((x) => x.imgPath);
					}
				});
			},
			// 确定
			sure() {
				this.allImg = this.miaoshuImgs.concat(this.zhuImg).concat(this.shopImg);
				console.log(this.allImg);
				let params = {
					goodsDetailReqList: this.dynamicItem,
					receiveMethod: this.receiveMethod,
					goodsDescribe: this.shopMiaoshu,
					goodsImgListReq: this.allImg,
					goodsName: this.title,
					goodsType: this.shopTypeId, // 商品类型
					manHourCost: this.fuwu,
					materialsExpenses: this.kuaidi,
					parentType: this.oneUuid, //一级分类
					sellSts: this.radio,
					storeUuid: this.storeUuid,
					// subType: this.twoUuid, //二级分类
					surplusNum: this.kucun,
					uuid: this.resUuid,
				};
//				debugger;
				accountApi.editShangjiaSure(params).then((res) => {
					if(res.code == "0000") {
						this.$toast.success("发布成功了!");
						setTimeout(() => {
							this.finishAll();
						}, 1000);
					} else {
						this.$toast.fail(res.msg);
					}
				});
			},
			// 返回
			goBack() {
				this.finishPage();
			},
		},
	};
</script>

<style lang="less" scoped>
	.putawayProductDetail {
		height: 100%;
		width: 100%;
		margin-top: 50px;
		.cont {
			width: 100%;
			padding: 0 15px;
			box-sizing: border-box;
			width: 100%;
			border-radius: 8px;
			margin-bottom: 10px;
			ul {
				width: 100%;
				li {
					height: 50px;
					line-height: 50px;
					margin-bottom: 10px;
					width: 100%;
					border-bottom: 1px solid #f1f1f1;
					.aboutwuliao {
						/deep/ .van-button {
							width: 150px;
							text-align: center;
							height: 32px;
							line-height: 32px;
							background: #f7f7f7;
							padding: 0;
							color: #212121;
							border: 0;
						}
						&:last-of-type {
							.baseTitle {
								width: 100%;
								margin: 13px 0;
							}
						}
						.addWuliao {
							color: #212121;
							margin: 0 auto;
							width: 245px;
							text-align: center;
							height: 32px;
							line-height: 32px;
							background: #f7f7f7;
						}
						.item {
							border-bottom: 1px solid #f1f1f1;
						}
					}
					/deep/ .van-cell__value {
						span {
							color: #090909;
						}
					}
					/deep/ .van-cell {
						padding: 0;
						height: 49px;
						line-height: 49px;
						&::after {
							border: none;
						}
						.van-cell__title {
							span {
								font-size: 16px;
								color: #666666;
							}
						}
						.van-cell__left-icon,
						.van-cell__right-icon {
							height: 50px;
							line-height: 50px;
							color: #666666;
						}
					}
					.item {
						width: 100%;
						display: flex;
						justify-content: space-between;
						&>div {
							font-size: 16px;
							color: #666666;
						}
						.div_l {
							width: 30%;
						}
						.div_radio {
							padding-top: 13px;
						}
						.div_r {
							width: 70%;
							/deep/ .van-cell {
								.van-field__control {
									font-size: 16px;
									color: #666666;
									text-align: right;
								}
							}
							/deep/ .van-field__body {
								input {
									color: #090909 !important;
								}
							}
						}
					}
					.imgTxt {
						padding: 10px 15px;
						box-sizing: border-box;
						width: 100%;
						border: 1px solid #e6e6e6;
						/deep/ .van-cell {
							font-size: 16px;
							color: #999999;
							height: auto !important;
							line-height: 16px !important;
							&::after {
								border: none;
							}
							// .van-field__value {
							//   height: 68px;
							// }
							.van-field__control::placeholder {
								font-size: 16px;
								color: #999999;
							}
						}
					}
				}
				.imgTxtLi {
					height: auto;
					padding-bottom: 20px;
					.upload {
						margin-top: 10px;
						/deep/ .van-uploader__wrapper {
							.van-uploader__preview {
								margin: 0 6px 8px 0;
							}
						}
					}
					&:last-of-type {
						padding-bottom: 0;
						border: none;
					}
				}
			}
			.radio {
				width: 100%;
				height: 18px;
				margin: 33px 0 40px 0;
				/deep/ .van-radio--horizontal {
					font-size: 16px;
					color: #666666;
				}
				/deep/ .van-radio-group--horizontal {
					display: flex;
					justify-content: center;
					.van-radio {
						&:first-of-type {
							margin-right: 5%;
						}
						&:last-of-type {
							margin-left: 5%;
						}
					}
				}
			}
			.btn {
				width: 100%;
				height: 60px;
				display: flex;
				justify-content: space-between;
				/deep/ .van-button {
					width: 48%;
					height: 44px;
				}
			}
		}
	}
</style>