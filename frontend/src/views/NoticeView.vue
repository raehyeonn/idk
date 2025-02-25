<script setup>
import { deleteNoticeAPI, getNoticeAPI } from "@/api";
import {useRoute} from "vue-router";
import {computed, onMounted, ref} from "vue";
import router from "@/router";
import {jwtDecode} from "jwt-decode";

function decodeJwtToken(token) {
    try {
        return jwtDecode(token);
    } catch (error) {
        console.error("유효하지 않은 토큰입니다.", error);
        return null;
    }
}

const currentMemberRole = computed(() => {
    const token = localStorage.getItem('Authorization');
    const decodedToken = decodeJwtToken(token);

    if(decodedToken && decodedToken.authorities) {
        return decodedToken.authorities;
    }

    return ['GUEST'];
});


const isAdmin = computed(() => currentMemberRole.value.includes('ADMIN'));

const notice = ref(null);

const getNotice = async function () {
    try {
        const response = await getNoticeAPI(useRoute().params.noticeId);
        return response.data;
    } catch (error) {
        console.log(error);
    }
}

const goEditNotice = function () {
    router.push(`/notice/${notice.value.id}/edit`);
};

const deleteNotice = async function () {
    try {
        if (confirm("정말 삭제하시겠습니까?")) {
            await deleteNoticeAPI(notice.value.id);
            await router.push('/notice');
        }
    } catch (error) {
        console.log(error);
    }

};

const formatCreatedAt = computed(() => {
    const createdAt = new Date(notice.value.createdAt);
    return createdAt.toISOString().split('T')[0];
});

onMounted(async () => {
    notice.value = await getNotice();
})
</script>

<template>
    <div class="notice-wrap" v-if="notice">
        <h2>공지사항</h2>
        <div class="notice-top">
            <img src="@/assets/N.png" alt="">
            <div class="notice-text">
                <span class="notice-title">{{notice.title}}</span>
                <div>
                    <span class="notice-info">{{notice.writer.nickname}}&ensp;|&ensp;{{formatCreatedAt}}&ensp;|&ensp;조회수 {{notice.viewCount}}</span>
                </div>
            </div>
        </div>
        <div class="notice-content">
            <pre>{{notice.content}}</pre>
        </div>
        <div class="notice-button" v-if="isAdmin">
            <button class="go-edit-button" @click="goEditNotice">수정</button>
            <button class="delete-button" @click="deleteNotice">삭제</button>
        </div>
    </div>
</template>

<style scoped>
.notice-wrap {
    width: 100%;
}

h2 {
    padding: 15px;
    border-bottom: 3px solid #000000;

    font-family: 'Gmarket Bold', sans-serif;
    font-size: 30px;
    color: #000000;
}

.notice-top {
    display: flex;
    border-bottom: 1px dashed #000000;
    padding: 15px;
}

.notice-title {
    font-family: 'Nexon Medium', sans-serif;
    font-size: 25px;
    color: #000000;
}
.notice-info {
    font-family: 'Gmarket Regular', sans-serif;
    font-size: 10px;
    color: #000000;
}
.notice-text {
    display: flex;
    flex-direction: column;
    justify-content: center;

}
img {
    width: 50px;
    height: 50px;
    margin-right: 15px;
}
pre {
    padding: 15px;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
    color: #000000;
    min-height: 400px;
    white-space: pre-wrap;
}
.notice-button {
    display: flex;
    justify-content: end;
    padding-right: 15px;
}
.go-edit-button {
    width: 50px;
    height: 30px;
    border-radius: 50px;
    border: 1px solid #FFFFFF;
    box-shadow: 0 0 5px #000000;
    background-color: #FFFFFF;
    color: #000000;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
    margin-right: 15px;
}
.delete-button {
    width: 50px;
    height: 30px;
    border-radius: 50px;
    border: 1px solid #333A73;
    box-shadow: 0 0 5px #000000;
    background-color: #333A73;
    color: #FFFFFF;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}
</style>
