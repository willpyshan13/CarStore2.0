<template>
  <div class="daijiaDetail">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="detail">
        <div class="infor">
          <div class="infor_l">
            <img src="../../../static/images/1.png" alt="" />
            <div>
              <p>{{ carOwnerName }}</p>
              <p>宝马 沪A2345</p>
            </div>
          </div>
          <div class="infor_r">
            <img src="../../../static/images/phone.png" alt="" />
          </div>
        </div>
        <div class="time">
          <div class="lable">出发时间: <span>及时</span></div>
        </div>
        <div class="baseDiv">
          <b>出发地:</b>
          <span>{{ startPlace }}</span>
        </div>
        <div class="baseDiv">
          <b>目的地:</b>
          <span>{{ endPlace }}</span>
        </div>
        <div class="baseDiv">
          <b>全程约:</b>
          <span
            >10km <i>¥{{ orderAmount }}</i></span
          >
        </div>
        <div class="btn">
          <van-button type="info">开始代驾</van-button>
          <van-button type="default">退回代驾单</van-button>
        </div>
        <p>退回代驾单&nbsp;&nbsp;&gt;</p>
      </div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "daijiaDetail",
  data() {
    return {
      carOwnerName: "",
      carOwnerMobile: "",
      startPlace: "",
      endPlace: "",
      orderAmount: "",
      details: [],
    };
  },
  created() {
    this.getXq();
  },
  methods: {
    // 详情
    getXq() {
      let params = "d843a082e9ec4aa4887e1ced1e9127ab";
      orderApi.daijiaOrder(params).then((res) => {
        if (res.code == "0000") {
          console.log(res.data);
          let details = res.data;
          this.carOwnerMobile = details.CarOwnerInfoRes.carOwnerMobile;
          this.carOwnerName = details.CarOwnerInfoRes.carOwnerName;
          this.startPlace = details.OrderInfoRes.startPlace;
          this.endPlace = details.OrderInfoRes.endPlace;
          this.orderAmount = details.OrderInfoRes.orderAmount;
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.daijiaDetail {
  height: 100%;
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    background: url("../../../static/images/3.png");
    background-size: 100% 100%;
    .detail {
      border-radius: 10px 10px 0px 0px;
      margin-bottom: 10px;
      width: 100%;
      height: 353px;
      background: #fff;
      padding: 5px 15px;
      box-sizing: border-box;
      position: fixed;
      bottom: 0;
      .infor {
        margin: 20px 0 30px 0;
        width: 100%;
        height: 68px;
        display: flex;
        justify-content: space-between;
        .infor_l {
          display: flex;
          justify-content: space-between;
          img {
            width: 68px;
            height: 68px;
            border-radius: 50%;
          }
          & > div {
            margin-left: 10px;
            p {
              &:first-of-type {
                height: 30px;
                font-size: 22px;
                font-weight: 500;
                color: #090909;
                line-height: 30px;
                text-align: left;
                margin: 4px 0 8px 0;
              }
              &:last-of-type {
                height: 17px;
                font-size: 12px;
                color: #666666;
                line-height: 17px;
              }
            }
          }
        }
        .infor_r {
          img {
            width: 26px;
            height: 26px;
            margin-top: 21px;
          }
        }
      }
      .time {
        width: 100%;
        display: flex;
        justify-content: space-between;
        .lable {
          font-size: 14px;
          color: #666666;
          span {
            margin-left: 13px;
            color: #090909;
            font-size: 14px;
          }
        }
      }
      .baseDiv {
        width: 100%;
        margin: 14px 0;
        b {
          display: inline-block;
          width: 60px;
          margin-right: 13px;
          font-size: 14px;
          color: #666666;
          font-weight: 400;
        }
        span {
          font-size: 14px;
          color: #090909;
          i {
            color: #f76565;
            margin-left: 10px;
            font-style: normal;
          }
        }
      }
      .btn {
        width: 100%;
        height: 64px;
        display: flex;
        justify-content: space-between;
        /deep/ .van-button {
          width: 48%;
          height: 44px;
        }
      }
      p {
        width: 100%;
        text-align: center;
        height: 17px;
        font-size: 12px;
        color: #999999;
        line-height: 17px;
      }
    }
  }
}
</style>
