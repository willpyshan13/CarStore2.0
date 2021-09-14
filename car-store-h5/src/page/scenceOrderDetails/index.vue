<template>
  <div class="scenceOrderDetail">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon name="arrow-left" class="m-header-icon" @click="back" />
          </div>
          <div class="title">现场服务详情</div>
        </van-col>
      </van-row>
    </div>
    <!-- <dl-title btntxt=""></dl-title> -->
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
      other: false,

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
      oid: "",
    };
  },
  created() {
    this.getDetail();
  },

  methods: {
    back() {
      localStorage.removeItem("scenceUuid");
      this.finishAll();
    },
    // 获取详情
    getDetail() {
      let params = localStorage.getItem("scenceUuid");
      // let params = this.getUuid();
      orderApi.localOrderDetail(params).then((res) => {
        if (res.code == "0000") {
          localStorage.removeItem("scenceUuid");
          let resData = res.data;
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
          this.warrantySts = resData.warrantySts == "0" ? "保修" : "不保修";
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
