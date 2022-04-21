package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawling {
   public static void main(String[] args) {
      final String url = "http://www.kyobobook.co.kr/bestSellerNew/bestseller.laf?range=1&kind=2&orderClick=DAB&mallGb=KOR&linkClass=A";
      Document doc = null;
      try {
         doc = Jsoup.connect(url).get();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      Elements eles1 = doc.select("li > div.detail>div.title");// 이름
      Elements eles2 = doc.select("li > div.detail>div.author");// 작가
      Elements eles3 = doc.select("li>div.detail>div.price>strong.book_price");// 가격
      Elements eles4 = doc.select("li > div.cover>a > strong");// 순위

      Iterator<Element> itr1 = eles1.iterator(); // 제목
      Iterator<Element> itr2 = eles2.iterator();// 저자
      Iterator<Element> itr3 = eles3.iterator();// 가격
      Iterator<Element> itr4 = eles4.iterator(); // 순위
      ArrayList<BookVO> datas = new ArrayList<BookVO>();

      while (itr1.hasNext()) {
         BookVO vo = new BookVO();
         String title = itr1.next().text();
         String author = itr2.next().text();
         String[] str = author.split(" | ");
         author = str[0];
         String price = itr3.next().text();
         String rate = itr4.next().text();
         System.out.println(title + author + price + rate);// 크롤링 확인용
         vo.setBookName(title);
         vo.setBookAuthor(author);
         vo.setBookPrice(price.replace(",", "").replace("원", ""));
         vo.setBookRate(rate);
         datas.add(vo);
      }

      final String driverName = "oracle.jdbc.driver.OracleDriver";
      final String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
      final String user = "roh";
      final String passwd = "1234";
      final String sql = "insert into book (bookPK,bookCnt,bookAuthor,bookName,bookPrice,bookRate) values((SELECT FLOOR(DBMS_RANDOM.VALUE(10000,99999)) RANDOM FROM DUAL),(SELECT FLOOR(DBMS_RANDOM.VALUE(1,10)) RANDOM FROM DUAL),?,?,?,?)";
      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
         Class.forName(driverName);//
         conn = DriverManager.getConnection(dburl, user, passwd);//
         pstmt = conn.prepareStatement(sql);
         for (BookVO v : datas) {
            pstmt.setString(1, v.getBookAuthor());
            pstmt.setString(2, v.getBookName());
            pstmt.setString(3, v.getBookPrice());
            pstmt.setString(4, v.getBookRate());
            pstmt.executeUpdate();
         }
         System.out.println("hi\n\n");
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            pstmt.close();
            conn.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
   }
}