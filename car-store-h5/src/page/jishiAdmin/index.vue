<template>
  <div class="jishiAdmin">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <div class="item" v-for="(item, index) in list" :key="index">
        <ul>
          <li>
            <div class="baseDiv">
              <b>姓名:</b>
              <span>{{ item.userName }}</span>
            </div>
            <div class="baseDiv">
              <b>手机号:</b>
              <span>{{ item.mobile }}</span>
            </div>
            <div class="baseDiv">
              <b>证件类型:</b>
              <span>身份证</span>
            </div>
            <div class="baseDiv">
              <b>证劵号码:</b>
              <span>{{ item.certificateNum }}</span>
            </div>
          </li>
        </ul>
        <div class="aboutGuanlian">
          <div class="guanlian" v-if="item.checkSts == 0">
            <div @click="guanlian(item.uuid, 1)">同意关联</div>
            <div @click="guanlian(item.uuid, 2)">拒绝关联</div>
          </div>
          <div
            class="jiebang"
            @click="jiebang(item.uuid)"
            v-if="item.checkSts == 1"
          >
            解绑技师
          </div>
          <div class="jiebang" v-if="item.checkSts == 2">已拒绝</div>
        </div>
      </div>
      <div v-if="show" class="no">暂无数据！</div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
export default {
  name: "jishiAdmin",
  data() {
    return {
      list: [],
      page: 1,
      pageSize: 10,
      total: 0,
      loading: false,
      finished: false,
      show: false,
    };
  },
  created() {
    this.getList(this.page, this.pageSize);
  },
  methods: {
    getList(page, pageSize) {
      let params = {
        pageNum: page,
        pageSize: pageSize,
        // storeUuid: "",
      };
      accountApi.shopJishiGuanlian(params).then((res) => {
        if (res.code == "0000") {
          if (res.data.length < 1) {
            this.show = true;
          }
          // this.list = res.data;
          this.loading = false;
          if (this.page == "1") {
            this.list = res.data;
          } else {
            this.list = this.list.concat(res.data);
          }
          this.total = res.total;
          // 如果加载完毕，显示没有更多了
          if (res.data.length == 0) {
            this.finished = true;
          }
          // 如果加载完毕，显示没有更多了
          if (res.data.length == 0) {
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
      this.getList(this.page, this.pageSize);
    },
    guanlian(uuid, flag) {
      let par = {
        checkSts: flag,
        uuid: uuid,
      };
      accountApi.tongyi(par).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("操作成功了");
          this.getList();
        } else {
          this.$toast.fail("操作失败");
        }
      });
    },
    jiebang(uuid) {
      accountApi.delShopJishi(uuid).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("解绑成功");
          this.getList();
        } else {
          this.$toast.fail("操作失败");
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.jishiAdmin {
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
    .item {
      width: 100%;
      border-radius: 8px;
      background: rgba(255, 255, 255, 0.8);
      margin-bottom: 10px;
      ul {
        width: 100%;
        li {
          margin-bottom: 10px;
          width: 100%;
          padding: 5px 15px;
          box-sizing: border-box;
          margin-bottom: 10px;
          &:last-of-type {
            margin-bottom: 0;
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
      .aboutGuanlian {
        border-top: 1px solid #f1f1f1;
        height: 50px;
        line-height: 50px;
        .guanlian {
          width: 100%;
          font-size: 14px;
          color: #090909;
          display: flex;
          justify-content: space-between;
          & > div {
            width: 50%;
            text-align: center;
            &:last-of-type {
              color: #f76565;
              border-left: 1px solid #f1f1f1;
            }
          }
        }
        .jiebang {
          width: 100%;
          text-align: center;
        }
      }
    }
  }
  .no {
    width: 100%;
    text-align: center;
    margin-left: -2.5%;
    position: fixed;
    top: 35%;
  }
}
</style>
