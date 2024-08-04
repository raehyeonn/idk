<script setup>

import {ref} from 'vue';
import NoticeWriteView from "@/views/NoticeWriteView.vue";
import ReportedQuestionsView from "@/views/ReportedQuestionsView.vue";
// import ReportedAnswersView from "@/views/ReportedAnswersView.vue";

const selectedCategory = ref('notice'); // 'notice', 'questions', 'answers'

const selectCategory = (category) => {
  selectedCategory.value = category;
}
</script>

<template>
  <div class="admin-wrap">
    <div class="admin-info">
      <p>관리자</p>
      <nav class="admin-menu">
        <ul>
          <li @click="selectCategory('notice')" :class="{ active: selectedCategory === 'notice' }">
            공지사항 작성
          </li>
          <li @click="selectCategory('questions')" :class="{ active: selectedCategory === 'questions' }">신고
            접수된 게시물
          </li>
          <li @click="selectCategory('answers')"
              :class="{ active: selectedCategory === 'answers' }">신고 접수된 답변
          </li>
        </ul>
      </nav>
    </div>
    <hr>

    <NoticeWriteView v-if="selectedCategory === 'notice'"/>
    <ReportedQuestionsView v-else-if="selectedCategory === 'questions'"/>
    <ReportedAnswersView v-else-if="selectedCategory === 'answers'"/>


  </div>
</template>

<style scoped>
.admin-wrap {
  width: 100%;
  display: flex;
  flex-direction: row;
}

.admin-info {
  margin-top: 120px;
  margin-right: 40px;
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 20px;
  width: 20%;
}

li {
  margin-left: 20px;
  margin-block: 20px;
  cursor: pointer;
  color: #C5CCD2;
}

li.active {
  color: #000000;
}

hr {
  color: #C5CCD2;
  margin-top: 120px;
  margin-right: 20px;
}

</style>
