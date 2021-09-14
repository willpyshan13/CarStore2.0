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
                <van-image
                  width="100%"
                  height="2.8rem"
                  fit="cover"
                  :src="item"
                />
              </div>
            </div>
          </div>
          <div class="section" style="margin-top:0.4rem;">
            <div class="title">{{ technicianName }}实付的回答</div>
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
    <cover v-if="isShowCover" @pay="handlePay"></cover>
  </div>
</template>
<script>
import cover from "@/components/cover";
import orderApi from "@/api/order";
import { ORDER_STATUS, ANSWER_STS } from "@/api/constType";
export default {
  components: {
    cover
  },
  data() {
    return {
      documentTitle: "旁听订单详情",
      uuid: "",
      orderDetail: {}
    };
  },
  computed: {
    technicianName() {
      let name = "";
      if (this.orderDetail.technicianName) {
        name = this.orderDetail.technicianName[0] + "师傅";
      }
      return name;
    },
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
</style>
