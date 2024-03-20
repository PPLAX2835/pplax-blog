<template>
  <div v-if="!item.hidden && item.children" class="menu-wrapper">

    <template v-if="!hasOneShowingChild(item.children) && isMenu(item) && item.route">
      <a :href="item.route" target="_blank" @click="clickLink(item.route,$event)">
        <el-menu-item :index="resolvePath(item.route)" :class="{'submenu-title-noDropdown':!isNest}">
          <item :icon="item.icon" :title="item.title" />
        </el-menu-item>
      </a>
    </template>

    <el-submenu v-else :index="item.route">
      <template slot="title">
        <item :icon="item.icon" :title="item.title" />
      </template>

      <template v-for="child in item.children" v-if="!child.hidden && isMenu(child) ">
        <sidebar-item
          v-if="child.children && child.children.length > 0 && hasMenuChild(child.children)"
          :is-nest="true"
          :item="child"
          :key="child.route"
          :base-path="resolvePath(child.route)"
          class="nest-menu"/>

        <a v-else :href="child.route" :key="child.title" target="_blank" @click="clickLink(child.route,$event)">
          <el-menu-item :index="resolvePath(child.route)">
            <item :icon="child.icon" :title="child.title" />
          </el-menu-item>
        </a>
      </template>
    </el-submenu>

  </div>
</template>

<script>
import path from 'path'
import { validateURL } from '@/utils/validate'
import Item from './Item'

export default {
  name: 'SidebarItem',
  components: { Item },
  props: {
    // route配置json
    item: {
      type: Object,
      required: true
    },
    isNest: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    }
  },
  data() {
    return {

    }
  },
  methods: {
    isMenu(item) {
      return item.type === 'menu';
    },
    hasMenuChild(children) {
      children.filter(item => {
        if (item.type === 'menu') {
          return true;
        }
      })
      return false;
    },
    hasOneShowingChild(children) {
      if (children.length === 0) {
        return false
      }
      for (let i = 0; i < children.length; i++) {
        if (!children[i].hidden) {
          return true
        }
      }
      return false
    },
    resolvePath(routePath) {
      return path.resolve(this.basePath, routePath)
    },
    isExternalLink(routePath) {
      return validateURL(routePath)
    },
    clickLink(routePath, e) {
      if (!this.isExternalLink(routePath)) {
        e.preventDefault()
        const path = this.resolvePath(routePath)
        this.$router.push(path)
      }
    }
  }
}
</script>
