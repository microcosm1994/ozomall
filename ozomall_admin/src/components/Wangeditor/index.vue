<template>
  <div class="wangEditor">
    <div ref="editor" class="wangEditor-container"></div>
  </div>
</template>

<script>
import wangEditor from "wangeditor";
export default {
  data() {
    return {
      editor: null
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      console.log(this.$refs);
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
      this.editor.customConfig.linkImgCallback = function(url) {
        console.log(url); // url 即插入图片的地址
      };
      this.editor.create();
    }
  }
};
</script>

<style scoped lang="less">
.wangEditor {
  width: 100%;
}
</style>
