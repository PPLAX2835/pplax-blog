<template>
  <div>
    <el-row :gutter="20">
        <el-card>
          <el-form label-position="right" label-width="100px">
            <el-form-item :label="item.nameZh" v-for="item in settingList" :key="item.uid">
              <el-col :span="8">
                <el-upload v-if="item.nameEn === 'siteLogo'" action="" class="avatar-uploader" :show-file-list="false"
                           :before-upload="uploadBefore" :http-request="uploadSectionAvatar" :data="item">
                  <img v-if="JSON.parse(item.value) !== undefined && JSON.parse(item.value).fileUrl !== undefined" :src="JSON.parse(item.value).fileUrl" class="avatar" alt="">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <el-input v-else v-model="item.value" size="mini" @blur="submit(item)"></el-input>
              </el-col>
              <el-col :span="3">
                <el-button type="danger">删除</el-button>
              </el-col>
            </el-form-item>
          </el-form>
        </el-card>
    </el-row>
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
     * 头像上传
     * @param param
     */
    uploadSectionAvatar: function (param) {
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
    addFavorite() {
      this.typeMap.card.push({
        nameEn: "favorite",
        nameZh: "自定义",
        type: 2,
        value: "{\"title\":\"\",\"content\":\"\"}"
      })
    },
    addBadge() {
      this.typeMap.footer.push({
        nameEn: "badge",
        nameZh: "徽标",
        type: 3,
        value: {
          color: "",
          subject: "",
          title: "",
          url: "",
          value: ""
        }
      })
    },
    deleteFavorite(favorite) {
      this.$confirm('是否确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        deleteSiteSetting(favorite.uid).then(resp => {
          this.$message.success('删除成功');
          this.getData()
        })
      })
    },
    deleteBadge(badge) {

      this.$confirm('是否确定删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        deleteSiteSetting(badge.uid).then(resp => {
          this.$message.success('删除成功');
          this.getData()
        })
      })
    },
    submit(item) {
      if (item.uid === undefined) {
        addSiteSetting(item).then(res => {
          this.getData()
        })
      } else {
        updateSiteSetting(item.uid, item).then(res => {
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
