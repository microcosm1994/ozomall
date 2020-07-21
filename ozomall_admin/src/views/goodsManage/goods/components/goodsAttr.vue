<template>
  <div class="goodsForm">
    <div class="goodsForm-form">
      <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="商品属性">
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
            @click="openAttrinput"
            size="mini"
            >添加属性</el-button
          >
        </el-form-item>
        <el-form-item label="商品规格">
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
        <el-form-item label="商品价格">
          <div class="goodsForm-form-item">
            <div class="item-header">
              <el-button type="text" size="small" @click="addSkuItem"
                >添加价格
              </el-button>
            </div>
            <el-table :data="skuData" border style="width: 100%">
              <el-table-column
                v-for="(item, index) in attrDynamicTags"
                :key="item.id"
                :label="item.name"
                width="150"
                align="center"
              >
                <template slot-scope="scope">
                  <el-select
                    :disabled="scope.row.isDisabled"
                    v-model="
                      skuData[scope.$index]['spe' + (index - 0 + 1) + 'Id']
                    "
                    :placeholder="item.name"
                  >
                    <el-option
                      v-for="opt in item.children"
                      :key="opt.id"
                      :label="opt.value"
                      :value="opt.id"
                    >
                    </el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column align="center" label="价格" width="120">
                <template slot-scope="scope">
                  <el-input
                    :disabled="scope.row.isDisabled"
                    v-model="skuData[scope.$index].price"
                    placeholder="请输入价格"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" label="库存" width="120">
                <template slot-scope="scope">
                  <el-input
                    :disabled="scope.row.isDisabled"
                    v-model="skuData[scope.$index].stock"
                    placeholder="请输入库存"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed="right" label="操作">
                <template slot-scope="scope">
                  <el-button
                    v-if="!scope.row.isDisabled"
                    type="text"
                    size="small"
                    @click="saveSku(scope)"
                    >保存</el-button
                  >
                  <el-button
                    v-if="!scope.row.isDisabled"
                    type="text"
                    size="small"
                    @click="cancelSku(scope)"
                    >取消</el-button
                  >
                  <el-button
                    v-else
                    type="text"
                    size="small"
                    @click="skuEdit(scope)"
                    >编辑</el-button
                  >
                  <el-button type="text" size="small">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-form-item>
        <el-form-item label="商品参数">
          <div class="goodsForm-form-item">
            <el-table :data="paramsData" border style="width: 100%">
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
  delGoodsAttrVal,
  addGoodsSku,
  getGoodsSkuList
} from "@/api/goodsManage";
export default {
  props: ["pageType", "goodsData", "prevStep", "nextStep"],
  data() {
    return {
      attrDynamicTags: [],
      speDynamicTags: [],
      attrInputVisible: false,
      attrInputValue: "",
      goodsAttrValOpt: [],
      paramsData: [],
      skuData: [
        {
          spe1Id: null, // 属性1
          spe2Id: null, // 属性2
          spe3Id: null, // 属性3
          price: "", // 价格
          stock: "", // 库存
          isDisabled: false
        }
      ]
    };
  },
  mounted() {
    if (this.pageType) {
      this.getGoodsAttr();
      this.getGoodsSkuList();
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
    // 打开属性输入框
    openAttrinput() {
      if (this.attrDynamicTags.length < 3) {
        this.attrInputVisible = true;
      } else {
        this.$message.info("最多只能添加3个属性");
      }
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
    // 添加sku
    addSkuItem() {
      let prev = this.skuData[this.skuData.length - 1]
      if (!prev['id']) {
        this.$message.info('请保存之后再继续添加。')
        return false
      }
      this.skuData.push({
        spe1Id: "", // 属性1
        spe2Id: "", // 属性2
        spe3Id: "", // 属性3
        price: "", // 价格
        stock: "", // 库存
        isDisabled: false // 编辑状态
      });
    },
    // 获取sku列表
    getGoodsSkuList() {
      this.skuData = [];
      getGoodsSkuList({ goodsId: this.goodsData.id })
        .then(res => {
          if (res.data.code === 1) {
            console.log(res);
            this.skuData = res.data.data.map(item => {
              return {
                ...item,
                isDisabled: true // 编辑状态
              };
            });
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 保存sku
    saveSku(scope) {
      console.log(scope);
      let len = this.attrDynamicTags.length;
      for (let i = 0; i < this.attrDynamicTags.length; i++) {
        if (!scope.row["spe" + (i + 1) + "Id"]) {
          this.$message.error("请选择关联属性");
          return false;
        }
      }
      if (!scope.row.price || !(scope.row.price - 0)) {
        this.$message.error("请输入价格,必须是数字。");
        return false;
      }
      if (!scope.row.stock || !(scope.row.stock - 0)) {
        this.$message.error("请输入库存，必须是数字。");
        return false;
      }
      let data = {
        goodsId: this.goodsData.id,
        spe1Id: scope.row.spe1Id,
        spe2Id: scope.row.spe2Id,
        spe3Id: scope.row.spe3Id,
        price: scope.row.price - 0,
        stock: scope.row.stock - 0
      };
      if (scope.row.id) {
        // 修改
      } else {
        // 新增
        addGoodsSku(data)
          .then(res => {
            if (res.data.code === 1) {
              this.getGoodsSkuList();
            }
          })
          .catch(err => {});
      }
    },
    // 取消编辑sku
    cancelSku(scope){
      if (scope.row.id) {
        scope.row.isDisabled = true
      } else {
        this.skuData.splice(scope.$index, 1)
      }
    },
    // 编辑sku
    skuEdit(scope) {
      scope.row.isDisabled = false
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
