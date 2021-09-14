<template>
    <div class="upload-pic">
        <div class="l">
            <el-upload
                class="avatar-uploader"
                action="#"
                :accept="accept"
                :multiple="multiple"
                :limit="limit"
                :file-list="fileList"
                list-type="picture-card"
                :before-upload="beforeUpload"
                :on-remove="handleRemove"
                :class="{hide:hideUploadEdit}"
            >
                <i class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </div>
        <div class="r">
            <div class="upload-tips">
                <div class="tip-title">*注意</div>
                <ul>
                    <slot></slot>
                    <li>文件大小不超过{{size}}MB</li>
                    <li>支持jpg,png,jpeg</li>
                </ul>
            </div>
        </div>
    </div>
</template>
<script>
import { uploadPic } from '@/api/upload'
export default {
    name: "UploadPic",
    props:{
        accept:{
            type:String,
            default:".jpg,.png,.jpeg"
        },
        multiple:{
            type:Boolean,
            default: true
        },
        limit:{
            type:Number,
            default:6
        },
        size:{
            type:Number,
            default:20
        },
        fileList:{
            type:Array,
            default(){
                return []
            }
        }
    },
    data(){
       return {

       }
    },
    computed:{
        hideUploadEdit(){
            return this.fileList.length >= this.limit;
        }
    },
    methods:{
        beforeUpload(file) {
            const isLt2M = file.size / 1024 / 1024 < this.size;

            if (!isLt2M) {
                this.$message.error(`上传图片大小不能超过 ${this.size}MB!`);
                return false
            }
            uploadPic(file).then((res) => {
                this.fileList.push({
                    url:res.data,
                    uid:file.uid
                })
            })
            return false;
        },
        handleRemove(file) {
            let fileIndex = this.fileList.findIndex((item) => item.uid == file.uid);
            if(fileIndex > -1){
                this.fileList.splice(fileIndex,1);
                this.$emit('descImg',this.fileList);
            }
        },
    }
}
</script>

<style lang="scss" scoped>
.upload-pic{
    display: flex;
    .l{

    }
    .r{
        .upload-tips{
            padding-top:10px;
            font-size:14px;
            line-height:1;
            padding-left:20px;
            color:#F56C6C;
            font-weight: bold;
        }
        ul{
            padding:0;
            li{
                font-size:12px;
                list-style: none;
                color:#909399;
                margin-top:10px;
                font-weight: 400;
                letter-spacing:1px;
            }
        }
    }
}
.hide{
    ::v-deep .el-upload--picture-card {
        display: none!important;
    }
}
</style>
