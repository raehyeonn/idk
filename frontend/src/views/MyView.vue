<script setup>
import {onMounted, ref} from "vue";
import {deleteMeAPI, getMeAPI, getMyAnswersAPI, getMyQuestionsAPI} from "@/api";
import router from "@/router";

const me = ref({});
const questions = ref({});
const answers = ref({});
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
        await deleteMeAPI();
        localStorage.removeItem('Authorization');
        document.cookie = "refresh_token=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
        router.push('/');
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
});
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

        <ul class = "menu">
          <li class="my-questions" @click="goQuestions">내가 작성한 질문</li>
          <li class="my-answers" @click="goAnswers">내가 작성한 답변</li>
        </ul>
      </div>

      <button class="delete-account" @click="confirmDeleteAccount">탈퇴하기</button>
    </div>


    <div class="my-page-right" v-show="isQuestions">
        <h2 class="my-question-list">나의 질문 목록({{questions.totalElements}})</h2>

        <ul v-for="question in questions.content" :key="question.id">
            <li>
                <div class="question-wrap" @click="goQuestion(question.id)">
                    <div class="question-information-top">
                        <b>{{question.title}}</b>
                    </div>
                    <div class="question-information-bottom">
                        <span>{{ formatDate(question.createdAt) }} | 조회수 {{ question.viewCount }}</span>
                        <span>{{ question.answerCount }}개의 답변</span>
                    </div>
                </div>
            </li>
        </ul>
    </div>

    <div class="my-page-right" v-show="isAnswers">
        <p class="my-question-list">나의 답변 목록({{answers.totalElements}})</p>

        <ul v-for="answer in answers.content" :key="answer.id">
            <li>
                <div class="answer-wrap" @click="goQuestionByAnswer(answer.id)">
                    <div class="question-information">
                        <p class="answer-question">질문</p>
                        <p class="answer-question-content">{{answer.title}}</p>
                    </div>
                    <div class="answer-information">
                        <div class="answer-information-left">
                            <p class="answer">나의 답변</p>
                            <p class="answer-content">{{answer.answerDetail.content}}</p>
                        </div>
                        <div class="answer-selected" v-if="answer.answerDetail.selected"><span>채택</span></div>
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
  font-size: 15px;
 text-align: center;
    border-bottom: 1px solid #C5CCD2;
    padding-bottom: 15px;
}

.my-count {
  display: flex;
  width: 100%;
  border-bottom: 1px solid #C5CCD2;

  padding: 15px 0 15px 0;
}

.my-question-count, .my-answer-count {
  width: 50%
}

.count-questions, .count-answers {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 15px;
  margin-bottom: 10px;
}

.count {
  font-family: 'Nexon Bold', sans-serif;
  font-size: 15px;
}

.menu {
    padding-top: 15px
}

.my-questions {
    cursor: pointer;
    list-style-position: inside;

    margin-bottom: 15px;

    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}

.my-answers {
    cursor: pointer;
    list-style-position: inside;

    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
    color: #C5CCD2;
}

.my-page-right {
  width: 75%;
  padding-left: 15px;
}

.my-page-right ul {
    list-style-type: none;
    padding: 15px;
    border: 1px solid #C5CCD2;
    border-radius: 5px;
    margin-bottom: 15px;
}

.my-question-list {
    padding-bottom: 15px;

    font-family: 'Nexon Bold', sans-serif;
    font-size: 30px;
}

.question-list li {
    list-style-type: none;
    margin-bottom: 15px;
}

.question-information-top {
  display: flex;
  justify-content: start;

  padding-bottom: 7px;
}

.question-information-bottom {
  display: flex;
  justify-content: space-between;
}

.question-information-top b {
    cursor: pointer;

    font-family: 'Nexon Light', sans-serif;
    font-size: 15px;
}

.question-information-bottom span {
    cursor: pointer;

    font-family: 'Nexon Medium', sans-serif;
    font-size: 10px;
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
    margin-bottom: 15px;
}




.answer-information {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.answer, .answer-question {
    padding-bottom: 7px;

    font-family: 'Nexon Light', sans-serif;
    font-size: 10px;
}

.answer-content, .answer-question-content {
    cursor: pointer;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}

.answer-selected {
    display: flex;
    justify-content: center;
    align-items: center;

    width: 50px;
    height: 30px;
    border-radius: 50px;
    background-color: #FBA834;
}

.answer-selected span {
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}
.question-wrap {
    cursor: pointer;
}
.answer-wrap {
    cursor: pointer;
}

.delete-account {
    cursor: pointer;
    align-self: end;

    width: 80px;
    height: 40px;
    color: #FFFFFF;
    background-color: #bd200b;
    transition: background-color 0.3s;
    border: none;
    border-radius: 5px;
    box-shadow: 0 0 1px #000000;

    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}
</style>
