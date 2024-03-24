<template>
  <div class="dashboard-container">
    <el-row class="panel-group" :gutter="40">

      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="eye" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">今日访问量：</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="visitAddTotal" :duration="3200"></count-to>
          </div>
        </div>
      </el-col>

      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class='card-panel'>
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="peoples" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">用户数:</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="userTotal" :duration="2600"></count-to>
          </div>
        </div>
      </el-col>

      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" >
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="message" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">评论数：</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="commentTotal" :duration="3000"></count-to>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shoppingCard">
            <svg-icon icon-class="form" class-name="card-panel-icon" />
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">文章数:</div>
            <count-to class="card-panel-num" :startVal="0" :endVal="blogTotal" :duration="3600"></count-to>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top:1.25rem">
      <el-col :xs="24" :sm="24" :lg="8">
        <el-card>
          <div class="e-title">文章阅读量排行</div>
          <el-table :data="blogList" style="width: 100%;padding-top: 15px">
            <el-table-column label="标题" width="300">
              <template slot-scope="scope">
                <el-link style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;" :underline="false"
                         target="_blank" :href="'http://pplax.xyz/' + 'articleInfo?articleId=' + scope.row.uid">{{ scope.row.title }}</el-link>
                <!-- 因为前台还没有，用了个临时的，这里记得后来改 -->
              </template>
            </el-table-column>
            <el-table-column label="点击量" prop="clickCount" width="100" align="center" />
          </el-table>
        </el-card>
      </el-col>

    </el-row>


  </div>
</template>

<script>
import { init } from "../../api/home";
import { mapGetters } from "vuex";
import CountTo from "vue-count-to";
export default {
  name: "dashboard",
  computed: {
    ...mapGetters([

    ])
  },
  components: {
    CountTo
  },
  created() {
    init().then(resp => {
      let data = resp.data

      this.visitAddTotal = data.statistics.visitAddTotal
      this.userTotal = data.statistics.userTotal
      this.commentTotal = data.statistics.commentTotal
      this.blogTotal = data.statistics.blogTotal

      this.blogList = data.blogList
    })
  },
  data() {
    return {
      visitAddTotal: 6,
      userTotal: 50,
      commentTotal: 15,
      blogTotal: 20,

      blogList: [],
    }
  },
  methods: {

  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.btn {
  width: 80px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: 14px;
  display: inline-block;
  white-space: nowrap;
  cursor: pointer;
  background: #fff;
  border: 1px solid #dcdfe6;
  color: #606266;
}

.btnClick {
  color: #409eff;
  border-color: #c6e2ff;
  background-color: #ecf5ff;
}

.btn:hover {
  color: #409eff;
  border-color: #c6e2ff;
  background-color: #ecf5ff;
}

.statistics-item {
  width: 600px;
  height: 400px;
  float: left;
  margin: 20px auto;
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);
    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }
      .icon-people {
        background: #40c9c6;
      }
      .icon-message {
        background: #36a3f7;
      }
      .icon-money {
        background: #f4516c;
      }
      .icon-shoppingCard {
        background: #34bfa3;
      }
    }
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shoppingCard {
      color: #34bfa3;
    }
    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px 70px 0 0;
      .card-panel-text {
        line-height: 18px;;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}
</style>
