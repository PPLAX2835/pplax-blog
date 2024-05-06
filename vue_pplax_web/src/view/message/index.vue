<template>
  <div>
    <!-- banner -->
    <div class="message-banner" :style="cover">
      <!-- 弹幕输入框 -->
      <div class="message-container">
        <h1 class="message-title">留言板</h1>
        <div class="message-input-wrapper">
          <el-input
              class="input"
              v-model="content"
              placeholder="说点什么吧"
              @keyup.enter.native="addToList"
              @focus="show = true"
          ></el-input>
          <el-button
              style="opacity: 0.6"
              class="ml-3"
              round
              @click="addToList"
              v-show="show"
          >发送</el-button
          >
        </div>
      </div>
      <!-- 弹幕列表 -->
      <div class="barrage-container">
        <vue-baberrage :barrageList="barrageList">
          <template v-slot:default="slotProps">
            <span class="barrage-items">
              <img
                  :src="slotProps.item.avatar"
                  width="30"
                  height="30"
                  style="border-radius: 50%"
              />
              <span class="ml-2">{{ slotProps.item.nickname }} :</span>
              <span class="ml-2">{{ slotProps.item.content }}</span>
            </span>
          </template>
        </vue-baberrage>
      </div>
    </div>
  </div>
</template>

<script>
import { listLeaveMessage, addLeaveMessage } from "@/api/message";
import {getWebSiteInfoValue} from "@/utils";
export default {
  metaInfo: {
  },
  mounted() {
    document.title = "留言板";

    let that = this
    listLeaveMessage(that.paramData).then(res => {
      for (let i = 0; i < res.data.length; i++) {
        let item = res.data[i]
        let userInfo = {}
        if (item.userInfo) {
          userInfo = item.userInfo
          if (!userInfo.avatar) {
            userInfo.avatar = {
              fileUrl: that.getWebSiteInfoValue('touristAvatar')
            }
          }
        } else {
          userInfo = {
            nickname: '游客',
            avatar: {
              fileUrl: that.getWebSiteInfoValue('touristAvatar')
            }
          }
        }
        that.barrageList.push({
          nickname: userInfo.nickname,
          avatar: userInfo.avatar ? userInfo.avatar.fileUrl : that.getWebSiteInfoValue('touristAvatar'),      		//头像
          content: item.content,             	//弹幕消息
          time: Math.floor(Math.random() * (21 - 10) + 10),
          status: 1,
        });
      }
      that.total = res.total

      that.paramData.currentPage++
    });
  },
  data() {
    return {
      total: 0,
      paramData: {
        type: 0,
        currentPage: 1,
        pageSize: 100
      },
      show: false,
      img: process.env.VUE_APP_IMG_API,
      content: "",
      count: null,
      timer: null,
      barrageList: [],
      user: this.$store.state.user,
    };
  },
  methods: {
    addToList() {
      if (this.count) {

        this.$toast.error("30秒后才能再次留言");
        return false;
      }
      if (this.content.trim() === "") {

        this.$toast.error("留言不能为空");
        return false;
      }
      var message = {
        avatar: this.user ? (this.user.userInfo.avatar ? this.user.userInfo.avatar.fileUrl : this.getWebSiteInfoValue('touristAvatar'))  : this.getWebSiteInfoValue('touristAvatar'),
        status: 1,
        nickname: this.user ? this.user.userInfo.nickname : "游客",
        content: this.content,
        time: Math.floor(Math.random() * (21 - 10) + 10)
      };

      addLeaveMessage({
        content: this.content
      }
      ).then(res => {
        this.barrageList.push(message);

        this.content = "";
        this.$toast.success("留言成功");
      }).catch(err => {
        this.content = "";
      });
      const TIME_COUNT = 30;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.timer = setInterval(() => {
          if (this.count > 0 && this.count <= 30) {
            this.count--;
          } else {
            clearInterval(this.timer);
            this.timer = null;
          }
        }, 1000);
      }
    },
    getWebSiteInfoValue(key) {
      return getWebSiteInfoValue(this.$store.state.webSiteInfo, key)
    }
  },
  computed: {
    cover() {
      var cover = this.getWebSiteInfoValue('leaveMessagePageBackground');
      return "background: url(" + cover + ") center center / cover no-repeat";
    }
  }
};
</script>

<style lang="scss" scoped>
/deep/ .el-input__inner {
  border-radius: 50px;
  opacity: 0.6;
}

.message-banner {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #49b1f5;
  animation: header-effect 1s;

  .message-container {
    position: absolute;
    width: 360px;
    top: 35%;
    left: 0;
    right: 0;
    text-align: center;
    z-index: 5;
    margin: 0 auto;
    color: #fff;

    .message-title {
      color: #eee;
      animation: title-scale 1s;
    }

    .message-input-wrapper {
      display: flex;
      justify-content: center;
      height: 2.5rem;
      margin-top: 2rem;

      .ml-3 {
        animation: left-in 1s ease;

        @keyframes left-in {
          0% {
            transform: translateY(-500%);
          }

          100% {
            transform: translateX(0);
          }
        }
      }
    }
  }

  .barrage-container {
    position: absolute;
    top: 80px;
    left: 0;
    right: 0;
    bottom: 0;
    width: 100%;

    .barrage-items {
      background: #000;
      border-radius: 100px;
      color: #fff;
      padding: 5px 10px 5px 5px;
      align-items: center;
      display: flex;
      margin-top: 10px;
    }
  }
}
</style>
