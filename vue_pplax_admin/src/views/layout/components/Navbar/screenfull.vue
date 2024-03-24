<template>
  <div @click="screen">
    <el-icon class="el-icon-full-screen" style="height: 2.5em;font-size: 18px;cursor: pointer" ></el-icon>
  </div>
</template>

<script>
export default {
  name: 'indexAss',

  data() {
    return {
      fullscreen: false,
    }
  },

  methods: {
    screen() {
      let element = document.documentElement

      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen()
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen()
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen()
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen()
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen()
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen()
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen()
        } else if (element.msRequestFullscreen) {
          // IE11

          element.msRequestFullscreen()
        }
      }

      this.fullscreen = !this.fullscreen
    },
  },
}
</script>

