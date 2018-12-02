package gr.teicm.cityguidetl.cityguidetl.Services;

import java.util.ArrayList;

import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CityService {

	@GET("/city")
	Call<ArrayList<City>> getCities();

	@GET("/cities/{id}")
	Call<City> getCity(@Path("id") String id);

	@Headers({"Accept: application/json"})
	@POST("/cities")
	Call<City> createCity(@Body City city);

	@DELETE("/cities/{id}")
	Call<Void> deleteCity(@Path("id") long id);

	@Headers({"Accept: application/json"})
	@PUT("/city/{id}")
	Call<City> updateCity(@Body City city, @Path("id") long id);
}
