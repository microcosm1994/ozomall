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
        <el-form-item label="商品品牌" prop="brandId">
          <el-select
            v-model="ruleForm.brandId"
            placeholder="请选择"
            @change="onBrandChange"
          >
            <el-option
              v-for="item in brandList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
              <span style="float: left">
                <img style="height: 30px;" :src="item.url" alt="" />
              </span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{
                item.name
              }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品分类" prop="classifyId">
          <el-cascader
            style="width:300px;"
            v-model="ruleForm.classifyId"
            :props="props"
            :filterable="true"
            ref="cascader"
            placeholder="请选择"
            @change="onClasifyChange"
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
        <el-form-item label="商品封面" prop="cover">
          <el-upload
            class="avatar-uploader"
            action="/api/goods/uploadCover"
            :headers="uploadHeaders"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="ruleForm.cover" :src="ruleForm.cover" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
    <div class="goodsForm-btn">
      <el-button type="primary" @click="submitForm('ruleForm')">
        下一步
      </el-button>
      <el-button @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>
import {
  getClassifyList,
  addGoods,
  getGoods,
  putGoods,
  getGoodsBrand
} from "@/api/goodsManage";
export default {
  props: [
    "pageType",
    "goodsData",
    "toStep",
    "updateGoods",
    "getGoods",
    "cancel"
  ],
  data() {
    let self = this;
    return {
      imageUrl: "",
      classifyLevel1: [],
      classifyLevel2: [],
      classifyLevel3: [],
      ruleForm: {
        goodsName: "",
        brandId: "",
        brandName: "",
        classifyId: "",
        classify1Name: "",
        classify2Name: "",
        classify3Name: "",
        goodsPrice: "",
        cover: ""
      },
      rules: {
        goodsName: [
          { required: true, message: "请输入商品名称", trigger: "blur" }
        ],
        brandId: [
          { required: true, message: "请选择商品品牌", trigger: "change" }
        ],
        classifyId: [
          { required: true, message: "请选择商品分类", trigger: "change" }
        ],
        goodsPrice: [
          { required: true, message: "请输入商品价格", trigger: "blur" }
        ],
        cover: [
          { required: true, message: "请上传商品封面", trigger: "change" }
        ]
      },
      props: {
        lazy: true,
        emitPath: true,
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
                self.cacheClassifyList(level, nodes);
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
      },
      brandList: []
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
    this.getGoodsBrand();
    if (this.pageType) {
      this.ruleForm.classifyId = [
        this.goodsData.classify1Id,
        this.goodsData.classify2Id,
        this.goodsData.classify3Id
      ];
      this.ruleForm.goodsName = this.goodsData.goodsName;
      this.ruleForm.brandId = this.goodsData.brandId;
      this.ruleForm.brandName = this.goodsData.brandName;
      this.ruleForm.goodsPrice = this.goodsData.goodsPrice;
      this.ruleForm.cover = this.goodsData.cover;
      this.ruleForm.classify1Name = this.goodsData.classify1Name;
      this.ruleForm.classify2Name = this.goodsData.classify2Name;
      this.ruleForm.classify3Name = this.goodsData.classify3Name;
      console.log(this.ruleForm);
    }
  },
  methods: {
    // 获取品牌列表
    getGoodsBrand() {
      getGoodsBrand({
        page: 1,
        size: 1000
      })
        .then(res => {
          if (res.data.code === 1) {
            this.brandList = res.data.data.records;
          }
        })
        .catch(err => {});
    },
    // 选择品牌触发
    onBrandChange(val) {
      for (let i = 0; i < this.brandList.length; i++) {
        if (this.brandList[i].id === val) {
          this.ruleForm.brandName = this.brandList[i].name;
        }
      }
    },
    // 选择分类时触发
    onClasifyChange(val) {
      console.log(val);
      // 获取一级菜单名称
      for (let i = 0; i < this.classifyLevel1.length; i++) {
        if (this.classifyLevel1[i].value === val[0]) {
          this.ruleForm.classify1Name = this.classifyLevel1[i].label;
        }
      }
      // 获取二级菜单名称
      for (let i = 0; i < this.classifyLevel2.length; i++) {
        if (this.classifyLevel2[i].value === val[1]) {
          this.ruleForm.classify2Name = this.classifyLevel2[i].label;
        }
      }
      // 获取三级菜单名称
      for (let i = 0; i < this.classifyLevel3.length; i++) {
        if (this.classifyLevel3[i].value === val[2]) {
          this.ruleForm.classify3Name = this.classifyLevel3[i].label;
        }
      }
    },
    // 缓存分类列表
    cacheClassifyList(level, list) {
      // 一级分类
      if (level + 1 === 1) {
        this.classifyLevel1 = list;
      }
      // 二级分类
      if (level + 1 === 2) {
        this.classifyLevel2 = list;
      }
      // 三级分类
      if (level + 1 === 3) {
        this.classifyLevel3 = list;
      }
    },
    // 上传成功后调用
    handleAvatarSuccess(res, file) {
      if (res.code === 1) {
        this.ruleForm.cover = res.data.url;
      }
      // this.imageUrl = URL.createObjectURL(file.raw);
    },
    // 上传图片前调用
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
        ...this.ruleForm,
        classify1Id: this.ruleForm.classifyId[0],
        classify2Id: this.ruleForm.classifyId[1],
        classify3Id: this.ruleForm.classifyId[2]
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
        ...this.ruleForm,
        classify1Id: this.ruleForm.classifyId[0],
        classify2Id: this.ruleForm.classifyId[1],
        classify3Id: this.ruleForm.classifyId[2]
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
