import Vue from 'vue'

Vue.mixin({
    computed: {
        baseSrc() {
            return this.$store.state.baseSrc
        }
    },
    methods: {
        setTime(time) {
            const date = time ? new Date(time) : new Date()
            const opt = {
                "y": date.getFullYear(), // 年
                "M": this.dubble(date.getMonth() + 1), // 月
                "d": this.dubble(date.getDate()), // 日
                "h": this.dubble(date.getHours()), // 时
                "m": this.dubble(date.getMinutes()), // 分
                "s": this.dubble(date.getSeconds()) // 秒
            };
            return `${opt.y}/${opt.M}/${opt.d}`
        },
        dubble(num) {
            if (num < 10) {
                return '0' + num
            } else {
                return num
            }
        },
        // 返回上一步
        finishPage() {
            if (this.isIOS()) {
                window.webkit.messageHandlers
                    .finishPage
                    .postMessage({ url: this.$route.name || "finishPage" })
                    // this.$router.go(-1);
            } else {
                window.js_android.finishPage()
            }
        },
        // 返回首页
        finishAll() {
            if (this.isIOS()) {
                window.webkit.messageHandlers
                    .finishAll
                    .postMessage({ url: this.$route.name || "finishAll" })
            } else {
                window.js_android.finishAll()
            }
        },
        // 获取uuid
        getUuid() {
//      	let textUUid = "b3a8d40e7142447bac8fd646179b1099";
//      	return textUUid
            if (this.isIOS()) {
                return this.$route.query && this.$route.query.uuid
            } else {
                return JSON.parse(window.js_android.getParam()).uuid;
            }
        },
        // 获取教程标题
        getJcTitle() {
            if (this.isIOS()) {
                return this.$route.query.courseName
            } else {
                return JSON.parse(window.js_android.getParam()).courseName;
            }
        },
        isIOS() {
            let u = navigator.userAgent;
            let isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
            let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid) {
                return false;
            } else if (isiOS) {
                return true;
            }
        },
        handleBack() {
          this.dispatchAppFn({
            fn: "finishPage"
          });
        },
    }
})