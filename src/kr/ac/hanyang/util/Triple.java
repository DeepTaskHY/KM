package kr.ac.hanyang.util;

public class Triple {
	private String subject;
	private String predicate;
	private String object;
	
	// 생성자
	public Triple(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	public Triple() {
		
	}
	
	public void setTriple(String subject, String predicate, String object) {
		this.subject = subject;
		this.predicate = predicate;
		this.object = object;
	}
	
	public String getSubject() {
		return subject;
	}
	public String getPredicate() {
		return predicate;
	}
	public String getObject() {
		return object;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
	public void setObject(String object) {
		this.object = object;
	}
}
