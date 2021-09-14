<template>
  <div class="dtcList">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon name="arrow-left" class="m-header-icon" @click="back" />
          </div>
          <div class="title">DTC列表</div>
        </van-col>
      </van-row>
    </div>
    <div class="cont">
      <div class="title">
        <div class="side">故障代码</div>
        <div class="center">故障说明</div>
        <div class="side">可查询次数</div>
      </div>
      <van-list
        v-model="loading"
        :finished="finished"
        :immediate-check="false"
        finished-text="没有更多了"
        @load="onLoad"
        :offset="10"
      >
        <van-cell
          v-for="(item, index) in listData"
          :key="index"
          @click="goDetail(item)"
        >
          <div class="flex" style="width: 100%">
            <div class="side" style="width: 25%; float: left">
              {{ item.dtcCode }}
            </div>
            <div
              class="center"
              style="
                width: 50%;
                float: left;
                border-left: 1px solid #f1f1f1;
                border-right: 1px solid #f1f1f1;
                box-sizing: border-box;
              "
            >
              {{ item.dtcExplain == "" ? "暂无说明" : item.dtcExplain }}
            </div>
            <div class="side" style="width: 25%; float: left">
              {{ item.readCount }}
            </div>
          </div>
        </van-cell>
      </van-list>
      <div class="no-data" v-if="!this.listData">暂无数据</div>
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
export default {
  name: "dtcList",
  data() {
    return {
      loading: false,
      finished: false,
      page: 1,
      pageSize: 10,
      listData: [],
    };
  },
  created() {
    this.getDtcList();
  },

  methods: {
    getDtcList(page, pageSize) {
      this.loading = true;
      let params = {
        pageNum: this.page,
        pageSize: this.pageSize,
      };
      orderApi.dtcList(params).then((res) => {
        if (res.code == "0000") {
          this.loading = false;
          console.log(res.data);
          if (this.page == "1") {
            this.listData = res.data;
          } else {
            this.listData = this.listData.concat(res.data);
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
      this.getDtcList(this.page, this.pageSize);
    },
    back() {
      this.finishAll();
    },

    goDetail(i) {
      this.$router.push({
        path: "/dtcDetailList",
        query: {
          fromUuid: i.uuid,
        },
      });
    },
  },
};
</script>

<style lang="less" scoped>
.title-wrapper {
  width: 100%;
  background: #fff;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1;
  .m-header {
    width: 100%;
    padding: 0 15px;
    height: 50px;
    line-height: 50px;
    background: #fff;
    color: #090909;
  }

  .m-header-icon {
    position: absolute;
    top: 12px;
    left: 6px;
    font-size: 26px;
  }
  .title {
    width: 100%;
    text-align: center;
    font-size: 18px;
    font-weight: 400;
    color: #090909;
  }
}
.dtcList {
  height: 100%;
  width: 100%;
  background: #edf5fb;
  margin-top: 50px;
  border-top: 1px solid #f1f1f1;
  .cont {
    width: 100%;
    background: #fff;
    padding: 0 15px;
    box-sizing: border-box;
    /deep/ .van-list {
      .flex {
        width: 100%;
        // display: flex;
        // justify-content: space-between;
        &::after {
          content: "";
          display: block;
          height: 0;
          overflow: hidden;
          clear: both;
        }
      }
      /deep/ .van-cell__value {
        width: 100%;
        // display: flex;
        // justify-content: space-between;
        border-bottom: 1px solid #f1f1f1;
        .side {
          width: 25%;
          text-align: center;
          padding: 10px 0;
          float: left;
        }
        .center {
          float: left;
          width: 50%;
          padding: 10px 5px;
          text-align: left;
          box-sizing: border-box;
        }
      }
      .van-cell {
        padding: 0;
      }
    }
    .title {
      width: 100%;
      color: #090909;
      height: 44px;
      line-height: 44px;
      display: flex;
      justify-content: space-between;
      border-bottom: 1px solid #f1f1f1;
      .side {
        width: 25%;
        text-align: center;
      }
      .center {
        width: 50%;
        text-align: center;
        border-left: 1px solid #f1f1f1;
        border-right: 1px solid #f1f1f1;
      }
    }
  }
}
</style>
