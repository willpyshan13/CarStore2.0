<template>
    <div class="app-container">
        <div class="page-detail">
            <el-form
                v-loading="loading"
                label-position="right"
                :model="form"
                :rules="rules"
                ref="form"
                label-width="120px"
            >
                <div class="section">
                    <div class="title">
                        {{uuid ? '编辑故障码' : '新增故障码'}}
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="故障标题" prop="dtcDefinition" ref="dtcDefinition">
                                    <el-input clearable v-model="form.dtcDefinition"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="故障代码" prop="dtcCode" ref="dtcCode">
                                    <el-input clearable v-model="form.dtcCode"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="品牌" prop="dtcBrandUuid" ref="dtcBrandUuid">
                                    <el-select clearable filterable  v-model="form.dtcBrandUuid">
                                        <el-option v-for="(item,index) in repairBrand" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="DTC类型" prop="dtcType" ref="dtcType">
                                    <el-select clearable v-model="form.dtcType">
                                        <el-option v-for="(item,index) in dtcType" :key="item.uuid"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
							<el-col :span="12" v-if="uuid">
                                <el-form-item label="审核类型" prop="dtcCheckSts" ref="dtcCheckSts">
                                    <el-radio v-model="form.dtcCheckSts" label="0">待审核</el-radio>
  									<el-radio v-model="form.dtcCheckSts" label="1">审核完成</el-radio>
  									<el-radio v-model="form.dtcCheckSts" label="2">审核驳回</el-radio>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row v-if="form.dtcCheckSts == '2'">
                            <el-col :span="24">
                                <el-form-item label="驳回详情" prop="dtcRemarks" ref="dtcRemarks">
                                    <editor :quill-index="0"  :placeholder="'请输入驳回详情'" :content="form.dtcRemarks"  @change="editorChange($event,'dtcRemarks')"></editor>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="诊断辅助" prop="dtcDiagnose" ref="dtcDiagnose">
                                    <editor :quill-index="1"  :placeholder="'请输入诊断辅助'" :content="form.dtcDiagnose"  @change="editorChange($event,'dtcDiagnose')"></editor>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="故障说明" prop="dtcExplain" ref="dtcExplain">
                                    <editor :quill-index="2"  :placeholder="'请输入故障说明'" :content="form.dtcExplain"  @change="editorChange($event,'dtcExplain')"></editor>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="故障可能原因" prop="dtcReasons" ref="dtcReasons">
                                    <editor :quill-index="3"  :placeholder="'请输入故障可能原因'" :content="form.dtcReasons"  @change="editorChange($event,'dtcReasons')"></editor>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                </div>
                <div class="section section-btn-list">
                    <div class="content section-btn-content">
                        <el-button class="save-btn" size="lage" type="primary" @click="handleSubmit">保存</el-button>
                    </div>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import UploadPic from "@/components/UploadPic"
import Editor from "@/components/Editor/Editor";
import {dtcList,dtcDetail,dtcAdd,dtcDelete,dtcUpdate} from "@/api/dtc"
import {queryBrandList} from "@/api/artificer";


export default {
    name: 'DtcDetail',
    components:{
        'upload-pic':UploadPic,
        'editor':Editor
    },
    data() {
        return {
            loading:false,
            uuid:'',
            repairBrand: [],
            form: {

            },
            rules: {
                dtcDefinition: [
                    {required: true, message: '请输入故障标题', trigger: 'blur'}
                ],
                dtcCode: [
                    {required: true, message: '请输入故障代码', trigger: 'blur'}
                ],
                dtcBrandUuid: [
                    {required: true, message: '请选择品牌', trigger: 'change'}
                ],
                dtcType:[
                    {required: true, message: '请选择DTC类型', trigger: 'change'}
                ],
                dtcAmount: [
                    {required: true, message: '请输入购买金额', trigger: 'blur'}
                ],
            },
            dtcType:[]
        }
    },
    async created() {
        this.uuid = this.$route.query.id;
        await this.initDict();
        await this.initBrandList();
        if(this.uuid){
            this.initData()
        }
    },
    methods: {
        async initDict(){
            this.dtcType = await this.$store.dispatch('dict/getDict','dtc_type');
        },
        initBrandList(){
            return new Promise((resolve,reject) => {
                queryBrandList().then((res) => {
                    let {data} = res;
                    let repairBrand = [];
                    repairBrand = data.map((item) => {
                        return {
                            lableDesc:item.configName,
                            uuid:item.uuid,
                        }
                    })
                    this.repairBrand = repairBrand || [];
                    resolve()
                }).catch((e) => {
                    console.log(e)
                })
            })

        },
        initData() {
            this.loading = true;
            dtcDetail(this.uuid).then((res) => {
                let { data } = res;
                this.form = data;
                this.loading = false;
            }).catch((e) => {
                this.loading = false;
            })
        },
        editorChange(event,field){
            this.form[field] = event
        },
        handleSubmit(){
            this.$refs['form'].validate((valid,object) => {
                if (valid) {
                    if(this.uuid){
                        dtcUpdate({
                            ...this.form,
                        },this.uuid).then((res) => {
                            this.$message.success('提交成功');
                            this.$router.go(-1)
                        })
                    }else{
                    	this.form['dtcCheckSts'] = '0';
                        dtcAdd({
                            ...this.form,
                        }).then((res) => {
                            this.$message.success('提交成功');
                            this.$router.go(-1)
                        })
                    }
                } else {
                    let split = ''
                    for (let i in object) {
                        let dom = this.$refs[i]
                        if (Object.prototype.toString.call(dom) !== '[object Object]') {
                            dom = dom[0]
                            split = dom.prop
                            let index = split.indexOf('.')
                            let last = split.lastIndexOf('.')
                            this.activeName = Number(split.slice(index + 1, last))
                            break
                        }
                        dom.$el.scrollIntoView({
                            block: 'center', //值有start,center,end，nearest，当前显示在视图区域中间
                            behavior: 'smooth' //值有auto、instant,smooth，缓动动画（当前是慢速的）
                        })
                    }
                    return false
                }
            });
        },
    }
}
</script>

<style lang="scss" scoped>

</style>
