<template>
  <van-uploader
  	class="pdf"
    v-model="fileList"
    multiple
    accept =".pdf"
    :disabled="unedit"
    :deletable="!unedit"
    :after-read="(file) => afterRead(file, imgType)"
    :max-count="1"
    @delete="deleteFile"
    :max-size="50000 * 1024"
  />
</template>

<script>
import utilityApi from "@/api/utility";
import lrz from "lrz";

export default {
  name: "upload-pdf",
  props: {
    imgs: {
      type: Array,
      default() {
        return [];
      },
    },
    maxCount: {
      type: Number,
      default: 1,
    },
    imgType: {
      type: Number,
      default: 0,
    },
    unedit: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      fileList: [],
      allImg: [],
      onOFF: true,
    };
  },
  watch: {
    imgs: {
      handler(v) {
        if (v && v.length && this.onOFF) {
          if (v.length === 1 && !v[0]) return;
          this.onOFF = false;
          const arr = JSON.parse(JSON.stringify(v));
          this.fileList = [];
          this.allImg = [];
          arr.map((x) => {
            const ran = Math.random();
            this.fileList.push({
              url: x,
              randomId: ran,
            });
            this.allImg.push({
              imgPath: x,
              imgType: this.imgType,
              randomId: ran,
            });
          });
          this.emitHandle();
        }
      },
    },
  },
  methods: {
    blobToUint8Array(b) {
      var uri = URL.createObjectURL(b),
        xhr = new XMLHttpRequest(),
        i,
        ui8;
      xhr.open("GET", uri, false);
      xhr.send();
      URL.revokeObjectURL(uri);
      ui8 = new Uint8Array(xhr.response.length);
      for (i = 0; i < xhr.response.length; ++i) {
        ui8[i] = xhr.response.charCodeAt(i);
      }
      return ui8;
    },

    afterRead(file, type) {
      const fil = file.file;
      console.log(fil, 'fil')
//    lrz(fil, {
//      width: 750,
//      //quality: 0.8    //自定义使用压缩方式
//    })
//      .then(function (rst) {
//        console.log(rst, "rst");
//        //成功时执行
//      })
//      .catch(function (error) {
//        //失败时执行
//      })
//      .always(function () {
//        //不管成功或失败，都会执行
//      });
      this.onOFF = false;
      let formdata = new FormData();
      formdata.append("file", fil);
      file.status = "uploading";
      file.message = "上传中...";
      utilityApi
        .upload(formdata)
        .then((res) => {
          if (res.data && res.data.length) {
            file.status = "done";
            this.$toast.success("上传成功了!");
            const ran = Math.random();
            this.fileList.forEach((x, i) => {
              if (i === this.fileList.length - 1) {
                x.randomId = ran;
              }
            });
            this.allImg.push({
              imgPath: res.data,
              imgType: type,
              randomId: ran,
            });
            this.emitHandle();
          } else {
            file.status = "done";
            this.$toast.fail("上传失败!");
            file.status = "done";
          }
        })
        .catch((err) => {
          this.$toast.fail("上传失败!");
          file.status = "failed";
          file.message = "上传失败";
        });
    },
    deleteFile(v) {
      const rans = this.fileList.map((x) => x.randomId);
      this.allImg = this.allImg.filter((x) => rans.includes(x.randomId));
      this.emitHandle();
    },
    emitHandle() {
      if (this.allImg.length > 0) {
        let allImg = JSON.parse(JSON.stringify(this.allImg));
        allImg = allImg.map((x) => {
          delete x.randomId;
          return x;
        });
        this.$emit("getAll", allImg);
      } else {
        this.$emit("getAll", []);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.fabuAnli{
	.cont{
		ul{
			li{
				line-height: 30px;
			}
		}
	}
}
.van-uploader__file-icon{
	margin-top: 20px !important;
  line-height: 30px !important;
}
</style>
