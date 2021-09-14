<template>
  <div class="onlinDetail">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="title">{{ title }}</div>
          <div class="ietm">
            <div class="main">
              {{ answerDesc }}
            </div>
            <div class="imageOrVideo">
              <van-image
                v-for="(it, index) in answerList"
                :key="index"
                :src="it"
              />
            </div>
          </div>
          <div class="ietm">
            <div class="main">
              {{ consultDesc }}
            </div>
            <div class="imageOrVideo">
              <van-image
                v-for="(ite, index) in consultList"
                :key="index"
                :src="ite"
              />
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "onlinDetail",
  data() {
    return {
      uuid: "",
      title: "",
      answerDesc: "",
      consultDesc: "",
      answerList: [],
      consultList: [],
    };
  },
  created() {
    if (this.$route.query && this.$route.query.Uuid) {
      this.uuid = this.$route.query.Uuid;
      this.getDetail();
    }
  },
  activated() {
    if (this.$route.query && this.$route.query.Uuid) {
      this.uuid = this.$route.query.Uuid;
      this.getDetail();
    }
  },
  methods: {
    getDetail() {
      let canshu = this.uuid;
      orderApi.online_detail(canshu).then((res) => {
        if (res.code == "0000") {
          this.title = res.data.title;
          this.answerDesc = res.data.answerDesc;
          this.consultDesc = res.data.consultDesc;
          this.answerList = res.data.answerUrlList;
          this.consultList = res.data.consultImgUrlList;
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.onlinDetail {
  height: 100%;
  width: 100%;
  margin-top: 50px;
  background: #edf5fb;
  //   /deep/ .m-header {
  //     background: #edf5fb !important;
  //   }
  .cont {
    width: 100%;
    // padding: 0 15px;
    box-sizing: border-box;
    ul {
      width: 100%;
      padding: 15px 0;
      li {
        padding: 10px 15px;
        box-sizing: border-box;
        margin-bottom: 10px;
        width: 100%;
        background: #fff;
        padding: 5px 15px;
        box-sizing: border-box;
        .title {
          margin-top: 12px;
          width: 100%;
          font-size: 18px;
          color: #090909;
          line-height: 25px;
        }
        .ietm {
          margin-bottom: 14px;
          .main {
            font-size: 14px;
            color: #666666;
            line-height: 20px;
            margin: 16px 0 14px 0;
          }
          .imageOrVideo {
            width: 100%;
            height: 140px;
            /deep/ .van-image {
              width: 100%;
              height: 140px;
              img {
                width: 100%;
                height: 100%;
              }
            }
          }
        }
      }
    }
  }
}
</style>
