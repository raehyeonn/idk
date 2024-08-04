<script setup>
//import router from "@/router";
import {onMounted, ref} from 'vue';
import {
  deleteReportedAnswerAPI,
  getReportedAnswerAPI,
  handleReportedAnswerAPI,
} from '@/api';
import arrowDown from '@/assets/arrowDown.png';
import arrowUp from '@/assets/arrowUp.png';

const reportedAnswers = ref([]);
const expandedAnswers = ref(new Set());

const fetchReports = async () => {
  try {
    const response = await getReportedAnswerAPI();
    reportedAnswers.value = response.data.content;
  } catch (error) {
    console.log(error);
  }
}

const handleReportedAnswers = async function (id) {
  try {
    if (confirm("해당유저를 정지 및 게시물을 가리시겠습니까?")) {
      await handleReportedAnswerAPI(id);
      await fetchReports();
    }
  } catch (error) {
    console.log(error);
  }
}

const deleteReportedAnswer = async function (id) {
  try {
    if (confirm("신고를 처리하지 않고 삭제하시겠습니까?")) {
      await deleteReportedAnswerAPI(id)
      await fetchReports();
    }
  } catch (error) {
    console.log(error);
  }
}

const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};

const toggleAnswer = (answerId) => {
  if (expandedAnswers.value.has(answerId)) {
    expandedAnswers.value.delete(answerId);
  } else {
    expandedAnswers.value.add(answerId);
  }
};

const isExpanded = (answerId) => expandedAnswers.value.has(answerId);

onMounted(() => {
  fetchReports();
});

</script>

<template>
  <div class="reported-answers-wrap">
    <h2>신고 접수된 답변 ( {{ reportedAnswers.length }} ) </h2>
    <ul v-if="reportedAnswers.length">
      <li v-for="reportedAnswer in reportedAnswers" :key="reportedAnswer.answerReportId">
        <div class="report-contents" @click="toggleAnswer(reportedAnswer.answerReportId)">
          <div class="report-header">
            <img class="toggle-icon"
                 :src="isExpanded(reportedAnswer.answerReportId) ? arrowUp : arrowDown"
                 alt="">
            <div class="report-text">
              <div class="report-title">
                {{ reportedAnswer.reportReason }}
              </div>
              <span class="report-info">
               {{reportedAnswer.reporter}} | {{ formatDate(reportedAnswer.createdAt) }}
              </span>
            </div>
          </div>
          <div v-if="isExpanded(reportedAnswer.answerReportId)" class="answer-details">
            <div class="answer-info">
              <p class="reported-contents">신고 내용</p>
              <p class="answer-content">{{ reportedAnswer.content }}</p>
            </div>
            <div class="report-bottom">
              <p class="reported-contents">신고 대상</p>
              <p class="answer-content">{{ reportedAnswer.writer.nickname }}</p>
            </div>
            <div class="action-buttons">
              <button @click.stop="handleReportedAnswers(reportedAnswer.answerReportId)"
                      class="handle-report">정지
              </button>
              <button @click.stop="deleteReportedAnswer(reportedAnswer.answerReportId)"
                      class="delete-report">삭제
              </button>
            </div>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.reported-answers-wrap {
  width: 100%;
  margin-top: 120px;
}

h2 {
  font-family: "Nexon Light", sans-serif;
  font-size: 36px;
}

.toggle-icon {
  margin-left: 10px;
  margin-top: 3px;
  width: 20px;
  height: 20px;
}

.report-header {
  cursor: pointer;
  display: flex;
  flex-direction: row;
}

li {
  list-style: none;
}

.report-contents {
  display: flex;
  flex-direction: column;
  margin-top: 40px;
}

.report-text {
  margin-left: 25px;
  display: flex;
  flex-direction: column;
}

.report-title {
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 25px;
}

.report-info {
  font-family: "Gmarket Light", sans-serif;
  font-size: 15px;
  margin-top: 10px;
}

.answer-details {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-left: 50px;
  margin-top: 10px;
  padding-inline: 20px;
  padding-block: 20px;
  background-color: #d9d9d9;
  border-radius: 15px;
  width: 100%;
}

.reported-contents {
  font-family: "Gmarket Light", sans-serif;
  font-size: 20px;
  margin-block: 10px;
}
.answer-title {
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 22px;
  margin-block: 10px;
  margin-left: 20px;
}

.answer-content {
  font-family: "Nexon Light", sans-serif;
  font-size: 22px;
  margin-left: 20px;
}

.action-buttons {
  display: flex;
  margin-top: 20px;
  gap: 20px;
  margin-left: auto;
}

.handle-report {
  width: 140px;
  height: 60px;
  border-radius: 50px;
  border: 1px solid #333A73;
  box-shadow: 0 0 5px #000000;
  background-color: #333A73;
  color: #FFFFFF;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 25px;
}

.delete-report {
  width: 140px;
  height: 60px;
  border-radius: 50px;
  border: 1px solid #FFFFFF;
  box-shadow: 0 0 5px #000000;
  background-color: #FFFFFF;
  color: #000000;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 25px;
}

</style>