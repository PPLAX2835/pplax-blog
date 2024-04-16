<template>
    <div class="article-container container">

        <!-- 左侧点赞和打赏 -->
        <div class="left-sidbarnav" :style="{ left: left }">
            <el-tooltip class="item" effect="dark" content="点赞" placement="left">
                <div class="left-item hand-style" title="点赞" @click="like(blog.uid)">
                    <el-badge :value="blog.likeCount" class="item">
                        <span>
                            <i v-if="blog.isLike" class="iconfont icon-dianzan4"></i>
                            <i v-else class="iconfont icon-dianzan1"></i>
                        </span>
                    </el-badge>
                </div>
            </el-tooltip>

            <el-tooltip class="item" effect="dark" content="收藏" placement="left">
                <div class="left-item hand-style" title="收藏" @click="handleCollect">
                    <el-badge :value="blog.collectCount" class="item">
                        <span style="font-size: 20px;">
                            <i v-if="blog.isCollect" class="el-icon-star-on"></i>
                            <i v-else class="el-icon-star-off"></i>
                        </span>
                    </el-badge>
                </div>
            </el-tooltip>

            <el-tooltip class="item" effect="dark" content="评论" placement="left">
                <div class="left-item hand-style" title="评论" @click="handleNodeClick('comment')">
                    <el-badge :value="blog.commentCount" class="item">
                        <span>
                            <i class="iconfont icon-pinglun"></i>
                        </span>
                    </el-badge>
                </div>
            </el-tooltip>

            <el-tooltip class="item" effect="dark" content="开启沉浸式阅读" placement="left">
                <div class="left-item hand-style" title="开启沉浸式阅读" @click="handleImmerse">
                    <span>
                        <i class="iconfont icon-full-screen"></i>
                    </span>
                </div>
            </el-tooltip>

        </div>

        <!-- 中间文章信息 -->
        <el-card class="article" id="articleBox">
            <el-tag @click="handleClick(blog.blogSort.uid, '/blogSort')" :class="blog.blogSort.icon" effect="dark" class="category hand-style">
                {{ blog.blogSort.sortName }}
            </el-tag>
            <h1 class="article-title">
                {{ blog.title }}
            </h1>
            <div class="article-desc">
                <div class="article-item">
                    <img v-lazy="blog.user.userInfo.avatar.fileUrl" :key="blog.user.userInfo.avatar.uid" alt="">
                    <div class="meta">
                        <div class="author">
                            <span class="link" href="#">{{ blog.user.userInfo.nickname }}</span>
                        </div>
                        <div class="item">
                            <span class="text textItem"> <i class="el-icon-time" style="color: rgb(236, 125, 98);"></i> {{
                                formatDate(blog.createTime)
                            }}</span>
                            <span class="text textItem"><i class="el-icon-chat-dot-round"
                                    style="color: rgb(211, 236, 98);"></i> {{ blog.commentCount }}
                                评论</span>
                            <span class="text textItem">
                                <i style="font-size: 0.7rem;color: rgb(38, 211, 153);" class="iconfont icon-dianzan1"></i>
                                {{ !blog.likeCount ? 0 : blog.likeCount }} 点赞
                            </span>
                            <span class="text"><i class="el-icon-view" style="color: rgb(190, 221, 16);"></i> {{
                                blog.quantity }} 阅读</span>

                        </div>
                    </div>
                </div>
                <time class="time">
                    {{ formatDate(blog.createTime, "MM/dd") }}
                </time>
            </div>
            <div class="tips">
                <i class="el-icon-message-solid"></i>
                <span style="color: orange;">温馨提示：</span>
                <div style="margin-left: 30px;margin-top: 5px;">
                    <span v-if="!blog.isOriginal">该文章为转载文章。</span>
                    本着开源共享、共同学习的精神，若内容或图片失效，请留言反馈。若有内容不小心影响到您的利益，请联系博主删除
                </div>
            </div>
            <!-- 文章内容 -->
            <div style="height: 100%;" class="box-article">
                <article class="content" :style="style" ref="blog" v-highlight v-html="this.blog.blogContent.content">
                </article>
            </div>

            <div class="read-duration">
                <div class="duration">
                    阅读时长，您已阅读：{{ hour }}时{{ minute }}分{{ second }}秒。
                </div>
            </div>

            <!-- 移动端点赞 -->
            <div class="dianzanBox">
                <div class="dianzan-item">
                    <div>
                        <span @click="like(blog.uid)">
                            <i v-if="blog.isLike" class="iconfont icon-dianzan4"></i>
                            <i v-else class="iconfont icon-dianzan1"></i>
                        </span>
                    </div>

                    <div v-if="blog.likeCount" class="likeCountItem">{{ blog.likeCount }}人已点赞</div>
                </div>
            </div>

            <!-- 文章标签和分享 -->
            <div class="tag-share">
                <div>
                    <a class="tagBtn hand-style" v-for=" item  in  blog.tagList " :key="item.uid"
                        @click="handleClick(item.uid, '/tags')">
                        <span type="success">
                            <i class="el-icon-collection-tag"></i> {{ item.name }}
                        </span>
                    </a>

                </div>
                <!-- 分享 -->
                <div class="social-share" id="social-share" @mouseleave="hiddenShareItmes" @mouseenter="showShareItmes">
                    <div class="share-item" id="share-item">
                        <a href="javascript:;" @click="qqShare" class="social-share-icon icon-qzone">
                            <i class="iconfont icon-qqkongjian"></i>
                        </a>
                        <a href="javascript:;" @click="qqHyShare" class="social-share-icon icon-qq">
                            <i class="iconfont icon-QQ"></i>
                        </a>
                        <a href="javascript:;" @click="weixinShare" class="social-share-icon icon-wechat">
                            <i class="iconfont icon-weixin"></i>
                        </a>
                        <a href="javascript:;" @click="weiboShare" class="social-share-icon icon-weibo">
                            <i class="iconfont icon-shejiaotubiao-06"></i>
                        </a>
                    </div>
                    <div style="width: 34px;display: inline-block;line-height: 34px; height: 34px;">
                        <i class="iconfont icon-fenxiang share hand-style"></i>
                    </div>


                </div>
            </div>
            <!-- 版权 -->
            <div class="copyright">
                <div class="copyrightItem">
                    <svg-icon icon-class="yuanchuang"></svg-icon>
                    <span class="text name">创作类型:</span>
                    <span class="text"> {{ blog.isOriginal ? '原创' : '转载' }}</span>
                </div>
                <div class="copyrightItem" v-if="blog.isOriginal">
                    <svg-icon icon-class="copyright"></svg-icon>
                    <span class="text name">版权归属:</span>
                    <span class="text"> {{ blog.user.userInfo.nickname }}</span>
                </div>
                <div class="copyrightItem" v-else>
                    <svg-icon icon-class="zzlink"></svg-icon>
                    <span class="text name">转载链接:</span>
                    <a :href="blog.originalUrl" class="text"> {{ blog.originalUrl }}</a>
                </div>
                <div class="copyrightItem">
                    <svg-icon icon-class="link"></svg-icon>
                    <span class="text name">本文链接:</span>
                    <a href="" class="text"> {{ locationUrl }}</a>
                </div>
                <div class="copyrightItem">
                    <svg-icon icon-class="xkxy"></svg-icon>
                    <span class="text name">许可协议:</span>
                    <span class="text">
                        本博客所有文章除特别声明外，均采用
                        <a class="text" href="https://creativecommons.org/licenses/by-nc-sa/4.0/">
                            CC BY-NC-SA 4.0
                        </a>
                        许可协议。转载请注明文章出处。
                    </span>
                </div>
                <svg-icon class="yuan" icon-class="yuan"></svg-icon>
            </div>

            <!-- 评论 -->
            <div class="comment-mian" id="comment">
                <div class="title">
                     <i class="iconfont icon-pinglun"></i>
                    <svg-icon icon-class="message"></svg-icon>
                    评论 <span style="color: var(--text-color);font-size: 0.8rem;">发表评论,来抢沙发</span>
                </div>
                <Comment :blogUid="blogUid" />
            </div>
        </el-card>
        <!-- 右边侧边栏 -->
        <div class="sidebar" v-if="rightShow">
            <div style="position: sticky;top:70px;">
                <div style="margin-top: 80px;">
                    <SiteInfo />
                </div>
                <div class="directory" @mouseenter="handleDireMousEnter" @mouseleave="handleDireMousLeave"
                    v-if="titles.length">
                    <el-card class="directory-item">
                        <div slot="header" class="title">
                            <i class="iconfont icon-menu_normal"></i> <span>文档目录</span>
                        </div>
                        <ul class="structureBox" style="">
                            <li ref="directoryItem"
                                :style="{ paddingLeft: item.level * 10 + 'px', filter: active !== index ? 'blur(1px)' : 'none' }"
                                :class="active == index ? 'structure active hand-style' : 'structure hand-style'"
                                v-for="( item, index ) in  titles " :key="index" @click="handleNodeClick(item.id)">
                                {{ item.title }}
                            </li>
                        </ul>
                    </el-card>
                </div>
            </div>
        </div>

        <image-preview :img="images"></image-preview>
    </div>
</template>
<script>
import { getBlog, blogLike } from "@/api/blog";
import { collect, cancelCollect } from '@/api/collect'
import { getToken } from "@/utils/cookieUtil";
import MarkdownIt from 'markdown-it'
import SiteInfo from '@/components/site/index.vue'
import Comment from '@/components/comment/index.vue'
import imagePreview from '@/components/imagePreview'
export default {
    components: {
        SiteInfo,
        Comment,
        imagePreview
    },
    metaInfo: {
    },
    data() {
        return {
            blog: {
              uid: this.$route.params.blogUid,
              blogContent: {
                content: '载入中'
              },
              blogSort: {
                sortName: '...',
                icon: ''
              },
              user: {
                userInfo: {
                  avatar: {
                    fileUrl: ''
                  }
                }
              }
            },
            rightShow: true,
            style: '',
            titles: [],
            images: {},
            active: 0,
            locationUrl: window.location.href,
            blogUid: this.$route.params.blogUid,
            user: this.$store.state.user,
            left: "0px",
            codes: [],
            timer: null,
            second: 0,
            minute: 0,
            hour: 0
        }
    },

    mounted() {
        setTimeout(function () {
          const element = document.getElementById("articleBox")
          this.left = (element.offsetLeft - 80) + "px"
        }, 10)

        // 监听滚动事件
        window.addEventListener('scroll', this.onScroll, false)
        this.timer = setInterval(() => {
            console.log(123)
            if (this.second === 60) {
                this.second = 0
                this.minute++
            } else {
                this.second++
            }
            if (this.minute === 60) {
                this.minute = 0
                this.hour++
            }
        }, 1000)
    },

    computed: {
    },
    watch: {
    },
    beforeDestroy() {
        clearInterval(this.timer);
        window.removeEventListener('scroll', this.onScroll);
    },

    created() {
        window.addEventListener('resize', () => {
            const element = document.getElementById("articleBox")
            if (element) {
                this.left = (element.offsetLeft - 80) + "px"
            }
        }, true)
        this.$bus.$emit('show');
      /**
       * 获取博客内容
       */
      getBlog(this.blogUid).then(res => {
            this.blog = res.data
            // 将博客内容从markdown转译为html
            const md = new MarkdownIt()
            this.blog.blogContent.content = md.render(this.blog.blogContent.content)    // markdown转html
            // 把被转义掉的video标签转义回来
            this.blog.blogContent.content = this.blog.blogContent.content.replace('&lt;video', '<video')
            this.blog.blogContent.content = this.blog.blogContent.content.replace('src=&quot;', 'src="')
            this.blog.blogContent.content = this.blog.blogContent.content.replace('&quot;&gt;&lt;/video&gt;', '"></video>')

            //处理目录和图片预览
            this.$nextTick(() => {
                //添加图片预览功能
                const imgList = this.$refs.blog.getElementsByTagName("img");
                let that = this
                for (var i = 0; i < imgList.length; i++) {
                    imgList[i].addEventListener("click", function (e) {
                        that.images = {
                            urls: e.target.currentSrc,
                            time: new Date().getTime()
                        }
                    });
                }
                // 添加文章生成目录功能
                let nodes = this.$refs.blog.querySelectorAll("h1,h2,h3,h4,h5,h6");
                for (let i = 0; i < nodes.length; i++) {
                    let node = nodes[i];
                    let reg = /^H[1-6]{1}$/;
                    if (reg.exec(node.tagName)) {
                        node.id = 'h' + parseInt(node.tagName.substring(1)) + i;
                    }
                    let toc = {
                        id: node.id,
                        title: node.innerText,
                        level: parseInt(node.tagName.substring(1)),
                    }
                    this.titles.push(toc);
                }
                //修改代码样式和添加复制按钮
                this.getCodes();
            })

            //修改标题和关键词
            document.title = this.blog.title
            if (this.blog.keywords != null) {
                document.querySelector('meta[name="keywords"]').setAttribute('content', this.blog.keywords)
            }

            this.$bus.$emit('close')
        }).catch(err => {
            this.$bus.$emit('close')
        })
    },

    methods: {
      /**
       * 目录相关事件
       */
      handleDireMousEnter() {
            for (let i = 0; i < this.$refs.directoryItem.length; i++) {
                this.$refs.directoryItem[i].style.filter = 'none'
            }
        },
        handleDireMousLeave() {
            for (let i = 0; i < this.$refs.directoryItem.length; i++) {
                if (this.active != i) {
                    this.$refs.directoryItem[i].style.filter = 'blur(1px)'
                }
            }
        },
        handleNodeClick(data, event) {
            //  实现跳转锚点
            document.getElementById(data).scrollIntoView({ behavior: 'smooth' })
        },

        getCodes() {
            this.codes = document.querySelectorAll("pre");
            if (this.codes.length > 0) {
                for (let i = 0; i < this.codes.length; i++) {
                    if (this.codes[i].offsetHeight != 0) {
                        return this.init();
                    } else {
                        for (let j = 0; j < this.codes.length; j++) {
                            if (this.codes[j].offsetHeight != 0) {
                                return this.init();
                            }
                        }
                        return;
                    }
                }
                return;
            }
        },
        init() {
            let that = this
            this.codes.forEach((item, index) => {
                let icon =
                    `<div class="mac-icon">` +
                    `<span class="mac-icon-red"></span>` +
                    `<span class="mac-icon-yellow"></span>` +
                    `<span class="mac-icon-green"></span>` +
                    `<i class=" el-icon-document-copy copy-button"></i>` +
                    `</div>`;
                item.insertAdjacentHTML("afterbegin", icon);
                let value = item.lastElementChild.innerText
                // 获取复制元素
                let copyButton =
                    item.firstElementChild.getElementsByClassName("copy-button")[0];
                copyButton.onclick = function () {
                    const clipboard = new that.Clipboard('.copy-button', {
                        text: () => value
                    })
                    clipboard.on('success', () => {
                        clipboard.destroy()
                    })
                    clipboard.on('error', () => {
                        that.$toast.error('复制失败')
                        clipboard.destroy()
                    })
                };

            });
        },
        qqShare() {
            const url = `https://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=${window.location.href}&sharesource=qzone&title=${this.blog.title}&summary=${this.blog.title}`
            window.open(url, 'renren-share', 'width=490,height=700');
        },
        qqHyShare() {
            const url = `http://connect.qq.com/widget/shareqq/index.html?url=${window.location.href}&sharesource=qzone&title=${this.blog.title}&summary=${this.blog.title}`
            window.open(url, 'renren-share', 'width=490,height=700');
        },
        weiboShare() {
            const url = `http://service.weibo.com/share/share.php?url=${window.location.href}&title=${this.blog.title}`;
            window.open(url, 'renren-share', 'width=490,height=700');
        },
        weixinShare() {
            const url = `https://api.pwmqr.com/qrcode/create/?url=${window.location.href}`;
            window.open(url, 'renren-share', 'width=490,height=700');
        },
        showShareItmes() {
            document.getElementById('share-item').style.display = 'inline-block'
            document.getElementById('social-share').style.backgroundColor = 'var(--article-share-color)'
        },
        hiddenShareItmes() {
            document.getElementById('share-item').style.display = 'none'
            document.getElementById('social-share').style.backgroundColor = 'unset'
        },
        checkAfter() {
            this.style = ''
        },
        onScroll() {
            const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
            for (let i = 0; i < this.titles.length; i++) {
                //获取小标题距离屏幕顶端的距离
                var offsetTop = document.getElementById(this.titles[i].id).offsetTop;
                //根据条件做出判断，我这里是当小标题和屏幕顶端的距离小于300且没有被标明在读时，就将其背景颜色改变。
                if ((offsetTop - scrollTop) < 80 && (offsetTop - scrollTop) > 0) {
                    this.active = i
                }
            }
        },
        handleImmerse() {
            this.rightShow = !this.rightShow
            window.setTimeout(() => {
                const element = document.getElementById("articleBox")
                this.left = (element.offsetLeft - 80) + "px"
            }, 10)


        },
        handleCollect() {
          if (getToken()) {
            let id = this.blog.uid;
            if (this.blog.isCollect) {
              cancelCollect(id).then(res => {
                this.blog.collectCount--
                this.blog.isCollect = 0

                this.$toast.success('取消收藏成功')

              })
            } else {
              collect(id).then(res => {
                this.blog.collectCount++
                this.blog.isCollect = 1

                this.$toast.success('收藏成功')
              })
            }
          } else {
            this.$store.commit("setLoginFlag", true);// 存储到vuex
          }

        },
        handleClick(uid, path) {
            this.$router.push({ path: path, query: { uid: uid } })
        },

        formatDate: function (value, args) {
            var dt = new Date(value);
            let year = dt.getFullYear();
            let month = (dt.getMonth() + 1).toString().padStart(2, '0');
            let date = dt.getDate().toString().padStart(2, '0');
            if (args === "MM/dd") {
                return `${month}/${date}`;
            }
            return `${year}-${month}-${date}`;
        },
        like(blogUid) {
          if (getToken()) {
            blogLike(blogUid).then(res => {
              if (this.blog.isLike) {
                this.blog.likeCount--;
                this.blog.isLike = false

                this.$toast.success('取消点赞')
              } else {
                this.blog.likeCount++;
                this.blog.isLike = true
                if (this.blog.readType == 2) {
                  this.checkAfter()
                }

                this.$toast.success('点赞成功')
              }

            }).catch(err => {

            })
          } else {
              this.$store.commit("setLoginFlag", true);// 存储到vuex
          }
        },

    },
}
</script>
<style lang="scss" scoped>
.article-container {
    padding: 10px;

    .read-duration {
        border-left: 5px solid #50bfff;
        background-color: var(--tips-backgroud-color);
        color: #bf5dd8;
        padding: 10px 0;
        margin-top: 15px;
        padding-left: 10px;
        border-top-right-radius: 5px;
        border-bottom-right-radius: 5px;
    }

    @media screen and (max-width: 1118px) {

        .article {
            width: 100%;

            .article-desc {
                .time {
                    display: none;
                }
            }

            .dianzanBox {
                text-align: center;
                margin-top: 15px;

                .dianzan-item {
                    i {
                        font-size: 1.5rem;
                    }

                    .likeCountItem {
                        color: var(--text-color);
                        font-size: 12px;
                    }

                    svg {
                        width: 30px;
                        height: 30px;
                        vertical-align: -10px;
                    }
                }
            }
        }

        .left-sidbarnav,
        .sidebar {
            display: none;
        }
    }

    @media screen and (min-width: 1119px) {

        /deep/ .el-dialog {
            width: 24%;
            border-radius: 10px;
        }

        .article {
            width: 50%;


            .dianzanBox {
                display: none;
            }

            .article-desc {
                .time {
                    font-size: 32px;
                    line-height: 42px;
                    color: orange;
                    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.25);
                    position: absolute;
                    right: 0;

                }
            }
        }

        .left-sidbarnav {
            position: fixed;
            top: 150px;
            z-index: 999;

            .left-item {
                margin-bottom: 20px;
                width: 50px;
                height: 50px;
                border-radius: 50%;
                text-align: center;
                line-height: 50px;
                background-color: var(--background-color);
                position: relative;
                color: var(--text-color);

                &:hover {
                    span {
                        color: var(--theme-color);
                    }

                    background-color: rgba(25, 153, 153, 0.2);
                }

                i {
                    font-size: 20px;
                }

                .el-icon-star-on {
                    font-size: 25px;
                }

                .like-count {
                    color: #fff;
                    display: inline-block;
                    width: 20px;
                    height: 20px;
                    border-radius: 50%;
                    position: absolute;
                    line-height: 20px;
                    background-color: var(--theme-color);
                    top: -8px;

                }

                .rewardItem {
                    position: absolute;
                    bottom: -150px;
                    left: 52px;
                    margin: 0;
                    padding: 0 0 15px;
                    background-color: #5956563e;
                    border-radius: 5px;
                    display: none;

                    .reward-img {
                        margin-left: 5px;
                        margin-right: 5px;
                        width: 275px;
                        height: 300px;
                        margin-top: 12px;
                    }
                }
            }

            .rewardMain {
                &:hover {
                    .rewardItem {
                        display: flex;
                        animation: left-in 1s ease;

                        @keyframes left-in {
                            0% {
                                transform: translateY(-50%);
                            }

                            100% {
                                transform: translateX(0);
                            }
                        }
                    }
                }
            }
        }

        .sidebar {
            margin-left: 20px;
            width: 20%;

            .directory {
                margin-top: 10px;

                .directory-item {
                    background-color: var(--background-color);


                    ul {
                        margin-top: 8px;
                        list-style: none;
                        padding: 0 10px;
                        max-height: 500px;
                        overflow: hidden;
                        overflow-y: scroll;

                        &::-webkit-scrollbar {
                            display: none;
                        }

                    }

                    /deep/ .el-card__header {
                        padding: 15px 20px;
                        border-bottom: 2px solid var(--border-line);
                    }

                    .title {
                        display: flex;

                        i {
                            font-size: 20px;
                            margin-right: 10px;
                        }

                        span {
                            color: var(--article-color);
                        }
                    }

                    .structure {
                        color: var(--article-color);
                        padding: 5px 0;
                        padding-left: 20px;
                        margin-bottom: 10px;
                        border-radius: 5px;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-box-orient: vertical;
                        -webkit-line-clamp: 2;
                        overflow: hidden;

                    }

                    .active {
                        font-size: 1.2rem;
                        transition: all .25s ease-in-out;
                    }

                    .active,
                    .structure:hover {
                        color: var(--theme-color);
                        background-color: var(--article-structure-backcolor);
                    }
                }
            }
        }
    }


    .article {
        background-color: var(--background-color);
        padding: 20px;
        height: 100%;
        margin-top: 80px;

        .category {
            border-radius: 3px;
            transition: transform .35s;
            height: 30px;
            line-height: 30px;

            &:hover {
                transform: translateY(-5px)
            }
        }

        .article-title {
            font-size: 24px;
            color: var(--article-color);
            text-align: center;
            padding-top: 20px;
            margin-bottom: 15px;
            word-break: break-word;
            text-shadow: 0 1px 2px rgba(0, 0, 0, 0.25);
            font-weight: 500;

            svg {
                width: 50px;
                height: 50px;
                vertical-align: -10px;
            }
        }

        .article-desc {
            height: 50px;
            border-bottom: 1px solid var(--border-line);
            margin-bottom: 15px;
            position: relative;
            display: flex;
            align-items: center;
            padding-bottom: 15px;

            &::after {
                content: '';
                position: absolute;
                bottom: -1.5px;
                left: 0;
                width: 80px;
                height: 3px;
                border-radius: 1.5px;
                background: var(--theme-color);
            }

            .article-item {
                display: flex;

                img {
                    width: 30px;
                    height: 30px;
                    border-radius: 50%;
                    margin-right: 10px;
                    padding: 3px;
                    -o-object-fit: cover;
                    object-fit: cover;
                    background: var(--border-line);
                    border: 1px solid #dcdfe6;
                }

                .meta {
                    display: flex;
                    flex-direction: column;
                    height: 35px;
                    justify-content: space-between;
                    font-size: 0.9rem;

                    .link {
                        font-weight: 500;
                        color: var(--theme-color);
                    }

                    .item {
                        color: #909399;
                        line-height: 16px;
                        display: flex;
                        align-items: center;

                        .textItem::after {
                            content: "/";
                            margin: 0 5px;
                        }
                    }
                }
            }



        }

        .tips {
            border-left: 5px solid #50bfff;
            background-color: var(--tips-backgroud-color);
            color: #888;
            border-radius: 5px;
            padding: 10px;

            i {
                color: #50bfff;
                margin-right: 5px;
            }
        }

        .box-article {
            .warpper {
                background: var(--article-srect-background);
                position: relative;
                height: 210px;
                padding: 5px;

                &::before {
                    content: "";
                    position: absolute;
                    top: -80px;
                    left: 0;
                    width: 100%;
                    height: 80px;
                    z-index: 2;
                    background: linear-gradient(180deg, rgba(55, 55, 55, 0), #ccc);
                }

                .item-title {
                    color: #fff;
                }

                .item-box {
                    border-radius: 10px;
                    background-color: var(--background-color);
                    height: 150px;
                    margin-left: 10px;
                    margin-right: 10px;
                    margin-top: 10px;
                    margin-bottom: 10px;
                    overflow: hidden;

                    span {
                        background: linear-gradient(135deg, #ff74cd 10%, #ec7d0b);
                        border-top-left-radius: 10px;
                        border-bottom-right-radius: 10px;
                        padding: 5px;
                        font-size: 0.9rem;
                        color: #fff;
                    }

                    .neirong {
                        text-align: center;
                        margin-top: 15px;
                        color: var(--text-color);
                        font-size: 0.9rem;
                    }

                    .btn {
                        margin: 0 auto;
                        display: block;
                        margin-top: 20px;
                    }
                }
            }
        }

        /deep/ .content {
            color: var(--article-color);
            line-height: 30px;
            margin-top: 10px;
            padding: 10px;

            .hljs-center {
                text-align: center;
            }

            blockquote {
                position: relative;
                padding: 0 10px;
                color: #6a737d;
                border-left: 0.25em solid #dfe2e5;
                margin: 20px 0;
            }

            h1,
            h2,
            h3,
            h4,
            h5,
            h6 {
                margin: 10px 0;
            }

            table {
                background-color: var(--article-table-back-color);

                td {
                    border: 1px solid var(--article-table-border-color);
                    padding: 5px;
                }

                tr {
                    background-color: var(--article-table-border-color);
                    transition: all .3s ease-in-out;

                    &:hover {
                        background-color: var(--article-table-back-color);
                        border: none;
                    }
                }

                th {
                    background-color: var(--article-table-back-color);
                }

            }

            p {
                a {
                    text-decoration: none;
                    color: #7bc549;

                    &::after {
                        content: '🔗';
                    }
                }
            }

            code {
                vertical-align: middle;
                word-break: break-word !important;
                border-radius: 2px !important;
                overflow-x: auto !important;
                background-color: #fff5f5;
                color: #ff502c;
                font-size: .87em !important;
                padding: 0.065em 0.4em !important;
            }

            ol {
                margin-left: 20px;
            }

            pre {
                opacity: 1 !important;
                margin: 10px 0;
                color: #f8f8f2 !important;
                overflow: hidden;
                box-shadow: rgba(0, 0, 0, 0.55) 0px 2px 10px;
                border-radius: 8px;

                code {
                    line-height: 20px !important;
                    font-size: 16px !important;
                    vertical-align: top;
                    padding: 10px !important;
                    border-bottom-left-radius: 8px !important;
                    border-bottom-right-radius: 8px !important;
                    background-color: #303133 !important;
                    color: #c0c4cc !important;
                    width: -webkit-fill-available;
                    display: inline-block;

                }

            }

            img {
                max-width: 100%;
                margin: 15px 0;
                border-radius: 5px !important;
                transition: box-shadow .35s, transform .35s;
                cursor: pointer;
                max-height: 500px;

                &:hover {
                    box-shadow: 5px 10px 5px rgba(0, 0, 0, 0.2);
                    transform: translateY(-10px)
                }
            }

            ul {
                margin-left: 20px;
            }

        }

        .tag-share {
            display: flex;
            margin-right: 5px;
            align-items: center;
            padding-bottom: 15px;
            margin-bottom: 20px;
            position: relative;
            border-bottom: 1px solid var(--border-line);
            margin-top: 20px;
            justify-content: space-between;

            .tagBtn {
                margin-right: 5px;
                background-color: var(--article-share-color);
                border: 1px solid var(--article-share-color);
                padding: 3px 5px;
                border-radius: 25px;
                display: inline-block;
                color: var(--text-color);

                i {
                    color: var(--theme-color);
                }
            }

            .social-share {
                position: relative;
                border-top-left-radius: 25px;
                border-bottom-left-radius: 25px;
                height: 100%;
                display: flex;
                align-items: center;

                &:hover .share {
                    transform: rotate(360deg);
                }

                .share-item {
                    text-align: left !important;
                    width: auto;
                    display: none;
                    margin-right: 15px;
                    transition: display .3s ease-in-out;

                }

                .share {
                    display: inline-block;
                    font-size: 18px;
                    width: 100%;
                    height: 100%;
                    font-weight: 700;
                    text-align: center;
                    color: #fff;
                    background: #99f;
                    border-radius: 50%;
                    transition: all .3s;

                }

                .social-share-icon {
                    margin-left: 10px;
                    display: inline-block;
                    width: 30px;
                    height: 30px;
                    font-size: 20px;
                    border-radius: 50%;
                    line-height: 30px;
                    border: 1px solid #666;
                    color: #666;
                    text-align: center;
                    vertical-align: middle;
                    text-decoration: none;

                    i {
                        font-size: 25px;
                    }
                }

                .icon-qzone {
                    border-color: #fdbe3d;
                    color: #fdbe3d;

                    &:hover {
                        background-color: #fdbe3d;

                        i {
                            color: white;
                        }
                    }
                }

                .icon-wechat {
                    color: #7bc549 !important;
                    border-color: #7bc549;

                    &:hover {
                        background-color: #7bc549;

                        i {
                            color: white;
                        }
                    }
                }

                .icon-weibo {
                    color: #ff763b !important;
                    border-color: #ff763b;

                    &:hover {
                        background-color: #ff763b;

                        i {
                            color: white;
                        }
                    }
                }

                .icon-qq {
                    color: #56b6e7 !important;
                    border-color: #56b6e7;

                    &:hover {
                        background-color: #56b6e7;

                        i {
                            color: white;
                        }
                    }
                }
            }


        }

        .wechatImg {
            img {
                width: 100%;
            }
        }

        .copyright {
            background-color: var(--text-color3);
            border-radius: 5px;
            width: 100%;
            min-height: 130px;
            position: relative;
            border: 1px dashed var(--theme-color);
            margin-top: 20px;

            &::before {
                content: "声明";
                background-image: linear-gradient(to right, #FFCC99, #FF99CC);
                width: 30%;
                padding: 5px;
                border-radius: 999px;
                position: absolute;
                top: -18px;
                left: 35%;
                text-align: center;
                border: 1px dashed var(--theme-color);
                font-weight: 700;
            }

            .copyrightItem {
                padding: 10px 10px;
                height: 100%;
                line-height: 26px;

                svg {
                    width: 18px;
                    height: 18px;
                    margin-right: 3px;
                    vertical-align: -4px;
                }

                .text {
                    color: #909399;
                    font-size: 14px;
                    margin-left: 8px;
                    text-decoration: none;
                }

                .name {
                    color: var(--theme-color);
                    font-weight: 700;
                }

                a:hover {
                    color: var(--theme-color);
                }
            }

            .yuan {
                width: 20px;
                height: 20px;
                position: absolute;
                top: 10px;
                right: 10px;
            }
        }





        .comment-mian {
            margin-top: 30px;

            .title {
                font-weight: 700;
                font-size: 20px;
                margin-top: 20px;
                color: var(--article-color);

                svg {
                    width: 20px;
                    height: 20px;
                }
            }
        }
    }


}


.wxImg {
    display: block;
    margin: 0 auto;
    width: 50%;
    height: 50%;
    margin-bottom: 15px;
}
</style>

<style>
.hljs {
    border-bottom-left-radius: 8px !important;
    border-bottom-right-radius: 8px !important;
}

.mac-icon {
    height: 15px !important;
    display: flex;
    align-items: center;
    border: 1px solid #272822 !important;
    background-color: #272822 !important;
    padding: 8px !important;
    border-top-left-radius: 8px !important;
    border-top-right-radius: 8px !important;

}

.mac-icon>span {
    display: inline-block !important;
    letter-spacing: 5px !important;
    word-spacing: 5px !important;
    width: 10px !important;
    height: 10px !important;
    border-radius: 8px !important;
}

.mac-icon-red {
    background-color: red !important;
}

.mac-icon-yellow {
    margin-left: 10px;
    background-color: yellow !important;
}

.mac-icon-green {
    margin-left: 10px;
    background-color: green !important;
}

.mac-icon-lang {
    width: 50px !important;
    padding-left: 10px !important;
    font-size: 16px !important;
    vertical-align: top !important;
}

.copy-button {
    border-radius: 3px;
    padding: 5px !important;
    color: #ffffff !important;
    font-size: 1rem;
    margin-left: auto;
}


.copy-button:hover {
    background-color: black !important;
}

.line-num-box {
    display: inline-block !important;
    color: #ccc !important;
    border-right: 2px solid #fff !important;
    line-height: 20px !important;
    font-size: 16px !important;
    text-align: right !important;
    padding-left: 10px !important;
    padding-right: 10px !important;
}
</style>