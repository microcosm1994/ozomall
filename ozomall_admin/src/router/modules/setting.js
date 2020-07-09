import Layout from '@/layout'

const settingRouter = {
    path: '/setting',
    component: Layout,
    redirect: '/setting/index',
    name: 'setting',
    meta: {
        title: '设置',
        icon: 'el-icon-setting'
    },
    children: [
        {
            path: 'index',
            component: () => import('@/views/menuSetting/index'),
            name: 'menuSetting',
            meta: { title: '配置菜单' }
        },
        {
            path: 'theme',
            component: () => import('@/views/theme/index'),
            name: 'theme',
            meta: { title: '主题' }
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
