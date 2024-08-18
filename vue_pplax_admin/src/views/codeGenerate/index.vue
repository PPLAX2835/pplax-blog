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
            <el-button v-if="canViewTable" type="primary" size="mini" @click="handleViewTable(scope)">查看字段</el-button>
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

    <!-- 数据库表详情 -->
    <el-dialog center :title="dbTableTitle" :visible.sync="dialogDbTableVisible" width="62%" :fullscreen="isFullScreen">
      <el-table border :data="dbTableData">
        <el-table-column width="180" align="center" prop="columnName" label="列名"></el-table-column>
        <el-table-column width="180" align="center" prop="dataType" label="数据类型"></el-table-column>
        <el-table-column width="180" align="center" prop="columnComment" label="描述"></el-table-column>
        <el-table-column width="180" align="center" prop="columnKey" label="key"></el-table-column>
        <el-table-column width="180" align="center" prop="EXTRA" label="EXTRA"></el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import IconPicker from "../../components/IconPicker"
import { mapGetters } from "vuex";
import {getTable, getTableList} from "../../api/codeGenerate";

export default {
  components: {
    IconPicker
  },

  data() {
    return {
      multipleSelection: [],
      showSearch: true,
      isFullScreen: false,
      params: {
        keyword: '',
        currentPage: 1,
        pageSize: 10
      },
      // 数据总数
      total:0,
      // 加载层信息
      loading: [],
      tableData: [],
      // 展示数据库表数据
      dialogDbTableVisible: false,
      dbTableTitle: '',
      dbTableData: []
    }
  },
  computed: {
    ...mapGetters([
      'menu'
    ]),
    /**
     * 检查是否有查看表详情的权限
     * @returns {boolean|*}
     */
    canViewTable: function () {
      return hasAuth(this.menu, 'GET:/api/admin/codeGenerate/table/{tableName}')
    },
  },
  created() {
    this.openLoading();
    this.fetchTableList();
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
     * 处理查看表详情按钮点击事件
     * @param scope
     */
    handleViewTable(scope) {
      this.dialogDbTableVisible = true
      this.dbTableTitle = scope.row.tableName

      getTable(this.dbTableTitle).then(res => {
        this.dbTableData = res.data
      })
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
