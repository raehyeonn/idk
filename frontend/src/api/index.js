import axios from "axios";
import {setInterceptors} from "@/api/interceptors";

const createAuthInstance = function () {
    const instance = axios.create({})
    return setInterceptors(instance);
}
const authInstance = createAuthInstance();

/**
 * UserAPI
 */
const createUserAPI = function (request) {
    return authInstance.post(`/api/users/join`, request);
}

const getMeAPI = function () {
    return authInstance.get(`/api/users/me`);
};
/**
 * QuestionAPI
 */
const createQuestionAPI = function (request) {
    return authInstance.post(`/api/questions`, request)
}

const editQuestionAPI = function (id, request) {
    return authInstance.put(`/api/questions/${id}`, request);
};

const deleteQuestionAPI = function (id) {
    return authInstance.delete(`/api/questions/${id}`);
}

const getQuestionAPI = function (id) {
    return authInstance.get(`/api/questions/${id}`);
}

const getQuestionsAPI = function (request) {
    return authInstance.get(
        `/api/questions?title=${request.title}&size=${request.size}&page=${request.page}`);
}

const getMyQuestionsAPI = function (request) {
    return authInstance.get(`/api/questions/me?size=${request.size}&page=${request.page}`);
};

/**
 * AnswerAPI
 */
const createAnswerAPI = function (request) {
    return authInstance.post(`/api/answers`, request);
}

const editAnswerAPI = function (id, request) {
    return authInstance.put(`/api/answers/${id}`, request)
};

const deleteAnswerAPI = function (id) {
    return authInstance.delete(`/api/answers/${id}`);
};

const selectAnswerAPI = function (id) {
    return authInstance.post(`/api/answers/${id}/selections`);
};

const getMyAnswersAPI = function (request) {
    return authInstance.get(
        `/api/answers/me?size=${request.size}&page=${request.page}`);
};


/**
 * NoticeAPI
 */
const createNoticeAPI = function (request) {
    return authInstance.post(`/api/notices`, request);
}

const editNoticeAPI = function (id, request) {
    return authInstance.put(`/api/notices/${id}`, request)
}

const deleteNoticeAPI = function (id) {
    return authInstance.delete(`/api/notices/${id}`);
};

const getNoticeAPI = function (id) {
    return authInstance.get(`/api/notices/${id}`)
}

const getNoticesTop5API = function () {
    return authInstance.get(`/api/notices/five`)
}

const getNoticesAPI = function () {
    return authInstance.get(`/api/notices`)
}
/**
 * ReportReasonAPI
 */
const getReportReasonAPI = function (){
    return authInstance.get(`/api/report-reasons`)
}

const postReportReasonAPI = function (request){
    return authInstance.post(`/api/report-reasons`, request)
}

const deleteReportReasonAPI = function (id){
    return authInstance.delete(`/api/report-reasons/${id}`)
}



/**
 * ReportAPI
 */
const getReportedQuestionAPI = function () {
    return authInstance.get(`/api/questions/reports`)
}

const handleReportedQuestionAPI = function (id) {
    return authInstance.post(`/api/questions/reports/handle/${id}`)
}

const deleteReportedQuestionAPI = function (id) {
    return authInstance.delete(`/api/questions/reports/${id}`)
}

const getReportedAnswerAPI = function () {
    return authInstance.get(`/api/reports/answers`)
}

const handleReportedAnswerAPI = function (id) {
    return authInstance.post(`/api/reports/answers/${id}`)
}

const deleteReportedAnswerAPI = function (id) {
    return authInstance.delete(`/api/reports/answers/${id}`)
}

const postQuestionReportAPI = function (request){
    return authInstance.post(`/api/questions/reports`,request)
}

const postAnswerReportAPI = function (request){
    return authInstance.post(`/api/reports/answers`,request)
}






export {
    createUserAPI,
    getQuestionsAPI,
    getQuestionAPI,
    createQuestionAPI,
    getNoticesAPI,
    getNoticeAPI,
    editNoticeAPI,
    deleteNoticeAPI,
    createNoticeAPI,
    deleteQuestionAPI,
    editQuestionAPI,
    createAnswerAPI,
    editAnswerAPI,
    deleteAnswerAPI,
    selectAnswerAPI,
    getReportedQuestionAPI,
    getReportedAnswerAPI,
    handleReportedQuestionAPI,
    deleteReportedQuestionAPI,
    handleReportedAnswerAPI,
    deleteReportedAnswerAPI,
    getReportReasonAPI,
    getNoticesTop5API,
    getMeAPI,
    getMyQuestionsAPI,
    getMyAnswersAPI,
    postQuestionReportAPI,
    postAnswerReportAPI,
    postReportReasonAPI,
    deleteReportReasonAPI
}
