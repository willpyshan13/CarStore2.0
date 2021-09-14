/**
 * @params {*} Obj  传给原生（Android或IOS）的参数
 * @fn "*" string  与原生（Android或IOS）约定好的方法名
 */
export default function dispatchAppFn({ fn, params } = {}) {
    let u = navigator.userAgent;
    let isAndroid = u.indexOf('Android') > -1 || u.indexOf('Linux') > -1; //android终端或者uc浏览器
    let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isAndroid) {
        let androidParams = ''
        for (let key in params) {
            androidParams += `"${params[key]}",`;
        }
        if (androidParams.length) {
            let params = androidParams.substring(0, androidParams.length - 1);
            return eval(`window.js_android[fn](${params})`);
        } else {
            return window.js_android[fn]();
        }
    } else if (isiOS) {
        window.webkit.messageHandlers[fn].postMessage(params);
    }
}