<template>
  <div class="shareJsDetail">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="aboutJs">
        <div class="_l">
          <img :src="photoImgUrl" alt="" />
        </div>
        <div class="_r">
          <div class="name">技师：{{ name }}</div>
          <div>工龄：{{ workingYear }}年</div>
          <div>{{ technologyType }}</div>
          <div>预约次数：{{ shareNum }}次</div>
          <div>评分：5分</div>
        </div>
      </div>
      <div class="time">预约时间：{{ appointmentTime }}</div>
      <div class="address">
        <van-icon name="map-marked" />
        {{ appointmentAddress }}
      </div>
      <ul>
        <li>品牌：{{ brandName }}</li>
        <li>车型：{{ modelName }}</li>
        <li>订单号：{{ orderNum }}</li>
        <li>下单时间：{{ createdTime }}</li>
        <li>支付费用：{{ payNum }}</li>
      </ul>
      <div class="tip">
        此费用为技师预约费用和简单检查费用，维修费请与技师洽谈
      </div>
      <div class="btn" v-if="wancheng">
        <van-button type="info" @click="tuikuan" v-if="orderStatus == 4"
          >同意退款</van-button
        >
        <!-- <van-button type="info" @click="end" v-if="orderStatus == 2"
          >完成订单</van-button
        > -->
        <!-- orderStatus == 6 立即抢单 -->
        <a :href="phone ? 'tel:' + phone : 'javascript:void(0)'">联系车主</a>
      </div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "shareJsDetail",
  data() {
    return {
      wancheng: true,
      Uuid: "",
      name: "",
      workingYear: "",
      shareNum: "",
      technologyType: "",
      photoImgUrl: "/static/images/default.png",
      appointmentTime: "",
      appointmentAddress: "",
      modelName: "",
      brandName: "",
      orderNum: "",
      payNum: "",
      createdTime: "",
      phone: "",
      orderStatus: "",
    };
  },
  created() {},
  activated() {
    if (this.$route.query && this.$route.query.Uuid) {
      this.Uuid = this.$route.query.Uuid;
      this.getDetail(this.Uuid);
    }
  },
  methods: {
    getDetail(a) {
      orderApi.shareJsDetail(a).then((res) => {
        if (res.code == "0000") {
          let resData = res.data;
          this.name = resData.name;
          this.orderStatus = resData.orderStatus;
          this.photoImgUrl = resData.photoImgUrl || '/static/images/default.png';
          this.workingYear = resData.workingYear;
          this.shareNum = resData.shareNum;
          this.technologyType = resData.technologyType;
          this.appointmentTime = resData.appointmentTime;
          this.appointmentAddress = resData.appointmentAddress;
          this.brandName = resData.brandName;
          this.modelName = resData.modelName;
          this.orderNum = resData.orderNum;
          this.payNum = resData.payNum;
          this.createdTime = resData.createdTime;
          this.phone = resData.carOwnerPhone;
        }
      });
    },
    tuikuan() {
      this.$toast.fail("当前功能暂未上线，敬请期待!");
    },
    phone() {},
    end() {
      this.wancheng = false;
    },
  },
};
</script>

<style lang="less" scoped>
.shareJsDetail {
  width: 100%;
  margin-top: 50px;
  .cont {
    padding: 0 15px;
    box-sizing: border-box;
    width: 100%;
    border-top: 1px solid #f1f1f1;
    .aboutJs {
      height: 130px;
      width: 100%;
      display: flex;
      justify-content: space-between;
      padding-top: 10px;
      box-sizing: border-box;
      ._l {
        width: 40%;
        img {
          width: 100%;
          height: 120px;
        }
      }
      ._r {
        width: 50%;
        padding-top: 10px;
        & > div {
          height: 22px;
          font-size: 14px;
          color: #666666;
          line-height: 22px;
        }
      }
    }
    .time {
      height: 24px;
      font-size: 14px;
      color: #666666;
      line-height: 24px;
      margin: 5px 0;
    }
    .address {
      border-top: 1px solid #f1f1f1;
      border-bottom: 1px solid #f1f1f1;
      height: 38px;
      font-size: 16px;
      color: #666666;
      line-height: 38px;
    }
    ul {
      width: 100%;
      margin: 10px 0;
      li {
        font-size: 16px;
        color: #666666;
        width: 100%;
        height: 30px;
        line-height: 30px;
      }
    }
    .tip {
      width: 100%;
      height: 20px;
      line-height: 20px;
      font-size: 12px;
      color: #666666;
    }
    .btn {
      width: 100%;
      margin-top: 20px;
      display: flex;
      justify-content: space-between;
      /deep/ .van-button {
        width: 30%;
        .van-button__text {
          font-size: 13px;
        }
      }
      a {
        text-align: center;
        line-height: 44px;
        width: 30%;
        color: #fff;
        background-color: #1989fa;
        border: 1px solid #1989fa;
        font-size: 13px;
      }
    }
  }
}
</style>
