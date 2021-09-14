<template>
	<div class="putawayProduct">
		<dl-title btntxt=""></dl-title>
		<div class="cont">
			<ul>
				<!-- <li>
          <van-cell is-link title="一级分类" :value="one" @click="oneType()" />
          <van-action-sheet
            v-model="show"
            :actions="actions"
            @select="onSelect"
          />
        </li>
        <li>
          <van-cell is-link title="二级分类" :value="two" @click="twoType()" />
          <van-action-sheet
            v-model="show2"
            :actions="actions2"
            @select="onSelect2"
          />
        </li> -->
				<blockquote>
					<li>
						<van-cell is-link title="请选择品牌" :value="brandVal" @click="brandClick()" />
						<cartType @brandSelect="brandSelect" :brandShow="brandShow"></cartType>
					</li>
					<li>
						<van-cell is-link title="请选择车型" :value="carVal" @click="carClick()" />
						<van-action-sheet v-model="carShow" :actions="carActions" @select="carSelect" />
					</li>
				</blockquote>
				<li>
					<van-cell is-link title="商品类型" :value="shopVal" @click="shop_type()" />
					<van-action-sheet v-model="shopShow" :actions="shopActions" @select="onSelectShop" />
				</li>
				<li v-if="$route.query && $route.query.oneId == '1002'">
					<van-cell is-link title="请填写轮胎型号" :value="tireModelVal" @click="tireModelClick()" />
					<van-popup v-model="tireModelShow" position="bottom">
					<van-picker
						 show-toolbar
            :columns="columns"
            :visible-item-count='9'
            @cancel="tireModelShow = false"
            @confirm="confirmTireModel"
          />
					</van-popup>
					<!--<van-action-sheet v-model="tireModelShow" :actions="tireModelActions" @select="tireModelSelect" />-->
				</li>
				<li>
					<div class="item">
						<div class="div_l">标题</div>
						<div class="div_r">
							<van-field v-model="title" placeholder="请填写标题" />
						</div>
					</div>
				</li>
				<!-- <li>
          <div class="item">
            <div class="div_l">物料名称</div>
            <div class="div_r">
              <van-field v-model="wuliaoName" placeholder="请填写物料名称" />
            </div>
          </div>
        </li>
        <li>
          <div class="item">
            <div class="div_l">物料价格</div>
            <div class="div_r">
              <van-field
                v-model="wuliaoPrice"
                type="number"
                placeholder="请填写价格"
              />
            </div>
            <div>(元)</div>
          </div>
        </li> -->

				<li style="height: auto">
					<div v-for="(item, index) in dynamicItem" :key="index" class="aboutwuliao">
						<pull-down v-if="$route.query && $route.query.oneId == '1006'" :title="txt + '名称'" selectType="share_station_type" @baseVal="(v) => { item.name = v }"></pull-down>
						<div class="item" v-else>
							<div class="div_l">{{ txt }}名称</div>
							<div class="div_r">
								<van-field v-model="item.name" :placeholder="`请填写${ txt }名称`" />
							</div>
						</div>
						<pull-down v-if="$route.query && $route.query.oneId == '1006'" :title="txt + '价格'" selectType="share_station_charging_money" @lableValue="(v) => { item.actAmt = v }"></pull-down>
						<div class="item" v-else>
							<div class="div_l">{{ txt }}价格</div>
							<div class="div_r">
								<van-field v-model="item.actAmt" type="number" placeholder="请填写价格" />
							</div>
							<div>(元)</div>
						</div>
						<div v-if="$route.query && $route.query.oneId == '1005'">
							<div class="item">
								<div class="div_l">店内工作年限</div>
								<div class="div_r">
									<van-field v-model="item.bak1" type="number" placeholder="请填写店内工作年限" />
								</div>
								<div>年</div>
							</div>
							<div class="item">
								<div class="div_l">累计工作年限</div>
								<div class="div_r">
									<van-field v-model="item.bak2" type="number" placeholder="请填写累计工作年限" />
								</div>
								<div>年</div>
							</div>
							<div class="item">
								<div class="div_l">是否有驾驶证</div>
								<div class="div_radio">
									<van-radio-group v-model="item.bak3" direction="horizontal">
										<van-radio name="0">是</van-radio>
										<van-radio name="1">否</van-radio>
									</van-radio-group>
								</div>
							</div>
						</div>
						<div v-if="$route.query && $route.query.oneId == '1006'">
							<div class="item">
								<van-cell is-link title="工位使用年限" :value="item.bak1" @click="durableYearsClick(index)" />
								<van-action-sheet v-model="durableYearsShow" :actions="tireModelActions" @select="durableYearsSelect" />
							</div>
							<div class="item">
								<div class="div_l">是否定期维护</div>
								<div class="div_radio">
									<van-radio-group v-model="item.bak2" direction="horizontal">
										<van-radio name="0">是</van-radio>
										<van-radio name="1">否</van-radio>
									</van-radio-group>
								</div>
							</div>
							<div class="item">
								<van-cell is-link title="最近一次维护时间" :value="item.bak3" @click="showPopup(index)" />
								<van-popup v-model="timeShow" position="bottom">
									<van-datetime-picker v-model="currentDate" :type="dateType" title="选择时间" :loading="isLoadingShow" :min-date="minDate" :max-date="maxDate" :formatter="formatter" @cancel="timeShow = false" @confirm="confirmPicker" />
								</van-popup>
							</div>
							<div class="item">
								<div class="div_l">维护方式</div>
								<!--<div class="div_r">
									<van-field v-model="item.bak4" type="number" placeholder="请填写累计工作年限" />
								</div>-->
								<div class="div_radio">
									<van-radio-group v-model="item.bak4" direction="horizontal">
									<van-radio name="店内">店内</van-radio>
									<van-radio name="第三方">第三方</van-radio>
								</van-radio-group>
								</div>
							</div>
						</div>
						<div style="width: 100%; text-aline: center">
							<van-button type="info" v-if="index !== 0" @click="deleteItem(item, index)">删除当前{{ txt }}信息</van-button>
						</div>
						<div class="baseTitle">
							<div v-if="index + 1 == dynamicItem.length" @click="addItem" class="addWuliao">
								+添加{{ txt }}
							</div>
						</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">配送方式</div>
						<div class="div_radio">
							<van-radio-group v-model="receiveMethod" direction="horizontal" @change="receiveMethodChange">
								<van-radio name="0">快递</van-radio>
								<van-radio name="1">到店服务</van-radio>
							</van-radio-group>
						</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">服务费</div>
						<div class="div_r">
							<van-field :disabled="fuwuDisabled" v-model="fuwu" type="number" placeholder="请填写价格" @focus="fuwuFocus" @blur="fuwuFocus" />
						</div>
						<div>(元)</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">平台补贴</div>
						<div class="div_r">
							<van-field disabled v-model="kuaidi" type="number" placeholder="请填写价格" />
						</div>
						<div>(元)</div>
					</div>
				</li>
				<li>
					<div class="item">
						<div class="div_l">平台服务费</div>
						<div class="div_r">
							<van-field disabled v-model="platformServiceMoney" type="number" placeholder="请填写价格"  @click="fuwuFocus"/>
						</div>
						<div>(元)</div>
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
							<div class="upload" v-for="(item,index) in videoArr" :key="index">
								<video webkit-playsinline style="width:100%;height: 2.8rem;" :src="item" controls="controls"></video>
							</div>
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
	import pullDown from "../../components/pull-down.vue";
	import cartType from "../../components/cartType.vue"
	let num1 = ['0','1','2','3','4','5','6','7','8','9']
	let num2 = ['0','1','2','3','4','5','6','7','8','9']
	let num3 = ['0','1','2','3','4','5','6','7','8','9']
	let num4 = ['0','1','2','3','4','5','6','7','8','9']
	let num5 = ['0','1','2','3','4','5','6','7','8','9']
	let num6 = ['R', 'B']
	let num7 = ['0','1','2','3','4','5','6','7','8','9']
	let num8 = ['0','1','2','3','4','5','6','7','8','9']
	let num9 = ['N','P','Q','R', 'S', 'T', 'U', 'H', 'V', 'Z', 'W', 'Y']
	export default {
		components: {
			uploadImg,
			pullDown,
			cartType
		},
		name: "putawayProduct",
		data() {
			return {
				txt: '物料',
				dynamicItem: [{
					actAmt: "",
					name: "",
				}, ],
				videoArr: [],
				receiveMethod: "0",
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
				wuliaoName: "",
				wuliaoPrice: "",
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
				flag: "1",
				platformServiceMoney: null,
				brandVal: "",
				brandShow: false,
				brandActions: [],
				carVal: "",
				carShow: false,
				carActions: [],
				timeShow: false,
				currentDate: new Date(),
				minDate: new Date(1990, 12, 30),
				maxDate: new Date(2090, 12, 31),
				isLoadingShow: true,
				dateType: "date",
				timeValue: "请选择日期",
				tireModelVal: '',
				tireModelShow: false,
				tireModelActions: [],
				tireModelId: '',
				durableYearsShow: false,
				durableYearsActions: [],
				durableYearsIndex: 0,
				timeIndex: 0,
				platformFee: 0,
				fuwuDisabled: true,
				columns: [
					{
            values:num1,
            className:'column1',
            defaultIndex: 0 //默认展示第几位，可根据需求来定
          },
          {
            values:num2,
            className:'column2',
            defaultIndex: 0
          },
          {
            values:num3,
            className:'column3',
            defaultIndex: 0
          },
          {
            values:num4,
            className:'column4',
            defaultIndex: 0
          },
          {
            values:num5,
            className:'column5',
            defaultIndex: 0
          },
          {
            values:num6,
            className:'column6',
            defaultIndex: 0
          },
          {
            values:num7,
            className:'column7',
            defaultIndex: 0
          },
          {
            values:num8,
            className:'column8',
            defaultIndex: 0
          },
          {
            values:num9,
            className:'column9',
            defaultIndex: 0
          }
				]
			};
		},
		created() {
			if(this.$route.query && this.$route.query.oneId == '1005') {
				this.txt = '技师'
				this.dynamicItem = [{
					actAmt: "",
					name: "",
					bak1: '',
					bak2: '',
					bak3: '',
				}]
			} else if(this.$route.query && this.$route.query.oneId == '1006') {
				this.txt = '共享'
				this.dynamicItem = [{
					actAmt: "",
					name: "",
					bak1: '',
					bak2: '',
					bak3: '',
					bak4: '',
				}]
			} else {
				this.txt = '物料'
				this.dynamicItem = [{
					actAmt: "",
					name: "",
				}]
			}
			// this.getDetail();//编辑
			// this.getMoney();
			this.queryStoreDetailByUser()
		},
		activated() {
			if(
				this.$route.query &&
				this.$route.query.oneId &&
				this.$route.query.twoId &&
				this.$route.query.sysSubsidy
			) {
				this.oneUuid = this.$route.query.oneId;
				this.twoUuid = this.$route.query.twoId;
				this.kuaidi = this.$route.query.sysSubsidy
			}
			this.getShop_type();
			if(this.$route.query && this.$route.query.oneId == '1005') {
				this.txt = '技师'
				this.dynamicItem = [{
					actAmt: "",
					name: "",
					bak1: '',
					bak2: '',
					bak3: '',
				}]
			} else if(this.$route.query && this.$route.query.oneId == '1006') {
				this.txt = '共享'
				this.dynamicItem = [{
					actAmt: "",
					name: "",
					bak1: '',
					bak2: '',
					bak3: '',
					bak4: '',
				}]
			} else {
				this.txt = '物料'
				this.dynamicItem = [{
					actAmt: "",
					name: "",
				}]
			}
			this.title = ''
		},
		computed: {},
		methods: {
			// 选择快递方式
			receiveMethodChange () {
				if (this.receiveMethod == '0') { // 快递
					this.fuwu = 0
					this.fuwuDisabled = true
				} else {
					this.fuwuDisabled = false
				}
				this.fuwuFocus()
			},
			platformServiceMoneyChange (value) {
				if (value > 200) {
					this.platformServiceMoney = 200
				}
			},
			queryStoreDetailByUser () {
				accountApi.queryStoreDetailByUser()
				.then(res => {
					this.platformFee = res.data.platformFee
				})
			},
			// 服务费聚焦
			fuwuFocus () {
				let isActAmt = true
				let sum = 0
				this.dynamicItem.forEach((item) => {
					if (item.actAmt == '') {
						isActAmt = false
					}
				})
				if (isActAmt === false) {
					this.$toast.fail('请先填写物料价格！')
				} else {
					this.dynamicItem.forEach((item) => {
						if (item.actAmt == '') {
							isActAmt = false
						}
						sum += Number(item.actAmt)
					})
					if (this.receiveMethod == '1') {
						if (isActAmt === true && this.fuwu !== '') {
							let totalCount = 0
							totalCount = Number(sum) + Number(this.fuwu) - this.kuaidi
							this.platformServiceMoney = totalCount * this.platformFee / 100
						}
					} else {
						let totalCount = 0
						totalCount = Number(sum) - this.kuaidi
						this.platformServiceMoney = totalCount * this.platformFee / 100
					}
				}
			},
			confirmTireModel (val) {
				this.tireModelVal = val[0] + val[1] + val[2] + '/' + val[3] + val[4] + ' ' + val[5] + ' ' + val[6] + val[7] + val[8]
				this.tireModelShow = false
			},
			// 选择最近一次维修时间
			// 显示弹窗
			showPopup(index) {
				this.timeShow = true;
				this.timeIndex = index
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
				if(this.dateType == "date") {
					this.timeValue = `${year}-${month}-${day}`;
				} else {
					this.timeValue = `${year}-${month}-${day} ${hour}:${minute}`;
				}
				this.timeShow = false;
				this.dynamicItem[this.timeIndex].bak3 = this.timeValue;
				console.log(this.dynamicItem[this.timeIndex].bak3, 'timeValue');
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
				this.vehicleModel = item.uuid;
				this.carShow = false;
			},
			// 选择品牌
			brandClick() {
				this.brandShow = true;
			},
			brandSelect(item) {
				this.brandVal = item.name;
				this.vehicleBrand = item.uuid;
				this.brandShow = false;
				this.carVal = '';
				this.vehicleModel = '';
				this.getCarType(this.vehicleBrand);
			},
			// 添加品牌选择
			addBrand() {
				this.pipaiShow = true;
			},
			getMoney() {
				managerApi.zidian('sysSubsidy').then((res) => {
					if(res.code == "0000") {
//						const resData = res.data;
//						const obj = resData.filter(x => this.$route.query && x.lableCode == this.$route.query.oneId)[0] || {}
						//this.kuaidi = obj.lableValue
					}
				});
			},
			parentFnUuid(id, v) {
				console.log(id, v, "0000000");
				this[v] = id;
			},
			addItem() {
				this.dynamicItem.push({
					actAmt: "",
					name: "",
				});
			},
			deleteItem(item, index) {
				this.dynamicItem.splice(index, 1);
				//   console.log(this.dynamicItem, "删除");
			},

			getMiaoshu(v) {
				console.log(v);
				this.videoArr = [];
				//  	debugger
				for(var i = 0; i < v.length; i++) {
					if(v[i]['imgPath'].indexOf("mp4") > -1 || v[i]['imgPath'].indexOf("mov") > -1 || v[i]['imgPath'].indexOf("avi") > -1 || v[i]['imgPath'].indexOf("wmv") > -1 || v[i]['imgPath'].indexOf("flv") > -1 || v[i]['imgPath'].indexOf("qsv") > -1) {
						this.videoArr.push(v[i]['imgPath'])
					}
				}
				console.log(this.videoArr)
				this.miaoshuImgs = v;
			},
			getZhu(v) {
				this.zhuImg = v;
			},
			getShop(v) {
				this.shopImg = v;
			},
			deleteFile(v) {
				console.log(v);
			},
			getType(flag) {
				let params = this.flag;
				accountApi.getOneType(params).then((res) => {
					if(res.code == "0000") {
						const resData = res.data;
						const newData = resData.map((x) => {
							x.name = x.groupName;
							return x;
						});
						this.actions = newData;
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
				this.show = false;
				this.one = item.name;
			},
			twoType() {
				if(this.flag != "") {
					this.show2 = true;
					let params = this.flag;
					accountApi.getOneType(params).then((res) => {
						if(res.code == "0000") {
							const resData = res.data;
							const newData = resData.map((x) => {
								x.name = x.groupName;
								return x;
							});
							this.actions2 = newData;
						}
					});
				} else {
					alert("请先选择一级分类");
				}
			},
			// 选择工位年限
			durableYearsClick (index) {
				this.durableYearsShow = true
				this.durableYearsIndex = index
			  this.getTireModel('age_limit')
			},
			// 工位年限
			durableYearsSelect (item) {
				this.dynamicItem[this.durableYearsIndex].bak1 = item.name
				// this.dynamicItem[this.durableYearsIndex].durableYearsId = item.uuid
				this.durableYearsShow = false;
			},
			// 选择轮胎型号
			tireModelClick () {
				this.tireModelShow = true;
				this.getTireModel("tire_numbers")
			},
			// 轮胎型号
			tireModelSelect(item) {
				this.tireModelId = item.uuid;
				this.tireModelShow = false;
				this.tireModelVal = item.name;
			},
			// 获工位使用年限、轮胎型号
			getTireModel(type) {
				managerApi.getShopType(type).then((res) => {
					if(res.code == "0000") {
						const shopData = res.data;
						const newShopData = shopData.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
						this.tireModelActions = newShopData;
					}
				});
			},
			onSelect2(item) {
				console.log(item.uuid);
				this.twoUuid = item.uuid;
				this.show2 = false;
				this.two = item.name;
			},
			// 获取商品分类
			getShop_type() {
				let goodsType = 'goods_type'
				if (this.twoUuid == '1001001' || this.twoUuid == '1001002' || this.twoUuid == '1001003' || this.twoUuid == '1002001' || this.twoUuid == '1002003' || this.twoUuid == '1002004') {
					goodsType = 'goods_type1'
				} else if (this.twoUuid == '1002006') {
					goodsType = 'goods_type2'
				}
				managerApi.getShopType(goodsType).then((res) => {
					if(res.code == "0000") {
						const shopData = res.data;
						const newShopData = shopData.map((x) => {
							x.name = x.lableDesc;
							return x;
						});
						this.shopActions = newShopData;
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
				let params = this.getUuid();
				accountApi.getDetail(params).then((res) => {
					if(res.code == "0000") {
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
						this.wuliaoPrice = res.data.materialsExpenses; // 材料费
						this.platformServiceMoney = res.data.platformServiceMoney; // 平台服务费
						this.vehicleModel = res.data.vehicleModel;
						this.vehicleBrand = res.data.vehicleBrand;
						this.receiveMethod = res.data.receiveMethod.toString();
						this.shopActions.forEach((e) => {
							if(e.uuid == res.data.goodsTypeUuid) {
								console.log(e.uuid);
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
					// detailList: [
					//   {
					//     actAmt: this.wuliaoPrice,
					//     name: this.wuliaoName,
					//   },
					// ],
					receiveMethod: parseInt(this.receiveMethod),
					detailList: this.dynamicItem,
					goodsDescribe: this.shopMiaoshu,
					goodsImgListReq: this.allImg,
					goodsName: this.title,
					goodsType: this.shopTypeId, // 商品类型
					manHourCost: this.fuwu,
					materialsExpenses: this.kuaidi,
					parentType: this.oneUuid, //一级分类
					sellSts: this.radio,
					storeUuid: "",
					subType: this.twoUuid, //二级分类
					surplusNum: this.kucun,
					platformServiceMoney: this.platformServiceMoney,
					vehicleModel: this.vehicleModel,
					vehicleBrand: this.vehicleBrand,
					tyreNo: this.tireModelVal
				}; 
				//   if (this.flag == "1") {
				accountApi.shangjiaSure(params).then((res) => {
					if(res.code == "0000") {
						this.$toast.success("发布成功了!");
						setTimeout(() => {
							this.finishAll();
						}, 1000);
					} else {
						this.$toast.fail(res.msg);
					}
				});
				//   } else {
				// accountApi.editShangjiaSure(params).then((res) => {
				//   if (res.data && res.data.length) {
				//     if (res.code == "0000") {
				//       this.$toast.success("发布成功了!");
				//     } else {
				//       this.$toast.fail(res.msg);
				//     }
				//   }
				// });
				//   }
			},
			// 返回
			goBack() {
				this.finishPage();
			},
		},
		watch: {}
	};
</script>

<style lang="less" scoped>
	.putawayProduct {
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
						.div_l.time{
							width: 44%;
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