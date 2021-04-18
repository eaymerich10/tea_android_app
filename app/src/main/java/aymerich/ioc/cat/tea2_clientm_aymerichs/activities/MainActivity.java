package aymerich.ioc.cat.tea2_clientm_aymerichs.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.LoginApi;

/**
 * MainActivity
 * Controlarà la pantalla del login i le crida per el registre de nous usuaris
 */
public class MainActivity extends AppCompatActivity {

    //Definició de variables
    public EditText et_username, et_password;
    TextView tv_registre;
    Button btn_login;
    SharedPreferences sharedPreferences;
    String url = "http://192.168.1.96:8080/";
    public static final String Sp_Status = "Status";
    public static final String MyPref = "MyPref";
    public String username, password, codiAcces;
    public Boolean sortida = false;


    /**
     * OnCreate
     *
     * @param savedInstanceState Crida a la classe Registre
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = (EditText) findViewById(R.id.et_email_registre);
        et_password = (EditText) findViewById(R.id.et_password_registre);
        tv_registre = (TextView) findViewById(R.id.tv_registre);
        btn_login = (Button) findViewById(R.id.b_enviar_registre);
        sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
        Login(); //Crida el mètode per fer el login
        tv_registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registre.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }


    /**
     * Metode Login.
     * Crida a la classe LoginApi.
     */
    public void Login() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_username.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (username.length() >= 1) { //Si l'usuari no es buit
                    if (password.length() >= 1) { //Si la contrasenya no és buida
                        try {
                            LoginApi loginApi = new LoginApi(MainActivity.this, username, password, url);
                            loginApi.Login(); //Crida el mètode login de la classe LoginApi, pero poder iniciar sessió al servidor.
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        et_password.setError("Introdueix una Contrasenya");
                    }
                } else {
                    et_username.setError("Introdueix un Usuari");
                }
            }
        });
    }

    /**
     * OnBackPressed
     * dos clics enrere tanca la app
     */
    @Override
    public void onBackPressed() {
        if (sortida) {
            finish();
        } else {
            sortida = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sortida = false;
                }
            }, 3 * 1000);
        }
    }

}
