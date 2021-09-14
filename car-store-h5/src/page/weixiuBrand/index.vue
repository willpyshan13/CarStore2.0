<template>
  <div class="weixiuBrand">
    <dl-title btntxt="" :flag="flag"></dl-title>
    <div class="cont">
      <div class="search">
        <van-search v-model="searVal" placeholder="请输入搜索关键词" />
      </div>
      <div class="brand-title">已选：</div>
      <div class="itemList">
        <div class="item" v-for="(it, ind) in clickAddList" :key="ind">
          {{ it.name }} <span @click="deleteItem(ind)">x</span>
        </div>
      </div>
      <div class="brand-title">品牌：</div>
      <ul>
        <li
          v-for="(item, index) in pinpaiType"
          :key="index"
          @click="liClick(item)"
        >
          <div class="lable">{{ item.name }}</div>
        </li>
      </ul>
      <div class="btn">
        <van-button type="info" @click="addBrand()">确认维修品牌</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
import managerApi from "@/api/manager";
export default {
  name: "weixiuBrand",
  data() {
    return {
      searVal: "",
      bankName: "",
      account: "",
      openBank: "",
      pinpai_type: [],
      clickAddList: [],
      flag: "1",
    };
  },
  created() {
    this.getPinpai();
  },
  activated() {
    // console.log(this.$route.query.newPinpais, "带过来品牌");
  },
  methods: {
    // 获取品牌类型
    getPinpai() {
      accountApi.carType().then((res) => {
        if (res.code == "0000") {
          const pipaiData = res.data;
          //   console.log(pipaiData);
          const new_data = pipaiData.map((x) => {
            x.name = x.configName;
            return x;
          });
          const new_data1 = new_data.map((y) => {
            y.id = y.uuid;
            return y;
          });
          this.pinpai_type = new_data1;
          this.clickAddList = pipaiData.filter((x) => {
            return JSON.parse(this.$route.query.newPinpais).includes(x.name);
          });
        }
      });
    },
    liClick(i) {
      //   this.searVal = i.name;
      this.searVal = "";
      if (this.clickAddList.length < 5) {
        this.clickAddList.push(i);
      } else {
        this.$toast.fail("最多只能选择5个品牌！");
      }
      let obj = {}; // 去重
      this.clickAddList = this.clickAddList.reduce((cur, next) => {
        obj[next.id] ? "" : (obj[next.id] = true && cur.push(next));
        return cur;
      }, []);
      return this.clickAddList;
    },
    //删除
    deleteItem(index) {
      console.log(index, "index");
      this.clickAddList.splice(index, 1);
    },

    addBrand() {
      const addList = this.clickAddList;
      this.$router.replace({
        name: this.$route.query.urlName,
        query: { addList: JSON.stringify(addList), from: "clickbtn" }, // 主页面  如果发现来自点击按钮  就刷新页面  其他方式进入主页面不刷新品牌数据
        params: { id: this.$route.query.urlId },
      });
      //   this.$router.push({ name: "jishiRgister", params: addList });
    },
  },
  deactivated() {
    //清除keep-alive的缓存
    this.$destroy(true);
  },
  computed: {
    pinpaiType() {
      console.log(this.clickAddList, "clickAddList");
      return this.pinpai_type.filter(
        (item) =>
          item.name.includes(this.searVal) &&
          !this.clickAddList.map((x) => x.name).includes(item.name)
      );
    },
  },
};
</script>

<style lang="less" scoped>
.brand-title {
  padding: 15px;
}

.weixiuBrand {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    // padding: 0 15px;
    box-sizing: border-box;
    border-top: 1px solid #f1f1f1;
    .search {
      height: 40px;
      width: 100%;
      margin: 10px 0 25px 0;
      /deep/ .van-search {
        height: 40px;
        .van-cell {
          padding: 0;
          height: 40px;
          line-height: 40px;
          .van-field__control {
            color: #090909;
          }
        }
      }
    }
    ul {
      width: 100%;
      margin-bottom: 20px;
      padding-bottom: 134px;
      li {
        height: 44px;
        line-height: 44px;
        padding: 0px 15px;
        box-sizing: border-box;
        display: flex;
        width: 100%;
        justify-content: space-between;
        border-bottom: 1px solid #f1f1f1;
        &:first-child {
          border-top: 1px solid #f1f1f1;
        }
        .lable {
          width: 80%;
          font-size: 16px;
          color: #666666;
        }
      }
    }
    .btn {
      position: fixed;
      left: 0;
      bottom: 0;
      background: #fff;
      padding: 20px 0 50px;
      width: 100%;
      text-align: center;
      /deep/ .van-button {
        width: 220px;
        height: 44px;
        background: #1691e3;
        border-radius: 8px;
        font-size: 16px;
        font-weight: 500;
        color: #ffffff;
      }
    }
    .itemList {
      padding: 0 15px;
      box-sizing: border-box;
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      .item {
        // min-width: 31%;
        padding: 0 20px;
        box-sizing: border-box;
        height: 44px;
        line-height: 44px;
        text-align: center;
        font-size: 16px;
        color: #090909;
        background: #f6f7f8;
        border-radius: 8px;
        margin: 0 3.5% 20px 0;
        position: relative;

        span {
          display: inline-block;
          width: 18px;
          height: 18px;
          line-height: 15px;
          border-radius: 50%;
          text-align: center;
          color: #fff;
          background: #000000;
          position: absolute;
          right: 0;
          top: 0;
        }
      }
    }
  }
}
</style>
