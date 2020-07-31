<template>
  <div class="modal">
    <div class="modal-header">
      <div slot="title" class="modal-header-title">{{ title }}</div>
    </div>
    <div class="modal-container">
      <div class="modal-container-form">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="用户昵称" prop="nickName">
            <el-input v-model="ruleForm.nickName"></el-input>
          </el-form-item>
          <el-form-item label="用户角色" prop="roleId">
            <el-select
              clearable
              v-model="ruleForm.roleId"
              placeholder="用户角色"
            >
              <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户账号" prop="userName">
            <el-input v-model="ruleForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="用户密码" prop="passWord">
            <el-input type="passWord" v-model="ruleForm.passWord"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="passWordCheck">
            <el-input type="passWord" v-model="ruleForm.passWordCheck"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="modal-container-btn">
        <el-button type="primary" @click="onSubmit('ruleForm')">确定</el-button>
        <el-button @click="closeModal">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getRole, addUser } from "@/api/userManage";
import cryptoMd5 from "crypto-js/md5";
export default {
  props: ["closeModal", "row"],
  data() {
    return {
      title: "添加用户",
      ruleForm: {
        nickName: "",
        roleId: "",
        userName: "",
        passWord: "",
        passWordCheck: ""
      },
      rules: {
        nickName: [
          { required: true, message: "请输入用户昵称", trigger: "blur" }
        ],
        roleId: [
          { required: true, message: "请选择用户角色", trigger: "change" }
        ],
        userName: [{ required: true, message: "请输入账号", trigger: "blur" }],
        passWord: [{ required: true, message: "请输入密码", trigger: "blur" }],
        passWordCheck: [
          { required: true, message: "请确认密码", trigger: "blur" }
        ]
      },
      roleList: [],
      options: []
    };
  },
  mounted() {
    this.getRole();
    if (this.row.id) {
      this.title = "编辑用户";
    }
  },
  methods: {
    // 获取分类
    getRole() {
      getRole()
        .then(res => {
          if (res.data.code === 1) {
            this.roleList = res.data.data;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {});
    },
    // 提交请求
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addUser({
            ...this.ruleForm,
            passWord: cryptoMd5(encodeURI(this.ruleForm.passWord)).toString()
          })
            .then(res => {
              if (res.data.code === 1) {
                this.closeModal();
                this.$message.success(res.data.msg);
              } else {
                this.$message.error(res.data.msg);
              }
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          return false;
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../../assets/css/modal.css";
</style>
