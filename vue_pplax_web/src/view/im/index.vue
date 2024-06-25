<template>
  <div class="im-main container">
    <div id="im" class="im-warpper">
      <el-card class="itemBox">
        <!-- 标题 -->
        <div class="title">{{ title }}</div>
        <div class="messageBox" id="messageBox" ref="messageContainer">
          <!-- 加载更多 -->
          <div
              class="more noSelect hand-style"
              v-show="pageData.currentPage < totalPage"
          >
            <div v-if="isLoding" class="loading">
              <div class="spinner"></div>
            </div>
            <div @click="handleMore" v-else>加载更多</div>
          </div>
          <!-- 消息内容框 -->
          <div
              class="messageItem"
              v-for="(item, index) in  messageList "
              :key="index"
          >
            <!-- 左边消息框 别人发送的消息 -->
            <div
                :class="item.status === statusList.WITHDRAW ? 'withdraw' : 'left'"
                v-if="user && item.userUid !== user.uid"
            >
              <a v-if="item.status !== statusList.WITHDRAW" :href="'/user?userUid=' + item.userUid">
                <el-avatar
                    class="noSelect"
                    :src="item.userInfo !== undefined ? (item.userInfo.avatar !== undefined ? item.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')) : ''"
                    :key="item.userUid"
                />
              </a>
              <div class="info">
                <div class="nickname noSelect userInfo">
                  {{ item.userInfo ? item.userInfo.nickname : '' }}
                  <span v-if="item.ip" class="item">
                    <i class="el-icon-location-information"></i>
                    IP属地:{{ splitIpAddress(item.address) }}
                  </span>
                  <span class="item">
                    <i class="el-icon-time"></i>
                    {{ timeFormat(item.createTime)
                    }}</span
                  >
                </div>

                <span
                    v-if="item.status !== statusList.WITHDRAW"
                    v-html="item.content"
                    class="messageContent"
                    @contextmenu.prevent="openRightClickMenu($event, item, index)"
                >
                </span>
                <span class="noSelect" v-else style="color: var(--text-color)">
                  "
                  {{ item.userInfo !== undefined ? item.userInfo.nickname : '' }}
                  " 撤回了一条消息
                </span>
              </div>
            </div>
            <!-- 右边消息框 自己发送的消息 -->
            <div
                :class="item.status === statusList.WITHDRAW ? 'withdraw' : 'right'"
                v-else
            >
              <div class="info">
                <div>
                  <el-avatar
                      v-if="item.status !== statusList.WITHDRAW"
                      class="noSelect"
                      :src="item.userInfo !== undefined ? (item.userInfo.avatar !== undefined ? item.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')) : getWebSiteInfoValue('touristAvatar')"
                      :key="item.userUid"
                  />
                </div>
                <div class="nickname">
                  <div class="userInfo">
                    <span class="item noSelect"
                    ><i class="el-icon-time"></i>
                      {{ timeFormat(item.createTime)
                      }}</span
                    >
                    <span v-if="item.ip" class="item noSelect"
                    ><i class="el-icon-location-information"></i>
                      IP属地:{{ splitIpAddress(item.address) }}
                    </span>
                    <span
                        class="noSelect"
                    >{{ item.userInfo !== undefined ? item.userInfo.nickname : '' }}</span
                    >
                  </div>

                  <div
                      v-if="item.status !== statusList.WITHDRAW"
                      v-html="item.content"
                      class="nowMessageContent"
                      @contextmenu.prevent="openRightClickMenu($event, item, index)"
                  ></div>
                  <div style="color: var(--text-color)" v-else class="noSelect">
                    "
                    {{ item.userInfo !== undefined ? item.userInfo.nickname : '' }}
                    " 撤回了一条消息
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 输入框 -->
        <div class="bottom">
          <!-- 输入选择 如表情、图片等 -->
          <div class="toolbars">
            <div>
              <span class="item hand-style" @click.stop="handleOpen">
                <i class="iconfont icon-biaoqing"></i>
              </span>
              <el-upload
                  class="avatar-uploader"
                  :show-file-list="false"
                  ref="upload"
                  name="filedatas"
                  action=""
                  :http-request="uploadSectionFile"
                  multiple
              >
                <span class="item hand-style">
                  <i class="el-icon-picture"></i>
                </span>
              </el-upload>
            </div>
          </div>
          <!-- 表情框 -->
          <div class="emoji-wrapper" v-show="emojiShow">
            <Emoji @chooseEmoji="handleChooseEmoji" />
          </div>
          <!-- 输入内容 -->
          <div
              id="im-input-box"
              class="im-input-box"
              ref="inputRef"
              @input="updateContent"
              contenteditable="true"
              @paste="optimizePasteEvent"
              @keydown="handkeyEnter"
              data-placeholder="说点什么呢"
          ></div>
          <el-button class="btn" @click="send($refs.inputRef.innerHTML, 1)"
          >发送[Enter]</el-button
          >
        </div>

        <!-- 自定义右键功能 -->
        <ul
            v-show="rightClickMenuVisible"
            :style="{ left: rightClickMenuLeft + 'px', top: rightClickMenuTop + 'px' }"
            class="contextmenu"
        >
          <li @click="clipboard" class="copyBtn">
            <div class="menuitem hand-style">
              <i class="el-icon-document-copy"></i> 复制
            </div>
          </li>
          <li @click="translate">
            <div class="menuitem hand-style">
              <i class="iconfont icon-fanyi"></i>翻译
            </div>
          </li>
          <li
              @click="handlePrivate"
              v-if="message && message.userUid !== user.uid"
          >
            <div class="menuitem hand-style">
              <i class="el-icon-chat-dot-round"></i>私信
            </div>
          </li>
          <li
              @click="withdraw"
              v-else-if="message && message.userUid === user.uid"
          >
            <div class="menuitem hand-style">
              <i class="iconfont icon-chehui"></i>撤回
            </div>
          </li>
          <li class="sousuo">
            <div class="menuitem hand-style">
              <i class="el-icon-search"></i>搜一搜
              <i class="el-icon-caret-right" style=""></i>

              <ul class="sousuomenu">
                <li @click="handleSearch(0)">
                  <div class="menuitem hand-style">百度搜索</div>
                </li>
                <li @click="handleSearch(1)">
                  <div class="menuitem hand-style">Gitee搜索</div>
                </li>
                <li @click="handleSearch(2)">
                  <div class="menuitem hand-style">Github搜索</div>
                </li>
              </ul>
            </div>
          </li>
        </ul>
      </el-card>
      <!-- 房间列表 -->
      <div class="online">
        <ul class="online-item">
          <li @click="dialogNewChatRoomVisible = true" class="onlineLi hand-style">
            <div class="room-list-item">
              <div class="roomName el-icon-circle-plus-outline"></div>
              <span>/</span>
              <div class="roomName el-icon-search"></div>
            </div>
          </li>
          <li
              ref="room"
              :class="!index ? 'onlineLi hand-style active' : 'onlineLi hand-style'"
              v-for="(item, index) in roomList"
              :key="index"
          >
            <div class="room-list-item" @click="selectUserIm(item, index)">
              <div class="room-list-item">
                <el-avatar
                    class="img"
                    :src="item.avatar !== undefined ? item.avatar.fileUrl : ''"
                    alt=""
                />
                <div class="roomName">
                  {{ item.name }}
                </div>
              </div>
            </div>
            <div v-if="item.type !== 0" class="close" @click="closeRoom(item.uid, index)">
              <span><i class="el-icon-close"></i></span>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <el-dialog
        :lock-scroll="false"
        title="粘贴图片"
        :visible.sync="imgDialogVisible"
        width="30%"
        center
    >
      <div style="width: 100%" id="dialogImg">
        <div v-html="textImg"></div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="imgDialogVisible = false"
        >取 消</el-button
        >
        <el-button size="small" type="primary" @click="uploadSectionFile(null)"
        >发 送</el-button
        >
      </span>
    </el-dialog>


    <el-dialog center :title="title" :visible.sync="dialogNewChatRoomVisible">

      <el-tabs >
        <el-tab-pane label="搜索群聊">

          <el-row>
            <el-form :model="chatRoomSearchParam" label-width="68px">
              <el-col :span="12">
                <el-form-item label="分类名">
                  <el-input v-model="chatRoomSearchParam.keyword" placeholder="请输入群聊名"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" size="small" @click="handleSearchChatRoom">查找</el-button>
                </el-form-item>
              </el-col>
            </el-form>
          </el-row>

          <el-row
              v-for="(item) in searchedCharRoomList"
          >
            <el-divider></el-divider>
            <el-col :span="4">
              <el-avatar
                  :src="item.avatar !== undefined ? item.avatar.fileUrl : ''"
                  alt=""
              />
            </el-col>
            <el-col :span="6">
              <h3>{{ item.name }}</h3>
            </el-col>
            <el-col :span="4">
              共{{ item.memberUids.split(',').length }}人
            </el-col>
            <el-col :span="4">
              <el-button @click="handleJoinChatRoom(item)">
                加入群聊
              </el-button>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="创建群聊">
          <div slot="label" @click="editChatRoomForm = {name: '', avatarUid: ''}; chatRoomAvatarUrl = ''">
            创建群聊
          </div>
          <el-form :rules="editChatRoomRules" ref="dataForm" :model="editChatRoomForm">
            <el-form-item prop="avatarUid" label="群聊头像" >
              <el-upload
                  class="avatar-uploader"
                  :show-file-list="false"
                  ref="upload"
                  name="filedatas"
                  action=""
                  :http-request="uploadChatRoomAvatar"
                  multiple
              >
                <el-avatar
                    v-if="chatRoomAvatarUrl"
                    :src="chatRoomAvatarUrl"
                />
                <i v-else class="el-icon-plus avatar-img-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item prop="name" label="群聊名">
              <el-input v-model="editChatRoomForm.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" @click="handleAddChatRoom">确定</el-button>
        </el-tab-pane>

        <el-tab-pane label="我的群聊">
          <div slot="label" @click="fetchMyChatRoomList">
            我的群聊
          </div>
          <el-col :span="14">
            <el-row
                v-for="(item) in myChatRoomList"
            >
              <el-divider></el-divider>
              <el-col :span="4">
                <el-avatar
                    :src="item.avatar !== undefined ? item.avatar.fileUrl : ''"
                    alt=""
                />
              </el-col>
              <el-col :span="10">
                <h3>{{ item.name }}</h3>
              </el-col>
              <el-col :span="4">
                <span v-if="item.type === 1">
                  共{{ item.memberUids.split(',').length }}人
                </span>
                <span v-else-if="item.type === 0">
                  公共群聊
                </span>
              </el-col>
              <el-col :span="4">
                <el-button @click="handleViewChatRoom(item)">
                  查看
                </el-button>
              </el-col>
            </el-row>
          </el-col>

          <el-col :span="2">
            <el-divider direction="vertical"></el-divider>
          </el-col>

          <el-col :span="8">
            <el-row>
              <el-form :rules="editChatRoomRules" ref="dataForm" :model="editChatRoomForm">
                <el-form-item prop="avatarUid" label="群聊头像" >
                  <el-upload
                      class="avatar-uploader"
                      :show-file-list="false"
                      ref="upload"
                      name="filedatas"
                      action=""
                      :http-request="uploadChatRoomAvatar"
                      multiple
                  >
                    <el-avatar
                        v-if="chatRoomAvatarUrl"
                        :src="chatRoomAvatarUrl"
                    />
                    <i v-else class="el-icon-plus avatar-img-icon"></i>
                  </el-upload>
                </el-form-item>
                <el-form-item prop="name" label="群聊名">
                  <el-input v-model="editChatRoomForm.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-button v-if="editChatRoomForm.uid" type="primary" size="small" @click="handleUpdateChatRoom">保存</el-button>
              </el-form>
            </el-row>
            <el-row>
              <!-- 这里可以加上群成员管理 -->
              <el-col v-for="member in chatRoomMemberList" :key="member.uid" :span="4" class="member-col">
                <el-tooltip :content="member.userInfo.nickname + '(' + (member.uid === user.uid ? '群主' : '') + ')'">
                  <a :href="'/user?userUid=' + member.uid">
                    <el-avatar :src="member.userInfo.avatar ? member.userInfo.avatar.fileUrl : getWebSiteInfoValue('touristAvatar')"></el-avatar>
                  </a>
                </el-tooltip>
                <span class="kick-member-span">
                  <i v-if="member.uid !== user.uid" class="el-icon-close kick-member-icon" @click="handleKickChatRoomMember(editChatRoomForm.uid, member.uid)"></i>
                  <svg-icon v-else icon-class="bozhu"></svg-icon>
                </span>
              </el-col>
            </el-row>
          </el-col>
        </el-tab-pane>

      </el-tabs>
    </el-dialog>

    <image-preview :img="images"></image-preview>
  </div>
</template>

<script>
import {chatRoomAvatarUpload} from "@/api/fileStorage";

let socket;
import { imageUpload, withdraw, addRoom } from '@/api/im'
import {
  getChatRoomList,
  deleteChatRoom,
  listChatMessage,
  addChatMessage,
  read,
  searchChatRoomList, joinChatRoom, createChatRoom, updateChatRoom, getChatRoomMemberList, kickChatRoomMember
} from "@/api/message";
import {getWebSiteInfoValue, parseTime} from "@/utils";
import { EStatus } from "@/base/EStatus";
import Emoji from '@/components/emoji'
import imagePreview from '@/components/imagePreview'
export default {
  components: {
    Emoji,
    imagePreview
  },
  data() {
    return {
      statusList: [],
      uploadPictureHost: process.env.VUE_APP_BASE_API + "/file/upload",
      websoketUrl: process.env.VUE_APP_WEBSOCKET_API,
      rightClickMenuVisible: false,
      imgDialogVisible: false,
      isLoding: false,
      rightClickMenuTop: 0,
      rightClickMenuLeft: 0,
      text: "",
      messageList: [],
      emojiShow: false,
      user: this.$store.state.user,
      totalPage: 0,
      isBackTop: false,
      message: null,
      textImg: null,
      selectIndex: null,
      title: "PPLAX blog",
      lastIndex: null,
      userId: this.$route.query.userId,
      chatRoomUid: null,
      pageData: {
        currentPage: 1,
        pageSize: 10
      },
      chatRoomSearchParam: {
        currentPage: 1,
        pageSize: 5,
        keyword: ''
      },
      editChatRoomForm: {
        uid: '',
        name: '',
        avatarUid: '',
        type: ''
      },
      editChatRoomRules: {
          name: [
            { required: true, message: '请输入分类名', trigger: 'blue' },
            { min: 1, max: 15, message: '长度在1到15之间', trigger: 'change' }
          ]
      },
      chatRoomAvatarUrl: '',
      searchedCharRoomList: [],
      myChatRoomList: [],
      chatRoomMemberList: [],

      roomList: [
      ],
      selectUserOnline: null,
      atMember: "",
      searchUrl: ['https://www.baidu.com/s?&wd=', 'https://search.gitee.com/?skin=rec&type=repository&q=', 'https://github.com/search?q='],
      lastEditRange: null,
      RegEx: /(?<=(img src="))[^"]*?(?=")/gims,
      images: {},
      dialogNewChatRoomVisible: false
    }
  },

  mounted() {
    document.addEventListener("click", this.handleClose)
    document.getElementById("im").oncontextmenu = new Function("event.returnValue=false");
    this.$refs['inputRef'].onclick = () => {
      // 获取选定对象
      let selection = window.getSelection()
      // 设置最后光标对象
      if (selection.rangeCount > 0) {
        // 记录光标最后点击可编辑div中所选择的位置
        this.lastEditRange = selection.getRangeAt(0);
      }
    }
  },
  watch: {
    //   监听属性对象，newValue为新的值，也就是改变后的值
    visible(newValue, oldValue) {
      if (newValue) {
        document.body.addEventListener("click", this.closeRightClickMenu);
      } else {
        document.body.removeEventListener("click", this.closeRightClickMenu);
      }
    },
    user(newName) {
      if (typeof (newName) == 'string') {
        this.user = JSON.parse(newName)
      } else {
        this.user = newName
        //发送soket连接
        this.init()
      }
    },
    messageList() {
      if (this.isBackTop) {
        this.$refs.messageContainer.scrollTop = 0
        this.isBackTop = false
      } else {
        this.$nextTick(() => {
          this.$refs.messageContainer.scrollTop = this.$refs.messageContainer.scrollHeight;
        })
      }

    }
  },
  created() {
    this.statusList = EStatus
    this.init()
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
    updateContent(event) {
      let selection = window.getSelection()
      this.lastEditRange = selection.getRangeAt(0);
      if (event.target.innerText.indexOf("img") != -1) {
        this.textImg = event.target.innerText
        event.target.innerText = null
        this.imgDialogVisible = true
        return;
      }
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
    handleSearch(type) {
      let url = this.searchUrl[type] + this.message.content.trim()
      window.open(url, '_blank')
    },

    closeRoom(id, index) {
      this.$confirm('此操作将退出该群聊，如果您是群主，将直接解散群聊，是否确定?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        lockScroll: false,
        type: 'warning'
      }).then(() => {
        deleteChatRoom(id).then(res => {
          this.$delete(this.roomList, index);

          this.$toast.success("删除成功");
        })
      }).catch(() => {
        this.$toast.info("已取消删除");
      });
    },

    //发送图片
    uploadSectionFile: function (param) {

      var formData = new FormData()
      if (!param) {
        var dialogImg = document.getElementById('dialogImg');
        var images = dialogImg.getElementsByTagName('img');
        // 提取每个图片的Base64数据，并添加到FormData对象中
        for (var i = 0; i < images.length; i++) {
          var imgSrc = images[i].getAttribute('src');

          var base64Data = imgSrc.split(',')[1];
          const byteCharacters = atob(base64Data);
          const byteArrays = [];
          for (let offset = 0; offset < byteCharacters.length; offset += 512) {
            const slice = byteCharacters.slice(offset, offset + 512);
            const byteNumbers = new Array(slice.length);
            for (let i = 0; i < slice.length; i++) {
              byteNumbers[i] = slice.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            byteArrays.push(byteArray);
          }
          const blob = new Blob(byteArrays, { type: 'image/jpeg' });
          formData.append('images[]', blob, 'image' + i + '.jpg'); // 根据实际的文件名进行调整
          formData.append('multipartFile', blob, Date.now() + '.jpg');
        }
      } else {
        this.files = param.file
        formData.append('file', this.files)
      }

      imageUpload(formData).then(res => {
        //上传之后发送消息
        let content = `<img src="${res.data.fileUrl}" alt="" class="messageImg" style="width: 150px;height: 150px;">`
        this.send(content)
        this.imgDialogVisible = false

      }).catch(err => {

      })
    },
    //截取地址
    splitIpAddress(address) {
      let splitedAddress = address.split("|")
      return splitedAddress[splitedAddress.length - 1]
    },
    //选择用户单聊
    selectUserIm(item, index) {

      if (this.lastIndex != null) {
        this.$refs.room[this.lastIndex].className = "onlineLi"
      }
      this.$refs.room[0].className = "onlineLi"
      this.$refs.room[index].className += " active"
      this.lastIndex = index


      this.pageData.currentPage = 1
      this.chatRoomUid = item.uid

      //为空则是群聊
      this.title = item.name
      this.messageList = []
      this.selectUserOnline = item
      listChatMessage(this.chatRoomUid, this.pageData).then(res => {
        let arr = res.data
        for (let i = arr.length - 1; i >= 0; i--) {
          this.messageList.push(arr[i])
        }
        this.totalPage = Math.ceil(res.total / this.pageData.pageSize)
      })
      //修改为已读
      read(item.uid)
      item.readNum = 0
    },
    /**
     * 打开右键菜单
     * @param e
     * @param item
     * @param index
     */
    openRightClickMenu(e, item, index) {
      var x = e.pageX; //这个应该是相对于整个浏览器页面的x坐标，左上角为坐标原点（0,0）
      var y = e.pageY; //这个应该是相对于整个浏览器页面的y坐标，左上角为坐标原点（0,0）
      this.rightClickMenuTop = y;
      this.rightClickMenuLeft = x;
      this.rightClickMenuVisible = true; //显示菜单
      this.message = item
      this.message.index = index
    },

    //撤回
    withdraw() {
      let message = {
        code: this.selectUserOnline == null ? 2 : 1,
        content: "消息已撤回",
        fromUserId: this.message.fromUserId,
        id: this.message.id,
        index: this.message.index,
        isRead: 0,
        isWithdraw: 1,
        toUserId: this.message.toUserId,
        type: 1
      }
      // 将组装好的json发送给服务端，由服务端进行转发
      withdraw(message).then(re => {

      })
    },
    /**
     * 创建私信
     */
    handlePrivate() {
      this.editChatRoomForm.uid = ''
      this.editChatRoomForm.name = ''
      this.editChatRoomForm.avatarUid = ''
      this.editChatRoomForm.type = 2
      this.editChatRoomForm.memberUids = this.user.uid + ',' + this.message.userUid

      createChatRoom(this.editChatRoomForm).then(res => {
        this.name = ''
        this.avatarUid = ''
        this.chatRoomAvatarUrl = ''
        this.editChatRoomForm.type = ''
        this.editChatRoomForm.memberUids = undefined
        this.open()
        this.dialogNewChatRoomVisible = false
        this.closeRightClickMenu()
      })
    },
    //翻译
    translate() {
      window.open("https://fanyi.baidu.com/?aldtype=16047#zh/en/" + this.message.content, '_blank')
      this.closeRightClickMenu()
    },
    //复制
    clipboard() {
      const clipboard = new this.Clipboard('.copyBtn', {
        text: () => this.message.content
      })
      clipboard.on('success', () => {
        clipboard.destroy()
        this.closeRightClickMenu()
      })
      clipboard.on('error', () => {
        this.$toast.error("复制失败");
        clipboard.destroy()
        this.closeRightClickMenu()
      })
    },
    //关闭菜单
    closeRightClickMenu() {
      this.rightClickMenuVisible = false; //关闭菜单
    },
    /**
     * 格式化时间戳
     * @param time
     * @returns {string|null}
     */
    timeFormat(time) {
      return parseTime(time);
    },
    //加载更多消息
    handleMore() {
      this.pageData.currentPage++;
      this.isBackTop = true
      this.isLoding = true
      // 这个应该是socket的，之后再说
      // if (this.selectUserOnline) {
      //     getUserImHistoryList(this.pageData).then(res => {
      //         let arr = res.data.records
      //         for (let i = 0; i < arr.length; i++) {
      //             this.messageList.unshift(arr[i])
      //         }
      //         this.isLoding = false
      //     })
      //     return;
      // }
      listChatMessage(this.chatRoomUid, this.pageData).then(res => {
        let arr = res.data
        for (let i = 0; i < arr.length; i++) {
          this.messageList.unshift(arr[i])
        }
        this.isLoding = false
      })
    },
    //获取群聊历史记录
    getHistoryList() {
      if (this.chatRoomUid) {
        listChatMessage(this.chatRoomUid, this.pageData).then(res => {
          let arr = res.data.records
          for (let i = arr.length - 1; i >= 0; i--) {
            this.messageList.push(arr[i])
          }
          this.totalPage = res.data.pages
        })
      }
    },
    //Enter事件
    handkeyEnter(event) {
      // 判断是否按下了Ctrl+Enter键
      if (event.ctrlKey && event.keyCode === 13) {
        // 在光标位置插入换行符
        event.preventDefault();
        const selection = window.getSelection();
        const range = selection.getRangeAt(0);
        const br = document.createElement('br');
        range.insertNode(br);
        range.setStartAfter(br);
        range.collapse(true);
        selection.removeAllRanges();
        selection.addRange(range);
        //this.$refs.inputRef.innerText += '\n';

        // 阻止默认的换行行为
        return;
      }
      if (event.keyCode == 13) {
        // 阻止默认的换行行为
        event.preventDefault();
        this.send(this.$refs.inputRef.innerHTML)
      }
    },
    //打开表情框
    handleOpen() {
      this.emojiShow = !this.emojiShow
    },
    //关闭表情框
    handleClose(e) {
      if (e.target.className == "messageImg") {
        this.images = {
          urls: e.target.currentSrc,
          time: new Date().getTime()
        }
      }
      if (e.target.className != "el-radio-button__orig-radio" && e.target.className != "el-radio-button__inner"
          && e.target.className != "el-upload__input" && e.target.className != "el-icon-plus avatar-uploader-icon") {
        this.emojiShow = false
      }

    },
    //添加表情
    handleChooseEmoji(value) {
      // 创建一个img标签（表情）
      let img = document.createElement('img');
      img.src = value.url;
      img.style.verticalAlign = 'middle';
      img.style.marginLeft = "2px"
      img.style.marginRight = "2px"

      img.style.maxHeight = value.maxHeight;
      img.style.height = value.width
      img.style.width = value.width
      let edit = this.$refs['inputRef']
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
        return
      }
    },
    //发送消息
    send(content, type) {

      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
        return;
      }
      if (!this.user) {
        this.$toast.error("请先登录");
        this.$store.state.loginFlag = true
        return;
      }
      const reg = /^\s*$/;
      if (reg.test(content)) {
        console.log('输入值不能为空');
        return;
      }

      let message = {
        'content': content,
        'readUserUids': this.user.uid
      }

      //发送消息
      addChatMessage(this.chatRoomUid, message).then(res => {
        this.messageList.push(res.data)
      })
      // 构建消息内容，本人消息
      this.$refs.inputRef.innerText = null;
      this.atMember = ""
    },
    //初始化socket
    init() {
      let _this = this;
      if (!_this.user) {
        this.$toast.error("登录才能进行群聊");
        return;
      }
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      } else {
        let socketUrl = _this.websoketUrl + "?userId=" + _this.user.uid;
        if (socket != null) {
          socket.close();
          socket = null;
        }
        // 开启一个websocket服务
        socket = new WebSocket(socketUrl);
        //打开事件
        socket.onopen = _this.open();
        //  浏览器端收消息，获得从服务端发送过来的文本消息
        socket.onmessage = function (msg) {
          console.log("收到数据====" + msg.data)
          let data = JSON.parse(msg.data)

          //群聊
          if (data.code == 2) {
            if (_this.selectUserOnline) {
              return;
            }
            // 这是撤回的逻辑
            if (data.index != null) {
              _this.messageList[data.index].content = data.content
              _this.messageList[data.index].isWithdraw = 1
              return;
            }
            _this.messageList.push(data)
            return;
          }
          //单聊
          if (data.code == 1) {
            for (let index = 0; index < _this.roomList.length; index++) {

              const room = _this.roomList[index]
              if (room.receiveId == data.fromUserId) {
                _this.roomList[index].readNum++
              }
            }
            if (!_this.selectUserOnline) {
              return;
            }
            if (_this.selectUserOnline.receiveId == data.fromUserId || _this.selectUserOnline.receiveId == data.toUserId) {

              //这是撤回的逻辑
              if (data.index != null) {
                _this.messageList[data.index].content = data.content
                _this.messageList[data.index].isWithdraw = 1
                return;
              }
              _this.messageList.push(data)
              return;
            }
            return;
          }
        };
        //关闭事件
        socket.onclose = function () {
          console.log("websocket已关闭");

        };
        //发生了错误事件
        socket.onerror = function () {
          console.log("websocket发生了错误");

        }
      }
    },

    open() {
      console.log("websocket已打开");
      //获取房间列表
      getChatRoomList(false).then(res => {
        this.roomList = res.data
      })
      //连接成功后获取历史聊天记录
      this.getHistoryList()
    },


    /**
     * 处理查找聊天室
     */
    handleSearchChatRoom() {
      searchChatRoomList(this.chatRoomSearchParam).then(res => {
        this.searchedCharRoomList = res.data
      })
    },

    /**
     * 处理加入群聊
     */
    handleJoinChatRoom(chatRoom) {
      joinChatRoom(chatRoom.uid).then(res => {
        this.$toast.success("加入成功");
        this.open()
        this.dialogNewChatRoomVisible = false
      })
    },

    /**
     * 上传聊天室头像
     * @param param
     */
    uploadChatRoomAvatar: function (param) {
      let file = param.file
      // FormData 对象
      var formData = new FormData()
      // 文件对象
      formData.append('file', file)
      chatRoomAvatarUpload(formData).then(res => {
        this.editChatRoomForm.avatarUid = res.data.uid
        this.chatRoomAvatarUrl = res.data.fileUrl
      })
    },

    /**
     * 添加新群聊
     */
    handleAddChatRoom: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {

          this.editChatRoomForm.type = 1
          createChatRoom(this.editChatRoomForm).then(res => {
            this.$toast.success("创建成功");
            this.name = ''
            this.avatarUid = ''
            this.chatRoomAvatarUrl = ''
            this.editChatRoomForm.type = ''
            this.open()
            this.dialogNewChatRoomVisible = false
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },

    /**
     * 更新群聊
     */
    handleUpdateChatRoom: function () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {

          updateChatRoom(this.editChatRoomForm.uid, this.editChatRoomForm).then(res => {
            this.$toast.success("修改成功");
            this.editChatRoomForm.name = ''
            this.editChatRoomForm.avatarUid = ''
            this.editChatRoomForm.uid = ''
            this.chatRoomAvatarUrl = ''
            this.open()
            this.dialogNewChatRoomVisible = false

            this.fetchMyChatRoomList()
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },

    /**
     * 获取我是群主的群聊
     */
    fetchMyChatRoomList: function () {
      getChatRoomList(true).then(res => {
        this.myChatRoomList = res.data
      })
    },

    /**
     * 处理查看该群聊
     */
    handleViewChatRoom: function (chatRoom) {
      this.editChatRoomForm.uid = chatRoom.uid
      this.editChatRoomForm.name = chatRoom.name
      this.editChatRoomForm.avatarUid = chatRoom.avatarUid
      this.chatRoomAvatarUrl = chatRoom.avatar ? chatRoom.avatar.fileUrl : ''

      getChatRoomMemberList(chatRoom.uid).then(res => {
        this.chatRoomMemberList = res.data
      })
    },

    /**
     * 将成员踢出群聊
     */
    handleKickChatRoomMember: function (chatRoomUid, memberUid) {
      this.$confirm('此操作将会踢出该群成员，是否确定?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        lockScroll: false,
        type: 'warning'
      }).then(() => {
        kickChatRoomMember(chatRoomUid, memberUid).then(res => {
          this.$toast.info("已踢出该成员");
          this.handleViewChatRoom({
            uid: this.editChatRoomForm.uid,
            name: this.editChatRoomForm.name,
            avatarUid: this.editChatRoomForm.avatarUid,
            avatar: {
              fileUrl: this.chatRoomAvatarUrl
            }
          })
        })
      }).catch(() => {
        this.$toast.info("已取消");
      });
    }
  }
}
</script>

<style lang="scss" scoped>
.loading {
  /* 加载中的样式 */
  position: relative;
  margin: 0 auto;
  margin-top: 20px;
}

/deep/ #dialogImg {
  img {
    width: 100%;
    height: 200px;
  }
}

.spinner {
  /* 转圈圈的样式 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 30px;
  height: 30px;
  border: 4px solid #ccc;
  border-top-color: var(--theme-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: translate(-50%, -50%) rotate(0deg);
  }

  100% {
    transform: translate(-50%, -50%) rotate(360deg);
  }
}

.im-main {
  min-height: calc(100vh - 167px);

  @media screen and (max-width: 1118px) {
    .im-warpper {
      width: 100%;
    }
  }

  @media screen and (min-width: 1119px) {
    .im-warpper {
      width: 60%;
    }
  }

  .im-warpper {
    color: var(--text-color1);
    display: flex;
    border: 2px solid var(--border-line);
    height: 100%;
    margin-top: 80px;
    background-color: #272a37;
    padding: 10px;
    border-radius: 10px;

    .noSelect {
      user-select: none;
    }

    .online {
      width: 250px;
      background-color: #323644;
      padding: 10px;
      color: #fff;

      .onlineCount {
        padding: 10px;
      }

      .online-item {
        list-style: none;

        .onlineLi {
          padding: 5px;
          line-height: 30px;
          border-radius: 5px;
          margin-top: 10px;
          position: relative;

          &:hover {
            background-color: #ccc;

            .close {
              display: block;
            }
          }

          .room-list-item {
            display: flex;
            align-items: center;

            .roomName {
              margin-left: 10px;
            }
          }

          .readNum {
            margin-left: 10px;
            display: inline-block;
            background-color: #e63131;
            border-radius: 50%;
            width: 25px;
            height: 25px;
            position: relative;

            span {
              position: absolute;
              left: -3px;
              top: -3px;
            }
          }

          .close {
            background-color: #fff;
            color: #000;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            position: absolute;
            top: -15px;
            right: -5px;
            display: none;

            span {
              position: absolute;
              top: -5px;
              left: -8px;
            }
          }
        }

        .active {
          background-color: #ccc;
        }

        img {
          width: 30px;
          height: 30px;
          border-radius: 50%;
          vertical-align: middle;
        }

        span {
          line-height: 30px;
          margin-left: 10px;
        }
      }
    }

    .itemBox {
      //  background-image: url("http://img.shiyit.com/imbg.png");
      background-color: #323644;
      width: 100%;
      box-shadow: none;
      margin-right: 10px;

      .title {
        text-align: center;
        height: 50px;
        line-height: 50px;
        font-size: 18px;
        color: var(--theme-color);
        border-bottom: 1px solid #666;
      }

      .messageBox,
      .emoji-wrapper {
        &::-webkit-scrollbar {
          width: 5px;
        }

        &::-webkit-scrollbar-thumb {
          background-color: #666;
          border-radius: 5px;
        }

        &::-webkit-scrollbar-track {
          background-color: #323644;
        }
      }

      .messageBox {
        height: 500px;
        overflow: auto;
        color: var(--text-color);

        .more {
          text-align: center;
          margin-top: 10px;
          color: rgba(185, 183, 183, 0.898);
        }

        .messageItem {
          margin-top: 20px;
          margin-bottom: 20px;

          .tag {
            width: 15px;
            height: 15px;
            vertical-align: -3px;
            margin: 0 5px;
          }

          .withdraw {
            text-align: center;

            .tag,
            img,
            .userInfo {
              display: none;
            }
          }

          .left {
            padding: 5px 10px;
            display: flex;

            .info {
              margin-left: 5px;

              .nickname {
                font-size: 0.8rem;
                display: block;
                margin-bottom: 3px;

                .item {
                  margin-left: 10px;
                  font-size: 12px;
                }
              }
            }
          }

          img {
            width: 45px;
            height: 45px;
            border-radius: 50%;
          }

          .messageContent,
          .nowMessageContent {
            display: inline-block;
            color: #555;
            border-radius: 3px;
            margin-top: 5px;
            max-width: 400px;
            padding: 8px;
            white-space: pre-wrap;
            user-select: auto;
            text-align: left;

            /deep/ a {
              text-decoration: none;
            }
          }

          .messageContent {
            background-color: var(--im-box-backgroudColor);
            border-radius: 2px 18px 18px;
          }

          .right {
            margin: 2px;
            padding: 5px 10px;
            position: relative;
            display: flex;
            flex-direction: row-reverse;

            .info {
              float: right;
              display: flex;
              flex-direction: row-reverse;
              color: var(--text-color);

              .nickname {
                display: inline-block;
                text-align: right;
                margin-bottom: 3px;
                width: 100%;

                .userInfo {
                  font-size: 0.8rem;
                }

                .item {
                  margin-right: 10px;
                  font-size: 12px;
                }
              }
            }

            .nowMessageContent {
              background-color: var(--im-user-box-backgroudColor);
              border-radius: 18px 2px 18px 18px;
            }

            img {
              float: right;
              margin-left: 5px;
            }
          }
        }
      }

      .bottom {
        border-top: 1px solid #999;
        margin-top: 20px;
        position: relative;

        .toolbars {
          padding: 10px;

          .item {
            margin-left: 10px;
            padding: 5px;
            border-radius: 5px;

            &:hover {
              background-color: rgb(92, 89, 89);
            }
          }

          i {
            color: #fff;
            font-size: 1.1rem;
          }

          svg {
            width: 20px;
            height: 20px;
            vertical-align: -3px;
          }

          /deep/ .avatar-uploader {
            display: inline-block;
          }
        }

        .emoji-wrapper {
          position: absolute;
          top: -210px;
          left: 10px;
        }

        .contentBox {
          height: 160px;
          width: 100%;
          padding: 20px;
          border: none;
          outline: none;
          resize: none;
          background-color: #323644;
          color: #fff;
          font-size: 15px;
        }

        .im-input-box {
          border: none;
          outline: none;
          color: #fff;
          padding-left: 10px;
          height: 166px;
          padding-top: 10px;

          /deep/ img {
            height: 100px;
            vertical-align: middle;
          }

          /deep/ .at_member {
            padding: 0;
            font-size: inherit;
            line-height: 1;
            color: var(--theme-color);
            background: transparent;
            border: none;
          }

          &:empty:before {
            content: attr(data-placeholder);
            color: #666;
          }
        }

        .btn {
          position: absolute;
          right: 10px;
          bottom: 10px;
          width: auto;
          height: 40px;
          border-radius: 10px;
          text-align: center;
          line-height: 40px;
          padding: 0 5px;
          background-image: linear-gradient(to left, #c3cb80, #4db66d, #26a6a0);
        }
      }

      .contextmenu {
        margin: 0;
        background: #424656;
        z-index: 3000;
        position: fixed; //关键样式设置固定定位
        list-style-type: none;
        font-weight: 400;
        color: #333;
        box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
        border-radius: 5px;

        li {
          margin: 0;
          padding: 5px;
          width: 100px;
          color: #fff;
          position: relative;

          .menuitem {
            padding: 5px;
            font-size: 0.8rem;
            border-radius: 5px;

            i {
              margin-right: 3px;
            }

            &:hover {
              background: #ddaec8;
            }
          }

          .sousuomenu {
            list-style: none;
            position: absolute;
            top: -50px;
            right: -110px;
            background: #424656;
            display: none;
            border-radius: 5px;
          }
        }

        .sousuo:hover {
          .sousuomenu {
            display: block;
          }
        }
      }
    }
  }
}

.member-col {
  position: relative;
}

.kick-member-span {
  position: absolute;
  top: 0;
  right: 0;
  background-color: #f6416c;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  opacity: 0;
  transition: opacity 0.3s;
}

.member-col:hover .kick-member-span {
  opacity: 1;
}

.member-col:active .kick-member-span {
  background-color: #999;
}

.kick-member-icon {
  font-size: 12px;
  color: #f8f3d4;
}
</style>
