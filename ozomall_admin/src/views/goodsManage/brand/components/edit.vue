<template>
  <div class="modal">
    <div class="modal-header">
      <div slot="title" class="modal-header-title">
        {{ title }}
      </div>
    </div>
    <div class="modal-container">
      <div class="modal-container-form">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="品牌名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="品牌logo" prop="url">
            <el-upload
              class="avatar-uploader"
              action="/api/admin/goods/uploadGoodsBrand"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
            >
              <img v-if="ruleForm.url" :src="ruleForm.url" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
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
import { addGoodsBrand, putGoodsBrand } from "@/api/goodsManage";
export default {
  props: ["closeModal", "row"],
  data() {
    const self = this;
    return {
      title: "新建",
      ruleForm: {
        name: "",
        url: ""
      },
      rules: {
        name: [{ required: true, message: "请输入品牌名称", trigger: "blur" }],
        url: [{ required: true, message: "请上传品牌logo", trigger: "change" }]
      }
    };
  },
  computed: {
    uploadHeaders() {
      return {
        token: this.$store.state.user.token
      };
    }
  },
  mounted() {
    if (this.row.id) {
      this.title = "修改";
      this.ruleForm.name = this.row.name;
      this.ruleForm.url = this.row.url;
    }
  },
  methods: {
    // 上传成功后调用
    handleAvatarSuccess(res, file) {
      if (res.code === 1) {
        this.ruleForm.url = res.data.url;
      } else {
        this.$message.error(res.msg);
      }
    },
    // 提交请求
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.row.id) {
            // 修改
            putGoodsBrand({
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
            // 添加
            addGoodsBrand(this.ruleForm)
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
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
