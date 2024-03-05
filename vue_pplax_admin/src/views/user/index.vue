<template>
  <div class="app-container">
    <!--查询or添加-->
    <el-form v-show="showSearch" :inline="true" ref="form" :model="params" label-width="68px">
      <el-form-item label="昵称">
        <el-input style="width: 150px" size="small" v-model="params.keyword" placeholder="请输入用户昵称"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleFind">查找</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 表格区域 -->
    <div>
      <el-table border :data="tableData" style="width: 100%">
        <el-table-column align="center" prop="avatar" label="头像"  width="120">
          <template slot-scope="scope">
            <div class="block"><el-avatar :size="50" :src="scope.row.userInfo.avatar.fileUrl"></el-avatar></div>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="username" label="用户名" width="120" />
        <el-table-column align="center" prop="userInfo.nickname" label="昵称" width="180" />
        <el-table-column align="center" label="用户角色" width="120">
          <template slot-scope="scope">
            <el-tag>
              {{ scope.row.role.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="lastLoginIp" label="上次登录ip" />
        <el-table-column align="center" prop="loginCount" label="登录次数" width="80" />
        <el-table-column align="center"  label="上次登录登录时间"  width="180" >
          <template slot-scope="scope">
            {{ timeFormat(scope.row.lastLoginTime) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="创建时间"  width="180" >
          <template slot-scope="scope">
            {{ timeFormat(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="修改时间"  width="180" >
          <template slot-scope="scope">
            {{ timeFormat(scope.row.updateTime) }}
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button v-if="canKick" type="danger" size="mini" @click="kick(scope)">强制下线</el-button>
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
import { getList } from '@/api/user'
import { hasAuth } from "../../utils/auth";
import { parseTime } from "../../utils";
import {mapGetters} from "vuex";

export default {

  data() {
    return {
      showSearch: true,
      params: {
        keyword: '',
        currentPage: 1,
        pageSize: 10
      },
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
  },
  created() {
    this.fetchList()
  },
  methods: {
    fetchList: function (){
      getList(this.params).then(res =>{
        this.tableData = res.data
        this.total = res.total
        this.loading.close()
      }).catch(err =>{
        console.log(err)
      })
    },
    /**
     * 检查是否有踢人下线的权限
     * @returns {boolean|*}
     */
    canKick: function () {
      console.log(this.menu)
      return hasAuth(this.menu, 'GET:/api/admin/user/{uid}/kick')
    },
    handleFind: function () {
      this.params.currentPage = 1;
      this.fetchList()
    },
    resetQuery: function (){
      this.params.keyword=null
      this.fetchList()
    },
    /**
     * 单页大小处理
     * @param val
     */
    handleSizeChange: function (val) {
      this.params.pageSize = val
      this.fetchList()
    },
    /**
     * 页数变化处理
     * @param val
     */
    handleCurrentChange: function (val) {
      this.params.currentPage = val
      this.fetchList()
    },
    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    }

  }
}
</script>
