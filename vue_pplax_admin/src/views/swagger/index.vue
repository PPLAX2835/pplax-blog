<template>
  <div>
    <el-menu mode="horizontal">
      <el-menu-item
        v-for="(resource, index) in swaggerResources"
        :index="index"
        @click="showDocs(resource)"
      >
        {{resource.name}}
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { getSwaggerResources, getV2ApiDocs} from "../../api/swagger";

export default {
  data() {
    return {
      swaggerResources: [],
      v2ApiDocs: []
    }
  },
  components : {

  },
  computed : {
  },
  created() {
    getSwaggerResources().then(res => {
      this.swaggerResources = res.data
    })
  },
  methods: {
    showDocs(resource) {
      getV2ApiDocs(resource)
    }
  }
}
</script>

<style scoped>
</style>
