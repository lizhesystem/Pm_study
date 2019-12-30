<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">

      <el-form-item label="项目编号" prop="proName">
        <el-input
          v-model="queryParams.proName"
          placeholder="请输入项目编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="项目名称" prop="proName">
        <el-input
          v-model="queryParams.proName"
          placeholder="请输入项目名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="运维人员" prop="workId">
        <el-input
          v-model="queryParams.insetallId"
          placeholder="请输入运维人员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="运行状态" prop="acceptanceStatus">
        <el-select
          v-model="queryParams.proStatus"
          placeholder="运行状态"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in proStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      <el-button type="text" @click="boxShow = !boxShow">高级查询</el-button>

      <transition name="sub-comments">
        <div v-if="boxShow">
          <el-form-item label="交易模式" prop="acceptanceStatus">
            <el-select
              v-model="queryParams.proStatus"
              placeholder="输入交易模式"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in tradeModeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="部署人员" prop="insetallId">
            <el-input
              v-model="queryParams.insetallId"
              placeholder="请输入系统部署人员"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="培训人员" prop="trainId">
            <el-input
              v-model="queryParams.trainId"
              placeholder="请输入系统培训人人员"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="银行接口" prop="bankName">
            <el-input
              v-model="queryParams.bankName"
              placeholder="请输入银行名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="验收状态" prop="acceptanceStatus">
            <el-select
              v-model="queryParams.acceptanceStatus"
              placeholder="验收状态"
              clearable
              size="small"
              style="width: 240px"
            >
              <el-option
                v-for="dict in acceptanceOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="部署时间" prop="insertTime">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.insertTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择部署时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="培训时间" prop="trainTime">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.trainTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择培训时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="上线时间" prop="businessTime">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.businessTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择上线时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="终止时间" prop="terminationTime">
            <el-date-picker clearable size="small" style="width: 200px"
                            v-model="queryParams.terminationTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择终止时间">
            </el-date-picker>
          </el-form-item>
        </div>
      </transition>

    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pro:proinfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pro:proinfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['pro:proinfo:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pro:proinfo:remove']"
        >删除
        </el-button>
      </el-col>

    </el-row>
    <el-table v-loading="loading" :data="proinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="expand">
        <template slot-scope="props">
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序列" align="center" prop="proId"/>
      <el-table-column label="项目编号" align="center" prop="proNum"/>
      <el-table-column label="项目名称" align="center" prop="proName"/>
      <el-table-column label="运维人员" align="center">
        <template>
          <span v-text="workName"></span>
        </template>
      </el-table-column>
      <el-table-column label="运行状态" align="center" prop="proStatus"/>
    </el-table>
    <!--分页-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改项目列对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目编号" prop="proNum">
          <el-input v-model="form.proNum" placeholder="请输入项目编号"/>
        </el-form-item>
        <el-form-item label="项目名称" prop="proName">
          <el-input v-model="form.proName" placeholder="请输入项目名称"/>
        </el-form-item>
        <el-form-item label="项目运营平台" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入项目运营平台"/>
        </el-form-item>
        <el-form-item label="项目交易模式：根据字典表获取" prop="tradeMode">
          <el-input v-model="form.tradeMode" placeholder="请输入项目交易模式：根据字典表获取"/>
        </el-form-item>
        <el-form-item label="银行编号：根据字典获取" prop="bankId">
          <el-input v-model="form.bankId" placeholder="请输入银行编号：根据字典获取"/>
        </el-form-item>
        <el-form-item label="银行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入银行名称"/>
        </el-form-item>
        <el-form-item label="客户联系人姓名" prop="custname">
          <el-input v-model="form.custname" placeholder="请输入客户联系人姓名"/>
        </el-form-item>
        <el-form-item label="客户联系人手机" prop="custphone">
          <el-input v-model="form.custphone" placeholder="请输入客户联系人手机"/>
        </el-form-item>
        <el-form-item label="客户联系人邮箱" prop="custemail">
          <el-input v-model="form.custemail" placeholder="请输入客户联系人邮箱"/>
        </el-form-item>
        <el-form-item label="所属项目经理" prop="proManager">
          <el-input v-model="form.proManager" placeholder="请输入所属项目经理"/>
        </el-form-item>
        <el-form-item label="系统部署人员" prop="insetallId">
          <el-input v-model="form.insetallId" placeholder="请输入系统部署人员"/>
        </el-form-item>
        <el-form-item label="系统培训人人员" prop="trainId">
          <el-input v-model="form.trainId" placeholder="请输入系统培训人人员"/>
        </el-form-item>
        <el-form-item label="验收状态：1验收 2未验收">
          <el-radio-group v-model="form.acceptanceStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="运行状态：1正常 2暂停">
          <el-radio-group v-model="form.proStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="部署时间" prop="insertTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.insertTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择部署时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="培训时间" prop="trainTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.trainTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择培训时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上线时间" prop="businessTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.businessTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择上线时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="终止时间" prop="terminationTime">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.terminationTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择终止时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listProinfo, getProinfo, delProinfo, addProinfo, updateProinfo, exportProinfo } from '@/api/pro/proinfo'
  import { resolveBlob } from '../../../utils/zipdownload'

  export default {
    data() {
      return {
        // 查询条件是否展开
        boxShow: false,
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 项目列表格数据
        proinfoList: [],
        // 交易模式
        tradeModeOptions: [],
        // 运行状态
        proStatusOptions: [],
        // 验收状态
        acceptanceOptions: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          proNum: undefined,
          proName: undefined,
          operator: undefined,
          tradeMode: undefined,
          bankId: undefined,
          bankName: undefined,
          custname: undefined,
          custphone: undefined,
          custemail: undefined,
          proManager: undefined,
          workId: undefined,
          insetallId: undefined,
          trainId: undefined,
          acceptanceStatus: undefined,
          proStatus: undefined,
          insertTime: undefined,
          trainTime: undefined,
          businessTime: undefined,
          terminationTime: undefined
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
      }
    },
    created() {
      this.getList()
    },
    methods: {
      /** 查询项目列列表 */
      getList() {
        this.loading = true
        listProinfo(this.queryParams).then(response => {
          console.log(response)
          this.proinfoList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          proId: undefined,
          proNum: undefined,
          proName: undefined,
          operator: undefined,
          tradeMode: undefined,
          bankId: undefined,
          bankName: undefined,
          custname: undefined,
          custphone: undefined,
          custemail: undefined,
          proManager: undefined,
          insetallId: undefined,
          trainId: undefined,
          acceptanceStatus: '0',
          proStatus: '0',
          insertTime: undefined,
          trainTime: undefined,
          businessTime: undefined,
          terminationTime: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined,
          remark: undefined
        }
        this.resetForm('form')
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.proId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加项目列'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const proId = row.proId || this.ids
        getProinfo(proId).then(response => {
          this.form = response.data
          this.open = true
          this.title = '修改项目'
        })
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.proId != undefined) {
              updateProinfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('修改成功')
                  this.open = false
                  this.getList()
                } else {
                  this.msgError(response.msg)
                }
              })
            } else {
              addProinfo(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess('新增成功')
                  this.open = false
                  this.getList()
                } else {
                  this.msgError(response.msg)
                }
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const proIds = row.proId || this.ids
        this.$confirm('是否确认删除项目列编号为"' + proIds + '"的数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delProinfo(proIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams
        this.$confirm('是否确认导出所有项目列数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return exportProinfo(queryParams)
        }).then(response => {
          this.download(response.msg)
        }).catch(function() {
        })
      }
    },
    computed: {
      workName() {
        return 'xxx'
      }
    }
  }
</script>
<style lang="scss" scoped>


  .sub-comments-enter-active, .sub-comments-leave-active {
    transition: all .5s ease;
    max-height: 150px;
  }

  .sub-comments-enter, .sub-comments-leave-to {
    /*transform: translate3d(0, 3rem, 0);*/
    transition: all .5s ease;
    opacity: 0;
    max-height: 0;
  }
</style>
