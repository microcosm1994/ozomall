<template>
  <div class="modal">
    <div class="modal-header">
      <div class="modal-header-title">查看订单</div>
    </div>
    <el-steps
      :active="row.status + 1"
      finish-status="success"
      simple
      align-center
    >
      <el-step title="提交订单"></el-step>
      <el-step title="支付订单"></el-step>
      <el-step title="平台发货"></el-step>
      <el-step title="确认收货"></el-step>
    </el-steps>
    <div class="modal-container">
      <div class="modal-container-item">
        <div class="item-title">基本信息</div>
        <div class="item-content">
          <el-table border :data="[row]" align="center">
            <el-table-column
              width="240"
              align="center"
              prop="orderNo"
              label="订单编号"
            >
            </el-table-column>
            <el-table-column align="center" prop="des" label="订单来源">
              <template slot-scope="scope">
                {{ scope.row.sourceType | sourceType }}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="des" label="用户账号">
              <template slot-scope="scope">
                {{ scope.row.userInfo.phone }}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="des" label="订单状态">
              <template slot-scope="scope">
                <el-tag
                  :type="
                    ['warning', 'danger', '', 'success', 'info'][
                      scope.row.status
                    ]
                  "
                  effect="plain"
                  disable-transitions
                  >{{ scope.row.status | status }}</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column align="center" prop="orderAmount" label="支付方式">
              <template slot-scope="scope">
                {{ scope.row.payType | payType }}
              </template>
            </el-table-column>
          </el-table>
          <el-table border :data="[row]" align="center">
            <el-table-column
              width="240"
              align="center"
              prop="paymentNo"
              label="支付订单流水号"
            >
            </el-table-column>
            <el-table-column
              align="center"
              prop="deliveryCompany"
              label="物流公司"
            >
            </el-table-column>
            <el-table-column align="center" prop="deliveryNo" label="物流单号">
            </el-table-column>
            <el-table-column align="center" prop="createTime" label="创建时间">
            </el-table-column>
            <el-table-column align="center" prop="paymentTime" label="付款时间">
            </el-table-column>
          </el-table>
          <el-table border :data="[row]" align="center">
            <el-table-column
              align="center"
              prop="deliveryTime"
              label="发货时间"
            >
            </el-table-column>
            <el-table-column
              align="center"
              prop="receiveTime"
              label="确认收货时间"
            >
            </el-table-column>
            <el-table-column></el-table-column>
            <el-table-column></el-table-column>
            <el-table-column></el-table-column>
          </el-table>
        </div>
      </div>
      <div class="modal-container-item">
        <div class="item-title">收货人信息</div>
        <div class="item-content">
          <el-table border :data="[row.addressInfo]" align="center">
            <el-table-column align="center" prop="consignee" label="收货人">
            </el-table-column>
            <el-table-column align="center" prop="phone" label="手机号">
            </el-table-column>
            <el-table-column align="center" prop="region" label="区域">
            </el-table-column>
            <el-table-column align="center" prop="address" label="详细地址">
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="modal-container-item">
        <div class="item-title">商品信息</div>
        <div class="item-content">
          <el-table border :data="[row]" align="center">
            <el-table-column
              align="center"
              prop="goodsId"
              label="商品编号"
              width="100"
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
            <el-table-column align="center" label="商品属性">
              <template slot-scope="scope">
                {{
                  scope.row.spe1Name +
                    " " +
                    scope.row.spe2Name +
                    " " +
                    scope.row.spe3Name
                }}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="goodsAmount" label="商品价格">
            </el-table-column>
            <el-table-column align="center" label="商品数量">
              <template slot-scope="scope">
                {{ 1 }}
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="modal-container-item">
        <div class="item-title">费用信息</div>
        <div class="item-content">
          <el-table border :data="[row]" align="center">
            <el-table-column align="center" label="商品金额">
              <template slot-scope="scope">
                ￥{{ scope.row.goodsAmount }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="运费">
              <template slot-scope="scope"> ￥{{ 14 }} </template>
            </el-table-column>
            <el-table-column align="center" label="优惠金额">
              <template slot-scope="scope">
                ￥{{ scope.row.promotionAmount }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="订单总金额">
              <template slot-scope="scope">
                ￥{{ scope.row.orderAmount }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="实际支付金额">
              <template slot-scope="scope">
                ￥{{ scope.row.payAmount }}
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div class="modal-container-btn">
        <el-button type="primary" size="small" @click="closeModal">
          关闭
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { getOrder } from "@/api/orderManage";
export default {
  props: ["closeModal", "row"],
  data() {
    return {};
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
    },
    payType(val) {
      switch (val - 0) {
        case 0:
          return "未支付";
          break;
        case 1:
          return "支付宝";
          break;
        case 2:
          return "微信";
          break;
        default:
          return "---";
          break;
      }
    }
  },
  mounted() {},
  methods: {}
};
</script>

<style scoped lang="less">
@import "../../../../assets/css/modal.css";
</style>
