<template>
  <div class="selectShop">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="search">全部</div>
      <ul>
        <li
          v-for="(item, index) in province"
          :key="index"
          @click="select(item.uuid)"
        >
          <div>{{ item.areaName }}</div>
          <div class="dayu"><van-icon name="arrow" /></div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import managerApi from "@/api/manager";
export default {
  name: "selectShop",
  data() {
    return {
      value: "",
      province: [],
    };
  },
  created() {
    this.getArea();
  },

  methods: {
    // 获取地区
    getArea() {
      managerApi.getOneJiArea().then((res) => {
        if (res.code == "0000") {
          this.province = res.data;
        }
      });
    },
    select(uuid) {
      this.$router.push({
        path: "/selectShopName",
        query: {
          uuid: uuid,
          urlName: this.$route.name,
          urlId: (this.$route.params && this.$route.params.id) || "",
        },
      });
    },
  },
};
</script>

<style lang="less" scoped>
.selectShop {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;

    border-top: 1px solid #f1f1f1;
    .search {
      width: 100%;
      padding: 0 15px;
      box-sizing: border-box;
      height: 38px;
      font-size: 14px;
      color: #666666;
      line-height: 38px;
      background: #edf5fb;
    }
    ul {
      width: 100%;
      box-sizing: border-box;
      margin-bottom: 50px;
      padding: 0 15px;
      box-sizing: border-box;
      li {
        height: 50px;
        line-height: 50px;
        display: flex;
        width: 100%;
        justify-content: space-between;
        border-bottom: 1px solid #f1f1f1;
        .dayu {
          color: #666666;
          font-size: 18px;
          height: 50px;
          line-height: 60px;
        }
        /deep/ .van-cell {
          padding: 0;
          height: 49px;
          line-height: 49px;
          .van-cell__left-icon,
          .van-cell__right-icon {
            height: 50px;
            line-height: 50px;
          }
        }
      }
    }
  }
}
</style>
