<template>
  <div class="wallet">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="main">
        <div class="money">
          <div class="price">
            <div>
              <b>￥</b><span>{{ accountAmt }}</span>
            </div>
            <p>帐户余额</p>
          </div>
          <div class="price">
            <div>
              <b>￥</b><span>{{ withdrawAmt }}</span>
            </div>
            <p>已提现</p>
          </div>
        </div>
        <div class="tixian">
          <div class="txL">
            可提现<span>{{ aviWithdrawAmt }}</span>
          </div>
          <div class="txL">
            待入账<span>{{ ingAmt }}</span>
          </div>
          <div class="txR">
            <van-popover
              v-model="showPopover"
              trigger="click"
              placement="bottom-end"
            >
              <van-grid
                square
                clickable
                :border="false"
                style="width: 210px; padding: 15px; box-sizing: border-box"
              >
                <div
                  class="txt"
                  style="font-size: 12px; color: #666666; line-height: 17px"
                >
                  规则规则规则，规则规则规则，规 则规则规则，规则规则规则，规则
                  规则规则，规则规则规则规则规则 规则规则规则规则规则规则规则规
                  规则规则规则规则规则规则规则规 规则规则规则规则规则规则则规则
                  规则规则规则规则规则规则则规则 规则规则规则规则规则规则规则
                </div>
              </van-grid>
              <template #reference>
                <van-button type="primary">?</van-button>
              </template>
            </van-popover>
          </div>
        </div>
        <div class="shuoming">提现金额将在2个工作日到账</div>
        <div class="shouru">
          <ul>
            <li>
              <div class="li_l">
                <div>代驾收入</div>
                <div style="margin-right: 18px">{{ withdrawAmt }}</div>
              </div>
              <div class="li_c">
                <span>{{ category }}</span>
              </div>
              <div class="li_r">
                <van-field v-model="driveIncome" placeholder="请输入提现现金" />
              </div>
            </li>
            <li>
              <div class="li_l">
                <div>维修收入</div>
                <div style="margin-right: 18px">{{ withdrawAmt1 }}</div>
              </div>
              <div class="li_c">
                <span>{{ category1 }}</span>
              </div>
              <div class="li_r">
                <van-field
                  v-model="maintainIncome"
                  placeholder="请输入提现现金"
                />
              </div>
            </li>
            <li>
              <div class="li_l">
                <div>案例收入</div>
                <div style="margin-right: 18px">{{ withdrawAmt2 }}</div>
              </div>
              <div class="li_c">
                <span>{{ category2 }}</span>
              </div>
              <div class="li_r">
                <van-field v-model="anliIncome" placeholder="请输入提现现金" />
              </div>
            </li>
            <li>
              <div class="li_l">
                <div>回答收入</div>
                <div style="margin-right: 18px">{{ withdrawAmt3 }}</div>
              </div>
              <div class="li_c">
                <span>{{ category3 }}</span>
              </div>
              <div class="li_r">
                <van-field v-model="huidaIncome" placeholder="请输入提现现金" />
              </div>
            </li>
            <li>
              <div class="li_l">
                <div>现场支持收入</div>
                <div style="margin-right: 18px">{{ withdrawAmt4 }}</div>
              </div>
              <div class="li_c">
                <span>{{ category4 }}</span>
              </div>
              <div class="li_r">
                <van-field
                  v-model="zhichiIncome"
                  placeholder="请输入提现现金"
                />
              </div>
            </li>
          </ul>
        </div>
        <div class="btn">
          <van-button type="info">提现</van-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
export default {
  name: "wallet",
  data() {
    return {
      showPopover: false,
      driveIncome: "",
      maintainIncome: "",
      anliIncome: "",
      huidaIncome: "",
      zhichiIncome: "",
      accountAmt: "",
      aviWithdrawAmt: "",
      ingAmt: "",
      withdrawAmt: "",
      category: "",
      category1: "",
      category2: "",
      category3: "",
      category4: "",
      withdrawAmt: "",
      withdrawAmt1: "",
      withdrawAmt2: "",
      withdrawAmt3: "",
      withdrawAmt4: "",
    };
  },
  created() {
    this.getNum();
    this.getClass();
  },
  methods: {
    getNum() {
      accountApi.zhanghu().then((res) => {
        if (res.code == "0000") {
          let resData = res.data;
          this.accountAmt = resData.accountAmt ? resData.accountAmt : 0;
          this.aviWithdrawAmt = resData.aviWithdrawAmt;
          this.ingAmt = resData.ingAmt;
          this.withdrawAmt = resData.withdrawAmt;
        }
      });
    },
    getClass() {
      accountApi.zijinClass().then((res) => {
        if (res.code == "0000") {
          let resData = res.data.profitStreamClassifies;
          //   1代驾2维修3案例4回答5商品售卖
          this.category = resData[0].amt ? resData[0].amt : 0;
          this.category1 = resData[1].amt ? resData[1].amt : 0;
          this.category2 = resData[2].amt ? resData[2].amt : 0;
          this.category3 = resData[3].amt ? resData[3].amt : 0;
          this.category4 = resData[4].amt ? resData[4].amt : 0;
          this.withdrawAmt = resData[0].withdrawAmt
            ? resData[0].withdrawAmt
            : 0;
          this.withdrawAmt1 = resData[1].withdrawAmt
            ? resData[1].withdrawAmt
            : 0;
          this.withdrawAmt2 = resData[2].withdrawAmt
            ? resData[2].withdrawAmt
            : 0;
          this.withdrawAmt3 = resData[3].withdrawAmt
            ? resData[2].withdrawAmt
            : 0;
          this.withdrawAmt4 = resData[4].withdrawAmt
            ? resData[4].withdrawAmt
            : 0;
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.wallet {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    border-top: 1px solid #f1f1f1;
    .main {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      .money {
        width: 100%;
        height: 122px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-bottom: 1px solid #f1f1f1;
        .price {
          width: 50%;
          & > div {
            text-align: center;
            color: #f76565;
            b {
              font-size: 16px;
            }
            span {
              font-size: 28px;
              font-weight: 500;
            }
          }
          p {
            text-align: center;
            font-size: 14px;
            font-weight: 500;
            color: #666666;
          }
        }
      }
      .tixian {
        display: flex;
        justify-content: space-between;
        margin: 12px 0;
        .txL {
          height: 22px;
          font-size: 16px;
          font-weight: 500;
          color: #090909;
          line-height: 22px;
          span {
            color: #f76565;
            margin-left: 6px;
          }
        }
        .txR {
          /deep/ .van-popover__wrapper {
            .van-button {
              width: 24px;
              height: 24px;
              border-radius: 50%;
              padding: 0;
              background: #1691e3;
            }
            .van-button--primary {
              border: 1px solid #1691e3;
              vertical-align: bottom;
            }
          }
        }
      }
      .shuoming {
        height: 20px;
        font-size: 14px;
        color: #666666;
        line-height: 20px;
      }
      .shouru {
        height: 255px;
        width: 100%;
        ul {
          width: 100%;
          margin-top: 20px;
          border-top: 1px solid #f1f1f1;
          li {
            width: 100%;
            height: 50px;
            line-height: 50px;
            border-bottom: 1px solid #f1f1f1;
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            .li_l {
              width: 45%;
              display: flex;
              justify-content: space-between;
              border-right: 1px solid #f1f1f1;
              & > div {
                color: #666666;
                font-size: 14px;
              }
            }
            .li_c {
              width: 20%;
              border-right: 1px solid #f1f1f1;
              span {
                color: #666666;
                margin-left: 10px;
                font-size: 14px;
              }
            }
            .li_r {
              width: 35%;
              /deep/ .van-cell {
                padding: 0;
                .van-field__control {
                  height: 49px;
                  line-height: 49px;
                  text-indent: 12px;
                  color: #666666;
                  font-size: 14px;
                }
                .van-field__control::placeholder {
                  color: #666666;
                  font-size: 14px;
                }
              }
            }
          }
        }
      }
      .btn {
        margin-top: 85px;
        /deep/ .van-button {
          height: 44px;
          width: 100%;
          background: #1691e3;
          border-radius: 8px;
          font-size: 16px;
        }
      }
    }
    /deep/ .van-dialog {
      width: 210px;
      height: 170px;
      left: 68% !important;
      background: #ffffff;
      box-shadow: 0px 2px 17px 0px rgba(0, 0, 0, 0.11);
    }
  }
}
</style>
