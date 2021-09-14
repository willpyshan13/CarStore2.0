<template>
  <div class="areaChoose">
    <div class="tx-input" @click="areaChoose">
      <input
        type="text"
        :placeholder="phdText"
        v-model="chooseValue"
        readonly
      />
    </div>
    <van-popup v-model="showAddrPopup" position="bottom">
      <van-area
        title="选择地区"
        :columns-num="2"
        :area-list="areaList"
        @cancel="showAddrPopup = false"
        @confirm="confArea"
        @visible-item-count="itemCount"
      />
    </van-popup>
  </div>
</template>

<script>
import managerApi from "@/api/manager";

export default {
  name: "areaChoose",
  props: {
    addressProvince: {
      type: String, //按钮名称
    },
    addressCity: {
      type: String, //按钮名称
    },
    // itemName: {
    //   type: String, //按钮名称
    //   default: "地区",
    // },
    phdText: {
      type: String, //按钮名称
      default: "请选择地区",
    },
    // showUnderline: {
    //   type: Boolean,
    //   default: true,
    // },
  },
  data() {
    return {
      areaList: {}, //省市区列表
      itemCount: 7,
      showAddrPopup: false, //弹出层展示
      chooseValue: "",
      allList: [],
      all: [],
    };
  },
  watch: {},
  created() {
    this.getAreaList(true);
  },
  methods: {
    initParams() {
      this.areaList = AreaList;
    },
    areaChoose() {
      this.showAddrPopup = true;
      this.getAreaList();
    },
    confArea(data) {
      console.log(this.all, data, "------");
      data = data.map((x) => {
        const obj = this.all.filter((y) => y.areaCode == x.code)[0];
        x.uuid = obj.uuid;
        return x;
      });
      data.push({ code: "", name: "", uuid: "" });
      this.$emit("allList", data);
      this.chooseValue = "";
      let data2 = data.reverse(); //反转数组
      for (let i = 0; i < data2.length; i++) {
        this.chooseValue = data2[i].name + " " + this.chooseValue;
      }
      // console.log(this.chooseValue);
      this.showAddrPopup = false;
    },
    // 获取地区
    getAreaList(flag) {
      managerApi.getAreaList().then((res) => {
        if (res.data && res.data.length) {
          this.all = res.data;
          const province = res.data.filter((x) => x.areaType == 1);
          const city = res.data.filter((x) => x.areaType == 2);
          //   const area = res.data.filter((x) => x.areaType == 3);
          const province_list = {};
          const city_list = {};
          const county_list = {};
          if (this.addressProvince && this.addressCity) {
            var arr = res.data.filter((x) => {
              return (
                x.uuid == this.addressProvince || x.uuid == this.addressCity
              );
            });
            var array = arr.map((x) => ({
              code: x.areaCode,
              name: x.areaName,
              uuid: x.uuid,
            }));
            array.push({ code: "", name: "", uuid: "" });
            this.$emit("allList", array);
          }

          province.forEach((x) => {
            province_list[x.areaCode] = x.areaName;
          });
          city.forEach((x) => {
            city_list[x.areaCode] = x.areaName;
          });
          //   area.forEach((x) => {
          //     county_list[x.areaCode] = x.areaName;
          //   });
          this.areaList = {
            // county_list,
            city_list,
            province_list,
          };
          console.log(this.areaList, "------");
          if (this.addressProvince && flag) {
            this.chooseValue =
              res.data.filter((x) => x.uuid == this.addressProvince)[0]
                .areaName +
              " " +
              res.data.filter((x) => x.uuid == this.addressCity)[0].areaName;
            //   " " +
            //   res.data.filter((x) => x.uuid == this.addressCounty)[0].areaName;
          }
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.areaChoose {
  float: right;
  /deep/ .tx-input {
    height: 49;
    line-height: 49px;
    input {
      height: 49px;
      line-height: 49px;
      text-align: right;
      font-size: 16px;
      color: #090909;
      border: none;
      &::placeholder {
        color: #999999;
      }
    }
  }
}
</style>
