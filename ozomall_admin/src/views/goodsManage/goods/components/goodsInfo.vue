<template>
  <div class="goodsForm">
    <div class="goodsForm-form">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="商品名称" prop="goodsName">
          <el-input
            style="width:300px;"
            v-model="ruleForm.goodsName"
            placeholder="商品名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品分类" prop="classifyId">
          <el-cascader
            style="width:300px;"
            v-model="ruleForm.classifyId"
            :props="props"
            :filterable="true"
            ref="cascader"
            :placeholder="classifyPlaceholder"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="商品价格" prop="goodsName">
          <el-input
            style="width:300px;"
            v-model="ruleForm.goodsPrice"
            suffix-icon="el-icon-price-tag"
            placeholder="商品价格"
          ></el-input>
        </el-form-item>
        <el-form-item label="商品封面">
          <el-upload
            class="avatar-uploader"
            action="/api/admin/goods/upload"
            :headers="uploadHeaders"
            :data="{
              goodsId: 1
            }"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
    <div class="goodsForm-btn">
      <el-button type="primary" @click="submitForm('ruleForm')">
        下一步
      </el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </div>
  </div>
</template>

<script>
import {
  getClassifyList,
  addGoods,
  getGoods,
  putGoods
} from "@/api/goodsManage";
export default {
  props: ["pageType", "goodsData", "toStep", "updateGoods", "getGoods"],
  data() {
    return {
      imageUrl: "",
      classifyPlaceholder: "请选择分类",
      ruleForm: {
        goodsName: "",
        classifyId: "",
        goodsPrice: ""
      },
      rules: {
        goodsName: [
          { required: true, message: "请输入商品名称", trigger: "blur" }
        ],
        classifyId: [
          { required: true, message: "请选择商品分类", trigger: "change" }
        ],
        goodsPrice: [
          { required: true, message: "请输入商品价格", trigger: "blur" }
        ]
      },
      props: {
        lazy: true,
        emitPath: false,
        lazyLoad(node, resolve) {
          const { level, value } = node;
          getClassifyList({ classifyLevel: level + 1, parentId: value })
            .then(res => {
              if (res.data.code === 1) {
                const nodes = res.data.data.map(item => {
                  return {
                    value: item.id,
                    label: item.name,
                    leaf: level >= 2
                  };
                });
                // 通过调用resolve将子节点数据返回，通知组件数据加载完成
                resolve(nodes);
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
  },
  computed: {
    uploadHeaders() {
      console.log(this.$store);
      return {
        token: this.$store.state.user.token
      };
    }
  },
  mounted() {
    if (this.pageType) {
      this.classifyPlaceholder = this.goodsData.classify.name;
      for (let key in this.ruleForm) {
        this.ruleForm[key] = this.goodsData[key];
      }
    }
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.pageType === 0) {
            this.addGoods();
          } else {
            this.putGoods();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    // 添加
    addGoods() {
      addGoods({
        ...this.ruleForm
      })
        .then(res => {
          if (res.data.code === 1) {
            this.updateGoods(res.data.data);
            this.toStep(1);
            this.$message.success(res.data.msg);
          } else {
            this.$message.err(res.data.msg);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 修改
    putGoods() {
      if (!this.goodsData.id) {
        return false;
      }
      putGoods({
        id: this.goodsData.id,
        ...this.ruleForm
      })
        .then(res => {
          if (res.data.code === 1) {
            this.toStep(1);
            this.getGoods(this.goodsData.id);
            this.$message.success(res.data.msg);
          } else {
            this.$message.err(res.data.msg);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style scoped lang="less">
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
.goodsForm {
  width: 100%;
  padding-top: 20px;
  .goodsForm-form {
    width: 500px;
    margin: 0 auto;
  }
  .goodsForm-btn {
    text-align: center;
  }
}
</style>
