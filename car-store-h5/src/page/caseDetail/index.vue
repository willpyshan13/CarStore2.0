<template>
	<div class="page">
		<div class="container">
			<dl-title btntxt=""></dl-title>
			<div class="content">
				<div class="space-block"></div>
				<div class="question-detail">
					<div class="section">
						<div class="title">
							{{ caseInfo.title }}
						</div>
						<div class="section-wrapper">
							<div class="section">
								<div class="subtitle">故障现象</div>
								<div class="desc" v-html="caseInfo.faultDesc"></div>
							</div>
							<!--<div class="section">
								<div class="subtitle">诊断思路与过程</div>
								<div class="desc" v-html="caseInfo.ideaProcess"></div>
							</div>-->
							<div class="section">
								<!--<div class="subtitle">结论总结</div>-->
								<!--<div class="desc" v-html="caseInfo.summary"></div>-->
								<pdf v-for="i in numPages" :key="i" :page="i" :src="pdfUrl" ref="pdf" style="width: 100%; height: auto;" @num-pages="pageCount=$event"></pdf>
							</div>
							<div class="pic-list" v-if="caseInfo.caseImgUrl && caseInfo.caseImgUrl.length">
								<div class="item" v-for="(item, index) in caseInfo.caseImgUrl" :key="index">
									<!--<van-image width="100%" height="4.6rem" fit="cover" :src="item" />-->
									<video webkit-playsinline style="margin-top: 10px;width:100%;height: 4.6rem;" :src="item" v-if="item.indexOf('mp4') > -1 || item.indexOf('avi') > -1 || item.indexOf('wmv') > -1 || item.indexOf('mpeg') > -1 || item.indexOf('m4v') > -1 || item.indexOf('mov') > -1 || item.indexOf('asf') > -1 || item.indexOf('flv') > -1 || item.indexOf('f4v') > -1 || item.indexOf('rmvb') > -1 || item.indexOf('rm') > -1 || item.indexOf('3gp') > -1 || item.indexOf('vob') > -1" controls="controls"></video>

									<img :src="item" class="imgCase" style="margin-top: 10px;width:100%;height: 4.6rem;" v-else/>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<cover v-if="isShowCover && !loading && price !== '0'" @pay="handlePay"></cover>
		<!--<page-loading :show="loading"></page-loading>-->
	</div>
</template>
<script>
	import cover from "@/components/cover";
	import orderApi from "@/api/order";
	//import { ORDER_STATUS } from "@/assets/js/config";
	import dispatchAppFn from "@/mixin/forPhone";
	import pdf from 'vue-pdf'
	export default {
		components: {
	    cover,
	    pdf
	  },
		data() {
			return {
				uuid: "",
	      documentTitle: "案例详情",
	      caseInfo: {},
	      orderUuid: "",
	      loading: true,
	      price: '',
	      courseAmount: 0,
	      pdfUrl: '',
	      pageCount: 0,
				numPages: 0,
				src: '', //文件路径
			};
		},
		created () {
			this.price = this.$route.query.price || ''
	    this.initParams();
	    this.init();
		},
		computed: {
			isShowCover() {
	      let bool = true;
	      let caseInfo = this.caseInfo;
	      if (caseInfo.uuid && caseInfo.orderSts == 1) {
	        bool = false;
	      }
	      return bool;
	    }
		},
		methods: {
			init(cases) {
				this.showLoading = true;
				orderApi.queryCaseDetail(this.uuid)
				.then(res => {
          this.caseInfo = res.data || {};
          this.courseAmount = this.caseInfo.amt
          this.loading = false;
          this.pdfUrl = pdf.createLoadingTask(this.caseInfo.summary);
					this.pdfUrl.promise.then(pdf => {
						console.log(pdf, 'pdf')
						this.numPages = pdf.numPages
					})
        })
        .catch(e => {
          e.msg && this.$toast(e.msg);
          this.loading = false;
        });
			},
			handlePay () {
	      let caseInfo = this.caseInfo;
	      if (caseInfo.orderUuid !== null) {
	        this.$router.push({
	          path: "/payOrderJy",
	          query: {
	          	dtcAmount: this.courseAmount,
	            oid: caseInfo.orderUuid,
	            caseConsult: 'caseConsult'
	          }
	        });
	      } else {
	        this.$toast.loading({
	          message: "加载中...",
	          forbidClick: true,
	          loadingType: "spinner"
	        });
	        orderApi.addCaseOrder(this.uuid)
	          .then(res => {
	            this.$toast.clear();
	            this.$router.push({
	              path: "/payOrderJy",
			          query: {
			          	dtcAmount: this.courseAmount,
			            oid: res.data,
			            caseConsult: 'caseConsult'
			          }
	            });
	          })
	          .catch(e => {
	            e.msg && this.$toast(e.msg);
	          });
	      }
	    },
	    initParams() {
	    	let u = navigator.userAgent;
		    let isAndroid = u.indexOf("Android") > -1 || u.indexOf("Linux") > -1; //android终端或者uc浏览器
		    let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	      if (isAndroid) {
	        let params = dispatchAppFn({
	            fn: "getCaseParam"
	          });
          params = JSON.parse(params);
          this["uuid"] = params.caseUuid;
	      } else {
	        let params = this.$route.query;
	        this["uuid"] = params.caseUuid;
	      }
	    }
		}
	};
</script>
<style lang="less" scoped>
	.page {
		padding-top: 50px;
	}
	
	.content {}
	
	.question-detail {
		padding: 0.28rem 0.3rem;
		.section {
			padding-bottom: 0.4rem;
			border-bottom: 0.5px solid rgba(241, 241, 241, 1);
			&:last-child {
				border-bottom: 0;
			}
		}
		.title {
			font-size: 0.36rem;
			font-family: PingFangSC-Regular, PingFang SC;
			font-weight: 400;
			color: #090909;
			line-height: 0.5rem;
		}
		.section-wrapper {
			padding: 0 0.2rem;
		}
		.section {
			margin-top: 0.28rem;
			.subtitle {
				font-size: 0.32rem;
				font-weight: 400;
			}
			.desc {
				/*width: 100% ;*/
				margin-top: 0.2rem;
				font-size: 0.32rem;
				font-family: PingFangSC-Regular, PingFang SC;
				font-weight: 400;
				color: #666666;
				line-height: 0.44rem;
			}
			
		}
		.pic-list {
			margin-top: 0.4rem;
			.item {
				margin-bottom: 0.2rem;
				&:last-child {
					margin-bottom: 0;
				}
			}
		}
	}
</style>