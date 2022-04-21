package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import model.*;

public class Userbag { // 책의 정보를 상속
	public int bagviewact;
	Scanner sc = new Scanner(System.in);

	ArrayList name = new ArrayList();// 제목
//	ArrayList code = new ArrayList(); // PK
	ArrayList price = new ArrayList();// 가격
	ArrayList number = new ArrayList(); // 수량
	ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
	int temp = 0;
	public String str;

	public Userbag() {

	}

	// 장바구니 화면
	public int bagview() {
		System.out.println("1. 장바구니에 도서 추가");
		System.out.println("2. 장바구니에 도서 제거");
		System.out.println("3. 현재 장바구니 목록");
		System.out.print("입력 : ");
		sc.nextLine();
		bagviewact = sc.nextInt();
		return bagviewact;

	}

	// 장바구니 화면 유효성체크
	public void bagviewcheck() {
		System.out.println("없는 번호입니다. 1~3까지의 번호를 입력해주세요.");
	}

	// 장바구니 추가
	public void addBag(BookVO vo) {

		name.add(vo.getBookName());

		System.out.println(name);
		System.out.print("몇개 추가하시겠습니까?");
		int num = sc.nextInt();

		number.add(num);

		String p = vo.getBookPrice().replace("원", "");
		String finalprice = p.replace(",", "");
		price.add((Integer.parseInt(finalprice)) * num);
		datas.add(name);
		datas.add(price);
		datas.add(number);

		System.out.println(datas);
//		System.out.println(name);
//		System.out.println(price);
//		System.out.println(number); 
		
	}

	// 장바구니 제거화면
	public int removeView() {
		System.out.println("1. 선택 제거");
		System.out.println("2. 전체 제거");
		System.out.println("입력 : ");
		int act = sc.nextInt();
		return act;
	}

	// 장바구니 제거화면 유효성체크
	public void removeCheck() {
		System.out.println("없는 번호입니다. 1 또는 2 입력해주세요.");
	}

	// 장바구니 제거 (전체 제거)
	public void removeAll() {
		System.out.print("장바구니를 전체 제거하시겠습니까?(y,n) : ");
		String str2 = sc.next();
		char ch = str2.charAt(0);
		if (ch == 'Y' || ch == 'y') {
			datas.clear();
			System.out.println("장바구니 초기화 완료!");
		} else if (ch == 'N' || ch == 'n') {
			System.out.println("목록으로 돌아갑니다.");
		}

	}

	// 장바구니 제거 (선택제거)
	public void removeOne() {
		System.out.println(name);	//현재 장바구니 도석제목들
		System.out.print("장바구니 제거할 도서 제목:");
		String inTitle = sc.next();	//제목 입력

		for (int i = 0; i < name.size(); i++) {
			if (name.get(i).equals(inTitle)) { 
				datas.remove(i);
				System.out.println(name);
				break;
			}
			
		}

	}

	// 장바구니 목록 출력
	public void showList() {
		System.out.println(datas);
	}
}
