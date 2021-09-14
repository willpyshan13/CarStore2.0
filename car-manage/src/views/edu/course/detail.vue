<template>
    <div class="app-container">
        <div class="page-detail">
            <el-form
                v-loading="loading"
                label-position="right"
                :model="form"
                :rules="rules"
                ref="form"
                label-width="180px"
            >
                <div class="section">
                    <div class="title">
                        {{uuid ? '编辑课程' : '新增课程'}}
                    </div>
                    <div class="content">
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="课程名称" prop="courseTitle" ref="courseTitle">
                                    <el-input clearable v-model="form.courseTitle"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="课程分类" prop="courseParentUuid" ref="courseParentUuid">
                                    <el-select
                                        v-model="form.courseParentUuid"
                                        filterable
                                        remote
                                        reserve-keyword
                                        placeholder="请输入课程分类关键词"
                                        :remote-method="(query) => {remoteMethod(query)}"
                                        :loading="searchLoading">
                                        <el-option
                                            v-for="item in courseParentList"
                                            :key="item.value"
                                            :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="课程讲师" prop="courseLecturer" ref="courseLecturer">
                                    <el-input clearable v-model="form.courseLecturer"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="课程封面" prop="courseCover" ref="courseCover">
                                    <upload-pic
                                        :limit="1"
                                        :file-list="form.courseCover"
                                    ></upload-pic>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="课程类型" prop="courseType" ref="courseType">
                                    <el-select clearable  v-model="form.courseType">
                                        <el-option v-for="(item,index) in courseType" :key="index"
                                                   :label="item.lableDesc" :value="item.uuid"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12" v-if="form.courseType == 0">
                                <el-form-item label="课程时间:" prop="courseTime" ref="courseTime">
                                    <el-date-picker
                                        v-model="form.courseTime"
                                        type="datetime"
                                        format="yyyy-MM-dd HH:mm:ss"
                                        value-format="yyyy-MM-dd HH:mm:ss"
                                        placeholder="请选择课程时间">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="课程简介" prop="courseIntro" ref="courseIntro">
                                    <editor :quill-index="0"  :placeholder="'请输入课程简介'" :content="form.courseIntro"  @change="editorChange($event,'courseIntro')"></editor>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24" v-if="form.courseType == 1">
                                <el-form-item label="课程内容" prop="courseContent" ref="courseContent">
                                    <editor :quill-index="1"  :placeholder="'请输入课程内容'" :content="form.courseContent"  @change="editorChange($event,'courseContent')"></editor>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24" v-if="form.courseType == 0">
                                <el-form-item label="课程地址" prop="courseUrl" ref="courseUrl">
                                    <el-input clearable v-model="form.courseUrl"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24">
                                <el-form-item label="课程金额" prop="courseAmount" ref="courseAmount">
                                    <el-input-number :controls="false" v-model="form.courseAmount" :precision="2"></el-input-number>
                                    <span class="warning-text" style="padding-left:10px;">元</span>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="是否为最新课程" prop="courseAmount" ref="courseAmount">
                                    <el-select v-model="form.latestCourse" placeholder="请选择">
																	    <el-option
																	      v-for="item in options"
																	      :key="item.value"
																	      :label="item.label"
																	      :value="item.value">
																	    </el-option>
																	  </el-select>
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
import {courseParentList,coursetDetail,courseAdd,courseUpdate,courseParentDetail} from "@/api/edu";
export default {
    name: 'EduCourseDetail',
    components:{
        'upload-pic':UploadPic,
        'editor':Editor
    },
    data() {
        return {
            loading:false,
            uuid:'',
            courseParentList:[],
            searchLoading:false,
            form: {
                courseCover:[],
                latestCourse: 0
            },
            currentParentList:[],
            rules: {
                // levelTwoUuid: [
                //     {required: true, message: '请选择商品类目', trigger: 'change'}
                // ],
                // goodsName: [
                //     {required: true, message: '请输入商品名称', trigger: 'blur'}
                // ],
                // goodsType: [
                //     {required: true, message: '请选择商品类型', trigger: 'change'}
                // ],
                // materialsExpenses: [
                //     {required: true, message: '请输入材料费', trigger: 'blur'}
                // ],
                // manHourCost: [
                //     {required: true, message: '请输入服务费', trigger: 'blur'}
                // ],
                // surplusNum: [
                //     {required: true, message: '请输入库存量', trigger: 'blur'}
                // ],
                // mainImg: [
                //     {required: true, message: '请上传商品主图', trigger: 'change'}
                // ],
                // goodsDescribe: [
                //     {required: true, message: '请输入商品描述', trigger: 'blur'}
                // ],
                // sellSts: [
                //     {required: true, message: '请选择上架时间', trigger: 'change'}
                // ],
            },
            options: [
            	{label: '否', value: 0},
            	{label: '是', value: 1}
            ]
        }
    },
    async created() {
        this.uuid = this.$route.query.id;
        if(this.uuid){
            this.initData()
        }
    },
    computed: {
        ...mapGetters([
            'courseType',
        ])
    },
    methods: {
        initData() {
            this.loading = true;
            coursetDetail(this.uuid).then((res) => {
                let { data } = res;
                data.courseCover = data.courseCover ? [{url:data.courseCover}] : [];
                if(data.courseParentUuid){
                    courseParentDetail(data.courseParentUuid).then((detail) => {
                        this.currentParentList = {
                            label:detail.data.courseTitle,
                            value:detail.data.uuid,
                        }
                        this.remoteMethod('',() => {
                            this.form = data;
                            this.loading = false;
                        })
                    })
                }else{
                    this.remoteMethod('',() => {
                        this.form = data;
                        this.loading = false;
                    })
                }
            }).catch((e) => {
                this.loading = false;
            })
        },
        editorChange(event,field){
            this.form[field] = event
        },
        handleSubmit(){
            this.$refs['form'].validate((valid,object) => {
                let form = this.form;
                let courseCover = form.courseCover[0].url;
                if (valid) {
                    if(this.uuid){
                        courseUpdate({
                            ...form,
                            courseCover
                        },form.uuid).then((res) => {
                            this.$message.success('提交成功');
                            this.$router.go(-1)
                        })
                    }else{
                        courseAdd({
                            ...form,
                            courseCover
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
        remoteMethod(query,callback) {
            this.searchLoading = true;
            courseParentList({
                courseTitle:query,
                pageNum:1,
                pageSize:20
            }).then((res) => {
                let { data,total } = res;
                let list = [];
                if(data.length){
                    list = data.map((item) => {
                        return {
                            label:item.courseTitle,
                            value:item.uuid,
                        }
                    })
                }
                if(this.currentParentList.value && !list.some(item => item.value == this.currentParentList.value)){
                    list.unshift(this.currentParentList)
                }
                console.log(this.currentParentList)
                console.log(list)
                this.courseParentList = list;
                this.searchLoading = false;
                callback && callback()
            }).catch((e) => {
                this.searchLoading = false;
            })
        },
    }
}
</script>

<style lang="scss" scoped>

</style>
