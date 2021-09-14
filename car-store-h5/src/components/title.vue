<template>
  <div class="title-wrapper">
    <van-row class="m-header">
      <van-col span="24">
        <div>
          <van-icon name="arrow-left" class="m-header-icon" @click="back" />
        </div>
        <div class="title">{{ title }}</div>
        <div class="btn themecolor" v-if="btntxt" @click="btntap">
          {{ btntxt }}
          <!-- <van-icon name="chat-o" dot size="25" /> -->
        </div>
      </van-col>
    </van-row>
  </div>
</template>

<script>
export default {
  name: "dl-title",
  props: {
    btntxt: {
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
    back() {
      //   this.$router.go(-1);
      //   window.js_android.finishPage();
      this.finishPage();
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
  background: #fff;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99999;
  .m-header {
    width: 100%;
    padding: 0 15px;
    height: 50px;
    line-height: 50px;
    background: #fff;
    color: #090909;
  }

  .m-header-icon {
    position: absolute;
    top: 12px;
    left: 6px;
    font-size: 26px;
  }
  .title {
    width: 100%;
    text-align: center;
    font-size: 18px;
    font-weight: 400;
    color: #090909;
  }
  .btn {
    position: absolute;
    top: 0;
    right: 16px;
    font-size: 18px;
    line-height: 50px;
    i {
      margin-top: 13px;
    }
  }
}
</style>
