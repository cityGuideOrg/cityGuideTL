package gr.teicm.cityguidetl.cityguidetl.Entities;

public class Photos {

	public Integer getTotalNearPhotos() {
		return totalNearPhotos;
	}

	public void setTotalNearPhotos(Integer totalNearPhotos) {
		this.totalNearPhotos = totalNearPhotos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	private Integer totalNearPhotos;

	private String id;

	private Double longitude;

	private Double latitude;







}
