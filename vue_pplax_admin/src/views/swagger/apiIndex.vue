<template>
  <div class = "apiindex-class">
    <el-row>
      <el-col :span="24" >
        <el-tabs tab-position="left" v-model="activeName">
          <el-tab-pane v-for="(menu, index) in menuTree" :key="index" :label="menu.name" :name="menu.name">
              <api-details v-for="path in menu.paths" :path="path"></api-details>
          </el-tab-pane>
<!--          <el-tab-pane label="接口测试" name="second">-->
<!--            <api-test></api-test>-->
<!--          </el-tab-pane>-->
          <el-tab-pane label="响应模型">
            <el-collapse >
              <el-collapse-item v-for="(definition, name) in v2ApiDocs.definitions" :name="name" class="collapse-class">
                <template slot="title" >
                  <div>
                    {{name}}
                    <i class="header-icon el-icon-info"></i>
                  </div>
                </template>
                <el-card>
                  <json-viewer :value="definition"></json-viewer>
                </el-card>
              </el-collapse-item>
            </el-collapse>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>

  </div>
</template>
<script>
import JsonViewer from "vue-json-viewer";
import apiDetails from "./apiDetails";
import {getV2ApiDocs} from "../../api/swagger";
// import apiTest from "./apiTest";
export default {
  props: [
    'resource'
  ],
  data() {
    return {
      activeName: '',
      v2ApiDocs: [],
      friendlyPaths: [],
      menuTree: []
    };
  },
  components: {
    apiDetails,
    JsonViewer
  },
  watch: {
    resource(newVal) {
      // 当props中的resource变化时执行此代码
      this.fetchApiDocs(newVal);
    }
  },
  methods: {
    fetchApiDocs(resource) {
      if (resource) {
        getV2ApiDocs(resource).then(res => {
          this.v2ApiDocs = res.data
          this.friendlyPaths = this.getFriendlyPaths(this.v2ApiDocs)
          this.menuTree = this.getMenuTreeObj(this.v2ApiDocs, this.friendlyPaths)
          console.log(this.menuTree)
        });
      }
    },
    /**
     * /获取友好的path对象，原生的path对象太复杂不方便取数
     * @param swaggerResources
     * @returns {boolean|*[]}
     */
    getFriendlyPaths: (swaggerResources) => {
      let paths = swaggerResources.paths;
      if (!paths) return false;
      let friendlyPaths = [];
      //遍历第一层
      for (let key in paths) {
        let friendlyPath = {};
        friendlyPath.path = key;
        let tem = paths[key];
        //遍历第二层
        for (let key in tem) {
          friendlyPath.sendWay = key;
          let tem2 = tem[key];
          friendlyPath.tags = tem2.tags;
          friendlyPath.summary = tem2.summary;
          friendlyPath.description = tem2.description;
          friendlyPath.operationId = tem2.operationId;
          friendlyPath.consumes = tem2.consumes;
          friendlyPath.produces = tem2.produces;
          friendlyPath.parameters = tem2.parameters;
          friendlyPath.responses = tem2.responses;
        }
        friendlyPaths.push(friendlyPath);
      }
      return friendlyPaths;
    },
    /**
     * 获取完整的、友好的菜单树，包括所有信息
     * @param v2ApiDocs
     * @param friendlyPaths
     * @returns {boolean|*}
     */
    getMenuTreeObj: (v2ApiDocs, friendlyPaths) => {
      let tagsArry = v2ApiDocs.tags;
      let pathArry = friendlyPaths;
      if (!tagsArry) return false;
      if (!pathArry) return false;
      for (let i = 0; i < tagsArry.length; i++) {//遍历标签
        let name = tagsArry[i].name;
        let tag = tagsArry[i];
        tag.paths = [];
        for (let i = 0; i < pathArry.length; i++) { //遍历paths查找对应的详情数据
          if (pathArry[i].tags[0].indexOf(name) >= 0) {
            tag.paths.push(pathArry[i]);
          }
        }
      }
      return tagsArry;
    },
  },
  mounted() {
    // 页面加载时调用一次
    this.fetchApiDocs(this.resource);
  }
};
</script>

<style scoped>
</style>
