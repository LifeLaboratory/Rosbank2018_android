package life.laboratory.rosbank2018;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Graph_interface {

    @POST("/api/v1/quotation")
    Call <Model> setQuery (@Body Query_model.MyQuery temp);//(@Query("From") Integer id_quatation_from, @Query("To") Integer id_quotation_to, @Query("Quant") String quant);
}
