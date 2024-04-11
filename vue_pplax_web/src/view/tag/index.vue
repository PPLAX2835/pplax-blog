<template>
    <div class='container'>
        <div class="tag-wapper">
            <div class="left">
                <div class="tag-box">
                    <div class="tag-title">
                        <svg-icon icon-class="tag"></svg-icon> 标签 ({{ tagList.length }})
                    </div>
                    <div class="tag-list">
                        <a ref="tag" :style="{ fontSize: tag.font }"
                            :class="handleChoose(tag, index) ? 'tag-option hand-style active' : 'tag-option hand-style'"
                            @click="handleClick(tag.uid, index)" v-for="(tag, index) in tagList" :key="index">
                            {{ tag.name }} <span class="num">{{ tag.cites }}</span>
                        </a>

                        <!-- 分页按钮 -->
                        <div>
                          <sy-pagination :current-page="tagPageData.currentPage" :page-size="tagPageData.pageSize" :total="tagTotal" @changePage="handleTagPage" />
                        </div>
                    </div>
                </div>

                <div class="article-list" v-if="blogList.length">
                    <div class="article-item  box-shadow-top" v-for="(article, index) in blogList" :key="index">
                        <div class="article-cover">
                          <img v-lazy="article.coverImage === undefined ? '' : article.coverImage.fileUrl" :key="article.uid" alt="">

                        </div>
                        <div class="article-right">
                            <router-link :to="'/blog/' + article.uid">
                              <h4>{{ article.title }}</h4>
                            </router-link>
                            <div class="article-meta">
                                <el-avatar class="userAvatar" :src="article.user.userInfo.avatar.fileUrl"></el-avatar>
                                <el-tooltip class="item" effect="dark" content="文章分类" placement="top">
                                    <el-tag size="mini" class="hand-style"
                                        @click="handleNatigation(article.blogSortUid, '/blogSort')">
                                        <i class=" el-icon-folder-opened"></i> {{ article.blogSort.sortName }}
                                    </el-tag>
                                </el-tooltip>
                                <i class="el-icon-time"></i> 2024-01-18
                            </div>
                        </div>
                    </div>
                    <!-- 分页按钮 -->
                    <div>
                        <sy-pagination :current-page="pageData.currentPage" :total="total" :page-size="pageData.pageSize" @changePage="handlePage" />
                    </div>
                </div>
                <sy-empty v-else message="此标签下暂无发布文章" />
            </div>
            <div class="right">
                <SiteInfo style="position: sticky;top:60px;" />

            </div>
        </div>
    </div>
</template>
   
<script>
import SiteInfo from '@/components/site/index.vue'
import { fetchBlogList, fetchTagList } from '@/api'
export default {
    name: 'tag',
    components: {
        SiteInfo
    },
    data() {
        return {
            tagList: [],
            blogList: [],
            lastIndex: null,
            tagTotal: 0,
            tagPageData: {
              currentPage: 1,
              pageSize: 20
            },
            total: 0,
            pageData: {
                currentPage: 1,
                pageSize: 8,
                tagUid: this.$route.query.uid,
            }
        }
    },
    created() {
        this.getTagList()
        if (this.pageData.tagUid != null) {
            this.fetchBlogList()
        }
    },
    methods: {
        handleNatigation(id, path) {
            this.$router.push({ path: path, query: { id: id } })
        },
        handleChoose(item) {
            return item.uid === this.pageData.tagUid;
        },
        handleClick(id, index) {
            if (index === this.lastIndex) {
                return
            }

            this.$refs.tag[index].className = 'tag-option hand-style active'
            if (this.lastIndex != null) {
                this.$refs.tag[this.lastIndex].className = 'tag-option hand-style '
            }
            this.lastIndex = index
            this.pageData.currentPage = 1
            this.pageData.tagUid = id
            this.blogList = []
            this.fetchBlogList()
        },
        handleTagPage(val) {
          this.tagPageData.currentPage = val;
          this.getTagList()
        },
        // 分页
        handlePage(val) {
            this.pageData.currentPage = val;
            this.fetchBlogList()
        },
        getTagList() {
            fetchTagList(this.tagPageData).then(res => {
                this.tagList.push(...res.data)
                this.tagTotal = res.total
                for (var i = 0; i < this.tagList.length; i++) {
                    this.tagList[i].font = Math.floor(Math.random() * 10) + 10 + "px"
                }
            })
        },
        fetchBlogList() {
            this.$bus.$emit('show');
            fetchBlogList(this.pageData).then(res => {
                this.blogList.push(...res.data)
                this.total = res.total
                this.$bus.$emit('close');
            }).catch(err => {
                this.$bus.$emit('close');
            })
        },

    },
}
</script>
   
<style lang='scss' scoped>
.tag-wapper {
    margin-top: 80px;

    display: flex;
    justify-content: space-between;

    @media screen and (max-width: 1118px) {
        width: 100%;

        .left {
            width: 100%;

            .article-cover {
                display: none;
            }
        }

        .right {
            display: none;
        }
    }

    @media screen and (min-width: 1119px) {
        width: 65%;

        .left {
            width: 70%;

            .article-cover {
                width: 150px;
                height: 80px;
            }
        }

        .right {
            width: 30%;
        }
    }

    .left {
        color: var(--text-color);
        margin-right: 15px;

        .tag-box {
            background-color: var(--background-color);
            padding: 15px;
            border-radius: 5px;

            .tag-title {
                font-size: 1.2rem;
                margin-bottom: 20px;
                margin-top: 10px;

                svg {
                    width: 20px;
                    height: 20px;
                    vertical-align: -5px;
                }
            }

            .tag-list {

                .tag-option {
                    display: inline-block;
                    border: 1px solid var(--border-line);
                    border-radius: 5px;
                    margin-right: 10px;
                    padding: 5px;
                    margin-bottom: 10px;

                    &:hover {
                        background-color: rgb(171, 214, 214);
                    }

                    .num {
                        display: inline-block;
                        border-radius: 50%;
                        width: 20px;
                        height: 20px;
                        background-color: #66b1ff;
                        text-align: center;
                        line-height: 20px;
                        color: white;
                        font-size: 12px;
                    }
                }

                .active {
                    background-color: rgb(171, 214, 214);
                }
            }
        }

        .article-list {
            margin-top: 20px;

            .article-item {
                padding: 10px;
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                background-color: var(--background-color);

                &:last-child {
                    border-bottom: none;
                }




                .article-cover {
                    border-radius: 5px;

                    img {
                        width: 100%;
                        height: 100%;
                        border-radius: 5px;
                    }
                }

                .article-right {
                    display: flex;
                    flex-direction: column;
                    margin-left: 10px;

                    a {
                        text-decoration: none;
                        color: var(--text-color);
                        overflow: hidden;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-box-orient: vertical;
                        -webkit-line-clamp: 1;

                        &:hover {
                            color: var(--theme-color);
                        }
                    }
                }

                .article-meta {
                    margin-top: 15px;
                    display: flex;
                    align-items: center;

                    .userAvatar {
                        margin-right: 10px;
                    }

                    i {
                        margin: 0 5px;
                    }
                }
            }
        }

    }

}
</style>