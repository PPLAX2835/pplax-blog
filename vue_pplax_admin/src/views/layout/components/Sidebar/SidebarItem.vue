<template>
  <div v-if="!item.hidden && item.childMenuList" class="menu-wrapper">

    <template v-if="!hasOneShowingChild(item.childMenuList) && isMenu(item) && item.route">
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

      <template v-for="child in item.childMenuList" v-if="!child.hidden && isMenu(child) ">
        <sidebar-item
          v-if="child.childMenuList && child.childMenuList.length > 0 && hasMenuChild(child.childMenuList)"
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
    test(item) {
      if (item.title === '首页') {
        console.log(item)

        console.log(!this.hasOneShowingChild(item.childMenuList))
        console.log(item.childMenuList)
        console.log(item.route && this.isMenu(item))

      }

      return false;

    },
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
      const showingChildren = children.filter(item => {
        if (item.hidden) {
          return false
        } else {
          // temp set(will be used if only has one showing child )
          return true
        }
      })
      if (showingChildren.length === 1) {
        return true
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
