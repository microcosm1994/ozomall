<template>
  <div class="page">
    <div class="page-header">
      <el-form :inline="true" :model="ruleForm" class="demo-form-inline">
        <el-form-item label="商品名称">
          <el-input
            v-model="ruleForm.goodsName"
            placeholder="商品名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item label="所属类别" prop="parentId">
          <el-cascader
            v-model="ruleForm.classifyId"
            :props="props"
            :filterable="true"
            ref="cascader"
            clearable
          ></el-cascader>
        </el-form-item>
        <el-form-item label="商品状态">
          <el-select v-model="ruleForm.status" placeholder="商品状态">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
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
        <el-button type="primary" @click="openEdit">添加商品</el-button>
      </div>
      <div class="page-container-table">
        <el-table border :data="tableData" style="width: 100%" align="center">
          <el-table-column
            align="center"
            prop="id"
            label="商品编号"
            width="100"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="cover"
            label="商品封面"
            width="180"
          >
            <template slot-scope="scope">
              <img :src="scope.row.cover" style="width: 120px;" alt="">
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="classifyId"
            label="所属类别"
            width="180"
          >
            <template slot-scope="scope">
              {{ scope.row.classify.name }}
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="goodsName"
            label="商品名称"
            width="180"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="goodsPrice"
            label="商品价格"
            width="180"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="commentCount"
            label="商品评论数量"
            width="180"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="sales"
            label="商品销量"
            width="180"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="status"
            label="商品状态"
            width="180"
          >
          </el-table-column>
          <el-table-column
            align="center"
            prop="step"
            label="商品填写进度"
            width="180"
          >
            <template slot-scope="scope">
              {{ scope.row.step | step }}
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="createTime"
            label="创建时间"
            width="180"
          >
          </el-table-column>
          <el-table-column
            width="200"
            align="center"
            prop="address"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                @click="handleClick(scope.row)"
                type="text"
                size="small"
                >查看</el-button
              >
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
  </div>
</template>

<script>
import { getClassifyList, getGoodsList, delGoods } from "@/api/goodsManage";
import { defaults } from "codemirror";
export default {
  data() {
    return {
      pageParams: {
        page: 1,
        size: 10,
        total: 0
      },
      ruleForm: {
        goodsName: "",
        status: "",
        classifyId: ""
      },
      tableData: [],
      props: {
        lazy: true,
        emitPath: false,
        lazyLoad(node, resolve) {
          const { level, value } = node;
          getClassifyList({ classifyLevel: level + 1, parentId: value })
            .then(res => {
              if (res.data.code === 1) {
                const nodes = res.data.data.map(item => {
                  return {
                    value: item.id,
                    label: item.name,
                    leaf: level >= 2
                  };
                });
                // 通过调用resolve将子节点数据返回，通知组件数据加载完成
                resolve(nodes);
              } else {
                this.$message.error(res.data.msg);
              }
            })
            .catch(err => {
              console.log(err);
            });
        }
      }
    };
  },
  filters: {
    step(val) {
      switch (val - 0) {
        case 3:
          return "完成";
          break;
        default:
          return "未完成";
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
      getGoodsList(data)
        .then(res => {
          if (res.data.code === 1) {
            console.log(res);
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
      this.$router.push({
        name: "goodsManageEdit",
        params: {
          id: row.id
        }
      });
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
    onSubmit() {
      console.log("submit!");
    },
    // 关闭弹框
    closeModal() {
      this.dialogVisible = false;
      this.getClassify(1, 0);
    },
    // 获取分类
    getClassify() {
      getClassifyList(data)
        .then(res => {})
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
</style>
