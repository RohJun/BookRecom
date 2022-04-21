package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemDAO {
   Connection conn;
   PreparedStatement pstmt;

   final String memberJoin="insert into member (memberPK,memberID,memberPw,memberName) values((SELECT DBMS_RANDOM.STRING('X', 5) FROM DUAL),?,?,?)";
   final String logIn="select * from member where memberID like '%'||?||'%' and memberPw like '%'||?||'%'";
   final String sql_purchase="update member set point=point-? where memberid=?";   
   final String sql_check="select point from member where memberid=?";
   final String sql_selectDiscount="select bookprice,booksales,bookname from book where bookname like '%'||?||'%'";
   final String sql_selectSave="select bookprice,booksales,bookname,bookcnt from book where bookname like '%'||?||'%' ";
   final String sql_selectRank="";
   final String sql_addPoint="update member set memberPoint = memberPoint+?";
   final String sql_changeCnt="update book set bookCnt=bookCnt+? where bookPK=?";
   final String sql_getMemberPoint="select memberpoint from member"; 
   final String sql_UpdatePoint="update member set memberPoint=?";
   final String sql_UpdateCnt="update book set bookcnt=? where bookname like '%'||?||'%'";
   //포인트 충전 함수

   // 회원가입
    public boolean MemberJoin(MemVO vo) {
        conn=JDBCUtil.connect();
        try {
           pstmt=conn.prepareStatement(memberJoin);
           pstmt.setString(1,vo.getMemberID());
           pstmt.setString(2,vo.getMemberPW());
           pstmt.setString(3,vo.getMemberName());
           pstmt.executeUpdate();
           System.out.println(vo);
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
           return false;
        }finally {
           JDBCUtil.disconnect(pstmt, conn);
        }
        System.out.println("회원가입 되었습니다.");
        return true;
     }

   // 로그인 (아이디 / 비밀번호 입력
    public boolean Login(MemVO vo) {
        conn=JDBCUtil.connect();
        try {
           pstmt=conn.prepareStatement(logIn);
           pstmt.setString(1, vo.getMemberID());
           pstmt.setString(2, vo.getMemberPW());
           int res=pstmt.executeUpdate();
           if(res==0) {
              System.out.println("로그인 실패");//로그 확인용
              return false;
           }
        } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
           return false;
        } finally {
           JDBCUtil.disconnect(pstmt, conn);
        }
        return true;
     }

   // 결제
   public boolean purchase(MemVO vo) {
      conn=JDBCUtil.connect();
      
      // 트랜잭션
      try {
         conn.setAutoCommit(false);

         pstmt=conn.prepareStatement(sql_purchase);
         pstmt.setInt(1, vo.getMemberPoint());
         pstmt.setString(2, vo.getMemberID());
         int res=pstmt.executeUpdate();
         if(res==0) {
            System.out.println("로그: DAO: 포인트사용대상없음!");
            return false;
         }

         pstmt=conn.prepareStatement(sql_check);
         pstmt.setInt(1, vo.getMemberPoint());
         ResultSet rs=pstmt.executeQuery();
         rs.next();
         if(rs.getInt(vo.getMemberPoint()) < 0) {
            conn.rollback(); // 수행을 취소해라!
            System.out.println("로그: update2(): 결제취소");
            return false;
         }
         else {
            conn.commit(); // 허가한다!
            System.out.println("로그: update2(): 결제승인완료");

         }
         rs.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return false;
      } finally {
         try {
            conn.setAutoCommit(true);
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }

   public MemVO getLoginMember(String memberId) {
     conn=JDBCUtil.connect();
      MemVO data = null;
      try {
         pstmt=conn.prepareStatement("select * from member where memberID=?");
         pstmt.setString(1,memberId);
         ResultSet rs=pstmt.executeQuery();
         if(rs.next()) {
            data=new MemVO();
            data.setMemberPK(rs.getString("memberPK"));
            data.setMemberID(rs.getString("memberID"));
            data.setMemberPW(rs.getString("memberPW"));
         }else if(!rs.next()) {
            return data;
         }
         rs.close();
         pstmt.executeUpdate();
     } catch (SQLException e) {
         e.printStackTrace();
     } finally {
         JDBCUtil.disconnect(pstmt, conn);
     }
      return data;
   }
   //포인트 충전
    public int addPoint(MemVO vo) {
         conn=JDBCUtil.connect();
         MemVO data=null;
         try {
            
           pstmt=conn.prepareStatement(sql_addPoint);
           pstmt.setInt(1,vo.getMemberPoint());
//           pstmt.setString(2,vo.getMemberID());
           pstmt.executeUpdate();

           System.out.println(vo.getMemberID()+"님이 "+vo.getMemberPoint()+"포인트 충전했습니다");
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
       
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }
      return 0;
         

      }
    
   // 할인 전 후 금액 보여주는 기능
   public boolean selectDiscount(BookVO vo) {
      conn=JDBCUtil.connect();
         BookVO data=null;
         try {
            pstmt=conn.prepareStatement(sql_selectDiscount);
            pstmt.setString(1,vo.getBookName());
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()) {
               data=new BookVO();
               data.setBookPrice(rs.getString("bookprice"));
               data.setBookSales(rs.getInt("booksales"));
               data.setBookName(rs.getString("bookname"));
            }else if(!rs.next()) {
               System.out.println("존재하지 않는 책입니다");
               return false;
            }
            System.out.println("검색하신 책의 할인 전 가격은");
            System.out.print(data.getBookName().replace("null","")+" : "+data.getBookPrice().replace("원", "포인트")+"입니다"); // 할인 되기 전 포인트 출력
            rs.close();
            pstmt.executeUpdate();
            int price=Integer.parseInt(data.getBookPrice().replace(",","").replace("원", "")); //,랑 원 제거
            int discountPoint=price-(price/data.getBookSales());// 할인 계산 후 가격
            System.out.println();
            System.out.println("할인 후 금액은 "+discountPoint+"포인트 입니다.");// 출력
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }
         return true;


   }

   // 적립 전 후 금액 보여주는 기능
   public boolean selectSave(BookVO vo) {
      conn=JDBCUtil.connect();
         MemVO mvo=new MemVO();
         BookVO data=new BookVO();
         try {
            pstmt=conn.prepareStatement(sql_selectSave);
            pstmt.setString(1,vo.getBookName());
            ResultSet rs=pstmt.executeQuery();
            if(rs.next()) {
               data.setBookCnt(rs.getInt("bookCnt"));
               data.setBookPrice(rs.getString("bookprice"));
               data.setBookName(rs.getString("bookname"));
            }else  {
               System.out.println("존재하지 않는 책입니다");
               return false;
            }
            pstmt.executeUpdate();
            pstmt=conn.prepareStatement(sql_getMemberPoint);
            ResultSet rs2=pstmt.executeQuery();
            if(rs2.next()) {
               mvo.setMemberPoint(rs2.getInt("memberPoint"));
               
            }
            pstmt.executeUpdate();
            rs.close();
            int price=Integer.parseInt(data.getBookPrice().replace(",","").replace("원", ""));  //,랑 원 제거
            int mPoint=mvo.getMemberPoint();//member포인트
            int cnt=data.getBookCnt();
            String name=data.getBookName();
            if(mPoint<price) {
               System.out.println("잔여 포인트가 부족합니다");
               return false;
            }
            if(cnt==0) {
               System.out.println("재고가 없습니다.");
               return false;
            }
            mPoint=(mPoint-price)+(price/10);//결제금액의 10프로 남은 금액에서 더하기..
            System.out.println(name+"을 결제하고 남은 포인트는"+mPoint+"입니다.");
            System.out.println(name+"의 재고 :"+cnt+"권 입니다.");
            if(cnt<0) {
               cnt=0;
            }else {
               cnt=cnt-1;               
            }
            pstmt=conn.prepareStatement(sql_UpdatePoint);
            pstmt.setInt(1,mPoint);
            pstmt.executeUpdate();
            pstmt=conn.prepareStatement(sql_UpdateCnt);
            pstmt.setInt(1,cnt);
            pstmt.setString(2,name);
            pstmt.executeUpdate();
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
         }finally {
            JDBCUtil.disconnect(pstmt, conn);
         }

         return true;


   }

   // 순위 선택하기
   //searchbyRate
   
   // 관리자모드 재고 증가
    public boolean changeCnt(BookVO vo) {
         conn=JDBCUtil.connect();
         
         try {
            pstmt=conn.prepareStatement(sql_changeCnt);
            pstmt.setInt(1, vo.getBookCnt());
            pstmt.setInt(2, vo.getBookPK());
            // bookPK sql문 추가해야함
            int res=pstmt.executeUpdate();
            if(res==0) {
               System.out.println("해당 상품 재고 없음!");
               return false;
            }
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }
         return true;
      }

}