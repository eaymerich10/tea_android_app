package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves.LlistarReservesApi;

/**
 *
 * Classe per l'activity que gestiona les reserves per l'administrador
 *
 */
public class ReservesAdmin extends AppCompatActivity {

    Button llistarReserves, eliminarReserva;
    private String codiAcces = "";
    private String url = "";
    private String rol = "";
    LlistarReservesApi llistarReservesApi;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_admin);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        codiAcces = i.getStringExtra("codiAcces");
        rol = i.getStringExtra("rol");
        llistarReserves = (Button) findViewById(R.id.b_llistar_reserves_admin);
        llistarReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llistarReservesApi = new LlistarReservesApi(ReservesAdmin.this, url, codiAcces, rol);
                llistarReservesApi.llistar();
            }
        });
    }
}