<template>
  <div>

    <el-card>
      <el-tabs value="site">
        <el-tab-pane label="站点配置" name="site">
          <el-form label-position="right" label-width="100px" >
            <el-row>
              <el-col :span="6">
                <el-form-item label="网站Logo" prop="siteLogo">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.siteLogo.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.siteLogo.value"  style="width: 150px;"/>
                  </el-tooltip>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="网站名称" prop="blogName">
                  <el-input v-model="settingMap.blogName.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="用户默认角色" prop="defaultRoleUid">
                    <el-select filterable size="small" :filter-method="handleRoleSearch" v-model="settingMap.defaultRoleUid.value" placeholder="请选择">
                      <el-option v-for="role in roleList" :label="role.roleName" :value="role.uid"></el-option>
                    </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="站点域名" prop="siteDomain">
                  <el-input v-model="settingMap.siteDomain.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="api域名" prop="apiDomain">
                  <el-input v-model="settingMap.apiDomain.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="关键词" prop="keyword">
                  <el-input v-model="settingMap.keyword.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="站点介绍" prop="summary">
                  <el-input v-model="settingMap.summary.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="Copyright" prop="copyright">
                  <el-input v-model="settingMap.copyright.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="备案号" prop="recordNum">
                  <el-input v-model="settingMap.recordNum.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="网站图片" name="image">
          <el-form>
            <el-row>
              <el-col :span="6">
                <el-form-item label="游客头像" prop="touristAvatar">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.touristAvatar.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.touristAvatar.value"  style="width: 150px;"/>
                  </el-tooltip>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="默认博客封面" prop="defaultBlogCoverImage">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.defaultBlogCoverImage.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.defaultBlogCoverImage.value" style="width: 80%" />
                  </el-tooltip>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="游客背景图片" prop="touristBackground">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.touristBackground.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.touristBackground.value" style="width: 80%" />
                  </el-tooltip>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="说说页面背景图" prop="sayPageBackground">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.sayPageBackground.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.sayPageBackground.value" style="width: 80%" />
                  </el-tooltip>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="留言页面背景图" prop="leaveMessagePageBackground">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.leaveMessagePageBackground.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.leaveMessagePageBackground.value" style="width: 80%" />
                  </el-tooltip>
                </el-form-item>
              </el-col>
            </el-row>


          </el-form>
        </el-tab-pane>
        <el-tab-pane label="作者信息" name="author">
          <el-form label-position="right" label-width="100px" >
            <el-row>
              <el-col :span="6">
                <el-form-item label="作者头像" prop="authorAvatar">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.authorAvatar.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.authorAvatar.value"  style="width: 150px;"/>
                  </el-tooltip>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="作者头像挂件" prop="authorAvatarPendant">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.authorAvatarPendant.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.authorAvatarPendant.value"  style="width: 150px;"/>
                  </el-tooltip>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="作者背景图片" prop="authorBackground">
                  <el-tooltip>
                    <div slot="content" style="text-align: center;min-width:180px;">
                      <el-input v-model="settingMap.authorBackground.value" auto-complete="off"></el-input>
                    </div>
                    <img :src="settingMap.authorBackground.value"  style="width: 80%"/>
                  </el-tooltip>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="作者" prop="author">
                  <el-input v-model="settingMap.author.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6">
                <el-form-item label="Gitee" prop="gitee">
                  <el-input v-model="settingMap.gitee.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="Github" prop="github">
                  <el-input v-model="settingMap.github.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="bilibili" prop="bilibili">
                  <el-input v-model="settingMap.bilibili.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>


            <el-row>
              <el-col :span="6">
                <el-form-item label="qq" prop="qq">
                  <el-input v-model="settingMap.qq.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>

              <el-col :span="6">
                <el-form-item label="email" prop="email">
                  <el-input v-model="settingMap.email.value" auto-complete="off"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-form-item label="关于我" prop="aboutMe">
                <mavon-editor placeholder="输入内容..." ref=md v-model="settingMap.aboutMe.value"
                              @imgAdd="imageAttachAdd">
                </mavon-editor>
              </el-form-item>
            </el-row>
          </el-form>
        </el-tab-pane>

        <el-tab-pane label="存储" name="storage">
          <el-form>
            <el-row>
              <el-form-item prop="storageMode" label="存储模式" >
                <div>
                  <el-radio v-model="settingMap.storageMode.value" :label="'localStorage'" border>本地存储</el-radio>
                  <el-radio v-model="settingMap.storageMode.value" :label="'minio'" border>minio</el-radio>
                  <el-radio v-model="settingMap.storageMode.value" :label="'qiniu'" border>七牛云</el-radio>
                </div>
              </el-form-item>
            </el-row>
            <div v-if="settingMap.storageMode.value === 'localStorage'">
              <el-row>
                <el-col :span="6">
                  <el-form-item label="基本路径" prop="localStorageBasePath">
                    <el-input v-model="settingMap.localStorageBasePath.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <div v-if="settingMap.storageMode.value === 'minio'">
              <el-row>
                <el-col :span="6">
                  <el-form-item label="端点" prop="minioEndpoint">
                    <el-input v-model="settingMap.minioEndpoint.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="访问key" prop="minioAccessKey">
                    <el-input v-model="settingMap.minioAccessKey.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6">
                  <el-form-item label="访问密钥" prop="minioSecretKey">
                    <el-input v-model="settingMap.minioSecretKey.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="桶名" prop="minioBucketName">
                    <el-input v-model="settingMap.minioBucketName.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
            <div v-if="settingMap.storageMode.value === 'qiniu'">
              <el-row>
                <el-col :span="6">
                  <el-form-item label="地区" prop="qiniuZone">
                    <el-select size="small" v-model="settingMap.qiniuZone.value" placeholder="请选择地区">
                      <el-option label="华东" value="huadong" />
                      <el-option label="华北" value="huabei" />
                      <el-option label="华南" value="huanan" />
                      <el-option label="北美" value="beimei" />
                      <el-option label="东南亚" value="xinjiapo" />
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="端点" prop="qiniuEndpoint">
                    <el-input v-model="settingMap.qiniuEndpoint.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="访问key" prop="qiniuAccessKey">
                    <el-input v-model="settingMap.qiniuAccessKey.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="6">
                  <el-form-item label="访问密钥" prop="qiniuSecretKey">
                    <el-input v-model="settingMap.qiniuSecretKey.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="6">
                  <el-form-item label="桶名" prop="qiniuBucketName">
                    <el-input v-model="settingMap.qiniuBucketName.value" auto-complete="off"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>

          </el-form>
        </el-tab-pane>
      </el-tabs>
      <el-row>
        <el-button @click="submit" type="primary" >保存</el-button>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { getSiteSettingMap, updateSiteSetting } from "../../api/siteSetting";
import {getRoleList} from "../../api/role";
import {aboutMeImageAttachUpload} from "../../api/fileStorage";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  components: {
    'mavonEditor': mavonEditor.mavonEditor
  },
  name: "SiteSetting",
  data() {
    return {
      deleteIds: [],
      roleList: [],
      roleParam: {
        keyword: '',
        currentPage: 1,
        pageSize: 5
      },
      settingMap: {
        aboutMe: {},
        apiDomain: {},
        author: {},
        authorAvatar: {},
        authorAvatarPendant: {},
        authorBackground: {},
        authorInfo: {},
        bilibili: {},
        blogName: {},
        copyright: {},
        defaultBlogCoverImage: {},
        defaultRoleUid: {},
        email: {},
        gitee: {},
        github: {},
        keyword: {},
        leaveMessagePageBackground: {},
        localStorageBasePath: {},
        minioAccessKey: {},
        minioBucketName: {},
        minioEndpoint: {},
        minioSecretKey: {},
        qiniuAccessKey: {},
        qiniuBucketName: {},
        qiniuEndpoint: {},
        qiniuSecretKey: {},
        qiniuZone: {},
        qq: {},
        recordNum: {},
        sayPageBackground: {},
        siteDomain: {},
        siteLogo: {},
        storageMode: {},
        summary: {},
        touristAvatar: {},
        touristBackground: {},
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      getSiteSettingMap().then(res => {
        this.settingMap = res.data
      })
      getRoleList(this.roleParam).then(res => {
        this.roleList = res.data
      })
    },
    handleRoleSearch(e) {
      this.roleParam.keyword = e
      getRoleList(this.roleParam).then(res => {
        this.roleList = res.data
      })
    },
    imageAttachAdd: function (pos, $file) {
      var formdata = new FormData();
      formdata.append('file', $file);
      aboutMeImageAttachUpload(formdata).then(res => {
        this.$refs.md.$img2Url(pos, res.data.fileUrl);
      }).catch(err => {
        console.log(err)
      })
    },
    submit(item) {
      let form = {}

      Object.keys(this.settingMap).forEach(key => {
        let value = ''
        if (typeof this.settingMap[key].value === 'object') {
          value = JSON.stringify(this.settingMap[key].value)
        } else {
          value = this.settingMap[key].value
        }

        form[key] = {
          value: value,
          uid: this.settingMap[key].uid
        }
      })

      updateSiteSetting(form).then(resp => {
        this.$message.success('保存成功');
      })

    }
  }
}
</script>

<style scoped>

</style>
