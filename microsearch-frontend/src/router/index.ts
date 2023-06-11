import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import IndexPage from "@/pages/IndexPage.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "home",
    meta: {
      title: "聚合搜索平台",
    },
    component: IndexPage,
  },
  {
    path: "/:category",
    name: "聚合搜索平台",
    meta: {
      title: "聚合搜索平台",
    },
    component: IndexPage,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
