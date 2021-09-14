<template>
  <div class="shopCenter">
    <dl-title btntxt=""></dl-title>
    <div class="gerenCont">
      <div class="cont">
        <div class="baseDiv">
          <div class="baseDiv_l">审核状态</div>
          <div class="baseDiv_r baseHasBtn">
            <span :style="{ color: statusColor }">{{ status }}</span>
            <van-button
              v-if="addData.checkSts != 1"
              type="info"
              size="small"
              class="sbtn"
              @click="getDetail(1)"
              >刷新</van-button
            >
          </div>
        </div>
        <div class="baseTitle">账号信息</div>
        <div class="baseDiv">
          <div class="baseDiv_l">店铺名称</div>
          <div class="baseDiv_r">
            <van-field v-model="storeName" placeholder="请输入" />
          </div>
        </div>
        <div class="baseDiv">
          <van-cell
            is-link
            title="店铺类型"
            :value="shop_type"
            @click="storeClick()"
            style="border-bottom: 0"
          />
          <van-action-sheet
            v-model="storeShow"
            :actions="store_type"
            @select="onStoreSelect"
          />
        </div>
        <div class="baseDiv" style="height: auto">
          <div class="baseDiv_l">添加品牌</div>
          <div
            class="baseDiv_r"
            style="
              color: #323233;
              line-height: 1.2;
              display: flex;
              align-items: center;
              padding: 5px 0;
              justify-content: flex-end;
            "
            @click="choice"
          >
            <div v-if="pinpais.length == 0">请选择品牌</div>
            <div v-else>{{ pinpais }}</div>
          </div>
        </div>
        <div class="baseTitle">企业信息</div>
        <div class="baseDiv">
          <div class="baseDiv_l">公司名称</div>
          <div class="baseDiv_r">
            <van-field v-model="companyName" placeholder="请输入" />
          </div>
        </div>
        <div class="baseDiv">
          <div class="baseDiv_l">公司地址</div>
          <div class="baseDiv_r">
            <areaChoose
              :phdText="phdText"
              :addressProvince="addData.companyAddressProvince"
              :addressCity="addData.companyAddressCity"
              @allList="allList"
            ></areaChoose>
          </div>
        </div>
        <div class="baseDiv">
          <div class="baseDiv_l">详细地址</div>
          <div class="baseDiv_r">
            <van-field v-model="companyAddressDetail" placeholder="请输入" />
          </div>
        </div>
        <div class="baseDiv khDiv">
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
        </div>
        <div class="baseDiv">
          <div class="baseDiv_l">收款账号</div>
          <div class="baseDiv_r">
            <van-field
              v-model="cardNumbers"
              type="digit"
              placeholder="请输入"
            />
          </div>
        </div>
        <div class="baseDiv">
          <div class="baseDiv_l">支付宝账号</div>
          <div class="baseDiv_r">
            <van-field v-model="zfbNum" type="digit" placeholder="请输入" />
          </div>
        </div>
        <div class="baseDiv">
          <div class="baseDiv_l">微信账号</div>
          <div class="baseDiv_r">
            <van-field v-model="wxNum" type="digit" placeholder="请输入" />
          </div>
        </div>
        <div class="loadDiv">
          <div class="loadTitle">
            <div class="baseDiv_l">营业执照</div>
            <div class="baseDiv_r" style="color: #999999">上传图片</div>
          </div>
          <div class="upload">
            <upload-img
              :max-count="1"
              :imgs="addressImg"
              :imgType="1"
              @getAll="getAddress"
            ></upload-img>
          </div>
        </div>
        <div class="loadDiv">
          <div class="loadTitle">
            <div class="baseDiv_l">店铺照片</div>
            <div class="baseDiv_r" style="color: #999999">上传图片</div>
          </div>
          <div class="upload">
            <upload-img
              :max-count="9"
              :imgs="storeImg"
              :imgType="1"
              @getAll="getStore"
            ></upload-img>
          </div>
        </div>
        <div v-for="(item, index) in dynamicItem" :key="index">
          <div class="baseTitle">
            <div>联系人信息{{ index + 1 }}</div>
            <div v-if="index + 1 == dynamicItem.length" @click="addItem">
              添加联系人
            </div>
          </div>
          <div class="baseDiv">
            <div class="baseDiv_l">姓名</div>
            <div class="baseDiv_r">
              <van-field v-model="item.userName" placeholder="请输入" />
            </div>
          </div>
          <div class="baseDiv">
            <div class="baseDiv_l">手机</div>
            <div class="baseDiv_r">
              <van-field
                type="digit"
                maxlength="11"
                v-model="item.mobile"
                placeholder="请输入"
              />
            </div>
          </div>
          <div class="baseDiv">
            <van-cell
              is-link
              title="人员类别"
              :value="item.renType"
              @click="renClick(index)"
            />
            <van-action-sheet
              v-model="renShow"
              :actions="ren_type"
              @select="(v) => onRenSelect(v, index)"
            />
          </div>
          <div class="baseDiv">
            <div class="baseDiv_l">E-mail</div>
            <div class="baseDiv_r">
              <van-field v-model="item.email" placeholder="请输入" />
            </div>
          </div>

          <div style="margin-top: 5px">
            <van-button
              type="info"
              v-if="index !== 0"
              @click="deleteItem(item, index)"
              >删除当前联系人信息</van-button
            >
          </div>
        </div>

        <div class="btn">
          <van-button type="info" @click="submit">提交</van-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import managerApi from "@/api/manager";
import areaChoose from "../../components/areaChoose.vue";
import uploadImg from "../../components/upload-img.vue";
import dispatchAppFn from "@/mixin/forPhone";

export default {
  components: { areaChoose, uploadImg },
  name: "shopCenter",
  props: {
    itemName: {
      type: String, //按钮名称
      default: "地区",
    },
    phdText: {
      type: String, //按钮名称
      default: "请选择地区",
    },
    showUnderline: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      dynamicItem: [
        {
          userName: "",
          idNum: "", //证件号码
          idType: "", //证件类型1身份证2护照
          email: "",
          mobile: "",
          personType: "", //人员类型
        },
      ],

      storeName: "",
      name: "",
      companyName: "",
      companyAddressDetail: "",
      userName: "",
      mobile: "",
      email: "",
      addressImg: [],
      storeImg: [],
      brand: "",
      tel: "",
      pipaiShow: false,
      storeShow: false,
      shop_type: "",
      shop_typeId: "",
      store_type: [],
      pipaiId: "",
      pinpai_type: [],
      fileList: [],
      address: "",
      areaList: {}, //省市区列表
      itemCount: 7,
      showAddrPopup: false, //弹出层展示
      chooseValue: "",
      renType: "",
      renShow: false,
      ren_type: [],
      renId: "",
      Province: "",
      City: "",
      County: "",
      pinpaiList: [],
      pinpais: "",
      ppList: [],
      addData: [],
      resUuid: "",
      cardNumbers: "",
      openBank: "",
      openBankUuid: "",
      openBankShow: false,
      openBankActions: [],
      zfbNum: "",
      wxNum: "",
      tixianVal: "",
      tixianUuid: "",
      tixianShow: false,
      tixianActions: [],
      index: 0,
    };
  },
  created() {
    this.renType1();
    this.txfs();
    this.getStoreType();
    this.getPinpai();
    this.getRenType();
    this.getDetail();
  },
  computed: {
    status() {
      if (this.addData.checkSts == 0) {
        return "待审核";
      } else if (this.addData.checkSts == 1) {
        return "审核通过";
      } else if (this.addData.checkSts == 2) {
        return "审核驳回";
      }
    },
    statusColor() {
      if (this.addData.checkSts == 0) {
        return "red";
      } else if (this.addData.checkSts == 1) {
        return "green";
      } else if (this.addData.checkSts == 2) {
        return "red";
      }
    },
  },
  activated() {
    // console.log(this.$route.query.addList, "选择品牌");
    if (
      this.$route.query &&
      this.$route.query.addList &&
      this.$route.query.addList.length &&
      this.$route.query.from === "clickbtn"
    ) {
      this.pinpais = JSON.parse(this.$route.query.addList)
        .map((x) => {
          return x.name;
        })
        .join("/");
      this.pinpaiList = JSON.parse(this.$route.query.addList).map((x) => {
        return x.uuid;
      });
    } else if (
      this.$route.query &&
      this.$route.query.addList &&
      this.$route.query.addList.length === 0 &&
      this.$route.query.from === "clickbtn"
    ) {
      this.pinpais = "";
    }
  },
  methods: {
    addItem() {
      this.dynamicItem.push({
        userName: "",
        idNum: "", //证件号码
        idType: "", //证件类型1身份证2护照
        email: "",
        mobile: "",
        personType: "", //人员类型
      });
    },
    deleteItem(item, index) {
      this.dynamicItem.splice(index, 1);
      console.log(this.dynamicItem, "删除");
    },
    //提现方式
    txfs() {
      managerApi.zidian("withdrawal_way").then((res) => {
        if (res.code == "0000") {
          let resDa = res.data;
          this.tixianActions = resDa.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
        }
      });
    },
    tixian() {
      this.tixianShow = true;
    },
    tixianSelect(item) {
      this.tixianShow = false;
      this.tixianVal = item.name;
      this.tixianUuid = item.uuid;
    },
    // 开户行
    renType1() {
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
      if (this.openBank !== "中国招商银行") {
        this.$toast("平台与中国招商银行有深度合作，请优先选择中国招商银行");
      }
    },
    getDetail(type) {
      accountApi.storeDetail().then((res) => {
        if (res.code == "0000") {
          this.addData = res.data;
          let resData = res.data;
          this.resUuid = resData.uuid;
          this.storeName = resData.storeName;
          this.companyName = resData.companyName;
          this.companyAddressDetail = resData.companyAddressDetail;
          this.shop_typeId = resData.storeType;
          this.storeImg = resData.shopImgList;
          this.addressImg = resData.businessImgList;
          this.pinpaiList = resData.brandUuidList;
          //   this.userName = resData.storeUserResList[0].userName;
          //   this.mobile = resData.storeUserResList[0].mobile;
          //   this.email = resData.storeUserResList[0].email;
          //   this.renId = resData.storeUserResList[0].personType;
          this.cardNumbers = resData.storeAccountRes.cardNumbers;
          this.openBankUuid = resData.storeAccountRes.depositBank;
          this.zfbNum = resData.storeAccountRes.alipayAccount;
          this.wxNum = resData.storeAccountRes.weChatAccount;
          this.tixianUuid = resData.storeAccountRes.withdrawalWay;
          this.dynamicItem = resData.storeUserResList;
          this.dynamicItem = this.dynamicItem.map((x) => {
            x.renType = this.ren_type.filter(
              (y) => y.uuid == x.personType
            )[0].lableDesc;
            return x;
          });
          if (this.addData.checkSts == 1) {
            dispatchAppFn({ fn: "setCheckSts" });
          }
          this.tixianActions.forEach((e) => {
            if (e.uuid == resData.storeAccountRes.withdrawalWay) {
              this.tixianVal = e.lableDesc;
            }
          });

          this.openBankActions.forEach((e) => {
            if (this.openBankUuid == e.uuid) {
              this.openBank = e.lableDesc;
            }
          });

          this.store_type.forEach((e) => {
            if (e.uuid == resData.storeType) {
              this.shop_type = e.lableDesc;
            }
          });
          // 品牌相关
          this.pinpai_type.forEach((e) => {
            this.pinpaiList.forEach((q) => {
              if (e.uuid == q) {
                this.ppList.push(e.name);
              }
            });
          });
          this.pinpais = this.ppList.join("/");
        }
      });
    },
    // 选择品牌
    choice() {
      const newPinpais = this.pinpais.split("/");
      this.$router.push({
        path: "/weixiuBrand",
        query: {
          newPinpais: JSON.stringify(newPinpais),
          urlName: this.$route.name,
          urlId: (this.$route.params && this.$route.params.id) || "",
        },
      });
    },
    // 获取店铺类型
    getStoreType() {
      managerApi.zidian("store_type").then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.store_type = newData;
        }
      });
    },
    // 获取品牌类型
    getPinpai() {
      accountApi.carType().then((res) => {
        if (res.code == "0000") {
          const pipaiData = res.data;
          const new_data = pipaiData.map((x) => {
            x.name = x.configName;
            return x;
          });
          this.pinpai_type = new_data;
          console.log(this.pinpai_type);
        }
      });
    },
    // 获取人员类型
    getRenType() {
      managerApi.zidian("person_type").then((res) => {
        if (res.code == "0000") {
          const renData = res.data;
          const newList = renData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.ren_type = newList;
        }
      });
    },
    // 地区选择
    allList(a) {
      this.Province = a[0].uuid;
      this.City = a[1].uuid;
      this.County = a[2].uuid;
    },
    // 上传图片
    getAddress(v) {
      const dizhiImgs = [];
      v.forEach((e) => {
        console.log(e.imgPath);
        dizhiImgs.push(e.imgPath);
      });
      this.addressImg = dizhiImgs;
    },
    getStore(v) {
      const storeImgs = [];
      v.forEach((e) => {
        console.log(e.imgPath);
        storeImgs.push(e.imgPath);
      });
      this.storeImg = storeImgs;
    },
    // 店铺类型选择
    storeClick() {
      this.storeShow = true;
    },
    onStoreSelect(i) {
      this.shop_typeId = i.uuid;
      this.storeShow = false;
      this.shop_type = i.name;
    },
    //人员类型
    renClick(index) {
      this.index = index;
      this.renShow = true;
    },
    onRenSelect(e, index) {
      //   this.renId = e.uuid;
      //   this.renShow = false;
      //   this.renType = e.name;
      this.dynamicItem[this.index].personType = e.uuid;
      this.renShow = false;
      this.dynamicItem[this.index].renType = e.name;
    },

    // 注册提交
    submit() {
      let params = {
        brandUuidList: this.pinpaiList,
        businessImgList: this.addressImg, //营业执照图片
        companyAddressProvince: this.Province, //公司地址-省
        companyAddressCity: this.City, //公司地址-市
        companyAddressCounty: this.County, //公司地址-县镇
        companyAddressDetail: this.companyAddressDetail, //公司地址-详细信息
        companyName: this.companyName,
        otherImgList: [], //资质等其他图片
        shopImgList: this.storeImg,
        storeAccountReq: {
          accountName: "",
          accountType: "",
          alipayAccount: this.zfbNum,
          cardNumbers: this.cardNumbers,
          depositBank: this.openBankUuid,
          subBranchName: "",
          weChatAccount: this.wxNum,
          withdrawalWay: this.tixianUuid,
        }, //开户相关
        storeName: this.storeName,
        storeType: this.shop_typeId,
        // storeUserReq: "", //店铺联系人
        // storeUserReq: [
        //   {
        //     idNum: "", //证件号码
        //     idType: "", //证件类型1身份证2护照
        //     email: this.email,
        //     mobile: this.mobile,
        //     personType: this.renId, //人员类型
        //     userName: this.userName,
        //   },
        // ],
        storeUserReqList: this.dynamicItem,
        uuid: this.resUuid,
      };
      debugger;
      accountApi.changeStoreDetail(params).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("更新成功了!");
          setTimeout(() => {
            this.finishAll();
          }, 1000);
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.baseHasBtn {
  display: flex;
  align-items: center;
  justify-content: flex-end;

  .sbtn {
    margin-left: 6px;
    border-radius: 6px;
  }
}
.shopCenter {
  width: 100%;
  margin-top: 50px;
  .gerenCont {
    width: 100%;
    height: 100%;
    border-top: 1px solid #f1f1f1;
    .cont {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      .baseTitle {
        height: 50px;
        line-height: 50px;
        width: 100%;
        font-size: 16px;
        color: #090909;
        border-bottom: 1px solid #f1f1f1;
        display: flex;
        justify-content: space-between;
        font-weight: bold;
        & > div:last-of-type {
          color: #1684e3;
        }
      }
      .baseDiv {
        height: 50px;
        line-height: 50px;
        width: 100%;
        display: flex;
        justify-content: space-between;
        border-bottom: 1px solid #f1f1f1;
        /deep/ .van-cell {
          padding: 0;
          height: 49px;
          line-height: 49px;
          &::after {
            border: none !important;
          }
          span {
            color: #666666;
          }
          .van-cell__left-icon,
          .van-cell__right-icon {
            line-height: 49px;
          }
        }
        .baseDiv_l {
          width: 35%;
          text-align: left;
          font-size: 16px;
          color: #666666;
        }
        .baseDiv_r {
          width: 65%;
          font-size: 16px;
          color: #090909;
          text-align: right;
          & > div {
            text-align: right;
          }
          /deep/ .tx-input {
            text-align: right;
            input {
              text-align: right;
              border: none;
              height: 48px;
            }
          }
          /deep/ .van-cell {
            padding: 0;
            height: 49px;
            line-height: 49px;
            &::after {
              border: none !important;
            }
            .van-field__control {
              text-align: right;
              &::placeholder {
                color: #999999;
              }
            }
          }
        }
      }
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
      .loadDiv {
        // height: 148px;
        border-bottom: 1px solid #f1f1f1;
        .upload {
          // height: 84px;
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
        margin-top: 40px;
        width: 100%;
        /deep/ .van-button {
          width: 100%;
          margin-bottom: 40px;
          line-height: 44px;
          height: 44px;
          background: #1691e3;
          border-radius: 8px;
          font-size: 16px;
        }
      }
      .address {
        width: 100%;
        height: 122px;
        border-bottom: 1px solid #f1f1f1;
        .addTitle {
          width: 100%;
          height: 50px;
          line-height: 50px;
          font-size: 16px;
          color: #666666;
        }
        /deep/ .van-cell {
          padding: 0;
          height: 70px;
        }
      }
    }
  }
}
</style>
