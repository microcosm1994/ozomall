<template>
  <div class="modal">
    <div class="modal-header">
      <div slot="title" class="modal-header-title">{{ title }}</div>
    </div>
    <div class="modal-container">
      <div class="modal-container-form">
        <el-form
          :status-icon="true"
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
          <el-form-item v-if="!row" label="用户密码" prop="passWord">
            <el-input type="passWord" v-model="ruleForm.passWord"></el-input>
          </el-form-item>
          <el-form-item v-if="!row" label="确认密码" prop="passWordCheck">
            <el-input
              type="passWord"
              v-model="ruleForm.passWordCheck"
            ></el-input>
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
import { addUser, putUser } from "@/api/sysManage";
import cryptoMd5 from "crypto-js/md5";

export default {
  props: ["closeModal", "row", "roleList"],
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.passWord) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
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
        passWord: [{ validator: validatePass, trigger: "blur" }],
        passWordCheck: [{ validator: validatePass2, trigger: "blur" }]
      },
      options: []
    };
  },
  mounted() {
    if (this.row) {
      this.title = "编辑用户";
      this.ruleForm.nickName = this.row.nickName;
      this.ruleForm.userName = this.row.userName;
      this.ruleForm.roleId = this.row.roleId;
    }
  },
  methods: {
    // 提交请求
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.row) {
            this.putUser();
          } else {
            this.addUser();
          }
        } else {
          return false;
        }
      });
    },
    // 添加用户
    addUser() {
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
    },
    // 修改用户
    putUser() {
      putUser({
        id: this.row.id,
        nickName: this.ruleForm.nickName,
        roleId: this.ruleForm.roleId,
        userName: this.ruleForm.userName
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
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../../assets/css/modal.css";
</style>
