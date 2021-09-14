<template>
    <div class="page" v-wechat-title="documentTitle">
        <div class="container">
            <dl-title btntxt=""></dl-title>
            <div class="content">
                <div class="space-block"></div>
                <div class="question-detail">
                    <div class="section">
                        <div class="title">
                            {{orderDetail.title}}
                        </div>
                        <div class="desc">
                            {{orderDetail.consultDesc}}
                        </div>
                        <div class="pic-list" v-if="orderDetail.consultImgList && orderDetail.consultImgList.length">
                            <div class="item" v-for="(item,index) in orderDetail.consultImgList" :key="index">
 								<!--{{item.indexOf('mp4')}}-->
                                <video webkit-playsinline style="width:100%;height: 2.8rem;" :src="item" 
                                	v-if="item.indexOf('mp4') > -1 || item.indexOf('avi') > -1 || item.indexOf('wmv') > -1 || item.indexOf('mpeg') > -1 || item.indexOf('m4v') > -1 || item.indexOf('mov') > -1 || item.indexOf('asf') > -1 || item.indexOf('flv') > -1 || item.indexOf('f4v') > -1 || item.indexOf('rmvb') > -1 || item.indexOf('rm') > -1 || item.indexOf('3gp') > -1 || item.indexOf('vob') > -1"
                                	controls="controls"></video>
                                <van-image
                                	v-else
                                    width="100%"
                                    height="2.8rem"
                                    fit="cover"
                                    :src="item"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="section" style="margin-top:0.4rem;">
                        <div class="title">
                            {{ technicianName }}的回答
                        </div>
                        <div class="desc">
                            {{orderDetail.answerDesc}}
                        </div>
                        <div class="pic-list" v-if="orderDetail.answerImgList && orderDetail.answerImgList.length">
                            <div class="item" v-for="(item,index) in orderDetail.answerImgList" :key="index">
                                <van-image
                                    width="100%"
                                    height="2.8rem"
                                    fit="cover"
                                    :src="item"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <cover v-if="isShowCover && price !== '0'" @pay="handlePay"></cover>
    </div>
</template>
<script>
import cover from "@/components/cover"
import orderApi from "@/api/order";
import dispatchAppFn from "@/mixin/forPhone";
export default {
    components:{
        cover
    },
    data(){
        return {
            documentTitle:'旁听详情',
            uuid:'',
            orderUuid:'',
            orderDetail:{},
            price: '',
            courseAmount: ''
        }
    },
    computed:{
        isShowCover(){
            let bool = true;
            let orderDetail = this.orderDetail;
            if(orderDetail.uuid && orderDetail.orderSts == 1){
                bool = false
            }
            return bool;
        },
        technicianName(){
            let name = '';
            if(this.orderDetail.technicianName){
                name = this.orderDetail.technicianName[0] + '师傅'
            }
            return name;
        }
    },
    created() {
    	this.price = this.$route.query.price || ''
      this.initParams();
      this.init();
    },
    methods:{
        init(){
        	/**********************************************/
      	// this.uuid = 'eae90b94571841f4a11a28c0cb3db75f';
        	/**********************************************/
        	orderApi.queryConsultDetail(this.uuid)
            .then((res) => {
                this.orderDetail = res.data || {};
            }).catch((e) => {
                e.msg && this.$toast(e.msg)
            })
        },
        handlePay(){
            let orderDetail = this.orderDetail;
            if(orderDetail.orderUuid !== null){
                this.$router.push({
                    path: "/payOrderJy",
					          query: {
					          	dtcAmount: this.courseAmount,
					            oid: orderDetail.orderUuid,
					            caseConsult: 'caseConsult'
					          }
                })
            }else{
                this.$toast.loading({
                    message: '加载中...',
                    forbidClick: true,
                    loadingType: 'spinner',
                })
                orderApi.addAuditorOrder({
                    orderUuid:this.orderUuid
                })
                .then((res) => {
                    this.$toast.clear()
                    this.$router.push({
                        path: "/payOrderJy",
							          query: {
							          	dtcAmount: this.courseAmount,
							            oid: res.data,
							            caseConsult: 'caseConsult'
							          }
                    })
                }).catch((e) => {
                    e.msg && this.$toast(e.msg)
                })
            }

        },
        initParams(){
        	let u = navigator.userAgent;
			    let isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1; //android终端或者uc浏览器
			    let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid){
              let params = dispatchAppFn({
                  fn:'getAuditParam'
              })
              params = JSON.parse(params);
              this['uuid'] = params.uuid;
              this['orderUuid'] = params.orderUuid;
            }else{
                let params = this.$route.query;
                this['uuid'] = params.uuid;
                this['orderUuid'] = params.orderUuid;
            }
        }
    }
}
</script>
<style lang="less" scoped>
.page{

}
.content{
	padding-top: 50px;
}
.question-detail{
    padding:0.28rem 0.3rem;
    .section{
        padding-bottom:0.4rem;
        border-bottom:0.5px solid rgba(241, 241, 241, 1);
        &:last-child{
            border-bottom:0;
        }
    }
    .title{
        font-size: 0.36rem;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #090909;
        line-height: 0.5rem;
    }
    .desc{
        margin-top: 0.32rem;
        font-size: 0.32rem;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #666666;
        line-height: 0.44rem;
        text-align: justify;
        word-wrap:break-word;
    }
    .pic-list{
        margin-top: 0.4rem;
        .item{
            margin-bottom: 0.2rem;
            &:last-child{
                margin-bottom:0;
            }
        }
    }
}
</style>
