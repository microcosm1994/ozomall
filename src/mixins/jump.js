export default {
    data: {
        mixin: 'MixinText'
    },
    methods: {
        // 跳转到搜索页
        toSearch() {
            wx.navigateTo({
                url: '/pages/search/index',
            });
        },
        // 跳转到分类页
        toClassify() {
            wx.navigateTo({
                url: '/pages/classify/index',
            });
        },
        // 跳转到商品详情页
        toProdDetails() {
            wx.navigateTo({
                url: '/pages/prodDetails/index',
            });
        },
    },
    created() {
        console.log('created in mixin');
    }
}
