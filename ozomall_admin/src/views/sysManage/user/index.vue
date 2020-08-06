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
        <el-form-item label="用户角色">
          <el-select v-model="ruleForm.roleId" placeholder="用户角色">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select clearable v-model="ruleForm.type" placeholder="用户类型">
            <el-option label="管理系统用户" :value="0"></el-option>
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
        <el-button type="primary" @click="openEdit(null)">添加用户</el-button>
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
          <el-table-column align="center" label="用户类型" width="180">
            <template slot-scope="scope">
              {{ scope.row.role.type | type }}
            </template>
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
                >编辑</el-button
              >
              <el-button
                type="text"
                size="small"
                @click="openPassEdit(scope.row)"
                >重置密码</el-button
              >&nbsp;
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
    <!-- 编辑弹框 -->
    <el-dialog :visible.sync="dialogVisible" v-if="dialogVisible" width="400px">
      <Edit
        v-if="dialogVisible"
        :row="row"
        :closeModal="closeModal"
        :roleList="roleList"
      />
    </el-dialog>
    <!-- 修改密码 -->
    <el-dialog
      :visible.sync="dialogVisible1"
      v-if="dialogVisible1"
      width="400px"
    >
      <div class="modal">
        <div class="modal-header">
          <div slot="title" class="modal-header-title">重置密码</div>
        </div>
        <div class="modal-container">
          <div class="modal-container-form">
            <el-form
              :status-icon="true"
              ref="editPassForm"
              :model="editPassForm"
              :rules="editPassRules"
              label-width="80px"
            >
              <el-form-item label="新密码" prop="passWord">
                <el-input
                  type="passWord"
                  v-model="editPassForm.passWord"
                ></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="passWordCheck">
                <el-input
                  type="passWord"
                  v-model="editPassForm.passWordCheck"
                ></el-input>
              </el-form-item>
            </el-form>
          </div>
          <div class="modal-container-btn">
            <el-button type="primary" @click="onSubmit('editPassForm')"
              >确定</el-button
            >
            <el-button @click="closeModal">取消</el-button>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getUserList, getRole, putUser, delUser } from "@/api/sysManage";
import Edit from "./components/edit";
import cryptoMd5 from "crypto-js/md5";
export default {
  components: {
    Edit
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.editPassForm.checkPass !== "") {
          this.$refs.editPassForm.validateField("checkPass");
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.editPassForm.passWord) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      dialogVisible: false,
      dialogVisible1: false,
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
      editPassForm: {
        passWord: "",
        passWordCheck: ""
      },
      editPassRules: {
        nickName: [
          { required: true, message: "请输入用户昵称", trigger: "blur" }
        ],
        roleId: [
          { required: true, message: "请选择用户角色", trigger: "change" }
        ],
        userName: [{ required: true, message: "请输入账号", trigger: "blur" }],
        passWord: [{ validator: validatePass, trigger: "blur" }],
        passWordCheck: [{ validator: validatePass2, trigger: "blur" }]
      },
      tableData: [],
      roleList: [],
      row: null
    };
  },
  filters: {
    type: function(val) {
      switch (val - 0) {
        case 0:
          return "管理系统用户";
          break;
        case 1:
          return "商城APP用户";
          break;
        default:
          return "未知用户";
          break;
      }
    }
  },
  mounted() {
    this.getRole();
    this.getData();
  },
  methods: {
    // 获取分类
    getRole() {
      let data = {
        type: 0
      };
      getRole(data)
        .then(res => {
          if (res.data.code === 1) {
            this.roleList = res.data.data;
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {});
    },
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
    // 打开用户编辑弹框
    openEdit(row) {
      this.row = row;
      this.dialogVisible = true;
    },
    // 打开重置密码弹框
    openPassEdit(row) {
      this.row = row;
      this.dialogVisible1 = true;
    },
    // 重置密码提交请求
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          putUser({
            id: this.row.id,
            passWord: cryptoMd5(
              encodeURI(this.editPassForm.passWord)
            ).toString()
          })
            .then(res => {
              if (res.data.code === 1) {
                this.closeModal();
                this.$message.success(res.data.msg);
              } else {
                this.$message.error(res.data.msg);
              }
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          return false;
        }
      });
    },
    // 删除商品信息
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
      this.dialogVisible1 = false;
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
