package life.laboratory.rosbank2018;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Buying_interface {
    @POST ("/api/v1/quotation")
    Call <Buying> setBuying(@Body Buying.Buy_class temp, @Body Double price);
}
