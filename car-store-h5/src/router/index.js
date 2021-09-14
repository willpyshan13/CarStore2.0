import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
	mode: 'history',
	routes: [{
			path: '/login',
			name: 'login',
			component: () =>
				import('@/page/login'),
			meta: {
				title: '登录'
			}
		},
		{
			path: '/',
			name: 'home',
			component: () =>
				import('@/page/home'),
			meta: {
				title: '嘟灵'
			},
		},
		{
			path: '/ask',
			name: 'ask',
			component: () =>
				import('@/page/ask/index'),
			meta: {
				title: ''
			},
		},
		{
			path: '/myOrder',
			name: 'myOrder',
			component: () =>
				import('@/page/myOrder'),
			meta: {
				title: ''
			},
		},

		{
			path: '/anliDetail',
			name: 'anliDetail',
			component: () =>
				import('@/page/anliDetail'),
			meta: {
				title: '案例详情'
			},
		},
		{
			path: '/anlifabu',
			name: 'anlifabu',
			component: () =>
				import('@/page/anlifabu'),
			meta: {
				title: '案例发布'
			},
		},
		{
			path: '/orderDetailEnd',
			name: 'orderDetailEnd',
			component: () =>
				import('@/page/orderDetailEnd'),
			meta: {
				title: '订单详情'
			},
		},
		{
			path: '/orderDetailEdit',
			name: 'orderDetailEdit',
			component: () =>
				import('@/page/orderDetailEdit'),
			meta: {
				title: '订单详情'
			},
		},
		{
			path: '/questionAns',
			name: 'questionAns',
			component: () =>
				import('@/page/questionAns'),
			meta: {
				title: '回答问题'
			},
		},
		{
			path: '/gerenxinxi',
			name: 'gerenxinxi',
			component: () =>
				import('@/page/gerenxinxi'),
			meta: {
				title: '个人信息'
			},
		},
		{
			path: '/shopCenter',
			name: 'shopCenter',
			component: () =>
				import('@/page/shopCenter'),
			meta: {
				title: '个人信息'
			},
		},
		{
			path: '/wallet',
			name: 'wallet',
			component: () =>
				import('@/page/wallet'),
			meta: {
				title: ''
			},
		},
		{
			path: '/orderList',
			name: 'orderList',
			component: () =>
				import('@/page/orderList'),
			meta: {
				title: '订单列表'
			},
		},
		{
			path: '/daijiaOrder',
			name: 'daijiaOrder',
			component: () =>
				import('@/page/daijiaOrder'),
			meta: {
				title: '代驾订单'
			},
		},
		{
			path: '/baoyang',
			name: 'baoyang',
			component: () =>
				import('@/page/baoyang'),
			meta: {
				title: '维修保养'
			},
		},
		{
			path: '/payOrder',
			name: 'payOrder',
			component: () =>
				import('@/page/payOrder'),
			meta: {
				title: '支付订单'
			},
		},
		{
			path: '/anliOrder',
			name: 'anliOrder',
			component: () =>
				import('@/page/anliOrder'),
			meta: {
				title: '案例订单'
			},
		},
		{
			path: '/jishiWenDa',
			name: 'jishiWenDa',
			component: () =>
				import('@/page/jishiWenDa'),
			meta: {
				title: '技师问答'
			},
		},
		{
			path: '/jishiAnliDetail',
			name: 'jishiAnliDetail',
			component: () =>
				import('@/page/jishiAnliDetail'),
			meta: {
				title: '技师案例详情'
			},
		},
		{
			path: '/onlineDetail',
			name: 'onlineDetail',
			component: () =>
				import('@/page/onlineDetail'),
			meta: {
				title: '线上咨询详情'
			},
		},
		{
			path: '/accountInfor',
			name: 'accountInfor',
			component: () =>
				import('@/page/accountInfor'),
			meta: {
				title: '账户信息'
			},
		},
		{
			path: '/accountInforJishi',
			name: 'accountInforJishi',
			component: () =>
				import('@/page/accountInforJishi'),
			meta: {
				title: '账户信息'
			},
		},

		{
			path: '/relevancyShop',
			name: 'relevancyShop',
			component: () =>
				import('@/page/relevancyShop'),
			meta: {
				title: '关联店铺'
			},
		},
		{
			path: '/jishiAdmin',
			name: 'jishiAdmin',
			component: () =>
				import('@/page/jishiAdmin'),
			meta: {
				title: '技师管理'
			},
		},
		{
			path: '/putawayProduct',
			name: 'putawayProduct',
			component: () =>
				import('@/page/putawayProduct'),
			meta: {
				title: '上架商品'
			},
		},
		{
			path: '/putawayProductDetail',
			name: 'putawayProductDetail',
			component: () =>
				import('@/page/putawayProductDetail'),
			meta: {
				title: '商品详情'
			},
		},
		{
			path: '/putawayProductType',
			name: 'putawayProductType',
			component: () =>
				import('@/page/putawayProductType'),
			meta: {
				title: '上架商品'
			},
		},
		{
			path: '/fabuProduct',
			name: 'fabuProduct',
			component: () =>
				import('@/page/fabuProduct'),
			meta: {
				title: '上架商品'
			},
		},
		{
			path: '/fabuAnli',
			name: 'fabuAnli',
			component: () =>
				import('@/page/fabuAnli'),
			meta: {
				title: '发布案例'
			},
		},
		{
			path: '/daijiaDetail',
			name: 'daijiaDetail',
			component: () =>
				import('@/page/daijiaDetail'),
			meta: {
				title: '代驾详情'
			},
		},
		{
			path: '/jishiRgister',
			name: 'jishiRgister',
			component: () =>
				import('@/page/jishiRgister'),
			meta: {
				title: '技师注册'
			},
		},
		{
			path: '/shopRgister',
			name: 'shopRgister',
			component: () =>
				import('@/page/shopRgister'),
			meta: {
				title: '店铺注册'
			},
		},
		{
			path: '/weixiuBrand',
			name: 'weixiuBrand',
			component: () =>
				import('@/page/weixiuBrand'),
			meta: {
				title: '维修品牌'
			},
		},
		// {
		//     path: '/relevancyShop1',
		//     name: 'relevancyShop1',
		//     component: () =>
		//         import ('@/page/relevancyShop1'),
		//     meta: {
		//         title: '关联店铺'
		//     },
		// },
		{
			path: '/selectShop',
			name: 'selectShop',
			component: () =>
				import('@/page/selectShop'),
			meta: {
				title: '选择店铺'
			},
		},
		{
			path: '/selectShopName',
			name: 'selectShopName',
			component: () =>
				import('@/page/selectShopName'),
			meta: {
				title: '选择店铺'
			},
		},
		{
			path: '/dtcAddOrEdit',
			name: 'dtcAddOrEdit',
			component: () =>
				import('@/page/dtcAddOrEdit'),
			meta: {
				title: '补录DTC'
			},
		},
		{
			path: '/dtcDetail',
			name: 'dtcDetail',
			component: () =>
				import('@/page/dtcDetail'),
			meta: {
				title: 'DTC详情'
			},
		},
		{
			path: '/dtcDetails',
			name: 'dtcDetails',
			component: () =>
				import('@/page/dtcDetails'),
			meta: {
				title: 'DTC详情'
			},
		},
		{
			path: '/dtcList',
			name: 'dtcList',
			component: () =>
				import('@/page/dtcList'),
			meta: {
				title: 'DTC列表'
			},
		},
		{
			path: '/dtcDetailList',
			name: 'dtcDetailList',
			component: () =>
				import('@/page/dtcDetailList'),
			meta: {
				title: 'DTC列表详情'
			},
		},
		{
			path: '/loading',
			name: 'loading',
			component: () =>
				import('@/page/loading'),
			meta: {
				title: '加载页面'
			},
		},
		{
			path: '/scenceOrder',
			name: 'scenceOrder',
			component: () =>
				import('@/page/scenceOrder'),
			meta: {
				title: '现场服务'
			},
		},
		{
			path: '/scenceOrderDetail',
			name: 'scenceOrderDetail',
			component: () =>
				import('@/page/scenceOrderDetail'),
			meta: {
				title: '现场服务'
			},
		},
		{
			path: '/scenceOrderDetails',
			name: 'scenceOrderDetails',
			component: () =>
				import('@/page/scenceOrderDetails'),
			meta: {
				title: '现场服务'
			},
		},
		{
			path: '/jiaoyuDetail',
			name: 'jiaoyuDetail',
			component: () =>
				import('@/page/jiaoyuDetail'),
			meta: {
				title: '教育培训详情'
			},
		},
		{
			path: '/kechengDetail',
			name: 'kechengDetail',
			component: () =>
				import('@/page/kechengDetail'),
			meta: {
				title: '课程详情'
			},
		},
		{
			path: '/trainingNeed',
			name: 'trainingNeed',
			component: () =>
				import('@/page/trainingNeed'),
			meta: {
				title: '培训需求'
			},
		},
		{
			path: '/myConsult',
			name: 'myConsult',
			component: () =>
				import('@/page/myConsult'),
			meta: {
				title: '我的提问'
			},
		},
		{
			path: '/myCase',
			name: 'myCase',
			component: () =>
				import('@/page/myCase'),
			meta: {
				title: '我的案例'
			},
		},
		{
			path: '/caseDetail',
			name: 'caseDetail',
			component: () =>
				import('@/page/caseDetail'),
			meta: {
				title: '案例详情'
			},
		},
		{
			path: '/consultDtail',
			name: 'consultDtail',
			component: () =>
				import('@/page/consultDtail'),
			meta: {
				title: '提问详情'
			},
		},
		{
			path: '/myCourse',
			name: 'myCourse',
			component: () =>
				import('@/page/myCourse'),
			meta: {
				title: '我的课程'
			},
		},
		{
			path: '/myjiaocheng',
			name: 'myjiaocheng',
			component: () =>
				import('@/page/myjiaocheng'),
			meta: {
				title: '我的教程'
			},
		},
		{
			path: '/buyServe',
			name: 'buyServe',
			component: () =>
				import('@/page/buyServe'),
			meta: {
				title: ''
			},
		},
		{
			path: '/shareJsDetail',
			name: 'shareJsDetail',
			component: () =>
				import('@/page/shareJsDetail'),
			meta: {
				title: '共享技师详情'
			},
		},
		{
			path: '/payOrderJy',
			name: 'payOrderJy',
			component: () =>
				import('@/page/payOrderJy'),
			meta: {
				title: '支付详情'
			},
		},
		{
			path: '/payxianchangOrder',
			name: 'payxianchangOrder',
			component: () =>
				import('@/page/payxianchangOrder'),
			meta: {
				title: '支付详情'
			},
		},
		{
			path: '/caseOrderDetail',
			name: 'caseOrderDetail',
			component: () =>
				import('@/page/orderDetail/caseOrderDetail'),
			meta: {
				title: '案例订单详情'
			},
		},
		{
			path: '/consultOrderDetail',
			name: 'consultOrderDetail',
			component: () =>
				import('@/page/orderDetail/consultOrderDetail'),
			meta: {
				title: '咨询订单详情'
			},
		},
		{
			path: '/auditOrderDetail',
			name: 'auditOrderDetail',
			component: () =>
				import('@/page/orderDetail/auditOrderDetail'),
			meta: {
				title: '咨询订单详情'
			},
		},
		{
			path: '/audit',
			name: 'audit',
			component: () =>
				import('@/page/audit/audit'),
			meta: {
				title: '旁听详情'
			},
		},
		{
			path: '/sharePage',
			name: 'sharePage',
			component: () =>
				import('@/page/sharePage/index'),
			meta: {
				title: '分享'
			}
		},
		{
			path: '/addDetection',
			name: 'addDetection',
			component: () =>
				import('@/page/detection/index'),
			meta: {
				title: '添加车辆检测'
			}
		}
	]
})