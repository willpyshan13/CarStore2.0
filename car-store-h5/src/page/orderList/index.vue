<template>
  <div class="orderList">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div>
            <img src="../../../static/images/3.png" alt="" />
            <span>代驾订单</span>
          </div>
          <div style="margin-right: 16px">
            <van-icon name="arrow" />
          </div>
        </li>
        <li>
          <div>
            <img src="../../../static/images/3.png" alt="" />
            <span>维修保养</span>
          </div>
          <div style="margin-right: 16px">
            <van-icon name="arrow" />
          </div>
        </li>
        <li>
          <div>
            <img src="../../../static/images/3.png" alt="" />
            <span>付费咨询</span>
          </div>
          <div style="margin-right: 16px">
            <van-icon name="arrow" />
          </div>
        </li>
        <li>
          <div>
            <img src="../../../static/images/3.png" alt="" />
            <span>技师案例</span>
          </div>
          <div style="margin-right: 16px">
            <van-icon name="arrow" />
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "orderList",
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.orderList {
  height: 100%;
  width: 100%;
  margin-top: 50px;
  background: #edf5fb;
  /deep/ .m-header {
    background: #edf5fb !important;
  }
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    ul {
      width: 100%;
      padding-top: 10px;
      li {
        margin-bottom: 10px;
        width: 100%;
        display: flex;
        justify-content: space-between;
        height: 80px;
        line-height: 80px;
        background: #ffffff;
        border-radius: 6px;
        img {
          width: 21px;
          height: 16px;
          margin: 0 13px 0 17px;
        }
        span {
          font-size: 16px;
          color: #090909;
        }
      }
    }
  }
}
</style>
