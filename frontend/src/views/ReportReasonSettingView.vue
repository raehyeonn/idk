<script setup>
import {deleteReportReasonAPI, getReportReasonAPI, postReportReasonAPI} from "@/api";
import {nextTick, onMounted, ref} from "vue";

const content = ref("");
const reportReasons = ref([]);
const reasonContent = ref(null);

const createReportReasons = async function() {
  try {
    const createReportReasonRequest = {
      content: content.value
    };
    await postReportReasonAPI(createReportReasonRequest);
    await fetchReportReasons();
    content.value = "";
  } catch (error) {
    console.log(error)
      if (error.response.data.status == 400) {
          alert("신고 사유를 입력해 주세요.");
          await nextTick(() => {
              reasonContent.value.focus();
          })
      }
  }
}
const deleteReportReason = async function(id) {
  try {
    if(confirm("정말 삭제하시겠습니까?")) {
      await deleteReportReasonAPI(id);
      await fetchReportReasons();
    }
  }catch (error) {
    console.log(error);
  }
}

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

</script>

<template>
  <div class="wrap">
    <div class="add-wrap">
      <h2>신고 사유 추가</h2>
      <textarea ref="reasonContent" name="report-reason" placeholder="신고 사유를 입력해 주세요." maxlength="100" v-model="content"></textarea>
      <div class="action-button">
        <button class="submit-button" @click="createReportReasons()">추가</button>
      </div>
    </div>
    <h2>신고 사유 목록</h2>
    <ul v-for="reportReason in reportReasons" :key="reportReason.id">
      <li>
        <div class="reason-wrap">
          <span class="reason">{{reportReason.content}}</span>
          <span class="reason-delete" @click="deleteReportReason(reportReason.id)">삭제</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.wrap {
  width: 100%;
  margin-top: 50px;
}

.add-wrap {
  display: flex;
  flex-direction: column;
  margin-bottom: 30px;
}

h2 {
  padding: 0 15px;
  margin-bottom: 30px;

  font-family: "Gmarket Bold", sans-serif;
  font-size: 40px;
}

textarea {
  resize: none;
  background-color: #EBEBEB;
  border: none;
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  width: 100%;
  height: 100px;
  color: #757575;
  line-height: 40px;
  column-count: 10;
  overflow: hidden;
  border-radius: 15px;
  padding: 30px;
  margin-bottom: 15px;
}

.action-button {
  display: flex;
  margin-left: auto;
}

.submit-button {
  width: 68px;
  height: 40px;
  background-color: #BD200B;
  border-radius: 50px;
  border: none;
  box-shadow: 0 0 5px #000000;

  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  color: #FFFFFF;
}

ul {
  list-style: none;
  border-bottom: 1px solid #C5CCD2;
  padding: 0 15px;
  margin-bottom: 15px;
}
li {
  margin-bottom: 15px;
}

.reason-wrap {
  display: flex;
  justify-content: space-between;
}

.reason {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
}

.reason-delete {
  font-family: 'Nexon Medium', sans-serif;
  font-size: 20px;
  cursor: pointer;
}
</style>
