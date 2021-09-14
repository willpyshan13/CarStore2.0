<template>
    <div class="navbar">
        <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>

        <breadcrumb class="breadcrumb-container"/>

        <div class="right-menu">
            <el-dropdown class="avatar-container" trigger="click">
                <div class="avatar-wrapper">
                    <img src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80" class="user-avatar">
                    <div class="username">{{ userInfo.userName }}</div>
                    <i class="el-icon-caret-bottom"/>
                </div>
                <el-dropdown-menu slot="dropdown" class="user-dropdown">
                    <el-dropdown-item @click.native="handleModifyPwd">
                        <span style="display:block;">修改密码</span>
                    </el-dropdown-item>
                    <el-dropdown-item @click.native="logout">
                        <span style="display:block;">退出登录</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>

        <!--修改密码弹出层-->
        <modify-pwd :modify-info="modifyInfo"></modify-pwd>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ModifyPwd from '@/components/ModifyPwd'

export default {
    components: {
        Breadcrumb,
        Hamburger,
        ModifyPwd
    },
    computed: {
        ...mapGetters([
            'sidebar',
            'userInfo'
        ])
    },
    data(){
        return {
            modifyInfo:{
                show:false
            }
        }
    },
    methods: {
        toggleSideBar() {
            this.$store.dispatch('app/toggleSideBar')
        },
        async logout() {
            await this.$store.dispatch('user/logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        },
        handleModifyPwd(){
            this.modifyInfo.show = true
        }
    }
}
</script>

<style lang="scss" scoped>
.navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .hamburger-container {
        line-height: 46px;
        height: 100%;
        float: left;
        cursor: pointer;
        transition: background .3s;
        -webkit-tap-highlight-color: transparent;

        &:hover {
            background: rgba(0, 0, 0, .025)
        }
    }

    .breadcrumb-container {
        float: left;
    }

    .right-menu {
        float: right;
        height: 100%;
        line-height: 50px;

        &:focus {
            outline: none;
        }

        .right-menu-item {
            display: inline-block;
            padding: 0 8px;
            height: 100%;
            font-size: 18px;
            color: #5a5e66;
            vertical-align: text-bottom;

            &.hover-effect {
                cursor: pointer;
                transition: background .3s;

                &:hover {
                    background: rgba(0, 0, 0, .025)
                }
            }
        }

        .avatar-container {
            margin-right: 30px;

            .avatar-wrapper {
                position: relative;
                display: flex;
                align-items: center;
                cursor: pointer;
                .username{
                    padding-left:10px;
                }
                .user-avatar {
                    cursor: pointer;
                    display: block;
                    width: 40px;
                    height: 40px;
                    border-radius: 10px;
                }

                .el-icon-caret-bottom {
                    cursor: pointer;
                    position: absolute;
                    right: -20px;
                    top: 50%;
                    transform: translateY(-50%);
                    font-size: 12px;
                }
            }
        }
    }
}
</style>
