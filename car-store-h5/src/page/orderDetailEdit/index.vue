<template>
  <div class="orderDetailEdit">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon
              name="arrow-left"
              class="m-header-icon"
              @click="goBcak()"
            />
          </div>
          <div class="title">订单详情</div>
          <div class="btn themecolor" v-show="!showTxt" @click="clickChange()">
            {{ btnTxt }}
          </div>
          <div class="btn themecolor" v-show="showTxt" @click="clickChange()">
            {{ btnTxt }}
          </div>
        </van-col>
      </van-row>
    </div>
    <!-- <dl-title btntxt="完成"></dl-title> -->
    <div class="cont">
      <h2>订单信息</h2>
      <div class="line"></div>
      <div class="title" v-if="orderType != '2'">
        {{ goodsName }}
      </div>
      <div class="title" v-if="orderType == '2'">检测服务</div>
      <p v-if="orderType == '0'">
        商品价格：￥
        <span>{{ receivableAmount }}X{{ goodsNum }}</span> 物流费用：￥<span>{{
          amtExpress
        }}</span>
        实际付款：￥{{ actualAmount }} <span></span>
      </p>
      <p v-if="orderType == '1'">
        商品价格：￥
        <span>{{ receivableAmount }}X{{ goodsNum }}</span> 服务费用：￥<span>{{
          amtService
        }}</span>
        实际付款：￥{{ actualAmount }} <span></span>
      </p>
      <p v-if="orderType == '2'">
        商品价格：￥
        <span>{{ receivableAmount }}X{{ goodsNum }}</span> 实际付款：￥{{
          actualAmount
        }}
        <span></span>
      </p>

      <div class="baseDiv">
        <div class="div">
          <div class="divl">支付状态</div>
          <div class="divr">
            {{
              orderSts == "0"
                ? "待支付"
                : orderSts == "1"
                ? "已支付"
                : orderSts == "2"
                ? "已取消"
                : orderSts == "3"
                ? "退款中"
                : orderSts == "4"
                ? "退款成功"
                : "退款失败"
            }}
          </div>
        </div>
        <div class="div">
          <div class="divl">订单状态</div>
          <div class="divr">{{ serviceSts == "0" ? "未完成" : "已完成" }}</div>
        </div>
      </div>
      <div class="baseDiv" v-if="orderType == '0'">
        <h4>物流信息</h4>
        <div class="div">
          <div class="divl">物流地址</div>
          <div class="divr">
            <input
              type="text"
              v-model="deliveryAddress"
              placeholder="请输入物流地址"
              :readonly="state"
            />
            <!-- <van-field v-model="deliveryAddress" placeholder="请输入物流地址" /> -->
          </div>
        </div>
        <div class="div">
          <div class="divl">收件人</div>
          <div class="divr">
            <input
              type="text"
              v-model="contacts"
              placeholder="请输入收件人"
              :readonly="state"
            />
            <!-- <van-field
              v-model="contacts"
              placeholder="请输入收件人"
              readonly="false"
            /> -->
          </div>
        </div>
        <div class="div">
          <div class="divl">联系方式</div>
          <div class="divr">
            <input
              type="text"
              v-model="mobile"
              placeholder="请输入联系方式"
              :readonly="state"
            />
            <!-- <van-field v-model="mobile" placeholder="请输入联系方式" /> -->
          </div>
        </div>
      </div>
      <div class="baseDiv">
        <h4>客户信息</h4>
        <div class="div">
          <div class="divl">联系方式</div>
          <div class="divr">
            <van-field v-model="mobile" placeholder="请输入联系方式" />
          </div>
        </div>
      </div>
      <div class="baseDiv" v-if="orderType != '0'">
        <h4>服务信息</h4>
        <div class="div">
          <div class="divl">维修技师</div>
          <div class="divr">
            <input
              type="text"
              v-model="technician"
              placeholder="请输入维修技师"
              :readonly="state"
            />
            <!-- <van-field v-model="technician" placeholder="请输入维修技师" /> -->
          </div>
        </div>
        <div class="div">
          <div class="divl">车辆进店里程</div>
          <div class="divr">
            <input
              type="text"
              v-model="mileageInto"
              placeholder="请输入车辆进店里程"
              :readonly="state"
            />
            <!-- <van-field v-model="mileageInto" placeholder="请输入车辆进店里程" /> -->
          </div>
        </div>
        <div class="div">
          <div class="divl">车辆出店里程</div>
          <div class="divr">
            <input
              type="text"
              v-model="mileageOut"
              placeholder="请输入车辆出店里程"
              :readonly="state"
            />
            <!-- <van-field v-model="mileageOut" placeholder="请输入车辆出店里程" /> -->
          </div>
        </div>
      </div>
      <div class="baseDiv">
        <h4>评价信息</h4>
        <div class="div">
          <div class="divl">商品相符</div>
          <div class="divr">
            <van-rate allow-half readonly v-model="goodsScore" />
          </div>
        </div>
        <div class="div">
          <div class="divl">服务态度</div>
          <div class="divr"><van-rate readonly v-model="serviceScore" /></div>
        </div>
      </div>

      <div class="beizhu">
        <h3>备注信息</h3>
        <div class="add">
          修改物流地址：
          <van-field v-model="logisticsAdd" placeholder="请输入修改物流地址" />
        </div>
      </div>
      <div class="btn">
        <van-button type="info">同意退款</van-button>
        <van-button type="default">完成订单</van-button>
        <p>
          请在确认退货收到以及与客户协商完成后点击同意退款
          点击完成订单后需客户确认完成或7天该订单自动修改为完成状态
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "orderDetailEdit",
  data() {
    return {
      goodsScore: 0,
      serviceScore: 0,
      btnTxt: "编辑",
      showTxt: false,
      state: false,
      orderType: 0,
      goodsName: "",
      orderSts: "",
      serviceSts: "",
      amtExpress: "",
      amtService: "",
      goodsNum: "",
      actualAmount: "",
      deliveryAddress: "",
      mobile: null,
      contacts: "",
      receivableAmount: "",
      technician: "",
      mileageInto: "",
      mileageOut: "",
      logisticsAdd: "",
      uuid: "",
      fuwu: false,
      jiance: false,
    };
  },
  activated() {
    if (this.$route.query && this.$route.query.Uuid) {
      this.uuid = this.$route.query.Uuid;
      this.getDetail();
    }
  },
  methods: {
    getDetail() {
      let canshu = this.uuid;
      orderApi.baoyangDetail(canshu).then((res) => {
        if (res.code == "0000") {
          console.log(res.data, "==========");
          let resData = res.data;
          this.goodsName = resData.goodsName;
          this.orderType = resData.orderType;
          this.orderSts = resData.orderSts;
          this.serviceSts = resData.serviceSts;
          this.amtExpress = resData.amtExpress;
          this.amtService = resData.amtService;
          this.goodsNum = resData.goodsNum;
          this.actualAmount = resData.actualAmount;
          this.deliveryAddress = resData.deliveryAddress;
          this.contacts = resData.contacts;
          this.mobile = resData.mobile;
          this.receivableAmount = resData.receivableAmount;
          this.goodsScore = resData.goodsScore
            ? Number(resData.goodsScore)
            : "";
          this.serviceScore = resData.serviceScore
            ? Number(resData.serviceScore)
            : 0;
        }
      });
    },
    clickChange() {
      this.showTxt = !this.showTxt;
      if (this.showTxt == false) {
        this.btnTxt = "编辑";
        this.state = false;
      } else {
        this.state = true;
        this.btnTxt = "完成";
      }
    },
    goBcak() {
      //  window.js_android.finishPage();
      this.finishPage();
    },
  },
};
</script>

<style lang="less" scoped>
.orderDetailEdit {
  width: 100%;
  margin-top: 50px;
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
    .btn {
      position: absolute;
      top: 0;
      right: 16px;
      font-size: 18px;
      line-height: 50px;
      i {
        margin-top: 13px;
      }
    }
  }
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    .line {
      width: 100%;
      height: 1px;
      background: #f1f1f1;
      margin: 13px 0 20px 0;
    }
    .title {
      font-size: 16px;
      color: #090909;
      line-height: 22px;
    }
    p {
      height: 17px;
      font-size: 12px;
      font-weight: 400;
      color: #666666;
      line-height: 17px;
      margin: 14px 0;
      span {
        font-size: 12px;
      }
    }
    .baseDiv {
      width: 100%;
      border-bottom: 1px solid #f1f1f1;
      h4 {
        height: 22px;
        font-size: 16px;
        font-weight: 500;
        color: #090909;
        line-height: 22px;
        margin: 20px 0;
      }
      .div {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        height: 22px;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
        margin-bottom: 20px;

        .divl {
          width: 30%;
          text-align: left;
        }
        .divr {
          width: 70%;
          text-align: right;
          input {
            width: 100%;
            color: #323233;
            text-align: right;
            border: none;
          }
          /deep/ .van-cell {
            padding: 0;
            input {
              text-align: right;
            }
          }
        }
      }
    }
    .beizhu {
      width: 100%;
      h3 {
        height: 22px;
        font-size: 16px;
        font-weight: 500;
        color: #090909;
        margin: 20px 0;
        line-height: 22px;
      }
      .add {
        height: 22px;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
        /deep/ .van-cell {
          float: right;
          width: 67%;
          padding: 0;
          input {
            text-align: right;
          }
        }
      }
    }
    .btn {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      margin-top: 30px;
      /deep/ .van-button {
        width: 48%;
        &:first-of-type {
          margin-right: 4%;
        }
      }
      p {
        margin: 20px 0;
        font-size: 12px;
        color: #999999;
        line-height: 17px;
      }
    }
  }
}
</style>
