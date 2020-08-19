import Layout from '@/layout'

const settingRouter = {
    path: '/ordersManage',
    component: Layout,
    redirect: '/ordersManage/orders',
    name: 'ordersManage',
    meta: {
        title: '订单中心',
        icon: 'el-icon-s-order'
    },
    children: [
        {
            path: 'orders',
            component: () => import('@/views/ordersManage/orders'),
            name: 'ordersManageOrders',
            meta: { title: '订单管理' }
        },
        {
            path: 'brand',
            component: () => import('@/views/ordersManage/orders'),
            name: 'ordersManage',
            meta: { title: '品牌管理' }
        },
    ]
}

export default settingRouter
