<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item>
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

        <el-table-column width="180" align="center" label="类型">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === 0" type="info">群聊</el-tag>
            <el-tag v-else>私聊</el-tag>
          </template>
        </el-table-column>

        <el-table-column width="180" align="center" label="成员1">
          <template slot-scope="scope">
            <span v-if="scope.row.type === 1">{{ scope.row.member1.userInfo.nickname }}</span>
          </template>
        </el-table-column>

        <el-table-column width="180" align="center" label="成员2">
          <template slot-scope="scope">
            <span v-if="scope.row.type === 1">{{ scope.row.member2.userInfo.nickname }}</span>
          </template>
        </el-table-column>

        <el-table-column width="180" align="center" label="添加时间">
          <template slot-scope="scope">
            <span>{{ timeFormat(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="200" fixed="right" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
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
    <el-dialog center :title="title" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="form">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getChatRoomList, deleteChatRoomBatch, deleteChatRoom } from "../../api/chatRoom";
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";

export default {
  components: {
  },

  data() {
    return {
      multipleSelection: [],
      showSearch: true,
      params: {
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 编辑/添加表单用到的
      formLabelWidth: '120px',
      isEditForm: false,
      dialogFormVisible: false,
      title: '',
      editingRoleUid: '',
      form: {
        level: '',
        name: ''
      },
      rules: {
        level: [
          { required: true, message: '请输入推荐等级', trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入标签名', trigger: 'blur' },
          { min: 1, max: 20, message: '标签名长度限制在1到20之间', trigger: 'change' }
        ]
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
      return hasAuth(this.menu, 'DELETE:/api/admin/chatRoom')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/chatRoom/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchChatRoomList();
  },
  methods: {
    fetchChatRoomList: function (){
      getChatRoomList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchChatRoomList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchChatRoomList()
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

        if (scope.row === undefined) {

          // 走的是批量删除
          if (this.multipleSelection.length) {
            let selections = this.multipleSelection;
            let uids = [selections.length]
            for (let i = 0; i < selections.length; i++) {
              uids[i] = selections[i].uid
            }
            deleteChatRoomBatch(uids).then(res => {
              this.fetchChatRoomList()
              this.$message.success('删除成功');
              this.loading.close()
            }).catch(() => {
              this.loading.close()
            });
          }
        } else {
          // 走单独删除
            deleteChatRoom(scope.row.uid).then(res => {
            this.fetchChatRoomList()
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

    submit: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.isEditForm) {
            // updateTag(this.editingTagUid, this.form).then(res => {
            //   this.$message.success("修改成功")
            //   this.editingTagUid = ''
            //   this.fetchChatRoomList()
            //   this.dialogFormVisible = false;
            //   this.close()
            // }).catch(err => {
            //   console.error(err)
            // })
          } else {
            // addTag(this.form).then(res => {
            //   this.$message.success("添加成功")
            //   this.fetchChatRoomList()
            //   this.dialogFormVisible = false;
            //   this.close()
            // }).catch(err => {
            //   console.error(err)
            // })
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
