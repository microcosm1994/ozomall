import Layout from '@/layout'

const settingRouter = {
    path: '/goodsManage',
    component: Layout,
    redirect: '/goodsManage/goods',
    name: 'goodsManage',
    meta: {
        title: '商品管理',
        icon: 'el-icon-s-goods'
    },
    children: [
        {
            path: 'goods',
            component: () => import('@/views/goodsManage/goods'),
            name: 'goodsManageGoods',
            meta: { title: '商品管理' }
        },
        {
            path: 'edit',
            component: () => import('@/views/goodsManage/goods/edit'),
            name: 'goodsManageEdit',
            hidden: true,
            meta: { title: '编辑商品' }
        },
        {
            path: 'classify',
            component: () => import('@/views/goodsManage/classify'),
            name: 'goodsManageClassify',
            meta: { title: '商品分类' }
        },
        {
            path: 'brand',
            component: () => import('@/views/goodsManage/brand'),
            name: 'goodsManageBrand',
            meta: { title: '品牌管理' }
        },
    ]
}

export default settingRouter
