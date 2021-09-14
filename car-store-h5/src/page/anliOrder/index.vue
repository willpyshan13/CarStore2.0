<template>
  <div class="anliOrder">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="aboutOrder">
            <div class="orderNum">订单号：<span>34343344333343</span></div>
            <div class="time">2021/01/12</div>
          </div>
          <div class="title">请问倒车时傻叉异响！原地打方向盘时会响一声！</div>
          <div class="main">
            你好，新车刹车异响的原因主要存在于刹车片与刹车盘
            之间摩擦系数不匹配，比如生锈异响这需要试车后才能 找……
          </div>
          <div class="payStatus">
            <div>已付款</div>
          </div>
        </li>
        <li>
          <div class="aboutOrder">
            <div class="orderNum">订单号：<span>34343344333343</span></div>
            <div class="time">2021/01/12</div>
          </div>
          <div class="title">请问倒车时傻叉异响！原地打方向盘时会响一声！</div>
          <div class="main">
            你好，新车刹车异响的原因主要存在于刹车片与刹车盘
            之间摩擦系数不匹配，比如生锈异响这需要试车后才能 找……
          </div>
          <div class="payStatus">
            <div>已付款</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "anliOrder",
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.anliOrder {
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
        .aboutOrder {
          width: 100%;
          display: flex;
          justify-content: space-between;
          margin-top: 15px;
          & > div {
            height: 20px;
            font-size: 14px;
            color: #666666;
            line-height: 20px;
          }
        }
        .title {
          font-size: 16px;
          color: #090909;
          line-height: 22px;
          margin: 12px 0;
        }
        .main {
          font-size: 14px;
          color: #666666;
          line-height: 20px;
          margin: 16px 0 6px 0;
        }
        .payStatus {
          width: 100%;
          height: 22px;
          font-size: 16px;
          color: #666666;
          line-height: 22px;
          margin-bottom: 20px;
          & > div {
            width: 100%;
            text-align: right;
          }
        }
      }
    }
  }
}
</style>
