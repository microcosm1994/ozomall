<template>
  <div class="modal">
    <div class="modal-header">
      <div slot="title" class="modal-header-title">
        新建
      </div>
    </div>
    <div class="modal-container">
      <div class="modal-container-form">
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="80px">
          <el-form-item label="分类名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
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
import { addClassify } from "@/api/classifyManage";
export default {
  props: ["closeModal"],
  data() {
    return {
      ruleForm: {
        name: ""
      },
      rules: {
        name: [
          { required: true, message: "请输入分类名称", trigger: "blur" },
          { min: 2, max: 5, message: "长度在 2 到 5 个字符", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addClassify(this.ruleForm)
            .then(res => {})
            .catch(err => {
              console.log(err);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../assets/css/modal.css";
</style>
