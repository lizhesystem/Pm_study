<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="问题标题" prop="knowledgeTitle">
        <el-input
          v-model="queryParams.knowledgeTitle"
          placeholder="请输入问题标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="市场编号" prop="proNum">
        <el-input
          v-model="queryParams.proNum"
          placeholder="请输入市场编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="知识库状态" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="交易模式" prop="tradeMode">
        <el-select
          v-model="queryParams.tradeMode"
          placeholder="请输入交易模式"
          size="small"
          clearable
          filterable
        >
          <el-option
            v-for="dict in tradeModeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['pro:knowledge:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['pro:knowledge:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:notice:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="knowledgeList" border  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="55" />
      <el-table-column
        label="问题标题"
        align="left"
        prop="knowledgeTitle"
        width="500"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <router-link :to="'/rep/knowledge/data/' + scope.row.knowledgeId" class="link-type">
            <span>{{ scope.row.knowledgeTitle }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="市场编号" align="center" width="80" prop="proDict.proNum" />
      <el-table-column label="项目名称" align="center" width="150" prop="proDict.proName" />
      <el-table-column label="交易模式" align="center" width="80" prop="tradeMode" />

      <!--<el-table-column-->
      <!--  label="问题类型"-->
      <!--  align="center"-->
      <!--  prop="status"-->
      <!--  :formatter="typeFormat"-->
      <!--  width="100"-->
      <!--/>-->
      <el-table-column
        label="状态"
        align="center"
        width="80"
      >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === '1'" type="success">正常</el-tag>
            <el-tag v-else  type="warning">已过期</el-tag>
          </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" width="100" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['pro:knowledge:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['pro:knowledge:remove']"
          >删除</el-button>
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

    <!-- 添加或修改知识库对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="问题描述" prop="knowledgeTitle">
              <el-input v-model="form.knowledgeTitle" placeholder="请输入问题描述" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="项目编号" prop="proNum">
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
          </el-col>
          <el-col :span="10">
            <el-form-item label="交易模式" prop="tradeMode">
              <el-select width="200px" v-model="form.tradeMode" placeholder="请选择交易模式"
                         clearable
                         filterable
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
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="内容">
              <Editor v-model="form.knowledgeContent" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="padding-top:20px">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  import { listKnowledge, getKnowledge, delKnowledge, addKnowledge, updateKnowledge } from "@/api/pro/knowledge";
  import { getProdict } from '@/api/pro/proinfo';
   import Editor from '@/components/Editor';

export default {
  components: {
    Editor
  },
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
      // 知识库数据
      knowledgeList: [],
      // 交易模式字典
      tradeModeOptions:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型数据字典
      statusOptions: [],
      // 日报状态数据字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        knowledgeTitle: undefined,
        proNum: undefined,
        tradeMode: undefined,
        createBy: undefined,
        status: undefined
      },
      // 表单参数
      form: {
        status:"1"
      },
      // 表单校验
      rules: {
        knowledgeTitle: [
          { required: true, message: "问题描述不能为空", trigger: "blur" }
        ],
        proNum: [
          { required: true, message: "项目编号不能为空", trigger: "blur" }
        ],
        tradeMode: [
          { required: true, message: "交易模式不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getProDict()
    this.getList();
    // 获取交易模式字典
    this.getDicts('pro_trade_mode').then(response => {
      this.tradeModeOptions = response.data
    })
    this.getDicts("sys_knowledge_status").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询知识库列表 */
    getList() {
      this.loading = true;
      listKnowledge(this.queryParams).then(response => {
        this.knowledgeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        knowledgeId: undefined,
        knowledgeTitle: undefined,
        knowledgeContent: undefined,
        proNum:undefined,
        tradeMode:undefined,
        status: "0",
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.knowledgeId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增知识库数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const knowledgeId = row.knowledgeId || this.ids
      getKnowledge(knowledgeId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改知识库数据";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.knowledgeId != undefined) {
            updateKnowledge(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addKnowledge(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const knowledgeId = row.knowledgeId || this.ids
      this.$confirm('是否确认删除该数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delKnowledge(knowledgeId);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 获取所有项目名称*/
    getProDict() {
      getProdict().then(responce => {
        this.proInfoDict = responce.data
      })
    }
  },
  watch: {
    // 处理交易模式
    // 处理交易模式
  }
};
</script>
