<script setup>
import { jwtDecode } from 'jwt-decode';
import router from "@/router";
import {getNoticesTop5API, getQuestionsAPI} from "@/api";
import {computed, onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const questions = ref([]);
const notices = ref([]);
let isLoading = ref(false);

function decodeJwtToken(token) {
  try {
    return jwtDecode(token);
  } catch (error) {
    console.error("유효하지 않은 토큰입니다.", error);
    return null;
  }
}

const role = computed(() => {
    const token = localStorage.getItem('Authorization');
    const decodedToken = decodeJwtToken(token);

    if(decodedToken && decodedToken.authorities) {
        return decodedToken.authorities;
    }

    return ['GUEST'];
});

const isAdmin = computed(() => role.value.includes('ADMIN'));
const isMember = computed(() => role.value.includes('MEMBER'));
const isUnverified = computed(() => role.value.includes('UNVERIFIED'));
const isSuspended = computed(() => role.value.includes('SUSPENDED'));
const isDeleted = computed(() => role.value.includes('DELETED'));
const isGuest = computed(() => role.value.includes('GUEST'));

// 날짜 yyyy-MM-dd 형식 출력
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};

const goQuestion = function (id) {
    router.push(`/question/${id}`);
}

const goQuestionWrite = function () {
    if (!isMember.value) {
        alert("로그인이 필요한 서비스입니다.");
        router.push(`/login`);
    } else {
        router.push(`/question/write`);
    }
};

const goNoticeList = function () {
    router.push("/notice");
}

const goNoticeDetail = function (id) {
    router.push(`/notice/${id}`);
}

const currentPage = ref(0);
const totalPages = ref(0);
const pageSize = 10;

const getQuestions = async function (query = '', page = 0) {
  if (isLoading.value) return;
  isLoading.value = true;
  try {
    const getQuestionsRequest = {
      title: query,
      size: pageSize,
      page: page
    }
    const response = await getQuestionsAPI(getQuestionsRequest);
    console.log(response);  // 응답 전체를 찍어보세요
      console.log(response.data.content);  // 실제 content가 제대로 있는지 확인
    questions.value = response.data.content;
    totalPages.value = response.data.totalPages;
    currentPage.value = page;
  } catch (error) {
    console.log(error);
  } finally {
    isLoading.value = false;
  }
}

const changePage = (page) => {
  getQuestions(route.query.search, page);
}

const pageNumbers = computed(() => {
  const pages = [];
  for (let i = 0; i < totalPages.value; i++) {
    pages.push(i);
  }
  return pages;
});

const getNoticesTop5 = async function () {
    try {
        const response = await getNoticesTop5API();
        notices.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

onMounted(() => {
  getQuestions(route.query.search || ''); // 검색어가 없으면 빈 문자열 전달
  getNoticesTop5();
})

watch(() => route.query.search, (newTitle) => {
  getQuestions(newTitle || ''); // 검색어가 없으면 빈 문자열 전달
})
</script>

<template>

    <div class="wrap">

        <section class="question">
            <div class="question-write">
                <span>
                    지금 이 순간 게시글
                </span>
                <a class="move" @click="goQuestionWrite" v-if="!isGuest && !isAdmin && !isUnverified && !isSuspended && !isDeleted">
                    질문하러 가기 >
                </a>
            </div>

            <ul class="question-list" v-for="question in questions" :key="question.id">
                <li>
                    <div class="question-wrap" @click="goQuestion(question.id)">
                        <div class="question-writer">
                            <span>{{ question.writer.nickname }}</span>
                            <span>{{ formatDate(question.createdAt) }} | 조회수 {{ question.viewCount }}</span>
                        </div>
                        <div class="question-title" >
                            <span class="move question-title1">{{ question.title }}</span>
                            <span class="question-title2">{{ question.answerCount }}개의 답변</span>
                        </div>
                    </div>
                </li>
            </ul>

            <div class="pagination">
                <button v-for="page in pageNumbers" :key="page" @click="changePage(page)" :class="{ active: currentPage === page }">
                    {{ page + 1 }}
                </button>
            </div>
        </section>

        <section class="notice">
            <div class="notice-more">
                <p>공지사항</p>
                <a class="move" @click="goNoticeList()">더보기 ></a>
            </div>

            <ul class="notice-list" v-for="notice in notices" :key="notice.id">
                <li>
                    <div class="notice-wrap">
                        <img src="@/assets/yellow-dot.svg" alt="" class="dot">
                        <div class="go-notice" @click="goNoticeDetail(notice.id)">
                            <p class="notice-title" >
                                {{ notice.title }}
                            </p>
                            <p class="notice-date">
                                {{ formatDate(notice.createdAt) }}
                            </p>
                        </div>
                    </div>
                </li>
            </ul>
        </section>

    </div>

</template>

<style scoped>

.wrap {
    display: flex;
    justify-content: space-between;

    width: 100%;
}

.question {
    width: 65%;
}

.notice {
    width: 30%;
}
.move {
    cursor: pointer;
}
.question-wrap {
    cursor: pointer;
}
.question-write {
    display: flex;
    justify-content: space-between;
    align-items: center;

    height: 35px;
    margin-bottom: 15px;
    padding: 0 15px;
    background-color: #333A73;
    border: none;
    border-radius: 5px;

    font-family: 'Nexon Bold', sans-serif;
    color: #FFFFFF;
    font-size: 15px;
}

.question-list {
    list-style: none;
    margin-bottom: 15px;
    border-bottom: 1px solid #C5CCD2;
}

.question-writer {
    display: flex;
    justify-content: space-between;

    padding: 0 15px 7px 15px;
}

.question-writer span {
    font-family: 'Nexon Light', sans-serif;
    font-size: 10px;
}

.question-title {
    display: flex;
    justify-content: space-between;
    padding: 0 15px 15px 15px;
}

.question-title span {
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}

.notice-more {
    display: flex;
    justify-content: space-between;
    align-items: center;

    height: 35px;
    margin-bottom: 15px;
    padding: 0 15px;
    background-color: #FBA834;
    border: none;
    border-radius: 5px;

    font-family: 'Nexon Bold', sans-serif;
    color: #000000;
    font-size: 15px;
}

.notice-list {
    list-style: none;
    margin-bottom: 15px;
}

.notice-wrap {
    display: flex;

}

.dot {
    width: 10px;
    height: 10px;
    margin-top: 4px;
    margin-right: 7px;
}

.notice-title {
    cursor: pointer;
    padding-bottom: 7px;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}

.notice-date {
    font-family: 'Nexon Light', sans-serif;
    font-size: 10px;
}

.go-notice {
    cursor: pointer;
    width: 100%;
}
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination button {
  margin: 0 5px;
  padding: 5px 10px;
  border: 1px solid #333A73;
  background-color: #FFFFFF;
  color: #333A73;
  cursor: pointer;
  font-family: 'Nexon Medium', sans-serif;
}

.pagination button.active {
  background-color: #333A73;
  color: #FFFFFF;
}
.question-title1 {
    flex: 5;
    overflow-x: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
.question-title2 {
    flex: 3;
    text-align: end;
}
</style>
