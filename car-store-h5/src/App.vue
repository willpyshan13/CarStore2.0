<template>
  <div id="app">
    <keep-alive>
      <router-view />
    </keep-alive>
    <!-- <div style="height: 50px"></div> -->
  </div>
</template>

<script>
import dispatchAppFn from "@/mixin/forPhone";
// console.log(dispatchAppFn);
export default {
  name: "App",
  created() {
    let u = navigator.userAgent;
    let isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1; //android终端或者uc浏览器
    let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    console.log(isAndroid, "isAndroid");
    if (isAndroid) {
      console.log(window.js_android.getToken(), "get-token");
      localStorage.setItem("token", window.js_android.getToken());
    } else if (isiOS) {
      window.webkit.messageHandlers.getToken.postMessage({
        url: "getToken",
      });
    }
  },
  beforeCreate() {
    localStorage.removeItem('token') // 初始进入的时候先清除token
    // let token =
    //   "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJXQUlDLVVTRVIgZm9yIG5vLXRlcm1pbmFsIiwiYXVkIjoibm8tdGVybWluYWwiLCJpc3MiOiJXQUlDLVVTRVIiLCJsb2dpblRva2VuIjoie1wiZXhwaXJlVGltZVwiOjIxNjAwLFwiZXh0cmFJbmZvXCI6e30sXCJsb2dpblRlcm1pbmFsXCI6XCJuby10ZXJtaW5hbFwiLFwibG9naW5UaW1lXCI6MTYxNTU2MDI5NDE3NyxcInVzZXJNb2JpbGVcIjpcIjE4NjIxMTk1NDQ3XCIsXCJ1c2VyTmFtZVwiOlwi6LCi5p6X5rGfXCIsXCJ1c2VyVHlwZVwiOjIsXCJ1c2VyVXVpZFwiOlwiMjBmMWRiNDI0OGIyNDQyM2I2ODdmMzA3YjNiMDkzZTJcIixcInV1aWRcIjpcIjIwZjFkYjQyNDhiMjQ0MjNiNjg3ZjMwN2IzYjA5M2UyXCJ9IiwiZXhwIjoxNjE2ODU2Mjk0LCJpYXQiOjE2MTU1NjAyOTR9.xJXedLpwmZZuFhafzp5gGj9ZuntI0-sFwm1F-aSHjcA"; //技师
    // let token =
    //   "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJXQUlDLVVTRVIgZm9yIG5vLXRlcm1pbmFsIiwiYXVkIjoibm8tdGVybWluYWwiLCJpc3MiOiJXQUlDLVVTRVIiLCJsb2dpblRva2VuIjoie1wiZXhwaXJlVGltZVwiOjIxNjAwLFwiZXh0cmFJbmZvXCI6e30sXCJsb2dpblRlcm1pbmFsXCI6XCJuby10ZXJtaW5hbFwiLFwibG9naW5UaW1lXCI6MTYxNTY2NjI1MjU0OSxcInVzZXJNb2JpbGVcIjpcIjE4NjIxMTk1NDQ3XCIsXCJ1c2VyTmFtZVwiOlwi6LCi5p6X5rGfXCIsXCJ1c2VyVHlwZVwiOjMsXCJ1c2VyVXVpZFwiOlwiZjk0YzY3ZWEzOTY1NDE3NTg5NTQyNWFlNmU0YTU4YTdcIixcInV1aWRcIjpcImY5NGM2N2VhMzk2NTQxNzU4OTU0MjVhZTZlNGE1OGE3XCJ9IiwiZXhwIjoxNjE2OTYyMjUyLCJpYXQiOjE2MTU2NjYyNTJ9.a9pZgeDWNdBVD7YzfJv_DMhSNK7_HUtlm_1GFcg1uRQ"; //店铺token
    // localStorage.setItem("token", token);
  },
  methods: {},
  watch: {
    $route: {
      handler(route) {
        if (route.name == "Report") {
          alert("0");
        }
      },
      deep: true,
    },
  },
};
</script>

<style lang="less">
@import url("../static/font-awesome-4.7.0/css/font-awesome.min.css");

#app {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0px;
  bottom: 0px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  text-align: left;
  font-family: PingFangSC-Regular, PingFang SC;
  font-size: 16px;
}

h1 {
  font-size: 2em;
}

h2 {
  font-size: 1.5em;
}

h3 {
  font-size: 1.17em;
}

h4 {
  font-size: 1em;
}

h5 {
  font-size: 0.83em;
}

h6 {
  font-size: 0.67em;
}

.themebg {
  background: #1691e3;
}

.themecolor {
  color: #1691e3;
}

.van-uploader__wrapper--disabled {
  opacity: 1;
}

.vue-html5-editor img {
  max-width: 100%;
  height: auto;
}

.anli-item-content {
  img {
    max-width: 100%;
    height: auto;
  }
}

.disabled-color {
  span {
    color: #c8c9cc !important;
  }
}
.space-block {
    width: 100%;
    height: 0.2rem;
    background: #EDF5FB;
}
img{
	max-width: 100%;
}
.van-action-sheet{
	min-height: 300px;
}
</style>
