<template>
  <div class="classify">
    <div class="classify-header">
      <el-button type="primary" @click="dialogVisible = true">新建</el-button>
    </div>
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
              v-if="classifyList.level1.length === 0"
            >
              暂无数据
            </li>
            <li
              v-else
              class="classifyBox-item"
              v-for="item in classifyList.level1"
              :key="item.id"
              @click="getClassify(2, item.id)"
            >
              <div class="item-name">
                {{ item.name }}
              </div>
              <div class="item-icon">
                <i class="el-icon-arrow-right" />
              </div>
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
            <li
              class="classifyBox-item"
              v-if="classifyList.level2.length === 0"
            >
              暂无数据
            </li>
            <li
              v-else
              class="classifyBox-item"
              v-for="item in classifyList.level2"
              :key="item.id"
              @click="getClassify(3, item.id)"
            >
              <div class="item-name">
                {{ item.name }}
              </div>
              <div class="item-icon">
                <i class="el-icon-arrow-right" />
              </div>
            </li>
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
            <li
              class="classifyBox-item"
              v-if="classifyList.level3.length === 0"
            >
              暂无数据
            </li>
            <li
              v-else
              class="classifyBox-item"
              v-for="item in classifyList.level3"
              :key="item.id"
            >
              <div class="item-name">
                {{ item.name }}
              </div>
            </li>
          </ul>
        </div>
      </el-col>
    </el-row>
    <!-- 新建弹框 -->
    <el-dialog :visible.sync="dialogVisible" v-if="dialogVisible" width="400px">
      <Create :closeModal="closeModal" />
    </el-dialog>
  </div>
</template>

<script>
import Create from "./modal/create";
import { getClassifyList } from "@/api/goodsManage";
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
    this.getClassify(1, 0);
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    // 关闭弹框
    closeModal() {
      this.dialogVisible = false;
      this.getClassify(1, 0);
    },
    // 获取分类
    getClassify(level, parentId) {
      let data = {
        classifyLevel: level,
        parentId: parentId
      };
      this.classifyList["level" + level] = [];
      this.classifyList["level" + (level + 1)] = [];
      getClassifyList(data)
        .then(res => {
          if (res.data.code === 1) {
            this.classifyList["level" + level] = res.data.data;
            console.log(this.classifyList);
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
  .classify-header {
    width: 100%;
    padding: 20px;
    background: #fff;
    border-radius: 5px;
    border: 1px solid #e9e9e9;
    box-shadow: 0 0 5px #e2e2e2;
  }
  .classifyBox {
    width: 100%;
    background: #fff;
    padding: 0 20px 20px;
    border-radius: 5px;
    margin-top: 10px;
    border: 1px solid #e9e9e9;
    box-shadow: 0 0 5px #e2e2e2;
    .classifyBox-header {
      width: 100%;
      margin-bottom: 10px;
      .classifyBox-header-title {
        width: 100%;
        height: 60px;
        line-height: 60px;
        text-align: center;
        font-size: 18px;
        font-weight: 600;
        border-bottom: 1px solid #e9e9e9;
      }
    }
    .classifyBox-item {
      width: 100%;
      height: 40px;
      line-height: 40px;
      padding-left: 20px;
      cursor: pointer;
      font-size: 14px;
      .item-name {
        display: inline-block;
        vertical-align: middle;
      }
      .item-icon {
        display: inline-block;
        vertical-align: middle;
        float: right;
        right: 20px;
      }
    }
    .item-active {
      color: #1890ff;
      background: #f5f5f5;
    }
  }
}
</style>
