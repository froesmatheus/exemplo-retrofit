package retrofitpostmon.matheusfroes.exemploretrofitpostmon;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mathe on 28/03/2016.
 */
public interface PostmonService {

    @GET("{id}")
    Call<Endereço> getEndereco(@Path("id") String cep);
}
