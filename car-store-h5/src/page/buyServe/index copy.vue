<template>
  <div class="page" v-wechat-title="documentTitle">
    <div class="container">
      <div class="header" ref="header">
        <div class="back">
          <van-nav-bar
            fixed
            class="custom-nav-bar"
            :title="documentTitle"
            :border="false"
            placeholder
            left-arrow
            safe-area-inset-top
            @click-left="handleBack"
            z-index="2000"
          />
        </div>
      </div>
      <div class="fadeIn animated faster content" v-show="!loading">
        <div class="pay-model-wrapper">
          <div class="pay-model">
            <div class="pay-info">
              <div class="amount">
                <div class="text">共</div>
                <div class="number">{{ orderAmount }}</div>
                <div class="text">元</div>
              </div>
              <div class="tips">支付总额</div>
            </div>
            <div class="pay-type">
              <van-radio-group v-model="payType">
                <van-cell-group :border="false">
                  <van-cell
                    v-for="(item, index) in payTypeList"
                    :key="index"
                    :title="item.label"
                    clickable
                    :border="false"
                    @click="payType = item.val"
                  >
                    <template #right-icon>
                      <van-radio icon-size="16" :name="item.val" />
                    </template>
                  </van-cell>
                </van-cell-group>
              </van-radio-group>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="pay-btn-wrapper">
      <div class="pay-btn" @click="sure">确认支付</div>
    </div>
    <page-loading :show="loading"></page-loading>
  </div>
</template>
<script>
// orderType:0 //维修保养
// orderType:1 //线上咨询
// orderType:2 //案例
import { queryOrderInfo, payOrder, queryOrderSts } from "@/assets/js/api";
import { webURL } from "@/assets/js/config";
export default {
  data() {
    return {
      loading: true,
      documentTitle: "支付订单",
      payTypeList: [
        {
          label: "微信支付",
          val: "weixin",
        },
        {
          label: "支付宝",
          val: "alipay",
        },
      ],
      payType: "weixin",
      oid: "",
      orderAmount: 0,
      isPay: 0,
      orderType: 1,
    };
  },
  created() {
    this.oid = this.$route.query.oid;
    this.isPay = this.$route.query.isPay;
    this.init();
    this.initDialog();
  },
  methods: {
    init() {
      this.loading = true;
      this.axios
        .get(queryOrderInfo + this.oid)
        .then((res) => {
          this.orderType = res.orderType;
          switch (res.orderType) {
            case 3:
            case 2:
            case 1:
              this.orderAmount = res.orderAmount;
              break;
            default:
              this.orderAmount = res.actualAmount;
              break;
          }
          this.loading = false;
        })
        .catch((e) => {
          e.msg && this.$toast(e.msg);
          this.loading = false;
        });
    },
    initDialog() {
      let _this = this;
      if (this.isPay == 1) {
//      this.$dialog.confirm({
//        title: "提示",
//        message: "请确认支付是否完成",
//        confirmButtonText: "已完成",
//        cancelButtonText: "未完成",
//        beforeClose,
//      });
//
//      function beforeClose(action, done) {
//        if (action === "confirm") {
//          _this.initSts(done);
//        } else {
//          _this.initSts(done);
//        }
//      }
				_this.initSts();
      }
    },
    initSts(done) {
      setTimeout(() => {
        this.axios
          .get(queryOrderSts + this.oid)
          .then((res) => {
            if (res) {
              this.jump();
              done && done();
            } else {
              done && done();
            }
          })
          .catch((e) => {
            e.msg && this.$toast(e.msg);
          });
      }, 2000);
    },
    jump() {
      let redirect_url = "";
      let orderType = parseInt(this.orderType);
      switch (orderType) {
        case 1:
          redirect_url = "/order/consult/detail?uuid=" + this.oid;
          break;
        case 3:
          redirect_url = "/order/case/detail?uuid=" + this.oid;
          break;
        case 4:
          redirect_url = "/order/audit/detail?uuid=" + this.oid;
          break;
        case 5:
          redirect_url = "/order/goods/detail?uuid=" + this.oid;
          break;
      }
      this.$router.replace({
        path: redirect_url,
      });
    },
    sure() {
      this.$toast.loading({
        message: "加载中...",
        forbidClick: true,
        loadingType: "spinner",
      });
      let redirect_url = webURL + "/payment?oid=" + this.oid + "&isPay=1";
      this.axios
        .post(payOrder, {
          channelType: this.payType,
          orderUuid: this.oid,
          paymentType: "MWEB",
          returnUrl: redirect_url,
        })
        .then((res) => {
          switch (this.payType) {
            case "weixin":
              let mwebUrl = res.mwebUrl;
              mwebUrl =
                mwebUrl + "&redirect_url=" + encodeURIComponent(redirect_url);
              window.location.replace(mwebUrl);
              break;
            case "alipay":
              let paymentLink = res.body;
              let div = document.createElement("div");
              div.innerHTML = paymentLink;
              document.body.appendChild(div);
              document.forms[0].submit();
              break;
          }
          this.$toast.clear();
        })
        .catch((e) => {
          e.msg && this.$toast(e.msg);
        });
    },
  },
};
</script>
<style lang="scss" scoped>
.page {
  min-height: 100vh;
  background: #edf5fb;
  .custom-nav-bar {
    /deep/ {
      .van-nav-bar {
        background: none;
      }
    }
  }
}
.content {
  padding: 0.2rem;
}

.pay-model {
  background: #ffffff;
  border-radius: 0.12rem;
  padding: 0.6rem 0.3rem 0.4rem;

  .title {
    position: relative;
    font-size: 0.32rem;
    font-weight: 500;
    color: #090909;
    line-height: 0.44rem;
    text-align: center;

    .close-icon {
      position: absolute;
      left: -0.36rem;
      top: 0;
      padding: 0 0.36rem;
      height: 0.44rem;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    }
  }
}

.pay-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-bottom: 0.28rem;
  border-bottom: 1px solid #f1f1f1;

  .amount {
    display: flex;
    align-items: flex-end;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #090909;
    line-height: 1;

    .text {
      font-size: 0.28rem;
      margin-bottom: 0.08rem;
    }

    .number {
      font-size: 0.68rem;
    }
  }

  .tips {
    margin-top: 0.2rem;
    font-size: 0.24rem;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #999999;
    line-height: 0.34rem;
  }
}

.pay-type {
  padding-top: 0.4rem;
  /deep/ {
    .van-cell {
      font-size: 0.28rem;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #090909;
      line-height: 0.4rem;
      padding: 0.14rem 0;
    }
  }
}

.pay-btn-wrapper {
  position: fixed;
  left: 0;
  bottom: 0.84rem;
  width: 100%;
  padding: 0 0.3rem;
}

.pay-btn {
  height: 0.88rem;
  background: #1691e3;
  border-radius: 0.16rem;
  text-align: center;
  line-height: 0.88rem;
  font-size: 0.32rem;
  font-family: PingFangSC-Regular, PingFang SC;
  font-weight: 400;
  color: #ffffff;
}
</style>