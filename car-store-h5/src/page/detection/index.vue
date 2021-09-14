<template>
	<div class="page detection_page">
		<div class="container">
			<dl-title btntxt=""></dl-title>
			<div>
				<div class="type_name">外观：</div>
				<div v-for="(item, index) in addVehicle.list">
					<div v-if="item.type === 'car_appearance'">
						<van-cell center :title="item.title">
							<template #right-icon>
								<van-switch v-model="item.yesNo" size="24" />
							</template>
						</van-cell>
					</div>
				</div>
				<div class="type_name">启动后怠速：</div>
				<div v-for="(item, index) in addVehicle.list">
					<div v-if="item.type === 'after_start'">
						<van-cell center :title="item.title">
							<template #right-icon>
								<van-switch v-model="item.yesNo" size="24" />
							</template>
						</van-cell>
					</div>
				</div>
				<div class="type_name">行驶中：</div>
				<div v-for="(item, index) in addVehicle.list">
					<div v-if="item.type === 'driving'">
						<van-cell center :title="item.title">
							<template #right-icon>
								<van-switch v-model="item.yesNo" size="24" />
							</template>
						</van-cell>
					</div>
				</div>
				<div class="type_name">其它建议：</div>
				<van-field class="border" v-model="addVehicle.remarks" rows="2" :border="true" type="textarea" placeholder="请输入内容" show-word-limit />
			</div>
			<div class="button_center">
				<van-button round type="info" @click="addVehicleTesting">确认提交</van-button>
			</div>
		</div>
	</div>
</template>

<script>
	import accountApi from "@/api/account";
	export default {
		name: "detectionPage",
		data() {
			return {
				checked: false,
				message: '',
				addVehicle: {
					list: [
						{ title: '轮胎有无受损', type: 'car_appearance', yesNo: true },
						{ title: '转向灯是否正常', type: 'car_appearance', yesNo: true },
						{ title: '前照灯是否正常（夜晚）', type: 'car_appearance', yesNo: true },
						{ title: '车辆有无异常抖动', type: 'after_start', yesNo: true },
						{ title: '仪表有无异常警告灯', type: 'after_start', yesNo: true },
						{ title: '转向是否正常', type: 'after_start', yesNo: true },
						{ title: '内部转向提醒灯是否正常', type: 'after_start', yesNo: true },
						{ title: '制动踏板测试有无明显异常', type: 'after_start', yesNo: true },
						{ title: '驾驶侧车窗（客户许可升降）是否正常', type: 'after_start', yesNo: true },
						{ title: '手制动电子制动是否正常', type: 'after_start', yesNo: true },
						{ title: '方向有无跑偏', type: 'driving', yesNo: true },
						{ title: '踩制动时方向有无跑偏', type: 'driving', yesNo: true },
						{ title: '踩制动时方向盘有无异常抖动', type: 'driving', yesNo: true },
						{ title: '行驶中噪音有无明显异常', type: 'driving', yesNo: true },
						{ title: '升降挡/换挡有无明显冲击/异常', type: 'driving', yesNo: true },
						{ title: '车灯光是否关闭', type: 'driving', yesNo: true },
						{ title: '钥匙有无归还', type: 'driving', yesNo: true },
					],
					remarks: '',
					technicianUuid: 'string',
					vehicleUserId: 'string',
				},
				isEditor: true,
			};
		},
		created() {
			// this.queryVehicleTesting()
		},
		activated() {},
		methods: {
			// 添加车辆检测信息
			addVehicleTesting() {
				accountApi.addVehicleTesting(this.addVehicle)
					.then(res => {
						this.$toast.success('提交成功');
					})
					.catch(e => {
						e.msg && this.$toast(e.msg);
					});
			},
			queryVehicleTesting() {
				accountApi.queryVehicleTesting()
					.then(res => {
						if (res.data !== null) {
							this.isEditor = false
							let obj = res.data
							this.addVehicle.list = JSON.parse(res.data.content)
							this.addVehicle.remarks = res.data.remarks
							this.addVehicle.technicianUuid = res.data.technicianUuid
							this.addVehicle.vehicleUserId = res.data.vehicleUserId
						}
				})
			}
		},
	};
</script>

<style lang="less" scoped>
	.detection_page {
		padding-bottom: 40px;
		padding-top: 50px;
		.type_name{
			font-size: 16px;
			padding: 20px 20px 10px 20px;
			color: #444;
		}
		.van-cell{
			color: #666;
		}
		.button_center{
			text-align: center;
			padding: 20px 0 0px;
		}
		.van-button--normal{
			width: 200px;
		}
		.border{
			border:1px solid #F1F1F1;
			margin: 0 20px 0;
			width: auto;
		}
		.van-switch{
			font-size: 16px !important;
		}
	}
</style>