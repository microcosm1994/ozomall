<template>
  <div class="classify">
    <el-row :gutter="4">
      <el-col :span="8">
        <div class="classifyBox">
          <div class="classifyBox-header">
            <div class="classifyBox-header-title">
              一级分类
            </div>
          </div>
          <ul>
            <li
              class="classifyBox-item"
              v-for="item in classifyList.level1"
              :key="item.id"
            >
              {{ item.name }}
            </li>
          </ul>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="classifyBox">
          <div class="classifyBox-header">
            <div class="classifyBox-header-title">
              二级分类
            </div>
          </div>
          <ul>
            <li class="classifyBox-item"></li>
          </ul>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="classifyBox">
          <div class="classifyBox-header">
            <div class="classifyBox-header-title">
              三级分类
            </div>
          </div>
          <ul>
            <li class="classifyBox-item"></li>
          </ul>
        </div>
      </el-col>
    </el-row>
    <!-- 新建弹框 -->
    <el-dialog
      :visible.sync="dialogVisible"
      :v-if="dialogVisible"
      width="400px"
    >
      <Create :closeModal="closeModal" />
    </el-dialog>
  </div>
</template>

<script>
import Create from "./modal/create";
import { getClassifyList } from "@/api/classifyManage";
export default {
  components: {
    Create
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        name: "",
        type: ""
      },
      classifyList: {
        level1: [],
        level2: [],
        level3: []
      }
    };
  },
  mounted() {
    this.getClassify(1);
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    // 关闭弹框
    closeModal() {
      this.dialogVisible = false;
    },
    // 获取一级分类
    getClassify(level) {
      let data = {
        classifyLevel: level
      };
      getClassifyList(data)
        .then(res => {
          if (res.data.code === 1) {
            this.classifyList["level" + level] = res.data.data;
            console.log(this.classifyList);
            this.$message.success(res.data.msg);
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    handleSizeChange() {
      this.getData();
    },
    handleCurrentChange() {
      this.getData();
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../assets/css/page.css";
.classify {
  padding-top: 20px;
  .classifyBox {
    width: 100%;
    background: #fff;
    padding: 0 20px 20px;
    border-radius: 5px;
    .classifyBox-header {
      width: 100%;
      .classifyBox-header-title {
        width: 100%;
        height: 60px;
        line-height: 60px;
        text-align: center;
        font-size: 18px;
        font-weight: 600;
        border-bottom: 1px solid #e2e2e2;
      }
    }
    .classifyBox-item {
      width: 100%;
      height: 40px;
      line-height: 40px;
      border-bottom: 1px solid #e2e2e2;
      background: #f5f5f5;
      padding-left: 20px;
      cursor: pointer;
    }
  }
}
</style>
