package life.laboratory.rosbank2018;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface Graph_interface {

    @POST("/api/v1/quotation")
    Call <Model> setQuery (@Body Query_model.MyQuery temp);//(@Query("From") Integer id_quatation_from, @Query("To") Integer id_quotation_to, @Query("Quant") String quant);
}
