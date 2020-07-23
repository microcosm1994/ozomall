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
        <el-form-item label="商品图片" prop="pics">
          <div class="goodsForm-form-item">
            <div class="item-title">
              tip：最好使用居中格式
            </div>
            <div class="item-container">
              <el-upload
                :headers="uploadHeaders"
                action="/api/admin/goods/upload"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :on-success="onSuccess"
                :file-list="fileList"
                :data="{
                  goodsId: goodsData.id
                }"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="" />
              </el-dialog>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="商品详情" prop="details">
          <div class="goodsForm-form-item">
            <div class="item-title">
              tip：最好使用居中格式
            </div>
            <div class="item-container">
              <Wangeditor />
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="goodsForm-btn">
      <el-button type="primary" @click="submitForm('ruleForm')">
        完成
      </el-button>
    </div>
  </div>
</template>

<script>
import Wangeditor from "@/components/Wangeditor";
import {
  getClassifyList,
  addGoods,
  getGoods,
  putGoods,
  getGoodsPic
} from "@/api/goodsManage";
export default {
  components: { Wangeditor },
  props: ["pageType", "goodsData", "prevStep", "nextStep", "updateGoods"],
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      fileList: [],
      ruleForm: {
        pics: "",
        details: ""
      },
      rules: {
        pics: [{ required: true, message: "请上传商品图片" }],
        details: [{ required: true, message: "请编辑商品详情" }]
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
      this.getGoodsPic();
      for (let key in this.ruleForm) {
        this.ruleForm[key] = this.goodsData[key];
      }
    }
  },
  methods: {
    // 获取商品图片
    getGoodsPic() {
      this.fileList = [];
      getGoodsPic({
        goodsId: this.goodsData.id
      })
        .then(res => {
          if (res.data.code === 1) {
            this.fileList = res.data.data;
          }
        })
        .catch(err => {});
    },
    // 删除时回调
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    // 上传文件成功回调
    onSuccess(response, file, fileList) {
      if (response.code === 1) {
        this.fileList.push(response.data);
      }
    },
    // 查看图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          putGoods({
            id: this.goodsData.id,
            ...this.ruleForm
          })
            .then(res => {
              if (res.data.code === 1) {
                this.nextStep();
                this.$message.success(res.data.msg);
              } else {
                this.$message.err(res.data.msg);
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
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};
</script>

<style scoped lang="less">
.goodsForm {
  width: 100%;
  padding-top: 20px;
  .goodsForm-form {
    width: 800px;
    margin: 0 auto;
    .el-tag + .el-tag {
      margin-left: 10px;
    }
    .button-new-tag {
      margin-left: 10px;
      height: 25px;
      line-height: 25px;
      padding-top: 0;
      padding-bottom: 0;
    }
    .input-new-tag {
      width: 90px;
      margin-left: 10px;
      vertical-align: bottom;
    }
    .goodsForm-form-item {
      width: 100%;
      background: #f5f5f5;
      margin-top: 5px;
      .item-title {
        width: 100%;
        padding-left: 10px;
      }
      .item-container {
        padding-bottom: 10px;
      }
    }
  }
  .goodsForm-btn {
    text-align: center;
  }
}
</style>
