package aymerich.ioc.cat.tea2_clientm_aymerichs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.ArrayList;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.CreaOficinaApi;

/**
 * Clase Registre
 * Formulari de registre
 */
public class CreaOficina extends AppCompatActivity {

    //Definició de variables
    EditText et_nom, et_capacitat, et_preu, et_serveis, et_provincia, et_poblacio, et_direccio;
    TextView tv_nom, tv_tipus, tv_capacitat, tv_preu, tv_serveis, tv_provincia, tv_poblacio, tv_direccio;
    Button bt_enviar;
    String url = "";
    String codiAcces = "";
    String tipus = "";
    private ArrayList tipus_oficines;
    private ArrayAdapter adapter;
    private ListView lv1;
    private static final String Sp_Status = "Status";
    private static final String MyPref = "MyPref";
    private String email, password, nom, cifEmp, direccio, poblacio, provincia, rol;
    private boolean okText = false;
    public static Activity fa;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_oficina);
        et_nom = (EditText) findViewById(R.id.et_nom_oficina);
        et_capacitat = (EditText) findViewById(R.id.et_capacitat_oficina);
        et_preu = (EditText) findViewById(R.id.et_preu_oficina);
        et_direccio = (EditText) findViewById(R.id.et_direccio_oficina);
        et_poblacio = (EditText) findViewById(R.id.et_poblacio_oficina);
        et_provincia = (EditText) findViewById(R.id.et_provincia_oficina);
        et_serveis = (EditText) findViewById(R.id.et_serveis_oficina);
        tv_nom = (TextView) findViewById(R.id.tv_nom_oficina);
        tv_tipus = (TextView) findViewById(R.id.tv_tipus_oficina);
        tv_capacitat = (TextView) findViewById(R.id.tv_capacitat_oficina);
        tv_preu = (TextView) findViewById(R.id.tv_preu);
        tv_poblacio = (TextView) findViewById(R.id.tv_poblacio_oficina);
        tv_direccio = (TextView) findViewById(R.id.tv_direccio_oficina_editar);
        tv_provincia = (TextView) findViewById(R.id.tv_provincia_oficina);
        tv_serveis = (TextView) findViewById(R.id.tv_serveis_oficina);
        bt_enviar = (Button) findViewById(R.id.b_crear_nova_oficina);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        codiAcces = i.getStringExtra("codiAcces");
        tipus_oficines=new ArrayList();
        tipus_oficines.add("OFICINA_PRIVADA");
        tipus_oficines.add("SUITE_OFICINES");
        tipus_oficines.add("ESCRIPTORI_DEDICAT");
        fa = this;
        adapter=new ArrayAdapter(this, R.layout.text_view_list,tipus_oficines);
        lv1=findViewById(R.id.lv_tipus_oficina);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tipus = lv1.getItemAtPosition(position).toString();
                view.setSelected(true);
            }
        });
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Oficina oficina = new Oficina(et_nom.getText().toString().trim(), tipus, et_capacitat.getText().toString().trim(), et_preu.getText().toString().trim());
                oficina.setServeis(et_serveis.getText().toString().trim());
                oficina.setDireccio(et_direccio.getText().toString().trim());
                oficina.setHabilitada("true");
                oficina.setProvincia(et_provincia.getText().toString().trim());
                oficina.setPoblacio(et_poblacio.getText().toString().trim());
                okText = NoNullText(et_nom.getText().toString().trim(), tipus, et_capacitat.getText().toString().trim(), et_preu.getText().toString().trim());
                if (okText == true) {
                    try {
                        CreaOficinaApi creaOficinaApi = new CreaOficinaApi(CreaOficina.this, oficina, url, codiAcces);
                        creaOficinaApi.Crearoficina();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public boolean NoNullText(String nom, String tipus, String capacitat, String preu) {
        if (nom.length() < 1 || tipus.length() < 1 || capacitat.length() < 1 || preu.length() < 1) {
            //Si algun camp es buit, marcara l'error a la pantalla
            if (nom.length() < 1) {
                et_nom.setError("Introdueix un nom");
            }
            if (tipus.length() < 1) {
                Toast.makeText(CreaOficina.this,"Clica un tipus",Toast.LENGTH_SHORT).show();
            }
            if (capacitat.length() < 1) {
                et_capacitat.setError("Introdueix una capacitat");
            }
            if (preu.length() < 1) {
                et_preu.setError("Introdueix un preu per l'oficina");
            }
            Toast.makeText(CreaOficina.this, "Algun camp no és correcte", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}

