package gr.teicm.cityguidetl.cityguidetl.Services;

import java.util.ArrayList;

import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.OAuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuthenticateService {

	@FormUrlEncoded
	@POST("/oauth/token")
	@Headers("Authorization: Basic Z3JleWNyMHc6c2FkbGlmZTIz")
	Call<OAuthResponse> login(@Field("username") String username, @Field("password") String password, @Field("grant_type") String type);

	@FormUrlEncoded
	@POST("/oauth/token")
	@Headers("Authorization: Basic Z3JleWNyMHc6c2FkbGlmZTIz")
	Call<OAuthResponse> refresh(@Field("refresh_token") String refresh_token, @Field("grant_type") String type);

}
