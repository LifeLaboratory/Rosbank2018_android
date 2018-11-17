package life.laboratory.rosbank2018.server;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Server {
    @POST("/api/v1/auth")
    Call<Session> auth(@Body User data);

    @GET("/api/v1/quotation")
    Call<Person> getUserInfo(@Query("Session") String UUID);
}
