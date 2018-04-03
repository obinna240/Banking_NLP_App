package com.db.tasks.objects;

import java.util.List;

public class DocStructure 
{
	private String heading;
	private String body;
	private String id;
	private List<String> bodyp;
	
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getBodyp() {
		return bodyp;
	}
	public void setBodyp(List<String> bodyp) {
		this.bodyp = bodyp;
	}
	
}
