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
                  <el-upload v-if="item.nameEn === 'siteLogo'" action="" class="avatar-uploader" :show-file-list="false"
                             :before-upload="uploadBefore" :http-request="uploadSectionLogo" :data="item">
                    <img v-if="JSON.parse(item.value) !== undefined && JSON.parse(item.value).fileUrl !== undefined" :src="JSON.parse(item.value).fileUrl" class="avatar" alt="">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                  <el-input v-else v-model="item.value" size="mini"></el-input>
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
    /**
     * 图片上传之前的验证
     * @param file
     * @returns {boolean}
     */
    uploadBefore: function (file) {
      const isImage = checkImgType(file);
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error('文件格式错误');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return isImage && isLt2M;
    },
    /**
     * Logo上传
     * @param param
     */
    uploadSectionLogo: function (param) {
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      siteLogoImageUpload(formData).then(res => {
        param.data.value = JSON.stringify(res.data)

        this.submit(param.data)
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
