<template>
  <div class="goodsForm">
    <div class="goodsForm-form">
      <div class="goodsForm-form-item">
        <div class="item-title">
          <el-button type="text" @click="submitForm('ruleForm')" size="small">
            修改基本信息
          </el-button>
        </div>
        <div class="item-container">
          <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="商品名称：">
              {{ goodsData.goodsName }}
            </el-form-item>
            <el-form-item label="所属类别：">
              {{ goodsData.classify1.name }} / {{goodsData.classify2.name}} / {{goodsData.classify3.name}}
            </el-form-item>
            <el-form-item label="商品价格：">
              ￥{{ goodsData.goodsPrice }}
            </el-form-item>
            <el-form-item label="商品封面：">
              <el-image
                style="width: 100px; height: 100px"
                :src="goodsData.cover"
                fit="cover"
              ></el-image>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div class="goodsForm-form-item">
        <div class="item-title">
          <el-button type="text" @click="submitForm('ruleForm')" size="small">
            修改商品属性
          </el-button>
        </div>
        <div class="item-container">
          <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="商品规格：">
              <el-table :data="skuData" border style="width: 100%">
                <el-table-column align="center" label="属性1" prop="spe1Id">
                </el-table-column>
                <el-table-column align="center" label="属性2" prop="spe2Id">
                </el-table-column>
                <el-table-column align="center" label="属性3" prop="spe3Id">
                </el-table-column>
                <el-table-column align="center" label="价格" prop="price">
                </el-table-column>
                <el-table-column align="center" label="库存" prop="stock">
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div class="goodsForm-form-item">
        <div class="item-title">
          <el-button type="text" @click="submitForm('ruleForm')" size="small">
            修改商品详情
          </el-button>
        </div>
        <div class="item-container">
          <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="商品图片：">
              <el-image
                v-for="item in fileList"
                :key="item.id"
                style="width: 100px; height: 100px"
                :src="item.url"
                :preview-src-list="srcList"
              >
              </el-image>
            </el-form-item>
            <el-form-item label="商品详情：">
              <div v-html="goodsData.details"></div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
    <div class="goodsForm-btn">
      <el-button type="primary" @click="toStep(2)">
        上一步
      </el-button>
      <el-button type="primary" @click="submitForm">
        完成
      </el-button>
    </div>
  </div>
</template>

<script>
import {
  getClassifyList,
  addGoods,
  getGoods,
  putGoods,
  getGoodsSkuList,
  getGoodsPic
} from "@/api/goodsManage";
export default {
  props: ["pageType", "goodsData", "toStep", "updateGoods"],
  data() {
    return {
      skuData: [],
      srcList: [],
      fileList: []
    };
  },
  mounted() {
    this.getGoodsSkuList();
    this.getGoodsPic();
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
            this.srcList = res.data.data.map(item => {
              return item.url;
            });
          }
        })
        .catch(err => {});
    },
    // 获取sku列表
    getGoodsSkuList() {
      this.skuData = [];
      getGoodsSkuList({ goodsId: this.goodsData.id })
        .then(res => {
          if (res.data.code === 1) {
            console.log(res);
            this.skuData = res.data.data;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 提交表单
    submitForm() {
      if (this.goodsData.step < 3) {
        putGoods({
          id: this.goodsData.id,
          step: 3
        })
          .then(res => {
            if (res.data.code === 1) {
              this.$message.success(res.data.msg);
            } else {
              this.$message.err(res.data.msg);
            }
          })
          .catch(err => {
            console.log(err);
          });
      }
      this.$router.push("/goodsManage/goods");
    }
  }
};
</script>

<style scoped lang="less">
.goodsForm {
  width: 100%;
  padding-top: 20px;
  .goodsForm-form {
    width: 80%;
    margin: 0 auto;
    .goodsForm-form-item {
      width: 100%;
      background: #f5f5f5;
      margin-top: 5px;
      .item-title {
        width: 100%;
        padding-left: 10px;
        text-align: right;
        padding-right: 20px;
      }
      .item-container {
        padding-bottom: 10px;
      }
    }
  }
  .goodsForm-btn {
    margin-top: 10px;
    text-align: center;
  }
}
</style>
