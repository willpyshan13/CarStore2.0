<template>
  <div class="baoyang">
    <dl-title btntxt=""></dl-title>
    <div class="tab">
      <van-dropdown-menu>
        <van-dropdown-item
          v-model="value"
          :options="option"
          @change="itemChange(value)"
        />
      </van-dropdown-menu>
    </div>
    <div class="cont">
      <div class="num">
        <div>
          <span>{{ completedNum }}</span>
          <p>已经完成</p>
        </div>
        <div>
          <span>{{ unpaidNum }}</span>
          <p>未付款</p>
        </div>
        <div>
          <span>{{ paidNotCompleteNum }}</span>
          <p>已付款未完成</p>
        </div>
        <div>
          <span>{{ refunded }}</span>
          <p>已退款</p>
        </div>
      </div>
      <!-- 维修保养 -->
      <div class="maintenance" v-if="maintenance">
        <van-list
          v-model="loading"
          :finished="finished"
          :immediate-check="false"
          finished-text="没有更多了"
          @load="onLoad"
          :offset="10"
        >
          <van-cell
            v-for="(item, index) in baoyangList"
            :key="index"
            @click="baoyangDetail(item.uuid)"
          >
            <div class="title">
              {{ item.goodsName }}
            </div>
            <div class="detail">
              商品价格:￥<span>{{ item.goodsPrice }}x{{ item.goodsNum }}</span
              >&nbsp;
              <span v-if="item.orderType == '0'"
                >物流费用:￥<span>{{ item.amtExpress }}</span></span
              >
              <span v-if="item.orderType == '1'"
                >服务费用:￥<span>{{ item.amtService }}</span></span
              >
              &nbsp; 实际付款:￥<span>{{ item.actualAmount }}</span>
            </div>
            <div class="status">
              <div>{{ item.contacts }} {{ item.mobile }}</div>
              <div>
                付款状态
                <span>{{
                  item.orderSts == "0"
                    ? "待支付"
                    : item.orderSts == "1"
                    ? "已支付"
                    : item.orderSts == "2"
                    ? "已取消"
                    : item.orderSts == "3"
                    ? "退款中"
                    : item.orderSts == "4"
                    ? "退款成功"
                    : item.orderSts == "5"
                    ? "退款失败"
                    : "已完成"
                }}</span>
              </div>
            </div>
          </van-cell>
        </van-list>
        <div class="no-data" v-if="!this.baoyangList">暂无数据</div>
      </div>
      <!-- 代驾订单 -->
      <div class="driveOrder" v-if="driveOrder">
        <van-list
          v-model="loading"
          :finished="finished"
          :immediate-check="false"
          finished-text="没有更多了"
          @load="onLoad"
          :offset="10"
        >
          <van-cell>
            <div class="price">￥88.00</div>
            <ul>
              <li>
                <span>出发时间:</span>
                <b>及时</b>
              </li>
              <li>
                <span>出发地:</span>
                <b>龙景花园111号南门</b>
              </li>
              <li>
                <span>目的地:</span>
                <b>希尔顿酒店（徐汇区）交通大学11号</b>
              </li>
              <li>
                <span>全程约:</span>
                <b>10km</b>
              </li>
            </ul>
          </van-cell>
        </van-list>
        <div class="no-data" v-if="!this.baoyangList">暂无数据</div>
      </div>
      <!-- 技师案例 -->
      <div class="jishiCase" v-if="jishiCase">
        <van-list
          v-model="loading2"
          :finished="finished2"
          :immediate-check="false"
          finished-text="没有更多了"
          @load="onLoad2"
        >
          <van-cell
            v-for="(it, index) in anliList"
            :key="index"
            @click="goDetail(it.uuid)"
          >
            <div class="order_num">
              <div class="order">订单号：{{ it.orderNum }}</div>
              <div class="time">{{ it.createdTime }}</div>
            </div>
            <h5>{{ it.faultDesc }}</h5>
            <div class="main">
              {{ it.ideaProcess }}
            </div>
            <div class="pay_status">
              <div class="status">
                <span class="no_pay" v-if="it.orderSts == 0">待支付</span>
                <span class="yet" v-if="it.orderSts != 0">{{
                  it.orderSts == "1"
                    ? "已支付"
                    : it.orderSts == "2"
                    ? "已取消"
                    : it.orderSts == "3"
                    ? "退款中"
                    : it.orderSts == "4"
                    ? "退款成功"
                    : "退款失败"
                }}</span>
              </div>
              <div class="payBtn" v-if="it.orderSts == 0">
                <van-button round type="info">立即支付</van-button>
              </div>
            </div>
          </van-cell>
        </van-list>
        <div class="no-data" v-if="!this.anliList">暂无数据</div>
      </div>
      <!-- 线上咨询 -->
      <div class="OnlineZixun" v-if="OnlineZixun">
        <van-list
          v-model="loading"
          :finished="finished"
          :immediate-check="false"
          finished-text="没有更多了"
          @load="onLoad"
          :offset="10"
        >
          <van-cell
            v-for="(ite, index) in zixunList"
            :key="index"
            @click="zixunDetail(ite.uuid)"
          >
            <h5>{{ ite.title }}</h5>
            <div class="main">
              {{ ite.consultDesc }}
            </div>
            <div class="pay_status">
              <div class="status">
                <span class="no_pay" v-if="ite.orderSts == 0">待支付</span>
                <span class="yet" v-if="ite.orderSts != 0">{{
                  ite.orderSts == "1"
                    ? "已支付"
                    : ite.orderSts == "2"
                    ? "已取消"
                    : ite.orderSts == "3"
                    ? "退款中"
                    : ite.orderSts == "4"
                    ? "退款成功"
                    : "退款失败"
                }}</span>
              </div>
              <div class="payBtn" v-if="ite.orderSts == 0">
                <van-button round type="info">立即支付</van-button>
              </div>
            </div>
          </van-cell>
        </van-list>
        <div class="no-data" v-if="!this.baoyangList">暂无数据</div>
      </div>
      <!-- 共享技师 -->
      <div class="share" v-if="share">
        <van-list
          v-model="loading"
          :finished="finished"
          :immediate-check="false"
          finished-text="没有更多了"
          @load="onLoad4"
          :offset="10"
        >
          <van-cell v-for="(ite, index) in shareList" :key="index">
            <h5 @click="shareDetail(ite.uuid)">{{ ite.orderNum }}</h5>
            <div class="main" @click="shareDetail(ite.uuid)">
              {{ ite.faultDescription }}
            </div>
            <div class="pay_status">
              <div class="status" @click="shareDetail(ite.uuid)">
                <span>{{ ite.orderStatusName }}</span>
              </div>
              <div class="payBtn">
                <van-button
                  round
                  type="info"
                  v-if="ite.orderStatus == 6"
                  @click="goRob(ite.uuid)"
                  >立即抢单</van-button
                >
                <van-button round type="info" v-else disabled
                  >立即抢单</van-button
                >
              </div>
            </div>
          </van-cell>
        </van-list>
        <div class="no-data" v-if="!this.baoyangList">暂无数据</div>
      </div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
import accountApi from "@/api/account";
export default {
  name: "baoyang",
  data() {
    return {
      completedNum: "",
      paidNotCompleteNum: "",
      refunded: "",
      unpaidNum: "",
      orderType: 0,
      value: 0,
      option: [
        { text: "维修保养", value: 0 },
        // { text: "代驾订单", value: 1 },
        { text: "技师案例", value: 2 },
        { text: "线上咨询", value: 3 },
        { text: "共享技师", value: 4 },
      ],
      maintenance: true,
      driveOrder: false,
      jishiCase: false,
      OnlineZixun: false,
      share: false,

      baoyangList: [],
      page: 1,
      pageSize: 10,
      loading: false,
      finished: false,
      loading2: false,
      finished2: false,
      total: 0,
      anliList: [],
      zixunList: [],
      shareList: [],
      technicianMobile: '',
      carOwnerMobile: '',
      userType: ''
    };
  },
  created() {
    this.getNum(this.orderType);
    this.getBaoyang();
    this.get()
  },
  methods: {
  	get () {
  		accountApi.getUserInfo()
  		.then(res => {
  			this.userType = res.data.userType
  			if (res.data.userType === 2) {// 技师
  				this.technicianMobile = res.data.userMobile
  			} else {
  				this.carOwnerMobile = res.data.userMobile
  			}
  		})
  	},
    // 查询数量
    getNum(orderType) {
      let para = {
        orderType: orderType,
        userType: 0,
      };
      orderApi.searNum(para).then((res) => {
        if (res.code == "0000") {
          this.completedNum = res.data.completedNum;
          this.paidNotCompleteNum = res.data.paidNotCompleteNum;
          this.refunded = res.data.refunded;
          this.unpaidNum = res.data.unpaidNum;
        }
      });
    },
    // tab切换
    itemChange(value) {
      if (value == "0") {
        this.maintenance = true;
        this.driveOrder = false;
        this.jishiCase = false;
        this.OnlineZixun = false;
        this.share = false;
        this.orderType = 5;
        this.getNum(this.orderType);
      }
      if (value == "1") {
        this.driveOrder = true;
        this.maintenance = false;
        this.jishiCase = false;
        this.OnlineZixun = false;
        this.share = false;
      }
      if (value == "2") {
        this.jishiCase = true;
        this.maintenance = false;
        this.driveOrder = false;
        this.OnlineZixun = false;
        this.share = false;
        this.page = 1;
        this.getanli(this.page, this.pageSize);
        this.orderType = 3;
        this.getNum(this.orderType);
      }
      if (value == "3") {
        this.OnlineZixun = true;
        this.maintenance = false;
        this.driveOrder = false;
        this.jishiCase = false;
        this.share = false;
        this.getOnline();
        this.orderType = 1;
        this.getNum(this.orderType);
      }
      if (value == "4") {
        this.OnlineZixun = false;
        this.maintenance = false;
        this.driveOrder = false;
        this.jishiCase = false;
        this.share = true;
        this.orderType = 10;
        this.getNum(this.orderType);
        this.share_js(this.page, this.pageSize);
      }
    },
    //   维修保养数据
    getBaoyang(page, pageSize) {
      this.loading = true;
      let param = {
        pageNum: page,
        pageSize: pageSize,
        state: 0,
      };
      orderApi.baoyang(param).then((res) => {
        if (res.code == "0000") {
          this.loading = false;
          console.log(res.data);
          if (this.page == "1") {
            this.baoyangList = res.data;
          } else {
            this.baoyangList = this.baoyangList.concat(res.data);
          }

          this.total = res.total;
          // 如果加载完毕，显示没有更多了
          if (res.data.length === 0) {
            this.finished = true;
          }
          // 如果加载完毕，显示没有更多了
          if (res.data.length === 0) {
            this.finished = true;
            return;
          }
        }
      });
    },
    // 维修保养跳转
    baoyangDetail(id) {
      this.$router.push({
        path: "/orderDetailEdit",
        query: {
          Uuid: id,
        },
      });
    },
    //滚动加载更多
    onLoad() {
      this.loading = false;
      this.page++;
      this.getBaoyang(this.page, this.pageSize);
    },
    onLoad2() {
      this.loading = false;
      this.page++;
      this.getanli(this.page, this.pageSize);
    },

    // 案例订单
    getanli(page, pageSize) {
      let para = {
        pageNum: page,
        pageSize: pageSize,
        state: 0,
        userType: 0,
      };
      this.loading2 = true;
      orderApi.anli_order(para).then((res) => {
        if (res.code == "0000") {
          this.loading2 = false;
          if (this.page == 1) {
            this.anliList = res.data;
          } else {
            this.anliList = this.anliList.concat(res.data);
          }
          this.total = res.total;
          if (res.data.length === 0) {
            this.finished2 = true;
          }
          if (res.data.length === 0) {
            this.finished2 = true;
            return;
          }
        }
      });
    },
    // 案例跳转
    goDetail(uuid) {
      this.$router.push({
        path: "/jishiAnliDetail",
        query: {
          Uuid: uuid,
        },
      });
    },

    // 线上咨询
    getOnline() {
    	let parm = {}
    	if (this.userType === 2) {// 技师
    		parm = {
	        pageNum: this.page,
	        pageSize: 10,
	        state: 0,
	        technicianMobile: this.technicianMobile
	      };
  		} else {
  			parm = {
	        pageNum: this.page,
	        pageSize: 10,
	        state: 0,
	        carOwnerMobile: this.carOwnerMobile
	        
	      };
  		}
      orderApi.onlineZixun(parm).then((res) => {
        if (res.code == "0000") {
          console.log(res.data);
          this.zixunList = res.data;
        }
      });
    },

    //线上咨询详情
    zixunDetail(uuid) {
      this.$router.push({
        path: "/onlineDetail",
        query: {
          Uuid: uuid,
        },
      });
    },

    //共享技师
    share_js(page, pageSize) {
      this.loading = true;
      let pa = {
        orderStatus: "",
        pageNum: page,
        pageSize: pageSize,
      };
      orderApi.shareJs(pa).then((res) => {
        if (res.code == "0000") {
          this.loading = false;
          let resData = res.data;
          if (this.page == "1") {
            this.shareList = resData;
          } else {
            this.shareList = this.shareList.concat(res.data);
          }
          this.total = res.total;
          // 如果加载完毕，显示没有更多了
          if (res.data.length === 0) {
            this.finished = true;
          }
          // 如果加载完毕，显示没有更多了
          if (res.data.length === 0) {
            this.finished = true;
            return;
          }
        }
      });
    },
    //共享技师滚动加载更多
    onLoad4() {
      this.loading = false;
      this.page++;
      this.share_js(this.page, this.pageSize);
    },
    //共享技师详情
    shareDetail(u) {
      this.$router.push({
        path: "/shareJsDetail",
        query: {
          Uuid: u,
        },
      });
    },
    // 立即抢单
    goRob(i) {
      orderApi.agreeOrder(i).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("抢单成功！");
          this.shareLis = [];
          this.page = 1;
          this.share_js(this.page, this.pageSize);
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.baoyang {
  width: 100%;
  margin-top: 50px;
  background: #edf5fb;
  /deep/ .m-header {
    background: #edf5fb !important;
  }
  .tab {
    width: 120px;
    text-align: center;
    position: fixed;
    top: 0;
    left: 50%;
    margin-left: -60px;
    z-index: 99999;
    /deep/ .van-dropdown-menu {
      width: 120px;
      margin: 0 auto;
      .van-dropdown-menu__bar {
        background: #edf5fb;
        box-shadow: none;
      }
      .van-dropdown-item {
        width: 120px;
        height: 224px;
        left: 50%;
        margin-left: -60px;
        .van-overlay {
          background-color: #fff;
          height: 80%;
          box-shadow: 0 2px 12px rgba(100, 101, 102, 0.12);
        }
        .van-cell__title {
          text-align: center;
        }
        .van-cell__value {
          display: none;
        }
      }
    }
  }
  .cont {
    width: 100%;
    padding: 0 15px 20px;
    box-sizing: border-box;
    .num {
      padding: 0 15px;
      box-sizing: border-box;
      width: 100%;
      height: 100px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      & > div {
        text-align: center;
        span {
          font-size: 24px;
          font-weight: 500;
          color: #f76565;
          line-height: 33px;
        }
        p {
          margin-top: 7px;
          font-size: 14px;
          color: #090909;
        }
      }
    }
    .maintenance {
      /deep/ .van-list {
        width: 100%;
        .van-cell {
          width: 100%;
          // height: 166px;
          background: #fff;
          border-radius: 8px;
          padding: 5px 15px;
          box-sizing: border-box;
          margin-bottom: 10px;
          .title {
            font-size: 16px;
            color: #090909;
            line-height: 22px;
            margin: 15px 0;
            min-height: 44px;
          }
          .detail {
            height: 17px;
            font-size: 12px;
            font-family: PingFangSC-Regular, PingFang SC;
            font-weight: 400;
            color: #666666;
            line-height: 17px;
            span {
              font-size: 13px;
            }
          }
          .status {
            margin-top: 20px;
            height: 50px;
            line-height: 50px;
            border-top: 1px solid #f1f1f1;
            display: flex;
            justify-content: space-between;
            & > div {
              font-size: 15px;
              color: #666666;
              span {
                font-size: 15px;
                color: #666666;
              }
            }
          }
        }
      }
    }
    .driveOrder {
      .price {
        width: 100%;
        height: 28px;
        font-size: 16px;
        font-weight: 500;
        color: #f76565;
        line-height: 28px;
        margin-bottom: 14px;
      }
      /deep/ .van-list {
        width: 100%;
        .van-cell {
          margin-bottom: 10px;
          width: 100%;
          // height: 166px;
          background: #fff;
          border-radius: 8px;
          padding: 20px 15px 6px;
          box-sizing: border-box;
          margin-bottom: 10px;
          ul {
            width: 100%;
            li {
              width: 100%;
              height: 20px;
              margin-bottom: 14px;
              line-height: 20px;
              span {
                font-size: 14px;
                color: #666666;
                display: inline-block;
                width: 22%;
              }
              b {
                font-size: 14px;
                color: #090909;
              }
            }
          }
        }
      }
    }
    .share,
    .jishiCase,
    .OnlineZixun {
      /deep/ .van-list {
        width: 100%;
        .van-cell {
          width: 100%;
          background: #fff;
          border-radius: 8px;
          padding: 20px 15px;
          box-sizing: border-box;
          margin-bottom: 10px;
          .order_num {
            width: 100%;
            display: flex;
            justify-content: space-between;
            height: 20px;
            line-height: 20px;
            .order {
              font-size: 14px;
              color: #666666;
            }
            .time {
              font-size: 12px;
              color: #999999;
            }
          }
          h5 {
            font-size: 16px;
            color: #090909;
            line-height: 22px;
            margin: 12px 0;
          }
          .main {
            font-size: 14px;
            color: #666666;
            line-height: 20px;
          }
          .pay_status {
            width: 100%;
            margin-top: 6px;
            .status {
              width: 100%;
              height: 22px;
              line-height: 22px;
              text-align: right;
              span {
                font-size: 16px;
              }
              .no_pay {
                color: #ff3838;
              }
              .yet {
                color: #666666;
              }
            }
            .payBtn {
              margin-top: 5px;
              width: 100%;
              height: 35px;
              /deep/ .van-button {
                float: right;
                width: 100%;
                height: 35px;
                background: #1691e3;
                font-size: 14px;
              }
            }
          }
        }
      }
    }
  }
  .no-data {
    width: 100%;
    height: 50px;
    line-height: 50px;
    text-align: center;
    font-size: 14px;
    color: #969799;
  }
}
</style>
