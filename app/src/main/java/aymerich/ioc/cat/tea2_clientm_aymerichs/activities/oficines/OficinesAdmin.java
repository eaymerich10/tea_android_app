package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.oficines.LlistatOficinesApi;

public class OficinesAdmin extends AppCompatActivity {

    Button b_llistar_sales;
    Button b_crear_oficina;
    String codiAcces = "";
    String url = "";
    public ArrayList<Oficina> sales = new ArrayList<Oficina>();
    LlistatOficinesApi llistatSales = null;
    CreaOficina creaOficina = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oficines_admin);
        b_llistar_sales = (Button) findViewById(R.id.b_obtenir_sales);
        b_crear_oficina = (Button) findViewById(R.id.b_crear_oficina);
        Intent i = getIntent();
        codiAcces = i.getStringExtra("codiAcces");
        url = i.getStringExtra("url");
        onClick();
    }

    private void onClick() {
        b_llistar_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llistatSales = new LlistatOficinesApi(OficinesAdmin.this, url + "oficines/", codiAcces);
                llistatSales.llistat();
            }
        });
        b_crear_oficina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OficinesAdmin.this, CreaOficina.class);
                intent.putExtra("codiAcces", codiAcces);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }
}