<template>
  <div class="payOrder">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon name="arrow-left" class="m-header-icon" @click="back" />
          </div>
          <div class="title">支付订单</div>
        </van-col>
      </van-row>
    </div>
    <!-- <dl-title btntxt=""></dl-title> -->
    <div class="cont">
      <ul>
        <li>
          <div class="money"><span>￥</span>{{ dtcAmount }}</div>
          <div class="line"></div>
          <div class="main">
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
        </li>
      </ul>
      <div class="btn">
        <van-button type="info" @click="submit">确认支付</van-button>
      </div>
      <div class="tishi" v-if="dtcShow">付费后该条数据仅可查看3次</div>
    </div>
  </div>
</template>

<script>
	// orderType:0 //维修保养
  // orderType:1 //线上咨询
  // orderType:3 //案例
import orderApi from "@/api/order";
export default {
  name: "payOrder",
  data() {
    return {
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
      // radio: "1",
      dtcAmount: "",
      payType: "weixin",
      oid: "",
      orderOid: "",
      isPay: 0,
      dtcShow: true,
      caseConsult: ''
    };
  },
  created() {
    this.initDialog();
  },
  activated() {
    if (this.$route.query.orderOid) {
      this.orderOid = this.$route.query.orderOid;
    }
    this.dtcAmount = this.$route.query.dtcAmount;
    this.oid = this.$route.query.oid;
    this.caseConsult = this.$route.query.caseConsult
    // this.oid = "1cd9750a3ef74ac8800f976bb82b696b";
    if (!this.$route.query.flagg) {
      this.dtcShow = false;
    }
    if (this.caseConsult == 'caseConsult') {
    	this.init()
    }
  },
  methods: {
  	init(){
      this.loading = true;
      orderApi.queryOrderInfo(this.oid)
      .then((res) => {
      	console.log(res, 'res')
          this.orderType = res.data.orderType;
          switch (res.data.orderType){
              case 3:
              case 2:
              case 1:
                  this.dtcAmount = res.data.orderAmount;
                  break
              case 10:
                  this.dtcAmount = res.data.payNum;
                  break
              default:
                  this.dtcAmount = res.data.actualAmount;
                  break
          }
          this.loading = false;
      }).catch((e) => {
          e.msg && this.$toast(e.msg)
          this.loading = false;
      })
    },
    initDialog() {
      if (this.$route.query && this.$route.query.isPay == 1) {
//      this.$dialog
//        .confirm({
//          title: "提示",
//          message: "请确认支付是否完成",
//          confirmButtonText: "已完成",
//          cancelButtonText: "未完成",
//        })
//        .then(() => {
//          //点击确认按钮后的调用
//          setTimeout(() => {
//            this.$router.push({
//              path: "/jiaoyuDetail",
//              // query: { gobackdtcdetail: 1 },
//            });
//          }, 2000);
//        });
					if (this.caseConsult == 'caseConsult') {
						_this.initSts()
					} else {
						setTimeout(() => {
              this.$router.push({
                path: "/jiaoyuDetail",
                // query: { gobackdtcdetail: 1 },
              });
            }, 2000);
					}
      }
    },
    back() {
      this.finishAll();
    },
		initSts(done){
      setTimeout(() => {
      	orderApi.queryOrderSts(this.oid)
      	.then((res) => {
              if(res){
                  this.jump()
              }
          }).catch((e) => {
              e.msg && this.$toast(e.msg)
          })
      },2000)
    },
    jump(){
      let redirectUrl = '';
      let orderType = parseInt(this.orderType);
      switch (orderType){
          case 1:
              redirectUrl = '/consultOrderDetail?uuid='+this.oid
              break
          case 3:
              redirectUrl = '/caseOrderDetail?uuid='+this.oid
              break
          case 4:
              redirect_url = '/auditOrderDetail?uuid='+this.oid
              break
      }
      this.$router.replace({
          path:redirectUrl
      })
    },
    submit() {
      this.$toast.loading({
        message: "加载中...",
        forbidClick: true,
        loadingType: "spinner",
      });
      let redirect_url =
        location.origin +
        "/payOrderJy?oid=" +
        this.oid +
        "&isPay=1" +
        "&dtcAmount=" +
        this.dtcAmount;
      let params = {
        channelType: this.payType,
        orderUuid: this.oid,
        paymentType: "MWEB",
        returnUrl: this.payType == "alipay" ? redirect_url : null,
      };
      orderApi
        .payOrder(params)
        .then((res) => {
          switch (this.payType) {
            case "weixin":
              let mwebUrl = res.data.mwebUrl;
              //   let redirect_url = "";
              //   let webURL = "http://car.store.123cgj.com/";
              //   //   let webURL = "http://car.store.dev.123cgj.com/";

              // redirect_url = location.origin  + "loading?uuid=" + this.oid;

              mwebUrl =
                mwebUrl + "&redirect_url=" + encodeURIComponent(redirect_url);
              window.location.href = mwebUrl;
              break;
            case "alipay":
              //   alert(JSON.stringify(res));
              if (res && res.data && res.data.body) {
                let paymentLink = res.data.body;
                let div = document.createElement("div");
                div.innerHTML = paymentLink;
                document.body.appendChild(div);
                document.forms[0].submit();
              }
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
.payOrder {
  height: 100%;
  width: 100%;
  overflow: hidden;
  background: #edf5fb;

  /deep/ .m-header {
    background: #edf5fb !important;
  }
  .cont {
    width: 100%;
    padding: 0 10px;
    box-sizing: border-box;
    margin-top: 50px;
    ul {
      width: 100%;
      padding: 15px 0;
      li {
        box-sizing: border-box;
        margin-bottom: 10px;
        width: 100%;
        background: #fff;
        padding: 0 15px;
        border-radius: 6px;
        box-sizing: border-box;
        .money {
          width: 100%;
          height: 107px;
          line-height: 107px;
          text-align: center;
          font-size: 34px;
          font-weight: 500;
          color: #090909;
          span {
            font-size: 14px;
          }
        }
        .line {
          width: 95%;
          margin: 0 auto;
          height: 1px;
          background: #f1f1f1;
        }
        .main {
          font-size: 14px;
          color: #666666;
          padding: 20px 0;
          /deep/ .van-hairline--top-bottom::after,
          /deep/ .van-hairline-unset--top-bottom::after {
            border: 0;
          }
          /deep/ .van-cell {
            line-height: 30px;
            padding: 0;
            &::after {
              border: none;
            }
          }
        }
      }
    }
    .btn {
      width: 100%;
      padding: 0 15px;
      position: fixed;
      bottom: 0px;
      left: 0;
      /deep/ .van-button {
        margin-bottom: 40px;
        width: 100%;
        line-height: 44px;
        height: 44px;
        background: #1691e3;
        border-radius: 8px;
        font-size: 16px;
      }
    }
    .tishi {
      width: 100%;
      text-align: center;
      line-height: 20px;
      height: 20px;
      font-size: 14px;
      position: fixed;
      bottom: 5px;
      left: 0;
    }
  }
}
</style>
