<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
        <el-form-item label="用户id">
          <el-input
            v-model="ruleForm.id"
            placeholder="用户id"
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
        <el-form-item label="用户手机号">
          <el-input
            v-model="ruleForm.phone"
            placeholder="用户手机号"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="用户性别">
          <el-select clearable v-model="ruleForm.gender" placeholder="用户性别">
            <el-option label="未知" :value="0"></el-option>
            <el-option label="男" :value="1"></el-option>
            <el-option label="女" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="vip">
          <el-select clearable v-model="ruleForm.vip" placeholder="vip">
            <el-option label="是" :value="1"></el-option>
            <el-option label="否" :value="0"></el-option>
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
          <el-table-column align="center" label="用户手机号" prop="phone">
          </el-table-column>
          <el-table-column align="center" label="vip" prop="vip" width="80">
            <template slot-scope="scope">
              {{ scope.row.vip ? "是" : "否" }}
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            label="性别"
            prop="gender"
            width="120"
          >
            <template slot-scope="scope">
              {{ scope.row.gender | gender }}
            </template>
          </el-table-column>
          <el-table-column align="center" label="用户签名" prop="sign">
          </el-table-column>
          <el-table-column
            width="150"
            align="center"
            prop="address"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.userName !== 'admin'"
                type="text"
                size="small"
                @click="openEdit(scope.row)"
                >查看</el-button
              >
              &nbsp;
              <el-popconfirm
                v-if="scope.row.userName !== 'admin'"
                title="确定删除此用户吗？"
                @onConfirm="delUser(scope)"
              >
                <el-button type="text" size="small" slot="reference"
                  >删除</el-button
                >
              </el-popconfirm>
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
    <!-- 查看弹框 -->
    <el-dialog :visible.sync="dialogVisible" v-if="dialogVisible" width="400px">
    </el-dialog>
  </div>
</template>

<script>
import { getMallUserList } from "@/api/mallUserManage";
import Edit from "./components/edit";
import { defaults } from "codemirror";
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
        id: "",
        nickName: "",
        phone: "",
        vip: "",
        gender: ""
      },
      tableData: [],
      row: null
    };
  },
  filters: {
    gender(val) {
      switch (val - 0) {
        case 0:
          return "未知";
          break;
        case 1:
          return "男";
          break;
        case 2:
          return "女";
          break;
        default:
          break;
      }
    }
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
      getMallUserList(data)
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
    // 打开用户编辑弹框
    openEdit(row) {
      this.row = row;
      this.dialogVisible = true;
    },
    // 删除用户
    delUser(scope) {
      delUser({
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
