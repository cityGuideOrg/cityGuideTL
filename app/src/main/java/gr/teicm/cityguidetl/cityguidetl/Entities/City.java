package gr.teicm.cityguidetl.cityguidetl.Entities;

import java.util.HashSet;

public class City{

	private Integer id;
	private String placeId;
	private String longitude;
	private String latitude;
	private String woe_name;
	private String content;


	public City() {
	}

	public Integer getId() {
		return id;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getWoe_name() {
		return woe_name;
	}

	public void setWoe_name(String woe_name) {
		this.woe_name = woe_name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
