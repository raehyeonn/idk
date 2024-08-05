import {createRouter, createWebHistory} from "vue-router";
import LoginView from "@/views/LoginView.vue";
import SignupView from "@/views/SignupView.vue";
import HomeView from "@/views/HomeView.vue";
import QuestionView from "@/views/QuestionView.vue";
import NoticeView from "@/views/NoticeView.vue";
import MyView from "@/views/MyView.vue";
import QuestionWriteView from "@/views/QuestionWriteView.vue";
import AdminView from "@/views/AdminView.vue";
import NoticeListView from "@/views/NoticeListView.vue";
import NoticeWriteView from "@/views/NoticeWriteView.vue";
import NoticeEditView from "@/views/NoticeEditView.vue";
import QuestionEditView from "@/views/QuestionEditView.vue";
import ReportedQuestionsView from "@/views/ReportedQuestionsView.vue";
import ReportedAnswersView from "@/views/ReportedAnswersView.vue";
import ReportQuestionModal from "@/views/ReportQuestionModal.vue";
import ReportAnswerModal from "@/views/ReportAnswerModal.vue";
import ReportReasonSettingView from "@/views/ReportReasonSettingView.vue";

const routes = [
    {
        path: "/login",
        name: "Login",
        component: LoginView,
    },
    {
        path: "/signup",
        name: "Signup",
        component: SignupView
    },
    {
        path:  "/",
        name: "Home",
        component: HomeView
    },
    {
        path: "/question/:questionId",
        name: "Question",
        component: QuestionView
    },
    {
        path: "/question/write",
        name: "QuestionWrite",
        component: QuestionWriteView
    },
    {
        path: "/question/:questionId/edit",
        name: "QuestionEdit",
        component: QuestionEditView
    },
    {
        path: "/notice/:noticeId",
        name: "Notice",
        component: NoticeView
    },
    {
        path: "/notice",
        name: "NoticeList",
        component: NoticeListView
    },
    {
        path: "/notice/write",
        name: "NoticeWrite",
        component: NoticeWriteView
    },
    {
        path: "/notice/:noticeId/edit",
        name: "NoticeEdit",
        component: NoticeEditView
    },
    {
        path: "/my",
        name: "My",
        component: MyView
    },
    {
        path: "/admin",
        name: "Admin",
        component: AdminView
    },
    {
        path: "/report/question",
        name: "ReportedQuestion",
        component: ReportedQuestionsView
    },
    {
        path: "/report/answer",
        name: "ReportedAnswer",
        component: ReportedAnswersView
    },
    {
        path: "/report/question-modal",
        name: "ReportQuestionModal",
        component: ReportQuestionModal
    },
    {
        path: "/report/answer-modal",
        name: "ReportAnswerModal",
        component: ReportAnswerModal
    },
    {
        path: "/report/reasons",
        name: "ReportReasonSetting",
        component: ReportReasonSettingView
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})



export default router;
