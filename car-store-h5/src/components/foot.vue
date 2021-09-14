<template>
  <van-tabbar route v-model="active">
    <van-tabbar-item replace to="/">
      <span>代驾</span>
      <img
        slot="icon"
        slot-scope="props"
        :src="props.active ? icon.active : icon.inactive"
      />
    </van-tabbar-item>
    <van-tabbar-item replace to="/" icon="home-o">
      <span>维修保养</span>
      <img
        slot="icon"
        slot-scope="props"
        :src="props.active ? icon.active1 : icon.inactive1"
      />
    </van-tabbar-item>
    <van-tabbar-item replace to="/" icon="home-o">
      <span>车友帮</span>
      <img
        slot="icon"
        slot-scope="props"
        :src="props.active ? icon.active2 : icon.inactive2"
      />
    </van-tabbar-item>
    <van-tabbar-item replace to="/my" icon="search">
      <span>我的</span>
      <img
        slot="icon"
        slot-scope="props"
        :src="props.active ? icon.active3 : icon.inactive3"
      />
    </van-tabbar-item>
  </van-tabbar>
</template>

<script>
export default {
  name: "foot",
  data() {
    return {
      active: 0,
      icon: {
        active: require("../../static/images/daijia1.png"),
        inactive: require("../../static/images/daijia.png"),
        active1: require("../../static/images/upkeep1.png"),
        inactive1: require("../../static/images/upkeep.png"),
        active2: require("../../static/images/carbang1.png"),
        inactive2: require("../../static/images/carbang.png"),
        active3: require("../../static/images/my1.png"),
        inactive3: require("../../static/images/my.png"),
      },
    };
  },
  methods: {
    formatDate(date) {
      return `${date.getMonth() + 1}/${date.getDate()}`;
    },
    onConfirm(date) {
      this.show = false;
      this.date = this.formatDate(date);
    },
  },
};
</script>

<style lang="less" scoped>
</style>
