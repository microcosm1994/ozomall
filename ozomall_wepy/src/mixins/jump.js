import { logout } from '../api/user'
export default {
    data: {
        mixin: 'MixinText'
    },
    methods: {
        // 跳转到登录页
        toLogin() {
            logout().then(res => {
                if (res.data.code === 1) {
                    wx.clearStorage()
                    wx.navigateTo({
                        url: '/pages/login/index',
                    });
                }
            })
        },
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
        // 跳转到最近购买页
        toBuyList() {
            wx.navigateTo({
                url: '/pages/buyList/index',
            });
        },
        // 跳转到我的账户页
        toAccount() {
            wx.navigateTo({
                url: '/pages/account/index',
            });
        },
        // 跳转到我的账户-账户明细页
        toAccountList() {
            wx.navigateTo({
                url: '/pages/account/list',
            });
        },
        // 跳转到订单列表页
        toOrder(orderStatus) {
            wx.navigateTo({
                url: '/pages/order/index',
                success: function (res) {
                    // 通过eventChannel向被打开页面传送数据
                    res.eventChannel.emit('orderStatus', { orderStatus })
                }
            });
        },
    },
    created() {
        console.log('created in mixin');
    }
}
