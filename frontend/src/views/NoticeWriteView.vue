<script setup>
import {nextTick, ref} from "vue";
import router from "@/router";
import {createNoticeAPI} from "@/api";

const title = ref("");
const content = ref("");
const contentTextarea = ref(null);

const handleInput = (event) => {
    const target = event.currentTarget;
    const maxlength = target.getAttribute("maxlength");
    if (target.value.length > maxlength) {
        target.value = target.value.slice(0, maxlength);
    }
}

const goPre = function () {
    router.go(-1);
}

const createNotice = async function () {
    try {
        const createNoticeRequest = {
            title: title.value,
            content: content.value
        }
        const response = await createNoticeAPI(createNoticeRequest);
        await router.push(`/notice/${response.data.id}`);
    } catch (error) {
        console.log(error);
        if (error.response.data.status == 400) {
            if (error.response.data.errors[0].field == 'title') {
                alert("제목을 입력해 주세요.");
                document.querySelector('.question-title').focus();
            } else if (error.response.data.errors[0].field == 'content') {
                alert("내용을 입력해 주세요.");
                await nextTick(() => {
                    contentTextarea.value.focus();
                })
            }
        }
    }
}
</script>

<template>
    <div class="write-wrap">
        <input type="text" class="question-title" v-model="title" placeholder="제목을 입력해 주세요." minlength="2" maxlength="50" @input="handleInput">
        <div class="write-content">
            <textarea ref="contentTextarea" name="question-content" v-model="content" placeholder="내용을 입력해 주세요." @input="handleInput" maxlength="500"></textarea>
        </div>
        <div class="write-button">
            <button class="go-pre-button" @click="goPre">뒤로가기</button>
            <button class="submit-button" @click="createNotice">작성하기</button>
        </div>
    </div>
</template>

<style scoped>
.write-wrap {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 50px;
}

.question-title {
    display: flex;

    width: 100%;
    height: 100px;
    padding-left: 50px;
    margin-bottom: 15px;

    border: 5px solid #333A73;
    border-radius: 50px;

    font-family: 'Nexon Medium', sans-serif;
    font-size: 25px;
    color: #757575;
}

.question-title::placeholder {
    font-family: 'Nexon Medium', sans-serif;
    font-size: 25px;
    color: #757575;
}

.write-content {
    background-color: #EBEBEB;
    border-radius: 15px;
    border: none;
    width: 100%;
    height: 600px;
    padding: 50px;
    margin-bottom: 30px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
textarea {
    resize: none;
    background-color: #EBEBEB;
    border: none;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 25px;
    width: 100%;
    height: 400px;
    color: #757575;
    line-height: 40px;
    column-count: 10;
}
textarea:focus {
    outline: none;
}
.go-pre-button {
    width: 160px;
    height: 75px;
    border-radius: 50px;
    border: 1px solid #FFFFFF;
    box-shadow: 0 0 5px #000000;
    background-color: #FFFFFF;
    color: #000000;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 25px;
    margin-right: 40px;
}
.submit-button {
    width: 160px;
    height: 75px;
    border-radius: 50px;
    border: 1px solid #333A73;
    box-shadow: 0 0 5px #000000;
    background-color: #333A73;
    color: #FFFFFF;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 25px;
}
</style>
