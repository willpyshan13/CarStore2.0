// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Vant from 'vant';
import 'vant/lib/index.css';
import router from './router'
import './mixin'

Vue.use(Vant);

Vue.config.productionTip = false

import title from './components/title'
import foot from './components/foot'
import uploadImg from './components/upload-img'

Vue.component('dl-title', title)
Vue.component('dl-foot', foot)
Vue.component('upload-img', uploadImg)

// router.beforeEach((to, from, next) => {
//     console.log(to, from, 'form')
//     if (to.path === from.path) {
//         history.go(-1)
//     } else {
//         next()
//     }
// })

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: { App },
    template: '<App/>'
})