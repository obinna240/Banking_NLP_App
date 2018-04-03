package com.db.tasks.objects;



import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DBDoc
{
	private String heading;
	private String body;
	@Id
	private String id;
	private Set<String> company;
	private Set<String> geoLocation;
	private Set<String> people;
	
	public Set<String> getCompany() {
		return company;
	}
	public void setCompany(Set<String> company) {
		this.company = company;
	}
	public Set<String> getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(Set<String> geoLocation) {
		this.geoLocation = geoLocation;
	}
	public Set<String> getPeople() {
		return people;
	}
	public void setPeople(Set<String> people) {
		this.people = people;
	}

	


	
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

}
