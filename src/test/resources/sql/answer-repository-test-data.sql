-- 사용자 데이터
INSERT INTO users (id, email, password, nickname, created_at, updated_at) VALUES
                                                                              (1, 'user1@example.com', 'password1', 'User1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                              (2, 'user2@example.com', 'password2', 'User2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 사용자 역할 데이터
INSERT INTO user_roles (user_id, role, created_at, updated_at) VALUES
                                                                   (1, 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                   (2, 'USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 질문 데이터
INSERT INTO questions (id, writer_id, title, content, is_selected, views, created_at, updated_at) VALUES
                                                                                                      (1, 1, '질문 1', '질문 내용 1', false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                      (2, 2, '질문 2', '질문 내용 2', false, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 답변 데이터
INSERT INTO answers (id, writer_id, question_id, content, is_selected, created_at, updated_at) VALUES
                                                                                                   (1, 1, 1, '답변 1', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                   (2, 1, 2, '답변 2', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                                                                                                   (3, 2, 1, '답변 3', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);