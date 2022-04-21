package model;

public class MemVO {
	private String memberPK;
	private String memberPW;
	private String memberID;
	private int memberPoint;
	private String memberName;
	public String getMemberPK() {
		return memberPK;
	}
	public void setMemberPK(String memberPK) {
		this.memberPK = memberPK;
	}
	public String getMemberPW() {
		return memberPW;
	}
	public void setMemberPW(String memberPw) {
		this.memberPW = memberPw;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberId) {
		this.memberID = memberId;
	}
	public int getMemberPoint() {
		return memberPoint;
	}
	public void setMemberPoint(int memberPoint) {
		this.memberPoint = memberPoint;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
	public String toString() {
		return "MemVO [memberPK=" + memberPK + ", memberPW=" + memberPW + ", memberID=" + memberID + ", memberPoint="
				+ memberPoint + ", memberName=" + memberName + "]";
	}
	
}
