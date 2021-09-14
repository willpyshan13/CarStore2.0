<template>
  <div class="login">
    <div class="welcome">
      <p>欢迎～</p>
      <p>使用嘟灵代驾</p>
    </div>
    <div class="inputDiv">
      <van-field
        v-model="phone"
        clearable
        label="手机号"
        maxlength="11"
        placeholder="请输入手机号"
      />
    </div>
    <div class="inputDiv inputCode">
      <van-field
        v-model="smscode"
        center
        clearable
        maxlength="6"
        type="number"
        placeholder="请输入验证码"
      />
      <van-button v-if="!cutDownTime" type="default" @click="sendSMSCode"
        >获取验证码</van-button
      >
      <van-button v-if="cutDownTime" type="default"
        >{{ cutDownTime }}s后再试</van-button
      >
    </div>
    <div class="aboutLogin">
      <van-button round :disabled="dis" type="info">登录</van-button>
      <van-checkbox
        v-model="checked"
        icon-size="13px"
        @change="singleChecked(checked)"
        >同意以下内容并授权给嘟灵平台完成注册/登录</van-checkbox
      >
      <div class="xieyi">
        <a href="#">《服务协议》</a>
        <a href="#">《隐私政策》</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      phone: "",
      smscode: "",
      cutDownTime: "",
      dis: true,
      checked: false,
    };
  },
  methods: {
    //   发送验证码
    sendSMSCode() {
      let pattern = /^[1][3,4,5,7,8][0-9]{9}$/g; //正则表达式，验证手机号格式
      if ((this.phone.length != 11) | !pattern.test(this.phone))
        return this.$toast("请输入正确的手机号");
      this.cutDownTime = 60;
      let timer = setInterval(() => {
        this.cutDownTime--;
        if (this.cutDownTime <= 0) {
          this.cutDownTime = "";
        }
      }, 1000);
    },
    singleChecked(val) {
      if (val == true) {
        this.dis = false;
      } else {
        this.dis = true;
      }
    },
  },
};
</script>

<style lang="less" scoped>
body {
  height: 100%;
}
.login {
  width: 100%;
  height: 100%;
  padding: 88px 30px;
  box-sizing: border-box;
  font-size: 16px;
  .welcome {
    margin: 19px 0 35px 0;
    p {
      font-size: 28px;
      font-family: PingFangSC-Medium, PingFang SC;
      font-weight: 500;
      color: #090909;
      line-height: 40px;
    }
  }
  .inputDiv {
    width: 100%;
    /deep/ .van-cell {
      margin: 5px 0;
      width: 100%;
      height: 50px;
      line-height: 33px;
      background: #f6f7f8;
      border-radius: 25px;
      .van-field__label {
        width: 72px;
        text-align: center;
        border-right: 1px solid #e5ecf2;
        span {
          font-size: 16px;
          font-family: PingFangSC-Medium, PingFang SC;
          font-weight: 500;
          color: #090909;
        }
      }
      input {
        font-size: 16px;
        font-family: PingFangSC-Regular, PingFang SC;
        color: #999999;
        &::placeholder {
          font-size: 16px;
          font-family: PingFangSC-Regular, PingFang SC;
          color: #999999;
        }
      }
    }
  }
  .inputCode {
    height: 50px;
    /deep/ .van-cell {
      width: 68%;
      float: left;
    }
    /deep/ .van-button {
      margin-top: 7px;
      height: 50px;
      line-height: 50px;
      width: 26%;
      float: right;
      padding: 0;
      border: none;
      font-size: 16px;
      font-family: PingFangSC-Regular, PingFang SC;
      color: #090909;
    }
  }
  .aboutLogin {
    width: 100%;
    height: 104px;
    margin-top: 40px;
    /deep/ .van-button {
      width: 100%;
      height: 50px;

      font-size: 16px;
      font-family: PingFangSC-Medium, PingFang SC;
      font-weight: 500;
    }
    /deep/ .van-checkbox {
      margin-top: 14px;
      .van-checkbox__label {
        font-size: 13px;
        font-family: PingFangSC-Medium, PingFang SC;
        font-weight: 500;
        color: #999999;
      }
    }
    .xieyi {
      height: 17px;
      width: 100%;
      text-align: center;
      a {
        font-size: 12px;
        font-family: PingFangSC-Medium, PingFang SC;
        font-weight: 500;
        color: #1684e3;
      }
    }
  }
}
</style>
