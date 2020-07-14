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
          <el-form-item label="分类等级" prop="classifyLevel">
            <el-select
              v-model="ruleForm.classifyLevel"
              placeholder="请选择分类等级"
              @change="getClassify"
            >
              <el-option label="一级分类" :value="1"></el-option>
              <el-option label="二级分类" :value="2"></el-option>
              <el-option label="三级分类" :value="3"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="ruleForm.classifyLevel !== 1" label="所属类别" prop="parentId">
            <el-select v-model="ruleForm.parentId" placeholder="请选择所属类别">
              <el-option label="无" :value="0"></el-option>
            </el-select>
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
import { addClassify } from "@/api/classifyManage";
import { getClassifyList } from "@/api/classifyManage";
export default {
  props: ["closeModal"],
  data() {
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
        ],
        classifyLevel: [
          { required: true, message: "请选择分类级别", trigger: "change" }
        ]
      }
    };
  },
  methods: {
    // 获取类别
    getClassify(val) {
      if (val === 1) {
        return false
      }
      let data = {
        classifyLevel: val - 1
      }
      getClassifyList(data)
        .then(res => {
          if (res.data.code === 1) {
            console.log(res.data.data);
            console.log(res.data.data.current);
            this.tableData = res.data.data.records;
            this.pageParam.page = res.data.data.current;
            this.pageParam.size = res.data.data.size;
            this.total = res.data.data.total;
            this.$message.success(res.data.msg);
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
