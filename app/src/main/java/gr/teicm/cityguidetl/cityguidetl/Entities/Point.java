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

	public double distance(Point point) {

		return Point.distance(latitude.floatValue(), point.latitude.floatValue(), longitude.floatValue(), point.longitude.floatValue(), 1, 1);

		//return point.latitude.subtract(this.latitude).pow(2).add(point.longitude.subtract(this.longitude).pow(2)).floatValue();
	}

	public static double distance(double lat1, double lat2, double lon1,
								  double lon2, double el1, double el2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return Math.sqrt(distance);
	}

}
