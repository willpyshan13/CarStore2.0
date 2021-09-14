<template>
  <van-action-sheet v-model="brandShow">
    <div class="content">
    	<div class="search">
        <van-search v-model="searVal" placeholder="请输入搜索关键词" />
      </div>
    </div>
    <button class="van-action-sheet__item" v-for="(item, index) in pinpaiType" @click="brandSelect(item)">
    	{{item.configName}}
    </button>
  </van-action-sheet>
</template>

<script>
import accountApi from "@/api/account";
export default {
  name: "cartType",
  props: {
  	brandShow: {
      type: Boolean,
      default: false,
    }
  },
  data() {
    return {
    	searVal: '',
    	brandActions: []
    };
  },
  created () {
  	this.getBrand()
  },
  methods: {
  	// 品牌
    getBrand() {
      accountApi.carType().then((res) => {
        if (res.code == "0000") {
          const pipaiData = res.data;
          const new_data = pipaiData.map((x) => {
            x.name = x.configName;
            return x;
          });
          const new_data1 = new_data.map((y) => {
            y.code = y.uuid;
            return y;
          });
          // this.vehicleBrand = new_data1;
          this.brandActions = new_data;
        }
      });
    },
    brandSelect(item) {
      this.searVal = ''
      this.$emit('brandSelect', item)
    },
  },
  computed: {
  	 pinpaiType() {
      return this.brandActions.filter(
        (item) =>
          item.name.includes(this.searVal)
      );
    },
  }
};
</script>

<style lang="less" scoped>
.van-search__content{
	.van-cell{
		padding: 0 !important;
		height: 34px !important;
		line-height: 34px !important;
	}
}
</style>
