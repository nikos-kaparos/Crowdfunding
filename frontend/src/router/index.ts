import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import Home from '@/views/Home.vue'
import Login from '@/views/Auth/Login.vue'
import Register from '@/views/Auth/Register.vue'
import Admin from "@/views/Admin/AdminUsers.vue"
import AdminProject from "@/views/Admin/AdminProject.vue"
import NewProject from "@/views/Project/NewProject.vue"
import Projects from "@/views/Project/Projects.vue"
import ProjectDetails from "@/views/Project/ProjectDetails.vue"
import Supporter from "@/views/Supporter/Supporter.vue"
import SupporterDetails from "@/views/Supporter/SupporterDetails.vue"
import CreatorEdit from "@/views/Creator/CreatorEdit.vue"
import CreatorUpdate from "@/views/Creator/CreatorUpdate.vue"

const routes: Array<RouteRecordRaw> = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/admin', component: Admin },
    { path: '/admin-projects', component: AdminProject },
    { path: '/new-projects', component: NewProject },
    { path: '/projects', component: Projects },
    { path: '/project/details/:id', component: ProjectDetails },
    { path: '/supporters', component: Supporter },
    { path: '/supporter/supporters-details/:id', component: SupporterDetails },
    { path: '/creator', component: CreatorEdit },
    { path: '/creator-update/:id', component: CreatorUpdate}
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router