package gr.teicm.cityguidetl.cityguidetl.Services;

import java.util.ArrayList;
import java.util.List;

import gr.teicm.cityguidetl.cityguidetl.Entities.BestRoute;
import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.Cost;
import gr.teicm.cityguidetl.cityguidetl.Entities.Point;
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

	@Headers({"Accept: application/json"})
	@GET("/flickr/{id}")
	Call<City> getCityWithInterestingPlaces(@Path("id") long id);

	@Headers({"Accept: application/json"})
	@POST("/route")
	Call<Cost> findBestRoute(@Body BestRoute body);

	@Headers({"Accept: application/json"})
	@POST("/visitedpoints")
	Call<Point> addVisitedPoint(@Body Point point);

	@Headers({"Accept: application/json"})
	@DELETE("/visitedpoints/{id}")
	Call<Void> deleteVisitedPoint(@Path("id") Integer id);

	@Headers({"Accept: application/json"})
	@GET("/visitedpoints")
	Call<List<Point>> getVisitedPoints();

}
