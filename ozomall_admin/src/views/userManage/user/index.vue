<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
        <el-form-item label="用户名">
          <el-input
            v-model="ruleForm.userName"
            placeholder="用户名"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input
            v-model="ruleForm.nickName"
            placeholder="用户昵称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input
            v-model="ruleForm.phone"
            placeholder="手机号"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="用户角色">
          <el-select v-model="ruleForm.roleId" placeholder="用户角色">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select clearable v-model="ruleForm.type" placeholder="用户类型">
            <el-option label="管理员用户" :value="0"></el-option>
            <el-option label="商城用户" :value="1"></el-option>
          </el-select>
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
        <el-button type="primary" @click="dialogVisible = true"
          >添加用户</el-button
        >
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column
            align="center"
            prop="id"
            label="用户编号"
            width="100"
          >
          </el-table-column>
          <el-table-column align="center" label="用户昵称" prop="nickName">
          </el-table-column>
          <el-table-column align="center" label="用户名" prop="userName">
          </el-table-column>
          <el-table-column
            align="center"
            label="用户角色"
            prop="roleId"
            width="180"
          >
            <template slot-scope="scope">
              {{ scope.row.role.name }}
            </template>
          </el-table-column>
          <el-table-column align="center" prop="phone" label="手机号">
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
              <el-button type="text" size="small" @click="delGoods(scope)"
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
    <el-dialog :visible.sync="dialogVisible" v-if="dialogVisible" width="400px">
      <Edit :row="row" :closeModal="closeModal" />
    </el-dialog>
  </div>
</template>

<script>
import { getUserList } from "@/api/userManage";
import Edit from "./components/edit";
export default {
  components: {
    Edit
  },
  data() {
    return {
      dialogVisible: false,
      pageParams: {
        page: 1,
        size: 10,
        total: 0
      },
      ruleForm: {
        userName: "",
        nickName: "",
        phone: "",
        roleId: "",
        type: ""
      },
      tableData: [],
      row: null
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
      getUserList(data)
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
    // 跳转编辑商品页
    openEdit(row) {
      this.row = row;
      this.dialogVisible = true;
    },
    // 删除商品信息
    delGoods(scope) {
      delGoods({
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
</style>
