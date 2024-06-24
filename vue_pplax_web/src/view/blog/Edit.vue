<template>
  <div class="article-add">
    <div class="containner">
      <div class="title">发布文章</div>
      <el-form
          style="display: flex"
          :rules="rules"
          ref="ruleForm"
          label-position="left"
          label-width="80px"
          :model="form"
      >
        <div class="article-left">
          <el-row :gutter="20">
            <el-col :span="16">
              <el-form-item label="文章标题" prop="title">
                <el-input v-model="form.title"></el-input>
              </el-form-item>
              <el-form-item label="文章简介" prop="summary">
                <el-input v-model="form.summary"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="封面图" prop="" class="articeAvatar">
                <el-upload
                    class="avatar-uploader"
                    :show-file-list="false"
                    ref="upload"
                    name="filedatas"
                    action=""
                    :http-request="uploadSectionFile"
                    :before-upload="handleUploadBefore"
                    multiple
                >
                  <img
                      v-if="coverImageUrl"
                      style="width: 100%; height: 100%"
                      :src="coverImageUrl"
                      class="imgAvatar"
                  />
                  <i v-else class="el-icon-plus avatar-img-icon"></i>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="文章内容" prop="content">
            <!--            <el-upload-->
            <!--                style="color: var(&#45;&#45;theme-color)"-->
            <!--                :show-file-list="false"-->
            <!--                ref="upload"-->
            <!--                name="filedatas"-->
            <!--                action=""-->
            <!--                :http-request="readMarkdownFile"-->
            <!--                :before-upload="handleUploadBefore"-->
            <!--                multiple-->
            <!--            >-->
            <!--              <i class="el-icon-upload"></i> 文档导入-->
            <!--            </el-upload>-->

            <mavon-editor
                placeholder="输入文章内容..."
                style="height: 500px"
                ref="md"
                v-model="form.content"
                @change=""
                @imgAdd="imgAdd"
                @save="save(1)"
            >
              <!-- 左工具栏后加入自定义按钮  -->
              <template slot="left-toolbar-after">
                <el-dropdown>
                  <span class="el-dropdown-link">
                    <i
                        class="op-icon fa el-icon-video-camera"
                        title="上传视频"
                    ></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                      <el-upload
                          style="display: inline-block"
                          :show-file-list="false"
                          ref="upload"
                          name="filedatas"
                          action=""
                          :http-request="videoAttachAdd"
                          multiple
                      >
                        <span>上传视频</span>
                      </el-upload>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <div @click="dialogVisible = true">添加视频地址</div>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
                <el-dropdown>
                  <span class="el-dropdown-link">
                    <i class="op-icon fa el-icon-folder" title="上传文件"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                      <el-upload
                          style="display: inline-block"
                          :show-file-list="false"
                          ref="upload"
                          name="filedatas"
                          action=""
                          :http-request="fileAttachAdd"
                          multiple
                      >
                        <span>上传文件</span>
                      </el-upload>
                    </el-dropdown-item>
                    <el-dropdown-item>
                      <div @click="addAttachDialogVisible = true">
                        添加文件地址
                      </div>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </template>
            </mavon-editor>
          </el-form-item>
        </div>
        <div class="article-right">
          <div class="top">
            <el-form-item label="标签" prop="tagUids">
              <el-tag
                  type="mini"
                  v-for="(item, index) of editingTagList"
                  :key="index"
                  style="margin: 0 1rem 0 0"
                  :closable="true"
                  @close="removeTag(item)"
              >
                {{ item.name }}
              </el-tag>

              <!-- 标签选项 -->
              <el-popover
                  placement="bottom-start"
                  width="460"
                  trigger="click"
                  v-if="editingTagList && editingTagList.length < 3"
              >
                <div class="popover-title">标签</div>
                <!-- 搜索框 -->
                <el-input
                    style="width: 100%"
                    v-model="tagForm.name"
                    placeholder="请输入标签名,enter添加自定义标签"
                    @keyup.enter.native="handleAddTag"
                    @input="handleSearchTag"
                />
                <!-- 标签 -->
                <div class="popover-container">
                  <div>添加标签</div>
                  <el-tag
                      v-for="(item, index) of tagOptionList"
                      :key="index"
                      style="margin-left: 3px; margin-top: 2px"
                      @click="addTag(item)"
                  >
                    {{ item.name }}
                  </el-tag>
                </div>
                <el-button type="primary" plain slot="reference" size="small">
                  添加标签
                </el-button>
              </el-popover>
            </el-form-item>
            <el-form-item label="文章分类" prop="blogSortUid">
              <el-select v-model="form.blogSortUid" placeholder="请选择分类">
                <el-option
                    v-for="item in blogSortList"
                    :key="item.uid"
                    :label="item.sortName"
                    :value="item.uid"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="创作类型" prop="isOriginal">
              <el-radio v-model="form.isOriginal" :label="true">原创</el-radio>
              <el-radio v-model="form.isOriginal" :label="false">转载</el-radio>
            </el-form-item>
            <el-form-item
                v-if="blog.isOriginal === false"
                label="文章地址"
                prop="originalUrl"
            >
              <el-input v-model="blog.originalUrl"></el-input>
            </el-form-item>

            <div class="bottom">
              <div class="btn-tips">Are you ready</div>
              <button
                  v-if="$store.state.user"
                  type="button"
                  class="btnDraft"
                  @click="save(1)"
              >
                <i class="el-icon-orange"></i> 保存草稿
              </button>
              <button
                  v-if="$store.state.user"
                  type="button"
                  class="btnSubmit"
                  @click="save(2)"
              >
                <i class="el-icon-circle-check"></i> 提交审核
              </button>
              <span v-else class="noBtn">
                暂无发布权限,请先<span
                  @click="$store.state.loginFlag = true"
                  class="hand-style"
              >登录</span
              >
              </span>
            </div>
          </div>
        </div>
      </el-form>
    </div>
    <el-dialog
        center
        title="添加视频"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <el-input v-model="videoInput" placeholder="视频地址"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addVideo">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
        center
        title="添加文件"
        :visible.sync="addAttachDialogVisible"
        width="30%"
    >
      <el-input v-model="attachInput" placeholder="文件地址"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addAttachDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAttach">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { upload, insertArticle, updateArticle, readMarkdownFile } from '@/api'
import { blogAttachUpload, blogCoverImageUpload, blogImageAttachUpload, blogVideoAttachUpload } from "@/api/fileStorage";
import { getBlog, addBlog, updateBlog } from "@/api/blog";
import { fetchTagList, addTag } from "@/api/tag"
import {fetchBlogSortList} from "@/api/blogSort";
import {EStatus} from "@/base/EStatus";
export default {
  data() {
    return {
      user: this.$store.state.user,
      addAttachDialogVisible: false,
      attachInput: '',
      coverImageUrl: '',
      blog: {
      },
      form: {
        content: '',
        blogSortUid: '',
        isOriginal: '',
        coverImageUid: '',
        articlesPart: '',
        summary: '',
        title: '',
        level: 0,
        tagUids: '',
        status: ''
      },
      tagForm: {
        level: 0,
        name: ''
      },
      blogSortList: [],
      dialogVisible: false,
      loading: [],
      img: process.env.VUE_APP_IMG_API,
      blogUid: this.$route.query.uid,
      tagList: [],
      editingTagList: [],
      tagOptionList: [],
      videoInput: "",
      rules: {
        title: [
          { required: true, message: '请输入文章标题', trigger: 'blur' },
          { min: 1, max: 20, message: '标题长度限制在1到20之间', trigger: 'change' }
        ],
        summary: [
          { required: false, message: '请输入文章简介', trigger: 'blur' },
          { min: 0, max: 50, message: '简介限制在0到50之间', trigger: 'change' }
        ],
        coverImageUid: [
          { required: false, message: '请上传标题图', trigger: 'blur' }
        ],
        tagUids: [
          { required: true, message: '请选择标签' },
          { validator: this.tagUidsValidator, trigger: 'change' },
        ],
        blogSortUid: [
          { required: true, message: '请选择分类', trigger: 'blur' }
        ],
        isOriginal: [
          { required: true, message: '请选择创作类型', trigger: 'change' },
        ],
        articlesPart: [
          { validator: this.articlesPartValidator, message: '请输入正确的转载地址', trigger: 'change' },
        ],
        status: [
          { required: true, message: '请选择状态', trigger: 'change' },
        ],
        level: [
          { required: true, message: '请选择推荐等级', trigger: 'change' },
        ],
      },
    };
  },
  beforeRouteLeave(to, from, next) {
    // 删除md编辑器的高亮样式 否则会跟文章详情代码块样式有冲突
    let css = document.getElementsByTagName('link');
    for (let i = 0; i < css.length; i++) {
      if (css[i].href.includes("https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.3.1/styles/github.min.css")) {
        css[i].parentNode.removeChild(css[i]);
      }
    }
    next()
  },
  created() {
    if (this.blogUid) {
      getBlog(this.blogUid).then(res => {
        this.form.content = res.data.blogContent.content
        this.form.blogSortUid = res.data.blogSortUid
        this.form.isOriginal = res.data.isOriginal
        this.form.coverImageUid = res.data.coverImageUid
        this.form.articlesPart = res.data.articlesPart
        this.form.summary = res.data.summary
        this.form.title = res.data.title
        this.form.level = res.data.level
        this.form.tagUids = res.data.tagUids
        this.form.status = res.data.status
        this.coverImageUrl = res.data.coverImage ? res.data.coverImage.fileUrl : ''
        this.editingTagList = res.data.tagList
      })
    }
    fetchBlogSortList().then(res => {
      this.blogSortList = res.data
    })
  },
  methods: {
    /**
     * 移除标签
     * @param item
     */
    removeTag: function (item) {
      this.form.tagUids = this.form.tagUids.replace(item.uid, '').replace(",,", "")
      const index = this.editingTagList.indexOf(item)
      this.editingTagList.splice(index, 1)
    },
    /**
     * 处理添加tag
     */
    handleAddTag: function () {
      addTag(this.tagForm).then(res => {
        this.$toast.success("添加成功")
        this.handleSearchTag()
      }).catch(err => {
        console.error(err)
      })
    },

    addTag: function (item) {
      if (this.editingTagList.length > 2) {
        this.$toast.error("最多添加三个标签,如需继续添加,请先删除一个!")
        return false;
      }
      if (this.editingTagList.indexOf(item) === -1) {
        this.editingTagList.push(item)
        this.form.tagUids += ',' + item.uid
        this.form.tagUids = this.form.tagUids.replace(",,", '')
      }
    },
    /**
     * 处理查找tag
     */
    handleSearchTag: function () {
      fetchTagList({
        sortByClickCount: false,
        sortByCites: false,
        keyword: this.tagForm.name,
        currentPage: 1,
        pageSize: 10
      }).then(res =>{
        this.tagOptionList = res.data
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    imgAdd: function (pos, $file) {

      var formdata = new FormData();
      formdata.append('file', $file);
      blogImageAttachUpload(formdata).then(res => {
        this.$refs.md.$img2Url(pos, res.data.fileUrl);
      }).catch(err => {
        console.log(err)
      })
    },

    /**
     * 保存操作
     */
    save: function (type) {
      this.form.tagUids = this.form.tagUids.trim().replace(/^(\s|,)+|(\s|,)+$/g, '');
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          if (type === 1) {
            this.form.status = EStatus.DRAFT
          } else {
            this.form.status = EStatus.PENDING_APPROVAL
          }

          if (this.blogUid) {
            updateBlog(this.blogUid, this.form).then(res => {
              this.blogUid = res.data.uid
              this.$toast.success("已保存")
              this.fetchBlogList()
            }).catch(err => {
              console.error(err)
            })
          } else {
            addBlog(this.form).then(res => {
              this.$toast.success("已保存")

              this.blogUid = res.data.uid
              this.isEditForm = true;

              this.fetchBlogList()
            })

          }
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    addAttach() {
      // 这里获取到的是mavon编辑器实例，上面挂载着很多方法
      const $vm = this.$refs.md
      // 将文件名与文件路径插入当前光标位置，这是mavon-editor 内置的方法
      $vm.insertText($vm.getTextareaDom(),
          {
            prefix: `[${this.attachInput}](${this.attachInput})`,
            subfix: '',
            str: ''
          })

      this.addAttachDialogVisible = false
      this.attachInput = ''
    },
    handleUploadBefore() {
      this.$bus.$emit('show');
    },
    videoAttachAdd: function (param) {
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', param.file)
      blogVideoAttachUpload(formData).then(res => {
        const $vm = this.$refs.md
        // 将文件名与文件路径插入当前光标位置，这是mavon-editor 内置的方法
        $vm.insertText($vm.getTextareaDom(),
            {
              prefix: `<video height=100% width=100% controls autoplay src="${res.data.fileUrl}"></video>`,
              subfix: '',
              str: ''
            })
      })
    },
    fileAttachAdd: function (param) {
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', param.file)
      blogAttachUpload(formData).then(res => {
        const $vm = this.$refs.md
        // 将文件名与文件路径插入当前光标位置，这是mavon-editor 内置的方法
        $vm.insertText($vm.getTextareaDom(),
            {
              prefix: `[${res.data.originalName}](${res.data.fileUrl})`,
              subfix: '',
              str: ''
            })
      })
    },
    readMarkdownFile(param) {
      var fileExtension = param.file.name.split('.').pop().toLowerCase();
      if (fileExtension !== "md") {
        this.$bus.$emit('close')
        this.$toast.error("只能上传md后缀的文件")
        return false;
      }
      this.files = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', this.files)
      readMarkdownFile(formData).then(res => {
        this.blog.contentMd = res.data.content
        this.blog.title = res.data.fileName
        this.$bus.$emit('close')
      }).catch(err => {
        this.$bus.$emit('close')
      })
    },
    addVideo() {
      // 这里获取到的是mavon编辑器实例，上面挂载着很多方法
      const $vm = this.$refs.md
      // 将文件名与文件路径插入当前光标位置，这是mavon-editor 内置的方法
      $vm.insertText($vm.getTextareaDom(),
          {
            prefix: `<video height=100% width=100% controls autoplay src="${this.videoInput}"></video>`,
            subfix: '',
            str: ''
          })

      this.dialogVisible = false
      this.videoInput = ''
    },
    uploadSectionFile: function (param) {

      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      formData.append("userUid", this.user.uid)
      blogCoverImageUpload(formData).then(res => {
        this.coverImageUrl = res.data.fileUrl
        this.form.coverImageUid = res.data.uid
        this.$bus.$emit('close')
      }).catch(err => {
        this.$bus.$emit('close')
      })
    },
  }
};
</script>
<style lang="scss" scoped>
.article-add {
  display: flex;
  justify-content: center;

  @media screen and (max-width: 1118px) {
    padding: 0 5px;

    .containner {
      width: 100%;
    }
  }

  @media screen and (min-width: 1119px) {
    .containner {
      width: 80%;
    }
  }

  .containner {
    margin-top: 80px;

    padding: 10px;
    color: var(--text-color);

    /deep/ .article-left input {
      border-top: none !important;
      border-left: none !important;
      border-right: none !important;

      &:hover {
        border-color: var(--theme-color) !important;
      }
    }

    .title {
      margin-bottom: 15px;
      position: relative;
      padding-left: 10px;

      &::before {
        content: " ";
        width: 5px;
        height: 100%;
        background: linear-gradient(to right, #ff00ff, #c2553a);
        position: absolute;
        bottom: 0;
        left: 0;
      }
    }

    .article-left,
    .article-right .top,
    .bottom {
      border-radius: 5px;
      padding: 10px;
    }

    .article-left {
      background-color: var(--background-color);
      margin-right: 20px;
      width: 100%;
      max-width: 80%;

      .el-textarea {
        width: 92%;
      }

      /deep/ .contentInput textarea {
        resize: none;
        background-color: var(--background-color);
      }
    }

    .article-right {
      width: 35%;
      height: 100%;
      display: flex;
      flex-direction: column;

      .top,
      .bottom {
        background-color: var(--background-color);
      }

      .bottom {
        margin-top: 20px;
        text-align: center;

        .btn-tips {
          color: #b5b5b5;
          font-size: 0.75rem;
          margin-bottom: 1rem;

          &::before {
            content: "";
            display: inline-block;
            background-color: #d8d8d8;
            width: 60px;
            height: 1px;
            margin: 0 12px;
            vertical-align: middle;
          }

          &::after {
            content: "";
            display: inline-block;
            background-color: #d8d8d8;
            width: 60px;
            height: 1px;
            margin: 0 12px;
            vertical-align: middle;
          }
        }

        .btnDraft,
        .btnSubmit {
          padding: 10px;
          width: 50%;
          border: none;
          margin: 0 auto;
          color: #fff;
          font-weight: 700;
        }

        .btnDraft {
          border-top-left-radius: 5px;
          border-bottom-left-radius: 5px;
          background: linear-gradient(135deg, #60e464 10%, #5cb85b 100%);
        }

        .btnSubmit {
          border-top-right-radius: 5px;
          border-bottom-right-radius: 5px;
          background: linear-gradient(135deg, #59c3fb 10%, #268df7 100%);
        }

        .noBtn {
          color: var(--text-color);

          span {
            color: var(--theme-color);
          }
        }
      }
    }
  }
}

.articeAvatar {
  /deep/ .avatar-uploader {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    max-width: 200px;
    height: 100px;
    text-align: center;
  }

  /deep/ .el-upload {
    width: 100%;
    height: 100%;
  }

  /deep/ .avatar-uploader:hover {
    border-color: #409eff;
  }

  /deep/ .el-icon-plus {
    font-size: 28px;
    color: #8c939d;
    line-height: 100px;
  }

  /deep/ .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>
