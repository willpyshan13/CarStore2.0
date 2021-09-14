<template>
  <div class="dtcDetail">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon name="arrow-left" class="m-header-icon" @click="back" />
          </div>
          <div class="title">DTC详情</div>
        </van-col>
      </van-row>
    </div>
    <!-- <dl-title btntxt=""></dl-title> -->
    <div class="cont">
      <ul>
        <li>
          <div class="title">DTC代码：</div>
          <div class="content">{{ dtcCode }}</div>
        </li>
        <li>
          <div class="title">DTC类型：</div>
          <div class="content">{{ dtc_type }}</div>
        </li>
        <li>
          <div class="title">定义：</div>
          <div class="content">{{ dtcDefinition }}</div>
        </li>
        <li>
          <div class="title">说明：</div>
          <div class="content" v-html="dtcExplain"></div>
        </li>
        <li>
          <div class="title">可能原因：</div>
          <div class="content" v-html="dtcReasons"></div>
        </li>
        <li>
          <div class="title">辅助诊断：</div>
          <div class="content" v-html="dtcDiagnose"></div>
        </li>
        <li>
          <div class="title">维修品牌：</div>
          <div class="content">{{ carVal }}</div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import managerApi from "@/api/manager";
import accountApi from "@/api/account";
import orderApi from "@/api/order";
export default {
  name: "dtcDetail",
  data() {
    return {
      dtcCode: "",
      dtcDefinition: "",
      pinpais: [],
      dtcExplain: "",
      dtcReasons: "",
      dtcDiagnose: "",
      carUuid: "",
      carVal: "",
      dtcUuid: "",
      oid: "",
      orderUuid: "",
      dtcAmount: "",
      orderOid: "",
      dtc_type: "",
      dtc_type_uuid: "",
      typeActions: [],
    };
  },
  created() {
    this.getDtcType();
  },
  activated() {
    if (this.$route.query && this.$route.query.fromUuid) {
      this.orderOid = this.$route.query.fromUuid;
      this.getDetail();
      //   alert(this.orderOid);
    }
  },
  methods: {
    // dtc类型相关
    getDtcType() {
      managerApi.zidian("dtc_type").then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.typeActions = newData;
        }
      });
    },

    back() {
      //   this.finishAll();
      this.finishPage();
    },
    getDetail() {
      //   let par = "14a1f7dfe3bf4ca2849a44a4407f1099";
      let par = this.orderOid;

      orderApi.dtcOrderDetail(par).then((res) => {
        if (res.code == "0000") {
          let resData = res.data;
          this.dtcUuid = resData.uuid;
          this.orderUuid = resData.orderUuid;
          this.dtcAmount = resData.dtcAmount;
          this.dtcCode = resData.dtcCode ? resData.dtcCode : "";
          this.dtcDefinition = resData.dtcDefinition
            ? resData.dtcDefinition
            : "";
          this.dtcExplain = resData.dtcExplain ? resData.dtcExplain : "";
          this.dtcReasons = resData.dtcReasons ? resData.dtcReasons : "";
          this.dtcDiagnose = resData.dtcDiagnose ? resData.dtcDiagnose : "";
          this.carUuid = resData.dtcBrandUuid;
          // 品牌
          accountApi.carType().then((res) => {
            if (res.code == "0000") {
              const pipaiData = res.data;
              const new_data = pipaiData.map((x) => {
                x.name = x.configName;
                return x;
              });
              new_data.forEach((e) => {
                if (e.uuid == resData.dtcBrandUuid) {
                  this.carVal = e.name;
                }
              });
            }
          });
          //   this.dtc_type_uuid = resData.dtcType;
          this.typeActions.forEach((e) => {
            if (e.uuid == resData.dtcType) {
              this.dtc_type = e.lableDesc;
            }
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
.dtcDetail {
  height: 100%;
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    border-top: 1px solid #f1f1f1;
    .edit {
      width: 100%;
      padding: 0 15px;
      text-align: right;
      margin-top: 15px;
      margin-bottom: -10px;
    }
    ul {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      li {
        width: 100%;
        border-bottom: 1px solid #f1f1f1;
        padding: 15px 0;
        overflow: hidden;
        .title {
          height: 22px;
          font-size: 16px;
          font-family: PingFangSC-Regular, PingFang SC;
          color: #333333;
          line-height: 22px;
          margin-bottom: 8px;
        }
        .content {
          font-size: 14px;
          font-family: PingFangSC-Regular, PingFang SC;
          color: #666666;
          line-height: 20px;
          img {
            width: 100% !important;
          }
        }
      }
    }
  }
}
</style>
