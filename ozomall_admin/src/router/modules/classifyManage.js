import Layout from '@/layout'

const settingRouter = {
    path: '/classifyManage',
    component: Layout,
    redirect: '/classifyManage/index',
    name: 'classifyManage',
    meta: {
        title: '分类管理',
        icon: 'nested'
    },
    children: [
        {
            path: 'index',
            component: () => import('@/views/classifyManage/index'),
            name: 'classifyManage',
            meta: { title: '商品分类' }
        },
        {
            path: 'theme',
            component: () => import('@/views/classifyManage/index'),
            name: 'theme',
            meta: { title: '首页分类' }
        },
        {
            path: 'markdown',
            component: () => import('@/views/classifyManage/index'),
            name: 'Markdown',
            meta: { title: '子分类' }
        },
    ]
}

export default settingRouter
