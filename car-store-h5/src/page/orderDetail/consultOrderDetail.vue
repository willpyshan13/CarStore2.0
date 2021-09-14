<template>
  <div class="page">
    <div class="container">
      <dl-title btntxt=""></dl-title>
      <div class="content">
        <div class="space-block"></div>
        <div class="question-detail">
          <div class="section">
            <div class="title">
              {{ orderDetail.title }}
            </div>
            <div class="desc">
              {{ orderDetail.consultDesc }}
            </div>
            <div
              class="pic-list"
              v-if="
                orderDetail.consultImgUrlList &&
                  orderDetail.consultImgUrlList.length
              "
            >
              <div
                class="item"
                v-for="(item, index) in orderDetail.consultImgUrlList"
                :key="index"
              >
                <!--<van-image
                  width="100%"
                  height="2.8rem"
                  fit="cover"
                  :src="item"
                />-->
                <video webkit-playsinline style="width:100%;height: 2.8rem;" :src="item" v-if="item.indexOf('mp4') > -1 || item.indexOf('avi') > -1 || item.indexOf('wmv') > -1 || item.indexOf('mpeg') > -1 || item.indexOf('m4v') > -1 || item.indexOf('mov') > -1 || item.indexOf('asf') > -1 || item.indexOf('flv') > -1 || item.indexOf('f4v') > -1 || item.indexOf('rmvb') > -1 || item.indexOf('rm') > -1 || item.indexOf('3gp') > -1 || item.indexOf('vob') > -1" controls="controls"></video>
								<van-image v-else width="100%" height="2.8rem" fit="cover" :src="item" />
              </div>
            </div>
          </div>
          <div
            class="section"
            style="margin-top:0.4rem;"
            v-if="orderDetail.orderSts == 1"
          >
            <div class="title">
              {{
                orderDetail.technicianName &&
                  orderDetail.technicianName.substring(0, 1)
              }}师傅的回答
            </div>
            <div class="desc">
              {{ orderDetail.answerDesc }}
            </div>
            <div
              class="pic-list"
              v-if="
                orderDetail.answerUrlList && orderDetail.answerUrlList.length
              "
            >
              <div
                class="item"
                v-for="(item, index) in orderDetail.answerUrlList"
                :key="index"
              >
                <van-image
                  width="100%"
                  height="2.8rem"
                  fit="cover"
                  :src="item"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="fixed-bottom" v-if="orderDetail.orderSts == 0">
      <div class="btn-list-wrapper">
        <div class="btn-list">
          <div class="btn plain" @click="handlePay">立即支付</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import orderApi from "@/api/order";
import { ORDER_STATUS } from "@/api/constType";
export default {
  data() {
    return {
      documentTitle: "咨询订单详情",
      uuid: "",
      orderDetail: {},
      courseAmount: ''
    };
  },
  computed: {
    status() {
      let currentStatus = ORDER_STATUS.find(item => {
        return item.value == this.orderDetail.orderSts;
      });
      return (currentStatus && currentStatus.label) || "";
    }
  },
  created() {
    this.uuid = this.$route.query.uuid;
    this.init();
  },
  methods: {
    init() {
    	orderApi.online_detail(this.uuid)
        .then(res => {
          this.orderDetail = res.data || {};
        })
        .catch(e => {
          e.msg && this.$toast(e.msg);
        });
    },
    handlePay() {
      this.$router.push({
        path: "/payOrderJy",
        query: {
        	dtcAmount: this.courseAmount,
          oid: this.uuid,
          caseConsult: 'caseConsult'
        }
      });
    }
  }
};
</script>
<style lang="less" scoped>
.page {
	padding-top: 50px;
}
.content {
  padding-bottom: 1.8rem;
}
.question-detail {
  padding: 0.28rem 0.3rem;
  .section {
    padding-bottom: 0.4rem;
    border-bottom: 0.5px solid rgba(241, 241, 241, 1);
    &:last-child {
      border-bottom: 0;
    }
  }
  .title {
    font-size: 0.36rem;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #090909;
    line-height: 0.5rem;
  }
  .desc {
    margin-top: 0.32rem;
    font-size: 0.32rem;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #666666;
    line-height: 0.44rem;
    text-align: justify;
    word-wrap: break-word;
  }
  .pic-list {
    margin-top: 0.4rem;
    .item {
      margin-bottom: 0.2rem;
      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}
.fixed-bottom {
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  padding-bottom: 0.64rem;
  padding-left: 0.3rem;
  padding-right: 0.3rem;
  z-index: 1000;
  .btn-list {
    display: flex;
    .btn {
      flex: 1;
      margin-right: 0.3rem;
      text-align: center;
      height: 0.88rem;
      border-radius: 0.08rem;
      border: 1px solid transparent;
      font-size: 0.32rem;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      line-height: 0.88rem;

      &:last-child {
        margin-right: 0;
      }
      &.plain {
        background: rgba(22, 145, 227, 1);
        color: rgba(255, 255, 255, 1);
      }
      &.default {
        color: rgba(9, 9, 9, 1);
        border-color: rgba(230, 230, 230, 1);
      }
    }
  }
}
</style>
