package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO {
   Connection conn;
   PreparedStatement pstmt;
   
   final String sql_showList="select * from book";
   final String sql_selectAuthor="select * from book where bookAuthor like '%'||?||'%'";
   final String sql_selectTitle="select * from book where bookName like '%'||?||'%'";
   final String sql_selectPrice="select * from book where bookPrice like '%'||?||'%'";
   final String sql_selectRank="select * from book where bookRate like ?";
   final String random="select * from ( select * from book order by dbms_random.value ) where rownum <= 1"; // 오늘의 책
//   final String sql_updateCnt="update book set bookCnt=bookCnt-? where bookPK=?";
   final String sql_changeCnt="update book set bookcnt=bookcnt+? where bookname like '%'||?||'%'";
   // 전체목록 보여주기
   public ArrayList<BookVO> showList(){ // 전체 목록 확인해주는것
         ArrayList<BookVO> datas=new ArrayList<BookVO>();
         conn=JDBCUtil.connect();
         try {
            pstmt=conn.prepareStatement(sql_showList);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()) {
              BookVO data=new BookVO();
               data.setBookAuthor(rs.getString("bookauthor"));
               data.setBookName(rs.getString("bookname"));
               data.setBookPrice(rs.getString("bookprice"));
               data.setBookRate(rs.getString("bookrate"));
               data.setBookSales(rs.getInt("booksales"));  
               data.setBookCnt(rs.getInt("bookcnt"));
               datas.add(data);
            }
            rs.close();
         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }
         return datas;
      }
      
   
   // executeQuery : select
   // 작가 선택
   public ArrayList<BookVO> selectAuthor(BookVO vo) { 
      ArrayList<BookVO> datas=new ArrayList<BookVO>();
      BookVO data=null;
      conn=JDBCUtil.connect(); 
      try { 
         pstmt=conn.prepareStatement(sql_selectAuthor);
         pstmt.setString(1, vo.getBookAuthor()); 
         ResultSet rs=pstmt.executeQuery();
         while(rs.next()) { 
            data=new BookVO(); 
            data.setBookRate(rs.getString("bookRate"));
            data.setBookAuthor(rs.getString("bookAuthor"));
            data.setBookPrice(rs.getString("bookPrice"));
            data.setBookName(rs.getString("bookName"));
            data.setBookSales(rs.getInt("bookSales"));
            data.setBookCnt(rs.getInt("bookCnt"));
            datas.add(data);
         } 
         rs.close(); 
      } 
      catch (SQLException e) {
         e.printStackTrace(); 
      } finally {
         JDBCUtil.disconnect(pstmt, conn); 
      } 
      return datas; 
   }
   
   // 제목 선택
   public ArrayList<BookVO> selectTitle(BookVO vo){
      ArrayList<BookVO> datas=new ArrayList<BookVO>();
      BookVO data=null;
      conn=JDBCUtil.connect(); 
      try { 
         pstmt=conn.prepareStatement(sql_selectTitle);
         pstmt.setString(1, vo.getBookName()); 
         ResultSet rs=pstmt.executeQuery();
         while(rs.next()) { 
             data=new BookVO(); 
             data.setBookRate(rs.getString("bookRate"));
             data.setBookAuthor(rs.getString("bookAuthor"));
             data.setBookPrice(rs.getString("bookPrice"));
             data.setBookName(rs.getString("bookName"));
             data.setBookSales(rs.getInt("bookSales"));
             data.setBookCnt(rs.getInt("bookCnt"));
             datas.add(data);
          } 
         rs.close(); 
      } 
      catch (SQLException e) {
         // TODO Auto-generated catch block e.printStackTrace(); 
    	  } finally {
         JDBCUtil.disconnect(pstmt, conn); } 
      return datas; 
   }
   
   // 가격 선택
   public ArrayList<BookVO> selectPrice(BookVO vo){
      
         ArrayList<BookVO> datas=new ArrayList<BookVO>();
         BookVO data=null;
         conn=JDBCUtil.connect(); 
         try { 
            pstmt=conn.prepareStatement(sql_selectPrice);
            pstmt.setString(1, vo.getBookPrice()); 
            //view에서 "16200"라고 입력받는데, DB에 가격데이터는 "16,200원"으로 되어있음
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()) { 
               data=new BookVO(); 
                data.setBookRate(rs.getString("bookRate"));
                data.setBookAuthor(rs.getString("bookAuthor"));
                data.setBookPrice(rs.getString("bookPrice"));
                data.setBookName(rs.getString("bookName"));
                data.setBookSales(rs.getInt("bookSales"));
                data.setBookCnt(rs.getInt("bookCnt"));
                datas.add(data);
            } 
            rs.close(); 
         } 
         catch (SQLException e) {
            // TODO Auto-generated catch block e.printStackTrace(); 
        	 } finally {
            JDBCUtil.disconnect(pstmt, conn); } 
         return datas; 
      }
      
   // 등수 선택
   public ArrayList<BookVO> selectRank(String searchByRank){
      ArrayList<BookVO> datas=new ArrayList<BookVO>();
      BookVO data=null;
      
      conn=JDBCUtil.connect(); 
      try { 
         pstmt=conn.prepareStatement(sql_selectRank);
         pstmt.setString(1, searchByRank); 
         ResultSet rs=pstmt.executeQuery();
         while(rs.next()) { 
            data=new BookVO(); 
             data.setBookRate(rs.getString("bookRate"));
             data.setBookAuthor(rs.getString("bookAuthor"));
             data.setBookPrice(rs.getString("bookPrice"));
             data.setBookName(rs.getString("bookName"));
             data.setBookSales(rs.getInt("bookSales"));
             data.setBookCnt(rs.getInt("bookCnt"));
             datas.add(data);
         } 
         rs.close(); 
      } 
      catch (SQLException e) {
         // TODO Auto-generated catch block e.printStackTrace(); 
    	  } finally {
         JDBCUtil.disconnect(pstmt, conn); } 
      return datas; 
   }
   

   //랜덤책 추천
   public void todayBook() { 
      conn=JDBCUtil.connect();
         BookVO data=null;
         try {

            pstmt=conn.prepareStatement(random);         
            ResultSet rs=pstmt.executeQuery();
            
            while(rs.next()) {
                data=new BookVO();
               data.setBookAuthor(rs.getString("bookauthor"));
               data.setBookName(rs.getString("bookname"));
               data.setBookPrice(rs.getString("bookprice"));
            }
            rs.close();
            System.out.println("책 제목 : "+data.getBookName());
            System.out.println("작가 : "+data.getBookAuthor());
            System.out.println("가격 : "+data.getBookPrice());

         } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }

   }


public BookVO update(BookVO vo) {
	 conn=JDBCUtil.connect(); 
	 BookVO data=null;
     try { 
    	
        pstmt=conn.prepareStatement(sql_selectTitle);
        pstmt.setString(1, vo.getBookName()); 
        ResultSet rs=pstmt.executeQuery();
        while(rs.next()) { 
           data=new BookVO(); 
           data.setBookRate(rs.getString("bookRate"));
           data.setBookAuthor(rs.getString("bookAuthor"));
           data.setBookPrice(rs.getString("bookPrice"));
           data.setBookName(rs.getString("bookName"));
           data.setBookSales(rs.getInt("bookSales"));
           data.setBookCnt(rs.getInt("bookCnt"));
        } 
        rs.close(); 
     } 
     
     catch (SQLException e) {
        e.printStackTrace(); 
     } finally {
        JDBCUtil.disconnect(pstmt, conn); 
     } 
     return data; 
  }

public boolean updateCnt(BookVO vo) {	//결재후 수량 업데이트
    conn=JDBCUtil.connect();
    
    try {
       pstmt=conn.prepareStatement(sql_changeCnt);
       pstmt.setInt(1, vo.getBookCnt());
       pstmt.setInt(2, vo.getBookPK());
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
public void purchasePoint(int memberPoint) {
 // TODO Auto-generated method stub
 
}
}
   
