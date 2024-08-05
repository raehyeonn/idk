<script setup>
import {onMounted, ref} from "vue";
import {deleteMeAPI, getMeAPI, getMyAnswersAPI, getMyQuestionsAPI} from "@/api";
import router from "@/router";

const me = ref({});
const questions = ref([]);
const answers = ref([]);
const page = ref(0);
const isQuestions = ref(true);
const isAnswers = ref(false);

const getMe = async function () {
    try {
        const response = await getMeAPI();
        me.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};


const goQuestions = function () {
    isQuestions.value = true;
    isAnswers.value = false;
    document.querySelector('.my-questions').style.color = '#000000';
    document.querySelector('.my-answers').style.color = '#C5CCD2';
};

const goAnswers = function () {
    isQuestions.value = false;
    isAnswers.value = true;
    document.querySelector('.my-answers').style.color = '#000000';
    document.querySelector('.my-questions').style.color = '#C5CCD2';
};

const getMyQuestions = async function () {
    try {
        const getMyQuestionsRequest = {
            size: 10,
            page: page.value
        }
        const response = await getMyQuestionsAPI(getMyQuestionsRequest);
        questions.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

const getMyAnswers = async function () {
    try {
        const getMyAnswersRequest = {
            size: 10,
            page: page.value
        }
        const response = await getMyAnswersAPI(getMyAnswersRequest);
        answers.value = response.data;
    } catch (error) {
        console.log(error);
    }
};

const confirmDeleteAccount = async () => {
  if (confirm("정말로 탈퇴하시겠습니까? 이 작업은 취소할 수 없습니다.")) {
    try {
      await deleteMeAPI(); // API 호출
      // 로그아웃 처리
      sessionStorage.removeItem('authHeader');
      sessionStorage.removeItem('userId');
      sessionStorage.setItem('roles', 'ANONYMOUS');
      // 홈페이지로 리다이렉트
      await router.push('/')
      location.reload();
    } catch (error) {
      console.error("계정 삭제 실패:", error);
      alert("계정 삭제에 실패했습니다. 다시 시도해주세요.");
    }
  }
};

const goQuestion = function (id) {
    router.push(`/question/${id}`);
};

const goQuestionByAnswer = function (id) {
    router.push(`/question/${id}`);
};

onMounted(async () => {
    await getMe();
    await getMyQuestions();
    await getMyAnswers();
})
</script>

<template>
<div class="wrap">

    <div class="my-page-left">
      <div class="left-top">
        <p class="my-nickname">{{ me.nickname }}</p>

        <div class="my-count">

          <div class="my-question-count">
            <p class="count-questions">나의 질문</p>
            <p class="count">{{questions.totalElements}}</p>
          </div>

          <div class="my-answer-count">
            <p class="count-answers">나의 답변</p>
            <p class="count">{{ answers.totalElements }}</p>
          </div>

        </div>

        <ul>
          <li class="my-questions" @click="goQuestions">나의 질문</li>
          <li class="my-answers" @click="goAnswers">나의 답변</li>
        </ul>
      </div>

      <button class="delete-account" @click="confirmDeleteAccount">탈퇴하기</button>
    </div>


    <div class="my-page-right" v-show="isQuestions">
      <p class="my-question-list">나의 질문 목록({{questions.totalElements}})</p>

      <ul class="question-list" v-for="question in questions.content" :key="question.id">
        <li>
          <div class="question-wrap" @click="goQuestion(question.id)">
            <div class="question-information-top">
                <b class="move">{{question.title}}</b>
            </div>
            <div class="question-information-bottom">
                <span>{{ formatDate(question.createdAt) }} | 조회수 {{ question.views }}</span>
              <span>{{ question.answerCount }}개의 답변</span>
            </div>
          </div>
        </li>
      </ul>

    </div>
    <div class="my-page-right" v-show="isAnswers">
        <p class="my-question-list">나의 답변 목록({{answers.totalElements}})</p>

        <ul class="answer-list" v-for="answer in answers.content" :key="answer.id">
            <li>
                <div class="answer-wrap" @click="goQuestionByAnswer(answer.questionId)">
                    <div class="question-information">
                        <p class="answer-question">질문</p>
                        <p class="answer-question-content">{{answer.title}}</p>
                    </div>
                    <div class="answer-information">
                        <div class="answer-information-left">
                            <p class="answer">나의 답변</p>
                            <p class="answer-content">{{answer.myAnswer.content}}</p>
                        </div>
                        <div class="answer-selected" v-if="answer.myAnswer.isSelected"><span>채택</span></div>
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
  width: 100%;
}

.my-page-left{
  width: 25%;
  padding-right: 15px;
  border-right: 1px solid #C5CCD2;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.my-nickname {
  font-family: 'Nexon Bold', sans-serif;
  font-size: 40px;
}

.my-count {
  display: flex;
  width: 100%;

  border-top: 1px solid #C5CCD2;
  border-bottom: 1px solid #C5CCD2;

  margin: 30px 0 30px 0;
  padding: 15px 0 15px 0;
}

.my-question-count, .my-answer-count {
  width: 50%
}

.count-questions, .count-answers {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  margin-bottom: 10px;
}

.count {
  font-family: 'Nexon Bold', sans-serif;
  font-size: 20px;
}

.my-questions {
  cursor: pointer;

  margin-bottom: 15px;
  list-style-position: inside;

  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
}

.my-answers {
  cursor: pointer;

  list-style-position: inside;

  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  color: #C5CCD2;
}

.my-page-right {
  width: 75%;
  padding-left: 15px;
}

.my-question-list {
  margin-bottom: 15px;
  padding: 0 15px 30px 15px;
  border-bottom: 3px solid #000000;

  font-family: 'Nexon Bold', sans-serif;
  font-size: 40px;
}

.question-list {
  padding: 0 15px;
  list-style: none;
  border-bottom: 1px solid #C5CCD2;
  margin-bottom: 15px;
}

.question-list li {
  margin-bottom: 15px;
}

.question-information-top {
  display: flex;
  justify-content: start;
  margin-bottom: 10px;
}

.question-information-bottom {
  display: flex;
  justify-content: space-between;
}

.question-information-top p {
  font-family: 'Nexon Light', sans-serif;
  font-size: 15px;
}

.question-information-bottom p {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
}

.move {
  cursor: pointer;
}

.answer-list {
    list-style: none;
    margin-bottom: 15px;
    padding: 0 15px;
    border-bottom: 1px solid #C5CCD2;
}

.answer-list li {
    margin-bottom: 15px;
}

.question-information {
    margin-bottom: 20px;
}

.question-information-top b {
    margin-bottom: 10px;

    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}

.question-information-bottom span {
    cursor: pointer;

    font-family: 'Nexon Light', sans-serif;
    font-size: 15px;
}

.answer-information {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.answer, .answer-question {
    margin-bottom: 10px;

    font-family: 'Nexon Light', sans-serif;
    font-size: 15px;
}

.answer-content, .answer-question-content {
    cursor: pointer;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 20px;
}

.answer-selected {
    display: flex;
    justify-content: center;
    align-items: center;

    width: 70px;
    height: 45px;
    border-radius: 50px;
    background-color: #FBA834;
}

.answer-selected span {
    font-family: 'Nexon Medium', sans-serif;
    font-size: 20px;
}
.question-wrap {
    cursor: pointer;
}
.answer-wrap {
    cursor: pointer;
}

.delete-account {
  margin-top: auto;
  padding: 5px 20px;
  border-radius: 5px;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
  align-self: end;
  border: 1px solid #bd200b;
  box-shadow: 0 0 1px #000000;
  background-color: #bd200b;
  color: #FFFFFF;
  width: 35%;
}
</style>
