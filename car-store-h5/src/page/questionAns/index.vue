<template>
  <div class="questionAns">
    <dl-title btntxt=""></dl-title>
    <div class="cont">
      <h3>{{ title }}</h3>
      <div class="infor">
        <div class="inforl">
          <img :src="carOwnerImgUrl" alt="" />
          <div class="geren">
            <div class="name">{{ carOwnerName }}</div>
            <div class="time"><span>提问于 </span>{{ createdTime }}</div>
          </div>
        </div>
        <div class="inforr">
          ￥<span>{{ consultAmt }}</span>
        </div>
      </div>
      <div class="quest">
        {{ consultDesc }}
        <img class="quest-img" v-for="(item, index) in consultImgList" :key="index" :src="item" alt="">
      </div>
      
      <div class="answer">
        <h3 v-if="answerSts == 1">回答内容</h3>
        <div class="content">
          <div class="daan">
            <van-field
              v-model="message"
              rows="5"
              autosize
              type="textarea"
              placeholder="优质回答会得到车主的赏金哦"
            />
            <div class="upload">
              <upload-img
                :max-count="5"
                :imgs="fileList"
                :imgType="1"
                @getAll="(v) => getStore(v, 'fileList')"
              ></upload-img>
            </div>
          </div>
          <!-- <div class="fabu" @click="fabu()">发布</div> -->
          <van-button type="info" size="large" @click="fabu" style="margin-top: 30px">回答</van-button>
        </div>
        
      </div>
      
    </div>
  </div>
</template>

<script>
import orderApi from "@/api/order";
import uploadImg from "../../components/upload-img.vue";

export default {
  components: { uploadImg },
  name: "questionAns",
  data() {
    return {
      title: "",
      consultDesc: "",
      createdTime: "",
      consultAmt: "",
      carOwnerName: "",
      carOwnerMobile: "",
      carOwnerImgUrl: "/static/images/default.png",
      message: "",
      fileList: [],
      resDataUuid: "",
      consultImgList: [],
      answerSts: 0
    };
  },
  created() {
    this.getWenDa();
  },
  methods: {
    getStore(v, str) {
      const storeImgs = [];
      v.forEach((e) => {
        storeImgs.push(e.imgPath);
      });
      this[str] = storeImgs;
    },
    phoneFormat(phoneNum) {
      if (Number(phoneNum) && String(phoneNum).length === 11) {
        const mb = String(phoneNum);
        const reg = /^(\d{3})\d{4}(\d{4})$/;
        return mb.replace(reg, "$1****$2");
      } else {
        return phoneNum;
      }
    },

    // 查询问答内容
    getWenDa() {
      let par = this.getUuid();
      //   let par = "506ce4c4b8434339aae6b032b7be7735";
      orderApi.wendaDetail(par).then((res) => {
        if (res.code == "0000") {
          console.log(res.data);
          let resData = res.data;
          this.resDataUuid = resData.uuid;
          this.title = resData.consultRes.title;
          this.consultDesc = resData.consultRes.consultDesc;
          this.consultImgList = resData.consultRes.consultImgList;
          this.createdTime = resData.createdTime;
          this.carOwnerName = resData.consultRes.carOwnerName
            ? resData.consultRes.carOwnerName
            : this.phoneFormat(resData.consultRes.carOwnerMobile);
          this.carOwnerImgUrl = resData.consultRes.carOwnerImgUrl || '/static/images/default.png';
          this.consultAmt = resData.consultRes.consultAmt;
          this.carOwnerMobile = this.phoneFormat(
            resData.consultRes.carOwnerMobile
          );
          this.message = resData.consultRes.answerDesc
          this.fileList = resData.consultRes.answerImgList
          this.answerSts = resData.consultRes.answerSts
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
    fabu() {
      let params = {
        answerContent: this.message ? this.message : "",
        answerImgList: this.fileList,
        orderUuid: this.resDataUuid, //订单uuid
        // technicianUuid: "222", //技师uuid
      };
      orderApi.answerTheQuestion(params).then((res) => {
        if (res.code == "0000") {
          this.$toast.success("发布成功了！");
          setTimeout(() => {
            this.finishAll();
          }, 1000);
        } else {
          this.$toast.fail(res.msg);
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
.quest-img {
  display: block;
  width: 100%;
  height: auto;
  margin-top: 20px;
}

.questionAns {
  width: 100%;
  margin-top: 50px;
  padding-bottom: 50px;
  .cont {
    width: 100%;
    h3 {
      padding: 0 15px;
      box-sizing: border-box;
      height: 28px;
      font-size: 20px;
      font-weight: 600;
      color: #090909;
      line-height: 28px;
      margin: 16px 0;
    }
    .infor {
      padding: 0 15px;
      box-sizing: border-box;
      height: 48px;
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      .inforl {
        width: 70%;
        img {
          float: left;
          width: 48px;
          height: 48px;
          border-radius: 50%;
        }
        .geren {
          float: left;
          margin-left: 10px;
          .name {
            height: 20px;
            font-size: 14px;
            font-weight: 500;
            color: #090909;
            line-height: 20px;
            margin: 4px 0;
          }
          .time {
            height: 17px;
            font-size: 12px;
            color: #666666;
            line-height: 17px;
            span {
              font-size: 12px;
            }
          }
        }
      }
      .inforr {
        width: 30%;
        line-height: 48px;
        text-align: right;
        font-size: 12px;
        font-weight: 500;
        color: #ff3838;
        span {
          font-size: 24px;
        }
      }
    }
    .quest {
      padding: 0 15px;
      box-sizing: border-box;
      margin-top: 13px;
      font-size: 15px;
      color: #666666;
      line-height: 21px;
      min-height: 120px;
    }
    .answer {
      margin-top: 20px;
      width: 100%;
      border-top: 1px solid #f1f1f1;
      .content {
        width: 100%;
        padding: 10px 15px 0;
        box-sizing: border-box;
        position: relative;
        &::after {
          content: "";
          display: block;
          clear: both;
        }
        .daan {
          // float: left;
          // width: 90%;
          //   height: 179px;
          background: #f6f7f8;
          padding: 10px 15px;
          box-sizing: border-box;
          /deep/ .van-cell {
            padding: 0;
            min-height: 64px;
            margin-bottom: 10px;
            font-size: 15px;
            color: #666666;
            line-height: 21px;
            background: #f6f7f8;
            .van-field__control {
              &::placeholder {
                color: #666666;
              }
            }
          }
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
        .fabu {
          //   height: 179px;
          box-sizing: border-box;
          //   padding-top: 70px;
          margin-left: 5%;
          float: right;
          width: 5%;
          font-size: 16px;
          font-weight: 500;
          color: #1691e3;
          position: absolute;
          right: 0;
          top: 50%;
          transform: translate(-50%, -50%);
        }
      }
    }
  }
}
</style>
