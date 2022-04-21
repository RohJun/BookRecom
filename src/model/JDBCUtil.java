package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// 공통되는 로직을 따로 관리하려고 생성한 Util클래스
public class JDBCUtil {
   static final String driverName="oracle.jdbc.driver.OracleDriver";
   static final String url="jdbc:oracle:thin:@localhost:1521:xe";
   static final String user="roh";
   static final String passwd="1234";
   public static Connection connect(){ // DB에 연결을 한다. == Connection을 확보한다.
      Connection conn=null;
      try {
         Class.forName(driverName);
         conn=DriverManager.getConnection(url, user, passwd);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return conn;
   }
   public static void disconnect(Statement stmt,Connection conn){
      try {
         stmt.close();
         conn.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}