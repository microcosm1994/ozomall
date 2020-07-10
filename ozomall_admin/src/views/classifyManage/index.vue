<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" ref="form" :model="form" label-width="80px">
        <el-form-item label="分类名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="page-container">
      <div class="page-container-header">
        <el-form :inline="true" ref="form" :model="form" label-width="80px">
          <el-form-item>
            <el-button
              icon="el-icon-refresh"
              type="primary"
              @click="onSubmit"
            ></el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="dialogVisible = true"
              >新建</el-button
            >
          </el-form-item>
        </el-form>
      </div>
      <div class="page-container-table">
        <el-table :data="tableData" style="width: 100%" border>
          <el-table-column
            align="center"
            prop="id"
            label="id"
            width="180"
          ></el-table-column>
          <el-table-column
            align="center"
            prop="name"
            label="分类名称"
          ></el-table-column>
          <el-table-column align="center" prop="createTime" label="创建时间">
          </el-table-column>
          <el-table-column
            width="200"
            align="center"
            prop="createTime"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >编辑</el-button
              >
              <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete(scope.$index, scope.row)"
              ></el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="page-container-page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[100, 200, 300, 400]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total="400"
          >
          </el-pagination>
        </div>
      </div>
    </div>
    <!-- 新建弹框 -->
    <el-dialog
      :visible.sync="dialogVisible"
      :v-if="dialogVisible"
      width="500px"
    >
      <Create :closeModal="closeModal"/>
    </el-dialog>
  </div>
</template>

<script>
import Create from "./modal/create";
export default {
  components: {
    Create
  },
  data() {
    return {
      dialogVisible: false,
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: ""
      },
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄"
        },
        {
          date: "2016-05-04",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1517 弄"
        },
        {
          date: "2016-05-01",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1519 弄"
        },
        {
          date: "2016-05-03",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1516 弄"
        }
      ],
      currentPage: 1
    };
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    },
    // 关闭弹框
    closeModal() {
      this.dialogVisible = false;
    },
    handleSizeChange() {},
    handleCurrentChange() {}
  }
};
</script>

<style scoped lang="less">
@import "../../assets/css/page.css";
</style>
