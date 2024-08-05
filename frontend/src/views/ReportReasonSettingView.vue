<script setup>

</script>

<template>
  <div class="wrap">
    <div class="add-wrap">
      <h2>신고 사유 추가</h2>
      <textarea name="report-reason" placeholder="신고 사유를 입력해 주세요." maxlength="100"></textarea>
      <div class="action-button">
        <button class="submit-button">추가</button>
      </div>
    </div>
    <h2>신고 사유 목록</h2>
    <ul>
      <li>
        <div class="reason-wrap">
          <span class="reason">불법 정보를 포함하고 있습니다.</span>
          <span class="reason-delete">삭제</span>
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
  font-family: 'Nexon Regular', sans-serif;
  font-size: 20px;
}

.reason-delete {
  font-family: 'Nexon Regular', sans-serif;
  font-size: 20px;
}
</style>
