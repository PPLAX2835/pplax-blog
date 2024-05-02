<template>
    <ul class="children">
      <div class="comment-wrp">
        <li
            class="ul-item"
            v-for="( childrenItem, childrenIndex ) in  children "
            :key="childrenIndex"
        >
          <!-- 评论内容 -->
          <div
              class="comment"
              @mouseenter="replyEnter(childrenItem.uid)"
              @mouseleave="replyLeave(childrenItem.uid)"
          >
            <div class="main">
              <div class="profile">
                <el-avatar
                    :src="childrenItem.commentator.userInfo.avatar ? childrenItem.commentator.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')"
                    size="medium"
                ></el-avatar>
              </div>
              <div class="commentinfo">
                <section class="commeta">
                  <div class="left">
                    <h4 class="author">
                      <a target="_blank" class="disabled">
                        {{ childrenItem.commentator.userInfo.nickname }}
                      </a>
                    </h4>
                  </div>
                  <a
                      href="javascript:;"
                      :ref="'childrenBtn' + childrenItem.uid"
                      @click="replyComment(childrenItem)"
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
                        {{ timeFormat(childrenItem.createTime) }}
                      </time>
                      <!--                                                        <span class="useragent-info">（-->
                      <!--                                                            <svg-icon :icon-class="childrenItem.browser" />-->
                      <!--                                                            {{ childrenItem.browserVersion }} &nbsp;-->
                      <!--                                                            <svg-icon :icon-class="childrenItem.system" />-->
                      <!--                                                            {{ childrenItem.systemVersion }}-->
                      <!--                                                            ）-->
                      <!--                                                        </span>-->
                      <span class="ipAddress">
                              IP属地:{{ splitIpAddress(childrenItem.address) }}
                            </span>
                    </div>
                  </div>
                </section>
                <div class="body markdown-body">
                  <div class="markdown-content">
                    <p>
                      <a
                          href="javascript:;"
                          style="color: #99ce00; text-decoration: none"
                      >@{{
                          childrenItem.commentator.userInfo.nickname }}
                      </a>

                      <span v-if="childrenItem.toUid">
                              回复
                              <a
                                  href="javascript:;"
                                  style="color: #99ce00; text-decoration: none"
                              >
                                @{{ childrenItem.targetUser.userInfo.nickname }}
                              </a>
                            </span>

                      <span v-html="childrenItem.content"></span>
                    </p>
                  </div>
                </div>
                <!-- 回复框 -->
                <Reply
                    :ref="'replys' + childrenItem.uid"
                    @reloadReply="reloadReply"
                >
                </Reply>
              </div>
            </div>
          </div>
        </li>

        <!-- 分页按钮 -->
        <div>

          <div class="page hand-style no-select" v-if="this.pageData.currentPage < Math.ceil((total / this.pageData.pageSize))" @click="handlePage">
            加载更多
          </div>

        </div>

      </div>
    </ul>
</template>
<script>
import {getWebSiteInfoValue, parseTime} from "@/utils";
import { commentReplyList } from "@/api/comment";
import {getUserUid} from "@/utils/cookieUtil";
import Reply from './Reply.vue'
import Emoji from '@/components/emoji'
export default {
  components: {
    Reply,
    Emoji
  },
  props: {
    children: {
      type: Array,
      default: [],
    },
    total: {
      type: Number,
      default: 0
    },
    commentUid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      pageData: {
        currentPage: 1,
        pageSize: 4
      },
      user: this.$store.state.user
    }
  },
  mounted() {

  },
  methods: {

    /**
     * 获得网站配置value
     * @param key
     * @returns {*|string}
     */
    getWebSiteInfoValue(key) {
      return getWebSiteInfoValue(this.$store.state.webSiteInfo, key)
    },


    /**
     * 回复评论
     * @param item
     */
    replyComment(item) {
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

      this.$refs['replys' + item.uid][0].showBox = true
      //传值给回复框
      this.$refs['replys' + item.uid][0].userUid = getUserUid();
      this.$refs['replys' + item.uid][0].toUid = item.uid;
      this.$refs['replys' + item.uid][0].toUserUid = item.userUid;
      this.$refs['replys' + item.uid][0].originalUid = item.originalUid;
      this.$refs['replys' + item.uid][0].type = 4;

      this.lastCommentId = item.uid
    },


    replyEnter(index) {
        this.$refs[`childrenBtn${index}`][0].style.opacity = 1
    },


    replyLeave(index) {
        this.$refs[`childrenBtn${index}`][0].style.opacity = 0
    },

    reloadReply() {
      this.pageData.currentPage = 1
      this.pageData.pageSize = 4
      commentReplyList(this.commentUid, this.pageData).then(resp => {
        this.children = resp.data
        this.total = resp.total
      })
    },

    handlePage() {
      this.pageData.currentPage++

      commentReplyList(this.commentUid, this.pageData).then(resp => {
        this.children.push(...resp.data)
        this.total = resp.total
      })
    },

    /**
     * 时间戳格式化
     */
    timeFormat(timestamp) {
      return parseTime(timestamp);
    },

    splitIpAddress(address) {
      let arr = address.split("|")
      return arr[arr.length - 1]
    },
  },
}
</script>
<style lang="scss" scoped>

.page {
  text-align: center;
  background-color: var(--pagination-background-color);
  width: 120px;
  height: 30px;
  line-height: 30px;
  border-radius: 50px;
  margin: 0 auto;
  margin-top: 20px;
  position: relative;
  white-space: nowrap;
  border: 1px solid var(--pagination-border-color);
  transition: all .3s;
  color: rgba(0, 0, 0, .65);

  &:hover {
    background-color: var(--pagination-hover-color)
  }

  &:active {
    transform: scale(0.7);
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
</style>
