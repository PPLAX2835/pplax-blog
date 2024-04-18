<template>
    <el-dialog title="搜索文章" :visible.sync="dialogVisible" :close-on-click-modal="false">
        <div>
            <el-input v-model="pageData.keyword" @input="inputChage" placeholder="请输入要搜索的关键词"></el-input>
        </div>

        <div class="dividerBox">
            <div class="divider">
                <svg-icon icon-class="car"></svg-icon>
            </div>
        </div>

        <div class="search-article" :key=refKey>
            <div class="item" v-for="(item, index) in list" :key="index">
                <router-link :to="'/blog/' + item.uid">
                    <a class="xiahuaxian article-title" v-html="item.title"></a>
                </router-link>
                <p v-html="item.summary"></p>
            </div>
            <!-- 分页按钮 -->
            <sy-pagination v-show="list.length" :currentPage="pageData.currentPage" :page-size="pageData.pageSize" :total="total" @changePage="handlePage" />
        </div>
    </el-dialog>
</template>
   
<script>
import {searchBlog} from "@/api/blog";
export default {
    name: '',
    data() {
        return {
            total: 0  ,
            pageData: {
                currentPage: 1,
                pageSize: 10,
                keyword: "",
            },
            pages: 0,
            list: [],
            tagList: [],
            refKey: 0
        }
    },
    beforeDestroy() {
        this.$store.state.searchDialogVisible = false
    },
    computed: {
        dialogVisible: {
            set(value) {
                this.$store.state.searchDialogVisible = value;
            },
            get() {
                this.pageData.keyword = ""
                this.list = []
                return this.$store.state.searchDialogVisible;
            }
        },
    },
    created() {

    },
    methods: {
        inputChage(event) {
            this.pageData.keyword = event.replace(/\s/g, '')
            if (this.pageData.keyword == "") {
                this.list = []
                return
            }
            this.refKey = new Date().getTime()
            this.pageData.currentPage = 1
            searchBlog(this.pageData).then(res => {
                this.list = res.data;
                this.total = res.total
            })
        },
        // 分页
        handlePage(val) {
            this.pageData.currentPage = val
            this.fetchArticleList()
        },
        fetchArticleList() {
          searchBlog(this.pageData).then(res => {
                this.list.push(...res.data);
            this.total = res.total
            })
        },
    },
}
</script>
   
<style lang='scss' scoped>
/deep/ .el-dialog {
    border-radius: 5px;

    @media screen and (min-width: 1119px) {
        width: 30%;
    }
}

.dividerBox {
    margin-top: 15px;
    margin-bottom: 20px;

    .divider {
        position: relative;
        border: 2px dashed #d2ebfd;

        &:hover svg {
            left: 90%;
        }

        svg {
            width: 25px;
            height: 25px;
            position: absolute;
            top: -15px;
            left: 5%;
            transition: all 1s ease-in-out;

            &:hover {
                left: 90%;
            }
        }
    }


}


.tagBox {
    margin-top: 15px;

    .tag-title {
        margin-bottom: 10px;
        position: relative;
        padding-left: 10px;

        &::before {
            content: "";
            width: 5px;
            height: 100%;
            background-color: #67c23a;
            position: absolute;
            left: 0;
            bottom: 0;
        }
    }

}

.search-article {
    margin-top: 20px;
    max-height: 400px;
    /* 垂直滚动 */
    overflow-y: scroll;

    .item {
        margin-bottom: 20px;

        a {
            text-decoration: none;
            color: var(--article-color);
        }
    }

    .article-title {
        font-size: 1.2rem;
        font-weight: 700;
        margin-bottom: 10px;
        display: inline-block;
        position: relative;
        padding-left: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        max-width: 90%;

        &::before {
            content: "";
            width: 5px;
            height: 5px;
            border: 5px solid var(--theme-color);
            position: absolute;
            border-radius: 50%;
            left: 0;
            bottom: 3px;
        }

        &:hover {
            color: var(--theme-color);
        }
    }

    p {
        padding-left: 20px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
    }
}
</style>