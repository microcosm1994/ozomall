<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
        <el-form-item label="角色名称">
          <el-input
            v-model="ruleForm.name"
            placeholder="角色名称"
            clearable
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="page-container">
      <div class="page-container-header">
        <el-button
          type="primary"
          icon="el-icon-refresh"
          @click="getData"
        ></el-button>
        <el-button type="primary" @click="dialogVisible = true"
          >添加角色</el-button
        >
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column
            align="center"
            prop="id"
            label="角色编号"
            width="100"
          >
          </el-table-column>
          <el-table-column align="center" label="角色名称" prop="name">
          </el-table-column>
          <el-table-column
            width="120"
            align="center"
            prop="address"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="openEdit(scope.row)"
                >编辑</el-button
              >
              <el-button type="text" size="small" @click="delGoods(scope)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog
      :visible.sync="dialogVisible"
      v-if="dialogVisible"
      width="400px"
    >
      <div class="modal">
        <div class="modal-header">
          <div slot="title" class="modal-header-title">
            {{modalTitle}}
          </div>
        </div>
        <div class="modal-container">
          <div class="modal-container-form">
            <el-form :model="form" :rules="rule" class="demo-form-inline" label-width="80px" ref="form">
              <el-form-item label="角色名称" prop="name">
                <el-input
                  v-model="form.name"
                  placeholder="角色名称"
                  clearable
                ></el-input>
              </el-form-item>
            </el-form>
          </div>
          <div class="modal-container-btn">
            <el-button type="primary" size="small" @click="submitForm('form')"
              >确定</el-button
            >
            <el-button size="small" @click="dialogVisible = false"
              >取消</el-button
            >
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getRole, addRole } from "@/api/userManage";
export default {
  data() {
    return {
      dialogVisible: false,
      ruleForm: {
        name: ""
      },
      form: {
        name: ""
      },
      rule: {
        name: [{ required: true, message: "请输入角色名称", trigger: "blur" }]
      },
      modalTitle: '新建',
      tableData: []
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    // 获取角色列表
    getData() {
      let data = {
        ...this.ruleForm
      };
      this.tableData = [];
      getRole(data)
        .then(res => {
          if (res.data.code === 1) {
            this.tableData = res.data.data;
          }
        })
        .catch(err => {});
    },
    // 跳转编辑页
    openEdit(row) {
      this.form.name = row.name;
      this.modalTitle = '编辑';
      this.dialogVisible = true;
    },
    // 删除角色
    delGoods(scope) {
      delGoods({
        id: scope.row.id
      })
        .then(res => {
          if (res.data.code === 1) {
            this.getData();
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {});
    },
    // 添加角色
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addRole({
            ...this.form
          })
            .then(res => {
              if (res.data.code === 1) {
                this.dialogVisible = false;
                this.getData();
              } else {
                this.$message.error(res.data.msg);
              }
            })
            .catch(err => {});
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../assets/css/page.css";
@import "../../../assets/css/modal.css";
</style>
