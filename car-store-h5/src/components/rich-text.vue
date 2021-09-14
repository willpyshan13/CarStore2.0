<template>
  <div class="richText">
    <editor
      style="margin-top: 10px"
      :content="ideaProcess"
      @change="updateData"
      :height="200"
      :z-index="1000"
      :auto-height="true"
      :show-module-name="false"
    ></editor>
  </div>
</template>

<script>
import utilityApi from "@/api/utility";
import VueHtml5Editor from "vue-html5-editor";
const editor = new VueHtml5Editor({
  icons: {
    text: "fa fa-pencil",
    color: "fa fa-paint-brush",
    font: "fa fa-font",
    align: "fa fa-align-justify",
    list: "fa fa-list",
    link: "fa fa-chain",
    unlink: "fa fa-chain-broken",
    tabulation: "fa fa-table",
    image: "fa fa-file-image-o",
    hr: "fa fa-minus",
    eraser: "fa fa-eraser",
    undo: "fa-undo fa",
    "full-screen": "fa fa-arrows-alt",
    info: "fa fa-info",
  },
  image: {
    // 文件最大体积，单位字节  max file size
    sizeLimit: 60000 * 1024,
    // 上传参数,默认把图片转为base64而不上传
    // upload config,default null and convert image to base64
    // upload: {
    //     url: 'http://car.api.123cgj.com/utility/file/uploadFile?type=other',
    //     headers: {},
    //     params: {},
    //     fieldName: 'file'
    // },
    // 压缩参数,默认使用localResizeIMG进行压缩,设置为null禁止压缩
    // compression config,default resize image by localResizeIMG (https://github.com/think2011/localResizeIMG)
    // set null to disable compression
    compress: {
      width: 750,
      quality: 80,
    },
    // uploadHandler(responseText){
    //   console.log(JSON.parse(responseText), 'aaaaaaaaaa');

    // var json = JSON.parse(responseText);
    // if(json.code == 0){
    //     return json.data
    // }else{
    //     alert(json.message)
    // }
    // }
  },
  language: "zh-cn",
  i18n: {
    //specify your language here
    "zh-cn": {
      align: "对齐方式",
      image: "图片",
      list: "列表",
      link: "链接",
      unlink: "去除链接",
      table: "表格",
      font: "文字",
      "full screen": "全屏",
      text: "排版",
      eraser: "格式清除",
      info: "关于",
      color: "颜色",
      "please enter a url": "请输入地址",
      "create link": "创建链接",
      bold: "加粗",
      italic: "倾斜",
      underline: "下划线",
      "strike through": "删除线",
      subscript: "上标",
      superscript: "下标",
      heading: "标题",
      "font name": "字体",
      "font size": "文字大小",
      "left justify": "左对齐",
      "center justify": "居中",
      "right justify": "右对齐",
      "ordered list": "有序列表",
      "unordered list": "无序列表",
      "fore color": "前景色",
      "background color": "背景色",
      "row count": "行数",
      "column count": "列数",
      save: "确定",
      upload: "上传",
      progress: "进度",
      unknown: "未知",
      "please wait": "请稍等",
      error: "错误",
      abort: "中断",
      reset: "重置",
    },
  },
  visibleModules: ["color", "font", "image", "align", "full-screen"],
});

export default {
  name: "richText",
  components: { editor },
  props: {
    content: {
      type: String,
    },
  },
  data() {
    return {
      ideaProcess: "",
    };
  },
  watch: {
    content: {
      handler(n, o) {
        // console.log(n, o, "-------");
        this.ideaProcess = n;
      },
      deep: true,
    },
  },
  methods: {
    updateData(v) {
      if (v.includes("data:image")) {
        const arr = v.split(`<img src="data:image/jpeg;base64,`);
        const arr1 = arr[1].split(`"`);
        const base64Img = `${arr1[0]}`;

        utilityApi
          .uploadBase64Image({
            base64Img,
            type: "other",
          })
          .then((res) => {
            if (res && res.data) {
              this.ideaProcess = `${arr[0]}<img src="${res.data}"${arr1
                .filter((x, i) => i > 0)
                .join(`"`)}`;
              this.$emit("ideaProcess", this.ideaProcess);
            }
          });
      } else {
        this.ideaProcess = v;
        this.$emit("ideaProcess", this.ideaProcess);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.richText {
  /deep/ .toolbar {
    z-index: 0 !important;
  }
}
</style>
