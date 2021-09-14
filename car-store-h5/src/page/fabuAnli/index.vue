<template>
	<div class="fabuAnli">
		<dl-title :btntxt="btntxt" @btntap="btntap"></dl-title>
		<div class="cont">
			<ul>
				<li>
					<div class="item">
						<div class="div_l">标题</div>
						<div class="div_r">
							<van-field v-model="title" placeholder="品牌+车型+故障现象" />
						</div>
					</div>
				</li>
				<li>
					<van-cell is-link title="请选择品牌" :value="brandVal" @click="brandClick()" />
					<cartType @brandSelect="brandSelect" :brandShow="brandShow"></cartType>
				</li>
				<li>
					<van-cell is-link title="请选择车型" :value="carVal" @click="carClick()" />
					<van-action-sheet v-model="carShow" :actions="carActions" @select="carSelect" />
				</li>
				<li>
					<div class="item">
						<div class="div_l">动力信息</div>
						<div class="div_r">
							<van-field v-model="powerInfo" placeholder="发动机类型+变速器类型" />
						</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">行驶里程</div>
						<div class="div_r">
							<van-field v-model="mileage" type="digit" placeholder="输入里程数" />
						</div>
						<div style="color: #090909; margin-left: 3px">Km</div>
					</div>
				</li>
				<!-- <li>
          <date-module
            :dateTitle="dateTitle"
            :dateType="dateType"
            @pitchOnDate="pitchOnDate"
          ></date-module>
        </li> -->
				<li>
					<div class="date">
						<van-cell :title="dateTitle" is-link :value-class="className" :value="timeValue" @click="showPopup" />
						<van-popup v-model="timeShow" position="bottom">
							<van-datetime-picker v-model="currentDate" :type="dateType" title="选择时间" :loading="isLoadingShow" :min-date="minDate" :max-date="maxDate" :formatter="formatter" @cancel="timeShow = false" @confirm="confirmPicker" />
						</van-popup>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">VIN</div>
						<div class="div_r">
							<van-field v-model="vin" placeholder="输入车辆车架号" />
						</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">案例价格</div>
						<div class="div_r">
							<van-field v-model="amt" maxlength="6" type="number" placeholder="请输入" />
						</div>
						<div style="color: #090909">元</div>
					</div>
				</li>
				<li class="imgTxtLi">
					<div class="item">
						<div class="div_l">案例简介:</div>
					</div>
					<div class="imgTxt" style="min-height: 134px">
						<van-field v-model="faultDesc" autosize rows="5" type="textarea" placeholder="简明扼要阐述故障现象（例：行驶中出现车身后部出现异响或冷车启动后发动机抖动仪表故障灯点亮）。" />
					</div>
				</li>
				<li>
					<van-cell is-link :value="attachSysTxt" title="所属系统" @click="xtClick()" />
					<van-action-sheet v-model="xtShow" :actions="xitongList" @select="onSelect" />
				</li>
				<!--<li class="imgTxtLi">
          <div class="item">
            <div class="div_l">诊断思路与过程：</div>
          </div>
          <div class="imgTxt">
            <van-field
              disabled
              autosize
              type="textarea"
              placeholder="详细说明维修故障的思路，分析原因，排查过程的方法，图文并茂的说明问题点及解决方案。涉及系统存在故障代码（DTC）/ 线路图/测量数据/ 波形图/NVH…等清洗体现"
            />
            <!-- <div class="upload">
              <upload-img
                :max-count="12"
                :imgs="caseImgList"
                :imgType="1"
                @getAll="(v) => getStore(v, 'caseImgList')"
              ></upload-img>
            </div> -->
				<!--<editor
              style="margin-top: 10px"
              :content="ideaProcess"
              @change="updateData"
              :height="200"
              :z-index="1000"
              :auto-height="true"
              :show-module-name="false"
            ></editor>
          </div>
        </li>-->
				<!--<li class="imgTxtLi">
          <div class="item">
            <div class="div_l">结论总结：</div>
          </div>
          <div class="imgTxt" style="min-height: 134px">
            <van-field
              v-model="summary"
              autosize
              type="textarea"
              placeholder="请输入"
            />
          </div>
        </li>-->
				<li class="imgTxtLi">
					<div class="item">
						<div class="div_l">上传pdf：</div>
						<upload-pdf :max-count="12" :imgType="3" :imgs="miaoshuPdf" @getAll="getPdf"></upload-pdf>
					</div>
				</li>
			</ul>
		</div>
		<van-overlay :show="show" @click="show = false">
			<div class="wrapper">
				<div class="block" @click.stop>
					<!--<iframe :src="pdfUrl" frameborder="0" style="width: 100%; height: 100%"></iframe>-->
					<pdf v-for="i in numPages" :key="i" :page="i" :src="pdfUrl" ref="pdf" style="width: 100%; height: auto;" @num-pages="pageCount=$event"></pdf>
				</div>
			</div>
		</van-overlay>
	</div>
</template>

<script>
	import accountApi from "@/api/account";
	import managerApi from "@/api/manager";
	import orderApi from "@/api/order";
	import utilityApi from "@/api/utility";
	import dateModule from "../../components/date-module.vue";
	import uploadImg from "../../components/upload-img.vue";
	import uploadPdf from "../../components/upload-pdf.vue";
	import VueHtml5Editor from "vue-html5-editor";
	import cartType from "../../components/cartType.vue"
	import pdf from 'vue-pdf'
	const editor = new VueHtml5Editor({
		icons: {
			text: "fa fa-pencil",
			color: "fa fa-paint-brush",
			font: "fa fa-font",
			align: "fa fa-align-justify",
			list: "fa fa-list",
			link: "fa fa-chain",
			unlink: "fa fa-chain-broken",
			tabulation: "fa fa-table",
			image: "fa fa-file-image-o",
			hr: "fa fa-minus",
			eraser: "fa fa-eraser",
			undo: "fa-undo fa",
			"full-screen": "fa fa-arrows-alt",
			info: "fa fa-info",
		},
		image: {
			// 文件最大体积，单位字节  max file size
			sizeLimit: 60000 * 1024,
			// 上传参数,默认把图片转为base64而不上传
			// upload config,default null and convert image to base64
			// upload: {
			//     url: 'http://car.api.123cgj.com/utility/file/uploadFile?type=other',
			//     headers: {},
			//     params: {},
			//     fieldName: 'file'
			// },
			// 压缩参数,默认使用localResizeIMG进行压缩,设置为null禁止压缩
			// compression config,default resize image by localResizeIMG (https://github.com/think2011/localResizeIMG)
			// set null to disable compression
			compress: {
				width: 750,
				quality: 80,
			},
			// uploadHandler(responseText){
			//   console.log(JSON.parse(responseText), 'aaaaaaaaaa');

			// var json = JSON.parse(responseText);
			// if(json.code == 0){
			//     return json.data
			// }else{
			//     alert(json.message)
			// }
			// }
		},
		language: "zh-cn",
		i18n: {
			//specify your language here
			"zh-cn": {
				align: "对齐方式",
				image: "图片",
				list: "列表",
				link: "链接",
				unlink: "去除链接",
				table: "表格",
				font: "文字",
				"full screen": "全屏",
				text: "排版",
				eraser: "格式清除",
				info: "关于",
				color: "颜色",
				"please enter a url": "请输入地址",
				"create link": "创建链接",
				bold: "加粗",
				italic: "倾斜",
				underline: "下划线",
				"strike through": "删除线",
				subscript: "上标",
				superscript: "下标",
				heading: "标题",
				"font name": "字体",
				"font size": "文字大小",
				"left justify": "左对齐",
				"center justify": "居中",
				"right justify": "右对齐",
				"ordered list": "有序列表",
				"unordered list": "无序列表",
				"fore color": "前景色",
				"background color": "背景色",
				"row count": "行数",
				"column count": "列数",
				save: "确定",
				upload: "上传",
				progress: "进度",
				unknown: "未知",
				"please wait": "请稍等",
				error: "错误",
				abort: "中断",
				reset: "重置",
			},
		},
		visibleModules: ["color", "font", "image", "align", "full-screen"],
	});

	export default {
		components: {
			dateModule,
			uploadImg,
			editor,
			cartType,
			uploadPdf,
			pdf
		},
		name: "fabuAnli",
		data() {
			return {
				timeShow: false,
				timeValue: "请选择日期",
				isLoadingShow: true,
				currentDate: new Date(),
				minDate: new Date(1990, 12, 30),
				maxDate: new Date(2090, 12, 31),
				className: "",

				btntxt: "发布",
				dateType: "date",
				dateTitle: "车辆制造年月",
				amt: "",
				title: "",
				powerInfo: "",
				mileage: "",
				vin: "",
				faultDesc: "",
				attachSys: "",
				attachSysTxt: "",
				ideaProcess: "",
				summary: "",
				caseImgList: [],
				model: "",
				madeTime: "",
				xtShow: false,
				show: false,
				xitongList: [],
				columns: [],
				pinpais: [],
				pipaiShow: false,
				pipaiId: "",
				pinpai_type: [],
				pinpaiList: [],
				brandVal: "",
				brandShow: false,
				brandActions: [],
				bianjiUuid: "", //编辑测试使用
				carVal: "",
				carShow: false,
				carActions: [],
				content: "",
				flag: "",
				uuidd: "",
				searVal: "",
				miaoshuPdf: [],
				show: false,
				pdfUrl: '',
				pageCount: 0,
				numPages: 0,
				src: '', //文件路径
			};
		},
		created() {
			this.getXitong();
			this.getEdit();
		},
		activated() {
			if(
				this.$route.query &&
				this.$route.query.addList &&
				this.$route.query.addList.length &&
				this.$route.query.from == "clickbtn"
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
			if(
				this.$route.query &&
				this.$route.query.flag &&
				this.$route.query.flag == "10"
			) {
				this.flag = this.$route.query.flag;
			}
			this.getEdit();
		},
		methods: {
			async loadPdfHandler(urlPath) {
				//src为你服务器上存放pdf的路径
				this.pdfUrl = pdf.createLoadingTask(urlPath);
				this.pdfUrl.promise.then(pdf => {
					console.log(pdf, 'pdf')
					this.numPages = pdf.numPages
				})
			},
			// 上传pdf
			getPdf(v) {
				this.pdfUrl = v[0].imgPath
				this.summary = v[0].imgPath
				this.miaoshuPdf = v;
				this.loadPdfHandler(this.pdfUrl)
				// this.show = true
			},

			updateData(v) {
				if(v.includes("data:image")) {
					const arr = v.split(`<img src="data:image/jpeg;base64,`);
					const arr1 = arr[1].split(`"`);
					const base64Img = `${arr1[0]}`;
					utilityApi
						.uploadBase64Image({
							base64Img,
							type: "other",
						})
						.then((res) => {
							if(res && res.data) {
								this.ideaProcess = `${arr[0]}<img src="${res.data}"${arr1[1]}`;
							}
						});
				} else {
					this.ideaProcess = v;
				}
			},
			// 显示弹窗
			showPopup() {
				this.timeShow = true;
				this.isLoadingShow = true;
				setTimeout(() => {
					this.isLoadingShow = false;
				}, 500);
			},
			// 确认选择的时间
			confirmPicker(val) {
				let year = val.getFullYear();
				let month = val.getMonth() + 1;
				let day = val.getDate();
				let hour = val.getHours();
				let minute = val.getMinutes();
				if(month >= 1 && month <= 9) {
					month = `0${month}`;
				}
				if(day >= 1 && day <= 9) {
					day = `0${day}`;
				}
				if(hour >= 0 && hour <= 9) {
					hour = `0${hour}`;
				}
				if(minute >= 0 && minute <= 9) {
					minute = `0${minute}`;
				}
				this.className = "timeClass";
				if(this.dateType == "date") {
					this.timeValue = `${year}-${month}-${day}`;
				} else {
					this.timeValue = `${year}-${month}-${day} ${hour}:${minute}`;
				}
				this.timeShow = false;
				this.madeTime = this.timeValue;
				console.log(this.timeValue);
			},
			// 选项格式化函数
			formatter(type, value) {
				if(type === "year") {
					return `${value}年`;
				} else if(type === "month") {
					return `${value}月`;
				} else if(type === "day") {
					return `${value}日`;
				}
				return value;
			},
			//   获取车辆类型
			getCarType(uuid) {
				accountApi.carType(uuid).then((res) => {
					if(res.code == "0000") {
						let resDa = res.data;
						//   const dataFilter = resDa.filter((x) => {
						//     return x.configType == 3;
						//   });
						const newData = resDa.map((x) => {
							x.name = x.configName;
							return x;
						});
						this.carActions = newData;
					} else {
						this.$toast.fail(res.msg);
					}
				});
			},
			carClick() {
				this.carShow = true;
			},
			carSelect(item) {
				this.carVal = item.name;
				this.model = item.uuid;
				this.carShow = false;
			},
			//   发布（新增/编辑）
			btntap() {
				if(this.title == "") {
					this.$toast.fail("请填写标题");
					return;
				}
				if(!this.amt || this.amt == "0") {
					this.$toast.fail("案例价格必须大于0");
					return;
				}
				if(this.brandUuid == "" || this.brandVal == "") {
					this.$toast.fail("请选择品牌");
					return;
				}
				if(this.model == "") {
					this.$toast.fail("请选择车型");
					return;
				}
				if(this.flag && this.flag == "10") {
					let params = {
						amt: Number(this.amt),
						attachSys: this.attachSys,
						brandUuid: this.brandUuid,
						caseImgList: this.caseImgList,
						faultDesc: this.faultDesc ? this.faultDesc : "",
						ideaProcess: this.ideaProcess ? this.ideaProcess : "",
						madeTime: this.madeTime,
						mileage: Number(this.mileage) ? Number(this.mileage) : 0,
						model: this.model,
						powerInfo: this.powerInfo ? this.powerInfo : "",
						summary: this.summary ? this.summary : "",
						title: this.title ? this.title : "",
						vin: this.vin ? this.vin : "",
						uuid: this.uuidd,
					};
					orderApi.changeFabu(params).then((res) => {
						if(res.code == "0000") {
							this.$toast.success("编辑成功了!");
							this.title = "";
							this.vin = "";
							this.summary = "";
							this.powerInfo = "";
							this.model = "";
							this.mileage = "";
							this.madeTime = "";
							this.timeValue = "请选择日期";
							this.ideaProcess = "";
							this.faultDesc = "";
							this.brandUuid = "";
							this.attachSys = "";
							this.amt = "";
							this.attachSysTxt = "";
							this.carVal = "";
							this.brandVal = "";
							// this.bianjiUuid = res.data;
							setTimeout(() => {
								this.finishAll();
							}, 1000);
						} else {
							this.$toast.fail(res.msg);
						}
					});
				} else {
					let params = {
						amt: Number(this.amt),
						attachSys: this.attachSys,
						brandUuid: this.brandUuid,
						caseImgList: this.caseImgList,
						faultDesc: this.faultDesc ? this.faultDesc : "",
						ideaProcess: this.ideaProcess ? this.ideaProcess : "",
						madeTime: this.madeTime,
						mileage: Number(this.mileage) ? Number(this.mileage) : 0,
						model: this.model,
						powerInfo: this.powerInfo ? this.powerInfo : "",
						summary: this.summary ? this.summary : "",
						title: this.title ? this.title : "",
						vin: this.vin ? this.vin : "",
					};
					orderApi.getJsFabu(params).then((res) => {
						if(res.code == "0000") {
							this.$toast.success("发布成功了!");
							this.title = "";
							this.vin = "";
							this.summary = "";
							this.powerInfo = "";
							this.model = "";
							this.mileage = "";
							this.madeTime = "";
							this.timeValue = "请选择日期";
							this.ideaProcess = "";
							this.faultDesc = "";
							this.brandUuid = "";
							this.attachSys = "";
							this.amt = "";
							this.attachSysTxt = "";
							this.carVal = "";
							this.brandVal = "";
							// this.bianjiUuid = res.data;
							setTimeout(() => {
								this.finishAll();
							}, 1000);
						} else {
							this.$toast.fail(res.msg);
						}
					});
				}
			},

			// 图片上传
			getStore(v, str) {
				const storeImgs = [];
				v.forEach((e) => {
					storeImgs.push(e.imgPath);
				});
				this[str] = storeImgs;
			},
			// 所属系统
			getXitong() {
				managerApi.zidian("attach_sys").then((res) => {
					if(res.code == "0000") {
						const resData = res.data;
						const newData = resData.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
						this.xitongList = newData;
						console.log(this.xitongList);
					}
				});
			},
			xtClick() {
				this.xtShow = true;
			},
			onSelect(item) {
				this.attachSysTxt = item.name;
				this.attachSys = item.uuid;
				this.xtShow = false;
			},
			// 选择品牌
			brandClick() {
				this.brandShow = true;
			},
			brandSelect(item) {
				this.brandVal = item.name;
				this.brandUuid = item.uuid;
				this.brandShow = false;
				this.getCarType(this.brandUuid);
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

			// 编辑获取接口
			getEdit() {
				let jsonUuid = this.getUuid();
				//   let jsonUuid = "1dbd941224ce4279b938bd6b08fb6224";
				if(jsonUuid && jsonUuid != "") {
					let params = jsonUuid;
					this.btntxt = "保存";
					orderApi.editFabu(params).then((res) => {
						if(res.code == "0000") {
							if(res.data) {
								let editData = res.data;
								this.title = editData.title;
								this.powerInfo = editData.powerInfo;
								this.faultDesc = editData.faultDesc;
								this.mileage = editData.mileage;
								this.ideaProcess = editData.ideaProcess;
								this.summary = editData.summary;
								this.vin = editData.vin;
								this.uuidd = editData.uuid;
								this.madeTime = editData.madeTime;
								this.timeValue = editData.madeTime;

								this.amt = editData.amt;
								this.caseImgList = editData.caseImgList;
								this.brandUuid = editData.brandUuid;
								this.attachSys = editData.attachSys;
								this.model = editData.model;
								console.log(this.brandActions, "0000000000");
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
										this.brandActions = new_data;
										this.brandActions.forEach((e) => {
											if(e.code == editData.brandUuid) {
												this.brandVal = e.name;
											}
										});
									}
								});

								this.xitongList.forEach((a) => {
									if(a.uuid == editData.attachSys) {
										this.attachSysTxt = a.lableDesc;
									}
								});

								accountApi.carType(editData.brandUuid).then((res) => {
									if(res.code == "0000") {
										let resDa = res.data;
										const newData = resDa.map((x) => {
											x.name = x.configName;
											return x;
										});
										this.carActions = newData;
										this.carActions.forEach((o) => {
											if(o.uuid == editData.model) {
												this.carVal = o.name;
											}
										});
									}
								});
							}
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
	.fabuAnli {
		height: 100%;
		width: 100%;
		margin-top: 50px;
		.wrapper {
			display: flex;
			align-items: center;
			justify-content: center;
			height: 100%;
		}
		.block {
			background-color: #fff;
			width: 100%;
			height: 80%;
			overflow-y: auto;
		}
		.cont {
			width: 100%;
			padding: 0 15px;
			box-sizing: border-box;
			width: 100%;
			border-radius: 8px;
			margin-bottom: 10px;
			padding-bottom: 70px;
			ul {
				width: 100%;
				li {
					height: 50px;
					line-height: 50px;
					margin-bottom: 10px;
					width: 100%;
					border-bottom: 1px solid #f1f1f1;
					.baseDiv_l {
						float: left;
						color: #666666;
					}
					.baseDiv_r {
						float: right;
						color: #666666;
						&>div {
							color: #090909;
						}
					}
					.date {
						span {
							color: #666666;
						}
					}
					/deep/ .van-cell {
						padding: 0;
						height: 48px;
						line-height: 48px;
						&::after {
							border: none;
						}
						.van-cell__title {
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
							span {
								font-size: 16px;
								color: #666666;
							}
						}
						.van-cell__value {
							color: #090909;
							span {
								text-align: right;
								overflow: hidden;
								text-overflow: ellipsis;
								white-space: nowrap;
								width: 100%;
								display: inline-block;
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
							width: 40%;
						}
						.div_r {
							width: 60%;
							/deep/ .van-cell {
								.van-field__control {
									font-size: 16px;
									color: #666666;
									text-align: right;
								}
							}
						}
					}
					.imgTxt {
						padding: 10px 15px;
						box-sizing: border-box;
						width: 100%;
						//   height: 215px;
						border: 1px solid #e6e6e6;
						/deep/ .van-cell {
							min-height: 88px;
							font-size: 16px;
							color: #999999;
							height: auto;
							line-height: 22px;
							.van-field__value {
								height: auto !important;
							}
							&::after {
								border: none;
							}
							.van-field__value {
								height: 68px;
							}
							.van-field__control::placeholder {
								font-size: 16px;
								color: #999999;
							}
						}
						.upload {
							margin-top: 25px;
						}
					}
				}
				.imgTxtLi {
					height: auto;
					padding-bottom: 20px;
					&:last-of-type {
						padding-bottom: 0;
						border: none;
					}
				}
			}
		}
	}
</style>