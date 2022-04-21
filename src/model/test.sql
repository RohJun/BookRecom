select * from user_tables;

--도서 테이블 생성
create table book(
   bookPK int primary key,				--pk
   bookAuthor varchar(150) not null,	--도서 저자
   bookName varchar(100) not null,		--도서 제목
   bookPrice varchar(100) not null,		--도서 가격
   bookRate varchar(100),						--도서 평점
   bookSales int default 10,			--도서 할인율
   bookCnt int default 0				--도서 재고
);
--확인
select * from book;
--초기화
drop table book;
--도서재고 랜덤값으로 입력
insert into book (bookCnt) values (select trunc(dbms_random.value(1,10) from dual) );


--회원 테이블 생성
create table member(
   memberPK varchar(100) primary key,	--회원pk
   memberID varchar(100) not null,		--회원아이디
   memberPW varchar(100) not null,		--회원비밀번호
   memberPoint int,						--회원 포인트
   memberName varchar(50) not null		--회원 이름
);
--확인
select * from member;

insert into member (memberPK,memberID,memberPW,memberPoint,memberName) values((SELECT DBMS_RANDOM.STRING('X', 5) FROM DUAL),'choi','1234',123,'최규정');
--초기화
drop table member;

