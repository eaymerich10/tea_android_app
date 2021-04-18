package aymerich.ioc.cat.tea2_clientm_aymerichs.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import aymerich.ioc.cat.tea2_clientm_aymerichs.tools.Parser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Oficina;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.EditarOficinaApi;

/**
 * Clase Registre
 * Formulari de registre
 */
public class EditarOficina extends AppCompatActivity {

    //Definició de variables
    EditText et_nom,  et_capacitat, et_preu, et_serveis, et_provincia, et_poblacio, et_direccio;
    TextView tv_nom, tv_tipus, tv_capacitat, tv_preu, tv_serveis, tv_provincia, tv_poblacio, tv_direccio;
    Button bt_enviar;
    String url = "";
    String tipus = "";
    String codiAcces = "";
    String idOficina = "";
    String oficinaString = "";
    Parser parser;
    private static final String Sp_Status = "Status";
    private static final String MyPref = "MyPref";
    private boolean okText = false;
    private ArrayList tipus_oficines;
    private ArrayAdapter adapter;
    private ListView lv1;
    public static Activity fa;
    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_oficina);
        et_nom = (EditText) findViewById(R.id.et_nom_oficina_editar);
        et_capacitat = (EditText) findViewById(R.id.et_capacitat_oficina_editar);
        et_preu = (EditText) findViewById(R.id.et_preu_oficina_editar);
        et_direccio = (EditText) findViewById(R.id.et_direccio_oficina_editar);
        et_poblacio = (EditText) findViewById(R.id.et_poblacio_oficina_editar);
        et_provincia = (EditText) findViewById(R.id.et_provincia_oficina_editar);
        et_serveis = (EditText) findViewById(R.id.et_serveis_oficina_editar);
        tv_nom = (TextView) findViewById(R.id.tv_nom_oficina_editar);
        tv_tipus = (TextView) findViewById(R.id.tv_tipus_oficina_editar);
        tv_capacitat = (TextView) findViewById(R.id.tv_capacitat_oficina_editar);
        tv_preu = (TextView) findViewById(R.id.tv_preu_editar);
        tv_poblacio = (TextView) findViewById(R.id.tv_poblacio_oficina_editar);
        tv_direccio = (TextView) findViewById(R.id.tv_direccio_oficina_editar);
        tv_provincia = (TextView) findViewById(R.id.tv_provincia_oficina_editar);
        tv_serveis = (TextView) findViewById(R.id.tv_serveis_oficina_editar);
        bt_enviar = (Button) findViewById(R.id.b_enviar_canvis_oficina);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        codiAcces = i.getStringExtra("codiAcces");
        idOficina = i.getStringExtra("idOficina");
        oficinaString = i.getStringExtra("oficina");
        tipus_oficines=new ArrayList();
        tipus_oficines.add("OFICINA_PRIVADA");
        tipus_oficines.add("SUITE_OFICINES");
        tipus_oficines.add("ESCRIPTORI_DEDICAT");
        fa = this;
        Log.i("oficinaString", oficinaString);
        parser = new Parser();
        Oficina ofi = new Oficina(null,null,null,null);
        ofi = parser.parserStringToOficina(oficinaString);
        omplirCamps(ofi);

        adapter=new ArrayAdapter(this, R.layout.text_view_list,tipus_oficines);
        lv1=findViewById(R.id.lv_tipus_oficina_editar);
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
                Oficina oficina = new Oficina(et_nom.getText().toString().trim(), tipus.trim(), et_capacitat.getText().toString().trim(), et_preu.getText().toString().trim());
                oficina.setServeis(et_serveis.getText().toString().trim());
                oficina.setDireccio(et_direccio.getText().toString().trim());
                oficina.setHabilitada("true");
                oficina.setProvincia(et_provincia.getText().toString().trim());
                oficina.setPoblacio(et_poblacio.getText().toString().trim());
                oficina.setIdOficina(idOficina);
                okText = NoNullText(et_nom.getText().toString().trim(), tipus.trim(), et_capacitat.getText().toString().trim(), et_preu.getText().toString().trim());
                if (okText == true) {
                    try {
                        EditarOficinaApi editarOficinaApi = new EditarOficinaApi(EditarOficina.this, oficina, url, codiAcces, idOficina);
                        editarOficinaApi.EditarOficina();
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
                Toast.makeText(EditarOficina.this,"Clica un tipus",Toast.LENGTH_SHORT).show();
            }
            if (capacitat.length() < 1) {
                et_capacitat.setError("Introdueix una capacitat");
            }
            if (preu.length() < 1) {
                et_preu.setError("Introdueix un preu per l'oficina");
            }
            Toast.makeText(EditarOficina.this, "Algun camp no és correcte", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    public void omplirCamps(Oficina oficina){
        et_nom.setText(oficina.getNom());
        tipus = oficina.getTipus();
        et_capacitat.setText(oficina.getCapacitat());
        et_preu.setText(oficina.getPreu());
        et_direccio.setText(oficina.getDireccio());
        et_poblacio.setText(oficina.getPoblacio());
        et_provincia.setText(oficina.getProvincia());
        et_serveis.setText(oficina.getServeis());
    }
}

