<template>
  <div>
    <el-select v-model="swaggerResourcesIndex" placeholder="请选择">
      <el-option
        v-for="(swaggerResource, index) in swaggerResources"
        :label="swaggerResource.name"
        :key="index"
        :value="index"
      >
      </el-option>
    </el-select>
    <api-index :resource="swaggerResources[swaggerResourcesIndex]"></api-index>
  </div>
</template>

<script>
import apiIndex from "./apiIndex";
import { getSwaggerResources, getV2ApiDocs} from "../../api/swagger";

export default {
  data() {
    return {
      swaggerResources: [],
      swaggerResourcesIndex: '',
      resource: []
    }
  },
  components : {
    apiIndex
  },
  computed : {
  },
  created() {
    getSwaggerResources().then(res => {
      this.swaggerResources = res.data
    })
  },
  methods: {
  }
}
</script>

<style scoped>
</style>
