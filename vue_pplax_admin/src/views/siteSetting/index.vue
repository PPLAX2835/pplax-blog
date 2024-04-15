<template>
  <div>
      <el-card v-for="item in settingList" :key="item.uid">
        <el-form label-position="right" label-width="100px" >
            <el-row>
              <el-col :span="10">
                <el-form-item label="中文名">
                  <el-input v-model="item.nameZh" size="mini"></el-input>
                </el-form-item>
                <el-form-item label="英文名">
                  <el-input v-model="item.nameEn" size="mini"></el-input>
                </el-form-item>
                <el-form-item label="值">
                  <el-input v-model="item.value" size="mini"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-button @click="submit(item)" >保存</el-button>
              <el-button @click="deleteSetting(item)" type="danger">删除</el-button>
            </el-row>
        </el-form>
      </el-card>
      <el-button @click="addSetting(item)" type="warning">新增配置</el-button>
  </div>
</template>

<script>
import { getSiteSettingList, updateSiteSetting, deleteSiteSetting, addSiteSetting } from "../../api/siteSetting";
import {siteLogoImageUpload} from "../../api/fileStorage";
import {checkImgType} from "../../utils/validate";

export default {
  name: "SiteSetting",
  data() {
    return {
      deleteIds: [],
      settingList: [],
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      getSiteSettingList().then(res => {
        this.settingList = res.data
      })
    },
    addSetting() {
      this.settingList.push({
        nameEn: "",
        nameZh: "",
        value: ""
      })
    },
    deleteSetting(item) {
      this.$confirm('是否确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        deleteSiteSetting(item.uid).then(resp => {
          this.$message.success('删除成功');
          this.getData()
        })
      })
    },
    submit(item) {
      if (item.uid === undefined) {
        addSiteSetting(item).then(res => {
          this.$message.success('保存成功');
          this.getData()
        })
      } else {
        updateSiteSetting(item.uid, item).then(res => {
          this.$message.success('保存成功');
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
