<template>
    <div class='user-main container'>
        <div class="user-warpper">
            <div class="userBox">
                <div class="backgroupImg">
                    <img v-lazy="user.userInfo.spaceBackgroundPicture ? user.userInfo.spaceBackgroundPicture.fileUrl : getWebSiteInfoValue('touristBackground')">

                    <el-row type="flex" class="row-bg top-btn">
                        <el-col :span="18">
                            <div class="grid-content bg-purple">
                                <a @click="btnClike(0)" class="topBtnItem hand-style">
                                    <div>{{ count.article }}</div>
                                    文章
                                </a>
                            </div>
                        </el-col>
                        <el-col :span="18">
                            <div class="grid-content bg-purple-light">
                                <a @click="btnClike(1)" class="topBtnItem hand-style">
                                    <div>{{ count.collect }}</div>
                                    收藏
                                </a>
                            </div>
                        </el-col>
                    </el-row>

                    <div v-if="loginUser.uid === userUid" class="more hand-style">
                        <div class="menu">
                            <ul>
                                <li @click="handleEditArticle">
                                  <svg-icon icon-class="message"></svg-icon> 撰写博客
                                </li>
                                <li @click="handleUpdateInfo">
                                    <svg-icon icon-class="edit"></svg-icon> 修改资料
                                </li>
                                <li @click="handleClikeMoreData">
                                    <svg-icon icon-class="xiangqing"></svg-icon> 更多资料
                                </li>
                                <li>
                                    <el-upload class="avatar-uploader" :show-file-list="false" ref="upload" name="filedatas"
                                         action=""
                                        :http-request="uploadBjCoverFile"
                                        :before-upload="handleUploadBefore" multiple>
                                        <svg-icon icon-class="photo"></svg-icon> 修改封面
                                    </el-upload>
                                </li>
                                <li @click="feedbackDialogTableVisible = true">
                                    <svg-icon icon-class="feedback"></svg-icon> 添加反馈
                                </li>
                            </ul>
                        </div>
                        <i class="el-icon-more-outline"></i>
                    </div>

                </div>
                <div class="user-item">
                    <div class="toolbar">
                        <img class="cover" :src="user.userInfo.avatar !== undefined ? user.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')" alt="">
                    </div>
                    <div class="userInfo">
                        <div class="nickname">
                            昵称：<span>{{ user.userInfo.nickname }}</span>

                        </div>
                        <div class="desc">
                            个人简介：{{ user.userInfo.summary ? user.userInfo.summary : "这家伙很懒，什么都没有写..." }}
                        </div>
                    </div>
                </div>

            </div>
            <div class="bottom-box">
                <div class="title">
                    <ul>
                        <li ref="btn" :class="index === 0 ? 'active hand-style' : 'hand-style'" @click="btnClike(index)"
                            v-for="(item, index) in btnList" :key="index">
                            <span class="item-title">
                                <i :class="item.icon"></i> {{ item.name }}
                            </span>
                        </li>
                    </ul>

                </div>
                <!-- <div class="btnBox">
                    <div class="btn hand-style" @click="handleClike(1)">发布</div>
                    <div class="btn hand-style" @click="handleClike(2)">待审核</div>
                    <div class="btn hand-style" @click="handleClike(0)">下架</div>
                </div> -->
                <div class="articleBox" v-if="dataList.length">
                    <div class="articleItem">
                        <div v-if="pageData.index != 2" class="article" v-for="(item, index) in dataList" :key="index">
                            <router-link :to="'/blog/' + item.uid">
                                <div class="article-cover hand-style">
                                    <img v-lazy="item.coverImage !== undefined ? item.coverImage.fileUrl : getWebSiteInfoValue('defaultBlogCoverImage')" :key="item.uid + 'coverImage'">
                                </div>
                            </router-link>

                            <div class="article-info">
                                <div style="display: flex;">
                                    <router-link :to="'/blog/' + item.uid">
                                        <div class="article-title xiahuaxian">
                                            {{ item.title }}
                                        </div>
                                    </router-link>
                                    <div class="status" :style="{ backgroundColor: statusList[item.status].style }">
                                        {{ statusList[item.status].name }}
                                    </div>
                                    <div class="articleBtn" v-if="loginUser.uid === userUid">
                                        <div v-if="pageData.index === 0">
                                            <el-tooltip class="item" effect="dark" content="修改文章" placement="top">
                                                <el-button type="primary" size="mini" @click="handleUpdateArticle(item.uid)"
                                                    icon="el-icon-edit" circle></el-button>
                                            </el-tooltip>
                                            <el-tooltip class="item" effect="dark" content="删除文章" placement="top">
                                                <el-button type="danger" size="mini"
                                                    @click="handleDeleteArticle(index, item.uid)" icon="el-icon-delete"
                                                    circle></el-button>
                                            </el-tooltip>
                                        </div>

                                        <el-tooltip v-if="pageData.index === 1" class="item" effect="dark" content="取消收藏"
                                            placement="top">
                                            <el-button type="danger" size="mini" @click="handleCanCollect(index, item.uid)"
                                                icon="el-icon-delete" circle></el-button>
                                        </el-tooltip>
                                    </div>
                                </div>


                                <div class="article-desc">
                                    {{ item.summary }}
                                </div>
                                <div class="article-tag">
                                    <el-tooltip class="item1" effect="dark" content="文章分类" placement="top">
                                        <el-tag class="hand-style" size="mini"
                                            @click="handleClike(item.blogSort.uid, '/blogSort')">
                                            <i class=" el-icon-folder-opened"></i> {{ item.blogSort.sortName }}
                                        </el-tag>
                                    </el-tooltip>
                                    <el-tooltip class="item1" effect="dark" content="文章标签" placement="top"
                                        v-for="tag in item.tagList" :key="tag.uid">

                                        <el-tag class="hand-style" :type="tagStyle[Math.round(Math.random() * 4)]"
                                            size="mini" @click="handleClike(tag.uid, '/tags')">
                                            <i class="el-icon-collection-tag"></i> {{ tag.name
                                            }}</el-tag>
                                    </el-tooltip>
                                </div>
                                <div class="article-user">
                                    <span class=" item">
                                        <i class=" el-icon-user"></i>
                                        <span class="nickname">{{ item.user.userInfo.username }}</span>
                                    </span>

                                    <span class="time item">
                                        <i class="el-icon-time"></i>{{ timeFormat(item.createTime) }}
                                    </span>
                                </div>

                            </div>
                        </div>

                    </div>
                    <!-- 分页按钮 -->
                    <sy-pagination :currentPage="pageData.currentPage" :page-size="pageData.pageSize" :total="total" @changePage="onPage" />
                </div>
                <div v-else>
                    <sy-empty />
                </div>
            </div>
        </div>
        <el-dialog title="个人信息" center :visible.sync="dialogTableVisible" :lock-scroll="false"
            :close-on-click-modal="false">
            <div style="">
                <div class="dialogItem item">
                    <span>
                        昵称：{{ userInfoForm.nickname }}
                    </span>
                    <span>
                        简介：{{ userInfoForm.summary }}
                    </span>
                </div>
              <div class="item">
                    <span>
                        性别：
                      <span v-if="userInfoForm.gender === 1">男</span>
                      <span v-else-if="userInfoForm.gender === 0">女</span>
                      <span v-else>保密</span>
                    </span>
              </div>

                <div class=" item">
                    <span>
                        邮箱： {{ userInfoForm.email }}
                      <span v-if="!userInfoForm.isEmailActivated" >（未验证）</span>
                    </span>
                </div>
                <div class="dialogItem item">
                    <span>
                      注册时间：{{ timeFormat(userInfoForm.createTime) }}
                    </span>
                  <span>
                    最后登录：{{ timeFormat(userInfoForm.lastLoginTime) }}
                    </span>
                </div>
            </div>
        </el-dialog>

        <!-- 修改资料弹出框 -->
        <el-dialog title="修改资料" center :visible.sync="editDialogTableVisible" :lock-scroll="false"
            :close-on-click-modal="false">
            <el-form :rules="userInfoRules" label-position="left" label-width="70px" :model="userInfoForm">
                <el-form-item label="头像：">
                    <el-upload class="avatar-uploader" :show-file-list="false" ref="upload" name="filedatas"
                        action="" :http-request="uploadSectionFile" :before-upload="handleUploadBefore"
                        multiple>
                        <img style="width: 50%;height: 50%;" :src="loginUser.userInfo.avatar ? loginUser.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')" class="imgAvatar">
                    </el-upload>
                </el-form-item>
                <el-form-item label="昵称：" prop="nickname">
                    <el-input v-model="userInfoForm.nickname"></el-input>
                </el-form-item>
                <el-form-item label="简介：" prop="summary">
                    <el-input v-model="userInfoForm.summary"></el-input>
                </el-form-item>
                <el-form-item label="邮箱：" prop="email">
                    <el-input v-model="userInfoForm.email"></el-input>
                </el-form-item>
                <el-form-item label="验证码" lab prop="code">
                  <div style="display: flex;">
                    <el-col>
                      <el-input class="input" placeholder="请输入验证码" v-model="userInfoForm.code" autocomplete="off"></el-input>
                    </el-col>
                    <el-col>
                      <a v-if="showSendBtnFlag" class="send hand-style" @click="handleSendEmailCode">发送</a>
                      <a v-else class="send hand-style">{{ countdown }}s</a>
                    </el-col>
                  </div>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updateUserInfo">提交</el-button>
            </span>
        </el-dialog>

        <!-- 添加反馈弹出框 -->
        <el-dialog title="添加反馈" center :visible.sync="feedbackDialogTableVisible" :lock-scroll="false"
            :close-on-click-modal="false">
            <el-form label-position="left" label-width="100px" :model="feedbackForm" :rules="rules" ref="feedbackRuleForm">
                <el-form-item label="反馈类型:" prop="type">
                    <el-radio v-model="feedbackForm.type" label="0">需求</el-radio>
                    <el-radio v-model="feedbackForm.type" label="1">缺陷</el-radio>
                </el-form-item>
                <el-form-item label="反馈标题:" prop="title">
                    <el-input v-model="feedbackForm.title"></el-input>
                </el-form-item>
                <el-form-item label="详细内容：" prop="content">
                    <el-input v-model="feedbackForm.content"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="insertFeedback">提交</el-button>
            </span>
        </el-dialog>
    </div>
</template>
   
<script>
import { getBlogListByUserUid, getUserCount, getUserInfo, updateMyUserInfo } from "@/api/user";
import {deleteBlog} from "@/api/blog";
import { addFeedback } from "@/api/feedback";
import { parseTime } from "@/utils";
import { cancelCollect } from '@/api/collect'
import {avatarUpload, spaceBackgroundPictureUpload} from "@/api/fileStorage";
import {getWebSiteInfoValue} from "@/utils";
import {getUserUid} from "@/utils/cookieUtil";
import {getEmailCaptcha} from "@/api/auth";
export default {
    name: '',
    data() {
        return {
            user: {
              userInfo: {
                avatar: {
                  fileUrl: ''
                },
                spaceBackgroundPicture: {
                  fileUrl: ''
                }
              }
            },
            userUid: this.$route.query.userUid,
            loginUser: this.$store.state.user,
            dataList: [],
            total: 0,
            pageData: {
                currentPage: 1,
                pageSize: 10,
                isCollect: false,
                index: 0
            },
            statusList: {
              '1': {
                name: '发布',
                style: '#67C23A'
              },
              '3': {
                name: '置顶',
                style: '#903749'
              },
              '4': {
                name: '下架',
                style: '#F56C6C'
              },
              '5': {
                name: '待审核',
                style: '#909399'
              },
              '6': {
                name: '草稿',
                style: '#E6A23C'
              }
            },
          userInfoForm: {
            },
            feedbackForm: {
              userUid: getUserUid(),
              title: '',
              content: '',
              type: ''
            },
            dialogTableVisible: false,
            editDialogTableVisible: false,
            feedbackDialogTableVisible: false,
            tagStyle: ["success", "warning", "danger", "info"],
            btnList: [
                {
                    icon: "el-icon-document",
                    name: "文章"
                },
                {
                    icon: "el-icon-star-off",
                    name: "收藏"
                },
            ],
            today: new Date().getFullYear() + '-' + (new Date().getMonth() + 1 < 10 ? ('0' + (new Date().getMonth() + 1)) : new Date().getMonth() + 1) + '-'
                + (new Date().getDate() < 10 ? ('0' + new Date().getDate()) : new Date().getDate()),
            isTodaySign: false,
            showSendBtnFlag: true,
            countdown: 60, // 倒计时初始值为 60 秒
            userInfoRules: {
              nickname: [
                { required: true, message: '请输入昵称', trigger: 'blur' },
                { min: 1,max: 50, message: '昵称长度在1到50之间', trigger: 'blur' },
              ],
              summary: [
                { max: 100, message: '简介最长不超过100', trigger: 'blur' },
              ],
              email: [
                { required: true, message: '请输入邮箱', trigger: 'blur' },
                { pattern: /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/, message: '请输入合法的邮箱', trigger: 'blur' },
                { min: 1,max: 50, message: '邮箱长度在1到50之间', trigger: 'blur' },
              ],
              code: [
                { required: true, message: '请输入验证码', trigger: 'blur' },
              ]
            },
            rules: {
                title: [
                    { required: true, message: '请输入反馈标题', trigger: 'blur' },
                ],
                type: [
                    { required: true, message: '请选择反馈类型', trigger: 'blur' },
                ],
                content: [
                    { required: true, message: '请输入反馈详细内容', trigger: 'blur' },
                ],
            },
            count: {
                article: 0,
                collect: 0,
                followed: 0,
            }

        }
    },
    created() {
        this.userInfoForm = {
          avatarPictureUid: this.loginUser.userInfo.avatarPictureUid,
          birthday: this.loginUser.userInfo.birthday,
          createTime: this.loginUser.createTime,
          gender: this.loginUser.userInfo.gender,
          nickname: this.loginUser.userInfo.nickname,
          spaceBackgroundPictureUid: this.loginUser.userInfo.spaceBackgroundPictureUid,
          summary: this.loginUser.userInfo.summary,
          updateTime: this.loginUser.updateTime,
          email: this.loginUser.email,
          isEmailActivated: this.loginUser.isEmailActivated,
          lastLoginTime: this.loginUser.lastLoginTime
        }
        this.getUserInfo()
        this.selectAricleList()
        // this.validateTodayIsSign()
        this.getCount()
    },
    methods: {
        getUserInfo() {
          getUserInfo(this.userUid).then(res => {
            this.user = res.data
          })
        },
        getCount() {
            getUserCount(this.userUid).then(res => {
                let count = {
                    article: res.data.blogCount,
                    collect: res.data.collectCount,
                    // followed: res.extra.followedCount,
                }
                this.count = count
            })
        },
        getWebSiteInfoValue(key) {
          return getWebSiteInfoValue(this.$store.state.webSiteInfo, key)
        },
        insertFeedback() {
            this.$refs['feedbackRuleForm'].validate((valid) => {
                if (valid) {
                    addFeedback(this.feedbackForm).then(res => {

                        this.$toast.success('反馈成功')
                        this.feedbackDialogTableVisible = false
                        this.feedbackForm = {}
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        after() {
            this.$store.commit('setUser', this.loginUser)
        },
        /**
         * 获取邮箱验证码，即发送邮箱
         */
        handleSendEmailCode() {
          if (this.userInfoForm.email == null || this.userInfoForm.email === '') {

            this.$toast.error('请输入邮箱');
            return
          }
          getEmailCaptcha(this.userInfoForm.email).then(res => {
            this.timer = setInterval(() => {
              if (this.countdown > 0) {
                this.showSendBtnFlag = false
                this.countdown--;
              } else {
                this.showSendBtnFlag = true
                clearInterval(this.timer);
                this.timer = null;
                this.countdown = 60
              }
            }, 1000);

            this.$toast.success('验证码发送成功');
          })
        },
        updateUserInfo() {
            updateMyUserInfo(this.userInfoForm).then(res => {
                this.user = res.data
                this.loginUser = res.data

                this.$toast.success('修改成功')
                this.userInfoForm.code = ''
                this.after()
                this.editDialogTableVisible = false
            })
        },
        handleClike(id, path) {
            this.$router.push({ path: path, query: { uid: id } })
        },
        handleUpdateInfo() {
            this.handleBefore()
            this.editDialogTableVisible = true
        },
        handleClikeMoreData() {
            this.handleBefore()
            this.dialogTableVisible = true
        },
        handleBefore() {
            getUserInfo(this.userUid).then(res => {
                this.userInfoForm.nickname = res.data.userInfo.nickname
                this.userInfoForm.summary = res.data.userInfo.summary
                this.userInfoForm.email = res.data.email
                this.userInfoForm.createTime = res.data.createTime
                this.userInfoForm.lastLoginTime = res.data.lastLoginTime
            })
        },
        handleUpdateArticle(uid) {
            this.$router.push({ path: "/blogEdit", query: { uid: uid } })
        },
        handleEditArticle() {
          this.$router.push({ path: "/blogEdit" })
        },
        handleCanCollect(index, id) {
            this.$confirm('确认取消收藏该文章吗？', '提示', {
                lockScroll: false,
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(_ => {
                    cancelCollect(id).then(res => {
                        this.dataList.splice(index, 1)

                        this.$toast.success('取消收藏成功')
                    })
                })
                .catch(_ => {

                    this.$toast.info('取消关闭')
                });
        },
        handleDeleteArticle(index, uid) {
            this.$confirm('确认删除该文章吗？', '提示', {
                lockScroll: false,
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(_ => {
                    deleteBlog(uid).then(res => {
                        this.dataList.splice(index, 1)

                        this.$toast.success('删除成功')
                    })
                })
                .catch(_ => {
                    this.$toast.info('取消关闭')
                });
        },
        timeFormat(time) {
          if (time) {
            return parseTime(time).split(" ")[0]
          }
        },
        onPage() {
            this.pageData.currentPage++;
            this.before()
        },
        btnClike(index) {
            if (this.pageData.index == index) {
                return
            }
            for (var i = 0; i < this.$refs.btn.length; i++) {
                this.$refs.btn[i].className = ""
            }
            this.$refs.btn[index].className = "active"
            this.dataList = []
            this.pageData.currentPage = 1
            this.pageData.index = index
            this.before()
        },
        before() {
            switch (this.pageData.index) {
                case 0:
                    this.pageData.isCollect = false
                    break;
                case 1:
                    this.pageData.isCollect = true
                    break;
                default:
                    this.pageData.isCollect = false
                    break;
            }
          this.selectAricleList()
        },
        selectAricleList(type) {
            this.$bus.$emit('show');
            if (type) {
                this.pageData.type = type
            }
            getBlogListByUserUid(this.userUid, this.pageData).then(res => {
                this.dataList.push(...res.data);
                this.total = res.total
                this.pages = res.data.pages
                this.$bus.$emit('close');
            }).catch(err => {
                this.$bus.$emit('close');
            })
        },
        handleUploadBefore() {
            this.$bus.$emit('show');
        },
        uploadBjCoverFile: function (param) {

          let file = param.file
          // FormData 对象
          var formData = new FormData()
          // 文件对象
          formData.append('file', file)
          spaceBackgroundPictureUpload(formData).then(res => {
            this.userInfoForm.spaceBackgroundPictureUid = res.data.uid
            this.updateUserInfo()
            this.$bus.$emit('close')
          }).catch(err => {
            this.$bus.$emit('close')
          })

        },
        uploadSectionFile: function (param) {
            let file = param.file
            // FormData 对象
            var formData = new FormData()
            // 文件对象
            formData.append('file', file)
            avatarUpload(formData).then(res => {
              this.userInfoForm.avatarPictureUid = res.data.uid
              this.user.userInfo.avatar = res.data
              this.loginUser.userInfo.avatar = res.data
              this.loginUserAvatarUrl = res.data.fileUrl
              this.$bus.$emit('close')
            }).catch(err => {
              this.$bus.$emit('close')
            })

        },
    },
}
</script>
   
<style lang='scss' scoped>
/deep/ .el-dialog {
    .el-dialog__body {
        padding: 10px 20px;
    }

    .item {
        margin-bottom: 10px;
    }

    .dialogItem {
        display: flex;

        span {
            width: 50%;
        }
    }
}

.user-main {

    @media screen and (max-width: 1118px) {
        padding: 10px;

        /deep/ .el-dialog {

            .dialogItem {
                display: flex;

                span {
                    width: 50%;
                }
            }
        }

        .backgroupImg img {
            height: 200px;
        }

        .user-item .toolbar {
            margin-left: 20px;
            width: 100px;

        }

        .sign {
            display: none;
        }

        .user-warpper {
            width: 100%;
        }

        .article-cover {
            display: none;
        }
    }

    @media screen and (min-width: 1119px) {
        /deep/ .el-dialog {
            width: 30%;
            border-radius: 10px;

            .dialogItem {
                display: flex;

                span {
                    width: 50%;
                }
            }
        }

        .backgroupImg img {
            height: 400px;
        }

        .user-item .toolbar {
            margin-left: 50px;
            width: 150px;

        }

        .user-warpper {
            width: 60%;
        }

        .article-cover {
            width: 160px;
            height: 150px;
            overflow: hidden;
            border-radius: 5px;

            img {
                height: 100%;
                width: 100%;
                border-radius: 5px;
                transition: all 0.5s;
            }



            &:hover {
                img {
                    transform: scale(1.1);
                }
            }
        }

        .sign {
            position: absolute;
            right: 50px;
            padding: 5px;
            border-radius: 5px;

            .signBtn,
            .disabledSignBtn {
                border: none;

                color: var(--text-color);
                background-color: var(--background-color);
            }

            .signBtn {
                &:hover {
                    color: var(--theme-color);
                }
            }

            .disabledSignBtn {
                cursor: no-drop;

            }

            svg {
                width: 20px;
                height: 20px;
                vertical-align: -4px;
                margin-right: 5px;
            }
        }
    }

    .user-warpper {
        display: flex;
        flex-direction: column;
        margin-top: 80px;

    }

    .userBox {
        background-color: var(--background-color);

        .backgroupImg {
            position: relative;
            width: 100%;

            .top-btn {
                width: 100%;
                position: absolute;
                top: 0;
                display: flex;
                background: linear-gradient(90deg, rgba(220, 233, 242, 0.5), rgba(255, 255, 255, 0.5), rgba(220, 233, 242, 0.4));
                text-align: center;
            }

            img {
                width: 100%;
            }

            .more {
                position: absolute;
                right: 20px;
                bottom: 10px;

                font-size: 20px;
                background-color: #90939987;
                border-color: #909399;
                border-radius: 50%;
                width: 30px;
                height: 30px;
                text-align: center;
                color: #fff;

                i {
                    margin-top: 5px;
                }

                .menu {
                    background-color: var(--background-color);
                    color: var(--text-color);
                    padding: 10px 0;
                    border-radius: 5px;
                    font-size: 0.9rem;
                    text-align: center;
                    position: absolute;
                    right: -15px;
                    bottom: 30px;
                    display: none;
                    animation: fade-in 0.3s linear 1;
                    width: 120px;

                    @keyframes fade-in {
                        0% {
                            transform: scale(0);
                        }

                        100% {
                            transform: scale(1);
                        }
                    }

                    ul {
                        list-style: none;
                    }

                    li {
                        padding: 5px;
                        transition: transform .35s !important;

                        &:hover {
                            color: var(--theme-color);
                            transform: translateX(6px);
                        }

                        svg {
                            width: 20px;
                            height: 20px;
                            vertical-align: -5px;
                        }
                    }
                }

                &:hover {
                    background-color: #909399;

                    .menu {
                        display: block;
                    }
                }
            }
        }

        .user-item {
            height: 80px;
            display: flex;
            margin-bottom: 20px;
            position: relative;
            align-items: center;

            .toolbar {
                padding: 5px;
                background-color: var(--background-color);
                border-radius: 5px;
                margin-top: -80px;
            }

            .cover {
                object-fit: cover;
                width: 100%;
                height: 100%;
            }

            .userInfo {
                margin: 20px;
                color: var(--article-color);
                font-style: italic;

                .nickname {

                    margin-bottom: 10px;


                    svg {
                        width: 35px;
                        height: 35px;
                        vertical-align: -12px;
                    }

                    span {
                        margin-right: 5px;
                        font-weight: 700;
                        background: radial-gradient(circle at 49.86% 48.37%, #0090ff 0, #0089ff 3.33%, #3a82ff 6.67%, #717aff 10%, #9371fb 13.33%, #ae67ef 16.67%, #c45de1 20%, #d652d2 23.33%, #e448c2 26.67%, #ef3eb0 30%, #f7369e 33.33%, #fd318c 36.67%, #ff317a 40%, #ff3569 43.33%, #fd3d57 46.67%, #f94646 50%, #f35035 53.33%, #ea5a22 56.67%, #e16308 60%, #d56d00 63.33%, #c97500 66.67%, #bb7d00 70%, #ac8300 73.33%, #9d8900 76.67%, #8c8f00 80%, #7a9300 83.33%, #669700 86.67%, #4f9b00 90%, #309e0e 93.33%, #00a029 96.67%, #00a23d 100%);
                        -webkit-background-clip: text;
                        background-clip: text;
                        -webkit-text-fill-color: transparent;
                    }
                }

                .desc {

                    font-size: 0.9rem;
                }
            }


        }

    }


    .bottom-box {
        border-radius: 10px;
        margin-top: 20px;
        min-height: 500px;

        .title {
            padding: 10px;

            ul {
                display: flex;
                list-style: none;

                li {
                    margin-right: 20px;
                    color: var(--text-color);

                    &:hover {
                        color: var(--theme-color);
                    }

                    .item-title {
                        margin-right: 5px;
                    }
                }

                .active {
                    color: var(--theme-color);
                    font-weight: 700;
                    font-style: italic;
                }
            }

            border-bottom: 1px solid var(--border-line);
        }

        .btnBox {
            padding: 10px;

            .btn {
                display: inline-block;
                margin-right: 10px;
                background-color: rgba(136, 136, 136, .1);
                padding: 6px;
                border-radius: 5px;
                font-size: 0.9rem;
                color: var(--text-color);

                &:hover {
                    background-color: var(--theme-color);
                    color: #fff;
                }
            }
        }

        .articleBox {
            height: 100%;
            padding-top: 10px;

            .articleItem {
                display: grid;
                grid-template-columns: repeat(2, 1fr);

            }

            .article {
                display: flex;
                padding: 10px;
                margin-bottom: 20px;
                border-radius: 5px;
                margin-left: 10px;
                background-color: var(--background-color);
                overflow: hidden;

                .status {
                    padding: 2px 30px;
                    width: 80px;
                    position: absolute;
                    right: -50px;
                    top: 5px;
                    opacity: 0.8;
                    transform: rotate(45deg);
                    color: #fff;
                    text-align: center;
                }

                .articleBtn {
                    position: absolute;
                    right: 0;
                    display: none;
                }

                &:hover {
                    .articleBtn {
                        display: block;
                    }

                    background-color: #9093994a;
                }





                .article-info {
                    margin-left: 20px;
                    width: 70%;
                    position: relative;

                    a {
                        text-decoration: none;
                        color: var(--article-color);
                    }

                    .article-title {
                        font-size: 1.1rem;
                        font-weight: 600;
                        -webkit-line-clamp: 1;

                        &:hover {
                            color: var(--theme-color);
                        }
                    }

                    .article-title,
                    .article-desc {
                        display: -webkit-box;
                        -webkit-box-orient: vertical;

                        overflow: hidden;
                        text-overflow: ellipsis;
                    }

                    .article-desc {
                        -webkit-line-clamp: 3;
                        margin-bottom: 10px;
                        margin-top: 5px;
                        color: var(--text-color);
                        min-height: 60px;
                    }

                    .article-tag {
                        .el-tag {
                            margin-right: 5px;
                        }
                    }

                    .article-user {
                        display: flex;
                        align-items: center;
                        margin-top: 10px;

                        .nickname {
                            color: var(--theme-color);
                        }

                        .item {
                            padding: 0 5px;
                            color: var(--text-color);

                            i {
                                margin-right: 5px;
                            }
                        }
                    }
                }


            }
        }
    }
}
</style>