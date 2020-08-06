import Layout from '@/layout'

export default {
    path: '/sys',
    component: Layout,
    redirect: '/sys/index',
    name: 'sys',
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
            path: 'user',
            component: () => import('@/views/sysManage/user'),
            name: 'sysManageUser',
            meta: { title: '账号管理' }
        },
        {
            path: 'role',
            component: () => import('@/views/sysManage/role'),
            name: 'sysManageRole',
            meta: { title: '角色管理' }
        },
    ]
}
