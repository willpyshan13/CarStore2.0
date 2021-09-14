<template>
  <div class="baoyang">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="num">
        <div>
          <span>2</span>
          <p>已经完成</p>
        </div>
        <div>
          <span>2</span>
          <p>未付款</p>
        </div>
        <div>
          <span>2</span>
          <p>已付款未完成</p>
        </div>
        <div>
          <span>2</span>
          <p>已退款</p>
        </div>
      </div>
      <ul>
        <li>
          <div class="title">
            美孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽
          </div>
          <div class="detail">
            商品价格：￥ <span>30X2</span> 物流费用：￥<span>10</span>
            实际付款：￥<span>70</span>
          </div>
          <div class="status">
            <div>张三 13245678987</div>
            <div>付款状态 <span>已付款</span></div>
          </div>
        </li>
        <li>
          <div class="title">
            美孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽
          </div>
          <div class="detail">
            商品价格：￥ <span>30X2</span> 物流费用：￥<span>10</span>
            实际付款：￥<span>70</span>
          </div>
          <div class="status">
            <div>张三 13245678987</div>
            <div>付款状态 <span>已付款</span></div>
          </div>
        </li>
        <li>
          <div class="title">
            美孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽
          </div>
          <div class="detail">
            商品价格：￥ <span>30X2</span> 物流费用：￥<span>10</span>
            实际付款：￥<span>70</span>
          </div>
          <div class="status">
            <div>张三 13245678987</div>
            <div>付款状态 <span>已付款</span></div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "baoyang",
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.baoyang {
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
    .num {
      padding: 0 15px;
      box-sizing: border-box;
      width: 100%;
      height: 100px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      & > div {
        text-align: center;
        span {
          font-size: 24px;
          font-weight: 500;
          color: #f76565;
          line-height: 33px;
        }
        p {
          margin-top: 7px;
          font-size: 14px;
          color: #090909;
        }
      }
    }
    ul {
      width: 100%;
      li {
        margin-bottom: 10px;
        width: 100%;
        height: 166px;
        background: #fff;
        border-radius: 8px;
        padding: 5px 15px;
        box-sizing: border-box;
        margin-bottom: 10px;
        .title {
          font-size: 16px;
          color: #090909;
          line-height: 22px;
          margin: 15px 0;
        }
        .detail {
          height: 17px;
          font-size: 12px;
          font-family: PingFangSC-Regular, PingFang SC;
          font-weight: 400;
          color: #666666;
          line-height: 17px;
        }
        .status {
          margin-top: 20px;
          height: 50px;
          line-height: 50px;
          border-top: 1px solid #f1f1f1;
          display: flex;
          justify-content: space-between;
          & > div {
            font-size: 16px;
            color: #666666;
          }
        }
      }
    }
  }
}
</style>
