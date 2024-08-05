<script setup>

import {defineEmits, defineProps, onMounted, ref} from "vue";
import {getReportReasonAPI, postAnswerReportAPI} from "@/api";

const props = defineProps({
  isOpen: Boolean,
  answerId: Number,
  answerInfo: Object
});

const emit = defineEmits(['close']);

const reportReasons = ref([]);
const selectedReason = ref('');

const fetchReportReasons = async () => {
  try {
    const response = await getReportReasonAPI();
    reportReasons.value = response.data;
  } catch (error) {
    console.log(error);
  }
}
onMounted(() => {
  fetchReportReasons();
 });


const closeModal = () => {
  emit('close');
  selectedReason.value = null;
};

const submitReport = async () => {
  if (!selectedReason.value) {
    alert("신고 사유를 선택해주세요.");
    return;
  }

  try {
    await postAnswerReportAPI({
      reportReasonId: selectedReason.value,
      answerId: props.answerId
    });
    alert("신고가 접수되었습니다.");
    closeModal();
  } catch (error) {
    console.error("신고 제출 실패:", error);
    alert("신고 제출에 실패했습니다. 다시 시도해주세요.");
  }
};
</script>

<template>
  <div v-if="isOpen" class="modal-overlay">
    <div class="modal-content">
      <div class="report-top">
        <img src="@/assets/report.png" alt="신고 아이콘">
        <span>신고하기</span>
      </div>
      <div class="report-info">
        <p><strong>작성자 | </strong>{{ answerInfo.writer }}</p>
        <p><strong>내 용 | </strong>{{ answerInfo.content }}</p>
      </div>
      <div class="report-reasons-box">
        <h3>사유선택</h3>
        <div class="report-reasons">
          <div v-for="reportReason in reportReasons" :key="reportReason.id" class="reason-option">
            <input type="radio" :id="reportReason.id" :value="reportReason.id" v-model="selectedReason">
            <label :for="reportReason.id">{{ reportReason.content }}</label>
          </div>
        </div>
      </div>
      <div class="button-group">
        <button class="btn-cancel" @click="closeModal">뒤로가기</button>
        <button class="btn-submit" @click="submitReport">신고하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding-inline: 70px;
  padding-block: 60px;
  border-radius: 30px;
  width: 700px;
}

.report-top {
  width: 100%;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  justify-content: center;
  border-bottom: solid rgba(0, 0, 0, 0.38) 1px;
  padding-bottom: 20px;
}

.report-top img {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.report-top span {
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 30px;
}

.report-info {
  font-family: "NEXON Lv1 Gothic OTF",sans-serif;
  color: #000000;
  font-size: 15px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  justify-content: center;
  padding-bottom: 20px;
  border-bottom: solid rgba(0, 0, 0, 0.2) 0.5px;
}

.report-info p strong{
  font-size: 17px;
  color: #888888;
  margin: 10px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.report-reasons {
  border-radius: 10px;
  border: #C5CCD2 solid 1px;
}

.reason-option {
  padding-inline: 22px;
  padding-block: 13px;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-bottom: #C5CCD2 solid 1px ;
}

.reason-option:last-child {
  border-bottom: none;
}
.reason-option input[type="radio"] {
  appearance: none;
  -webkit-appearance: none;
  width: 24px;
  height: 24px;
  border: 2px solid #ccc;
  border-radius: 50%;
  outline: none;
  margin-right: 10px;
  position: relative;
  cursor: pointer;
}

.reason-option input[type="radio"]:checked {
  border-color: #bd200b; /* 선택 시 테두리 색상 */
}

.reason-option input[type="radio"]:checked::before {
  content: ' ';
  width: 12px;
  height: 12px;
  background-color: #bd200b; /* 선택 시 내부 색상 */
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 중앙 정렬 */
}



.reason-option label {
  font-family: "NEXON Lv1 Gothic OTF", sans-serif;
  font-size: 15px;
  margin-left: 14px;
}

.report-reasons-box h3 {
  margin-block: 20px;
  font-family: "NEXON Lv1 Gothic OTF",sans-serif;
  font-weight: bold;
  font-size: 17px;
}

.button-group {
  display: flex;
  justify-content: center;
  margin-top: 40px;
  gap: 40px;
}

.btn-cancel, .btn-submit {
  width: 90px;
  height: 40px;
  border-radius: 50px;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 12px;
}

.btn-cancel {
  background-color: #f5f5f5;
  border: #f5f5f5 solid 1px;
  box-shadow: 0 0 2px rgba(0, 0, 0, 0.4);
}

.btn-submit {
  background-color: #bd200b;
  color: white;
  border: none;
}
</style>