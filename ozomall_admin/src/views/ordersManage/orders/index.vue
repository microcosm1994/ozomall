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
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column
            width="120"
            align="center"
            prop="des"
            label="订单状态"
          >
            <template slot-scope="scope">
              <el-tag
                :type="['warning', 'danger', '', 'success', 'info'][scope.row.status]"
                effect="plain"
                disable-transitions
                >{{ scope.row.status | status }}</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="orderNo"
            label="订单编号"
            width="200"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="goodsName"
            label="商品名称"
            width="200"
          >
          </el-table-column>
          <el-table-column
            width="120"
            align="center"
            prop="des"
            label="订单来源"
          >
            <template slot-scope="scope">
              {{ scope.row.sourceType | sourceType }}
            </template>
          </el-table-column>
          <el-table-column
            width="120"
            align="center"
            prop="des"
            label="用户账号"
          >
            <template slot-scope="scope">
              {{ scope.row.userInfo.phone }}
            </template>
          </el-table-column>
          <el-table-column align="center" prop="orderAmount" label="订单金额">
             <template slot-scope="scope">
              ￥{{ scope.row.orderAmount }}
            </template>
          </el-table-column>
          <el-table-column
            width="180"
            align="center"
            prop="createTime"
            label="创建时间"
          >
          </el-table-column>
          <el-table-column
            width="120"
            align="center"
            prop="address"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="openDetail(scope.row)"
                >查看</el-button
              >
              <el-button
                v-if="scope.row.status === 1"
                type="primary"
                size="mini"
                @click="openHandle(scope.row)"
                >发货</el-button
              >
              <el-button
                v-if="scope.row.status === 4"
                type="danger"
                size="mini"
                @click="delGoodsBrand(scope)"
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
    <!-- 订单详情 -->
    <el-dialog v-if="dialogVisible" :visible.sync="dialogVisible" width="80%">
      <Detail :closeModal="closeModal" :row="row" />
    </el-dialog>
    <!-- 发货处理弹框 -->
    <el-dialog v-if="dialogVisible1" :visible.sync="dialogVisible1" width="400">
      <Handle :closeModal="closeModal" :row="row" />
    </el-dialog>
  </div>
</template>

<script>
// import Edit from "./components/edit";
import Detail from "./components/detail";
import Handle from "./components/handle";
import { getOrder } from "@/api/orderManage";
export default {
  components: {
    Detail,
    Handle
  },
  data() {
    return {
      dialogVisible: false, // 订单详情
      dialogVisible1: false, // 发货处理
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
    // 获取订单列表
    getData() {
      let data = {
        ...this.ruleForm,
        page: this.pageParams.page,
        size: this.pageParams.size,
        del: 0
      };
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
    // 打开订单详情弹框
    openDetail(row) {
      this.row = row;
      this.dialogVisible = true;
    },
    // 打开发货弹框
    openHandle(row) {
      this.row = row;
      this.dialogVisible1 = true;
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
      this.dialogVisible1 = false;
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
