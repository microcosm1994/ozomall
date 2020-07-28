import Layout from '@/layout'

const settingRouter = {
    path: '/mallManage',
    component: Layout,
    redirect: '/mallManage/index',
    name: 'mallManage',
    meta: {
        title: '商城管理',
        icon: 'el-icon-s-shop'
    },
    children: [
        {
            path: 'index',
            component: () => import('@/views/mallManage/banner'),
            name: 'mallManageBanner',
            meta: { title: 'banner管理' }
        },
        {
            path: 'recoClassify',
            component: () => import('@/views/mallManage/recoClassify'),
            name: 'mallManageRecoClassify',
            meta: { title: '推荐分类' }
        },
        {
            path: 'recoGoods',
            component: () => import('@/views/mallManage/recoGoods'),
            name: 'mallManageRecoGoods',
            meta: { title: '推荐商品' }
        },
    ]
}

export default settingRouter
