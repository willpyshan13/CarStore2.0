<template>
  <div class="home">
    <dl-title btntxt="完成"></dl-title>
    <van-pull-refresh v-model="isLoading" @refresh="onRefresh">
      <div style="margin: 60px 0">
        <van-button type="primary" @click="$router.push({ name: 'myOrder' })"
          >我的（切换）</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'anliDetail' })"
          >案例详情（线上服务）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'onlineDetail' })"
          >线上咨询详情（线上服务）</van-button
        >
        <!-- <van-button type="primary" @click="$router.push({ name: 'anlifabu' })"
        >案例发布</van-button
      > -->
        <van-button
          type="primary"
          @click="$router.push({ name: 'orderDetailEnd' })"
          >订单详情完成（我的）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'orderDetailEdit' })"
          >订单详情编辑（我的）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'questionAns' })"
          >回答问题(线上服务)</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'gerenxinxi' })"
          >我的个人信息</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'shopCenter' })"
          >（店铺）个人信息</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'wallet' })"
          >钱包</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'orderList' })"
          >订单列表</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'daijiaOrder' })"
          >代驾订单</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'baoyang' })"
          >维修保养</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'payOrder' })"
          >支付订单</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'anliOrder' })"
          >案例订单</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'jishiWenDa' })"
          >技师问答</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'jishiAnliDetail' })"
          >技师案例详情</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'accountInfor' })"
          >账户信息(店铺)</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'accountInforJishi' })"
          >账户信息(技师)</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'relevancyShop' })"
          >关联店铺</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'jishiAdmin' })"
          >技师管理（我的）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'putawayProduct' })"
          >上架商品</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'putawayProductDetail' })"
          >上架商品详情</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'putawayProductType' })"
          >上架商品(分类)</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'putawayProductDetail' })"
          >上架商品详情</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'fabuProduct' })"
          >上架发布商品（线上服务）</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'fabuAnli' })"
          >发布案例（商品管理）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'daijiaDetail' })"
          >代驾详情（代驾模块）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'jishiRgister' })"
          >技师注册</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'shopRgister' })"
          >店铺注册</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'weixiuBrand' })"
          >维修品牌（模糊搜索）</van-button
        >
        <!-- <van-button
        type="primary"
        @click="$router.push({ name: 'relevancyShop1' })"
        >关联店铺（代驾模块）</van-button
      > -->
        <van-button type="primary" @click="$router.push({ name: 'selectShop' })"
          >选择店铺（代驾模块）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'selectShopName' })"
          >选择店铺（店铺选择）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'dtcAddOrEdit' })"
          >DTC新增编辑</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'dtcDetail' })"
          >DTC详情</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'dtcList' })"
          >DTC列表</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'dtcDetailList' })"
          >DTC列表详情</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'loading' })"
          >加载页面</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'scenceOrder' })"
          >现场支持下单（新增编辑）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'scenceOrderDetail' })"
          >现场支持下单（详情）</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'jiaoyuDetail' })"
          >详情</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'kechengDetail' })"
          >课程详情</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'trainingNeed' })"
          >培训需求</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'myCourse' })"
          >我的课程</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'myjiaocheng' })"
          >我的教程</van-button
        >
        <van-button type="primary" @click="$router.push({ name: 'buyServe' })"
          >购买服务</van-button
        >
        <van-button
          type="primary"
          @click="$router.push({ name: 'shareJsDetail' })"
          >共享技师详情</van-button
        >
        <upload-img
          :max-count="5"
          :imgType="2"
          :imgs="[]"
          @getAll="getAll"
        ></upload-img>
      </div>

      <van-field-checkbox
        label="多选checkbox"
        placeholder="请选择"
        v-model="value2"
        :columns="columns"
        label-width="100"
        :option="{ label: 'name', value: 'code' }"
        @confirm="confirm"
      />
      <!-- 轮播 -->
      <div class="swiper">
        <van-swipe :autoplay="3000">
          <van-swipe-item v-for="(image, index) in images" :key="index">
            <img :src="image" />
          </van-swipe-item>
        </van-swipe>
      </div>
      <div class="map">
        <img src="../../../static/images/3.png" alt="" />
        <div class="askAndPosition">
          <div class="ask">
            <img src="../../../static/images/1.png" alt="" />
            <span @click="$router.push({ name: 'my' })"
              >平台已要求司机全程佩戴口罩</span
            >
          </div>
          <div class="position">
            <img src="../../../static/images/2.png" alt="" />
          </div>
        </div>
      </div>
      <div class="gomsg">
        <div class="top">
          <img src="../../../static/images/daijia1.png" alt="" />
          <span>代驾</span>
          <span>不辜负每一程相遇</span>
        </div>
        <div class="cont">
          <van-tabs v-model="active">
            <van-tab title="现在">
              <div class="infor">
                <div class="chufa">
                  <i></i>从<span>欧阳图文快印</span>&gt;&nbsp;出发
                </div>
                <div class="mudidi">
                  <i></i>
                  <van-field v-model="value" placeholder="请输入你的目的地" />
                </div>
              </div>
            </van-tab>
            <van-tab title="预约">预约</van-tab>
          </van-tabs>
        </div>
      </div>
      <!-- <van-cell title="选择单个日期" :value="date" @click="show = true" />
    <van-calendar v-model="show" @confirm="onConfirm" /> -->
      <dl-foot></dl-foot>
    </van-pull-refresh>
  </div>
</template>

<script>
import uploadImg from "../../components/upload-img.vue";
import vanFieldCheckbox from "../../components/van-field-checkbox.vue";
export default {
  components: { uploadImg, vanFieldCheckbox },
  name: "home",
  data() {
    return {
      isLoading: false,
      date: "",
      show: false,
      images: [
        "../../../static/images/1.png",
        "../../../static/images/2.png",
        "../../../static/images/3.png",
      ],
      active: 0,
      value: "",
      value2: ["1"], // checkbox选中的value
      columns: [
        // 如果可选数据不是label-value,需要配置下option，如果是就不需要配置
        { name: "我是选中的label", code: "1", other: "额外数据" },
        { name: "我也是选中的label33333", code: "2", other: "额外数据" },
        { name: "我是选中的label", code: "21", other: "额外数据" },
        { name: "我也是选中的label555555555", code: "22", other: "额外数据" },
        { name: "我是选中的label", code: "11", other: "额外数据" },
        { name: "我也是选中的label", code: "52", other: "额外数据" },
        { name: "我是选中的label", code: "71", other: "额外数据" },
        { name: "我也是选中的label", code: "72", other: "额外数据" },
      ],
    };
  },
  methods: {
    onRefresh() {
      setTimeout(() => {
        this.$toast.success("刷新成功");
        this.isLoading = false;
      }, 1000);
    },
    formatDate(date) {
      return `${date.getMonth() + 1}/${date.getDate()}`;
    },
    onConfirm(date) {
      this.show = false;
      this.date = this.formatDate(date);
    },
    getAll(v) {
      console.log(v, "vvvvvvvvvvvvvv");
    },
    confirm(data1, data2) {
      // select确定,
      // tips 正常获取值，用不到这个方法，用v-model获取值即可，这个方法是告诉你，可以获取任何你想要的数据
      // data1 是当前选中数据的value的数组
      // data2 是当前选中数据的整个obj集合
      console.log(data1, data2);
      this.value3 = data2;
    },
  },
};
</script>

<style lang="less" scoped>
.home {
  font-size: 16px;
  .swiper {
    width: 100%;
    height: 88px;
    .van-swipe {
      width: 100%;
      height: 100%;
    }
    img {
      width: 100%;
      height: 100%;
    }
    /deep/ .van-swipe__indicators {
      padding: 2px 10px;
      background: rgba(0, 0, 0, 0.2);
      border-radius: 5px;
      bottom: 6px;
      .van-swipe__indicator--active {
        background: #fff;
      }
    }
  }
  .map {
    width: 100%;
    height: 300px;
    position: relative;
    img {
      width: 100%;
      height: 100%;
    }
    .askAndPosition {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      height: 38px;
      position: absolute;
      bottom: 10px;
      .ask {
        float: left;
        width: 214px;
        height: 35px;
        line-height: 35px;
        background: #f6fcfa;
        box-shadow: 0px 2px 4px 0px rgba(199, 204, 218, 0.17);
        border-radius: 10px;
        img {
          width: 35px;
          height: 35px;
          border-radius: 10px;
          float: left;
          margin-right: 8px;
        }
        span {
          font-size: 12px;
          font-family: PingFangSC-Medium, PingFang SC;
          font-weight: 500;
          color: #4f8a76;
        }
      }
      .position {
        float: right;
        width: 38px;
        height: 38px;
        border-radius: 50%;
        background: #ffffff;
        display: flex;
        justify-content: center;
        align-items: center;
        img {
          width: 25px;
          height: 25px;
          border-radius: 50%;
        }
      }
    }
  }
  .gomsg {
    width: 100%;
    height: 255px;
    border-radius: 10px 10px 0px 0px;
    background: #edf5fb;
    box-sizing: border-box;
    .top {
      padding: 0 10px;
      height: 40px;
      line-height: 40px;
      background: #edf5fb;
      box-shadow: 0px -4px 27px 0px rgba(58, 102, 250, 0.11);
      border-radius: 10px 10px 0px 0px;
      box-sizing: border-box;
      img {
        width: 17px;
        height: 20px;
        vertical-align: sub;
      }
      span {
        font-size: 14px;
        font-family: PingFangSC-Medium, PingFang SC;
        font-weight: 500;
        color: #1684e3;
        &:first-of-type {
          font-size: 18px;
          margin: 0 20px 0 9px;
        }
      }
    }
    .cont {
      width: 100%;
      padding: 0 10px;
      box-sizing: border-box;
      height: 215px;
      /deep/ .van-tabs__wrap {
        width: 102px;
        .van-tabs__nav {
          background: #edf5fb;
          margin-left: -10px;
        }
        .van-tab {
          padding: 0;
          span {
            font-size: 16px;
            font-family: PingFangSC-Regular, PingFang SC;
            color: #090909;
          }
        }
        .van-tab--active {
          span {
            font-size: 18px;
            font-weight: 600;
          }
        }
        .van-tabs__line {
          width: 14px;
          height: 4px;
          background: #1684e3;
          border-radius: 2px;
        }
      }
      /deep/ .van-tabs__content {
        margin-top: 10px;
        .infor {
          width: 100%;
          height: 146px;
          background: #ffffff;
          border-radius: 8px;
          padding: 25px 15px;
          box-sizing: border-box;
          .chufa {
            width: 100%;
            height: 22px;
            line-height: 22px;
            font-size: 16px;
            font-family: PingFangSC-Regular, PingFang SC;
            color: #090909;
            margin-bottom: 20px;
            i {
              display: inline-block;
              width: 4px;
              height: 4px;
              border-radius: 50%;
              box-shadow: 0 0 0 3px #1684e3;
              margin: -4px 15px 0 15px;
              vertical-align: middle;
            }
            span {
              color: #1684e3;
              margin: 0 5px;
            }
          }
          .mudidi {
            width: 100%;
            position: relative;
            i {
              display: inline-block;
              width: 4px;
              height: 4px;
              border-radius: 50%;
              box-shadow: 0 0 0 3px #00c47a;
              position: absolute;
              left: 15px;
              z-index: 1;
              top: 25px;
            }
            /deep/ .van-cell {
              width: 100%;
              height: 54px;
              line-height: 36px;
              background: #edf5fb;
              border-radius: 8px;
              font-size: 20px;
              font-family: PingFangSC-Semibold, PingFang SC;
              font-weight: 600;
              color: #090909;
              input {
                text-indent: 15px;
              }
            }
          }
        }
      }
    }
  }
}
</style>
