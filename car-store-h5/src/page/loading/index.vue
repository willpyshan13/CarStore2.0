<template>
  <div class="loading">
    <div v-if="load">
      <van-loading size="40px" vertical>加载中...</van-loading>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "loading",
  data() {
    return {
      load: true,
      uuid: "",
      timer: null,
    };
  },
  created() {
    this.timer = setInterval(() => {
      this.getData();
    }, 1000);
  },
  activated() {
    // alert(this.$route.query.uuid);
    this.uuid = this.$route.query.uuid;
  },
  methods: {
    getData() {
      this.load = true;
      let params = this.uuid;
      orderApi.allQingQiu(params).then((res) => {
        if (res.code == "0000") {
          if (res.data == true) {
            clearInterval(this.timer);
            this.load = false;
            this.$router.push({
              path: "/dtcDetail",
              query: { gobackdtcdetail: 1 },
            });
          } else if (res.data == true) {
            this.load = true;
            this.$toast.fail("支付失败，请返回重试");
          }
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.loading {
  height: 100%;
  text-align: center;
  width: 100%;
  overflow: hidden;
  background: #edf5fb;
  position: relative;
  /deep/ .van-loading {
    text-align: center;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
}
</style>
