
<template>
  <div class="putawayProductType">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <van-cell is-link title="一级分类" :value="one" @click="oneType()" />
          <van-action-sheet
            v-model="show"
            :actions="actions"
            @select="onSelect"
          />
        </li>
        <li>
          <van-cell is-link title="二级分类" :value="two" @click="twoType()" />
          <van-action-sheet
            v-model="show2"
            :actions="actions2"
            @select="onSelect2"
          />
        </li>
      </ul>
      <div class="btn">
        <van-button type="info" @click="next()">下一步</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import accountApi from "@/api/account";
export default {
  name: "putawayProductType",
  data() {
    return {
      show: false,
      show2: false,
      one: "",
      two: "",
      actions: [],
      actions2: [],
      oneUuid: "",
      twoUuid: "",
      sysSubsidy: ""
    };
  },
  created() {
    this.getType();
  },
  methods: {
    getType(flag) {
      let params = this.flag;
      accountApi.getOneType(params).then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.groupName;
            return x;
          });
          this.actions = newData;
        }
      });
    },
    oneType() {
      this.show = true;
      this.flag = -1;
      this.two = ''
      this.getType(this.flag);
    },
    onSelect(item) {
      this.flag = item.uuid;
      this.oneUuid = item.uuid;
      console.log(item, item.uuid);
      this.show = false;
      this.one = item.name;
    },
    twoType() {
      if (this.flag != "") {
        this.show2 = true;
        let params = this.flag;
        accountApi.getOneType(params).then((res) => {
          if (res.code == "0000") {
            const resData = res.data;
            const newData = resData.map((x) => {
              x.name = x.groupName;
              return x;
            });
            this.actions2 = newData;
          }
        });
      } else {
        alert("请先选择一级分类");
      }
    },
    onSelect2(item) {
      console.log(item.uuid);
      this.twoUuid = item.uuid;
      this.show2 = false;
      this.two = item.name;
      this.sysSubsidy = item.sysSubsidy
    },
    next() {
      if (this.oneUuid == "" || this.twoUuid == "") {
        this.$toast.fail("请选择完在点击下一步");
        return;
      }
      this.$router.push({
        path: "/putawayProduct",
        query: {
          oneId: this.oneUuid,
          twoId: this.twoUuid,
          sysSubsidy: this.sysSubsidy
        },
      });
    },
  },
};
</script>

<style lang="less" scoped>
.putawayProductType {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    width: 100%;
    border-radius: 8px;
    margin-bottom: 10px;
    ul {
      width: 100%;
      li {
        height: 50px;
        line-height: 50px;
        margin-bottom: 10px;
        width: 100%;
        border-bottom: 1px solid #f1f1f1;

        /deep/ .van-cell__value {
          span {
            color: #090909;
          }
        }
        /deep/ .van-cell {
          padding: 0;
          height: 49px;
          line-height: 49px;
          &::after {
            border: none;
          }
          .van-cell__title {
            span {
              font-size: 16px;
              color: #666666;
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

    .btn {
      width: 100%;
      height: 60px;
      /deep/ .van-button {
        width: 100%;
        height: 44px;
      }
    }
  }
}
</style>

