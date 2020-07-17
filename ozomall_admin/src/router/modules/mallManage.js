import Layout from '@/layout'

const settingRouter = {
    path: '/mallManage',
    component: Layout,
    redirect: '/mallManage/index',
    name: 'mallManage',
    meta: {
        title: '商城配置',
        icon: 'el-icon-s-shop'
    },
    children: [
        {
            path: 'index',
            component: () => import('@/views/menuSetting/index'),
            name: 'mallManage_info',
            meta: { title: 'banner管理' }
        },
        {
            path: 'theme',
            component: () => import('@/views/theme/index'),
            name: 'theme',
            meta: { title: '更换主题' }
        },
        {
            path: 'markdown',
            component: () => import('@/views/components-demo/markdown'),
            name: 'MarkdownDemo',
            meta: { title: 'Markdown' }
        },
    ]
}

export default settingRouter
