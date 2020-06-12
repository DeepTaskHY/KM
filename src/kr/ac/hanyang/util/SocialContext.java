package kr.ac.hanyang.util;

import org.json.simple.JSONObject;

public class SocialContext{
	private long id;
	private JSONObject isro_social; 
	private JSONObject information;
	private String name;
	private String result;
	
	/**
	 * json 타입의 소셜컨텍스트 메시지를 받아 SocialContext 객체를 생성하는 생성자
	 * @param socialContext : json 타입의 메시지
	 */
	public SocialContext(JSONObject socialContext) {
		id = (long) socialContext.get("id");
		isro_social = (JSONObject) socialContext.get("isro_social");
		name = socialContext.get("name").toString();
		if (socialContext.containsKey("information")) {
			information = (JSONObject) socialContext.get("information");
		}
		if (socialContext.containsKey("result")) {
			result = socialContext.get("result").toString();
		}
	}
	
	public SocialContext() {
		isro_social = new JSONObject();
		information = new JSONObject();
	}
	
	
	public long getId() {
		return id;
	}
	public JSONObject getInformation() {
		return information;
	}
	public JSONObject getIsro_social() {
		return isro_social;
	}
	public String getName() {
		return name;
	}
	public String getResult() {
		return result;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	public void setInformation(JSONObject information) {
		this.information = information;
	}
	public void setIsro_social(JSONObject isro_social) {
		this.isro_social = isro_social;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
