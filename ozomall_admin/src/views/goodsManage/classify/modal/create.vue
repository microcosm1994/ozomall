<template>
  <div class="modal">
    <div class="modal-header">
      <div slot="title" class="modal-header-title">
        {{ modalTitle }}
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
          <el-form-item v-if="!row" label="所属类别" prop="parentId">
            <el-cascader
              v-model="ruleForm.parentId"
              :props="props"
              :filterable="true"
              @change="cascaderHandle"
              ref="cascader"
              clearable
            ></el-cascader>
          </el-form-item>
          <el-form-item v-if="!row" label="快速选择">
            <el-button v-if="!qselectDisvble" type="text" @click="qselect">打开快速选择</el-button>
            <el-button v-if="qselectDisvble" type="text" @click="qselectDisvble = false">关闭快速选择</el-button>
            <el-form-item v-if="qselectDisvble" label="选择品牌">
              <el-select
                placeholder="请选择"
                v-model="qselectValue"
                @change="qselectChange"
                clearable
              >
                <el-option
                  v-for="item in brandList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                  <span style="float: left">
                    <img style="height: 30px;" :src="item.url" alt="" />
                  </span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{
                    item.name
                  }}</span>
                </el-option>
              </el-select>
            </el-form-item>
          </el-form-item>
          <el-form-item label="分类名称" prop="name">
            <el-input v-model="ruleForm.name" clearable></el-input>
          </el-form-item>
          <el-form-item label="图片" prop="url">
            <el-upload
              class="avatar-uploader"
              action="/api/classify/upload"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
            >
              <img v-if="ruleForm.url" :src="ruleForm.url" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
      <div class="modal-container-btn">
        <el-button type="primary" @click="onSubmit('ruleForm')">确定</el-button>
        <el-button @click="closeModal(null)">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  addClassify,
  putClassify,
  getClassifyList,
  getGoodsBrand
} from "@/api/goodsManage";
export default {
  props: ["closeModal", "row"],
  data() {
    const self = this;
    return {
      modalTitle: "新建",
      qselectDisvble: false,
      qselectValue: "",
      ruleForm: {
        name: "",
        parentId: "",
        url: "",
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
      brandList: [],
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
  computed: {
    uploadHeaders() {
      return {
        token: this.$store.state.user.token
      };
    }
  },
  mounted() {
    this.getClassify();
    this.modalTitle = "新建";
    if (this.row && this.row.id) {
      this.modalTitle = "编辑";
      this.ruleForm.name = this.row.name;
      this.ruleForm.parentId = this.row.parentId;
      this.ruleForm.url = this.row.url;
      this.ruleForm.classifyLevel = this.row.classifyLevel;
    }
  },
  methods: {
    // 打开快速选择
    qselect() {
      this.getGoodsBrand();
      this.qselectDisvble = true;
    },
    // 快速选择
    qselectChange(val) {
      console.log(val);
      for (let i = 0; i < this.brandList.length; i++) {
        if (this.brandList[i].id === val) {
          this.ruleForm.name = this.brandList[i].name;
          this.ruleForm.url = this.brandList[i].url;
        }
      }
    },
    // 获取品牌列表
    getGoodsBrand() {
      getGoodsBrand({
        page: 1,
        size: 1000
      })
        .then(res => {
          if (res.data.code === 1) {
            this.brandList = res.data.data.records;
          }
        })
        .catch(err => {});
    },
    // 上传成功后调用
    handleAvatarSuccess(res, file) {
      if (res.code === 1) {
        this.ruleForm.url = res.data.url;
      } else {
        this.$message.error(res.msg);
      }
    },
    // 选中触发
    cascaderHandle(val) {
      const node = this.$refs.cascader.getCheckedNodes()[0];
      this.ruleForm.classifyLevel = node.level + 1;
    },
    // 获取分类
    getClassify() {
      getClassifyList()
        .then(res => {
          if (res.data.code === 1) {
            this.classifyList = res.data.data;
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
          if (this.row && this.row.id) {
            this.putClassify();
          } else {
            this.addClassify();
          }
        } else {
          return false;
        }
      });
    },
    // 添加
    addClassify() {
      addClassify(this.ruleForm)
        .then(res => {
          if (res.data.code === 1) {
            this.closeModal(
              this.ruleForm.classifyLevel,
              this.ruleForm.parentId
            );
            this.$message.success(res.data.msg);
          } else {
            this.$message.error(res.data.msg);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    // 修改
    putClassify() {
      putClassify({
        id: this.row.id,
        ...this.ruleForm
      }).then(res => {
        if (res.data.code === 1) {
          this.closeModal(this.ruleForm.classifyLevel, this.ruleForm.parentId);
          this.$message.success(res.data.msg);
        } else {
          this.$message.error(res.data.msg);
        }
      });
    }
  }
};
</script>

<style scoped lang="less">
@import "../../../../assets/css/modal.css";
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
