package team.onepoom.idk.question.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import team.onepoom.idk.answer.domain.Answer;
import team.onepoom.idk.answer.domain.QAnswer;
import team.onepoom.idk.member.domain.QMember;
import team.onepoom.idk.question.domain.QQuestion;
import team.onepoom.idk.question.domain.Question;
import team.onepoom.idk.question.dto.GetAllQuestionsResponse;
import team.onepoom.idk.question.dto.GetMyQuestionResponse;
import team.onepoom.idk.question.dto.QGetAllQuestionsResponse;
import team.onepoom.idk.question.dto.QGetMyQuestionResponse;

@Repository
@RequiredArgsConstructor
public class CustomQuestionRepository {

    private final JPAQueryFactory queryFactory;
    QQuestion question = QQuestion.question;
    QAnswer answer = QAnswer.answer;
    QMember member = QMember.member;

    // 질문에 답변이 달려있는지 확인
    // 답변이 달려있다면 수정 불가능하게 하기 위함.
    public boolean existAnswer(long id) {
        Answer answer = queryFactory
        .selectFrom(this.answer)
        .where(this.answer.question.id.eq(id))
        .fetchFirst();

        return answer != null;
    }

    public Question findQuestion(long id) {
        return queryFactory
        .selectFrom(question)
        .leftJoin(question.writer).fetchJoin()
        .leftJoin(question.answers, answer).fetchJoin()
        .leftJoin(answer.writer).fetchJoin()
        .where(question.id.eq(id))
        .fetchOne();
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? question.title.contains(title) : null;
    }

    public Page<GetAllQuestionsResponse> findQuestions(String title, Pageable pageable) {
        List<GetAllQuestionsResponse> questions = queryFactory
        .select(new QGetAllQuestionsResponse(
        question,
        JPAExpressions
        .select(answer.count())
        .from(answer)
        .where(answer.question.id.eq(question.id))))
        .from(question)
        .leftJoin(question.writer).fetchJoin()
        .where(titleContains(title))
        .where(question.blindedAt.isNull())
        .orderBy(question.createdAt.desc())
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .fetch();

        JPAQuery<Long> count = queryFactory
        .select(question.count())
        .from(question)
        .where(titleContains(title))
        .where(question.blindedAt.isNull());

        return PageableExecutionUtils.getPage(questions, pageable, count::fetchOne);
    }

    public Page<GetMyQuestionResponse> findMyQuestions(long writerId, Pageable pageable) {
        List<GetMyQuestionResponse> questions = queryFactory
        .select(new QGetMyQuestionResponse(
        question,
        JPAExpressions
        .select(answer.count())
        .from(answer)
        .where(answer.question.id.eq(question.id))))
        .from(question)
        .where(question.writer.id.eq(writerId))
        .orderBy(question.createdAt.desc())
        .limit(pageable.getPageSize())
        .offset(pageable.getOffset())
        .fetch();

        JPAQuery<Long> count = queryFactory
        .select(question.count())
        .from(question)
        .where(question.writer.id.eq(writerId));

        return PageableExecutionUtils.getPage(questions, pageable, count::fetchOne);
    }

}
