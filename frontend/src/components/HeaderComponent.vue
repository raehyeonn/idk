<script setup>
import router from "@/router";
import {computed, ref, watch} from "vue";
import {useRoute} from "vue-router";
import {jwtDecode} from "jwt-decode";
import axios from "axios";

const route = useRoute();
const search = ref("");
const isAuthenticated = ref(localStorage.getItem('Authorization'));

function decodeJwtToken(token) {
    try {
        return jwtDecode(token);
    } catch (error) {
        console.error("유효하지 않은 토큰입니다.", error);
        return null;
    }
}

const role = computed(() => {
    const token = localStorage.getItem('Authorization');
    const decodedToken = decodeJwtToken(token);

    if(decodedToken && decodedToken.authorities) {
        return decodedToken.authorities;
    }

    return [];
});

const isAdmin = computed(() => role.value.includes('ADMIN'));

const goHome = function () {
  router.push({name: 'Home', query: {}}); // 쿼리 파라미터 제거
}

const goLogin = function () {
    router.push("/login");
}

const goSignup = function () {
    router.push("/signup");
}

const goMy = function () {
    router.push("/my");
}

const goAdmin = function () {
  router.push("/admin")
}

const goQuestions = function () {
  if (search.value.trim()) {
    router.push({name: 'Home', query: {search: search.value.trim()}});
  } else {
    router.push({name: 'Home', query: {}}); // 검색어가 없으면 쿼리 파라미터 제거
  }
}

const logout = async function () {

    await axios.post('/api/auth/logout');

    localStorage.removeItem('Authorization');
    document.cookie = "refresh_token=; expires=Thu, 01 Jan 1970 00:00:00 GMT";

    isAuthenticated.value = false;

    router.push('/');
    location.reload();

}

watch(() => route.query.search, (newSearch) => {
  search.value = newSearch || '';
}, { immediate: true });

</script>

<template>
    <div class="header-wrap">
        <div class="header-left">
            <a href="" @click="goHome">
                <h1>I DON'T KNOW</h1>
                <img src="@/assets/logo.png" alt="메인 페이지로 이동">
            </a>
            <input type="text" class="header-search" v-model="search" @keyup.enter="goQuestions">
        </div>
        <div class="header-right" v-if="!isAuthenticated">
            <button class="go-signup-button" @click = "goSignup">회원가입</button>
            <button class="go-login-button" @click = "goLogin">로그인</button>
        </div>
        <div class="header-right" v-if="isAuthenticated">
          <button class="go-signup-button" @click="isAdmin ? goAdmin() : goMy()">
            {{ isAdmin ? '관리자 페이지' : '마이페이지' }}
          </button>
            <button class="go-login-button" @click = "logout">로그아웃</button>
        </div>
    </div>
</template>

<style scoped>
.header-wrap {
    width: 60vw;
    height: 100px;
    display: flex;
    align-items: center;
}

.header-left {
    display: flex;
    align-items: center;
    width: 65%;
    margin-right: 20px;
}




.header-left a{
    display: flex;
    align-items: center;
    margin-right: 20px;

}
img {
    max-width: 290px;
    width: 100%;
}


/*숨기기*/
h1 {
    position: absolute;
    width: 1px;
    height: 1px;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    border: 0;
}

.header-left input {
    max-width: 525px;
    width: 100%;
    height: 40px;
    border: 3px solid #333A73;
    border-radius: 20px;
    padding-left: 50px;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 14px;
    line-height: 45px;
    letter-spacing: 1px;
    margin-left: auto;
    background-image: url("@/assets/search.png");
    background-repeat: no-repeat;
    background-position: 10px center;
    background-size: 25px;
}

.header-right {
    width: 30%;
    display: flex;
    flex: 1;
    justify-content: end;
}

.go-signup-button {
    width: 100px;
    height: 40px;
    border-radius: 50px;
    border: 1px solid #333A73;
    box-shadow: 0 0 5px #000000;
    background-color: #333A73;
    color: #FFFFFF;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
}

.go-login-button {
    width: 80px;
    height: 40px;
    border-radius: 50px;
    border: 1px solid #FFFFFF;
    box-shadow: 0 0 5px #000000;
    background-color: #FFFFFF;
    color: #000000;
    font-family: 'Nexon Medium', sans-serif;
    font-size: 15px;
    margin-left: 20px;
}
@media (max-width: 1024px) {
    .header-wrap {
        width: 90vw;
        height: 100px;
        display: flex;
        align-items: center;
    }
}
</style>
