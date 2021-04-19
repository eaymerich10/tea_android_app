package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.MainActivity;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.users.RegisterApi;

/**
 * Clase Registre
 * Formulari de registre
 */
public class Registre extends AppCompatActivity {

    //Definició de variables
    EditText et_email, et_nom, et_password, et_confirmarPassword, et_cifEm, et_direccio, et_poblacio, et_provincia, et_rol;
    TextView tv_email, tv_nom, tv_password, tv_cifEm, tv_poblacio, tv_direccio, tv_provincia, tv_rol;
    Button bt_enviar, bt_tornar;
    String url = "";
    private static final String Sp_Status = "Status";
    private static final String MyPref = "MyPref";
    private String email, password, confirmarPassword, nom, cifEmp, direccio, poblacio, provincia, rol;
    private boolean okText = false;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        et_email = (EditText) findViewById(R.id.et_email_registre);
        et_password = (EditText) findViewById(R.id.et_password_registre);
        et_confirmarPassword = (EditText) findViewById(R.id.et_confirmar_password_registre);
        et_nom = (EditText) findViewById(R.id.et_nom_registre);
        et_cifEm = (EditText) findViewById(R.id.et_cif_registre);
        et_direccio = (EditText) findViewById(R.id.et_direccio_registre);
        et_poblacio = (EditText) findViewById(R.id.et_poblacio_registre);
        et_provincia = (EditText) findViewById(R.id.et_provincia_registre);
        et_rol = (EditText) findViewById(R.id.et_rol_registre);
        tv_email = (TextView) findViewById(R.id.tv_email_registre);
        tv_password = (TextView) findViewById(R.id.tv_password_registre);
        tv_nom = (TextView) findViewById(R.id.tv_nom_registre);
        tv_cifEm = (TextView) findViewById(R.id.tv_cif_registre);
        tv_poblacio = (TextView) findViewById(R.id.tv_poblacio_registre);
        tv_direccio = (TextView) findViewById(R.id.tv_direccio_registre);
        tv_provincia = (TextView) findViewById(R.id.tv_provincia_registre);
        tv_rol = (TextView) findViewById(R.id.tv_rol_registre);
        bt_tornar = (Button) findViewById(R.id.b_tornar_registre);
        bt_enviar = (Button) findViewById(R.id.b_enviar_registre);
        Intent i = getIntent();
        url = i.getStringExtra("url");
        bt_tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registre.this, MainActivity.class);
                startActivity(intent);
            }
        });
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString().trim();
                if(et_password.getText().toString().trim().equals(et_confirmarPassword.getText().toString().trim())){
                    password = et_password.getText().toString().trim();
                } else{
                    et_confirmarPassword.setError("La contrasenya no coincideix");
                }
                nom = et_nom.getText().toString().trim();
                cifEmp = et_cifEm.getText().toString().trim();
                direccio = et_direccio.getText().toString().trim();
                poblacio = et_poblacio.getText().toString().trim();
                provincia = et_provincia.getText().toString().trim();
                rol = et_rol.getText().toString().trim();
                okText = NoNullText(email, password, nom, cifEmp, direccio, poblacio, provincia, rol);
                if (okText == true) {
                    try {
                        RegisterApi registerApi = new RegisterApi(Registre.this, email, password, nom, cifEmp, direccio, poblacio, provincia, rol, url);
                        registerApi.Register();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * No null text boolean.
     * Controla que no hi hagi caps buits
     *
     * @param email     the email
     * @param pass      the pass
     * @param nom       the nom
     * @param cif       the cif
     * @param direccio  the direccio
     * @param poblacio  the poblacio
     * @param provincia the provincia
     * @param rol       the rol
     * @return the boolean
     */
    public boolean NoNullText(String email, String pass, String nom, String cif, String direccio, String poblacio, String provincia, String rol) {
        if (email.length() < 1 || pass.length() < 1 || nom.length() < 1 || cif.length() < 1 || direccio.length() < 1 || poblacio.length() < 1 || provincia.length() < 1 || rol.length() < 1) {
            //Si algun camp es buit, marcara l'error a la pantalla
            if (email.length() < 1) {
                et_email.setError("Introdueix un email");
            }
            if (pass.length() < 1) {
                et_password.setError("Introdueix una contrasenya");
            }
            if (nom.length() < 1) {
                et_nom.setError("Introdueix un nom");
            }
            if (cif.length() < 1) {
                et_cifEm.setError("Introdueix un CIF per l'empresa");
            }
            if (direccio.length() < 1) {
                et_direccio.setError("Introdueix una direcció");
            }
            if (poblacio.length() < 1) {
                et_poblacio.setError("Introdueix una població");
            }
            if (provincia.length() < 1) {
                et_provincia.setError("Introdueix una provincia");
            }
            if (rol.length() < 1) {
                et_rol.setError("Introdueix un rol");
            }
            Toast.makeText(Registre.this, "Algun camp no és correcte", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}

