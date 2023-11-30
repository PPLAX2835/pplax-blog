<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container" style="margin: 10px 0 10px 0">
      <el-button
        class="filter-item"
        type="primary"
        @click="handleAdd"
        icon="el-icon-edit"
      >添加博客分类</el-button
      >
    </div>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column type="selection"></el-table-column>

      <el-table-column label="序号" width="60">
        <template slot-scope="scope">
          <span>{{scope.$index + 1}}</span>
        </template>
      </el-table-column>

      <el-table-column label="分类名" width="100">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.sortName }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="简介" width="200">
        <template slot-scope="scope">
			<span
        v-if="scope.row.summary != null"
      >{{ submitStr(scope.row.summary, 30) }}</span
      >
        </template>
      </el-table-column>

      <el-table-column label="内容" width="200">
        <template slot-scope="scope">
			<span
        v-if="scope.row.content != null"
      >{{ submitStr(scope.row.content, 30) }}</span
      >
        </template>
      </el-table-column>

      <el-table-column label="父级分类名" width="150">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.parentBlogSort != null"
          >{{ scope.row.parentBlogSort.sortName }}</el-tag
          >
        </template>
      </el-table-column>

      <el-table-column label="创建时间" width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
          <template v-if="scope.row.status == status.ENABLE">
            <span>正常</span>
          </template>
          <template v-if="scope.row.status == status.FREEZE">
            <span>冻结</span>
          </template>
          <template v-if="scope.row.status == status.DISABLED">
            <span>已删除</span>
          </template>
          <template v-if="scope.row.status == status.STICK">
            <span>顶置</span>
          </template>
        </template>
      </el-table-column>

      <el-table-column label="操作" fixed="right" min-width="170">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="primary" size="small"
          >编辑</el-button
          >
          <el-button @click="handleDelete(scope.row)" type="danger" size="small"
          >物理删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <div class="block">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 添加或修改对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      label-width="100px"
    >
      <el-form ref="form" :model="form" :rules="editRules">
        <el-form-item
          v-if="isEditForm == true"
          label="博客分类UID"
          :label-width="formLabelWidth"
        >
          <el-input v-model="form.uid" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item
          label="分类名"
          :label-width="formLabelWidth"
          required
          prop="sortName"
        >
          <el-input
            v-model="form.sortName"
            auto-complete="off"
            placeholder="请输入分类名"
          ></el-input>
        </el-form-item>

        <el-form-item label="简介" :label-width="formLabelWidth">
          <el-input
            v-model="form.summary"
            auto-complete="off"
            placeholder="请输入简介"
          ></el-input>
        </el-form-item>

        <el-form-item label="内容" :label-width="formLabelWidth">
          <el-input
            v-model="form.content"
            auto-complete="off"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>

        <el-form-item
          label="状态"
          :label-width="formLabelWidth"
          required
          prop="status"
        >
          <el-select
            v-model="form.status"
            size="small"
            placeholder="请选择"
            filterable
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="父级分类" :label-width="formLabelWidth">
          <el-select
            v-model="form.parentBlogSortUid"
            size="small"
            filterable
            remote
            reserve-keyword
            placeholder="请输入父级分类"
            :remote-method="getParentBlogSortList"
            :loading="loading"
          >
            <el-option
              filterable
              remote
              v-for="item in parentBlogSortOptions"
              :key="item.uid"
              :label="item.sortName"
              :value="item.uid"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getBlogSortList, checkSortNameExists, addBlogSort, editBlogSort, physicalDeleteBlogSort } from '../../api/blogSort';
import EStatus from "../../base/EStatus";

export default {

  data() {
    return {
      loading: false,
      tableData: [], //博客分类数据
      keyword: "",
      currentPage: 1,
      pageSize: 10,
      status: EStatus,
      total: 0, //总数量
      title: "增加博客分类",
      dialogFormVisible: false, //控制弹出框
      formLabelWidth: '120px',
      isEditForm: false ,
      parentBlogSortOptions: [],
      statusOptions: [{
        value: 1,
        label: '激活'
      }, {
        value: 0,
        label: '删除'
      }, {
        value: 2,
        label: '冻结'
      }, {
        value: 3,
        label: '置顶'
      }],
      editRules: {
        sortName: [
          { required: true, trigger: 'blur', message: '请输入分类名' },
          { trigger: 'blur', message: '分类名已存在', validator: this.checkExistSortName },
          { max: 10, message: '长度不超过10', trigger: 'blur' }
        ],
        status: [
          { required: true, trigger: 'blur', message: '请选择状态' }
        ]
      },
      form: {
        uid: null,
        sortName: null,
        summary: null,
        content: null,
        status: null,
        parentBlogSortUid: null
      },
      currentEditName: null
    }
  },
  created() {
    var that = this;

    // 请求获得博客分类列表
    var params = {
      keyword: this.keyword,
      currentPage: this.currentPage,
      pageSize: this.pageSize
    }
    this.blogSortList(params);

  },
  methods: {
    /**
     * 获取分类列表
     * @param {*} params
     */
    blogSortList: function(params) {
      getBlogSortList(params).then(response => {
        this.tableData = response.data.records;
        this.currentPage = response.data.current;
        this.pageSize = response.data.size;
        this.total = response.data.total;
      });
    },
    /**
     * 选择框的输入事件，获得父级分类列表
     * @param {*} query
     */
    getParentBlogSortList(query) {
      this.loading = true;

      if(query.trim() != '') {
        var params = {
          keyword: query,
          currentPage: 1,
          pageSize: 10
        }

        getBlogSortList(params).then(response => {
          this.parentBlogSortOptions = response.data.records

          this.loading = false;
        });

      }
    },
    /**
     * 限制字符串长度
     * @param {*} str
     * @param {*} index
     */
    submitStr: function(str, index) {
      if(str.length > index) {
        return str.slice(0, index) + "...";
      }
      return str;
    },
    /**
     * 换页
     * @param {*} val
     */
    handleCurrentChange: function(val) {
      this.currentPage = val;
      var params = {
        keyword: this.keyword,
        currentPage: this.currentPage,
        pageSize: this.pageSize
      }
      this.blogSortList(params);
    },
    /**
     * 添加按钮单击事件，弹出窗口
     */
    handleAdd: function() {
      this.dialogFormVisible = true;
      this.form = {
        uid: null,
        sortName: null,
        summary: null,
        content: null,
        status: EStatus.ENABLE,
        parentBlogSortUid: null
      };
      this.isEditForm = false;
      this.title = '添加博客分类'
    },
    /**
     * 处理编辑按钮单击事件
     * @param {*} row
     */
    handleEdit: function(row) {
      this.form = row;
      this.currentEditName = row.sortName
      this.dialogFormVisible = true;
      this.isEditForm = true;
      this.title = '编辑博客分类'
    },
    /**
     * 处理删除按钮单击事件
     * @param {*} row
     */
    handleDelete: function(row) {
      var that = this;

      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = {
          uid: row.uid
        }
        physicalDeleteBlogSort(params).then(response=> {
          this.$message({
            type: "success",
            message: response.data
          });
          that.blogSortList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
    /**
     * 检查分类名是否已存在
     * @param {*} rule 这个不能删，不然就之间校验失败了
     * @param {*} value
     * @param {*} callback
     */
    checkExistSortName(rule, value, callback) {
      checkSortNameExists({sortName: value}).then(response => {
        if (!response.data && value != this.currentEditName) {
          return callback(new Error('分类名已存在'))
        } else {
          return callback()
        }
      });
    },
    /**
     * 提交表单
     */
    submitForm() {
      this.$refs.form.validate((validate) => {
        if (validate) {

          const params = this.form ;
          if(this.isEditForm) {
            // 编辑分类的提交
            editBlogSort(params).then(response=> {
              this.$message({
                type: "success",
                message: response.data
              });

              this.dialogFormVisible = false;
              this.blogSortList();
            })

          } else {
            // 添加分类的提交
            addBlogSort(params).then(response=> {
              this.$message({
                type: "success",
                message: response.data
              });

              this.dialogFormVisible = false;
              this.blogSortList();
            })
          }
        }
      })

    },


  }
}
</script>

