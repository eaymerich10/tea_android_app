package aymerich.ioc.cat.tea2_clientm_aymerichs.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.CanviPassApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.LogoutApi;

/**
 * Classe User
 * Gestiona totes les funcions de la pantalla d'usuari
 */
public class User extends AppCompatActivity {

    Button bt_reservarSales;
    Button bt_veureReserves;
    Button bt_factures;
    Button bt_sortir;
    Button bt_logout;
    Button bt_canvi;
    Button bt_enviar;
    Button bt_cancelar;
    TextView tv_nova_pass;
    TextView tv_inici;
    EditText et_canvi;
    SharedPreferences sharedPreferences;
    String codiAcces = "";
    String url = "";
    String pass = "";
    LogoutApi logoutApi = null;
    public static final String MyPref = "MyPref";
    private Boolean exit = false;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        bt_veureReserves = (Button) findViewById(R.id.b_veure_reserves_user);
        bt_reservarSales = (Button) findViewById(R.id.b_reservar_sales_user);
        bt_factures = (Button) findViewById(R.id.b_veure_factures);
        bt_sortir = (Button) findViewById(R.id.b_sortida_user);
        bt_logout = (Button) findViewById(R.id.b_logout_user);
        bt_canvi = (Button) findViewById(R.id.boto_canvi_user);
        bt_enviar = (Button) findViewById(R.id.bt_enviar_user);
        bt_cancelar = (Button) findViewById(R.id.bt_cancelar_user);
        et_canvi = (EditText) findViewById(R.id.et_canvi_pass_user);
        tv_nova_pass = (TextView) findViewById(R.id.introduex_pass_tv_user);
        tv_inici = (TextView) findViewById(R.id.reserves_tv);
        Intent i = getIntent();
        codiAcces = i.getStringExtra("codiAcces");
        url = i.getStringExtra("url");
        logoutApi = new LogoutApi(User.this, url + "/logout/", codiAcces);
        OnClick();
    }

    /**
     * OnClick. Gestiona els Onclick de l'activity
     */
    private void OnClick() {
        bt_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                logoutApi.logout();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(User.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                User.this.startActivity(intent);
                finish();
            }
        });
        bt_canvi.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            et_canvi.setVisibility(View.VISIBLE);
                                            bt_enviar.setVisibility(View.VISIBLE);
                                            bt_cancelar.setVisibility(View.VISIBLE);
                                            tv_nova_pass.setVisibility(View.VISIBLE);
                                            tv_inici.setVisibility(View.INVISIBLE);
                                            bt_veureReserves.setVisibility(View.INVISIBLE);
                                            bt_reservarSales.setVisibility(View.INVISIBLE);
                                            bt_factures.setVisibility(View.INVISIBLE);
                                            bt_logout.setVisibility(View.INVISIBLE);
                                            bt_sortir.setVisibility(View.INVISIBLE);
                                        }
                                    }
        );
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = et_canvi.getText().toString().trim();
                if (pass.length() >= 1) {
                    CanviPassApi canviPass = new CanviPassApi(User.this, url, codiAcces, pass);
                    canviPass.Canvi();
                    et_canvi.setVisibility(View.INVISIBLE);
                    bt_enviar.setVisibility(View.INVISIBLE);
                    bt_cancelar.setVisibility(View.INVISIBLE);
                    tv_nova_pass.setVisibility(View.INVISIBLE);
                    tv_inici.setVisibility(View.VISIBLE);
                    bt_reservarSales.setVisibility(View.VISIBLE);
                    bt_veureReserves.setVisibility(View.VISIBLE);
                    bt_factures.setVisibility(View.VISIBLE);
                    bt_logout.setVisibility(View.VISIBLE);
                    bt_sortir.setVisibility(View.VISIBLE);
                } else {
                    et_canvi.setError("Introdueix una contrasenya.");
                }

            }
        });
        bt_cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                et_canvi.setVisibility(View.INVISIBLE);
                bt_enviar.setVisibility(View.INVISIBLE);
                bt_cancelar.setVisibility(View.INVISIBLE);
                tv_nova_pass.setVisibility(View.INVISIBLE);
                tv_inici.setVisibility(View.VISIBLE);
                bt_reservarSales.setVisibility(View.VISIBLE);
                bt_veureReserves.setVisibility(View.VISIBLE);
                bt_factures.setVisibility(View.VISIBLE);
                bt_logout.setVisibility(View.VISIBLE);
                bt_sortir.setVisibility(View.VISIBLE);

            }
        });
        bt_sortir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutApi.logout();
                finish();
            }
        });
    }

    /**
     * On back pressed.
     */
    @Override
    public void onBackPressed() {
        if (exit) {
            logoutApi.logout();
            finish();
        } else {
            Toast.makeText(this, "Torna a fer clic per sortir",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }

    }

}
