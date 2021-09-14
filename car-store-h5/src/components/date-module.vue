//封装日期组件
<template>
  <div class="date">
    <van-cell
      :title="dateTitle"
      is-link
      :value-class="className"
      :value="timeValue"
      @click="showPopup"
    />
    <van-popup v-model="timeShow" position="bottom">
      <van-datetime-picker
        v-model="currentDate"
        :type="dateType"
        title="选择时间"
        :loading="isLoadingShow"
        :min-date="minDate"
        :max-date="maxDate"
        :formatter="formatter"
        @cancel="timeShow = false"
        @confirm="confirmPicker"
      />
    </van-popup>
  </div>
</template>

<script>
export default {
  name: "dateModule",
  data() {
    return {
      timeShow: false,
      timeValue: "请选择时间",
      isLoadingShow: true,
      currentDate: new Date(),
      minDate: new Date(1990, 12, 30),
      maxDate: new Date(2090, 12, 31),
      className: "",
    };
  },

  props: {
    dateType: {
      type: String,
      default: function () {
        return [];
      },
    },
    dateTitle: {
      type: String,
      default: function () {
        return [];
      },
    },
  },

  methods: {
    // 显示弹窗
    showPopup() {
      this.timeShow = true;
      this.isLoadingShow = true;
      setTimeout(() => {
        this.isLoadingShow = false;
      }, 500);
    },
    // 确认选择的时间
    confirmPicker(val) {
      let year = val.getFullYear();
      let month = val.getMonth() + 1;
      let day = val.getDate();
      let hour = val.getHours();
      let minute = val.getMinutes();
      if (month >= 1 && month <= 9) {
        month = `0${month}`;
      }
      if (day >= 1 && day <= 9) {
        day = `0${day}`;
      }
      if (hour >= 0 && hour <= 9) {
        hour = `0${hour}`;
      }
      if (minute >= 0 && minute <= 9) {
        minute = `0${minute}`;
      }
      this.className = "timeClass";
      if (this.dateType == "date") {
        this.timeValue = `${year}-${month}-${day}`;
      } else {
        this.timeValue = `${year}-${month}-${day} ${hour}:${minute}`;
      }
      this.timeShow = false;
      // console.log(this.timeValue);
      this.$emit("pitchOnDate", this.timeValue);
    },
    // 选项格式化函数
    formatter(type, value) {
      if (type === "year") {
        return `${value}年`;
      } else if (type === "month") {
        return `${value}月`;
      } else if (type === "day") {
        return `${value}日`;
      }
      //  else if (type === "hour") {
      //   return `${value}时`;
      // } else if (type === "minute") {
      //   return `${value}分`;
      // } else if (type === "second") {
      //   return `${value}秒`;
      // }
      return value;
    },
  },
};
</script>

<style lang="less" scoped>
.date {
  width: 100%;
  /deep/ .van-cell {
    width: 100%;
    .van-cell__title {
      text-align: left;
    }
    .van-cell__value {
      span {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 100%;
        display: inline-block;
      }
    }
  }

  .checkbox-con {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
  }
}
</style>
