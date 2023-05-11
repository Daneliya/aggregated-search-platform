<template>
  <!--  搜索框  -->
  <div class="index-page">
    <a-input-search
      v-model:value="searchParams.text"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="onSearch"
    />
  </div>
  {{ JSON.stringify(searchParams) }}
  <!--  标签页  -->
  <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
    <a-tab-pane key="post" tab="文章">
      <PostList />
    </a-tab-pane>
    <a-tab-pane key="picture" tab="图片">
      <PictureList />
    </a-tab-pane>
    <a-tab-pane key="user" tab="用户">
      <UserList />
    </a-tab-pane>
  </a-tabs>
</template>

<script setup lang="ts">
import { ref, watchEffect } from "vue";
import HelloWorld from "@/components/HelloWorld.vue";
import PostList from "@/components/PostList.vue";
import UserList from "@/components/UserList.vue";
import PictureList from "@/components/PictureList.vue";
import { useRoute, useRouter } from "vue-router";
// 输入的数据
//const searchText = ref("");
//const activeKey = ref("post");
const router = useRouter();
const route = useRoute();
const activeKey = route.params.category;

// 初始值
const initSearchParams = {
  text: "",
  pageSize: 10,
  pageNum: 1,
};

const searchParams = ref(initSearchParams);

watchEffect(() => {
  searchParams.value = {
    ...initSearchParams,
    text: route.query.text,
  } as any;
});

// 搜索方法
const onSearch = (value: string) => {
  //alert(value);
  router.push({
    // query: {
    //   text: value,
    // },
    query: searchParams.value,
  });
};

const onTabChange = (key: string) => {
  router.push({
    path: `/${key}`,
    query: searchParams.value,
  });
};
</script>
