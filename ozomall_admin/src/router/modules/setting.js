import Layout from '@/layout'

export default {
    path: '/setting',
    component: Layout,
    redirect: '/setting/index',
    name: 'setting',
    meta: {
        title: '系统设置',
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
