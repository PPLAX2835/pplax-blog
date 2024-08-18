<template>
  <div class="app-container">
    <!--查询or其他操作-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="表名">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入表名"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
      </el-form-item>

    </el-form>

    <!-- 表格区域 -->
    <div style="margin-top: 5px">
      <el-table border :data="tableData" style="width: 100%" :default-sort="{ prop: 'sort', order: 'descending' }"
                @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection" />
        <el-table-column width="180" align="center" prop="tableName" label="表名称"></el-table-column>
        <el-table-column width="180" align="center" prop="tableComment" label="表描述"></el-table-column>
        <el-table-column width="180" align="center" prop="ENGINE" label="ENGINE"></el-table-column>

        <el-table-column width="180" align="center" label="添加时间">
          <template slot-scope="scope">
            <span>{{ timeFormat(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column width="250" fixed="right" align="center" label="操作" class-name="small-padding fixed-width">
          <template slot-scope="scope">
<!--            <el-button v-if="canUpdate" type="primary" size="mini" @click="handleUpdate(scope)">编辑</el-button>-->
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

  </div>
</template>

<script>
import { getMenuTree } from "../../api/menu";
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import IconPicker from "../../components/IconPicker"
import { EStatus } from "../../base/EStatus"
import { mapGetters } from "vuex";
import {getTableList} from "../../api/codeGenerate";

export default {
  components: {
    IconPicker
  },

  data() {
    return {
      multipleSelection: [],
      multipleMenuSelection: [],
      showSearch: true,
      isFullScreen: false,
      params: {
        keyword: '',
        currentPage: 1,
        pageSize: 10
      },
      statusList: [],
      // 数据总数
      total:0,
      // 加载层信息
      loading: [],
      tableData: [],
      menuTree: [],
      isMenuFullSelect: false
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
      return hasAuth(this.menu, 'DELETE:/api/admin/role')
    },
    /**
     * 检查是否有删除的权限
     * @returns {boolean|*}
     */
    canDelete: function () {
      return hasAuth(this.menu, 'DELETE:/api/admin/role/{uid}')
    },
    /**
     * 检查是否有添加的权限
     * @returns {boolean|*}
     */
    canAdd: function () {
      return hasAuth(this.menu, 'POST:/api/admin/role')
    },
    /**
     * 检查是否有更新的权限
     * @returns {boolean|*}
     */
    canUpdate: function () {
      return hasAuth(this.menu, 'PUT:/api/admin/role/{uid}')
    },
  },
  created() {
    this.statusList = EStatus;
    this.openLoading();
    this.fetchTableList();
    this.fetchMenuTree();
  },
  methods: {
    fetchTableList: function (){
      getTableList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    fetchMenuTree: function (){
      getMenuTree().then(res =>{
        this.menuTree = res.data
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
      this.fetchTableList()
    },
    /**
     * 重置查询参数
     */
    resetQuery: function (){
      this.params.keyword=''
      this.fetchTableList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchTableList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchTableList()
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
     * 处理菜单复选框选择事件
     * @param val
     */
    handleMenuSelectionChange: function (val) {
      this.multipleMenuSelection = val;
    },
    /**
     * 处理用户手动选中事件
     * @param selection
     * @param row
     */
    handleMenuSelection: function (selection, row) {
      let selected = (selection.length > 0) && selection.indexOf(row) !== -1; //为true时选中，为false未选中
      if (!selected && this.isMenuFullSelect) {
        this.isMenuFullSelect = false
      }
      this.menuRecursionSelection(row, selected)
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

  }
}
</script>
