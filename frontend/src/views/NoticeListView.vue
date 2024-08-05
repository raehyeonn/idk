<script setup>
import router from "@/router";
import {ref, onMounted} from 'vue';
import {getNoticesAPI} from '@/api';

const notices = ref([]);

const goNotice = function (id) {
  router.push(`/notice/${id}`);
}

const fetchNotices = async () => {
  try {
    const response = await getNoticesAPI();
    notices.value = response.data.content;
  } catch (error) {
    console.log(error);
  }
}

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};

onMounted(() => {
  fetchNotices();
});
</script>

<template>
  <div class="notice-wrap">
    <h2>공지사항</h2>
    <div class="notice-top">
      <ul v-if="notices.length">
        <li v-for="notice in notices" :key="notice.id" @click="goNotice(notice.id)">
          <p class="notice-title">{{ notice.title }}</p>
          <div class="notice-info">
            {{notice.writer.nickname}}  |  {{formatDate(notice.createdAt)}}
          </div>
        </li>
      </ul>
      <p class="notice-title" v-else>공지사항이 없습니다.</p>
    </div>
  </div>
</template>

<style scoped>

.notice-wrap {
  width: 100%;
  margin-top: 100px;
}

h2 {
  font-family: 'Gmarket Bold', sans-serif;
  font-size: 47px;
  color: #000000;
  margin-bottom: 35px;
  padding-left: 35px;
}
.notice-top {
  display: flex;
  border-top: 3px solid #000000;
}

ul {
  width: 100%;
  list-style-type: none;
  display: flex;
  flex-direction: column;
}

li {
  width: 100%;
  list-style-type: none;
  border-bottom: 1px solid #000000;
  padding-block: 20px;
  cursor: pointer;
}

.notice-title {
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 24px;
  margin-bottom: 10px;
  margin-left: 10px;
}

.notice-info {
  font-family: "Gmarket Light", sans-serif;
  font-size: 15px;
  margin-left: 10px;
}

</style>
