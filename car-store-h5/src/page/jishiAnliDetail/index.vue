<template>
  <div class="jishiAnliDetail">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="title">{{ title }}</div>
          <div class="ietm">
            <div class="main">
              {{ faultDesc }}
            </div>
            <div class="imageOrVideo">
              <van-image src="../../../static/images/1.png" />
            </div>
          </div>
          <div class="ietm">
            <div class="main">
              {{ ideaProcess }}
            </div>
            <div class="imageOrVideo">
              <van-image src="../../../static/images/1.png" />
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
  name: "jishiAnliDetail",
  data() {
    return {
      uuid: "",
      title: "",
      faultDesc: "",
      ideaProcess: "",
    };
  },
  created() {
    this.getDetail();
  },
  activated() {
    if (this.$route.query && this.$route.query.Uuid) {
      this.uuid = JSON.parse(this.$route.query.Uuid);
      this.getDetail();
    }
  },
  methods: {
    getDetail() {
      let canshu = this.uuid;
      orderApi.anli_detail(canshu).then((res) => {
        if (res.code == "0000") {
          console.log(res.data);
          this.title = res.data.title;
          this.faultDesc = res.data.faultDesc;
          this.ideaProcess = res.data.ideaProcess;
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.jishiAnliDetail {
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
