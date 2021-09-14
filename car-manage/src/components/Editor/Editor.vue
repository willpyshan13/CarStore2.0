<template>
    <div>
        <el-upload style="display: none" action="#" :show-file-list="false" :before-upload="imgUrlUpload">
            <el-button class="button-upload" slot="trigger" size="small" type="primary">上传图片</el-button>
        </el-upload>
        <quill-editor ref="QuillEditor" v-model="newContent" :disabled="disabled" :options="editorOption" @change="onEditorChange" @paste.native.stop="handlePaste"></quill-editor>
    </div>
</template>

<script>
    import 'quill/dist/quill.core.css';
    import 'quill/dist/quill.snow.css';
    import 'quill/dist/quill.bubble.css';
    import { quillEditor } from 'vue-quill-editor';
    import { uploadPic } from '@/api/upload';
    import Quill from 'quill';
    import ImageResize from 'quill-image-resize-module';
    Quill.register('modules/imageResize',ImageResize);

    const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],

        [{'header': 1}, {'header': 2}],               // custom button values
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
        [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
        [{'direction': 'rtl'}],                         // text direction

        [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown
        [{'header': [1, 2, 3, 4, 5, 6, false]}],

        [{'color': []}, {'background': []}],          // dropdown with defaults from theme
        [{'font': []}],
        [{'align': []}],
        ['link', 'image', 'video'],
        ['clean']                                         // remove formatting button
    ]

    export default {
        name: 'editor',
        props:{
            'placeholder':{

            },
            'content':{

            },
            'quillIndex':{
                type:Number,
                default:0
            },
            disabled:{
                type:Boolean,
                default:false
            }
        },
        data: function(){
            return {
                newContent:this.content,
                editorOption: {
                    placeholder: this.placeholder,
                    modules: {
                        toolbar: {
                            container: toolbarOptions,  // 工具栏
                            handlers: {
                                'image': (value) => {
                                    if (value) {
                                        const index = this.quillIndex;
                                        document.querySelectorAll('.button-upload')[index].click()
                                    } else {
                                        this.quill.format('image', false);
                                    }
                                }
                            }
                        },
                        imageResize:{
                            displayStyles:{
                                backgroundColor:'black',
                                border:'none',
                                color:'white'
                            },
                            modules:['Resize','DisplaySize','Toolbar']
                        }
                    }
                }
            }
        },
        watch: {
            content(newContent, pldContent) {
                this.newContent = newContent
            }
        },
        components: {
            quillEditor
        },
        methods: {
            onEditorChange({ editor, html, text }) {
                this.$emit('change',html)
            },
            // 上传图片
            imgUrlUpload(file) {
                const isLt2M = file.size / 1024 / 1024 < 10;
                var _this = this;
                uploadPic(file).then(function(res) {
                    let quill = _this.$refs.QuillEditor.quill
                    // 如果上传成功
                    if (res.data) {
                        let length = quill.getSelection().index;
                        quill.insertEmbed(length, 'image', res.data)
                        quill.setSelection(length + 1)
                    } else {
                        _this.$message.error('图片插入失败');
                    }
                });
                return false;
            },
            handlePaste(e){
                var items,flag=false;
                //判断IE浏览器
                if(!!window.ActiveXObject || "ActiveXObject" in window){
                    items = window.clipboardData.files;
                }else{
                    var cli = e.clipboardData || e.originalEvent.clipboardData;
                    items = cli.items;
                }
                for (var i = 0, len = items.length; i < len; i++) {
                    var item = items[i];
                    //ie
                    if(!!window.ActiveXObject || "ActiveXObject" in window){
                        if (item.type.indexOf("image/")>-1) {
                            flag = true;
                            break;
                        }
                    }
                    //非ie
                    else{
                        if (item.kind === "string") {
                            console.log('string');
                            // item.getAsString(function (str) {
                            // str 是获取到的字符串
                            //  })
                        } else if (item.kind === "file") {
                            //有文件，则直接break
                            console.log('file');
                            flag = true;
                            break;
                            // var pasteFile = item.getAsFile();
                            // pasteFile就是获取到的文件
                        }
                    }
                }
                if(flag){
                    e.preventDefault()
                    return false;// 终止粘贴操作
                }
            }
        }
    }
</script>
<style>
    .editor-btn{
        margin-top: 20px;
    }
    .ql-editor{
        height:400px!important;
        line-height:1!important;
    }
    .ql-snow{
        line-height: 1 !important;
    }
    :focus{
        outline: none;
    }
</style>
