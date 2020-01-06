<template>
  <div class="Context">
    <el-row type="flex" justify="center">
      <el-col :span="18">
        <el-card class="box-card">
          <div v-html="knowledge.knowledgeContent"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

  import { getKnowledge } from '@/api/pro/knowledge'

  export default {
    data() {
      return {
        // 知识库数据
        knowledge: ''
      }
    },
    created() {
      const dictId = this.$route.params && this.$route.params.knowledgeId
      this.getContext(dictId)
    },
    methods: {
      getContext(id) {
        getKnowledge(id).then(response => {
          this.knowledge = response.data
        })
      }
    }

  }
</script>

<style lang="scss" scoped >
.Context{
  margin-top: 30px;
}
</style>
