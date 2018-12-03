package gr.teicm.cityguidetl.cityguidetl.Entities;


import java.math.BigDecimal;

public class Point {
	public Integer id;
	public String pointName;
	private BigDecimal longitude;
	private BigDecimal latitude;

	public Integer getId() {
		return id;
	}
	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

}
