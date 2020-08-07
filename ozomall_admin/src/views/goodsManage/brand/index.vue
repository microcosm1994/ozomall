<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
        <el-form-item label="品牌名称">
          <el-input
            v-model="ruleForm.name"
            placeholder="品牌名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getData">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="page-container">
      <div class="page-container-header">
        <el-button
          type="primary"
          icon="el-icon-refresh"
          @click="getData"
        ></el-button>
        <el-button type="primary" @click="openEdit">添加品牌</el-button>
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column
            align="center"
            prop="id"
            label="品牌编号"
            width="100"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="url"
            label="品牌Logo"
            width="180"
          >
            <template slot-scope="scope">
              <img :src="scope.row.url" style="width: 50px;" alt="" />
            </template>
          </el-table-column>
          <el-table-column align="center" prop="name" label="品牌名称">
          </el-table-column>
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
              <el-button type="text" size="small" @click="delGoodsBrand(scope)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="page-container-page">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageParams.page"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageParams.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageParams.total"
        >
        </el-pagination>
      </div>
    </div>
    <!-- 编辑弹框 -->
    <el-dialog v-if="dialogVisible" :visible.sync="dialogVisible" width="500px">
      <Edit :closeModal="closeModal" :row="row" />
    </el-dialog>
  </div>
</template>

<script>
import Edit from "./components/edit";
import { getGoodsBrand, delGoodsBrand } from "@/api/goodsManage";
import { defaults } from "codemirror";
export default {
  components: {
    Edit
  },
  data() {
    return {
      dialogVisible: false,
      row: null,
      pageParams: {
        page: 1,
        size: 10,
        total: 0
      },
      ruleForm: {
        name: ""
      },
      tableData: []
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    // 获取商品列表
    getData() {
      let data = {
        ...this.ruleForm,
        page: this.pageParams.page,
        size: this.pageParams.size
      };
      this.tableData = [];
      getGoodsBrand(data)
        .then(res => {
          if (res.data.code === 1) {
            this.tableData = res.data.data.records;
            this.pageParams.page = res.data.data.current;
            this.pageParams.current = res.data.data.current;
            this.pageParams.total = res.data.data.total;
          }
        })
        .catch(err => {});
    },
    // 打开编辑弹框
    openEdit(row) {
      this.row = row
      this.dialogVisible = true
    },
    // 删除品牌
    delGoodsBrand(scope) {
      delGoodsBrand({
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
    },
    handleSizeChange(val) {
      this.pageParams.size = val
      this.getData();
    },
    handleCurrentChange(val) {
      this.pageParams.page = val
      this.getData();
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../assets/css/page.css";
</style>
