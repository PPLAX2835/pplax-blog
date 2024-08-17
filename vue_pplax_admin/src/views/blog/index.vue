<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="博客名">
        <el-input style="width: 150px" size="small" v-model="params.blogTitle" placeholder="请输入博客名"/>
      </el-form-item>
      <el-form-item label="分类">
        <el-select clearable filterable remote :remote-method="fetchBlogSortList" style="width: 130px" size="small" v-model="params.blogSortUid" placeholder="请选择分类">
          <el-option
            v-for="item in blogSortOptionList"
            :key="item.sortName"
            :label="item.sortName"
            :value="item.uid"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select style="width: 130px" size="small" v-model="params.status" placeholder="请选择状态">
          <el-option label="全部" value=""/>
          <el-option label="发布" :value="statusList.ENABLE" />
          <el-option label="顶置" :value="statusList.STICK" />
          <el-option label="下架" :value="statusList.OFF_SHELF" />
          <el-option label="待审批" :value="statusList.PENDING_APPROVAL" />
          <el-option label="草稿" :value="statusList.DRAFT" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        <el-button v-if="canAdd" type="primary" icon="el-icon-plus" size="small" @click="handleCreate">新增</el-button>
        <el-button v-if="canDeleteBatch" :disabled="!multipleSelection.length" type="danger" icon="el-icon-delete" size="small"
                   @click="handleDelete">批量删除
        </el-button>
      </el-form-item>

    </el-form>

    <!-- 表格区域 -->
    <div style="margin-top: 5px">
      <el-table border :data="tableData" style="width: 100%" :default-sort="{ prop: 'sort', order: 'descending' }"
                @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection" />
        <el-table-column width="170" align="center" label="文章封面">
          <template slot-scope="scope">
            <el-image v-if="scope.row.coverImage && scope.row.coverImage.fileUrl" :size="50" :src="scope.row.coverImage.fileUrl" class="article-cover" />
            <i id="imgIcon" v-else class="el-icon-warning-outline" />
          </template>
        </el-table-column>
        <el-table-column width="180" align="center" prop="title" label="标题"></el-table-column>
        <el-table-column width="120" align="center" label="作者">
          <template slot-scope="scope">
            {{ scope.row.user.userInfo.nickname }}
          </template>
        </el-table-column>
        <el-table-column width="200" align="center" prop="summary" label="摘要"></el-table-column>
        <el-table-column width="120" align="center" label="分类">
          <template slot-scope="scope">
            <el-tag type="warning">{{ scope.row.blogSort.sortName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="200" align="center" label="标签">
          <template slot-scope="scope">
            <el-tag v-for="item in scope.row.tagList">{{ item.name }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="80" align="center" prop="level" label="推荐等级"></el-table-column>
        <el-table-column width="80" align="center" prop="clickCount" label="点击量"></el-table-column>
        <el-table-column width="80" align="center" prop="collectCount" label="收藏数"></el-table-column>
        <el-table-column width="80" align="center" label="是否原创">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.isOriginal">是</el-tag>
            <a :href="scope.row.articlesPart" target="_blank" v-else>
              <el-tooltip content="查看原文">
                <el-tag type="info">否</el-tag>
              </el-tooltip>
            </a>
          </template>
        </el-table-column>
        <el-table-column width="80" align="center" label="状态">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status === statusList.ENABLE">发布</el-tag>
            <el-tag v-else-if="scope.row.status === statusList.STICK">置顶</el-tag>
            <el-tag type="info" v-else-if="scope.row.status === statusList.PENDING_APPROVAL">待审批</el-tag>
            <el-tag type="danger" v-else-if="scope.row.status === statusList.OFF_SHELF">下架</el-tag>
            <el-tag type="warning" v-else-if="scope.row.status === statusList.DRAFT">草稿</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="180" align="center" label="添加时间">
          <template slot-scope="scope">
            <span>{{ timeFormat(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="180" align="center" label="更新时间">
          <template slot-scope="scope">
            <span>{{ timeFormat(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="250" fixed="right" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button v-if="(scope.row.status === statusList.ENABLE) && canPromote" type="warning" size="mini" @click="handlePromote(scope)">置顶</el-button>
            <el-button v-else-if="(scope.row.status === statusList.STICK) && canPromote" type="warning" size="mini" @click="handlePromoteCancel(scope)">取消置顶</el-button>
            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>
            <el-button v-if="canDelete" size="mini" type="danger" @click="handleDelete(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--分页区域-->
    <div class="pagination-container" style="float: right;margin-bottom: 1.25rem;margin-top: 1.25rem;">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="params.currentPage" :page-size="params.pageSize" :page-sizes="[10, 20, 30]"
                     layout="total, sizes,prev, pager, next,jumper" :total="total">
      </el-pagination>
    </div>

    <!-- 编辑弹窗 -->
    <el-dialog center :title="title" :visible.sync="dialogFormVisible" fullscreen>
      <el-form :rules="rules" ref="dataForm" :model="form">
        <el-row>
          <el-col :span="14">
            <el-form-item label="文章标题" :label-width="formLabelWidth" prop="title">
              <el-input v-model="form.title" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="文章简介" :label-width="formLabelWidth" prop="summary">
              <el-input auto-complete="off" v-model="form.summary"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item v-if="editingBlogUid" label="标题图" prop="coverImageUid" :label-width="formLabelWidth">
              <el-col :span="2">
                <el-upload action="" class="avatar-uploader" :show-file-list="false"
                           :before-upload="uploadBefore" :http-request="uploadSelectionCoverImage">
                  <el-image style="border-radius: 4px; width: 800%" v-if="editingBlogCoverImageUrl" :src="editingBlogCoverImageUrl"></el-image>
                  <i v-else class="el-icon-plus avatar-img-icon"></i>
                </el-upload>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6.5">
            <el-form-item label="标签" :label-width="formLabelWidth" prop="tagUids">

              <el-tag v-for="(item, index) of editingBlogTagList" :key="index" style="margin:0 1rem 0 0" :closable="true"
                      @close="removeTag(item)">
                {{ item.name }}
              </el-tag>

              <!-- 标签选项 -->
              <el-popover placement="bottom-start" width="460" trigger="click"
                          v-if="editingBlogTagList && editingBlogTagList.length < 3">
                <div class="popover-title">标签</div>
                <!-- 搜索框 -->
                <el-input style="width:100%" v-model="tagForm.name" placeholder="请输入标签名,enter添加自定义标签"
                          @keyup.enter.native="handleAddTag" @input="handleSearchTag" />
                <!-- 标签 -->
                <div class="popover-container">
                  <div>添加标签</div>
                  <el-tag v-for="(item, index) of tagOptionList" :key="index" style="margin-left: 3px;margin-top: 2px"
                          @click="addTag(item)">
                    {{ item.name }}
                  </el-tag>
                </div>
                <el-button type="primary" plain slot="reference" size="small">
                  添加标签
                </el-button>
              </el-popover>

            </el-form-item>
          </el-col>
          <el-col :span="6.5">
            <el-form-item label="分类" :label-width="formLabelWidth" prop="blogSortUid">
              <el-select style="width: 130px" size="small" v-model="form.blogSortUid" placeholder="请选择分类">
                <el-option
                  v-for="item in blogSortOptionList"
                  :key="item.uid"
                  :label="item.sortName"
                  :value="item.uid"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6.5">
            <el-form-item prop="isOriginal" label="创作类型" :label-width="formLabelWidth">
              <div>
                <el-radio v-model="form.isOriginal" :label="true" border>原创</el-radio>
                <el-radio v-model="form.isOriginal" :label="false" border>转载</el-radio>
              </div>
            </el-form-item>
          </el-col>
          <el-col v-if="!form.isOriginal" :span="6.5">
            <el-form-item label="转载地址" :label-width="formLabelWidth" prop="articlesPart">
              <el-input auto-complete="off" v-model="form.articlesPart"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6.5">
            <el-form-item prop="status" label="状态" :label-width="formLabelWidth">
              <div>
                <el-radio v-model="form.status" :label="statusList.ENABLE" border>发布</el-radio>
                <el-radio v-model="form.status" :label="statusList.STICK" border>顶置</el-radio>
                <el-radio v-model="form.status" :label="statusList.OFF_SHELF" border>下架</el-radio>
                <el-radio v-model="form.status" :label="statusList.PENDING_APPROVAL" border>待审批</el-radio>
                <el-radio v-model="form.status" :label="statusList.DRAFT" border>草稿</el-radio>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="6.5">
            <el-form-item prop="level" label="推荐等级" :label-width="formLabelWidth">
              <el-select style="width: 130px" size="small" v-model="form.level" placeholder="请选择">
                <el-option label="正常" :value="0"></el-option>
                <el-option label="一级推荐" :value="1"></el-option>
                <el-option label="二级推荐" :value="2"></el-option>
                <el-option label="三级推荐" :value="3"></el-option>
                <el-option label="四级推荐" :value="4"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :spam="24">
            <el-form-item :label-width="formLabelWidth" label="内容" prop="contentMd">
              <mavon-editor placeholder="输入文章内容..." style="height: 500px" ref=md v-model="form.content"
                            @change="" @imgAdd="imageAttachAdd" @save="save">
                <!-- 左工具栏后加入自定义按钮  -->
                <template slot="left-toolbar-after">
                  <el-dropdown>
                      <span class="el-dropdown-link">
                        <i class="op-icon fa el-icon-video-camera" title="上传视频"></i>
                      </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>
                        <el-upload style="display: inline-block;" :show-file-list="false" ref="upload" name="filedatas"
                                   action="" :http-request="videoAttachAdd" multiple>
                          <span>上传视频</span>
                        </el-upload>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <div @click="addVideoDialogVisible = true">添加视频地址</div>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                  <el-dropdown>
                      <span class="el-dropdown-link">
                        <i class="op-icon fa el-icon-folder" title="上传文件"></i>
                      </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>
                        <el-upload style="display: inline-block;" :show-file-list="false" ref="upload" name="filedatas"
                                   action="" :http-request="fileAttachAdd" multiple>
                          <span>上传文件</span>
                        </el-upload>
                      </el-dropdown-item>
                      <el-dropdown-item>
                        <div @click="addAttachDialogVisible = true">添加文件地址</div>
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </template>
              </mavon-editor>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog center title="添加视频" :visible.sync="addVideoDialogVisible" width="30%">
      <el-input v-model="videoInput" placeholder="视频地址"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addVideoDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addVideo">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog center title="添加文件" :visible.sync="addAttachDialogVisible" width="30%">
      <el-input v-model="attachInput" placeholder="文件地址"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addAttachDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAttach">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {getBlogSortList} from "../../api/blogSort";
import { getBlogList, getBlogContent, addBlog, updateBlog, deleteBlog, deleteBlogBatch, promote, promoteCancel } from "../../api/blog";
import { addTag, getTagList} from "../../api/tag";
import { hasAuth, getUserUid } from "../../utils/auth";
import { checkImgType } from "../../utils/validate"
import { parseTime } from "../../utils";
import IconPicker from "../../components/IconPicker"
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";
import {
  blogAttachUpload,
  blogCoverImageUpload,
  blogImageAttachUpload,
  blogVideoAttachUpload
} from "../../api/fileStorage";
// 新增：导入组件
import mavonEditor from 'mavon-editor'

// 新增：导入样式
import 'mavon-editor/dist/css/index.css'

export default {
  components: {
    IconPicker,
    'mavonEditor': mavonEditor.mavonEditor
  },

  data() {
    return {
      multipleSelection: [],
      showSearch: true,
      blogSortOptionList: [],
      params: {
        blogTitle: '',
        blogSortUid: '',
        tagUid: '',
        status: '',
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      addVideoDialogVisible: false,
      addAttachDialogVisible: false,
      tagOptionList: [],
      videoInput: '',
      attachInput: '',
      title: '',
      editingBlogUid: '',
      editingBlogCoverImageUrl: '',
      editingBlogTagList: [],
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
      // 数据总数
      total:0,
      // 加载层信息
      loading: [],
      tableData: []
    }
  },
  computed: {
    ...mapGetters([
      'menu'
    ]),
    /**
     * 检查是否有批量删除的权限
     * @returns {boolean|*}
     */
    canDeleteBatch: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/blog')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/blog/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/blog')
    },
    /**
     * 判断是否可以使用置顶按钮
     * @returns {boolean|*}
     */
    canPromote: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/blog/{uid}/promote') && hasAuth(this.menu, 'DELETE:/api/admin/blog/{uid}/promote')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/blog/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchBlogList();
    this.fetchBlogSortList();
  },
  methods: {
    fetchBlogSortList: function (key){
      getBlogSortList({
        sortByClickCount: false,
        sortByCites: false,
        keyword: key,
        currentPage: 1,
        pageSize: 20
      }).then(res =>{
        this.blogSortOptionList = res.data
      }).catch(err =>{
        console.log(err)
      })
    },
    fetchBlogList: function (){
      getBlogList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 查找按钮点击事件
     */
    handleFind: function () {
      this.params.currentPage = 1;
      this.fetchBlogList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.blogTitle=''
      this.params.blogSortUid = ''
      this.params.tagUid = ''
      this.params.status = ''
      this.fetchBlogList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchBlogList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchBlogList()
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },
    /**
     * 处理复选框选择事件
     * @param val
     */
    handleSelectionChange: function (val) {
      this.multipleSelection = val;
    },
    /**
     * 打开加载层
     */
    openLoading: function () {
      this.loading = this.$loading({
        lock: true,
        text: "正在加载中~",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    },
    /**
     * 设置编辑框 是编辑还是添加
     * @param title
     * @param isEditForm
     */
    beforeShow: function (title, isEditForm) {
      this.title = title
      this.isEditForm = isEditForm

      this.dialogFormVisible = true
    },
    addTag: function (item) {
      if (this.editingBlogTagList.length > 2) {
        this.$message.error("最多添加三个标签,如需继续添加,请先删除一个!")
        return false;
      }
      if (this.editingBlogTagList.indexOf(item) === -1) {
        this.editingBlogTagList.push(item)
        this.form.tagUids += ',' + item.uid
        this.form.tagUids = this.form.tagUids.replace(",,", '')
      }
    },
    /**
     * 移除标签
     * @param item
     */
    removeTag: function (item) {
      this.form.tagUids = this.form.tagUids.replace(item.uid, '').replace(",,", "")
      const index = this.editingBlogTagList.indexOf(item)
      this.editingBlogTagList.splice(index, 1)
    },
    /**
     * tagUids字段检查
     * @param rule
     * @param value
     * @param callback
     */
    tagUidsValidator: function (rule, value, callback) {
      // 去除收尾的逗号
      value = value.trim().replace(/^(\s|,)+|(\s|,)+$/g, '');
      let uids = value.split(',')

      if (uids.length <= 0) {
        callback(new Error('请选择至少一个标签'))
      } else if (uids.length > 3) {
        callback(new Error('请选择最多选择三个标签'))
      } else {
        callback()
      }
    },
    /**
     * 转载地址字段验证
     * @param rule
     * @param value
     * @param callback
     */
    articlesPartValidator: function (rule, value, callback) {
      var urlPattern = /^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)$/gm; // validate fragment locator

      let res = urlPattern.test(value)

      if (!res && !this.form.isOriginal) {
        callback(new Error('请输入正确的转载地址'))
      } else {
        callback()
      }
    },
    /**
     * 处理添加tag
     */
    handleAddTag: function () {
      addTag(this.tagForm).then(res => {
        this.$message.success("添加成功")
        this.handleSearchTag()
      }).catch(err => {
        console.error(err)
      })
    },
    /**
     * 处理查找tag
     */
    handleSearchTag: function () {
      getTagList({
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
    /**
     * 处理删除按钮的点击事件
     * @param scope
     */
    handleDelete: function (scope) {
      this.$confirm('是否确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        this.openLoading();

        if (!scope.row) {

          // 走的是批量删除
          if (this.multipleSelection.length) {
            let selections = this.multipleSelection;
            let uids = [selections.length]
            for (let i = 0; i < selections.length; i++) {
              uids[i] = selections[i].uid
            }
            deleteBlogBatch(uids).then(res => {
              this.fetchBlogList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
          deleteBlog(scope.row.uid).then(res => {
            this.fetchBlogList()
            this.$message.success('删除成功');
            this.loading.close()
          }).catch(() => {
            this.loading.close()
          });

        }
      }).catch(() => {
        this.loading.close()
      });

    },
    /**
     * 添加按钮的点击事件
     */
    handleCreate: function () {
      this.editingBlogUid = ''
      this.editingBlogCoverImageUrl = ''
      this.editingBlogTagList = []
      this.form.title = ''
      this.form.articlesPart = ''
      this.form.tagUids = ''
      this.form.blogSortUid = ''
      this.form.isOriginal = ''
      this.form.coverImageUid = ''
      this.form.summary = ''
      this.form.level = 0
      this.form.status = ''
      this.form.content = ''

      this.beforeShow("新增博客", 0)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    /**
     * 编辑按钮点击事件
     * @param scope
     */
    handleUpdate: function (scope) {
      this.editingBlogUid = scope.row.uid
      if (scope.row.coverImage) {
        this.editingBlogCoverImageUrl = scope.row.coverImage.fileUrl
      } else {
        this.editingBlogCoverImageUrl = ''
      }
      this.editingBlogTagList= scope.row.tagList
      this.form.title = scope.row.title
      this.form.tagUids = scope.row.tagUids
      this.form.blogSortUid = scope.row.blogSortUid
      this.form.isOriginal = scope.row.isOriginal
      this.form.coverImageUid = scope.row.coverImageUid
      this.form.summary = scope.row.summary
      this.form.level = scope.row.level
      this.form.status = scope.row.status
      this.form.articlesPart = scope.row.articlesPart
      getBlogContent(scope.row.uid).then(res => {
        this.form.content = res.data.content
      })

      this.beforeShow("编辑博客", 1)
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },

    /**
     * 置顶按钮的点击事件
     * @param scope
     */
    handlePromote: function (scope) {
      this.$confirm('此操作将会把该博客放到首位, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openLoading();

        promote(scope.row.uid).then(res => {
          this.fetchBlogList()
          this.$message.success('置顶成功');
          this.loading.close()
        }).catch(() => {
          this.loading.close()
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消置顶'
        })
      })
    },
    /**
     * 取消置顶按钮的点击事件
     * @param scope
     */
    handlePromoteCancel: function (scope) {
      this.$confirm('此操作将取消置顶博客, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.openLoading();

        promoteCancel(scope.row.uid).then(res => {
          this.fetchBlogList()
          this.$message.success('取消置顶成功');
          this.loading.close()
        }).catch(() => {
          this.loading.close()
        });

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    /**
     * 图片上传之前的准备
     */
    uploadBefore: function (file) {
      const isImage = checkImgType(file);
      if (!isImage) {
        this.$message.error('文件格式错误');
      }
      return isImage
    },
    /**
     * 上传博客封面
     * @param param
     */
    uploadSelectionCoverImage: function (param) {
      this.openLoading();
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      formData.append("userUid", getUserUid())
      blogCoverImageUpload(formData).then(res => {
        this.form.coverImageUid = res.data.uid
        this.editingBlogCoverImageUrl = res.data.fileUrl
        this.loading.close()
      })
    },
    imageAttachAdd: function (pos, $file) {
      var formdata = new FormData();
      formdata.append('file', $file);
      blogImageAttachUpload(formdata).then(res => {
        this.$refs.md.$img2Url(pos, res.data.fileUrl);
      }).catch(err => {
        console.log(err)
      })
    },
    videoAttachAdd: function (param) {
      this.openLoading()
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
        this.loading.close()
      }).catch(err => {
        this.loading.close()
      })
    },
    addVideo() {
      // 这里获取到的是mavon编辑器实例，上面挂载着很多方法
      const $vm = this.$refs.md
      // 将文件名与文件路径插入当前光标位置，这是mavon-editor 内置的方法
      $vm.insertText($vm.getTextareaDom(),
        {
          prefix: `<video height=100% width=100% src="${this.videoInput}"></video>`,
          subfix: '',
          str: ''
        })

      this.dialogVisible = false
      this.videoInput = ''
    },
    fileAttachAdd: function (param) {
      this.openLoading()
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
        this.loading.close()
      }).catch(err => {
        this.loading.close()
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

    /**
     * 保存操作
     */
    save: function () {
      this.form.tagUids = this.form.tagUids.trim().replace(/^(\s|,)+|(\s|,)+$/g, '');
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            updateBlog(this.editingBlogUid, this.form).then(res => {
              this.$message.success("已保存")
              this.fetchBlogList()
            }).catch(err => {
              console.error(err)
            })
          } else {
            this.form.status = EStatus.DRAFT
            addBlog(this.form).then(res => {
              this.$message.success("已保存")

              this.editingBlogUid = res.data.uid
              this.isEditForm = true;

              this.fetchBlogList()
            }).catch(err => {
              console.error(err)
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },

    submit: function () {
      this.form.tagUids = this.form.tagUids.trim().replace(/^(\s|,)+|(\s|,)+$/g, '');
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            updateBlog(this.editingBlogUid, this.form).then(res => {
              this.$message.success("修改成功")
              this.editingBlogUid = ''
              this.fetchBlogList()
              this.dialogFormVisible = false;
            }).catch(err => {
              console.error(err)
            })
          } else {
            addBlog(this.form).then(res => {
              this.$message.success("添加成功")
              this.fetchBlogList()
              this.dialogFormVisible = false;
            }).catch(err => {
              console.error(err)
            })
          }

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    }
  }
}
</script>
<style>

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.article-cover {
  position: relative;
  width: 100%;
  border-radius: 4px;
}


</style>
