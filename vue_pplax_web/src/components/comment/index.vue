<template>
  <div class="comment-main">
    <div class="comment-item">
      <div class="box">
        <div class="avatar">
          <img
              v-if="user && user.userInfo.avatar"
              :src="user.userInfo.avatar.fileUrl"
              alt=""
          />
          <img v-else :src="getWebSiteInfoValue('touristAvatar')" alt="" />
        </div>
        <div class="ml-3">
          <div class="comment-input">
            <div
                id="textarea"
                ref="textareaRef"
                contenteditable="true"
                @input="onInput"
                @paste="optimizePasteEvent"
                data-placeholder="说点什么呢"
                class="comment-textarea hand-style"
            ></div>
          </div>
          <div class="comment-btn">
            <span
                @click.stop="chooseEmoji = !chooseEmoji"
                class="emoji-btn hand-style"
            >
              <i class="iconfont icon-biaoqing"></i>
            </span>
            <el-button
                @click="addComment"
                style=""
                class="upload-btn v-comment-btn hand-style"
            >
              提交
            </el-button>
          </div>
          <div class="emoji-wrapper" v-show="chooseEmoji">
            <Emoji @chooseEmoji="handleChooseEmoji" />
          </div>
        </div>
      </div>
    </div>

    <ul class="commentwrap">
      <div class="comment-wrp">
        <li
            class="ul-item"
            ref="commentBoxref"
            v-for="(item, index) in  commentList "
            :key="index"
        >
          <!-- 评论内容 -->
          <div
              class="comment"
              @mouseenter="replyEnter(item.uid, false)"
              @mouseleave="replyLeave(item.uid)"
          >
            <div class="main">
              <div class="profile">
                <el-avatar
                    :src="item.commentator.userInfo.avatar ? item.commentator.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')"
                    size="medium"
                ></el-avatar>
              </div>
              <div class="commentinfo">
                <section class="commeta">
                  <div class="left">
                    <h4 class="author">
                      <a target="_blank" class="disabled">
                        {{ item.commentator.userInfo.nickname }}
                      </a>
                    </h4>
                  </div>
                  <a
                      :ref="'replyBtn' + item.uid"
                      @click="replyComment(item, false)"
                      class="comment-reply-link hand-style"
                  >回复</a
                  >
                  <div class="right">
                    <div class="info">
                      <time
                          itemprop="datePublished"
                          datetime="1680523318635"
                          class="comment-time"
                      >发布于
                        {{ timeFormat(item.createTime) }}
                      </time>
                      <!--                                            <span class="useragent-info">（-->
                      <!--                                                <svg-icon :icon-class="item.browser" />-->
                      <!--                                                {{ item.browserVersion }} &nbsp;-->
                      <!--                                                <svg-icon :icon-class="item.system" />-->
                      <!--                                                {{ item.systemVersion }}-->
                      <!--                                                ）-->
                      <!--                                            </span>-->
                      <span class="ipAddress">
                        IP属地:{{ splitIpAddress(item.address) }}
                      </span>
                    </div>
                  </div>
                </section>
                <div class="body markdown-body">
                  <div class="markdown-content">
                    <p v-html="item.content"></p>
                  </div>
                </div>
                <!-- 回复框 -->
                <Reply :ref="'reply' + item.uid" @reloadReply="reloadReply">
                </Reply>
              </div>
            </div>
          </div>
          <ul class="children">
            <div class="comment-wrp">

              <children :children="item.children" :total="item.childrenTotal" :comment-uid="item.uid"></children>


            </div>
          </ul>
        </li>

        <!-- 分页按钮 -->
        <div>
          <sy-pagination
              :currentPage="pageData.currentPage"
              :total="total"
              :page-size="pageData.pageSize"
              @changePage="moreComment"
          />
        </div>
      </div>
    </ul>
  </div>
</template>
<script>
import { fetchBlogCommentList, postBlogComment } from '@/api/blog'
import {getWebSiteInfoValue, parseTime} from "@/utils/index";
import Reply from './Reply.vue'
import Children from "./Children";
import Emoji from '@/components/emoji'
import {getUserUid} from "@/utils/cookieUtil";
export default {
  components: {
    Reply,
    Emoji,
    Children
  },
  props: {
    blogUid: {
      type: String,
      default: "",
    }
  },
  data() {
    return {
      chooseEmoji: false,
      opacity: 0,
      show: null,
      user: this.$store.state.user,
      // 加载层信息
      pageData: {
        currentPage: 1,
        pageSize: 5,
      },
      commentList: [],
      total: 0,
      lastEditRange: null,
      lastCommentId: null,
    }
  },
  mounted() {
    this.getComments()
    document.addEventListener("click", e => {
      if (e.target.className != "el-radio-button__orig-radio" && e.target.className != "el-radio-button__inner"
          && e.target.className != "el-upload__input" && e.target.className != "el-icon-plus avatar-uploader-icon") {
        this.chooseEmoji = false
      }
    })
    this.$refs['textareaRef'].onclick = () => {
      // 获取选定对象
      let selection = window.getSelection()
      // 设置最后光标对象
      if (selection.rangeCount > 0) {
        // 记录光标最后点击可编辑div中所选择的位置
        this.lastEditRange = selection.getRangeAt(0);
      }
    }
  },
  methods: {
    /**
     * 获取评论
     */
    getComments() {
      fetchBlogCommentList(this.blogUid, this.pageData).then(res => {
        this.commentList = res.data
        this.total = res.total
      })
    },
    /**
     * 选择表情的事件
     * @param value
     */
    handleChooseEmoji(value) {
      // 创建一个img标签（表情）
      let img = document.createElement('img');
      img.src = value.url;
      img.style.verticalAlign = 'middle';
      img.style.marginLeft = "2px"
      img.style.marginRight = "2px"
      img.style.maxHeight = value.maxHeight;
      img.style.height = value.height
      img.style.width = value.width
      let edit = this.$refs['textareaRef']
      edit.focus()
      let selection = window.getSelection()
      // 如果存在最后的光标对象
      if (this.lastEditRange) {
        // 选区对象清除所有光标
        selection.removeAllRanges();
        // 并添加最后记录的光标，以还原之前的状态
        selection.addRange(this.lastEditRange);
        // 获取到最后选择的位置
        var range = selection.getRangeAt(0);
        // 在此位置插入表情图
        range.insertNode(img)
        // false，表示将Range对象所代表的区域的起点移动到终点处
        range.collapse(false)

        // 记录最后的位置
        this.lastEditRange = selection.getRangeAt(0);
      } else {
        // 将表情添加到可编辑的div中，作为可编辑div的子节点
        edit.appendChild(img)
        // 使用选取对象，选取可编辑div中的所有子节点
        selection.selectAllChildren(edit)
        // 合并到最后面，即实现了添加一个表情后，把光标移到最后面
        selection.collapseToEnd()
      }
      this.chooseEmoji = false
    },
    replyLeave(index, isChildren) {
      if (isChildren) {
        this.$refs[`childrenBtn${index}`][0].style.opacity = 0
      } else {
        this.$refs[`replyBtn${index}`][0].style.opacity = 0

      }
    },
    replyEnter(index, isChildren) {
      if (isChildren) {
        this.$refs[`childrenBtn${index}`][0].style.opacity = 1
      } else {
        this.$refs[`replyBtn${index}`][0].style.opacity = 1

      }
    },
    replyComment(item, isChildren) {
      if (this.user == null) {
        this.$store.state.loginFlag = true;
        return
      }
      if (this.$refs['replys' + this.lastCommentId] != null) {
        this.$refs['replys' + this.lastCommentId][0].showBox = false
      }
      if (this.$refs['reply' + this.lastCommentId] != null) {
        this.$refs['reply' + this.lastCommentId][0].showBox = false
      }

      if (isChildren) {
        this.$refs['replys' + item.uid][0].showBox = true
        //传值给回复框
        this.$refs['replys' + item.uid][0].userUid = getUserUid();
        this.$refs['replys' + item.uid][0].toUid = item.uid;
        this.$refs['replys' + item.uid][0].toUserUid = item.userUid;
        this.$refs['replys' + item.uid][0].originalUid = item.originalUid;
        this.$refs['replys' + item.uid][0].type = 4;
      } else {
        this.$refs['reply' + item.uid][0].showBox = true
        //传值给回复框
        this.$refs['reply' + item.uid][0].userUid = getUserUid();
        this.$refs['reply' + item.uid][0].toUserUid = item.userUid;
        this.$refs['reply' + item.uid][0].originalUid = item.uid;
        this.$refs['reply' + item.uid][0].type = 4;
      }
      this.lastCommentId = item.uid
    },

    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },

    /**
     * 获得网站配置value
     * @param key
     * @returns {*|string}
     */
    getWebSiteInfoValue(key) {
      return getWebSiteInfoValue(this.$store.state.webSiteInfo, key)
    },
    reloadReply(index) {
      let query = {
        currentPage: this.pageData.currentPage,
        pageSize: 5
      }

      fetchBlogCommentList(this.blogUid, query).then(res => {
        this.commentList = res.data
        this.total = res.total
      })
    },
    optimizePasteEvent(e) {
      // 监听粘贴内容到输入框事件，对内容进行处理 处理掉复制的样式标签，只拿取文本部分
      e.stopPropagation()
      e.preventDefault()
      let text = '', event = (e.originalEvent || e)
      if (event.clipboardData && event.clipboardData.getData) {
        text = event.clipboardData.getData('text/plain')
      } else if (window.clipboardData && window.clipboardData.getData) {
        text = window.clipboardData.getData('text')
      }
      if (document.queryCommandSupported('insertText')) {
        document.execCommand('insertText', false, text)
      } else {
        document.execCommand('paste', false, text)
      }
    },
    onInput(e) {
      let selection = window.getSelection()
      this.lastEditRange = selection.getRangeAt(0);
    },
    addComment() {
      if (this.user == null) {
        this.$store.commit("setLoginFlag", true)
        return
      }
      if (!this.$refs.textareaRef.innerHTML) {
        this.$toast.error('评论不能为空')
        return;
      }
      let comment = {
        content: this.$refs.textareaRef.innerHTML
      }
      postBlogComment(this.blogUid, comment).then(res => {
        this.pageData.currentPage = 1
        this.getComments()
        this.$toast.success('评论成功')
        this.$store.commit("isCommentFlag", true)
        this.$refs.textareaRef.innerHTML = ""
      })
    },
    moreComment(val) {
      this.$bus.$emit('show');
      this.pageData.currentPage = val;
      fetchBlogCommentList(this.blogUid, this.pageData).then(res => {
        this.commentList.push(...res.data)
        this.total = res.total
        this.$bus.$emit('close');
      })
    },

    splitIpAddress(address) {
      let arr = address.split("|")
      return arr[arr.length - 1]
    },
  },
}
</script>
<style lang="scss" scoped>
.comment-main {
  .comment-item {
    border: 1px solid rgba(144, 147, 153, 0.31);
    border-radius: 4px;
    padding: 10px;
    margin: 10px 0 10px;

    .box {
      display: flex;

      .avatar {
        line-height: normal;
        position: relative;
        vertical-align: middle;

        img {
          width: 40px;
          width: 40px;
          overflow: hidden;
          border-radius: 50%;
        }
      }

      .ml-3 {
        margin-left: 12px !important;
        width: 100%;
        position: relative;

        .comment-input {
          position: relative;

          .comment-textarea {
            font-size: 0.875rem;
            color: var(--article-color) !important;
            outline: none;
            padding: 10px 5px;
            min-height: 122px;
            resize: none;
            width: 100%;
            border-radius: 4px;
            border-style: none;
            background: url("https://img.shiyit.com/plun.gif") 100% 100%
            no-repeat;
            transition: all 0.25s ease-in-out 0s;

            &:focus {
              background-position-y: 150px;
            }

            &:empty:before {
              content: attr(data-placeholder);
              color: #666;
            }
          }
        }

        .comment-btn {
          display: flex;
          align-items: center;
          margin: 10px 0;

          .emoji-btn {
            i {
              font-size: 1.3rem;
            }
          }

          .v-comment-btn {
            border: 1px solid var(--border-line);
            border-radius: 4px;
            text-align: center;
            padding: 10px 16px;
            font-size: 14px;
            transition: all 0.3s;
            outline: none;
          }

          @media screen and (max-width: 767px) {
            .upload-btn {
              margin-left: auto;
              color: #fff;
              background-color: var(--theme-color);
              position: relative;
            }
          }

          @media screen and (min-width: 768px) {
            .upload-btn {
              margin-left: auto;
              color: #fff;
              background-color: var(--theme-color);
              overflow: hidden;
              transition: all 0.5s;
              position: relative;

              &:hover {
                background-color: #00c853;
              }

              &:hover::before {
                left: 120%;
                transition: all 0.5s;
              }

              &::before {
                content: "";
                position: absolute;
                top: 0;
                left: -40%;
                width: 20%;
                height: 100%;
                background: linear-gradient(
                        90deg,
                        transparent,
                        #fff,
                        transparent
                );
                transform: skew(-45deg);
              }
            }
          }
        }

        .emoji-wrapper {
          position: absolute;
          top: -62px;
        }
      }
    }
  }

  .commentwrap {
    width: 100%;
    padding: 0;
    list-style: none;

    .comment-wrp {
      padding: 10px 0 16px 0;

      .ul-item {
        clear: both;
        margin: 0;
        padding: 0;
        overflow: hidden;
        list-style: none;
        margin-bottom: 20px;
        border-bottom: 1px solid var(--border-line);

        &:last-child {
          border: 0;
          padding-bottom: 0;
        }

        .bozhu {
          display: inline-block;
          min-width: 30px;
          text-align: center;
          font-size: 12px;
          background: rgb(254, 44, 85);
          color: #fff;
          border-radius: 4px;
        }

        .left {
          .author {
            font-size: 24px;
            font-weight: 400;
            margin: 0;
            letter-spacing: 0px;
            text-transform: none;
            line-height: 20px;

            a {
              text-decoration: none;
              font-size: 14px;
              font-weight: 600;
              color: #82848a;
            }

            svg {
              width: 15px;
              height: 15px;
              vertical-align: -3px;
            }
          }
        }

        .comment {
          width: 100%;
          padding-top: 10px;
          float: left;

          .main {
            float: right;
            width: 100%;
            padding: 0;

            &:last-child {
              border-bottom: 0;
            }

            .profile {
              float: left;
              margin-top: 6px;

              a {
                text-decoration: none;
                color: var(--theme-color);

                img {
                  width: 100%;
                  max-width: 40px;
                  height: 40px;
                  border-radius: 100%;
                  box-shadow: 0 1px 10px -6px rgba(0, 0, 0, 0.5);
                  background-color: #f5f5f5;
                  transition: transform 0.75s;

                  &:hover {
                    transform: rotate(360deg);
                  }
                }
              }
            }

            .commentinfo {
              .commeta {
                font-size: 16px;
                margin-bottom: 5px;
                text-transform: uppercase;
                color: #9499a8;
                margin-left: 50px;

                section {
                  display: block;
                }

                .comment-reply-link {
                  text-decoration: none;
                  font-size: 12px;
                  margin-left: 10px;
                  float: right;
                  text-transform: uppercase;
                  color: #fff;
                  background-color: var(--theme-color);
                  line-height: 20px;
                  padding: 0 6px;
                  border-radius: 3px;
                  opacity: 0;
                }

                .right {
                  .info {
                    margin-top: 2px;
                    font-size: 12px;
                    letter-spacing: 0px;
                    text-transform: none;
                    color: rgba(0, 0, 0, 0.35);

                    .comment-time {
                      display: inline-block;
                      margin-top: 6px;
                      font-size: 12px;
                      color: #657786;
                    }

                    .useragent-info {
                      color: #657786;

                      svg {
                        vertical-align: -2px;
                        width: 13px;
                        height: 13px;
                      }
                    }

                    .ipAddress {
                      color: #657786;
                    }
                  }
                }
              }

              .body {
                color: #63686d;
                position: relative;
                margin-bottom: 15px;
                line-height: 1;
                white-space: pre-line;
                word-break: break-all;
                font-size: 14px !important;
                word-wrap: break-word;

                .markdown-content {
                  padding: 10px;
                  white-space: pre-line;
                  word-break: break-all;
                  background: var(--comment-box);
                  border-radius: 0 8px 8px;
                }
              }
            }
          }

          .showd {
            padding-left: 40px;
          }
        }

        .children {
          padding: 0;
          list-style: none;
          margin-left: 40px;

          .comment-wrp {
            padding: 10px 0 16px 0;

            li {
              clear: both;
              margin: 0;
              padding: 0;
              overflow: hidden;
              list-style: none;
              margin-bottom: 20px;
              border-bottom: 1px solid var(--border-line);

              &:last-child {
                border: 0;
                margin-bottom: 0;
              }

              .comment {
                width: 100%;
                padding-top: 10px;
                float: left;

                .main {
                  float: right;
                  width: 100%;
                  padding: 0;

                  &:last-child {
                    border-bottom: 0;
                  }

                  .profile {
                    float: left;
                    margin-top: 6px;

                    a {
                      text-decoration: none;

                      img {
                        width: 100%;
                        max-width: 40px;
                        height: 40px;
                        border-radius: 100%;
                        box-shadow: 0 1px 10px -6px rgba(0, 0, 0, 0.5);
                        background-color: #f5f5f5;
                        transition: transform 0.75s;

                        &:hover {
                          transform: rotate(360deg);
                        }
                      }
                    }
                  }

                  .commentinfo {
                    .commeta {
                      font-size: 16px;
                      margin-bottom: 5px;
                      text-transform: uppercase;
                      color: #9499a8;
                      margin-left: 50px;

                      section {
                        display: block;
                      }

                      .comment-reply-link {
                        text-decoration: none;
                        font-size: 12px;
                        display: block;
                        margin-left: 10px;
                        float: right;
                        text-transform: uppercase;
                        color: #fff;
                        background-color: var(--theme-color);
                        line-height: 20px;
                        padding: 0 6px;
                        border-radius: 3px;
                      }

                      .right {
                        .info {
                          margin-top: 2px;
                          font-size: 12px;
                          letter-spacing: 0px;
                          text-transform: none;
                          color: rgba(0, 0, 0, 0.35);

                          .comment-time {
                            display: inline-block;
                            margin-top: 6px;
                            font-size: 12px;
                            color: #657786;
                          }

                          .useragent-info {
                            color: #657786;

                            svg {
                              vertical-align: -2px;
                              width: 13px;
                              height: 13px;
                            }
                          }

                          .ipAddress {
                            color: #657786;
                          }
                        }
                      }
                    }

                    .body {
                      color: #63686d;
                      position: relative;
                      margin-bottom: 15px;
                      line-height: 1;
                      white-space: pre-line;
                      word-break: break-all;
                      font-size: 14px !important;
                      word-wrap: break-word;

                      .markdown-content {
                        padding: 10px;
                        white-space: pre-line;
                        word-break: break-all;
                        background: var(--comment-box);
                        border-radius: 0 8px 8px;
                      }
                    }
                  }
                }

                .showd {
                  padding-left: 40px;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
