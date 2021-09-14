<template>
  <div class="anlifabu">
    <dl-title btntxt="发布"></dl-title>
    <div class="cont">
      <h2>请输入标题</h2>
      <van-field
        v-model="message"
        autosize
        type="textarea"
        placeholder="请输入正文"
        show-word-limit
      />
      <div class="upload">
        <van-uploader v-model="fileList" multiple :after-read="afterRead" />
      </div>
      <div class="price">
        <span>价格</span>
        <van-field v-model="priceNum" type="number" placeholder="请输入价格" />
        <span>元</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "anlifabu",
  data() {
    return {
      message: "",
      priceNum: "",
      fileList: [{ url: "../../../static/images/2.png", isImage: true }],
    };
  },
  methods: {
    afterRead(file) {
      // 此时可以自行将文件上传至服务器
      console.log(file);
    },
  },
};
</script>

<style lang="less" scoped>
.anlifabu {
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    h2 {
      height: 53px;
      font-size: 18px;
      font-weight: 600;
      color: #090909;
      line-height: 25px;
      margin-bottom: 16px;
      padding-top: 28px;
    }
    /deep/ .van-cell {
      height: 140px;
      padding: 0;
      .van-field__control {
        font-size: 16px;
        color: #666666;
        line-height: 22px;
        height: 100%;
      }
      &::after {
        border-bottom: none;
      }
    }
    .upload {
      width: 100%;
      margin: 14px 0;
      padding-bottom: 10px;
      border-bottom: 1px solid #f1f1f1;
      /deep/ .van-uploader__preview-image {
        width: 85px;
        height: 85px;
      }
      /deep/ .van-uploader__upload {
        width: 85px;
        height: 85px;
        background: #edf5fb;
      }
    }
    .price {
      width: 100%;
      height: 44px;
      line-height: 44px;
      /deep/ .van-cell.van-field {
        height: 44px;
        line-height: 44px;
        width: 176px;
        height: 44px;
        background: #edf5fb;
        border-radius: 6px;
        float: left;
        margin: 0 10px 0 14px;
        .van-field__control {
          height: 44px;
          line-height: 44px;
          text-indent: 12px;
          font-size: 16px;
          color: #090909;
        }
      }
      span {
        font-size: 16px;
        color: #090909;
        float: left;
      }
    }
  }
}
</style>
