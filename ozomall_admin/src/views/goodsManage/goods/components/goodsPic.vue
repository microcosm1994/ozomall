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
            <div class="item-title"></div>
            <div class="item-container">
              <el-upload
                name="file"
                :headers="uploadHeaders"
                action="/api/admin/goods/upload"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-success="onSuccess"
                :on-remove="onRemove"
                :before-remove="handleBeforeRemove"
                :file-list="fileList"
                :data="{
                  goodsId: goodsData.id
                }"
                :limit="10"
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
              <Wangeditor ref="wangeditor" :config="wangeditorConfig" />
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="goodsForm-btn">
      <el-button type="primary" @click="toStep(1)">
        上一步
      </el-button>
      <el-button type="primary" @click="submitForm('ruleForm')">
        下一步
      </el-button>
      <el-button @click="cancel">
        取消
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
  getGoodsPic,
  delGoodsPic
} from "@/api/goodsManage";
export default {
  components: { Wangeditor },
  props: ["pageType", "goodsData", "toStep", "getGoods", "cancel"],
  data() {
    return {
      dialogImageUrl: "",
      dialogVisible: false,
      fileList: [],
      wangeditorConfig: {},
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
      this.$refs.wangeditor.editor.txt.html(this.goodsData.details);
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
    // 上传成功回调
    onSuccess(res, file, fileList) {
      if (res.code === 1) {
        fileList[fileList.length - 1]["id"] = res.data.id;
        fileList[fileList.length - 1]["name"] = res.data.name;
        this.fileList = fileList;
      }
    },
    // 删除回调
    onRemove(file, fileList) {
      console.log(fileList);
      this.fileList = fileList;
    },
    // 删除前回调
    handleBeforeRemove(file) {
      return new Promise((reslove, reject) => {
        delGoodsPic({
          id: file.id,
          name: file.name
        })
          .then(res => {
            if (res.data.code === 1) {
              reslove();
              this.$message.success(res.data.msg);
            } else {
              this.$message.error(res.data.msg);
              reject();
            }
          })
          .catch(err => {
            this.$message.error("服务器错误");
            reject();
          });
      });
    },
    // 查看图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 提交表单
    submitForm(formName) {
      if (this.fileList.length > 0) {
        this.ruleForm.pics = true;
      } else {
        this.ruleForm.pics = "";
      }
      let html = this.$refs.wangeditor.editor.txt.html();

      if (html && html !== "<p><br></p>") {
        this.ruleForm.details = true;
      } else {
        this.ruleForm.details = "";
      }
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log(this.goodsData.details === html);
          console.log(this.goodsData.details);
          console.log(html);
          if (this.goodsData.details === html && this.goodsData.step > 2) {
            this.toStep(this.goodsData.step);
          } else {
            putGoods({
              id: this.goodsData.id,
              details: html,
              step: this.goodsData.step < 2 ? 2 : this.goodsData.step
            })
              .then(res => {
                if (res.data.code === 1) {
                  this.toStep(3);
                  this.getGoods(this.goodsData.id);
                  this.$message.success(res.data.msg);
                } else {
                  this.$message.err(res.data.msg);
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
    width: 90%;
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
