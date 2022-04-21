package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.BookVO;
import model.MemVO;

public class CView {
	Scanner sc = new Scanner(System.in);
	public int act;   
	public int adminact;
	public int purchaseact;
	public int memact;	

	public void showList(ArrayList<BookVO> datas) {   //월간베스트 도서목록
		System.out.println("교보문고 월간 베스트");
		for(BookVO vo:datas) {
			System.out.println(vo);
		}
	}
	public int choiceMenu() {   // 초기 화면 선택
		System.out.println("1. 회원가입 2. 로그인 3. 종료하기");
		System.out.print("번호 입력 : ");
		act = sc.nextInt();
		return act;
	}
	public String selectMember() {
		System.out.print("아이디 입력 : ");
		String id=sc.next();
		return id;
	}

	public MemVO memJoin() {      //회원가입
		MemVO mvo=new MemVO();
		System.out.println("회원아이디 입력 : ");
		String newmemberID =  sc.next();
		mvo.setMemberID(newmemberID);

		System.out.println("비밀번호 입력 : ");
		String newmemberPW = sc.next();
		mvo.setMemberPW(newmemberPW);

		System.out.println("이름 입력 : ");
		String newmemberName = sc.next();
		mvo.setMemberName(newmemberName);

		return mvo;
	}

	public MemVO login() {   //회원 로그인
		MemVO mvo=new MemVO();

		System.out.print("아이디 입력 : ");
		String memberID =  sc.next();
		mvo.setMemberID(memberID);

		System.out.print("비밀번호 입력 : ");
		String memberPW = sc.next();
		mvo.setMemberPW(memberPW);
		System.out.println("로그인 성공!");
		return mvo;
	}

	public void failLogin() {
		System.out.println("해당 아이디는 존재하지않습니다 다시 시도하세요:(");
	}
	
	public void exit() {
		System.out.println("프로그램종료..");
	}

	public int memView() {   //회원 창
		System.out.println("1. 구매");
		System.out.println("2. 검색모드");
		System.out.println("3. 오늘의 추천도서");
		System.out.println("4. 장바구니");
		System.out.println("5. 포인트충전");
		System.out.println("6. 로그아웃!");
		System.out.print("번호 입력 : ");
		int act = sc.nextInt();
	    
		return act;
	}

	
	//회원 구매 창
	public int purchaseView() {   
		System.out.println("1. 선택 구매");
		System.out.println("2. 장바구니 구매");
		System.out.print("입력 :");
		purchaseact=sc.nextInt();
		return purchaseact;
		
	}
	//선택 구매
	public String purchaseSelect() {
		System.out.println("구매할 도서의 제목 입력 : ");
		String title= sc.next();
		  sc.nextLine();
		  return title ;
	}
	//장바구니 구매
	public void purchaseBag() {
		
	}
	//선택 구매 성공
	public void purchaseSelect_Y() {
		System.out.println("구매 성공 페이지");
	}
	//선택 구매 실패
	public void purchaseSelect_N() {
		System.out.println("구매 실패 페이지");
	}
	//장바구니 구매 성공
	public void purchaseBag_Y() {
		System.out.println("구매 성공 페이지");
	}
	//장바구니 구매 실패
	public void purchaseBag_N() {
		System.out.println("구매 실패 페이지");
	}
	//구매 유효성검사
	public void purchaseCheck() {
		System.out.println("없는 번호입니다. 1 또는 2 입력해주세요.");
	}

	public int search() {   //회원 2. 검색
		System.out.println("1. 작가 검색");      
		System.out.println("2. 제목 검색");   
		System.out.println("3. 가격별 검색");   
		System.out.println("4. 순위 검색");   
		System.out.println("번호 입력 : ");   
		return act=sc.nextInt();
	}
	public String searchAuthor() {   //회원 검색(저자)
		String author;
		System.out.println("검색할 저자 입력 : ");
		return author = sc.next();
	}
	public String searchTitle() {      //회원 검색(제목)
		
		System.out.println("검색할 도서의 제목 입력 : ");
		String title= sc.next();
		  sc.nextLine();
		  return title ;
	  
	}
	public String searchPrice() {      //회원 검색(가격)
		String price;
		System.out.println("검색할 도서의 가격 입력 : ");
		return price = sc.next();
	}
	public String searchByRank() {   //회원 검색(순위)
		String rank;
		System.out.println("검색할 도서의 순위 입력(숫자) :");
		return rank = sc.next();
	}
	public void searchCheck() {
		System.out.println("없는 번호입니다 1~4까지의 번호 입력해주세요.");
	}
	//   public void searchbyRate() {   //회원 검색(평점)
	//      
	//   }

	public void printBookList(ArrayList<BookVO> datas) {
		for(BookVO vo:datas) {
			System.out.println(vo);
		}
	}
	public void todayBook() {   //회원 3. 오늘의 도서
		System.out.println("오늘의 추천 도서는 ");   
	}

	public void bag() {      //회원 4. 장바구니
		System.out.println("");   
	}
	public String addbagTitle() {      //장바구니 도서제목으로 추가하기
		
		System.out.println("장바구니에 추가할 도서의 제목 입력 : ");
		String title= sc.next();
		  sc.nextLine();
		  return title ;
	  
	}
	public void addBag() {   //회원 장바구니(추가)
		System.out.println("장바구니에 추가하시겠습니까?(y/n)");
		String addbag = sc.next();
	}
	public void addBag_Y() {
		System.out.println("장바구니 추가 성공 페이지");
	}
	public void addBag_N() {
		System.out.println("장바구니 추가 실패 페이지");
	}
	public void removeBag() {   //회원 장바구니(제거)
		System.out.println("장바구니에서 제거하시겠습니까?(y/n) :");
		String removebag = sc.next();
	}
	public void removeBag_Y() {
		System.out.println("장바구니 제거 성공 페이지");
	}
	public void removeBag_N() {
		System.out.println("장바구니 제거 실패 페이지");
	}

	public int addPoint() {   //회원 5. 포인트
		System.out.println("충전할 금액 입력 : ");   
		int point;
		return point=sc.nextInt();
	}

	public void memExit() {   //회원 모드 종료
		System.out.println("회원모드 종료됩니다");   
	}
	public void memCheck() {	//요효성 검사(회원모드)
		System.out.println("없는 번호입니다. 1~6까지의 번호를 입력해주세요");
	}
	
	//관리자모드
	
	public int adminView() {      //관리자 창
		System.out.println("1. 재고 관리");
		System.out.println("2. 관리자 모드 종료");
		adminact = sc.nextInt();
		return adminact;
	}
	
	public String changeCnt() {   //관리자 재고관리
		System.out.println("재고 수정할 도서 제목 입력 : ");
		String str = sc.next();
		//vo.setCnt();
		//vo.setBid();
		//return vo;
		return str ;
	}
	public int changeCnt2() {
		System.out.println("얼마나 추가할까요?");
		int cnt = sc.nextInt();
		return cnt;
		
	}

	public void changeCnt_Y(){
		System.out.println("재고추가 완료 페이지");
	}
	public void changeCnt_N(){
		System.out.println("재고추가 실패 페이지");
	}

	public void adExit() {
		System.out.println("관리자 모드 종료");
	}
	public void adCheck() {
		System.out.println("없는 번호입니다. 1 또는 2 입력해주세요.");
	}
}