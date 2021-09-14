<template>
  <div class="jishiWenDa">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <ul>
        <li>
          <div class="title">请问倒车时傻叉异响！原地打方向盘时会响一声！</div>
          <div class="ietm">
            <div class="main">
              你好，新车刹车异响的原因主要存在于刹车片与刹车盘
              之间摩擦系数不匹配，比如生锈异响这需要试车后才能 找……
            </div>
            <div class="imageOrVideo">
              <van-image src="../../../static/images/1.png" />
            </div>
          </div>
          <div class="ietm">
            <div class="main">
              你好，新车刹车异响的原因主要存在于刹车片与刹车盘
              之间摩擦系数不匹配，比如生锈异响这需要试车后才能 找……
            </div>
            <div class="imageOrVideo">
              <van-image src="../../../static/images/1.png" />
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "jishiWenDa",
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.jishiWenDa {
  height: 100%;
  width: 100%;
  margin-top: 50px;
  background: #edf5fb;
  //   /deep/ .m-header {
  //     background: #edf5fb !important;
  //   }
  .cont {
    width: 100%;
    // padding: 0 15px;
    box-sizing: border-box;
    ul {
      width: 100%;
      padding: 15px 0;
      li {
        padding: 10px 15px;
        box-sizing: border-box;
        margin-bottom: 10px;
        width: 100%;
        background: #fff;
        padding: 5px 15px;
        box-sizing: border-box;
        .title {
          margin-top: 12px;
          width: 100%;
          font-size: 18px;
          color: #090909;
          line-height: 25px;
        }
        .ietm {
          margin-bottom: 14px;
          .main {
            font-size: 14px;
            color: #666666;
            line-height: 20px;
            margin: 16px 0 14px 0;
          }
          .imageOrVideo {
            width: 100%;
            height: 140px;
            /deep/ .van-image {
              width: 100%;
              height: 140px;
              img {
                width: 100%;
                height: 100%;
              }
            }
          }
        }
      }
    }
  }
}
</style>
