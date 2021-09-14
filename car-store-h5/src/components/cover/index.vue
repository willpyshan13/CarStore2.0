<template>
  <div class="cover-wrapper" @touchmove.prevent>
    <div class="cover">
      <div class="continu">
        <div class="btn" @click="gopay">查看全部</div>
      </div>
      <div class="intro">
        <div class="title">购买须知：</div>
        <div class="list">
          <div class="item">1. 购买后，仅支持线上查看，不可下载；</div>
          <div class="item">2. 此款商品为虚拟商品，不支持退款；</div>
          <div class="item">3. 最终解释权归本司所有。</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    gopay() {
      this.$emit("pay");
    }
  }
};
</script>

<style lang="less" scoped>
.cover-wrapper {
  position: fixed;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  z-index: 1000;
  .cover {
    position: absolute;
    left: 0.3rem;
    right: 0.3rem;
    top: calc(46px + 3.3rem);
    bottom: 0;
    z-index: 10;
    display: flex;
    flex-direction: column;
    .continu {
      width: 100%;
      height: 4.6rem;
      background: linear-gradient(
        180deg,
        rgba(255, 255, 255, 0) 0%,
        #ffffff 100%
      );
      .btn {
        width: 6.1rem;
        height: 0.88rem;
        background: #1691e3;
        border-radius: 0.16rem;
        text-align: center;
        line-height: 0.88rem;
        margin: 2.76rem auto 0;
        font-size: 0.32rem;
        font-weight: 500;
        color: #ffffff;
      }
    }
    .intro {
      padding-top: 0.38rem;
      flex: 1;
      background: #ffffff;
      .title {
        font-size: 0.32rem;
        color: #090909;
        line-height: 0.44rem;
      }
      .list {
        margin-top: 0.24rem;
        .item {
          font-family: PingFangSC-Regular, PingFang SC;
          font-weight: 400;
          font-size: 0.3rem;
          color: #666666;
          line-height: 0.42rem;
        }
      }
    }
  }
}
</style>
