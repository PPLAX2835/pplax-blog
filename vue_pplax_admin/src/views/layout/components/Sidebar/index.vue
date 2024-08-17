<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
    >
      <sidebar-item v-for="menu in menuList" :key="menu.title" :item="menu" :base-path="menu.route"/>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  created() {
    this.$store.dispatch('getCurrentUserRoleWithMenu')
    this.$store.dispatch('getRoleList')
    this.$store.dispatch('getSiteSetting')
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'menu'
    ]),
    routes() {
      return this.$router.options.routes
    },
    menuList() {
      return this.menu
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
