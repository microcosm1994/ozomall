<template>
  <div class="edit">
    <div class="edit-header">
      <div class="edit-header-title">
        {{ pageTitle }}
      </div>
    </div>
    <div class="edit-container">
      <div class="edit-container-header">
        <el-steps
          :active="activeStep"
          finish-status="success"
          simple
          align-center
        >
          <el-step title="填写商品信息"></el-step>
          <el-step title="添加商品规格"></el-step>
          <el-step title="编辑商品详情"></el-step>
          <el-step title="完成编辑"></el-step>
        </el-steps>
      </div>
      <div class="edit-container-content">
        <keep-alive>
          <component
            :pageType="pageType"
            v-bind:is="activeComponent"
            :goodsData="goodsData"
            :updateGoods="updateGoods"
            :toStep="toStep"
            :getGoods="getGoods"
            :cancel="cancel"
          ></component>
        </keep-alive>
      </div>
    </div>
  </div>
</template>

<script>
import { getClassifyList, getGoods } from "@/api/goodsManage";
import goodsInfo from "./components/goodsInfo";
import goodsAttr from "./components/goodsAttr";
import goodsPic from "./components/goodsPic";
import goodsDetail from "./components/goodsDetail";
export default {
  components: {
    goodsInfo,
    goodsAttr,
    goodsPic,
    goodsDetail
  },
  data() {
    return {
      pageType: 0, // 0新建，1编辑
      pageTitle: "添加商品",
      activeStep: 0,
      activeComponent: "",
      componentsName: ["goodsInfo", "goodsAttr", "goodsPic", "goodsDetail"],
      goodsData: null
    };
  },
  created() {
    if (this.$route.params.id) {
      this.pageType = 1;
      this.pageTitle = "编辑商品";
      this.getGoods(this.$route.params.id);
    } else {
      this.activeComponent = this.componentsName[this.activeStep];
    }
  },
  methods: {
    // 获取商品信息
    getGoods(id) {
      this.activeStep = 1;
      this.activeComponent = "";
      getGoods({ id })
        .then(res => {
          if (res.data.code === 1) {
            this.goodsData = res.data.data;
            this.activeStep = this.goodsData.step + 1;
            this.activeComponent =
              this.activeStep < 4
                ? this.componentsName[this.activeStep]
                : this.componentsName[this.activeStep - 1];
          }
        })
        .catch(err => {});
    },
    // 上一步
    toStep(step) {
      this.activeComponent = this.componentsName[step];
    },
    // 更新商品数据
    updateGoods(data) {
      this.goodsData = data;
    },
    // 返回商品管理页
    cancel(){
      this.$router.push("/goodsManage/goods")
    }
  }
};
</script>

<style scoped lang="less">
.edit {
  width: 100%;
  padding-top: 20px;
  .edit-header {
    width: 100%;
    background: #fff;
    padding: 20px;
    border: 1px solid #e4e4e4;
    box-shadow: 0 0 5px #e2e2e2;
    .edit-header-title {
      width: 100%;
      text-align: center;
      font-size: 18px;
      font-weight: 600;
    }
  }
  .edit-container {
    width: 100%;
    background: #fff;
    padding: 20px;
    border: 1px solid #e4e4e4;
    box-shadow: 0 0 5px #e2e2e2;
    margin-top: 10px;
  }
}
</style>
