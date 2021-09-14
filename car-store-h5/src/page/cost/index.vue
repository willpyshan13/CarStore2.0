<template>
  <div class="cost">
    <!-- <subtitle btntxt="代驾费用"></subtitle> -->
    <div class="costCont">
      <div class="cont">
        <p>代驾费用详情</p>
        <div class="price">共<span>125.9</span>元</div>
        <p>费用按开始行驶时间计算，本单<span>21:15</span>开始行驶</p>
        <div class="jiage">
          <ul>
            <li>
              <b>起步价（含10公里）</b><span><i>36</i>元</span>
            </li>
            <li>
              <b>微信支付</b><span><i>36</i>元</span>
            </li>
          </ul>
          <p>计费规则&nbsp;&gt;</p>
        </div>
      </div>
    </div>

    <!-- <van-cell title="选择单个日期" :value="date" @click="show = true" />
    <van-calendar v-model="show" @confirm="onConfirm" /> -->
    <dl-foot></dl-foot>
  </div>
</template>

<script>
import subtitle from "../../components/subtitle.vue";
export default {
  components: { subtitle },
  name: "cost",
  data() {
    return {
      date: "",
      show: false,
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
.cost {
  font-size: 16px;
  width: 100%;
  height: 100%;
  background: #edf5fb;
  .costCont {
    width: 100%;
    height: 100%;
    padding: 10px;
    box-sizing: border-box;
    background: #edf5fb;
    .cont {
      width: 100%;
      height: 291px;
      background: #ffffff;
      border-radius: 6px;
      padding: 20px;
      box-sizing: border-box;
      p {
        width: 100%;
        text-align: center;
        height: 17px;
        font-size: 12px;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #999999;
        line-height: 17px;
        span {
          font-size: 12px;
        }
      }
      .price {
        width: 100%;
        text-align: center;
        height: 48px;
        line-height: 48px;
        font-size: 14px;
        font-family: PingFangSC-Medium, PingFang SC;
        font-weight: 500;
        color: #090909;
        margin: 16px 0 8px 0;
        span {
          font-size: 34px;
        }
      }
      .jiage {
        margin-top: 24px;
        width: 100%;
        height: 120px;
        border-top: 1px solid #f1f1f1;
        padding-top: 10px;
        box-sizing: border-box;
        ul {
          width: 100%;
          li {
            width: 100%;
            height: 20px;
            line-height: 20px;
            margin: 10px 0;
            b {
              float: left;
              font-size: 14px;
              font-family: PingFangSC-Regular, PingFang SC;
              font-weight: 400;
              color: #090909;
            }
            span {
              font-size: 14px;
              font-family: PingFangSC-Regular, PingFang SC;
              font-weight: 400;
              color: #090909;
              float: right;
              i {
                font-style: normal;
              }
            }
          }
        }
        p {
          height: 17px;
          font-size: 12px;
          font-family: PingFangSC-Regular, PingFang SC;
          font-weight: 400;
          color: #999999;
          line-height: 17px;
          margin-top: 30px;
        }
      }
    }
  }
}
</style>
