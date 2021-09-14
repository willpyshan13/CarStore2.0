<template>
  <div class="accountInforJishi">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="lable">开户名</div>
          <div class="information">
            <van-field v-model="bankName" placeholder="请输入" />
          </div>
        </li>
        <li>
          <div class="lable">银行帐户</div>
          <div class="information">
            <van-field type="digit" v-model="account" placeholder="请输入" />
          </div>
        </li>

        <li>
          <van-cell
            is-link
            title="开户银行"
            :value="openBank"
            @click="open_bank()"
          />
          <van-action-sheet
            v-model="openBankShow"
            :actions="openBankActions"
            @select="openBankSelect"
          />
        </li>
        <!-- <li>
          <div class="lable">支行名称</div>
          <div class="information">
            <van-field v-model="zhihangName" placeholder="请输入" />
          </div>
        </li>
        <li>
          <van-cell
            is-link
            title="提现方式"
            :value="tixianVal"
            @click="tixian()"
          />
          <van-action-sheet
            v-model="tixianShow"
            :actions="tixianActions"
            @select="tixianSelect"
          />
        </li>
        <li>
          <van-cell
            is-link
            title="账户类型"
            :value="accountVal"
            @click="account_type()"
          />
          <van-action-sheet
            v-model="accountShow"
            :actions="accountActions"
            @select="accountSelect"
          />
        </li> -->
      </ul>
      <div class="loadDiv">
        <div class="loadTitle">
          <div class="baseDiv_l">平台借记卡</div>
          <div class="baseDiv_r">请上传正反面</div>
        </div>
        <div class="upload">
          <upload-img
            :max-count="1"
            :imgs="debitCard"
            :imgType="1"
            @getAll="(v) => getStore(v, 'debitCard')"
          ></upload-img>
          <upload-img
            :max-count="1"
            :imgs="debitCard1"
            :imgType="1"
            @getAll="(v) => getStore1(v, 'debitCard1')"
          ></upload-img>
        </div>
      </div>
      <div class="btn">
        <van-button type="info" @click="submit()">保存</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import uploadImg from "../../components/upload-img.vue";
import accountApi from "@/api/account";
import managerApi from "@/api/manager";
export default {
  name: "accountInforJishi",
  components: { uploadImg },
  data() {
    return {
      bankName: "",
      zhihangName: "",
      account: "",
      openBank: "",
      openBankUuid: "",
      openBankShow: false,
      openBankActions: [],
      debitCard: [], // 平台借记卡正面
      debitCard1: [], // 平台借记卡反面
      //   tixianVal: "",
      //   tixianUuid: "",
      //   tixianShow: false,
      //   tixianActions: [],
      //   accountVal: "",
      //   accountUuid: "",
      //   accountShow: false,
      //   accountActions: [],
    };
  },
  created() {
    // this.txfs();
    // this.accountType();
    this.renType();
    this.zhanghaoDetail();
  },
  methods: {
    getStore(v, str) {
      const storeImgs = [];
      v.forEach((e) => {
        storeImgs.push(e.imgPath);
      });
      this[str] = storeImgs;
      console.log(this[str]);
    },
    getStore1(v, str) {
      const storeImgs = [];
      v.forEach((e) => {
        storeImgs.push(e.imgPath);
      });
      this[str] = storeImgs;
    },
    zhanghaoDetail() {
      accountApi.jishizhanghuDetail().then((res) => {
        if (res.code == "0000") {
          this.resUuid = res.data.uuid;
          this.bankName = res.data.accountName;
          this.account = res.data.cardNumbers;
          // this.zhihangName = res.data.subBranchName;
          this.openBankUuid = res.data.depositBank;
          this.debitCard = [res.data.debitCardUrl];
          this.debitCard1 = [res.data.debitCardBackUrl];
          this.openBankActions.forEach((e) => {
            if (e.uuid == res.data.depositBank) {
              this.openBank = e.lableDesc;
            }
          });
        }
      });
    },
    //提现方式
    // txfs() {
    //   managerApi.zidian("withdrawal_way").then((res) => {
    //     if (res.code == "0000") {
    //       let resDa = res.data;
    //       this.tixianActions = resDa.map((x) => {
    //         x.name = x.lableDesc;
    //         return x;
    //       });
    //     }
    //   });
    // },
    // tixian() {
    //   this.tixianShow = true;
    // },
    // tixianSelect(item) {
    //   this.tixianShow = false;
    //   this.tixianVal = item.name;
    //   this.tixianUuid = item.uuid;
    // },
    // //账号类型
    // accountType() {
    //   managerApi.zidian("account_type").then((res) => {
    //     if (res.code == "0000") {
    //       console.log(res.data);
    //       let resDa = res.data;
    //       this.accountActions = resDa.map((x) => {
    //         x.name = x.lableDesc;
    //         return x;
    //       });
    //     }
    //   });
    // },
    // account_type() {
    //   this.accountShow = true;
    // },
    // accountSelect(item) {
    //   this.accountShow = false;
    //   this.accountVal = item.name;
    //   this.accountUuid = item.uuid;
    // },
    // 开户行
    renType() {
      managerApi.zidian("deposit_bank").then((res) => {
        if (res.code == "0000") {
          let resDa = res.data;
          this.openBankActions = resDa.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
        }
      });
    },
    open_bank() {
      this.openBankShow = true;
    },
    openBankSelect(item) {
      this.openBankShow = false;
      this.openBank = item.name;
      this.openBankUuid = item.uuid;
    },

    submit() {
      if (this.bankName == "") {
        this.$toast.fail("户名必填");
        return;
      }
      if (this.account == "") {
        this.$toast.fail("账号必填");
        return;
      }
      if (this.openBankUuid == "") {
        this.$toast.fail("请选择开户行");
        return;
      }
      // if (this.zhihangName == "") {
      //   this.$toast.fail("请输入支行");
      //   return;
      // }
      //   if (this.tixianUuid == "") {
      //     this.$toast.fail("请选择提现方式");
      //     return;
      //   }
      //   if (this.accountUuid == "") {
      //     this.$toast.fail("请选择账户类型");
      //     return;
      //   }
      let params = {
        accountName: this.bankName,
        // accountType: this.accountUuid,
        alipayAccount: "",
        debitCardBackUrl: this.debitCard1[0],
        debitCardUrl: this.debitCard[0],
        cardNumbers: this.account,
        depositBank: this.openBankUuid,
        // subBranchName: this.zhihangName, // 支行
        uuid: this.resUuid ? this.resUuid : "",
        // withdrawalWay: this.tixianUuid,
      };
      accountApi.jishizhanghu(params).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("操作成功了");
          setTimeout(() => {
            this.finishAll();
          }, 1000);
        } else {
          this.$toast.fail("操作失败了");
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.accountInforJishi {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    box-sizing: border-box;
    border-top: 1px solid #f1f1f1;
    ul {
      width: 100%;

      li {
        height: 50px;
        line-height: 50px;
        padding: 0px 15px;
        box-sizing: border-box;
        display: flex;
        width: 100%;
        justify-content: space-between;
        border-bottom: 1px solid #f1f1f1;
        /deep/ .van-cell {
          padding: 0;
          line-height: 50px;
          .van-cell__title {
            span {
              color: #666666;
            }
          }
          .van-cell__right-icon {
            height: 50px;
            line-height: 50px;
          }
        }
        .lable {
          width: 20%;
          font-size: 16px;
          color: #666666;
        }
        .information {
          width: 80%;
          /deep/ .van-cell {
            padding: 0;
            height: 49px;
            line-height: 49px;
            .van-field__control {
              text-align: right;
              font-size: 16px;
              color: #999999;
              &::placeholder {
                font-size: 16px;
                color: #999999;
              }
            }
          }
        }
      }
    }
    .loadDiv {
      height: 148px;
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      border-bottom: 1px solid #f1f1f1;
      margin-bottom: 100px;
      .loadTitle {
        height: 50px;
        line-height: 50px;
        width: 100%;
        border: none;
        display: flex;
        justify-content: space-between;
        .baseDiv_l {
          color: #666666;
        }
        .baseDiv_r {
          color: #999999;
        }
      }
      .upload {
        display: flex;
        justify-content: start;
        height: 84px;
        /deep/ .van-uploader__upload {
          width: 116px;
          height: 84px;
          background: #edf5fb;
        }
        /deep/ .van-uploader__preview-image {
          width: 116px;
          height: 84px;
        }
      }
    }
    .btn {
      width: 100%;
      height: 44px;
      padding: 0 15px;
      box-sizing: border-box;
      /deep/ .van-button {
        width: 100%;
        background: #1691e3;
        border-radius: 8px;
        line-height: 44px;
        font-size: 16px;
        font-weight: 500;
      }
    }
  }
}
</style>
