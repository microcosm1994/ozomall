<template>
  <div class="modal">
    <div class="modal-header">
      <div slot="title" class="modal-header-title">
        新建
      </div>
    </div>
    <div class="modal-container">
      <div class="modal-container-form">
        <el-form
          ref="ruleForm"
          :model="ruleForm"
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="所属类别" prop="parentId">
            <el-cascader
              v-model="ruleForm.parentId"
              :props="props"
              :filterable="true"
              @change="cascaderHandle"
              ref="cascader"
            ></el-cascader>
          </el-form-item>
          <el-form-item label="分类名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="modal-container-btn">
        <el-button type="primary" @click="onSubmit('ruleForm')">确定</el-button>
        <el-button @click="closeModal">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { addClassify, getClassifyList } from "@/api/goodsManage";
export default {
  props: ["closeModal"],
  data() {
    const self = this;
    return {
      ruleForm: {
        name: "",
        parentId: "",
        classifyLevel: 1
      },
      rules: {
        name: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
        parentId: [
          { required: true, message: "请选择所属类别", trigger: "change" }
        ]
      },
      classifyList: [],
      options: [],
      props: {
        lazy: true,
        checkStrictly: true,
        emitPath: false,
        lazyLoad(node, resolve) {
          const { level, value } = node;
          if (level > 1) {
            resolve();
            return false;
          }
          getClassifyList({ classifyLevel: level + 1, parentId: value })
            .then(res => {
              if (res.data.code === 1) {
                const nodes = res.data.data.map(item => {
                  return {
                    value: item.id,
                    label: item.name,
                    leaf: level >= 1
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
  mounted() {
    this.getClassify();
  },
  methods: {
    // 选中触发
    cascaderHandle(val) {
      const node = this.$refs.cascader.getCheckedNodes()[0]
      this.ruleForm.classifyLevel = node.level + 1
    },
    // 获取分类
    getClassify() {
      getClassifyList()
        .then(res => {
          if (res.data.code === 1) {
            this.classifyList = res.data.data;
            console.log(this.classifyList);
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 提交请求
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          addClassify(this.ruleForm)
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
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../../assets/css/modal.css";
</style>
