<template>
	<div class="share_page">
		<img src="../../asstes/images/beijingtu.jpg" />
		<div class="btn"><img @click="downloadApp" src="../../asstes/images/lijixiazai.png" /></div>
	</div>
</template>

<script>
	export default {
		name: "sharePage",
		data() {
			return {};
		},
		methods: {
			downloadApp() {
				var u = navigator.userAgent;
				var isWeixin = u.toLowerCase().indexOf('micromessenger') !== -1; // 微信内
				var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端
				var isIOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
				if(isWeixin) {
					this.$toast.fail('请在浏览器上打开')
				} else {
					//android端
					if(isAndroid) {
						//安卓app的scheme协议
						window.location.href = 'yanxinstore://';
						setTimeout(function() {
							let hidden = window.document.hidden || window.document.mozHidden || window.document.msHidden || window.document.webkitHidden
							if(typeof hidden == "undefined" || hidden == false) {
								window.location.href = "https://admin.dlvehicle.com/apk/CarStoreProject_Pro_0408_1.apk";
							}
						}, 2000);
					}
					//ios端
					if(isIOS) {
						//ios的scheme协议
						window.location.href = 'https://store.dlvehicle.com/apple-app-site-association'
						setTimeout(function() {
							let hidden = window.document.hidden || window.document.mozHidden || window.document.msHidden || window.document.webkitHidden
							if(typeof hidden == "undefined" || hidden == false) {
								//App store下载地址
								window.location.href = "https://apps.apple.com/cn/app/%E5%98%9F%E4%B8%80%E5%AE%B6%E6%9C%8D%E5%8A%A1/id1556363705";
							}
						}, 2000);
					}
				}
			}
		},
	};
</script>

<style lang="less" scoped>
	.share_page {
		position: relative;
		.btn {
			text-align: center;
			img {
				position: absolute;
				bottom: 40px;
				left: 10%;
				width: 80%;
			}
		}
	}
</style>