import router, {constantRoutes, getRouterList,asyncRoutes} from '@/router'


const state = {
    routes: [],
    accessedRoutes: [],
}

const mutations = {
    SET_ROUTES: (state, routes) => {
        state.accessedRoutes = routes;
        state.routes = constantRoutes.concat(routes)
        console.log(state.routes)
    },
}

const actions = {
    generateRoutes({commit}) {
        return new Promise(resolve => {
            let routes = getRouterList(JSON.parse(JSON.stringify(asyncRoutes)));
            commit('SET_ROUTES', routes)
            router.addRoutes(routes)
            resolve(routes)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
