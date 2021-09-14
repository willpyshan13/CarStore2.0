<template>
	<div class="page">
		<div class="container">
			<dl-title btntxt=""></dl-title>
			<div class="content">
				<div class="space-block"></div>
				<van-list v-model="loading" :finished="finished" finished-text="" @load="init">
					<div class="my-question-list">
						<div class="my-question-item-wrapper" v-for="(item, index) in list" :key="index" @click="handleDetail(item)">
							<div class="my-question-item">
								<div class="title">
									{{ item.caseName }}
								</div>
								<div class="intro" v-html="item.ideaProcess">
								</div>
							</div>
							<div class="space-block"></div>
						</div>
					</div>
					<!--{{list.length}}-->
					<div v-if="finished && !list.length">
						<van-empty description="无数据" />
					</div>
				</van-list>
			</div>
		</div>
	</div>
</template>
<script>
	import orderApi from "@/api/order";
	export default {
		data() {
			return {
				documentTitle: "我的案例",
				loading: false,
				finished: false,
				list: [],
				pageNum: 0
			};
		},
		methods: {
			init() {
				this.pageNum = this.pageNum + 1;
				let params = {
					orderType:3,
					pageNum: this.pageNum,
					state: 0
				}
				orderApi.queryOrderCaseList(params).then((res) => {
					let list = res.data || [];
					// 加载状态结束
					this.loading = false;
					// 数据全部加载完成
					if(this.pageNum == 1) {
						this.list = list;
					} else {
						this.list = this.list.concat(list);
					}
					if(!list.length) {
						this.finished = true;
					}
					console.log(this.list)
				}).catch(e => {
					e.msg && this.$toast(e.msg);
				});
			
			},
			handleDetail(item) {
				console.log(item)
				this.$router.push({
					path: "/caseOrderDetail",
					query: {
						uuid: item.uuid
					}
				});
			}
		}
	};
</script>
<style>
 .intro img{
 display: none;
 }
</style>
<style lang="less" scoped>
	.page {}
	
	.content {
		padding-top: 50px;
	}
	.space-block{
		background: #EDF5FB;
		height: 10px;
	}
	.my-question-item {
		padding: 0.3rem 0.3rem 0.4rem;
		background: #ffffff;
		.title {
			font-size: 0.32rem;
			font-family: PingFangSC-Regular, PingFang SC;
			font-weight: 400;
			color: #090909;
			line-height: 0.44rem;
		}
		.intro {
			margin-top: 0.32rem;
			font-size: 0.28rem;
			font-family: PingFangSC-Regular, PingFang SC;
			font-weight: 400;
			color: #666666;
			line-height: 0.4rem;
		}
	}
</style>