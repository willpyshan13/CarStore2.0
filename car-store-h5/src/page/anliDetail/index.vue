<template>
  <div class="anliDetail">
    <dl-title btntxt="编辑" @btntap="btntap"></dl-title>
    <div class="cont">
      <div class="num">
        <div class="shouyi">
          <h5>收益</h5>
          <div class="price">
            ¥<span>{{ earnings }}</span>
          </div>
          <p>
            价格：<span>{{ amt }}</span
            >元
          </p>
        </div>
        <div class="xiaoliang">
          <h5>销量</h5>
          <div class="price">
            <span>{{ salesVolume }}</span>
          </div>
          <p>
            发布时间：<span>{{ time }}</span>
          </p>
        </div>
      </div>
      <div class="success">
        <div class="item">
          <h4>成功案例</h4>
          <!-- <p>{{ ideaProcess }}</p>
          <div class="img" v-for="(item, index) in caseImgList" :key="index">
            <img :src="item" alt="" />
          </div> -->
          <h2 v-if="data.title" class="text-align: center; padding: 0 40rpx">
            {{ data.title }}
          </h2>
          <div v-if="data.faultDesc">
            <span style="font-weight: 900">故障现象：</span>
            {{ data.faultDesc }}
          </div>
          <div v-if="ideaProcess">
            <span style="font-weight: 900">诊断思路与过程：</span>
          </div>
          <div class="anli-item-content" v-html="ideaProcess"></div>
          <div v-if="data.summary">
            <span style="font-weight: 900">结论总结：</span> {{ data.summary }}
          </div>
          <div class="time">
            <span style="font-weight: 900">发布时间：</span>
            <span>{{ time }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "anliDetail",
  data() {
    return {
      time: "",
      earnings: "",
      salesVolume: "",
      amt: "",
      caseImgList: [],
      ideaProcess: "",
      data: {},
    };
  },
  created() {
    this.getEdit();
  },
  methods: {
    // 编辑获取接口
    getEdit() {
      let params = this.getUuid();
      //   let params = "86a3a74cac464dfaa3bdd50b61e58681";
      orderApi.editFabu(params).then((res) => {
        if (res.code == "0000") {
          this.data = res.data;
          this.earnings = res.data.earnings ? res.data.earnings : 0;
          this.amt = res.data.amt ? res.data.amt : 0;
          this.time = res.data.madeTime;
          this.salesVolume = res.data.salesVolume ? res.data.salesVolume : 0;
          this.caseImgList = res.data.caseImgList;
          this.ideaProcess = res.data.ideaProcess;
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
    btntap() {
      this.$router.push({
        path: "/fabuAnli",
        query: {
          flag: "10",
        },
      });
    },
  },
};
</script>

<style lang="less" scoped>
.anliDetail {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    .num {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      h5 {
        width: 100%;
        text-align: center;
        font-size: 14px;
        font-weight: 500;
        color: #666666;
      }
      .price {
        width: 100%;
        text-align: center;
        height: 40px;
        line-height: 40px;
        font-size: 16px;
        font-weight: 500;
        color: #090909;
        span {
          font-size: 28px;
        }
      }
      .shouyi {
        width: 48%;
        .price {
          color: #f76565;
        }
      }
      .xiaoliang {
        width: 52%;
      }
      p {
        height: 17px;
        font-size: 12px;
        color: #999999;
        line-height: 17px;
        span {
          font-size: 12px;
          color: #999999;
        }
      }
    }
    .success {
      margin-top: 20px;
      width: 100%;
      border-top: 1px solid #f1f1f1;
      .item {
        margin-top: 8px;
        & > div {
          margin: 15px 0;
        }
        h4 {
          height: 25px;
          font-size: 18px;
          font-weight: 600;
          color: #090909;
          line-height: 25px;
          margin: 12px 0;
        }
        p {
          font-size: 14px;
          font-weight: 400;
          color: #666666;
          line-height: 20px;
        }
        .img {
          width: 345px;
          margin: 12px 0;
          img {
            width: 100%;
            height: auto;
            border-radius: 8px;
          }
        }
        .time {
          height: 17px;
          font-size: 12px;
          font-weight: 400;
          color: #999999;
          line-height: 17px;
          span {
            font-size: 12px;
            font-weight: 400;
            color: #999999;
          }
        }
      }
    }
  }
}
</style>
