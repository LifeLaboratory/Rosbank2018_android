package life.laboratory.rosbank2018.server;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Server {
    @POST("/api/v1/auth")
    Call<Session> auth(@Body User data);
}
