<template>
  <div class="page" v-wechat-title="documentTitle">
    <div class="container">
      <dl-title btntxt=""></dl-title>
      <div class="content">
        <div class="space-block"></div>
        <div class="question-detail">
          <div class="section">
            <div class="title">
              {{ orderDetail.title }}
            </div>
            <div class="section-wrapper">
              <div class="section">
                <div class="subtitle">故障现象</div>
                <div class="desc" v-html="orderDetail.faultDesc"></div>
              </div>
              <div class="section">
                <div class="subtitle">诊断思路与过程</div>
                <div class="desc" v-html="orderDetail.ideaProcess"></div>
              </div>
              <div class="section">
                <div class="subtitle">结论总结</div>
                <div class="desc" v-html="orderDetail.summary"></div>
              </div>
              <div
                class="pic-list"
                v-if="orderDetail.caseImgUrl && orderDetail.caseImgUrl.length"
              >
                <div
                  class="item"
                  v-for="(item, index) in orderDetail.caseImgUrl"
                  :key="index"
                >
                  <van-image
                    width="100%"
                    height="4.6rem"
                    fit="cover"
                    :src="item"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <cover v-if="isShowCover && !showLoading" @pay="handlePay"></cover>
    <!--<page-loading :show="showLoading"></page-loading>-->
  </div>
</template>
<script>
import cover from "@/components/cover";
import orderApi from "@/api/order";
import { ORDER_STATUS } from "@/api/constType";
export default {
  components: {
    cover
  },
  data() {
    return {
      documentTitle: "案例订单详情",
      uuid: "",
      orderDetail: {},
      showLoading: true,
      courseAmount: ''
    };
  },
  computed: {
    status() {
      let currentStatus = ORDER_STATUS.find(item => {
        return item.value == this.orderDetail.orderSts;
      });
      return (currentStatus && currentStatus.label) || "";
    },
    isShowCover() {
      let bool = true;
      let orderDetail = this.orderDetail;
      if (orderDetail.uuid && orderDetail.orderSts == "1") {
        bool = false;
      }
      return bool;
    }
  },
  created() {
    this.uuid = this.$route.query.uuid;
    this.init();
  },
  methods: {
    init() {
      this.showLoading = true;
      orderApi.anli_detail(this.uuid)
        .then(res => {
          this.showLoading = false;
          this.orderDetail = res.data || {};
        })
        .catch(e => {
          this.showLoading = false;
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
}
.content {
	padding-top: 50px;
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
  .section-wrapper {
    padding: 0 0.2rem;
  }
  .section {
    margin-top: 0.28rem;
    .subtitle {
      font-size: 0.32rem;
      font-weight: 400;
    }
    .desc {
      margin-top: 0.2rem;
      font-size: 0.32rem;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #666666;
      line-height: 0.44rem;
    }
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
</style>
