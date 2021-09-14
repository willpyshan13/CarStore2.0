<template>
  <div class="selectShopName">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="search">
        <van-search v-model="searVal" placeholder="请输入店铺名称" />
      </div>

      <ul v-if="searVal != ''" style="margin-bottom: 0">
        <li
          v-for="(item, index) in shopLists"
          :key="index"
          @click="goBack(item)"
          style="color: #f76565"
        >
          {{ item.storeName }}
        </li>
      </ul>
      <ul>
        <li
          v-for="(item, index) in shopList"
          :key="index"
          @click="goBack(item)"
        >
          {{ item.storeName }}
        </li>
      </ul>
      <div v-if="noData" class="no-data">暂无数据!</div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";

export default {
  name: "selectShopName",
  data() {
    return {
      searVal: "",
      shopList: [],
      addressDetail: "",
      page: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      finished: false,
      noData: false,
    };
  },
  created() {
    this.getShopList();
  },
  activated() {
    console.log(this.$route.query.uuid);
  },
  methods: {
    getShopList(page, pageSize) {
      let par = {
        areaUuid: this.$route.query.uuid,
        pageNum: page,
        pageSize: pageSize,
      };
      accountApi.searchDianpu(par).then((res) => {
        if (res.code == "0000") {
          this.loading = false;
          if (res.data.length < 1) {
            this.noData = true;
          }
          if (this.page == "1") {
            this.shopList = res.data;
          } else {
            this.shopList = this.shopList.concat(res.data);
          }
          this.total = res.total;
          // 如果加载完毕，显示没有更多了
          if (res.data.length === 0) {
            this.finished = true;
          }
          // 如果加载完毕，显示没有更多了
          if (res.data.length === 0) {
            this.finished = true;
            return;
          }
        }
      });
    },
    //滚动加载更多
    onLoad() {
      this.loading = false;
      this.page++;
      this.getShopList(this.page, this.pageSize);
    },
    goBack(i) {
      console.log(i);
      this.searVal = i.storeName;
      this.addressDetail =
        i.addressProvinceName +
        i.addressCityName +
        // i.addressCountyName +
        i.companyAddressDetail;
      this.$router.push({
        path: "/relevancyShop",
        query: {
          shopNAme: JSON.stringify(i.storeName),
          shopUuid: JSON.stringify(i.uuid),
          shopAddress: JSON.stringify(this.addressDetail),
          flag: "2",
        },
      });
    },
  },
  computed: {
    shopLists() {
      return this.shopList.filter(
        (item) => item.storeName.indexOf(this.searVal) > -1
      );
    },
  },
  deactivated() {
    //清除keep-alive的缓存
    this.$destroy(true);
  },
};
</script>

<style lang="less" scoped>
.selectShopName {
  width: 100%;
  margin-top: 50px;
  background: #edf5fb;
  height: 100%;
  .cont {
    width: 100%;
    background: #fff;
    border-top: 1px solid #f1f1f1;
    margin-bottom: 10px;
    .search {
      height: 50px;
      width: 100%;
      margin-top: 10px;
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
        &:first-child {
          border-top: 1px solid #f1f1f1;
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
    .no-data {
      text-align: center;
      width: 100%;
      position: fixed;
      top: 200px;
    }
  }
}
</style>
