<template>
  <div class="orderDetailEnd">
    <dl-title btntxt="编辑"></dl-title>
    <div class="cont">
      <h2>订单信息</h2>
      <div class="line"></div>
      <div class="title">
        美孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽孚机油/MOBIL 金美丽
      </div>
      <p>
        商品价格：￥ <span>30X2</span> 物流费用：￥<span>10</span>
        实际付款：￥70 <span></span>
      </p>
      <div class="baseDiv">
        <div class="div">
          <div class="divl">支付状态</div>
          <div class="divr">已支付</div>
        </div>
        <div class="div">
          <div class="divl">支付状态</div>
          <div class="divr">已支付</div>
        </div>
      </div>
      <div class="baseDiv">
        <h4>物流信息</h4>
        <div class="div">
          <div class="divl">物流地址</div>
          <div class="divr">上海市闵行区景西路56号302</div>
        </div>
        <div class="div">
          <div class="divl">收件人</div>
          <div class="divr">王芳</div>
        </div>
      </div>
      <div class="baseDiv">
        <h4>客户信息</h4>
        <div class="div">
          <div class="divl">联系方式</div>
          <div class="divr">张三 12456890993</div>
        </div>
      </div>
      <div class="baseDiv">
        <h4>服务信息</h4>
        <div class="div">
          <div class="divl">维修技师</div>
          <div class="divr">张三 12456890993</div>
        </div>
        <div class="div">
          <div class="divl">车辆进店里程</div>
          <div class="divr">999999km</div>
        </div>
        <div class="div">
          <div class="divl">车辆出店里程</div>
          <div class="divr">999999km</div>
        </div>
      </div>
      <div class="baseDiv">
        <h4>评价信息</h4>
        <div class="div">
          <div class="divl">商品相符</div>
          <div class="divr"><van-rate v-model="value" /></div>
        </div>
        <div class="div">
          <div class="divl">服务态度</div>
          <div class="divr"><van-rate v-model="startVal" /></div>
        </div>
      </div>

      <div class="beizhu">
        <h3>备注信息</h3>
        <div class="add">
          修改物流地址：<span>上海市闵行区景西路56号302</span>
        </div>
      </div>
      <div class="btn">
        <van-button type="info">同意退款</van-button>
        <van-button type="default">完成订单</van-button>
        <p>
          请在确认退货收到以及与客户协商完成后点击同意退款
          点击完成订单后需客户确认完成或7天该订单自动修改为完成状态
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "orderDetailEnd",
  data() {
    return {
      value: 1,
      startVal: 1,
    };
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.orderDetailEnd {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    .line {
      width: 100%;
      height: 1px;
      background: #f1f1f1;
      margin: 13px 0 20px 0;
    }
    .title {
      font-size: 16px;
      color: #090909;
      line-height: 22px;
    }
    p {
      height: 17px;
      font-size: 12px;
      font-weight: 400;
      color: #666666;
      line-height: 17px;
      margin: 14px 0;
      span {
        font-size: 12px;
      }
    }
    .baseDiv {
      width: 100%;
      border-bottom: 1px solid #f1f1f1;
      h4 {
        height: 22px;
        font-size: 16px;
        font-weight: 500;
        color: #090909;
        line-height: 22px;
        margin: 20px 0;
      }
      .div {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        height: 22px;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
        margin-bottom: 20px;

        .divl {
          width: 30%;
          text-align: left;
        }
        .divr {
          width: 70%;
          text-align: right;
        }
      }
    }
    .beizhu {
      width: 100%;
      h3 {
        height: 22px;
        font-size: 16px;
        font-weight: 500;
        color: #090909;
        margin: 20px 0;
        line-height: 22px;
      }
      .add {
        height: 22px;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
      }
    }
    .btn {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      margin-top: 30px;
      /deep/ .van-button {
        width: 48%;
        &:first-of-type {
          margin-right: 4%;
        }
      }
      p {
        margin: 20px 0;
        font-size: 12px;
        color: #999999;
        line-height: 17px;
      }
    }
  }
}
</style>
