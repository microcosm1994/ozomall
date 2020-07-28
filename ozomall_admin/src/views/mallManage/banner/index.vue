<template>
  <div class="page">
    <div class="page-container">
      <div class="page-container-header">
        <el-button
          type="primary"
          icon="el-icon-refresh"
          @click="getData"
        ></el-button>
        <el-button type="primary" @click="openEdit">添加banner</el-button>
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column align="center" prop="id" label="编号" width="70">
          </el-table-column>
          <el-table-column align="center" prop="url" label="图片" width="180">
            <template slot-scope="scope">
              <el-image
                style="width: 100px;"
                :src="scope.row.url"
                :preview-src-list="[scope.row.url]"
              >
              </el-image>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="link"
            label="跳转链接"
          ></el-table-column>
          <el-table-column
            width="120"
            align="center"
            prop="address"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="openEdit(scope.row)"
                >编辑</el-button
              >
              <el-button type="text" size="small" @click="del(scope)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- 编辑弹框 -->
    <el-dialog v-if="dialogVisible" :visible.sync="dialogVisible" width="500px">
      <Edit :closeModal="closeModal" :row="row" />
    </el-dialog>
  </div>
</template>

<script>
import { getBanner, delBanner } from "@/api/mallManage";
import Edit from "./components/edit";
export default {
  components: {
    Edit
  },
  data() {
    return {
      row: null,
      dialogVisible: false,
      tableData: []
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    // 获取商品列表
    getData() {
      this.tableData = [];
      getBanner()
        .then(res => {
          if (res.data.code === 1) {
            this.tableData = res.data.data;
          }
        })
        .catch(err => {});
    },
    // 跳转编辑商品页
    openEdit(row) {
      this.row = row;
      this.dialogVisible = true;
    },
    // 删除商品信息
    del(scope) {
      delBanner({
        id: scope.row.id
      })
        .then(res => {
          if (res.data.code === 1) {
            this.getData();
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {});
    },
    // 关闭弹框
    closeModal() {
      this.dialogVisible = false;
      this.getData();
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../assets/css/page.css";
</style>
