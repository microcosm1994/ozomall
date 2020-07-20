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
        <el-form-item label="商品属性" prop="goodsName">
          <el-tag
            :key="tag.id"
            :hit="false"
            effect="dark"
            type="info"
            v-for="tag in attrDynamicTags"
            closable
            :disable-transitions="false"
            @close="attrRemoveTag(tag)"
            size="small"
          >
            {{ tag.name }}
          </el-tag>
          <el-input
            class="input-new-tag"
            v-if="attrInputVisible"
            v-model="attrInputValue"
            ref="saveTagInput"
            @keyup.enter.native="handleInputConfirm(attrInputValue)"
            @blur="handleInputConfirm(attrInputValue)"
            size="mini"
          >
          </el-input>
          <el-button
            v-else
            type="primary"
            class="button-new-tag"
            @click="attrInputVisible = true"
            size="mini"
            >添加属性</el-button
          >
        </el-form-item>
        <el-form-item label="商品规格" prop="classifyId">
          <div
            class="goodsForm-form-item"
            v-for="(item, index) in attrDynamicTags"
            :key="item.id"
          >
            <div class="item-title">{{ item.name }}</div>
            <div class="item-container">
              <el-tag
                :key="tag.id"
                effect="dark"
                type="success"
                v-for="tag in item.children"
                closable
                :disable-transitions="false"
                @close="speRemoveTag(tag)"
                size="small"
              >
                {{ tag.value }}
              </el-tag>
              <el-input
                class="input-new-tag"
                v-if="goodsAttrValOpt[index].inputVisible"
                v-model="goodsAttrValOpt[index].inputValue"
                ref="saveTagInput"
                @keyup.enter.native="
                  handleAttrVal(item.id, goodsAttrValOpt[index])
                "
                @blur="handleAttrVal(item.id, goodsAttrValOpt[index])"
                size="mini"
              >
              </el-input>
              <el-button
                v-else
                type="primary"
                class="button-new-tag"
                @click="goodsAttrValOpt[index].inputVisible = true"
                size="mini"
                >添加{{ item.name }}</el-button
              >
            </div>
          </div>
        </el-form-item>
        <el-form-item label="商品价格" prop="goodsName">
          <div class="goodsForm-form-item">
            <el-table :data="skuData" border style="width: 100%">
              <el-table-column
                v-for="item in attrDynamicTags"
                :key="item.id"
                :label="item.name"
                width="150"
              >
              </el-table-column>
              <el-table-column label="价格" width="120"> </el-table-column>
              <el-table-column label="库存" width="120"> </el-table-column>
              <el-table-column label="市区" width="120"> </el-table-column>
              <el-table-column label="地址" width="300"> </el-table-column>
              <el-table-column label="邮编" widtlh="120"> </el-table-column>
              <el-table-column
                align="center"
                fixed="right"
                label="操作"
                width="200"
              >
                <template slot-scope="scope">
                  <el-button
                    @click="handleClick(scope.row)"
                    type="text"
                    size="small"
                    >查看</el-button
                  >
                  <el-button type="text" size="small">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-form-item>
        <el-form-item label="商品参数" prop="goodsName">
          <div class="goodsForm-form-item">
            <el-table :data="skuData" border style="width: 100%">
              <el-table-column
                align="center"
                prop="date"
                label="参数名称"
                width="150"
              >
              </el-table-column>
              <el-table-column align="center" prop="name" label="参数值">
              </el-table-column>
              <el-table-column
                align="center"
                fixed="right"
                label="操作"
                width="200"
              >
                <template slot-scope="scope">
                  <el-button
                    @click="handleClick(scope.row)"
                    type="text"
                    size="small"
                    >查看</el-button
                  >
                  <el-button type="text" size="small">编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="goodsForm-btn">
      <el-button type="primary" @click="prevStep">
        上一步
      </el-button>
      <el-button type="primary" @click="submitForm('ruleForm')">
        下一步
      </el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </div>
  </div>
</template>

<script>
import {
  addGoodsAttr,
  getGoodsAttr,
  delGoodsAttr,
  addGoodsAttrVal,
  delGoodsAttrVal
} from "@/api/goodsManage";
export default {
  props: ["pageType", "goodsData", "prevStep", "nextStep"],
  data() {
    return {
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
      attrDynamicTags: [],
      speDynamicTags: ["标签一", "标签二", "标签三"],
      attrInputVisible: false,
      attrInputValue: "",
      goodsAttrValOpt: [],
      skuData: []
    };
  },
  mounted() {
    if (this.pageType) {
      this.getGoodsAttr();
    }
  },
  methods: {
    // 删除属性标签
    attrRemoveTag(tag) {
      console.log(tag);
      delGoodsAttr({ goodsId: tag.goodsId, name: tag.name })
        .then(res => {
          if (res.data.code === 1) {
            this.getGoodsAttr();
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 删除属性值标签
    speRemoveTag(tag) {
      delGoodsAttrVal({ goodsAttrId: tag.goodsAttrId, value: tag.value })
        .then(res => {
          if (res.data.code === 1) {
            this.getGoodsAttr();
          }
        })
        .catch(err => {
          console.log(err);
        });
      this.speDynamicTags.splice(this.speDynamicTags.indexOf(tag), 1);
    },
    // 添加属性
    handleInputConfirm(val) {
      if (val) {
        let data = {
          goodsId: this.goodsData.id,
          name: val
        };
        addGoodsAttr(data)
          .then(res => {
            if (res.data.code === 1) {
              this.getGoodsAttr();
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(err => {});
      }
      this.attrInputVisible = false;
      this.attrInputValue = "";
    },
    // 获取商品属性
    getGoodsAttr() {
      getGoodsAttr({ goodsId: this.goodsData.id })
        .then(res => {
          if (res.data.code === 1) {
            this.attrDynamicTags = res.data.data;
            this.goodsAttrValOpt = res.data.data.map(item => {
              return {
                inputVisible: false,
                inputValue: ""
              };
            });
          }
        })
        .catch(error => {});
    },
    // 添加属性值
    handleAttrVal(id, opt) {
      if (opt.inputValue) {
        let data = {
          goodsAttrId: id,
          value: opt.inputValue
        };
        addGoodsAttrVal(data)
          .then(res => {
            if (res.data.code === 1) {
              this.getGoodsAttr();
            } else {
              this.$message.error(res.data.msg);
            }
          })
          .catch(err => {});
      }
      opt.inputVisible = false;
      opt.inputValue = "";
    },
    // 提交表单
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // ...
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
  }
  .goodsForm-btn {
    text-align: center;
  }
}
</style>
