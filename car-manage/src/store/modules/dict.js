import {queryDictList} from '@/api/dict'

const getDefaultState = () => {
    return {
        dictMap:{},
        checkSts:[
            {
                lableDesc:"待审核",
                uuid:0
            },
            {
                lableDesc:"审核通过",
                uuid:1
            },
            {
                lableDesc:"审核驳回",
                uuid:2
            }
        ],
        sellSts:[
            {
                lableDesc:"库存",
                uuid:0
            },
            {
                lableDesc:"在售",
                uuid:1
            }
        ],
        userRole:[
            {
                lableDesc:"店铺",
                uuid:0
            },
            {
                lableDesc:"技师",
                uuid:1
            }
        ],
        orderType:[
            {
                lableDesc:"点评",
                uuid:0
            },
            {
                lableDesc:"提问",
                uuid:1
            },
            {
                lableDesc:"回答",
                uuid:2
            },
            {
                lableDesc:"案例",
                uuid:3
            },
            {
                lableDesc:"旁听",
                uuid:4
            }
        ],
        orderSts:[
            {
                lableDesc:"待支付",
                uuid:0
            },
            {
                lableDesc:"已支付",
                uuid:1
            },
            {
                lableDesc:"已取消",
                uuid:2
            },
            {
                lableDesc:"退款中",
                uuid:3
            },
            {
                lableDesc:"退款成功",
                uuid:4
            },
            {
                lableDesc:"退款失败",
                uuid:5
            },
            {
                lableDesc:"已完成",
                uuid:6
            }
        ],
        makeOrderSts:[
            {
                lableDesc:"待付款",
                uuid:1
            },
            {
                lableDesc:"待服务",
                uuid:2
            },
            {
                lableDesc:"已完成",
                uuid:3
            },
            {
                lableDesc:"退款中",
                uuid:4
            },
            {
                lableDesc:"已退款",
                uuid:5
            },
            {
                lableDesc:"待接单",
                uuid:6
            }
        ],
        afterSaleSts:[
            {
                lableDesc:"等待买家退货",
                uuid:0
            },
            {
                lableDesc:"已退货 待收货",
                uuid:1
            },
            {
                lableDesc:"已收货 换货中",
                uuid:2
            },
            {
                lableDesc:"系统退款中",
                uuid:3
            },
            {
                lableDesc:"已完成",
                uuid:4
            },
            {
                lableDesc:"已取消",
                uuid:5
            }
        ],
        evaluateSts:[
            {
                lableDesc:"未评论",
                uuid:0
            },
            {
                lableDesc:"已评论",
                uuid:1
            },
            {
                lableDesc:"好评",
                uuid:2
            },
            {
                lableDesc:"中评",
                uuid:3
            },
            {
                lableDesc:"差评",
                uuid:4
            }
        ],
        payType:[
            {
                lableDesc:"微信支付",
                uuid:0
            },
            {
                lableDesc:"支付宝支付",
                uuid:1
            }
        ],
        deliveryMode:[
            {
                lableDesc:"快递",
                uuid:0
            },
            {
                lableDesc:"送货上门",
                uuid:1
            }
        ],
        refundType:[
            {
                lableDesc:"线上退款",
                uuid:0
            },
            {
                lableDesc:"线下退款",
                uuid:1
            }
        ],
        serviceType:[
            {
                lableDesc:"即时",
                uuid:0
            },
            {
                lableDesc:"预约",
                uuid:1
            }
        ],
        answerSts:[
            {
                lableDesc:"未答复",
                uuid:0
            },
            {
                lableDesc:"已答复",
                uuid:1
            }
        ],
        courseType:[
            {
                lableDesc:"直播",
                uuid:0
            },
            {
                lableDesc:"图文",
                uuid:1
            }
        ],
        queryType:[
            {
                lableDesc:"未抢订单",
                uuid:0
            },
            {
                lableDesc:"已抢订单",
                uuid:1
            },
            {
                lableDesc:"发布订单",
                uuid:2
            }
        ],
        grabbingOrdersSts:[
            {
                lableDesc:"未抢",
                uuid:0
            },
            {
                lableDesc:"已抢",
                uuid:1
            }
        ]
    }
}

const state = getDefaultState()

const mutations = {
    SET_DICT: (state, info) => {
        state.dictMap[info.type] = info.data || []
    }
}

const actions = {
    getDict({commit,state}, type) {
        return new Promise((resolve, reject) => {
            if(state.dictMap[type] && state.dictMap[type].length){
                resolve(state.dictMap[type])
            }else{
                queryDictList(type).then(response => {
                    const { data } = response
                    commit('SET_DICT', {
                        type:type,
                        data:data
                    })
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            }
        })
    },
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

