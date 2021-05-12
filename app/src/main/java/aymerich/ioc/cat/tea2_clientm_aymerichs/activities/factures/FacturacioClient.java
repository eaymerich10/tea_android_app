package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.factures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.factures.LlistarFacturesApi;

/**
 *
 * Classe encarregada de la facturació del client
 */
public class FacturacioClient extends AppCompatActivity {
    Button llistarFactures;
    private String codiAcces = "";
    private String url = "";
    private String rol = "";
    LlistarFacturesApi llistarFacturesApi;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturacio_client);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        codiAcces = i.getStringExtra("codiAcces");
        rol = i.getStringExtra("rol");
        llistarFactures = (Button) findViewById(R.id.b_llistar_factures_client);
        llistarFactures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llistarFacturesApi = new LlistarFacturesApi(FacturacioClient.this, url, codiAcces, rol);
                llistarFacturesApi.llistar();
            }
        });
    }
}