<script setup>
import {
  createAnswerAPI,
  deleteAnswerAPI,
  deleteQuestionAPI, editAnswerAPI,
  getQuestionAPI,
  selectAnswerAPI
} from "@/api";
import {useRoute} from "vue-router";
import {computed, nextTick, onMounted, ref, watch} from "vue";
import router from "@/router";
import ReportQuestionModal from "@/views/ReportQuestionModal.vue";
import ReportAnswerModal from "@/views/ReportAnswerModal.vue";

const question = ref(null);
const content = ref("");
const route = useRoute();
const selectedAnswer = ref(null);

const isReportQuestionModalOpen = ref(false);
const questionInfo = ref({});

const isReportAnswerModalOpen = ref(false);
const answerInfo = ref({});

const currentUserId = computed(() => {
  return parseInt(sessionStorage.getItem('userId') || '0');
});

const isQuestionAuthor = computed(() => {
  return question.value && question.value.writer.id === currentUserId.value;
});

const userRoles = computed(() => {
    const rolesString = sessionStorage.getItem('roles');
    return rolesString ? rolesString.split(' ') : [];
});

const isAdmin = computed(() => userRoles.value.includes('ADMIN'));

const openReportQuestionModal = () => {
  questionInfo.value = {
    writer: question.value.writer.nickName,
    title: question.value.title,
    content: question.value.content
  };
  isReportQuestionModalOpen.value = true;
};

const openReportAnswerModal = (answer) => {
  selectedAnswer.value = answer;
  answerInfo.value = {
    writer: answer.writer.nickName,
    content: answer.content
  };
  isReportAnswerModalOpen.value = true;
};

const closeQuestionReportModal = () => {
  isReportQuestionModalOpen.value = false;
};

const closeAnswerReportModal = () => {
  isReportAnswerModalOpen.value = false;
}

const getQuestion = async function () {
  try {
    const response = await getQuestionAPI(route.params.questionId);
    return response.data;

  } catch (error) {
    console.log(error);
  }
}

const autoResize = function () {
  nextTick(() => {
    const textarea = document.querySelector('textarea');
    if (textarea) {
      textarea.style.height = 'auto';
      textarea.style.height = textarea.scrollHeight + 'px';
    }
  })
}

watch(content, () => {
  nextTick(() => {
    autoResize();
  })
})

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};

const deleteQuestion = async function () {
  try {
    if (confirm("정말 삭제하시겠습니까?")) {
      await deleteQuestionAPI(question.value.id);
      await router.push('/');
    }
  } catch (error) {
    console.log(error);
  }
}

const goEditQuestion = function () {
  router.push(`/question/${question.value.id}/edit`);
}

const formatCreatedAt = computed(() => {
  return question.value ? formatDate(question.value.createdAt) : '';
});

const createAnswer = async function (questionId) {
  try {
    const createAnswerRequest = {
      questionId: questionId,
      content: content.value
    };
    await createAnswerAPI(createAnswerRequest);
    question.value = await getQuestion();
    content.value = '';
  } catch (error) {
    console.log(error);
  }
};

const isAnswerAuthor = (answer) => {
  return answer.writer.id === currentUserId.value;
};

const editingAnswer = ref(null);
const editContent = ref('');

const startEditAnswer = (answer) => {
  editingAnswer.value = answer.id;
  editContent.value = answer.content;
};

const cancelEditAnswer = () => {
  editingAnswer.value = null;
  editContent.value = '';
};

const updateAnswer = async (answerId) => {
  try {
    const request = { content: editContent.value };
    const response = await editAnswerAPI(answerId, request);
    const updatedAnswer = response.data;

    // 질문 객체 내의 해당 답변만 업데이트
    const answerIndex = question.value.answers.findIndex(a => a.id === answerId);
    if (answerIndex !== -1) {
      question.value.answers[answerIndex] = updatedAnswer;
    }
    cancelEditAnswer();
  } catch (error) {
    console.error('답변 수정 실패:', error);
  }
};


const deleteAnswer = async (answerId) => {
  if (confirm('정말로 이 답변을 삭제하시겠습니까?')) {
    try {
      await deleteAnswerAPI(answerId);
      question.value = await getQuestion();
    } catch (error) {
      console.error('답변 삭제 실패:', error);
    }
  }
};

const selectAnswer = async (answerId) => {
  try {
    await selectAnswerAPI(answerId);
    question.value = await getQuestion();
  } catch (error) {
    console.error('답변 채택 실패:', error);
  }
};

const hasSelectedAnswer = computed(() => {
  return question.value && question.value.answers.some(answer => answer.selected);
});

onMounted(async () => {
  question.value = await getQuestion();
})

</script>

<template>
  <div class="question-wrap" v-if="question">
    <div>
      <h2>궁금해요</h2>
      <div class="question-top">
        <img src="@/assets/Q.png" alt="">
        <div class="contents-text">
          <span class="contents-title">{{ question.title }}</span>
          <div>
          <span class="question-info">
            {{ question.writer.nickName }} | {{ formatCreatedAt }} | 조회수 {{ question.views }}
          </span>
            <button class="report-button" aria-label="신고하기" @click="openReportQuestionModal"
                    v-if="!isQuestionAuthor">
              <img src="@/assets/report.png" alt="신고 아이콘">
            </button>
          </div>
        </div>
      </div>
      <pre class="question-contents">{{ question.content }}</pre>
      <div class="question-button" v-if="isQuestionAuthor && !question.answers.length">
        <button class="go-edit-button" @click="goEditQuestion">수정하기</button>
        <button class="blue-button" @click="deleteQuestion">삭제하기</button>
      </div>
      <hr>

      <div class="answer-top">
        <img src="@/assets/A.png" alt="">
        <div class="contents-text">
          <span class="contents-title">총 {{ question.answerCount }}개의 답변이 작성되었습니다.</span>
        </div>
      </div>
      <div class="write-wrap">
        <div class="write-answer" v-if="!isAdmin">
        <textarea name="answer-content" v-model="content" placeholder="내용을 입력해 주세요."
                  maxlength="1000" @input="autoResize"></textarea>
          <div>
            <strong>저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 게시물은 관리자에 의해 제재를 받으실 수
              있습니다.</strong>
            <button class="blue-button" @click="createAnswer(question.id)">등록하기</button>
          </div>
        </div>
      </div>
      <ul v-for="answer in question.answers" :key="answer.id">
        <li :class="{ 'selected-answer': answer.selected }">
          <div class="contents-text">
            <div>
              <span class="answer-info">
                {{ answer.writer.nickName }} | {{ formatDate(answer.createdAt) }}
              </span>
              <div>
                <button class="report-button" aria-label="신고하기" @click="openReportAnswerModal(answer)"
                        v-if="!isAnswerAuthor(answer)">
                  <img src="@/assets/report.png" alt="신고 아이콘">
                </button>
              </div>
            </div>
            <pre v-if="editingAnswer !== answer.id" class="answer-contents">{{
                answer.content
              }}</pre>
            <textarea v-else v-model="editContent" class="edit-answer-textarea"></textarea>
            <div class="answer-actions">
              <button v-if="isQuestionAuthor && !hasSelectedAnswer && !answer.selected"
                      class="select-button"
                      @click="selectAnswer(answer.id)">채택
              </button>
              <div>
                <template v-if="isAnswerAuthor(answer) && !answer.selected">
                  <template v-if="editingAnswer !== answer.id">
                    <button class="edit-button" @click="startEditAnswer(answer)">수정</button>
                    <button class="delete-button" @click="deleteAnswer(answer.id)">삭제</button>
                  </template>
                  <template v-else>
                    <button class="save-button" @click="updateAnswer(answer.id)">저장</button>
                    <button class="cancel-button" @click="cancelEditAnswer">취소</button>
                  </template>
                </template>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <ReportQuestionModal
      :isOpen="isReportQuestionModalOpen"
      :questionId="question?.id"
      :questionInfo="questionInfo"
      @close="closeQuestionReportModal"
  />
  <ReportAnswerModal
      :isOpen="isReportAnswerModalOpen"
      :answerId="selectedAnswer?.id"
      :answerInfo="answerInfo"
      @close="closeAnswerReportModal"
  />
</template>

<style scoped>
.question-wrap {
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

.question-top {
  display: flex;
  border-top: 3px solid #000000;
  border-bottom: 1px solid #000000;
  padding: 35px;
}

.contents-title {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 45px;
  color: #333A73;
  vertical-align: bottom;
  margin-top: 20px;
    overflow-x: hidden;
    text-overflow: ellipsis;
    width: 80%;
}

.question-info {
  font-family: 'Gmarket Regular', sans-serif;
  font-size: 20px;
  color: #000000;
}

.contents-text {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

img {
  margin-right: 30px;
  width: 120px;
  height: 135px;
}

.question-contents {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 30px;
  color: #000000;
  margin-block: 80px;
  margin-left: 40px;
    white-space: pre-wrap; /* 줄바꿈을 자동으로 처리 */
    overflow-wrap: break-word; /* 긴 단어가 넘어가지 않도록 자동으로 줄바꿈 */

}
.question-button {
  display: flex;
  justify-content: end;
  margin-right: 5px;
  margin-bottom: 20px;
}

.go-edit-button {
  width: 160px;
  height: 60px;
  border-radius: 50px;
  border: 1px solid #FFFFFF;
  box-shadow: 0 0 5px #000000;
  background-color: #FFFFFF;
  color: #000000;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 25px;
  margin-right: 25px;
}

.blue-button {
  width: 160px;
  height: 60px;
  border-radius: 50px;
  border: 1px solid #333A73;
  box-shadow: 0 0 5px #000000;
  background-color: #333A73;
  color: #FFFFFF;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 25px;
}

.answer-top {
  margin-block: 45px;
  display: flex;
  padding: 35px;
  align-items: center;
  background-color: rgba(251, 168, 52, 0.27);
  border-radius: 20px;
}

.answer-top .contents-title {
  color: #263140;
}

ul {
  list-style-type: none;
  padding: 0;
  margin-top: 20px;
}

li {
  list-style-type: none; /* 추가: 점 제거 */
  margin-bottom: 40px;
  padding: 35px;
  border: 1px solid #b3b3b3;
  border-radius: 20px;
  background-color: #d9d9d9;
}

.selected-answer {
  background-color: #FFFFFF;
  border: 5px solid #FBA834;
}


.answer-info {
  font-family: "Nexon Light", sans-serif;
  font-size: 20px;
  color: #000000;
}

.answer-contents {
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 30px;
  color: #000000;
    white-space: pre-wrap; /* 줄바꿈을 자동으로 처리 */
    overflow-wrap: break-word; /* 긴 단어가 넘어가지 않도록 자동으로 줄바꿈 */
}

.contents-text div {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.report-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
}

.report-button img {
  width: 31px;
  height: 31px;
}

.write-wrap {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 40px;
}

.write-answer {
  background-color: #FFFFFF;
  border-radius: 15px;
  border: 1px solid #000000;
  width: 100%;
  height: auto;
  padding: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.write-answer div {
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.write-answer strong {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  color: #000000;
  flex: 1;
  align-self: end;
}

textarea {
  resize: none;
  background-color: #FFFFFF;
  border: none;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 25px;
  width: 100%;
  height: auto;
  max-height: 400px;
  color: #000000;
  line-height: 40px;
  column-count: 10;
}

textarea:focus {
  outline: none;
}

.answer-actions {
  display: flex;
  margin-top: 10px;
  justify-content: space-between;
}

.answer-actions div {
  display: flex;
  gap: 10px;
  margin-left: auto;
}

.edit-button, .delete-button, .save-button, .cancel-button, .select-button {
  width: 100px;
  height: 50px;
  border-radius: 20px;
  color: white;
  border: none;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  cursor: pointer;
}

.edit-button, .save-button {
  border: 1px solid #FFFFFF;
  box-shadow: 0 0 2px #000000;
  background-color: #FFFFFF;
  color: #000000;
}

.delete-button, .cancel-button {
  border: 1px solid #333A73;
  box-shadow: 0 0 2px #000000;
  background-color: #333A73;
  color: white;
}

.select-button {
  background-color: #FBA834;
}

.edit-answer-textarea {
  width: 100%;
  min-height: 100px;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #d9d9d9;
}
</style>
