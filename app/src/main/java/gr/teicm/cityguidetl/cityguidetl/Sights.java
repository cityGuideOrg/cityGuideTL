package gr.teicm.cityguidetl.cityguidetl;

public class Sights {

   private int totalPhotos;
   private  long id;
   private String longitude;
   private String latitude;

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Sights (int totalPhotos, long id, String longitude , String latitude)
   {
        this.totalPhotos=totalPhotos;
        this.id=id;
        this.longitude=longitude;
        this.latitude=latitude;

   }
}
