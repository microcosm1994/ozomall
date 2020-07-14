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
            component: () => import('@/views/classifyManage/group/index'),
            name: 'classifyManage',
            meta: { title: '类别分组' }
        },
        {
            path: 'theme',
            component: () => import('@/views/classifyManage/classify/index'),
            name: 'theme',
            meta: { title: '商品分类' }
        },
        {
            path: 'markdown',
            component: () => import('@/views/classifyManage/hotGroup/index'),
            name: 'Markdown',
            meta: { title: '热门分组' }
        },
    ]
}

export default settingRouter
