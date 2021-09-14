<template>
  <div class="title-wrapper">
    <van-row class="m-header">
      <van-col span="24">
        <van-icon name="arrow-left" class="m-header-icon" @click="back(flag)" />
        <div @click="btntap()">{{ btntxt }}</div>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  name: "subtitle",
  props: {
    btntxt: {
      type: String,
      default: "",
    },
    flag: {
      type: String,
      default: "",
    },
  },
  data() {
    return {};
  },
  computed: {
    title() {
      return this.$route.meta.title;
    },
  },
  methods: {
    back(flag) {
      if (flag) {
        this.router.back();
      } else {
        this.$router.go(-1);
      }
    },
    btntap() {
      this.$emit("btntap", this.btntxt);
    },
  },
};
</script>

<style lang="less" scoped>
.title-wrapper {
  width: 100%;
  background: #edf5fb;
  .m-header {
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    color: #090909;
  }

  .m-header-icon {
    position: absolute;
    top: 16px;
    left: 6px;
    font-size: 18px;
  }

  //   .btn {
  //     position: absolute;
  //     top: 0;
  //     right: 16px;
  //     font-size: 18px;
  //     line-height: 50px;
  //     i {
  //       margin-top: 13px;
  //     }
  //   }
}
</style>
