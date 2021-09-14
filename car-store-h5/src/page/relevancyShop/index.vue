<template>
  <div class="relevancyShop">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon name="arrow-left" class="m-header-icon" @click="back" />
          </div>
          <div class="title">关联店铺</div>
        </van-col>
      </van-row>
    </div>
    <!-- <dl-title btntxt=""></dl-title> -->
    <div class="cont">
      <div class="guanlian">
        <span>关联店铺</span>
      </div>
      <div v-if="details">
        <ul>
          <li>
            <div class="lable">店铺名称</div>
            <div class="information" @click="shopName()">
              <span v-if="shop_name == ''"><van-icon name="arrow" /></span>
              <span v-else>{{ shop_name }}</span>
            </div>
          </li>
          <li>
            <div class="lable">店铺地址</div>
            <div class="information">
              <span>{{ addressDetail }}</span>
            </div>
          </li>
        </ul>
        <div class="btn">
          <van-button type="info" @click="submit">提交审核</van-button>
          <van-button type="info" @click="jump()">跳过</van-button>
        </div>
      </div>
      <div v-if="noDetails">
        <ul>
          <li>
            <div class="lable">店铺名称</div>
            <div class="information">
              <span>{{ shop_name }}</span>
            </div>
          </li>
          <li>
            <div class="lable">店铺地址</div>
            <div class="information">
              <span>{{ addressDetail }}</span>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
export default {
  name: "relevancyShop",
  data() {
    return {
      shop_name: "",
      dianpuUuid: "",
      jsUuid: "",
      shopList: [],
      addressDetail: "",
      details: true,
      noDetails: false,
      flag: "",
    };
  },
  activated() {
    if (this.$route.query && this.$route.query.flag == "2") {
      if (this.$route.query && this.$route.query.shopNAme) {
        let name = JSON.parse(this.$route.query.shopNAme);
        let address = JSON.parse(this.$route.query.shopAddress);
        this.dianpuUuid = JSON.parse(this.$route.query.shopUuid);
        this.shop_name = name;
        this.addressDetail = address;
      } else {
        this.shop_name = "";
        this.dianpuUuid = "";
        this.addressDetail = "";
      }
    } else if (this.$route.query && this.$route.query.flag == "1") {
      if (this.$route.query && this.$route.query.jsUuid) {
        this.jsUuid = JSON.parse(this.$route.query.jsUuid);
        this.flag = this.$route.query.flag;
      } else {
        this.jsUuid = "";
      }
    }
  },
  created() {
    if (!this.$route.query || this.$route.query.from != "rgister") {
      this.detail();
    }
  },
  methods: {
    //   详情
    detail() {
      accountApi.jsGuanlianShop().then((res) => {
        if (res) {
          if (res.code == "0000") {
            this.shop_name = res.data.storeRelateDetailRes.storeName;
            this.addressDetail =
              res.data.storeRelateDetailRes.addressProvinceName +
              res.data.storeRelateDetailRes.addressCityName +
              res.data.storeRelateDetailRes.companyAddressDetail;
            this.jsUuid = res.data.storeRelateDetailRes.technicianUuid;
            this.dianpuUuid = res.data.storeRelateDetailRes.storeUuid;
            //   let resData = [res.data];
            //   this.shopList = resData.map((x) => {
            //     x.name = x.storeName;
            //     return x;
            //   });
          }
        } else {
          this.details = false;
          this.noDetails = true;
        }
      });
    },
    // 店铺名称
    shopName() {
      this.$router.push({
        path: "/selectShop",
        query: {
          urlName: this.$route.name,
          urlId: (this.$route.params && this.$route.params.id) || "",
        },
      });
    },
    submit() {
      if (this.shop_name == "") {
        this.$toast.fail("请选择店铺后重新提交");
        return;
      }
      //   alert(localStorage.getItem("token"));
      if (this.flag == "1") {
        let params = {
          storeUuid: this.dianpuUuid, // 店铺id
          technicianUuid: this.jsUuid, //技师id
        };
        accountApi.guanlianShopNoToken(params).then((res) => {
          if (res.code == "0000") {
            this.$toast.success("提交审核成功了!");
            setTimeout(() => {
              this.finishAll();
            }, 1000);
          } else {
            this.$toast.fail(res.msg);
          }
        });
      } else {
        let params = {
          storeUuid: this.dianpuUuid, // 店铺id
        };
        accountApi.guanlianShop(params).then((res) => {
          if (res.code == "0000") {
            this.$toast.success("提交审核成功了!");
            setTimeout(() => {
              this.finishAll();
            }, 1000);
          }
        });
      }
    },
    jump() {
      this.finishAll();
    },
    back() {
      if (this.flag == "1") {
        this.$router.push({
          path: "/jishiRgister",
          // query: {
          //   dtcAmount: res.data.dtcAmount,
          // },
        });
      } else {
        this.finishPage();
      }
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
.relevancyShop {
  width: 100%;
  margin-top: 50px;
  //   background: #edf5fb;
  //   /deep/ .m-header {
  //     background: #edf5fb !important;
  //   }
  .cont {
    width: 100%;
    // padding: 0 15px;
    box-sizing: border-box;
    border-top: 1px solid #f1f1f1;
    .guanlian {
      width: 100%;
      height: 50px;
      line-height: 50px;
      padding: 0 15px;
      box-sizing: border-box;
      span {
        font-size: 16px;
        font-weight: 500;
        color: #090909;
      }
    }
    ul {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      margin-bottom: 210px;
      li {
        height: 50px;
        line-height: 50px;
        display: flex;
        width: 100%;
        justify-content: space-between;
        border-bottom: 1px solid #f1f1f1;
        &:first-of-type {
          border-top: 1px solid #f1f1f1;
        }
        /deep/ .van-cell {
          padding: 0;
          height: 49px;
          line-height: 50px;
          .van-cell__title {
            span {
              color: #666666;
            }
          }
          .van-cell__left-icon,
          .van-cell__right-icon {
            height: 50px;
            line-height: 50px;
            color: #666666;
          }
        }
        .lable {
          width: 20%;
          font-size: 16px;
          color: #666666;
        }
        .information {
          width: 80%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          text-align: right;
          span {
            font-size: 16px;
            color: #090909;
          }
        }
      }
    }
    .btn {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      /deep/ .van-button {
        height: 44px;
        width: 100%;
        background: #1691e3;
        border-radius: 8px;
        line-height: 44px;
        font-size: 16px;
        font-weight: 500;
        &:last-of-type {
          margin-top: 30px;
          color: #1684e3;
          background: #f6f7f8;
          border-color: #f6f7f8;
        }
      }
    }
  }
}
</style>
