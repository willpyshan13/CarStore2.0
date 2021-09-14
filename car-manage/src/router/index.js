import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'
Vue.use(Router)


let map = new Map()

/* Layout */
map.set("Layout", () => import('@/layout'))

// 店铺
map.set("Store", () => import('@/views/store/index'))
map.set("StoreDetail", () => import('@/views/store/detail'))

// 车主
map.set("Owner", () => import('@/views/owner/index'))
map.set("OwnerDetail", () => import('@/views/owner/detail'))

// 技师
map.set("Artificer", () => import('@/views/artificer/index'))
map.set("ArtificerDetail", () => import('@/views/artificer/detail'))

// 商品
map.set("Product", () => import('@/views/product/index'))
map.set("ProductDetail", () => import('@/views/product/detail'))

// 内容管理
map.set("Post", () => import('@/views/post/index'))


// DTC管理
map.set("Dtc", () => import('@/views/dtc/index'))
map.set("DtcDetail", () => import('@/views/dtc/detail'))

// 提现管理
map.set("Withdraw", () => import('@/views/withdraw/index'))
map.set("WithdrawDetail", () => import('@/views/withdraw/detail'))

// 系统管理
map.set("User", () => import('@/views/system/user'))
map.set("Role", () => import('@/views/system/role'))
map.set("Log", () => import('@/views/system/log'))


// 订单组件
map.set("GoodsOrder", () => import('@/views/order/goods/index'))
map.set("GoodsOrderDetail", () => import('@/views/order/goods/detail'))

map.set("DrivingOrder", () => import('@/views/order/driving/index'))
map.set("DrivingOrderDetail", () => import('@/views/order/driving/detail'))

map.set("CaseOrder", () => import('@/views/order/case/index'))
map.set("CaseOrderDetail", () => import('@/views/order/case/detail'))

map.set("ConsultOrder", () => import('@/views/order/consult/index'))
map.set("ConsultOrderDetail", () => import('@/views/order/consult/detail'))


map.set("DtcOrder", () => import('@/views/order/dtc/index'))
map.set("DtcOrderDetail", () => import('@/views/order/dtc/detail'))

map.set("SceneOrder", () => import('@/views/order/scene/index'))
map.set("SceneOrderDetail", () => import('@/views/order/scene/detail'))


map.set("CourseOrder", () => import('@/views/order/course/index'))
map.set("CourseOrderDetail", () => import('@/views/order/course/detail'))


map.set("MakeOrder", () => import('@/views/order/make/index'))
map.set("MakeOrderDetail", () => import('@/views/order/make/detail'))

map.set("EduClassify", () => import('@/views/edu/classify/index'))
map.set("EduCourse", () => import('@/views/edu/course/index'))
map.set("EduCourseDetail", () => import('@/views/edu/course/detail'))

map.set("HuliOrder", () => import('@/views/huli/index'))
/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
    {
        path: '/login',
        component: () => import('@/views/login/index'),
        hidden: true
    },
    {
        path: '/404',
        component: () => import('@/views/404'),
        hidden: true
    },
    {
        path: '/dashboard',
        component: map.get('Layout'),
        redirect: '/dashboard/index',
        children:[
            {
                path: 'index',
                name: 'Dashboard',
                component: () => import('@/views/dashboard/index'),
            }
        ],
        hidden: true
    },
    {
        path: '/',
        component: map.get('Layout'),
        redirect: () => {
            let accessedRoutes = store.getters.accessedRoutes;
            let path = accessedRoutes.length ? accessedRoutes[0].path : '/dashboard'
            return path
        }
    }
]


export const asyncRoutes = [
    {
        path: '/store',
        component: 'Layout',
        redirect: '/store/index',
        children: [{
            path: 'index',
            name: 'Store',
            component: 'Store',
            meta: {title: '店铺管理', icon: 'store'}
        },{
            path: 'detail',
            name: 'StoreDetail',
            component:'StoreDetail',
            hidden:true,
            meta: {title: '店铺详情',activeMenu:"/store/index"}
        }]
    },
    {
        path: '/huli',
        component: 'Layout',
        redirect: '/huli/index',
        children: [{
            path: 'index',
            name: 'HuliOrder',
            component: 'HuliOrder',
            meta: {title: '养护管理', icon: 'product'}
        }]
    },
    {
        path: '/owner',
        component: 'Layout',
        redirect: '/owner/index',
        children: [{
            path: 'index',
            name: 'Owner',
            component: 'Owner',
            meta: {title: '车主管理', icon: 'owner'}
        },{
            path: 'detail',
            name: 'OwnerDetail',
            component: 'OwnerDetail',
            hidden:true,
            meta: {title: '车主详情',activeMenu:"/owner/index"}
        }]
    },
    {
        path: '/artificer',
        component: 'Layout',
        redirect: '/artificer/index',
        children: [{
            path: 'index',
            name: 'Artificer',
            component:'Artificer',
            meta: {title: '技师管理', icon: 'artificer'}
        },{
            path: 'detail',
            name: 'ArtificerDetail',
            component:'ArtificerDetail',
            hidden:true,
            meta: {title: '技师详情',activeMenu:"/artificer/index"}
        }]
    },
    {
        path: '/product',
        component: 'Layout',
        redirect: '/product/index',
        children: [{
            path: 'index',
            name: 'Product',
            component: 'Product',
            meta: {title: '商品管理', icon: 'product'}
        },{
            path: 'detail',
            name: 'ProductDetail',
            component: 'ProductDetail',
            hidden:true,
            meta: {title: '商品详情',activeMenu:"/product/index"}
        }]
    },
    {
        path: '/order',
        component: 'Layout',
        redirect: '/order/goods/index',
        alwaysShow: true, //一直显示根路由
        meta: {title: '订单管理', icon: 'order'},
        children: [{
            path: 'goods/index',
            name: 'GoodsOrder',
            component:'GoodsOrder',
            meta: {title: '商品订单'}
        },{
            path: 'goods/detail',
            name: 'GoodsOrderDetail',
            component: 'GoodsOrderDetail',
            hidden:true,
            meta: {title: '商品订单详情',activeMenu:"/order/goods/index"}
        },{
            path: 'driving/index',
            name: 'DrivingOrder',
            component:'DrivingOrder',
            meta: {title: '代驾订单'}
        },{
            path: 'driving/detail',
            name: 'DrivingOrderDetail',
            component: 'DrivingOrderDetail',
            hidden:true,
            meta: {title: '代驾订单详情',activeMenu:"/order/driving/index"}
        },{
            path: 'case/index',
            name: 'CaseOrder',
            component:'CaseOrder',
            meta: {title: '案例订单'}
        },{
            path: 'case/detail',
            name: 'CaseOrderDetail',
            component: 'CaseOrderDetail',
            hidden:true,
            meta: {title: '案例订单详情',activeMenu:"/order/case/index"}
        },{
            path: 'consult/index',
            name: 'ConsultOrder',
            component:'ConsultOrder',
            meta: {title: '问答订单'}
        },{
            path: 'consult/detail',
            name: 'ConsultOrderDetail',
            component: 'ConsultOrderDetail',
            hidden:true,
            meta: {title: '问答订单详情',activeMenu:"/order/consult/index"}
        },{
            path: 'dtc/index',
            name: 'DtcOrder',
            component:'DtcOrder',
            meta: {title: 'DTC订单'}
        },{
            path: 'dtc/detail',
            name: 'DtcOrderDetail',
            component: 'DtcOrderDetail',
            hidden:true,
            meta: {title: 'DTC订单详情',activeMenu:"/order/dtc/index"}
        },{
            path: 'scene/index',
            name: 'SceneOrder',
            component:'SceneOrder',
            meta: {title: '现场支持订单'}
        },{
            path: 'scene/detail',
            name: 'SceneOrderDetail',
            component: 'SceneOrderDetail',
            hidden:true,
            meta: {title: '现场支持订单详情',activeMenu:"/order/scene/index"}
        },{
            path: 'course/index',
            name: 'CourseOrder',
            component:'CourseOrder',
            meta: {title: '课程订单'}
        },{
            path: 'course/detail',
            name: 'CourseOrderDetail',
            component: 'CourseOrderDetail',
            hidden:true,
            meta: {title: '课程订单详情',activeMenu:"/order/course/index"}
        }
        ,{
            path: 'make/index',
            name: 'MakeOrder',
            component:'MakeOrder',
            meta: {title: '技师预约订单'}
        },{
            path: 'make/detail',
            name: 'MakeOrderDetail',
            component: 'MakeOrderDetail',
            hidden:true,
            meta: {title: '技师预约订单详情',activeMenu:"/order/make/index"}
        }]
    },
    {
        path: '/post',
        component: 'Layout',
        redirect: '/post/index',
        children: [{
            path: 'index',
            name: 'Post',
            component:'Post',
            meta: {title: '内容管理', icon: 'post'}
        }]
    },
    {
        path: '/dtc',
        component: 'Layout',
        redirect: '/dtc/index',
        children: [{
            path: 'index',
            name: 'Dtc',
            component:'Dtc',
            meta: {title: '故障码维护', icon: 'example'}
        },{
            path: 'detail',
            name: 'DtcDetail',
            component: 'DtcDetail',
            hidden:true,
            meta: {title: '故障码详情',activeMenu:"/dtc/index"}
        }]
    },
    {
        path: '/withdraw',
        component: 'Layout',
        redirect: '/withdraw/index',
        children: [{
            path: 'index',
            name: 'Withdraw',
            component:'Withdraw',
            meta: {title: '提现管理', icon: 'withdraw'}
        },{
            path: 'detail',
            name: 'WithdrawDetail',
            component: 'WithdrawDetail',
            hidden:true,
            meta: {title: '提现详情',activeMenu:"/withdraw/index"}
        }]
    },
    {
        path: '/edu',
        component: 'Layout',
        redirect: '/edu/classify/index',
        alwaysShow: true, //一直显示根路由
        meta: {title: '教育培训', icon: 'order'},
        children: [{
            path: 'classify/index',
            name: 'EduClassify',
            component:'EduClassify',
            meta: {title: '分类管理'}
        },{
            path: 'course/index',
            name: 'EduCourse',
            component:'EduCourse',
            meta: {title: '课程管理'}
        },{
            path: 'course/detail',
            name: 'EduCourseDetail',
            component:'EduCourseDetail',
            hidden:true,
            meta: {title: '课程详情',activeMenu:"/edu/course/index"}
        }]
    },
    {
        path: '/system',
        component: 'Layout',
        redirect: '/system/user',
        name: 'System',
        alwaysShow: true, //一直显示根路由
        meta: {title: '系统管理', icon: 'password'},
        children: [
            {
                path: 'user',
                name: 'User',
                component:'User',
                meta: {title: '用户管理'}
            },
            {
                path: 'role',
                name: 'Role',
                component:'Role',
                meta: {title: '角色管理'}
            },
            {
                path: 'log',
                name: 'Log',
                component: 'Log',
                meta: {title: '日志管理'}
            }
        ]
    },
    // 404 page must be placed at the end !!!
    {path: '*', redirect: '/404', hidden: true}
]

export const getRouterList = (userRouter) => {
    digui(userRouter)
    function digui(userRouter = []) {
        userRouter.forEach(function(item, index) {
            if (item.children != null && item.children.length > 0) {
                digui(item.children)
            }
            if(item.component){
                item.component = map.get(item.component)//通过映射找到我们定义好的组件
            }
        })
    }
    return userRouter
}

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router
