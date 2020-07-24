<template>
  <div class="wangEditor">
    <div ref="editor" class="wangEditor-container"></div>
  </div>
</template>

<script>
import wangEditor from "wangeditor";
export default {
  props: ["config"],
  data() {
    return {
      editor: null
    };
  },
  computed: {
    token() {
      return this.$store.state.user.token;
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.editor = new wangEditor(this.$refs.editor);
      this.editor.customConfig.menus = [
        "head", // 标题
        "bold", // 粗体
        "fontSize", // 字号
        "fontName", // 字体
        "italic", // 斜体
        "underline", // 下划线
        "strikeThrough", // 删除线
        "foreColor", // 文字颜色
        "backColor", // 背景颜色
        "link", // 插入链接
        "list", // 列表
        "justify", // 对齐方式
        "quote", // 引用
        // "emoticon", // 表情
        "image", // 插入图片
        "table", // 表格
        "video", // 插入视频
        // "code", // 插入代码
        "undo", // 撤销
        "redo"
      ];
      this.editor.customConfig.zIndex = 100;
      this.editor.customConfig.pasteIgnoreImg = false;
      this.editor.customConfig.uploadImgServer = "/api/admin/goods/detailsUpload";
      this.editor.customConfig.uploadFileName = "file";
      this.editor.customConfig.showLinkImg = false
      this.editor.customConfig.uploadImgHeaders = {
        token: this.token
      };
      this.editor.customConfig.uploadImgParams = this.config;
      this.editor.customConfig.uploadImgHooks = {
        customInsert: this.customInsert
      };
      this.editor.customConfig.customAlert = info => {
        // info 是需要提示的内容
        this.$message.error(info);
      };
      this.editor.create();
    },
    // 插入图片
    customInsert(insertImg, result, editor) {
      if (result.code === 1) {
        insertImg(result.data.url);
      }
    }
  }
};
</script>

<style scoped lang="less">
.wangEditor {
  width: 100%;
}
</style>
