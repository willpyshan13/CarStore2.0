<template>
    <el-dialog
        title="修改密码"
        :visible.sync="modifyInfo.show"
        append-to-body
        width="50%"
    >
        <el-form
            label-position="right"
            :model="form"
            :rules="rules"
            ref="form"
            label-width="100px"
        >
            <el-row>
                <el-col :span="24">
                    <el-form-item label="原密码" prop="originalPassword">
                        <el-input type="password" v-model="form.originalPassword"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="新密码" prop="password">
                        <el-input type="password" v-model="form.password"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="确认新密码" prop="confirmPassword">
                        <el-input type="password" v-model="form.confirmPassword"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer">
            <el-button @click="modifyInfo.show = false">取 消</el-button>
            <el-button type="primary" @click="handleConfirm">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
import { updateUserPwd } from "@/api/user";
export default {
    name: "ModifyPwd",
    props:['modifyInfo'],
    data(){
        let validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.form.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            form: {
                originalPassword:'',
                password:'',
                confirmPassword:'',
            },
            rules: {
                originalPassword:[
                    { required: true, message: '请输入原密码', trigger: 'blur' }
                ],
                password:[
                    { required: true, message: '请输入新密码', trigger: 'blur' }
                ],
                confirmPassword:[
                    { required: true,validator: validatePass, trigger: 'blur' }
                ]
            }
        }
    },
    methods:{
        handleConfirm() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    updateUserPwd({
                        originalPassword:this.form.originalPassword,
                        password:this.form.password,
                    }).then((res) => {
                        this.$message({
                            message: '密码修改成功',
                            type: 'success'
                        });
                        this.modifyInfo.show = false;
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        }
    }
}
</script>

<style scoped>

</style>
