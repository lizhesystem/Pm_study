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
      <el-form-item label="运维人员" prop="workName">
        <el-input
          v-model="queryParams.workName"
          placeholder="请输入运维人员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="运行状态" prop="proStatus">
        <el-select
          v-model="queryParams.proStatus"
          placeholder="运行状态"
          clearable
          size="small"
          style="width: 200px"
        >
          <el-option
            v-for="dict in proStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="text" @click="boxShow = !boxShow">高级查询</el-button>
      </el-form-item>
      <transition name="sub-comments">
        <div v-if="boxShow">
          <el-form-item label="验收状态" prop="acceptanceStatus">
            <el-select
              v-model="queryParams.acceptanceStatus"
              placeholder="验收状态"
              clearable
              size="small"
              style="width: 200px"
            >
              <el-option
                v-for="dict in acceptanceOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="交易模式" prop="tradeMode">
            <el-select
              v-model="tradeModeFormat"
              placeholder="请输入交易模式"
              size="small"
              clearable
              filterable
              multiple
            >
              <el-option
                v-for="dict in tradeModeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="银行接口" prop="bankId">
            <el-select
              v-model="bankIdFormat"
              placeholder="请输入银行名称"
              clearable
              size="small"
              filterable
              multiple>
              <el-option
                v-for="dict in bankType"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="部署人员" prop="insetallName">
            <el-input
              v-model="queryParams.insetallName"
              placeholder="请输入部署人员姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <br>
          <el-form-item label="培训人员" prop="trainName">
            <el-input
              v-model="queryParams.trainName"
              placeholder="请输入培训人员姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="验收时间">
            <el-date-picker clearable size="small" style="width: 240px"
                            v-model="dateRange"
                            type="daterange"
                            value-format="yyyy-MM-dd"
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
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
    <el-table  v-loading="loading" :data="proinfoList" @selection-change="handleSelectionChange">
      <!--项目详细-->
      <el-table-column type="expand">
        <template slot-scope="scope">
          <el-row justify="center">
            <el-col :span="6">
              <el-alert
                title="客户联系人信息:"
                type="warning"
                :closable="false">
              </el-alert>
              <div>
                <ul class="list-group list-group-striped">
                  <li class="list-group-item">
                    <svg-icon icon-class="tool"/>&nbsp;&nbsp;项目运营平台
                    <div class="pull-right">{{ scope.row.operator }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="user"/>&nbsp;&nbsp;客户联系人姓名
                    <div class="pull-right">{{ scope.row.custname }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="phone"/>&nbsp;&nbsp;客户联系电话
                    <div class="pull-right">{{ scope.row.custphone }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="email"/>&nbsp;&nbsp;客户邮箱地址
                    <div class="pull-right">{{ scope.row.custemail }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="date"/>&nbsp;&nbsp;创建日期
                    <div class="pull-right">{{ scope.row.createTime }}</div>
                  </li>
                </ul>
              </div>
            </el-col>

            <el-col :offset="2" :span="6">
              <el-alert class="el-alert"
                title="项目详细信息:"
                type="warning"
                :closable="false">
              </el-alert>
              <div>
                <ul class="list-group list-group-striped">
                  <li class="list-group-item">
                    <svg-icon icon-class="tree"/>&nbsp;&nbsp;交易模式
                    <div class="pull-right">{{ scope.row.tradeMode }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="validCode"/>&nbsp;&nbsp;银行接口
                    <div class="pull-right">{{ scope.row.bankId }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="user"/>&nbsp;&nbsp;部署人员
                    <div class="pull-right">{{ scope.row.insetallName }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="user"/>&nbsp;&nbsp;培训人员
                    <div class="pull-right">{{ scope.row.trainName }}</div>
                  </li>
                  <li class="list-group-item">
                    <svg-icon icon-class="documentation"/>&nbsp;&nbsp;备注信息
                    <div class="remark">{{ scope.row.remark }}</div>
                  </li>
                </ul>
              </div>
            </el-col>

            <el-col :offset="2" :span="6">
              <el-timeline class="timeline">
                <el-timeline-item
                  v-for="(activity, index) in activities(scope.row)"
                  :key="index"
                  :type="activity.type"
                  :color="activity.color"
                  :timestamp="activity.timestamp">
                  {{activity.content}}
                </el-timeline-item>
              </el-timeline>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="50" align="center"/>
      <el-table-column type="index" label="序列" align-="center"/>
      <!--<el-table-column label="序列" width="50" align="center" type="index"/>-->
      <el-table-column label="项目编号" align="center" prop="proNum"/>
      <el-table-column label="项目名称" align="center" prop="proName"/>
      <el-table-column label="运维人员" align="center" prop="workName"/>
      <el-table-column label="项目经理" align="center" prop="proManager"/>
      <el-table-column label="运行状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.proStatus === '1'" type="success">正常</el-tag>
          <el-tag v-else-if="scope.row.proStatus === '2'" type="danger">暂停</el-tag>
          <el-tag v-else type="warning">部分暂停</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="验收状态" align="center" prop="acceptanceStatus">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.acceptanceStatus === '1'" type="success">已验收</el-tag>
          <el-tag v-else-if="scope.row.acceptanceStatus === '2'" type="danger">未验收</el-tag>
          <el-tag v-else type="warning">部分验收</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="项目上线日期" align="center" prop="businessTime"/>
      <el-table-column label="最后更新时间" align="center" prop="updateTime"/>

      <el-table-column
        label="操作"
        align="center"
        width="150"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pro:proinfo:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.userId !== 1"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pro:proinfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
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
    <el-dialog class="dialog-form" :title="title" :visible.sync="open" width="800px" center
               :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="项目编号" prop="proNum">
              <el-input
                v-model="form.proNum"
                placeholder="请输入项目编号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目名称" prop="proName">
              <el-input v-model="form.proName" placeholder="请输入项目名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="维护人员" prop="workName">
              <el-input v-model="form.workName" placeholder="请输入维护人员"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="部署人员" prop="insetallName">
              <el-input v-model="form.insetallName" placeholder="请输入部署人员"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="培训人员" prop="trainName">
              <el-input v-model="form.trainName" placeholder="请输入培训人员"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="运行状态" prop="proStatus">
              <el-select v-model="form.proStatus" placeholder="请选择">
                <el-option
                  v-for="dict in proStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="center">
          客户资料
        </el-divider>
        <el-row>
          <el-col :span="16">
            <el-form-item label="运营平台" prop="operator">
              <el-input v-model="form.operator" placeholder="请输入项目运营平台"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目经理" prop="proManager">
              <el-input v-model="form.proManager" placeholder="请输入项目经理"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="客户姓名" prop="custname">
              <el-input v-model="form.custname" placeholder="请输入客户姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户手机" prop="custphone">
              <el-input v-model="form.custphone" placeholder="请输入客户手机"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="客户邮箱" prop="custemail">
              <el-input v-model="form.custemail" placeholder="请输入客户邮箱"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center">项目详细</el-divider>
        <el-row>
          <el-col :span="8">
            <el-form-item label="部署时间" prop="insetallTime">
              <el-date-picker clearable size="small" style="width: 170px"
                              v-model="form.insetallTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择部署时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="培训时间" prop="trainTime">
              <el-date-picker clearable size="small" style="width: 170px"
                              v-model="form.trainTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择培训时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="上线时间" prop="businessTime">
              <el-date-picker clearable size="small" style="width: 170px"
                              v-model="form.businessTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择上线时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="交易模式" prop="tradeMode">
              <el-select width="200px" v-model="tradeModeSelect" placeholder="请选择交易模式"
                         clearable
                         filterable
                         multiple
              >
                <el-option
                  v-for="dict in tradeModeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="银行接口" prop="bankId">
              <el-select width="200px" v-model="bankIdSelect" placeholder="请选择交易模式"
                         clearable
                         filterable
                         multiple
              >
                <el-option
                  v-for="dict in bankType"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="验收状态" prop="acceptanceStatus">
              <el-select v-model="form.acceptanceStatus" placeholder="请选择">
                <el-option
                  v-for="dict in acceptanceOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="验收时间" prop="acceptanceTime">
              <el-date-picker clearable size="small" style="width: 170px"
                              v-model="form.acceptanceTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择验收时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="终止时间" prop="terminationTime">
              <el-date-picker clearable size="small" style="width: 170px"
                              v-model="form.terminationTime"
                              type="date"
                              value-format="yyyy-MM-dd"
                              placeholder="选择终止时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
          </el-form-item>
        </el-row>
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

  export default {
    data() {
      return {
        // 时间轴
        // activities: [],
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
        // 银行接口
        bankType: [],
        // 运行状态
        proStatusOptions: [],
        // 验收状态
        acceptanceOptions: [],
        // 日期范围
        dateRange: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 处理tradeMode
        tradeModeFormat: '',
        // 处理bankID
        bankIdFormat: '',
        // 处理select TradeMode
        tradeModeSelect: [],
        bankIdSelect: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          proNum: undefined,
          proName: undefined,
          tradeMode: undefined,
          bankId: undefined,
          workName: undefined,
          insetallId: undefined,
          trainId: undefined,
          acceptanceStatus: undefined,
          proStatus: undefined
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          proNum: [
            { required: true, message: '项目编号不能为空', trigger: 'blur' }
          ],
          proName: [
            { required: true, message: '项目名称不能为空', trigger: 'blur' }
          ],
          workName: [
            { required: true, message: '维护人员不能为空', trigger: 'blur' }
          ],
          insetallName: [
            { required: true, message: '部署人员不能为空', trigger: 'blur' }
          ],
          trainName: [
            { required: true, message: '培训人员不能为空', trigger: 'blur' }
          ],
          proStatus: [
            { required: true, message: '运行状态不能为空', trigger: 'change' }
          ],
          proManager: [
            { required: true, message: '项目经理不能为空', trigger: 'blur' }
          ],
          custemail: [
            {
              type: 'email',
              message: '请输入正确的邮箱地址',
              trigger: ['blur', 'change']
            }
          ],
          custphone: [
            {
              pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
              message: '请输入正确的手机号码',
              trigger: 'blur'
            }
          ],
          acceptanceStatus: [
            { required: true, message: '请选择验收状态', trigger: 'change' }
          ]
        }
      }
    },
    created() {
      this.getList()
      // 获取运行状态字典
      this.getDicts('pro_run_status').then(response => {
        this.proStatusOptions = response.data
      })
      // 获取交易模式字典
      this.getDicts('pro_trade_mode').then(response => {
        this.tradeModeOptions = response.data
      })
      // 获取银行接口字典
      this.getDicts('pro_bank_type').then(response => {
        this.bankType = response.data
      })
      // 获取验收状态字典
      this.getDicts('pro_completion_status').then(response => {
        this.acceptanceOptions = response.data
      })
    },
    methods: {
      /** 查询项目列列表 */
      getList() {
        this.loading = true
        listProinfo(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          // console.log(response)
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
          proNum: undefined,
          proName: undefined,
          operator: undefined,
          tradeMode: [],
          bankId: [],
          custname: undefined,
          custphone: undefined,
          custemail: undefined,
          proManager: undefined,
          workName: undefined,
          insetallName: undefined,
          trainName: undefined,
          acceptanceStatus: undefined,
          proStatus: undefined,
          insetallTime: undefined,
          trainTime: undefined,
          businessTime: undefined,
          acceptanceTime: undefined,
          terminationTime: undefined,
          remark: undefined
        }
        this.resetForm('form')
        this.tradeModeSelect = []
        this.bankIdSelect = []
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
        // 新增的时候重置下查询表单
        this.resetForm('queryForm')
        this.open = true
        this.title = '添加项目信息'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset()
        const proId = row.proId || this.ids
        getProinfo(proId).then(response => {
          this.form = response.data
          // 处理交易模式和银行编号,如果去掉交易模式默认状态是0
          if (this.form.tradeMode !== null && this.form.tradeMode !== '') {
            this.tradeModeSelect = this.form.tradeMode.split(',')
          } else {
            this.tradeModeSelect = []
          }
          if (this.form.bankId !== null && this.form.bankId !== '') {
            this.bankIdSelect = this.form.bankId.split(',')
          } else {
            this.bankIdSelect = []
          }
          this.open = true
          this.title = '修改项目信息'
        })
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            // 去除表单空格
            this.form.proNum = this.form.proNum.trim()
            this.form.proName = this.form.proName.trim()
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
        this.$confirm('是否确认删除该项目列数据?', '警告', {
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
      },
      // 时间线处理
      activities(rows) {
        let proTimeLine = []
        if (rows.insetallTime !== null && rows.insetallTime !== '') {
          proTimeLine.push({ 'content': '项目部署时间', 'timestamp': rows.insetallTime,'color': "#BAE7FF"})
        }
        if (rows.trainTime !== null && rows.trainTime !== '') {
          proTimeLine.push({ 'content': '项目培训时间', 'timestamp': rows.trainTime,'color': "#69C0FF" })
        }
        if(rows.businessTime !== null && rows.businessTime !== ''){
          proTimeLine.push({ 'content': '项目上线时间', 'timestamp': rows.businessTime,'color': "#1890FF" })
        }
        if(rows.acceptanceTime !== null && rows.acceptanceTime !== ''){
          proTimeLine.push({ 'content': '项目验收时间', 'timestamp': rows.acceptanceTime,'type': 'success' })
        }
        if(rows.terminationTime !== null && rows.terminationTime !== ''){
          proTimeLine.push({ 'content': '项目终止时间', 'timestamp': rows.terminationTime,'type': 'info' })
        }
        return proTimeLine
      }
    },
    computed: {},
    watch: {
      // 处理交易模式
      tradeModeFormat() {
        this.queryParams.tradeMode = this.tradeModeFormat.join(',')
      },
      // 处理银行接口
      bankIdFormat() {
        this.queryParams.bankId = this.bankIdFormat.join(',')
      },
      // 处理交易模式
      tradeModeSelect() {
        if (this.tradeModeSelect.length !== 0) {
          this.form.tradeMode = this.tradeModeSelect.join(',')
        }
      },
      // 处理银行接口
      bankIdSelect() {
        if (this.bankIdSelect.length !== 0) {
          this.form.bankId = this.bankIdSelect.join(',')
        }
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

  .timeline {
    margin-top: 20px;
  }
  .remark{
    padding-top: 20px;
  }
  .el-alert{
    font-weight: 700;
    color: #606266;
    background-color: #F0F2F5;
  }
</style>
