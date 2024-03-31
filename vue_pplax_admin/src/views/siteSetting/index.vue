<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>基础设置</span>
          </div>
          <el-form label-position="right" label-width="100px">
            <el-form-item :label="item.nameZh" v-for="item in typeMap.baseSetting" :key="item.uid">
              <el-input v-model="item.value" size="mini" @blur="submit(item)"></el-input>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>资料卡</span>
          </div>
          <el-form label-position="right" label-width="100px">
            <el-form-item :label="item.nameZh" v-for="item in typeMap.card" :key="item.uid">
              <div v-if="item.nameEn==='favorite'">
                <el-col :span="20">
                  <el-input v-model="item.value" size="mini" @blur="submit(item)"></el-input>
                </el-col>
                <el-col :span="4">
                  <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteFavorite(item)">删除</el-button>
                </el-col>
              </div>
              <div v-else>
                <el-input v-model="item.value" size="mini" @blur="submit(item)"></el-input>
              </div>
            </el-form-item>
            <el-button type="primary" size="mini" icon="el-icon-plus" @click="addFavorite">添加自定义</el-button>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <el-row style="margin-top: 20px">
      <el-card>
        <div slot="header">
          <span>页脚徽标</span>
        </div>
        <el-form :inline="true" v-for="badge in typeMap.footer" :key="badge.uid">
          <el-form-item label="title">
            <el-input v-model="badge.value.title" size="mini" @blur="submit(badge)"></el-input>
          </el-form-item>
          <el-form-item label="url">
            <el-input v-model="badge.value.url" size="mini" @blur="submit(badge)"></el-input>
          </el-form-item>
          <el-form-item label="subject">
            <el-input v-model="badge.value.subject" size="mini" @blur="submit(badge)"></el-input>
          </el-form-item>
          <el-form-item label="value">
            <el-input v-model="badge.value.value" size="mini" @blur="submit(badge)"></el-input>
          </el-form-item>
          <el-form-item label="color">
            <el-input v-model="badge.value.color" size="mini" @blur="submit(badge)"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="deleteBadge(badge)">删除</el-button>
          </el-form-item>
        </el-form>
        <el-button type="primary" size="mini" icon="el-icon-plus" @click="addBadge">添加 badge</el-button>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import { getSiteSettingList, updateSiteSetting, deleteSiteSetting, addSiteSetting } from "../../api/siteSetting";

export default {
  name: "SiteSetting",
  data() {
    return {
      deleteIds: [],
      typeMap: {
        baseSetting: [],
        footer: [],
        card: [],
        link: []
      },
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.typeMap = {
        baseSetting: [],
        footer: [],
        card: [],
        link: []
      }
      getSiteSettingList().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          if (res.data[i].type === 1) {
            this.typeMap.baseSetting.push(res.data[i])
          } else if (res.data[i].type === 2) {
            this.typeMap.card.push(res.data[i])
          } else if (res.data[i].type === 3) {
            this.typeMap.footer.push(res.data[i])
          } else if (res.data[i].type === 4) {
            this.typeMap.link.push(res.data[i])
          }
        }
        console.log(this.typeMap)
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
