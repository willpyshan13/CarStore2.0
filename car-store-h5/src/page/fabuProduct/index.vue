<template>
  <div class="fabuProduct">
    <dl-title btntxt="发布"></dl-title>
    <div class="cont">
      <div class="textarea">
        <van-field
          v-model="textarea"
          autosize
          type="textarea"
          placeholder="请输入标题（品牌+车型+故障现象）"
        />
      </div>
      <ul>
        <li class="imgTxtLi">
          <div class="item">
            <div class="div_l">商品主图</div>
          </div>
          <div class="upload">
            <van-uploader v-model="fileList" multiple :after-read="afterRead" />
          </div>
        </li>
        <li>
          <div class="item">
            <div class="div_l">价格</div>
            <div class="div_r">
              <van-field v-model="addressee" placeholder="请输入" />
            </div>
          </div>
        </li>
        <li>
          <van-cell is-link title="品牌" @click="show = true" />
          <van-action-sheet
            v-model="show"
            :actions="actions"
            @select="onSelect"
          />
        </li>
        <li>
          <div class="item">
            <div class="div_l">价格</div>
            <div class="div_r">
              <van-field v-model="addressee" placeholder="请输入" />
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "fabuProduct",
  data() {
    return {
      textarea: "",
      show: false,
      actions: [
        { name: "保时捷4S店" },
        { name: "宝马4S店" },
        { name: "奥迪4S店" },
      ],
      addressee: "",
      message: "",
      fileList: [],
      radio: "1",
    };
  },
  methods: {
    onSelect(item) {
      // 默认情况下点击选项时不会自动收起
      // 可以通过 close-on-click-action 属性开启自动收起
      this.show = false;
      Toast(item.name);
    },
    afterRead(file) {
      // 此时可以自行将文件上传至服务器
      console.log(file);
    },
  },
};
</script>

<style lang="less" scoped>
.fabuProduct {
  height: 100%;
  width: 100%;
  margin-top: 50px;
  .cont {
    width: 100%;
    padding: 0 15px;
    box-sizing: border-box;
    width: 100%;
    border-radius: 8px;
    margin-bottom: 10px;
    .textarea {
      width: 100%;
      height: 165px;
      padding-top: 20px;
      box-sizing: border-box;
      /deep/ .van-cell {
        height: 100%;
        padding: 0;
        line-height: 25px;
        .van-field__control {
          font-size: 18px;
          color: #090909;
          line-height: 25px;
          &::placeholder {
            font-size: 18px;
            color: #090909;
          }
        }
      }
    }
    ul {
      width: 100%;
      li {
        height: 50px;
        line-height: 50px;
        margin-bottom: 10px;
        width: 100%;
        border-bottom: 1px solid #f1f1f1;
        /deep/ .van-cell {
          padding: 0;
          height: 49px;
          line-height: 49px;
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
        .item {
          width: 100%;
          display: flex;
          justify-content: space-between;
          & > div {
            font-size: 16px;
            color: #666666;
          }
          .div_l {
            width: 30%;
          }
          .div_r {
            width: 70%;
            /deep/ .van-cell {
              .van-field__control {
                font-size: 16px;
                color: #666666;
                text-align: right;
              }
            }
          }
        }
        .imgTxt {
          padding: 10px 15px;
          box-sizing: border-box;
          width: 100%;
          height: 174px;
          border: 1px solid #e6e6e6;
          /deep/ .van-cell {
            font-size: 16px;
            color: #999999;
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
          .upload {
            margin-top: 25px;
          }
        }
      }
      .imgTxtLi {
        height: auto;
        padding-bottom: 20px;
        &:last-of-type {
          padding-bottom: 0;
          border: none;
        }
      }
    }
  }
}
</style>
