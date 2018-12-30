package gr.teicm.cityguidetl.cityguidetl.Utilities;

import java.io.IOException;

import gr.teicm.cityguidetl.cityguidetl.Activities.LoginActivity;
import gr.teicm.cityguidetl.cityguidetl.Activities.MainActivity;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticatorInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request()
				.newBuilder()
				.addHeader("Authorization", "Bearer " + MainActivity.accessToken)
				.build();

		Response response = chain.proceed(request);

		if(response.code() == 401) {

			MainActivity.accessToken = LoginActivity.authenticateService.refresh(MainActivity.refreshToken, "refresh_token")
			.execute()
			.body()
			.getAccess_token();

			response =  chain.proceed(chain.request()
					.newBuilder()
					.addHeader("Authorization", "Bearer " + MainActivity.accessToken)
					.build());
		}

		return response;
	}
}
