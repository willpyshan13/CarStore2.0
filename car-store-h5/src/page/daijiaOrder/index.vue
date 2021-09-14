<template>
  <div class="daijiaOrder">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="price"><b>￥</b><span>88.00</span></div>
          <div class="time">
            <div class="lable">出发时间: <span>及时</span></div>
            <div class="juli">15km</div>
          </div>
          <div class="baseDiv">
            <b>出发地:</b>
            <span>龙景花园111号南门</span>
          </div>
          <div class="baseDiv">
            <b>目的地:</b>
            <span>希尔顿酒店（徐汇区）交通大学11号</span>
          </div>
          <div class="baseDiv">
            <b>全程约:</b>
            <span>10km</span>
          </div>
        </li>
        <li>
          <div class="price"><b>￥</b><span>88.00</span></div>
          <div class="time">
            <div class="lable">出发时间: <span>及时</span></div>
            <div class="juli">15km</div>
          </div>
          <div class="baseDiv">
            <b>出发地:</b>
            <span>龙景花园111号南门</span>
          </div>
          <div class="baseDiv">
            <b>目的地:</b>
            <span>希尔顿酒店（徐汇区）交通大学11号</span>
          </div>
          <div class="baseDiv">
            <b>全程约:</b>
            <span>10km</span>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "daijiaOrder",
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.daijiaOrder {
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
      li {
        margin-bottom: 10px;
        width: 100%;
        height: 209px;
        background: rgba(255, 255, 255, 0.8);
        border-radius: 8px;
        padding: 5px 15px;
        box-sizing: border-box;
        margin-bottom: 10px;
        .price {
          width: 100%;
          height: 28px;
          font-weight: 500;
          color: #f76565;
          margin: 15px 0;
          b {
            font-size: 16px;
          }
          span {
            font-size: 20px;
          }
        }
        .time {
          width: 100%;
          display: flex;
          justify-content: space-between;
          .lable {
            font-size: 14px;
            color: #666666;
            span {
              margin-left: 13px;
              color: #090909;
              font-size: 14px;
            }
          }
          .juli {
            font-size: 14px;
            color: #666666;
          }
        }
        .baseDiv {
          width: 100%;
          margin: 14px 0;
          b {
            display: inline-block;
            width: 60px;
            margin-right: 13px;
            font-size: 14px;
            color: #666666;
            font-weight: 400;
          }
          span {
            font-size: 14px;
            color: #090909;
          }
        }
      }
    }
  }
}
</style>
