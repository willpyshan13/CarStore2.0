<template>
  <div class="kechengDetail">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="title">{{ title }}</div>
      <div class="item">
        <div class="item_l">讲师：</div>
        <div class="item_r">{{ courseLecturer }}</div>
      </div>
      <div class="item">
        <div class="item_l">价格：</div>
        <div class="item_r">￥{{ courseAmount }}</div>
      </div>
      <div class="item" v-if="tiemShow">
        <div class="item_l">课程时间：</div>
        <div class="item_r">{{ courseTime }}</div>
      </div>
      <div class="ittm">
        <div class="ittm_t">内容简介:</div>
        <div class="ittm_b" v-html="courseIntro"></div>
      </div>
      <div class="ittm" v-if="kecheng">
        <div class="ittm_t">课程内容:</div>
        <div class="ittm_b" v-html="courseContent"></div>
      </div>
      <div class="ittm" v-if="addr">
        <div class="ittm_t">地址:</div>
        <div class="ittm_b">{{ courseUrl }}</div>
      </div>
    </div>
    <!-- <div class="btn" v-if="contShow1">
      <van-button type="info" @click="buy">立即购买</van-button>
    </div> -->
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "kechengDetail",
  data() {
    return {
      uuid: "",
      title: "",
      courseLecturer: "",
      content: "",
      faultDesc: "",
      courseTime: "",
      ideaProcess: "",
      courseAmount: "",
      courseIntro: "",
      courseContent: "",
      //   contShow: false,
      //   contShow1: false,
      tiemShow: false,
      courseUrl: "",
      oid: "",
      kecheng: false,
      addr: false,
    };
  },
  created() {
    // this.getDetail();
  },
  activated() {
    if (this.$route.query && this.$route.query.fromUuid) {
      this.uuid = this.$route.query.fromUuid;
      this.getDetail();
    }
  },
  methods: {
    getDetail() {
      let canshu = this.uuid;
      //   let canshu = "5333076562f642c9b36ed0c792551136";
      orderApi.jiaochengDetail(canshu).then((res) => {
        if (res.code == "0000") {
          let resData = res.data;
          this.oid = resData.uuid;
          this.content = resData.courseContent;
          this.title = resData.courseTitle;
          this.courseLecturer = resData.courseLecturer;
          this.courseTime = resData.courseTime;
          this.courseAmount = resData.orderAmount;
          this.courseIntro = resData.courseIntro;
          this.courseContent = resData.courseContent;
          this.courseUrl = resData.courseUrl;
          if (resData.courseType == "0") {
            this.tiemShow = true;
            this.addr = true;
            this.kecheng = false;
          } else {
            this.tiemShow = false;
            this.kecheng = true;
            this.addr = false;
          }
        }
      });
    },
    buy() {
      this.$router.push({
        path: "/payOrderJy",
        query: {
          dtcAmount: this.courseAmount,
          oid: this.oid,
        },
      });
    },
  },
};
</script>
<style lang="less">
.ittm_b {
  img {
    width: 100% !important;
  }
}
</style>
<style lang="less" scoped>
.kechengDetail {
  //   height: 100%;
  width: 100%;
  margin-top: 50px;
  .cont {
    border-top: 1px solid #f1f1f1;
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    .title {
      width: 100%;
      color: #090909;
      height: 50px;
      line-height: 50px;
      font-size: 16px;
      font-family: PingFangSC-Medium, PingFang SC;
      font-weight: 500;
      border-bottom: 1px solid #f1f1f1;
      color: #090909;
    }
    .item {
      height: 50px;
      line-height: 50px;
      display: flex;
      justify-content: space-between;
      border-bottom: 1px solid #f1f1f1;
      .item_l {
        font-size: 16px;
        color: #666666;
      }
      .item_r {
        color: #090909;
      }
    }
    .ittm {
      width: 100%;
      .ittm_t {
        height: 50px;
        line-height: 50px;
        font-size: 16px;
        color: #666666;
      }
      .ittm_b {
        color: #666666;
        img {
          width: 100%;
        }
      }
    }
  }

  .btn {
    margin-top: 50px;
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    /deep/ .van-button {
      margin-bottom: 18px;
      width: 100%;
      line-height: 44px;
      height: 44px;
      background: #1691e3;
      border-radius: 8px;
      font-size: 16px;
    }
  }
}
</style>
