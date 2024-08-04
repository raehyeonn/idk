<script setup>

</script>

<template>
  <div class="wrap">

    <div class="my-page-left">
      <p class="my-nickname">홍길동</p>

      <div class="my-count">

        <div class="my-question-count">
          <p class="count-questions">나의 질문</p>
          <p class="count">15</p>
        </div>

        <div class="my-answer-count">
          <p class="count-answers">나의 답변</p>
          <p class="count">4</p>
        </div>

      </div>

      <ul>
        <li class="my-questions">나의 질문</li>
        <li class="my-answers">나의 답변</li>
      </ul>
    </div>


    <div class="my-page-right">
      <p class="my-answer-list">나의 답변 목록(4)</p>

      <ul class="answer-list">
        <li>

          <div class="answer-wrap">

            <div class="question-information">
              <p class="question-information-top">질문</p>
              <p class="question-information-bottom">오리 종류 알려주세요!!!!!!!!</p>
            </div>

            <div class="answer-information">
              <div class="answer-information-left">
                <p class="answer-information-top">나의 답변</p>
                <p class="answer-information-bottom">오리 종류는 집오리, 청둥오리 등이 있습니다.</p>
              </div>
              <div class="answer-selected"><span>채택</span></div>
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

.wrap * {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

.my-page-left {
  width: 25%;
  padding-right: 15px;
  border-right: 1px solid #C5CCD2;
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
  font-family: 'Nexon Regular', sans-serif;
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

  font-family: 'Nexon Regular', sans-serif;
  font-size: 20px;
  color: #C5CCD2;
}

.my-answers {
  cursor: pointer;

  list-style-position: inside;

  font-family: 'Nexon Regular', sans-serif;
  font-size: 20px;
}

.my-page-right {
  width: 75%;
  padding-left: 15px;
}

.my-answer-list {
  margin-bottom: 15px;
  padding: 0 15px 30px 15px;
  border-bottom: 3px solid #000000;

  font-family: 'Nexon Bold', sans-serif;
  font-size: 40px;
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

.question-information-top {
  margin-bottom: 10px;

  font-family: 'Nexon Light', sans-serif;
  font-size: 15px;
}

.question-information-bottom {
  cursor: pointer;

  font-family: 'Nexon Regular', sans-serif;
  font-size: 20px;
}

.answer-information {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.answer-information-top {
  margin-bottom: 10px;

  font-family: 'Nexon Light', sans-serif;
  font-size: 15px;
}

.answer-information-bottom {
  cursor: pointer;
  font-family: 'Nexon Regular', sans-serif;
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
  font-family: 'Nexon Regular', sans-serif;
  font-size: 20px;
}
</style>