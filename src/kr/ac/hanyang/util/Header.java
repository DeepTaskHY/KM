package kr.ac.hanyang.util;

import java.text.DecimalFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Header{
	
	private String source;
	private JSONArray target;
	private JSONArray content;
	private String timestamp;
	
	/**
	 * json 타입의 헤더 메시지를 받아 Header 객체를 생성하는 생성자
	 * @param header : json 타입의 메시지
	 */
	public Header(JSONObject header) {
		this.source = header.get("source").toString();
		this.target = (JSONArray) header.get("target");
		this.content = (JSONArray) header.get("content");
		this.timestamp = header.get("timestamp").toString();
	}
	
	public Header() {
		target = new JSONArray();
		content = new JSONArray();
	}
	
	public String getSource() {
		return source;
	}
	public JSONArray getTarget() {
		return target;
	}
	public JSONArray getContent() {
		return content;
	}
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	@SuppressWarnings("unchecked")
	public void setTarget(String target) {
		this.target.add(target);
	}
	public void setContent(JSONArray content) {
		this.content = content;
	}
	public void setTimestamp() {
		long currentTime = System.currentTimeMillis();
		DecimalFormat decimalFormat = new DecimalFormat("#.#########");
		this.timestamp = decimalFormat.format((double) currentTime / 1000);
	}
	
}
