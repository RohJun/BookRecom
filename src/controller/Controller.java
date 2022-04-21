package controller;

import java.util.ArrayList;

import model.BookDAO;
import model.BookVO;
import model.MemDAO;
import model.MemVO;
import model.Userbag;
import view.CView;
import model.*;

public class Controller {

	MemDAO mdao;
	BookDAO bdao;
	CView view;
	Userbag bag;

	public Controller() {
		mdao = new MemDAO();
		bdao = new BookDAO();
		view = new CView();
		bag = new Userbag();
	}

	public void startApp() {
		boolean login = false;
		MemVO loginMember = null; // 로그인한 회원의 정보

		while (true) {
			ArrayList<BookVO> datas = bdao.showList();
			view.showList(datas);

			int menu = view.choiceMenu();

			// 회원가입
			if (menu == 1) {
				MemVO mvo = view.memJoin();
				mdao.MemberJoin(mvo);
			}
			// 로그인
			else if (menu == 2) {
				MemVO mvo = view.login();
				login = mdao.Login(mvo);

				// 로그인 성공한 경우
				if (login) {
					loginMember = mdao.getLoginMember(mvo.getMemberID());
				}
				// 로그인 실패
				else {
					view.failLogin();
					continue;
				}
			}
			// 종료하기
			else if (menu == 3) {
				view.exit();
				break;
			}
			// 관리자모드
			else if(menu==999) {
	            int adMenu=view.adminView();
	            //관리자모드 재고 관리
	            if(adMenu==1) {   
	               String searchTitle = view.changeCnt();   //도서제목으로 검색
	               int cnt = view.changeCnt2();   //추가 수량 
	                  BookVO bvo=new BookVO();
	                    bvo.setBookCnt(cnt);
	                    bvo.setBookName(searchTitle);
	                     mdao.changeCnt(bvo);
	                     
	            }
	            else if(adMenu==2) {   //관리자모드 종료
	               view.adExit();
	               break;
	            }
	            else {
	               view.adCheck();
	               continue;
	            }
	         }

			// 로그인 성공
			if (login) {
				while (true) {
					int memMenu = view.memView();
					// 구매
					if (memMenu == 1) {
						int purchaseMenu = view.purchaseView();
						// 선택 구매
						if (purchaseMenu == 1) {
							view.purchaseSelect();
							String searchTitle = view.searchTitle();
							BookVO bvo = new BookVO();
							bvo.setBookName(searchTitle);
							mdao.selectSave(bvo);
						}
//						//장바구니 구매
//						else if(purchaseMenu==2) {
//							view.purchaseBag();
//							if(포인트부족) {
//								view.purchaseBag_N();
//							}
//							else {
//								view.purchaseBag_Y();
//							}
//						}
//						else {
//							view.purchaseCheck();
					}
//					} 
					// 검색
					else if (memMenu == 2) {
						int searchMenu = view.search();

						// 작가검색
						if (searchMenu == 1) {

							String searchAuthor = view.searchAuthor();
							BookVO bvo = new BookVO();
							bvo.setBookAuthor(searchAuthor);
							view.printBookList(bdao.selectAuthor(bvo));

						}
						// 제목검색
						else if (searchMenu == 2) {

							String searchTitle = view.searchTitle();
							BookVO bvo = new BookVO();
							bvo.setBookName(searchTitle);
							view.printBookList(bdao.selectTitle(bvo));

						}
						// 가격별 검색
						else if (searchMenu == 3) {

							String searchPrice = view.searchPrice();
							BookVO bvo = new BookVO();
							bvo.setBookPrice(searchPrice);
							view.printBookList(bdao.selectPrice(bvo));

						}
						// 순위검색
						else if (searchMenu == 4) {

							String searchByRank = view.searchByRank();
							BookVO bvo = new BookVO();
							bvo.setBookRate(searchByRank);
							view.printBookList(bdao.selectRank(searchByRank));
						}
						// 유효성검사(검색모드)
						else {
							view.searchCheck();
							continue;
						}
					}
					// 오늘의 추천 도서
					else if (memMenu == 3) {
						view.todayBook();
						bdao.todayBook();
					}
					// 장바구니
					else if (memMenu == 4) {
						int bagMenu = bag.bagview();
						// 장바구니 도서 추가
						while (true) {
							if (bagMenu == 1) {
								String searchTitle = view.addbagTitle();
								BookVO bvo = new BookVO();
								bvo.setBookName(searchTitle);
								bvo = bdao.update(bvo);
								bag.addBag(bvo);
								break;
							}
							// 장바구니 도서 제거
							else if (bagMenu == 2) {
								int bagremoveMenu = bag.removeView();
								// 선택제거
								if (bagremoveMenu == 1) {
									bag.removeOne();
									
								}
								// 전체제거
								else if (bagremoveMenu == 2) {
									bag.removeAll();
								} else {
									bag.removeCheck();
								}
							}
							// 장바구니 목록 출력
							else if (bagMenu == 3) {
								bag.showList();
							}
							// 유효성체크(장바구니메뉴)
							else {
								bag.bagviewcheck();
							}
						}

					}
					// 포인트 충전
					else if (memMenu == 5) {
						int addPoint = view.addPoint();
						MemVO mvo = loginMember;
						mvo.setMemberPoint(addPoint);
						System.out.println("로그" + mvo);
						mdao.addPoint(mvo); // 충전할 금액과 현재 로그인된 사람 정보 넘김
					}
					// 로그아웃
					else if (memMenu == 6) {
						view.memExit();
						loginMember = null;
						break;
					}
					// 유효성검사(회원모드)
					else {
						view.memCheck();
						continue;
					}
				}
			}

		}

	}

}
