package retrofitpostmon.matheusfroes.exemploretrofitpostmon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText edtCep;
    private Button btnEnviar;
    private TextView tvDados;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        edtCep = (EditText) findViewById(R.id.edtCep);
        tvDados = (TextView) findViewById(R.id.tvDados);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarEndereco();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.postmon.com.br/v1/cep/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void solicitarEndereco() {
        String cep = edtCep.getText().toString();

        PostmonService service = retrofit.create(PostmonService.class);

        Call<Endereço> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereço>() {
            @Override
            public void onResponse(Call<Endereço> call, Response<Endereço> response) {
                if (response.isSuccessful()) {
                    Endereço endereço = response.body();

                    String strEndereço = "Cidade: " + endereço.getCidade() + "\n" +
                            "Bairro: " + endereço.getBairro() + "\n" +
                            "Logradouro: " + endereço.getLogradouro();

                    tvDados.setText(strEndereço);
                }
            }

            @Override
            public void onFailure(Call<Endereço> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
