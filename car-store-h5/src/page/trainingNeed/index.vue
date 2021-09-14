<template>
  <div class="trainingNeed">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <van-cell
            is-link
            title="请选择品牌"
            :value="brandVal"
            @click="brandClick()"
          />
          <cartType @brandSelect="brandSelect" :brandShow="brandShow"></cartType>
        </li>
        <li>
          <van-cell
            is-link
            title="请选择车型"
            :value="carVal"
            @click="carClick()"
          />
          <van-action-sheet
            v-model="carShow"
            :actions="carActions"
            @select="carSelect"
          />
        </li>
      </ul>
      <div>
        <div class="desc">需求具备描述</div>
        <div class="imgTxt">
          <van-field
            v-model="faultDesc"
            autosize
            rows="5"
            type="textarea"
            placeholder="请输入"
          />
        </div>
      </div>
      <div class="btn">
        <van-button type="info" @click="submit">提交</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import managerApi from "@/api/manager";
import accountApi from "@/api/account";
import orderApi from "@/api/order";
import cartType from "../../components/cartType.vue"
export default {
	components: { cartType },
  name: "trainingNeed",
  data() {
    return {
      faultDesc: "",
      brandVal: "",
      brandShow: false,
      brandActions: [],
      bianjiUuid: "",
      carVal: "",
      carShow: false,
      carActions: [],
    };
  },
  created() {
    this.getCarType();
  },
  activated() {
    if (this.$route.query && this.$route.query.gobackdtcdetail == 1) {
      window.js_android.goBack();
    }
  },
  methods: {
    brandSelect(item) {
      this.brandVal = item.name;
      this.brandUuid = item.uuid;
      this.brandShow = false;
      this.getCarType(this.brandUuid);
    },
    // 选择品牌
    brandClick() {
      this.brandShow = true;
    },

    //   获取车辆类型
    getCarType(uuid) {
      accountApi.carType(uuid).then((res) => {
        if (res.code == "0000") {
          let resDa = res.data;
          //   const dataFilter = resDa.filter((x) => {
          //     return x.configType == 3;
          //   });
          const newData = resDa.map((x) => {
            x.name = x.configName;
            return x;
          });
          this.carActions = newData;
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
    carClick() {
      this.carShow = true;
    },
    carSelect(item) {
      this.carVal = item.name;
      this.model = item.uuid;
      this.carShow = false;
    },

    submit() {},
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
.trainingNeed {
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
    ul {
      width: 100%;
      li {
        height: 50px;
        line-height: 50px;
        width: 100%;
        border-bottom: 1px solid #f1f1f1;
        display: flex;
        justify-content: space-between;

        /deep/ .van-cell {
          padding: 0;
          height: 48px;
          line-height: 48px;
          &::after {
            border: none;
          }
          .van-cell__title {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            span {
              font-size: 16px;
              color: #666666;
            }
          }
          .van-cell__value {
            color: #090909;
            span {
              text-align: right;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              width: 100%;
              display: inline-block;
            }
          }
          .van-cell__left-icon,
          .van-cell__right-icon {
            height: 50px;
            line-height: 50px;
            color: #666666;
          }
        }
      }
    }
    .desc {
      height: 50px;
      line-height: 50px;
      color: #666666;
    }
    .imgTxt {
      box-sizing: border-box;
      width: 100%;
      border: 1px solid #e6e6e6;
      /deep/ .van-cell {
        min-height: 88px;
        font-size: 16px;
        color: #999999;
        height: auto;
        line-height: 22px;
        .van-field__value {
          height: auto !important;
        }
        &::after {
          border: none;
        }
        .van-field__value {
          height: 68px;
        }
        .van-field__control::placeholder {
          font-size: 16px;
          color: #999999;
        }
      }
    }
    .btn {
      width: 100%;
      padding: 0 15px;
      margin-top: 70px;
      /deep/ .van-button {
        margin-bottom: 40px;
        width: 100%;
        line-height: 44px;
        height: 44px;
        background: #1691e3;
        border-radius: 8px;
        font-size: 16px;
      }
    }
  }
}
</style>
