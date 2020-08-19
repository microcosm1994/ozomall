<template>
  <div class="modal">
    <div class="modal-header">
      <div class="modal-header-title">查看订单</div>
    </div>
    <div class="modal-container">
      <div class="modal-container-form">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="订单编号">
            {{ row.orderNo }}
          </el-form-item>
          <el-form-item label="物流公司">
            {{ row.deliveryCompany }}
          </el-form-item>
          <el-form-item label="物流单号" prop="deliveryNo">
            <el-input v-model="ruleForm.deliveryNo"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="modal-container-btn">
        <el-button type="primary" size="small" @click="onSubmit('ruleForm')">
          确认
        </el-button>
        <el-button size="small" @click="closeModal">
          取消
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { handle } from "@/api/orderManage";
export default {
  props: ["closeModal", "row"],
  data() {
    return {
      ruleForm: {
        deliveryNo: ""
      },
      rules: {
        deliveryNo: [
          { required: true, message: "请输入物流单号", trigger: "blur" }
        ]
      }
    };
  },
  mounted() {},
  methods: {
    // 提交请求
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          handle({
            id: this.row.id,
            ...this.ruleForm
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
          console.log("error submit!!");
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
