<script setup>
import router from "@/router";
import {getNoticesTop5API, getQuestionsAPI} from "@/api";
import {onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
const questions = ref([]);
const notices = ref([]);
let isLoading = ref(false);

// 날짜 yyyy-MM-dd 형식 출력
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};

const goQuestion = function (id) {
    router.push(`/question/${id}`);
}

const goQuestionWrite = function () {
    router.push(`/question/write`);
};

const goNoticeList = function () {
    router.push("/notice");
}

const goNoticeDetail = function (id) {
    router.push(`/notice/${id}`);
}

const getQuestions = async function (query) {
    if (isLoading.value) {
        return;
    }
    isLoading.value = true;
    try {
        if (query === undefined) {
            query = '';
        }
        const getQuestionsRequest = {
            title: query,
            size: 10,
            page: 0
        }
        const response = await getQuestionsAPI(getQuestionsRequest);
        questions.value = response.data.content;
    } catch (error) {
        console.log(error);
    }
}

const getNoticesTop5 = async function () {
    try {
        const response = await getNoticesTop5API();
        notices.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

onMounted(() => {
    getQuestions(route.query.search);
    getNoticesTop5()
})

watch(async () => route.query.search, (newTitle) => {
    getQuestions(newTitle);
})
</script>

<template>

    <div class="wrap">

        <div class="question">
            <div class="question-write">
                <span>지금 이 순간 게시글</span>
                <a class="move" @click="goQuestionWrite">질문하러 가기 ></a>
            </div>

            <ul class="question-list" v-for="question in questions" :key="question.id">
                <li>
                    <div class="question-wrap" @click="goQuestion(question.id)">
                        <div class="question-writer">
                            <span>{{ question.writer.nickName }}</span>
                            <span>{{
                                    formatDate(question.createdAt)
                                }} | 조회수 {{ question.views }}</span>
                        </div>
                        <div class="question-title" >
                            <span class="move">{{ question.title }}</span>
                            <span>{{ question.answerCount }}개의 답변</span>
                        </div>
                        <hr>
                    </div>
                </li>
            </ul>
        </div>

        <div class="notice">
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
                                {{ notice.title }}</p>
                            <p class="notice-date">{{ formatDate(notice.createdAt) }}</p>
                        </div>
                    </div>
                </li>
            </ul>
        </div>

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

    height: 50px;
    margin-bottom: 15px;
    padding: 0 15px;
    background-color: #333A73;
    border: none;
    border-radius: 15px;

    font-family: 'Nexon Bold', sans-serif;
    color: #FFFFFF;
    font-size: 20px;
}

.question-list {
    list-style: none;
    margin-bottom: 15px;
}

.question-writer {
    display: flex;
    justify-content: space-between;

    padding: 0 15px 10px 15px;
}

.question-writer span {
    font-family: 'Nexon Light', sans-serif;
    font-size: 15px;
}

.question-title {
    display: flex;
    justify-content: space-between;
    padding: 0 15px 15px 15px;
}

.question-title span {
    font-family: 'Nexon Regular', sans-serif;
    font-size: 20px;
}

.notice-more {
    display: flex;
    justify-content: space-between;
    align-items: center;

    height: 50px;
    margin-bottom: 15px;
    padding: 0 15px;
    background-color: #FBA834;
    border: none;
    border-radius: 15px;

    font-family: 'Nexon Bold', sans-serif;
    color: #000000;
    font-size: 20px;
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
    margin: 10px 10px 0 0;
}

.notice-title {
    cursor: pointer;
    margin: 0 0 10px 0;
    font-family: 'Nexon Regular', sans-serif;
    font-size: 20px;
}

.notice-date {
    font-family: 'Nexon Light', sans-serif;
    font-size: 15px;
}

.go-notice {
    cursor: pointer;
    width: 100%;
}
</style>
