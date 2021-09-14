<template>
  <div class="myCourse">
    <div class="title-wrapper">
      <van-row class="m-header">
        <van-col span="24">
          <div>
            <van-icon name="arrow-left" class="m-header-icon" @click="back" />
          </div>
          <div class="title">{{ Title }}</div>
        </van-col>
      </van-row>
    </div>
    <!-- <dl-title btntxt=""></dl-title> -->
    <div class="cont">
      <van-list
        v-model="loading"
        :finished="finished"
        :immediate-check="false"
        finished-text="没有更多了"
        @load="onLoad"
        :offset="10"
      >
        <!-- <van-cell
          v-for="(item, index) in listData"
          :key="index"
          @click="goDetail(item)"
        >
          <div class="side">{{ item.courseTitle }}</div>
          <div class="center">{{ item.courseCover }}</div>
          <div class="side">{{ item.readCount }}</div>
        </van-cell> -->
        <van-cell>
          <div
            class="item"
            v-for="(item, index) in listData"
            :key="index"
            @click="goDetail(item)"
          >
            <img :src="item.courseCover" alt="" />
            <div class="dsc">{{ item.courseTitle }}</div>
          </div>
        </van-cell>
      </van-list>
      <div class="no-data" v-if="!this.listData">暂无数据</div>

      <!-- <div class="btn">
        <van-button type="info" @click="submit">提交</van-button>
      </div> -->
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
import dispatchAppFn from "@/mixin/forPhone";
export default {
  name: "myCourse",
  data() {
    return {
      loading: false,
      finished: false,
      listData: [],
      page: 1,
      pageSize: 10,
      Title: "",
      latestCourse: 0,
      uuid: ''
    };
  },
  created() {
  	this.initParams()
    this.getDtcList(this.page, this.pageSize);
  },
  activated() {
    this.Title = this.getJcTitle();
    // if (this.$route.query && this.$route.query.gobackdtcdetail == 1) {
    //   window.js_android.goBack();
    // }
  },
  methods: {
    getDtcList(page, pageSize) {
      this.loading = true;
      let params = {
        courseParentUuid: this.uuid,
        // courseParentUuid: "375491e3abf04bb4a35c73bcda623186",
        pageNum: page,
        pageSize: pageSize,
      };
      orderApi.kechengList(params).then((res) => {
        if (res.code == "0000") {
          this.loading = false;
          let courseList = []
          if (this.latestCourse == 1) {
          	res.data.forEach((item) => {
          		if (item.latestCourse == 1) {
          			courseList.push(item)
          		}
          	})
          } else {
          	courseList = res.data
          }
          // alert(JSON.stringify(courseList))
          if (this.page == "1") {
            this.listData = courseList;
          } else {
            this.listData = this.listData.concat(courseList);
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
      this.getDtcList(this.page, this.pageSize);
    },

    goDetail(i) {
      this.$router.push({
        path: "/jiaoyuDetail",
        // path: "/kechengDetail",
        query: {
          fromUuid: i.uuid,
        },
      });
    },
    back() {
      this.finishAll();
    },
    initParams() {
			let u = navigator.userAgent;
	    let isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1; //android终端或者uc浏览器
	    let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
      if (isAndroid) {
        let params = dispatchAppFn({
          fn: "getParam"
        });
        params = JSON.parse(params);
        this["uuid"] = params.uuid;
        this["latestCourse"] = params.latestCourse;
      } else {
        let params = this.$route.query;
        this["uuid"] = params.uuid;
        this["latestCourse"] = params.latestCourse;
      }
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
.myCourse {
  width: 100%;
  height: 100%;
  background: #edf5fb;
  margin-top: 50px;
  border-top: 1px solid #f1f1f1;
  .cont {
    width: 100%;
    background: #edf5fb;
    padding: 0 15px;
    box-sizing: border-box;
    /deep/ .van-list {
      width: 100%;
      padding-top: 10px;
      .van-cell {
        width: 100%;
        padding: 0;
        .van-cell__value {
          background: #edf5fb;
          // display: flex;
          // justify-content: space-between;
          & > .item {
            float: left;
            width: 49%;
            height: 150px;
            background: #ffffff;
            border-radius: 4px;
            margin-bottom: 10px;
            &:nth-child(2n + 1) {
              margin-right: 2%;
            }
            img {
              width: 100%;
              height: 115px;
            }
            .dsc {
              text-align: center;
              height: 25px;
              font-size: 14px;
              width: 100%;
              color: #333333;
              line-height: 25px;
            }
          }
        }
      }
    }
  }
}
</style>
