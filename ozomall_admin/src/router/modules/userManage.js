import Layout from '@/layout'

export default {
    path: '/user',
    component: Layout,
    redirect: '/user/index',
    name: 'userManage',
    meta: {
        title: '用户中心',
        icon: 'peoples'
    },
    children: [
        {
            path: 'index',
            component: () => import('@/views/userManage/user'),
            name: 'userManageUser',
            meta: { title: '用户管理' }
        },
        {
            path: 'role',
            component: () => import('@/views/userManage/role'),
            name: 'userManageRole',
            meta: { title: '角色管理' }
        }
    ]
}

