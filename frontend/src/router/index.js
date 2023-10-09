import { createRouter, createWebHashHistory } from 'vue-router'
import AdminUsersView from '@/components/AdminUsersView.vue'
import AdminUserDetail from '@/components/AdminUserDetail.vue'

const routes = [
  {
    path: '/admin/users',
    component: AdminUsersView,
    children: [
      { path: ':id', component: AdminUserDetail }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
