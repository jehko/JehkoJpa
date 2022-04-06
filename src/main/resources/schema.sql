DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS NOTICE;
DROP TABLE IF EXISTS NOTICELIKE;

create table USER
(
	ID			BIGINT auto_increment primary key,
	EMAIL		VARCHAR(255),
	USER_NAME	VARCHAR(255),
	PASSWORD	VARCHAR(255),
	PHONE		VARCHAR(255),
	DELETED		BOOLEAN,
	STATUS      INTEGER,
	REG_DATE	TIMESTAMP,
	UPDATE_DATE	TIMESTAMP
);

create table NOTICE
(
	ID			BIGINT auto_increment primary key,
	TITLE		VARCHAR(255),
	CONTENTS	VARCHAR(255),
	
	HITS		INTEGER,
	LIKES		INTEGER,
	DELETED		BOOLEAN,
	
	REG_DATE		TIMESTAMP,
	UPDATE_DATE		TIMESTAMP,
	DELETED_DATE	TIMESTAMP,
	
	USER_ID		BIGINT,
	constraint FK_NOTICE_USER_ID foreign key(USER_ID) references USER(ID)
);

create table NOTICE_LIKE
(
	ID			BIGINT auto_increment primary key,
	
	NOTICE_ID	BIGINT,
	USER_ID		BIGINT,
	
	constraint FK_NOTICE_LIKE_NOTICE_ID foreign key(NOTICE_ID) references NOTICE(ID),
	constraint FK_NOTICE_LIKE_USER_ID foreign key(USER_ID) references USER(ID)
);

create table USER_LOGIN_HISTORY
(
    ID      BIGINT  auto_increment primary key,
    USER_ID     BIGINT,
    EMAIL		VARCHAR(255),
    USER_NAME	VARCHAR(255),
    IP_ADDR 	VARCHAR(255),
    LOGIN_DATE	TIMESTAMP
);

create table USER_INTEREST
(
    ID      BIGINT  auto_increment primary key,
    USER_ID     BIGINT,
    INTEREST_USER_ID     BIGINT,
    REG_DATE    TIMESTAMP,

	constraint FK_USER_INTEREST_USER_ID foreign key(USER_ID) references USER(ID),
	constraint FK_USER_INTEREST_INTEREST_USER_ID foreign key(INTEREST_USER_ID) references USER(ID)
);

create table USER_POINT
(
    ID                  BIGINT auto_increment primary key,
    USER_ID             BIGINT,
    USER_POINT_TYPE     VARCHAR(255),
    POINT               BIGINT,

	constraint FK_USER_POINT_USER_ID foreign key(USER_ID) references USER(ID)
);

create table BOARD_TYPE
(
    ID      BIGINT  auto_increment primary key,
    BOARD_NAME	VARCHAR(255),
    USING_YN    BOOLEAN,
    REG_DATE	TIMESTAMP,
    UPDATE_DATE	TIMESTAMP
);

create table BOARD
(
    ID      BIGINT  auto_increment primary key,
    TITLE	VARCHAR(255),
    CONTENTS	VARCHAR(255),
    BOARD_TYPE_ID   BIGINT,
    USER_ID   BIGINT,
    REG_DATE	TIMESTAMP,
    UPDATE_DATE	TIMESTAMP,
    TOP_YN      BOOLEAN,

    PUBLISH_START_DATE  DATE,
    PUBLISH_END_DATE  DATE,

	constraint FK_BOARD_BOARD_TYPE_ID foreign key(BOARD_TYPE_ID) references BOARD_TYPE(ID),
	constraint FK_BOARD_USER_ID foreign key(USER_ID) references USER(ID)
);

create table BOARD_COMMENT
(
    ID      BIGINT  auto_increment primary key,
    COMMENTS	VARCHAR(255),
    BOARD_ID   BIGINT,
    USER_ID   BIGINT,
    REG_DATE	TIMESTAMP,
    UPDATE_DATE	TIMESTAMP,

	constraint FK_BOARD_COMMENT_USER_ID foreign key(USER_ID) references USER(ID),
	constraint FK_BOARD_COMMENT_BOARD_ID foreign key(BOARD_ID) references BOARD(ID)
);

create table BOARD_HITS
(
    ID      BIGINT  auto_increment primary key,
    BOARD_ID   BIGINT,
    USER_ID   BIGINT,
    REG_DATE	TIMESTAMP,

	constraint FK_BOARD_HITS_BOARD_ID foreign key(BOARD_ID) references BOARD(ID),
	constraint FK_BOARD_HITS_USER_ID foreign key(USER_ID) references USER(ID)
);

create table BOARD_LIKE
(
    ID      BIGINT  auto_increment primary key,
    BOARD_ID   BIGINT,
    USER_ID   BIGINT,
    REG_DATE	TIMESTAMP,

	constraint FK_BOARD_LIKE_BOARD_ID foreign key(BOARD_ID) references BOARD(ID),
	constraint FK_BOARD_LIKE_USER_ID foreign key(USER_ID) references USER(ID)
);

create table BOARD_BAD_REPORT
(
    ID      BIGINT  auto_increment primary key,

    USER_ID     BIGINT,
    COMMENTS    VARCHAR(255),

    BOARD_ID    BIGINT,
    BOARD_USER_ID   BIGINT,
    BOARD_TITLE	VARCHAR(255),
    BOARD_CONTENTS	VARCHAR(255),
    BOARD_REG_DATE	TIMESTAMP,
    REG_DATE	TIMESTAMP,

	constraint FK_BOARD_BAD_REPORT_USER_ID foreign key(USER_ID) references USER(ID)
);

create table BOARD_SCRAP
(
    ID      BIGINT  auto_increment primary key,
    BOARD_ID   BIGINT,
    USER_ID   BIGINT,
    REG_DATE	TIMESTAMP,

	constraint FK_BOARD_SCRAP_BOARD_ID foreign key(BOARD_ID) references BOARD(ID),
	constraint FK_BOARD_SCRAP_USER_ID foreign key(USER_ID) references USER(ID)
);

create table BOARD_BOOKMARK
(
    ID      BIGINT  auto_increment primary key,
    BOARD_ID   BIGINT,
    USER_ID   BIGINT,
    REG_DATE	TIMESTAMP,

	constraint FK_BOARD_BOOKMARK_BOARD_ID foreign key(BOARD_ID) references BOARD(ID),
	constraint FK_BOARD_BOOKMARK_USER_ID foreign key(USER_ID) references USER(ID)
);

create table LOGS
(
    ID      BIGINT auto_increment primary key,
    PATH    VARCHAR(255),
    USER_ID BIGINT,
    REG_DATE    TIMESTAMP
);