<template>
  <div>
    <el-menu class="el-menu-demo" mode="horizontal">
      <el-menu-item v-for="(swaggerResource, index) in swaggerResources" :index="index">
        <a :href="baseHost + '/druid/' + swaggerResource.name.split('-')[0]" target="_blank">
        {{swaggerResource.name}}
        </a>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { getSwaggerResources} from "../../api/swagger";

export default {
  components: {
  },
  data() {
    return {
      swaggerResources: [],
      baseHost: process.env.BASE_HOST
    };
  },
  methods: {
  },
  created() {
    getSwaggerResources().then(res => {
      this.swaggerResources = res.data
    })
  },
};
</script>
