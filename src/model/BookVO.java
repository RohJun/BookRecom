package model;

public class BookVO {
   private String book;
   private int bookPK;
   private String bookAuthor;
   private String bookName;
   private String bookPrice;
   private String bookRate;
   private int bookSales;
   private int bookCnt;
   
   
   @Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	return super.equals(obj);
}
public String getBook() {
      return book;
   }
   public void setBook(String book) {
      this.book = book;
   }
   public int getBookPK() {
      return bookPK;
   }
   public void setBookPK(int bookPK) {
      this.bookPK = bookPK;
   }
   public String getBookAuthor() {
      return bookAuthor;
   }
   public void setBookAuthor(String bookAuthor) {
      this.bookAuthor = bookAuthor;
   }
   public String getBookName() {
      return bookName;
   }
   public void setBookName(String bookName) {
      this.bookName = bookName;
   }
   public String getBookPrice() {
      return bookPrice;
   }
   public void setBookPrice(String bookPrice) {
      this.bookPrice = bookPrice;
   }
   public String getBookRate() {
      return bookRate;
   }
   public void setBookRate(String bookRate) {
      this.bookRate = bookRate;
   }
   public int getBookSales() {
      return bookSales;
   }
   public void setBookSales(int bookSales) {
      this.bookSales = bookSales;
   }
   public int getBookCnt() {
		return bookCnt;
   }
	public void setBookCnt(int bookCnt) {
		this.bookCnt = bookCnt;
	}
	@Override
	public String toString() {
		return ""+bookRate+ "위||	" + bookName + "  작가: " + bookAuthor+ "    가격: "+ bookPrice + "   할인률: " + bookSales +"%"+" 책 재고 :"+bookCnt+"]";
	}
  
   
   
}