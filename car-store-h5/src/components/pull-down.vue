<template>
  <div>
    <van-cell is-link :title="title" :value="brandVal" @click="selectClick()" />
    <van-action-sheet
      v-model="brandShow"
      :actions="database"
      @select="onSelect"
    />
  </div>
</template>

<script>
import managerApi from "@/api/manager";

export default {
  name: "pull-down",
  props: {
    title: {
      type: String,
      default() {
        return "";
      },
    },
    selectType: {
      type: String,
      default() {
        return "";
      },
    },
  },
  data() {
    return {
      brandShow: "",
      brandVal: "",
      brandShow: false,
      database: [],
    };
  },
  //   watch: {
  //     imgs: {
  //       handler(v) {
  //         if (v && v.length && this.onOFF) {
  //           this.onOFF = false;
  //           const arr = JSON.parse(JSON.stringify(v));
  //           this.fileList = [];
  //           this.allImg = [];
  //           arr.map((x) => {
  //             const ran = Math.random();
  //             this.fileList.push({
  //               url: x,
  //               randomId: ran,
  //             });
  //             this.allImg.push({
  //               imgPath: x,
  //               imgType: this.imgType,
  //               randomId: ran,
  //             });
  //           });
  //           this.emitHandle();
  //         }
  //       },
  //     },
  //   },
  created() {
    this.xiala();
  },
  methods: {
    xiala() {
      let i = this.selectType;
      managerApi.zidian(i).then((res) => {
        if (res.code == "0000") {
          const resData = res.data;
          const newData = resData.map((x) => {
            x.name = x.lableDesc;
            return x;
          });
          this.database = newData;
          this.$emit("baseData", this.database);
        }
      });
    },
    selectClick() {
      this.brandShow = true;
      this.xiala();
      setTimeout(() => {
	      this.$emit('selectClick', this.selectType, this.brandVal, this.database)
      }, 100)
    },
    onSelect(item) {
      this.brandVal = item.name;
      this.brandUuid = item.uuid;
      this.brandShow = false;
      this.$emit("baseUuid", this.brandUuid);
      this.$emit("baseVal", this.brandVal);
      this.$emit("lableValue", item.lableValue);
    },
  },
};
</script>

<style lang="less" scoped>
</style>
