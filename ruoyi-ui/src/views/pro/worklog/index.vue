<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="项目编号" prop="pronum">
        <el-select v-model="queryParams.proNum" placeholder="请选择项目编号" filterable clearable>
          <el-option
            v-for="(item,index) in proInfoDict"
            :key="index"
            :value="item.proNum">
            <span style="float: left">{{ item.proNum }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.proName }}</span>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="上传用户" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入上传用户"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传时间">
        <el-date-picker clearable size="small" style="width: 240px"
                        v-model="dateRange"
                        type="daterange"
                        value-format="yyyy-MM-dd"
                        range-separator="-"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期">
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
          type="success"
          icon="el-icon-upload2"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['pro:worklog:add']"
        >日报上传
        </el-button>
      </el-col>
      <!--<el-col :span="1.5">-->
      <!--  <el-button-->
      <!--    type="primary"-->
      <!--    icon="el-icon-edit"-->
      <!--    size="mini"-->
      <!--    :disabled="single"-->
      <!--    @click="handleUpdate"-->
      <!--    v-hasPermi="['pro:worklog:edit']"-->
      <!--  >修改-->
      <!--  </el-button>-->
      <!--</el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['pro:worklog:remove']"
        >删除
        </el-button>
      </el-col>
      <!--<el-col :span="1.5">-->
      <!--  <el-button-->
      <!--    type="primary"-->
      <!--    icon="el-icon-download"-->
      <!--    size="mini"-->
      <!--    @click="handleExport"-->
      <!--    v-hasPermi="['pro:worklog:export']"-->
      <!--  >导出-->
      <!--  </el-button>-->
      <!--</el-col>-->
    </el-row>

    <el-table v-loading="loading" border :data="worklogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column type="index" label="序列" width="70" align="center"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{  parseTime(scope.row.createTime)  }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传用户" align="center" prop="createBy" width="160"/>
      <el-table-column label="日报问题描述" align="center" prop="worklogTitle"/>
      <el-table-column label="项目名称" align="center" prop="proNum">
        <template slot-scope="scope">
          <span>{{ getFullProName(scope.row.proNum)  }}</span>
        </template>
      </el-table-column>
      <el-table-column label="附件路径" align="center" prop="fileurl"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!--<el-button-->
          <!--  size="mini"-->
          <!--  type="text"-->
          <!--  icon="el-icon-download"-->
          <!--  @click="handleUpdate(scope.row)"-->
          <!--  v-hasPermi="['pro:worklog:edit']"-->
          <!--&gt;修改-->
          <!--</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pro:worklog:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handDownload(scope.row)"
            v-hasPermi="['pro:worklog:export']"
          >下载
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

    <!-- 添加或修改日报对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="问题描述" prop="worklogTitle">
          <el-input v-model="form.worklogTitle" placeholder="请输入日报标题"/>
        </el-form-item>
        <el-form-item label="项目名称" prop="proNum">
          <el-select v-model="form.proNum" placeholder="请选择" filterable>
            <el-option
              v-for="(item,index) in proInfoDict"
              :key="index"
              :label="item.proName"
              :value="item.proNum">
              <span style="float: left">{{ item.proNum }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.proName }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日报附件">
          <el-upload
            class="upload-demo"
            :limit="1"
            accept=".xlsx,.xls,.doc,.rtf,.docx,.wps"
            drag
            :headers="upload.headers"
            :action="upload.url"
            :disabled="upload.isUploading"
            :multiple="false"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
            ref="my-upload"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>

            <div class="el-upload__tip" slot="tip">
              <el-alert
                title="请确保上传文件正确！"
                type="warning"
                :closable="false"
                show-icon>
              </el-alert>
            </div>
          </el-upload>
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
  import {
    listWorklog,
    delWorklog,
    addWorklog,
    updateWorklog,
    exportWorklog
  } from '@/api/pro/worklog'
  import { getProdict } from '@/api/pro/proinfo'
  import { getToken } from '@/utils/auth'
  import { downLoadFile } from '../../../utils/zipdownload'

  export default {
    data() {
      return {
        // 项目名称字典
        proInfoDict: [],
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
        // 日报表格数据
        worklogList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          worklogTitle: undefined,
          fileurl: undefined,
          pronum: undefined
        },
        // 表单参数
        form: {},
        // 导入日报参数
        upload: {
          // 是否禁用上传
          isUploading: false,
          // 设置上传的请求头部
          headers: { Authorization: 'Bearer ' + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + '/common/upload'
        },
        // 日期范围
        dateRange: [],
        // 表单校验
        rules: {
          worklogTitle: [
            { required: true, message: '日报标题不能为空', trigger: 'blur' }
          ],
          proNum: [
            { required: true, message: '项目编号不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.getProDict()
      this.getList()
    },
    methods: {
      /** 查询日报列表 */
      getList() {
        this.loading = true
        listWorklog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.worklogList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
        this.clearFiles()
      },
      // 表单重置
      reset() {
        this.form = {
          worklogId: undefined,
          worklogTitle: undefined,
          fileurl: undefined,
          proNum: undefined
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
        this.ids = selection.map(item => item.worklogId)
        this.single = selection.length != 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加日报'
      },
      /** 修改按钮操作 */
      // handleUpdate(row) {
      //   this.reset()
      //   const worklogId = row.worklogId || this.ids
      //   getWorklog(worklogId).then(response => {
      //     this.form = response.data
      //     this.open = true
      //     this.title = '修改日报'
      //   })
      // },
      /* 下载文件按钮*/
      handDownload(row) {
        const url = row.fileurl
        downLoadFile('/pro/worklog/downFile?fileurl=' + url, this.getFullProName(row.proNum), row)
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (this.form.fileurl) {
              if (this.form.worklogId != undefined) {
                updateWorklog(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess('修改成功')
                    this.open = false
                    this.getList()
                  } else {
                    this.msgError(response.msg)
                  }
                })
              } else {
                addWorklog(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess('新增成功')
                    this.open = false
                    this.getList()
                  } else {
                    this.msgError(response.msg)
                  }
                })
              }
            } else {
              this.$message.error('日报文档未上传!')
              return false
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const worklogIds = row.worklogId || this.ids
        this.$confirm('是否确认删除该日报数据项?', '警告', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function() {
          return delWorklog(worklogIds)
        }).then(() => {
          this.getList()
          this.msgSuccess('删除成功')
        }).catch(function() {
        })
      },
      /** 导出按钮操作 */
      // handleExport() {
      //   const queryParams = this.queryParams
      //   this.$confirm('是否确认导出所有日报数据项?', '警告', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     type: 'warning'
      //   }).then(function() {
      //     return exportWorklog(queryParams)
      //   }).then(response => {
      //     this.download(response.msg)
      //   }).catch(function() {
      //   })
      // },
      /** 获取所有项目名称*/
      getProDict() {
        getProdict().then(responce => {
          this.proInfoDict = responce.data
        })
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        // 文件路径封装到form里
        this.form.fileurl = response.fileName
        this.upload.isUploading = false
        this.$refs.upload.clearFiles()
      },
      // 清空上传的文件
      clearFiles() {
        this.$refs['my-upload'].clearFiles()
      },
      // 获取项目全称
      getFullProName(proNum) {
        try {
          let proInfo = this.proInfoDict.filter(pro => {
            return pro.proNum === proNum
          })
          return proNum + proInfo[0].proName
        } catch (e) {

        }
      }
    },
    computed: {}
  }
</script>
