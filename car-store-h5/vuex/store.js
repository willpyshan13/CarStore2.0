import vue from 'vue'
import Vuex from 'vuex'
vue.use(Vuex)
const state = {
    count: 1,
    token: ''
}

const mutations = {
    add(state) {
        state.count++;
    },
    reduce(state) {
        state.count--;
    },
    set_token(state, token) {
        state.token = token
    },
}

export default new Vuex.Store({
    state,
    mutations
})