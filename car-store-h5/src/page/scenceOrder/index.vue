<template>
  <div class="scenceOrder">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <van-cell
            is-link
            title="请选择品牌"
            :value="brandVal"
            @click="brandClick()"
          />
          <cartType @brandSelect="brandSelect" :brandShow="brandShow"></cartType>
        </li>
        <li>
          <van-cell
            is-link
            title="请选择车型"
            :value="carVal"
            @click="carClick()"
          />
          <van-action-sheet
            v-model="carShow"
            :actions="carActions"
            @select="carSelect"
          />
        </li>
        <li>
          <van-cell
            is-link
            title="年款"
            :value="yearVal"
            @click="yearClick()"
          />
          <van-action-sheet
            v-model="yearShow"
            :actions="arrYear"
            @select="yearSelect"
          />
        </li>

        <li>
          <div class="item">
            <div class="div_l">VIN</div>
            <div class="div_r">
              <van-field v-model="vinCode" placeholder="请输入VIN" />
            </div>
          </div>
        </li>

        <li>
          <van-cell
            is-link
            title="变速器"
            :value="bsqVal"
            @click="bsqClick()"
          />
          <van-action-sheet
            v-model="bsqShow"
            :actions="bsqActions"
            @select="bsqSelect"
          />
        </li>
        <li>
          <van-cell is-link title="" :value="bsqVal1" @click="bsqClick1()" />
          <van-action-sheet
            v-model="bsqShow1"
            :actions="bsqActions1"
            @select="bsqSelect1"
          />
        </li>

        <li>
          <pull-down
            :title="subtitle1"
            :selectType="selectType1"
            @baseData="parentFn"
            :uuid11="uuid1"
            @baseUuid="(v) => parentFnUuid(v, 'uuid1')"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle2"
            :selectType="selectType2"
            @baseData="parentFn"
            :uuid11="uuid2"
            @baseUuid="(v) => parentFnUuid(v, 'uuid2')"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle3"
            :selectType="selectType3"
            @baseData="parentFn"
            :uuid11="uuid3"
            @baseUuid="(v) => parentFnUuid(v, 'uuid3')"
          ></pull-down>
        </li>

        <li>
          <van-cell
            is-link
            title="保修状态"
            :value="warrantyVal"
            @click="warrantyClick()"
          />
          <van-action-sheet
            v-model="warrantyShow"
            :actions="warrantySts"
            @select="warrantySelect"
          />
        </li>
        <li class="imgTxtLi">
          <div class="item">
            <div class="div_l">其他状态:</div>
          </div>
          <div class="imgTxt" style="height: 134px">
            <van-field
              v-model="otherSts"
              autosize
              rows="5"
              type="textarea"
              placeholder="例如：该问题是进店前就存在还是检修或事故产生的新故障"
            />
          </div>
        </li>
        <li class="imgTxtLi">
          <div class="item">
            <div class="div_l">故障描述:</div>
          </div>
          <div class="imgTxt" style="height: 134px">
            <van-field
              v-model="faultDesc"
              autosize
              rows="5"
              type="textarea"
              placeholder="简明扼要阐述故障现象（例：行驶中出现车身后部出现异响或冷车启动后发动机抖动仪表故障灯点亮）。"
            />
          </div>
        </li>

        <li>
          <pull-down
            :title="subtitle4"
            :selectType="selectType4"
            @baseData="parentFn"
            :uuid11="uuid4"
            @baseUuid="(v) => parentFnUuid(v, 'uuid4')"
          ></pull-down>
        </li>

        <li class="imgTxtLi">
          <div class="item">
            <div class="div_l">已检过程:</div>
          </div>
          <div class="imgTxt" style="height: 134px">
            <van-field
              v-model="alreadyInspect"
              autosize
              rows="5"
              type="textarea"
              placeholder="请输入"
            />
          </div>
        </li>
        <li class="imgTxtLi">
          <div class="item">
            <div class="div_l" style="width: 45%">是否有DTC故障代码:</div>
          </div>
          <div class="imgTxt" style="height: 134px">
            <van-field
              v-model="dtcCode"
              autosize
              rows="5"
              type="textarea"
              placeholder="可传多个故障代码，以逗号（，）隔开"
            />
          </div>
        </li>
        <li class="imgTxtLi">
          <div class="item">
            <div class="div_l">DTC图片：</div>
          </div>
          <div class="upload">
            <upload-img
              :max-count="8"
              :imgs="dtcImgList"
              :imgType="1"
              @getAll="(v) => getStore(v, 'dtcImgList')"
            ></upload-img>
          </div>
        </li>
        <li>
          <pull-down
            :title="subtitle8"
            :selectType="selectType8"
            @baseData="parentFn"
            :uuid11="uuid8"
            @baseUuid="(v) => parentFnUuid(v, 'uuid8')"
            :val11="val8"
            @baseVal="(v) => parentVal(v, 'val8')"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle9"
            :selectType="selectType9"
            @baseData="parentFn"
            :uuid11="uuid9"
            @baseUuid="(v) => parentFnUuid(v, 'uuid9')"
            :val11="val9"
            @baseVal="(v) => parentVal(v, 'val9')"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle5"
            :selectType="selectType5"
            @baseData="parentFn"
            :uuid11="uuid5"
            @baseUuid="(v) => parentFnUuid(v, 'uuid5')"
            :val11="val5"
            @baseVal="(v) => parentVal(v, 'val5')"
            @selectClick="selectClick"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle6"
            :selectType="selectType6"
            @baseData="parentFn"
            :uuid11="uuid6"
            @baseUuid="(v) => parentFnUuid(v, 'uuid6')"
            :val11="val6"
            @baseVal="(v) => parentVal(v, 'val6')"
            @selectClick="selectClick"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle7"
            :selectType="selectType7"
            @baseData="parentFn"
            :uuid11="uuid7"
            @baseUuid="(v) => parentFnUuid(v, 'uuid7')"
            :val11="val7"
            @baseVal="(v) => parentVal(v, 'val7')"
            @selectClick="selectClick"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle10"
            :selectType="selectType10"
            @baseData="parentFn"
            :uuid11="uuid10"
            @baseUuid="(v) => parentFnUuid(v, 'uuid10')"
            :val10="val10"
            @baseVal="(v) => parentVal(v, 'val10')"
          ></pull-down>
        </li>
        <li>
          <pull-down
            :title="subtitle11"
            :selectType="selectType11"
            :uuid11="uuid11"
            @baseData="parentFn"
            @baseUuid="(v) => parentFnUuid(v, 'uuid11')"
            :val11="val11"
            @baseVal="(v) => parentVal(v, 'val11')"
          ></pull-down>
        </li>

        <li>
          <div class="feiyong">
            <div class="div_l">总支付费用：</div>
            <div class="div_r">¥{{ totalAmount }}元</div>
          </div>
        </li>
      </ul>
      <div class="btn">
        <van-button type="info" @click="submit">提交</van-button>
      </div>
      <div class="tips">下单后平台订单服务费将不予以退回</div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import managerApi from "@/api/manager";
import orderApi from "@/api/order";
import uploadImg from "../../components/upload-img.vue";
import pullDown from "../../components/pull-down.vue";
import cartType from "../../components/cartType.vue"

export default {
  components: { uploadImg, pullDown, cartType },
  name: "scenceOrder",
  data() {
    return {
      subtitle: "变速器",
      selectType: "transmission_type",
      uuid0: "",
      subtitle1: "发动机排量",
      selectType1: "engine_displacement",
      uuid1: "",
      subtitle2: "驱动方式",
      selectType2: "driving_mode",
      uuid2: "",
      subtitle3: "增压系统",
      selectType3: "supercharging_system",
      uuid3: "",
      subtitle4: "维修类型",
      selectType4: "goods_type",
      uuid4: "",
      subtitle5: "基本检查费",
      selectType5: "basic_inspect_cost",
      uuid5: "",
      val5: "",
      subtitle6: "相关线路检查费",
      selectType6: "line_inspect_cost",
      uuid6: "",
      val6: "",
      subtitle7: "诊断仪使用费",
      selectType7: "diagnosis_instrument_use_cost",
      uuid7: "",
      val7: "",
      subtitle8: "车辆钣金修复费用",
      selectType8: "sheet_metal_repair_cost",
      uuid8: "",
      val8: "",
      subtitle9: "车辆油漆修复费用",
      selectType9: "paint_repair_cost",
      uuid9: "",
      val9: "",
      subtitle10: " 其它费用",
      selectType10: "other_cost",
      uuid10: "",
      val10: "",
      subtitle11: "平台订单服务费",
      selectType11: "order_service_cost",
      val11: "",
      uuid11: "",
      faultDesc: "",
      className: "",
      otherSts: "",
      dateType: "date",
      vinCode: "",
      dtcCode: "",
      attachSys: "",
      attachSysTxt: "",
      ideaProcess: "",
      dtcImgList: [],
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
      brandUuid: "",

      bsqShow: false,
      bsqVal: "",
      bsqUuid: "",
      bsqActions: [],
      bsqShow1: false,
      bsqVal1: "",
      bsqUuid1: "",
      bsqActions1: [],
      arrYear: [],
      yearVal: "",
      yearShow: false,
      alreadyInspect: "", //已检过程
      warrantySts: [
        { id: "0", name: "保修" },
        { id: "1", name: "不保修" },
      ],
      warrantyVal: "", // 保修状态
      warrantyUuid: "",
      warrantyShow: false,
      totalAmount: "",
      searVal: "",
    };
  },
  created() {
    this.transmission();
    this.getXitong();
    this.getDate();
  },
  activated() {
    if (
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
    } else if (
      this.$route.query &&
      this.$route.query.addList &&
      this.$route.query.addList.length === 0 &&
      this.$route.query.from === "clickbtn"
    ) {
      this.pinpais = "";
    }
  },
  watch: {
    val11() {
      this.totalAmountFn();
    },
    val10() {
      this.totalAmountFn();
    },
    val9() {
      this.totalAmountFn();
    },
    val8() {
      this.totalAmountFn();
    },
    val7() {
      this.totalAmountFn();
    },
    val6() {
      this.totalAmountFn();
    },
    val5() {
      this.totalAmountFn();
    },
  },
  methods: {
    totalAmountFn() {
      let totalNum =
        Number(this.val5) +
        Number(this.val6) +
        Number(this.val7) +
        Number(this.val8) +
        Number(this.val9) +
        Number(this.val10) +
        Number(this.val11);
      let bianliang = Math.round(totalNum * 10);
      this.totalAmount = bianliang / 10;
    },
    //保修状态
    warrantyClick() {
      this.warrantyShow = true;
    },
    warrantySelect(i) {
      this.warrantyVal = i.name;
      this.warrantyUuid = i.id;
      this.warrantyShow = false;
    },
    // 年款相关
    getDate() {
      let curYear = new Date().getFullYear();
      for (var i = 0; i < 30; i++) {
        var oldYear = curYear--;
        this.arrYear.push({ name: oldYear });
      }
    },
    yearClick() {
      this.yearShow = true;
    },
    yearSelect(i) {
      this.yearShow = false;
      this.yearVal = i.name;
    },
    // 选择时判断车辆钣金修复费用和车辆油漆修复费用是否有选择费用 如果未选择基本检查费、相关线路检查费、诊断仪使用费则不显示价格0
		selectClick (type, val, data) {
			if (type === 'basic_inspect_cost' || type === 'line_inspect_cost' || type === 'diagnosis_instrument_use_cost') {
				if ((this.val8 == '' || this.val8 == 0) && (this.val9 == '' || this.val9 == 0)) {
						data.splice(0,1)
				}
			}
		},
    parentFn(v) {},
    parentVal(val, v) {
      this[v] = val;
      this.totalAmount = Number(val);
    },
    parentFnUuid(id, v) {
      this[v] = id;
    },

    //   获取车辆类型
    getCarType(uuid) {
      accountApi.carType(uuid).then((res) => {
        if (res.code == "0000") {
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
      this.model = item.uuid;
      this.carShow = false;
    },

    // 图片上传
    getStore(v, str) {
      const storeImgs = [];
      v.forEach((e) => {
        storeImgs.push(e.imgPath);
      });
      this[str] = storeImgs;
    },

    // 变速器类型
    transmission() {
      managerApi.zidian("transmission_type").then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.bsqActions = newData;
        }
      });
    },
    bsqClick() {
      this.bsqShow = true;
    },
    bsqSelect(i) {
      this.bsqShow = false;
      this.bsqVal = i.name;
      this.bsqUuid = i.uuid;
      this.bsqUuid1 = '';
      this.bsqVal1 = '';
      this.transmission1(i.lableCode);
      this.bsqActions1 = [];
    },

    // 具体变速器下
    transmission1(e) {
      managerApi.zidian(e).then((res) => {
        if (res.code == "0000") {
          const resData1 = res.data;
          const newData1 = resData1.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.bsqActions1 = newData1;
        }
      });
    },
    bsqClick1() {
      this.bsqShow1 = true;
    },
    bsqSelect1(i) {
      this.bsqShow1 = false;
      this.bsqVal1 = i.name;
      this.bsqUuid1 = i.uuid;
    },

    // 所属系统
    getXitong() {
      managerApi.zidian("attach_sys").then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.xitongList = newData;
          //   console.log(this.xitongList);
        }
      });
    },
    xtClick() {
      this.xtShow = true;
    },
    onSelect(item) {
      this.attachSysTxt = item.name;
      this.attachSys = item.lableCode;
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
    // 提交（新增/编辑）
    submit() {
      let params = {
        alreadyInspect: this.alreadyInspect, //已检过程
        basicInspectAmountUuid: this.uuid5, //基本检查费用
        boosterSystemUuid: this.uuid3, //增压系统uuid
        // brandName: "string", // 品牌名称 ,
        brandUuid: this.brandUuid, // 品牌uuid
        // carModelName: "string", //车型名称 ,
        carModelUuid: this.model, //车型名称uuid
        carPaintRepairAmountUuid: this.uuid9, //车辆油漆修复费用
        carSheetMetalAmountUuid: this.uuid6, // 相关线路检查费用
        carStyle: this.yearVal, // 车款 ,
        detailedAddr: "", //详细地址 ,
        diagnosisInstrumentAmountUuid: this.uuid7, //诊断仪使用费
        drivingModeUuid: this.uuid2, //驱动方式Uuid
        dtcCode: this.dtcCode, // DTC故障code ,
        dtcImageList: this.dtcImgList, //dtc图片
        dtcUuid: "", //dtc故障uuid 暂时没有传空
        engineDisplacementUuid: this.uuid1, //发动机排量uuid
        faultDesc: this.faultDesc, //故障描述
        latitude: "", //纬度
        lineInspectAmountUuid: this.uuid6, // 相关线路检查费用
        longitude: "", // 经度 ,
        orderServiceAmountUuid: this.uuid11, //平台订单服务费 ,
        otherAmountUuid: this.uuid10, //其他费用费 ,
        otherSts: this.otherSts, //其他状态
        repairTypeUuid: this.uuid4, //维修类型uuid
        totalAmount: this.totalAmount, //总费用  ,
        transmissionOneLevelUuid: this.bsqUuid, //变速器一级uuid
        transmissionTwoLevelUuid: this.bsqUuid1, //变速器二级
        vinCode: this.vinCode, //VIN码
        warrantySts: this.warrantyUuid, //保修状态
      };
      
      orderApi.localOrder(params).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("提交成功了!");
          this.$router.push({
            path: "/payxianchangOrder",
            query: { oid: res.data, dtcAmount: this.totalAmount },
          });
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
  },
  computed: {}
};
</script>

<style lang="less" scoped>
.scenceOrder {
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
    padding-bottom: 30px;
    ul {
      width: 100%;
      li {
        height: 50px;
        line-height: 50px;
        margin-bottom: 10px;
        width: 100%;
        border-bottom: 1px solid #f1f1f1;
        &:last-of-type {
          border-bottom: none;
        }
        .baseDiv_l {
          float: left;
          color: #666666;
        }
        .baseDiv_r {
          float: right;
          color: #666666;
          & > div {
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
          & > div {
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
        }
        .upload {
          /deep/ .van-uploader__upload {
            margin: 0;
          }
        }
        .feiyong {
          width: 100%;
          display: flex;
          justify-content: space-between;
          .div_l {
            color: #090909;
          }
          .div_r {
            color: #ff3838;
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
    .btn {
      width: 100%;
      margin-top: 40px;
      /deep/ .van-button {
        margin-bottom: 18px;
        width: 100%;
        line-height: 44px;
        height: 44px;
        background: #1691e3;
        border-radius: 8px;
        font-size: 16px;
      }
    }
    .tips {
      width: 100%;
      font-size: 12px;
      font-weight: 400;
      color: #666666;
    }
  }
}
</style>
