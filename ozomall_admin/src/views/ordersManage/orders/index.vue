<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
        <el-form-item label="订单编号">
          <el-input
            v-model="ruleForm.orderNo"
            placeholder="订单编号"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="商品名称">
          <el-input
            v-model="ruleForm.goodsName"
            placeholder="商品名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select v-model="ruleForm.status" placeholder="订单状态" clearable>
            <el-option label="待付款" :value="0"></el-option>
            <el-option label="待发货" :value="1"></el-option>
            <el-option label="待收货" :value="2"></el-option>
            <el-option label="交易完成" :value="3"></el-option>
            <el-option label="交易关闭" :value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付方式">
          <el-select
            v-model="ruleForm.payType"
            placeholder="支付方式"
            clearable
          >
            <el-option label="未支付" :value="0"></el-option>
            <el-option label="支付宝" :value="1"></el-option>
            <el-option label="微信支付" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单来源">
          <el-select
            v-model="ruleForm.sourceType"
            placeholder="订单来源"
            clearable
          >
            <el-option label="微信小程序" :value="0"></el-option>
            <el-option label="app订单" :value="1"></el-option>
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
        <el-button type="primary" @click="openEdit">添加品牌</el-button>
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column align="center" prop="id" label="编号" width="55">
          </el-table-column>
          <el-table-column
            align="center"
            prop="orderNo"
            label="订单编号"
            width="200"
          >
          </el-table-column>
          <el-table-column align="center" label="商品图片" width="100">
            <template slot-scope="scope">
              <img :src="scope.row.goodsPic" style="width: 70px;" alt="" />
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="goodsName"
            label="商品名称"
            width="200"
          >
          </el-table-column>
          <el-table-column width="120" align="center" prop="des" label="订单来源">
            <template slot-scope="scope">
              {{ scope.row.sourceType | sourceType }}
            </template>
          </el-table-column>
          <el-table-column width="120" align="center" prop="des" label="用户账号">
            <template slot-scope="scope">
              {{ scope.row.userInfo.phone }}
            </template>
          </el-table-column>
          <el-table-column width="120" align="center" prop="des" label="订单状态">
            <template slot-scope="scope">
              {{ scope.row.status | status }}
            </template>
          </el-table-column>
          <el-table-column align="center" prop="orderAmount" label="订单金额">
          </el-table-column>
          <el-table-column width="180" align="center" prop="createTime" label="创建时间">
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
                >查看</el-button
              >
              <el-button type="text" size="small" @click="openEdit(scope.row)"
                >发货</el-button
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
      <!-- <Edit :closeModal="closeModal" :row="row" /> -->
    </el-dialog>
  </div>
</template>

<script>
// import Edit from "./components/edit";
import { getOrder } from "@/api/orderManage";
import { defaults } from "codemirror";
export default {
  components: {
    // Edit
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
        orderNo: "",
        userId: "",
        goodsName: "",
        payType: "",
        sourceType: "",
        status: ""
      },
      tableData: []
    };
  },
  filters: {
    sourceType(val) {
      switch (val - 0) {
        case 0:
          return "微信小程序";
          break;
        case 1:
          return "app订单";
          break;
        default:
          return "---";
          break;
      }
    },
    status(val) {
      switch (val - 0) {
        case 0:
          return "待付款";
          break;
        case 1:
          return "待发货";
          break;
        case 2:
          return "待收货";
          break;
        case 3:
          return "交易成功";
          break;
        case 4:
          return "交易关闭";
          break;
        default:
          return "---";
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
        size: this.pageParams.size,
        del: 0
      };
      this.tableData = [];
      getOrder(data)
        .then(res => {
          if (res.data.code === 1) {
            this.tableData = res.data.data.records;
            this.pageParams.page = res.data.data.current;
            this.pageParams.size = res.data.data.size;
            this.pageParams.total = res.data.data.total;
          }
        })
        .catch(err => {});
    },
    // 打开编辑弹框
    openEdit(row) {
      this.row = row;
      this.dialogVisible = true;
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
      this.pageParams.size = val;
      this.getData();
    },
    handleCurrentChange(val) {
      this.pageParams.page = val;
      this.getData();
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../assets/css/page.css";
</style>
