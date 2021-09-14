
<template>
  <div class="buyServe">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="top">
        <div class="shop_name">世华汽车维修保养服务中心</div>
        <div class="by_name">Chemical Guys 漆面深层清洁</div>
        <div class="imgs">
          <img src="../../../static/images/1.png" alt="" />
          <img src="../../../static/images/2.png" alt="" />
          <img src="../../../static/images/3.png" alt="" />
          <img src="../../../static/images/3.png" alt="" />
        </div>
      </div>
      <div class="main">
        <van-tabs v-model="active" scrollspy sticky>
          <van-tab title="特价详情" class="tab1">
            <div class="about_price">
              <ul>
                <li>
                  <div class="about_price_l">化学小子两桶水</div>
                  <div class="about_price_r">
                    <span class="num">2份</span>
                    <span class="price">188.00元</span>
                  </div>
                </li>
                <li>
                  <div class="about_price_l">化学小子漆面深层清洁</div>
                  <div class="about_price_r">
                    <span class="num">2份</span>
                    <span class="price">188.00元</span>
                  </div>
                </li>
                <li>
                  <div class="about_price_l"></div>
                  <div class="about_price_r">
                    <span class="num">总价</span>
                    <span class="price">988.00元</span>
                  </div>
                </li>
              </ul>
            </div>
            <div class="imgTxt">
              <div class="title">图文详情</div>
              <img src="../../../static/images/3.png" alt="" />
              <div>
                决定是否获得丰厚方法和好看的回复快乐和卢卡斯的我i饿哦我挥挥手和发哦i谁给我哦欸和沃尔等候i大哥
              </div>
            </div>
          </van-tab>
          <van-tab title="购买须知" class="tab2">
            <div class="title">购买须知</div>
            <div class="miaoshu">
              <div class="baseDiv">
                <div class="biaoti">有效期</div>
                <div class="neirong">购买后90天内有效</div>
              </div>
              <div class="baseDiv">
                <div class="biaoti">预约信息</div>
                <div class="neirong">
                  无需预约，如遇消费高峰时段您可能需要排队
                </div>
              </div>
            </div>
            <div class="about_address">
              <div class="title">适用门店</div>
              <div class="mendian">
                <div class="md_l">世化汽车维修保养服务中心</div>
                <div class="md_r">
                  <van-icon name="phone-o" /><span>电话</span>
                </div>
              </div>
              <div class="star">
                <van-icon name="star" color="#F76565" /><span>4.72分</span>
              </div>
              <div class="address">
                <van-icon name="map-marked" color="#666666" />
                <span>江月路1500号7栋5-8号</span>
              </div>
            </div>
          </van-tab>
          <van-tab title="网友评价" class="tab3">
            <div class="title">精选评价（40）</div>
            <van-row type="flex" justify="space-between">
              <van-col span="12">
                <img src="../../../static/images/3.png" alt="" />
                <div style="float: left">
                  <p>罗师傅</p>
                  <p>
                    <van-icon name="star" color="#F76565" /><span>4.72分</span>
                  </p>
                </div>
              </van-col>
              <van-col span="6">
                <div class="time">2020.12.13</div>
              </van-col>
            </van-row>
            <div class="pinglun">
              洗了也好几年，当家经理也换了一个。不变
              的是，还是热情认真。洗的也干净，主要还 比隔壁嘉里城市的方便…
            </div>
          </van-tab>
        </van-tabs>
      </div>
    </div>
    <div class="buy">
      <div class="price"><i>￥</i><span>288</span></div>
      <van-button type="info" @click="gobuy">立即购买</van-button>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
export default {
  name: "buyServe",
  data() {
    return {
      active: 0,
    };
  },
  created() {},
  mounted() {},
  methods: {
    gobuy() {},
  },
};
</script>
<style lang="less" scoped>
.buyServe {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    .top {
      width: 100%;
      height: 133px;
      .shop_name {
        height: 22px;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
      }
      .by_name {
        height: 25px;
        font-size: 18px;
        font-weight: 500;
        color: #090909;
        line-height: 25px;
        margin: 6px 0 16px 0;
      }
      .imgs {
        width: 100%;
        height: 63px;
        img {
          float: left;
          height: 63px;
          width: 23.5%;
          margin-right: 2%;
          &:last-of-type {
            margin-right: 0;
          }
        }
      }
    }
    .main {
      margin-top: 50px;
      width: 100%;
      /deep/ .van-tabs__wrap {
        margin-bottom: 15px;
      }
      /deep/ .van-tabs {
        .van-tabs__line {
          background-color: #1691e3;
          width: 14px;
          height: 4px;
          margin-left: -2px;
        }
        .van-tabs__content {
          .tab1 {
            .about_price {
              width: 100%;
              height: auto;
              border-top: 1px solid #f1f1f1;
              border-bottom: 1px solid #f1f1f1;
              padding: 12px 0 8px 0;
              ul {
                li {
                  margin-bottom: 10px;
                  display: flex;
                  justify-content: space-between;
                  .about_price_l {
                    height: 17px;
                    font-size: 12px;
                    color: #666666;
                    line-height: 17px;
                  }
                  .about_price_r {
                    span {
                      height: 17px;
                      font-size: 12px;
                      color: #090909;
                      line-height: 17px;
                      &:last-of-type {
                        margin-left: 25px;
                      }
                    }
                  }
                }
              }
            }
            .imgTxt {
              width: 100%;
              height: auto;
              padding: 6px 0;
              .title {
                text-align: center;
                height: 50px;
                line-height: 50px;
                font-size: 16px;
                color: #090909;
              }
              img {
                margin-bottom: 30px;
              }
            }
          }
          .tab2 {
            width: 100%;
            margin-top: 40px;
            .title {
              height: 22px;
              font-size: 16px;
              color: #090909;
              line-height: 22px;
            }
            .miaoshu {
              padding-bottom: 20px;
              .baseDiv {
                width: 100%;
                .biaoti {
                  height: 17px;
                  font-size: 12px;
                  color: #666666;
                  line-height: 17px;
                  margin-top: 6px;
                }
                .neirong {
                  color: #090909;
                  margin-top: 6px;
                  height: 17px;
                  font-size: 12px;
                  line-height: 17px;
                }
              }
            }
            .about_address {
              width: 100%;
              padding: 20px 0;
              border-top: 1px solid #f1f1f1;
              border-bottom: 1px solid #f1f1f1;
              .mendian {
                height: 20px;
                display: flex;
                justify-content: space-between;
                .title {
                  margin: 6px 0;
                }
                .md_l {
                  font-size: 14px;
                  color: #090909;
                  line-height: 20px;
                }
                .md_r {
                  line-height: 20px;
                  /deep/ .van-icon {
                    vertical-align: middle;
                  }
                  span {
                    font-size: 12px;
                    color: #666666;
                    margin-left: 5px;
                  }
                }
              }
              .star {
                width: 100%;
                height: 20px;
                line-height: 20px;
                span {
                  font-size: 12px;
                  font-weight: 500;
                  color: #f76565;
                  margin-left: 10px;
                }
                /deep/ .van-icon {
                  vertical-align: middle;
                }
              }
              .address {
                margin-top: 8px;
                width: 100%;
                height: 20px;
                line-height: 20px;
                /deep/ .van-icon {
                  vertical-align: middle;
                }
                span {
                  font-size: 12px;
                  color: #666666;
                  margin-left: 10px;
                }
              }
            }
          }
          .tab3 {
            width: 100%;
            .title {
              height: 62px;
              line-height: 62px;
              font-size: 16px;
              font-weight: 500;
              color: #090909;
            }
            /deep/ .van-row {
              .van-col {
                &:first-of-type {
                  img {
                    width: 48px;
                    height: 48px;
                    border-radius: 50%;
                    float: left;
                    margin-right: 14px;
                  }
                  & > div {
                    p {
                      &:first-of-type {
                        height: 20px;
                        font-size: 14px;
                        font-weight: 500;
                        color: #090909;
                        line-height: 20px;
                        margin: 3px 0;
                      }
                      &:last-of-type {
                        height: 20px;
                        line-height: 20px;
                        display: flex;
                        align-items: center;
                        span {
                          font-size: 12px;
                          font-weight: 500;
                          color: #f76565;
                          margin-left: 5px;
                        }
                      }
                    }
                  }
                }
                &:last-of-type {
                  height: 48px;
                  line-height: 48px;
                  .time {
                    text-align: right;
                    font-size: 10px;
                    color: #666666;
                  }
                }
              }
            }
            .pinglun {
              height: auto;
              font-size: 15px;
              font-family: PingFangSC-Regular, PingFang SC;
              font-weight: 400;
              color: #090909;
              line-height: 21px;
              margin: 7px 0 0 60px;
            }
          }
        }
      }
    }
  }
  .buy {
    margin: 140px 0 80px 0;
    width: 100%;
    height: 44px;
    line-height: 44px;
    border-top: 1px solid #f1f1f1;
    display: flex;
    justify-content: space-between;
    padding-bottom: 30px;
    .price {
      margin-left: 15px;
      color: #090909;
      i {
        font-size: 12px;
        font-style: normal;
      }
      span {
        font-size: 18px;
        font-weight: 500;
      }
    }
    /deep/ .van-button {
      font-size: 14px;
    }
  }
}
</style>

