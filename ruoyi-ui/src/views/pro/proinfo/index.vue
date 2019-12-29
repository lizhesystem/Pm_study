<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="项目编号" prop="proNum">
        <el-input
          v-model="queryParams.proNum"
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
      <el-form-item label="项目运营平台" prop="operator">
        <el-input
          v-model="queryParams.operator"
          placeholder="请输入项目运营平台"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目交易模式：根据字典表获取" prop="tradeMode">
        <el-input
          v-model="queryParams.tradeMode"
          placeholder="请输入项目交易模式：根据字典表获取"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行编号：根据字典获取" prop="bankId">
        <el-input
          v-model="queryParams.bankId"
          placeholder="请输入银行编号：根据字典获取"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="银行名称" prop="bankName">
        <el-input
          v-model="queryParams.bankName"
          placeholder="请输入银行名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户联系人姓名" prop="custname">
        <el-input
          v-model="queryParams.custname"
          placeholder="请输入客户联系人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户联系人手机" prop="custphone">
        <el-input
          v-model="queryParams.custphone"
          placeholder="请输入客户联系人手机"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户联系人邮箱" prop="custemail">
        <el-input
          v-model="queryParams.custemail"
          placeholder="请输入客户联系人邮箱"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属项目经理" prop="proManager">
        <el-input
          v-model="queryParams.proManager"
          placeholder="请输入所属项目经理"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统部署人员" prop="insetallId">
        <el-input
          v-model="queryParams.insetallId"
          placeholder="请输入系统部署人员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="系统培训人人员" prop="trainId">
        <el-input
          v-model="queryParams.trainId"
          placeholder="请输入系统培训人人员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="验收状态：1验收 2未验收" prop="acceptanceStatus">
        <el-select v-model="queryParams.acceptanceStatus" placeholder="请选择验收状态：1验收 2未验收" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="运行状态：1正常 2暂停" prop="proStatus">
        <el-select v-model="queryParams.proStatus" placeholder="请选择运行状态：1正常 2暂停" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:proinfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:proinfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:proinfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:proinfo:export']"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="proinfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="项目ID" align="center" prop="proId"/>
      <el-table-column label="项目编号" align="center" prop="proNum"/>
      <el-table-column label="项目名称" align="center" prop="proName"/>
      <el-table-column label="项目运营平台" align="center" prop="operator"/>
      <el-table-column label="项目交易模式：根据字典表获取" align="center" prop="tradeMode"/>
      <el-table-column label="银行编号：根据字典获取" align="center" prop="bankId"/>
      <el-table-column label="银行名称" align="center" prop="bankName"/>
      <el-table-column label="客户联系人姓名" align="center" prop="custname"/>
      <el-table-column label="客户联系人手机" align="center" prop="custphone"/>
      <el-table-column label="客户联系人邮箱" align="center" prop="custemail"/>
      <el-table-column label="所属项目经理" align="center" prop="proManager"/>
      <el-table-column label="系统部署人员" align="center" prop="insetallId"/>
      <el-table-column label="系统培训人人员" align="center" prop="trainId"/>
      <el-table-column label="验收状态：1验收 2未验收" align="center" prop="acceptanceStatus"/>
      <el-table-column label="运行状态：1正常 2暂停" align="center" prop="proStatus"/>
      <el-table-column label="部署时间" align="center" prop="insertTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.insertTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="培训时间" align="center" prop="trainTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.trainTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上线时间" align="center" prop="businessTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.businessTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="终止时间" align="center" prop="terminationTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.terminationTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:proinfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:proinfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

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
          // this.proinfoList = response.rows;
          // this.total = response.total;
          // this.loading = false;
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
          this.title = '修改项目列'
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
    }
  }
</script>
