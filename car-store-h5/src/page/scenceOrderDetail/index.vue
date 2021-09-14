<template>
  <div class="scenceOrderDetail">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="title">品牌：</div>
          <div class="content">{{ brandName }}</div>
        </li>
        <li>
          <div class="title">车型:</div>
          <div class="content">{{ carModelName }}</div>
        </li>
        <li>
          <div class="title">年款:</div>
          <div class="content">{{ carStyle }}</div>
        </li>
        <li>
          <div class="title">VIN码:</div>
          <div class="content">{{ vinCode }}</div>
        </li>
        <li style="height: auto">
          <div class="title">变速器:</div>
          <div
            class="content"
            style="
              line-height: 1.2;
              padding-bottom: 5px;
              display: flex;
              align-items: center;
              justify-content: flex-end;
            "
          >
            {{ transmissionOneLevel }}
          </div>
        </li>
        <li>
          <div class="title"></div>
          <div class="content">{{ transmissionTwoLevel }}</div>
        </li>
        <li>
          <div class="title">发动机排量:</div>
          <div class="content">{{ engineDisplacement }}</div>
        </li>
        <li>
          <div class="title">驱动方式:</div>
          <div class="content">{{ drivingMode }}</div>
        </li>
        <li>
          <div class="title">增压系统:</div>
          <div class="content">{{ boosterSystem }}</div>
        </li>
        <li>
          <div class="title">保修状态:</div>
          <div class="content">{{ warrantySts }}</div>
        </li>
        <li>
          <div class="title">其他状态:</div>
          <div class="content">{{ otherSts }}</div>
        </li>
        <li style="height: auto">
          <div class="title">故障描述:</div>
          <div
            class="content"
            style="
              line-height: 1.2;
              display: flex;
              align-items: center;
              padding-bottom: 5px;
              justify-content: flex-end;
            "
          >
            {{ faultDesc }}
          </div>
        </li>
        <li style="height: auto">
          <div class="title">维修类型:</div>
          <div
            class="content"
            style="
              line-height: 1.2;
              padding-bottom: 5px;
              display: flex;
              align-items: center;
              justify-content: flex-end;
            "
          >
            {{ repairType }}
          </div>
        </li>
        <li style="height: auto">
          <div class="title">已检过程:</div>
          <div
            class="content"
            style="
              line-height: 1.2;
              padding-bottom: 5px;
              display: flex;
              align-items: center;
              justify-content: flex-end;
            "
          >
            {{ alreadyInspect }}
          </div>
        </li>
        <li style="height: auto">
          <div class="title">是否有DTC故障代码:</div>
          <div
            class="content"
            style="
              line-height: 1.2;
              padding-bottom: 5px;
              display: flex;
              align-items: center;
              justify-content: flex-end;
            "
          >
            {{ dtcCode }}
          </div>
        </li>
        <li style="height: auto; display: block">
          <div class="title" style="width: 100%">DTC图片:</div>
          <div class="content" style="text-align: left; width: 100%">
            <img
              v-for="(item, index) in fxdtcImageList"
              :key="index"
              :src="item"
            />
          </div>
        </li>
        <li>
          <div class="title">基本检查费用:</div>
          <div class="content">¥{{ basicInspectAmount }}</div>
        </li>
        <li>
          <div class="title">相关线路检查费:</div>
          <div class="content">¥{{ lineInspectAmount }}</div>
        </li>
        <li>
          <div class="title">诊断仪使用费:</div>
          <div class="content">¥{{ diagnosisInstrumentAmount }}</div>
        </li>
        <li>
          <div class="title">其他费用费:</div>
          <div class="content">¥{{ otherAmount }}</div>
        </li>
        <li>
          <div class="title">平台订单服务费:</div>
          <div class="content">¥{{ orderServiceAmount }}</div>
        </li>
        <li>
          <div class="title">总支付费用:</div>
          <div class="content">¥{{ totalAmount }}</div>
        </li>
      </ul>
      <blockquote v-if="!hiddenAll">
        <ul v-if="oneself">
          <li>
            <div class="title" style="color: #090909">现场技师填写</div>
            <div class="content"></div>
          </li>
          <li class="imgTxtLi">
            <div class="item">
              <div class="div_l">故障描述:</div>
            </div>
            <div class="imgTxt" style="height: 134px">
              <van-field
                v-model="faultDescJs"
                autosize
                rows="5"
                type="textarea"
                placeholder="简明扼要阐述故障现象（例：行驶中出现车身后部出现异响或冷车启动后发动机抖动仪表故障灯点亮）。"
              />
            </div>
          </li>
          <li class="imgTxtLi">
            <div class="item">
              <div class="div_l">已检过程:</div>
            </div>
            <div class="imgTxt" style="height: 134px">
              <van-field
                v-model="alreadyInspectJs"
                autosize
                rows="5"
                type="textarea"
                placeholder="请简要描述已检过程。"
              />
            </div>
          </li>
          <li class="imgTxtLi">
            <div class="item">
              <div class="div_l" style="width: 45%">是否有DTC故障代码:</div>
            </div>
            <div class="imgTxt" style="height: 134px">
              <van-field
                v-model="dtcCodeJs"
                autosize
                rows="5"
                type="textarea"
                placeholder="可传多个故障代码，以逗号（，）隔开。"
              />
            </div>
          </li>
          <li class="imgTxtLi">
            <div class="item">
              <div class="div_l">DTC图片：</div>
            </div>
            <div class="imgTxt">
              <div class="upload">
                <upload-img
                  :max-count="8"
                  :imgs="dtcImgListJs"
                  :imgType="1"
                  @getAll="(v) => getStore(v, 'dtcImgListJs')"
                ></upload-img>
              </div>
            </div>
          </li>
          <li class="imgTxtLi">
            <div class="item">
              <div class="div_l" style="width: 45%">维修总结:</div>
            </div>
            <div class="imgTxt" style="height: 134px">
              <van-field
                v-model="repairSummaryJs"
                autosize
                rows="5"
                type="textarea"
                placeholder="请输入维修总结内容"
              />
            </div>
          </li>
          <li style="border: none; height: 30px; line-height: 30px">
            <div class="item">
              <div class="div_l">故障是否解决：</div>
              <div class="div_r"></div>
            </div>
          </li>
          <li>
            <van-radio-group direction="horizontal" v-model="faultSolve">
              <van-radio :name="0" shape="square">是</van-radio>
              <van-radio :name="1" shape="square">否</van-radio>
            </van-radio-group>
          </li>
        </ul>

        <div class="waitPay" v-if="noPay">
          <div class="btn">
            <van-button type="info" @click="submit">继续支付</van-button>
          </div>
          <div class="tips">下单后平台订单服务费将不予以退回</div>
        </div>
        <div class="alreadyPay" v-if="alreadyPay">
          <div class="btn">
            <a :href="phone ? 'tel:' + phone : 'javascript:void(0)'" v-if="ziji"
              >联系技师</a
            >

            <van-button type="info" @click="jsAdd" v-if="other"
              >完成订单</van-button
            >
            <van-button type="info" @click="orderSuccess" v-if="selfe"
              >完成订单</van-button
            >
            <van-button type="info" @click="tuikuan" v-if="ziji"
              >退款申请</van-button
            >
          </div>
        </div>
      </blockquote>
    </div>
  </div>
</template>

<script>
import managerApi from "@/api/manager";
import orderApi from "@/api/order";
import uploadImg from "../../components/upload-img.vue";

export default {
  components: { uploadImg },
  name: "scenceOrderDetail",
  data() {
    return {
      ziji: true,
      other: false,
      selfe: false,
      oneself: true,
      noPay: false,
      alreadyPay: false,
      fxdtcImageList: [],
      brandName: "",
      carModelName: "",
      carStyle: "",
      vinCode: "",
      oneBsq: "",
      bsqActions: [],
      TwoBsq: "",
      paiLiang: [],
      pailiang: "",
      otherAmount: "",
      orderServiceAmount: "",
      diagnosisInstrumentAmount: "",
      lineInspectAmount: "",
      basicInspectAmount: "",
      totalAmount: "",
      dtcCode: "",
      faultDesc: "",
      otherSts: "",
      warrantySts: "",
      alreadyInspect: "",
      drivingMode: "",
      boosterSystem: "",
      engineDisplacement: "",
      transmissionOneLevel: "",
      transmissionTwoLevel: "",
      repairType: "",
      dtcImageList: [],
      radio: "1",
      weixiuList: [],
      wxVal: "",
      wxUuid: "",
      wxShow: false,
      oid: "",
      alreadyInspectJs: "",
      faultDescJs: "",
      dtcCodeJs: "",
      repairSummaryJs: "",
      dtcImgListJs: [],
      faultSolve: 0,
      phone: "",
      hiddenAll: false,
      sceneOrderTechnicianUuid: ''
    };
  },
  created() {
    this.getDetail();
    this.getweixiu();
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
  methods: {
    orderSuccess() {
      let sceneOrderUuid = this.oid;
      orderApi.wancheng(sceneOrderUuid).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("订单完成了!");
          this.alreadyPay = false;
          this.oneself = false;
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
    // 获取详情
    getDetail() {
      localStorage.setItem("scenceUuid", this.getUuid());
      // let params = "07d0651597384fff8c592830db2e5899";
      // let params = this.getUuid() || '0a0f5dfb8a424e208f961f3d0bb5bf55';
      let params = this.getUuid();
      orderApi.localOrderDetail(params).then((res) => {
        if (res.code == "0000") {
          let resData = res.data;
          this.phone = resData.buyerMobile;
          this.fxdtcImageList = resData.dtcImageList;
          this.oid = resData.uuid;
          this.brandName = resData.brandName;
          this.repairType = resData.repairType;
          this.carModelName = resData.carModelName;
          this.carStyle = resData.carStyle;
          this.vinCode = resData.vinCode;
          this.otherAmount = resData.otherAmount;
          this.orderServiceAmount = resData.orderServiceAmount;
          this.diagnosisInstrumentAmount = resData.diagnosisInstrumentAmount;
          this.lineInspectAmount = resData.lineInspectAmount;
          this.basicInspectAmount = resData.basicInspectAmount;
          this.totalAmount = resData.totalAmount;
          this.dtcCode = resData.dtcCode;
          this.faultDesc = resData.faultDesc;
          this.otherSts = resData.otherSts;
          this.alreadyInspect = resData.alreadyInspect;
          this.drivingMode = resData.drivingMode;
          this.boosterSystem = resData.boosterSystem;
          this.engineDisplacement = resData.engineDisplacement;
          this.transmissionOneLevel = resData.transmissionOneLevel;
          this.transmissionTwoLevel = resData.transmissionTwoLevel;
          this.faultDescJs = resData.technicianFaultDesc;
          this.alreadyInspectJs = resData.technicianAlreadyInspect;
          this.dtcCodeJs = resData.technicianDtcCode;
          this.dtcImgListJs = resData.technicianDtcImgList;
          this.repairSummaryJs = resData.repairSummary;
          this.faultSolve = resData.faultSolve;
          this.sceneOrderTechnicianUuid = resData.sceneOrderTechnicianUuid
          if (resData.warrantySts == "0") {
            this.warrantySts = "保修"
          } else if (resData.warrantySts == "1") {
            this.warrantySts = "不保修"
          } else {
            this.warrantySts = ""
          }
          
          if (resData.orderSts == "0") {
            this.noPay = true;
            this.alreadyPay = false;
          }
          if (resData.orderSts == "1") {
            this.noPay = false;
            this.alreadyPay = true;
          }
          if (resData.orderSts == "6") {
            this.noPay = false;
            this.alreadyPay = false;
            this.oneself = false;
          }
          if (resData.isOneself == true) {
            this.oneself = false;
            this.selfe = true;
            this.ziji = true;
          }
          if (resData.isOneself == false) {
            if (resData.grabbingOrdersSts === 0) {
               this.hiddenAll = true
            }
            this.oneself = resData.orderSts != "6";
            this.other = true;
            this.ziji = false;
          }
        }
      });
    },

    // 提交（新增/编辑）
    submit() {
      this.$router.push({
        path: "/payxianchangOrder",
        query: {
          dtcAmount: this.totalAmount,
          oid: this.oid,
        },
      });
    },
    // 技师新增
    jsAdd() {
      if (this.faultDescJs == "") {
        this.$toast.fail("请填写故障描述");
        return;
      }
      if (this.alreadyInspectJs == "") {
        this.$toast.fail("请填写已检过程");
        return;
      }
      if (this.dtcCodeJs == "") {
        this.$toast.fail("请填写故障代码");
        return;
      }
      if (this.repairSummaryJs == "") {
        this.$toast.fail("请填写维修总结");
        return;
      }
      if (this.dtcImgListJs.length < 1) {
        this.$toast.fail("请上传图片");
        return;
      }
      let par = {
        alreadyInspect: this.alreadyInspectJs,
        dtcCode: this.dtcCodeJs,
        dtcImgList: this.dtcImgListJs,
        faultDesc: this.faultDescJs,
        faultSolve: this.faultSolve,
        repairSummary: this.repairSummaryJs,
        sceneOrderUuid: this.oid,
      };
      if (this.sceneOrderTechnicianUuid) {
        par.sceneOrderTechnicianUuid = this.sceneOrderTechnicianUuid
      }
      orderApi.localJishiAdd(par).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("提交成功了!");
          this.noPay = false;
          //   this.alreadyPay = false;
          //   this.alreadyInspectJs = "";
          //   this.faultDescJs = "";
          //   this.dtcCodeJs = "";
          //   this.repairSummaryJs = "";
          //   this.dtcImgListJs = [];
          //   this.oneself = false;
        }
      });
    },
    //退款申请
    tuikuan() {
      this.$toast.fail("当前功能暂未上线，敬请期待!");
    },

    // 图片上传
    getStore(v, str) {
      const storeImgs = [];
      v.forEach((e) => {
        storeImgs.push(e.imgPath);
      });
      this[str] = storeImgs;
    },
    // 维修类型
    getweixiu() {
      managerApi.zidian("goods_type").then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.weixiuList = newData;
          console.log(this.weixiuList);
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.scenceOrderDetail {
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
    padding-bottom: 20px;
    ul {
      width: 100%;
      li {
        height: 50px;
        line-height: 50px;
        margin-bottom: 10px;
        width: 100%;
        border-bottom: 1px solid #f1f1f1;
        display: flex;
        justify-content: space-between;
        &:last-of-type {
          border-bottom: none;
        }
        .title {
          color: #666666;
          width: 50%;
        }
        .content {
          text-align: right;
          width: 50%;
          color: #090909;
          img {
            width: 84px;
            height: 84px;
            margin-right: 3px;
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
            text-align: right;
          }
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
        /deep/ .van-radio-group {
          margin: 0 auto;
          width: 60%;
          display: flex;
          justify-content: space-between;
        }
      }
      .imgTxtLi {
        height: auto;
        padding-bottom: 20px;
        display: block;
        // &:last-of-type {
        //   padding-bottom: 0;
        //   border: none;
        // }
        .imgTxt {
          width: 100%;
          padding: 10px 15px;
          box-sizing: border-box;
          border: 1px solid #f1f1f1;

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
      }
    }

    .btn {
      width: 100%;
      padding: 0 15px;
      margin-top: 20px;
      /deep/ .van-button {
        margin-bottom: 30px;
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
      text-align: center;
      color: #666666;
    }
    .alreadyPay {
      width: 100%;
      .btn {
        width: 100%;
        display: flex;
        justify-content: space-between;
        /deep/ .van-button {
          width: 30%;
          .van-button__text {
            font-size: 13px;
          }
        }
        a {
          border-radius: 8px;
          text-align: center;
          line-height: 44px;
          height: 44px !important;
          width: 30%;
          color: #fff;
          background-color: #1989fa;
          border: 1px solid #1989fa;
          font-size: 13px;
        }
      }
    }
  }
}
</style>
