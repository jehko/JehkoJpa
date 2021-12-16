

INSERT INTO USER(EMAIL, USER_NAME, PASSWORD, PHONE, DELETED, STATUS, REG_DATE, UPDATE_DATE) VALUES('user1@naver.com', '사용자1', '1111', '010-1234-1234', 0, 1, '2021-10-21 03:00:00.000000', null);
INSERT INTO USER(EMAIL, USER_NAME, PASSWORD, PHONE, DELETED, STATUS, REG_DATE, UPDATE_DATE) VALUES('user2@naver.com', '사용자2', '1111', '010-1212-1212', 0, 1, '2021-10-21 03:00:00.000000', null);
INSERT INTO USER(EMAIL, USER_NAME, PASSWORD, PHONE, DELETED, STATUS, REG_DATE, UPDATE_DATE) VALUES('user3@naver.com', '사용자3', '1111', '010-2345-1234', 0, 1, '2021-10-21 03:00:00.000000', null);
INSERT INTO USER(EMAIL, USER_NAME, PASSWORD, PHONE, DELETED, STATUS, REG_DATE, UPDATE_DATE) VALUES('user4@naver.com', '사용자4', '1111', '010-4567-1234', 0, 2, '2021-10-21 03:00:00.000000', null);

INSERT INTO NOTICE(TITLE, CONTENTS, HITS, LIKES, REG_DATE, DELETED, USER_ID) VALUES('제목1', '내용1', 0, 0, '2021-10-21 01:00:00.000000', 0, 1);
INSERT INTO NOTICE(TITLE, CONTENTS, HITS, LIKES, REG_DATE, DELETED, USER_ID) VALUES('제목2', '내용2', 0, 0, '2021-10-21 02:00:00.000000', 0, 2);
INSERT INTO NOTICE(TITLE, CONTENTS, HITS, LIKES, REG_DATE, DELETED, USER_ID) VALUES('제목3', '내용3', 0, 0, '2021-10-21 03:00:00.000000', 0, 3);


INSERT INTO NOTICE_LIKE(NOTICE_ID, USER_ID) VALUES(1, 1);


INSERT INTO USER_LOGIN_HISTORY(USER_ID, EMAIL, USER_NAME, IP_ADDR, LOGIN_DATE) VALUES(1, 'user1@naver.com', '사용자1', '111.111.111.111', '2021-10-21 01:00:00.000000');