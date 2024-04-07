<template>
    <div class='emoji-container'>
        <div class="emojiBox" v-if="type == 0">
            <span class="emoji-item hand-style" v-for="(item, index) of emojiList" :key="index"
                @click="chooseEmoji(item.url, 0)">
                <el-tooltip class="item" effect="dark" :content="item.name" placement="top">
                    <img :src="item.url" class="emoji" :title="item.name" />
                </el-tooltip>
            </span>
        </div>

        <div class="emojiBox" v-if="type == 1">
            <span class="emoji-item hand-style" v-for="(item, index) of heoList" :key="index"
                @click="chooseEmoji(item.url, 1)">
                <el-tooltip class="item" effect="dark" :content="item.name" placement="top">
                    <img :src="item.url" class="heoImg" :title="item.name" />
                </el-tooltip>
            </span>
        </div>


        <div class="btnBox">
            <el-radio-group v-model="type" @input="handleChage">
                <el-radio-button :label="0">表情</el-radio-button>
                <el-radio-button :label="1">heo</el-radio-button>
            </el-radio-group>
        </div>
    </div>
</template>
   
<script>
import { upload } from '@/api'
export default {
    name: '',
    data() {
        return {
            uploadPictureHost: process.env.VUE_APP_BASE_API + "/file/upload",
            emojiList: [],
            heoList: [],
            type: 0,
            myEmojiList: [],
            files: [],
            visible: false,
            left: 0,
            top: 0,
            emoji: {}
        }
    },

    created() {
        this.emojiList = require('@/assets/emoji.json');
    },
    methods: {
        handleStick() {
            stickEmoji(this.emoji.id).then(res => {
                this.$toast.success('置顶成功');
                this.visible = false
                this.getEmojiList()
            })
        },
        handleDelete() {
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                deleteEmoji(this.emoji.id).then(res => {

                    this.$toast.success('删除成功');
                    this.visible = false
                    this.getEmojiList()
                })
            }).catch(() => {
                this.$toast.info("已取消删除");
            });
        },
        handleChage(value) {
            this.visible = false
            if (value == 2) {
                this.getEmojiList()
                this.$nextTick(() => {
                    document.getElementById("collectEmoji").oncontextmenu = new Function("event.returnValue=false");
                })
            }
            if (value == 1) {
                this.heoList = require('@/assets/heo.json');
            }
        },
        getEmojiList() {
            getEmojiList().then(res => {
                this.myEmojiList = res.data
            })
        },
        handleTabClick(index) {
            let btnList = document.getElementsByClassName("itemBtn")
            for (var i = 0; i < btnList.length; i++) {
                btnList[i].className = "itemBtn"
                if (i == index) {
                    btnList[i].className = "itemBtn active"
                }
            }
        },
        chooseEmoji(url, type) {
            let emoji = {
                url: url,
                type: type
            }

            if (type == 1) {
                emoji.width = "50px";
                emoji.height = "100%"
            }
            if (type == 2) {
                emoji.maxHeight = "100px";
                emoji.height = "100%"
            }
            if (type == 0) {
                emoji.width = "25px"
                emoji.height = "25px"
            }

            this.$emit('chooseEmoji', emoji);
        },
        //右击
        openMenu(e, item) {
            var x = e.x; //这个应该是相对于整个浏览器页面的x坐标，左上角为坐标原点（0,0）
            var y = e.y; //这个应该是相对于整个浏览器页面的y坐标，左上角为坐标原点（0,0）
            this.top = y;
            this.left = x;
            this.visible = true; //显示菜单
            this.emoji = item
        },
        uploadSectionFile: function (param) {
            this.files = param.file
            // FormData 对象
            var formData = new FormData()
            // 文件对象
            formData.append('multipartFile', this.files)
            upload(formData).then(res => {
                let emoji = {
                    url: res.data
                }
                addEmoji(emoji).then(res => {
                    this.getEmojiList()
                })
            })

        },
    },
}
</script>
   
<style lang='scss' scoped>
/deep/ .el-radio-button__inner {
    padding: 8px 15px !important;
}

.emoji-container {
    max-width: 400px;
    background-color: var(--background-color);
    border: 1px solid var(--border-line);
    padding: 5px;
    border-radius: 5px;

    .emojiBox {
        min-height: 150px;
        max-height: 150px;
        max-width: 400px;
        overflow-y: scroll;

        &::-webkit-scrollbar {
            display: none;
        }

        .emoji-item {
            display: inline-block;

            .emoji {
                padding: 3px;
                border-radius: 5px;
                width: 30px;
                height: 30px;

                &:hover {
                    background-color: rgb(221, 221, 221)
                }
            }

            .heoImg {
                width: 40px;
                height: 40px;
                transition: all 0.35s;
                margin-left: 5px;

                &:hover {
                    transform: scale(1.2);
                }
            }
        }
    }

    .btnBox {
        margin-top: 10px;
        padding-top: 5px;
        border-top: 2px solid var(--border-line);
        color: var(--text-color);
    }


}
</style>