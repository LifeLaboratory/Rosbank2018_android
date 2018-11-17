package life.laboratory.rosbank2018;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Buying_interface {
    @POST ("/api/v1/quotation")
    Call <Buying> setBuying(@Body Buying.Buy_class temp);
}
