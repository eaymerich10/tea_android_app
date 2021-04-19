package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.users;

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
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.MainActivity;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines.OficinesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves.ReservesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.users.LogoutApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.users.CanviPassApi;

/**
 * Classe Admin
 * Gestiona totes les funcions de la pantalla d'administrador
 */
public class Admin extends AppCompatActivity {

    Button bt_gestionarSales;
    Button bt_gestionarReserves;
    Button bt_facturacio;
    Button bt_sortir;
    Button bt_logout;
    Button bt_canvi;
    Button bt_enviar;
    Button bt_cancelar;
    TextView tv_nova_pass;
    TextView tv_inici;
    EditText et_canvi, et_confirmarCanvi;
    SharedPreferences sharedPreferences;
    String codiAcces = "";
    String url = "";
    String pass = "";
    String rol = "";
    LogoutApi logoutApi = null;
    public static final String MyPref = "MyPref";
    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bt_gestionarSales = (Button) findViewById(R.id.b_gestionar_sala);
        bt_gestionarReserves = (Button) findViewById(R.id.b_gestionar_reserves);
        bt_facturacio = (Button) findViewById(R.id.b_facturacio_admin);
        bt_sortir = (Button) findViewById(R.id.b_sortir_admin);
        bt_logout = (Button) findViewById(R.id.b_logout_admin);
        bt_canvi = (Button) findViewById(R.id.boto_canvi_user);
        bt_enviar = (Button) findViewById(R.id.bt_enviar_user);
        bt_cancelar = (Button) findViewById(R.id.bt_cancelar_user);
        et_canvi = (EditText) findViewById(R.id.et_canvi_pass_admin);
        et_confirmarCanvi = (EditText) findViewById(R.id.et_canvi_pass_confirmar_admin);
        tv_nova_pass = (TextView) findViewById(R.id.introduex_pass_tv_user);
        tv_inici = (TextView) findViewById(R.id.reserves_tv);
        Intent i = getIntent();
        codiAcces = i.getStringExtra("codiAcces");
        url = i.getStringExtra("url");
        rol = i.getStringExtra("rol");
        logoutApi = new LogoutApi(Admin.this, url + "/logout/", codiAcces);
        OnClick();
    }

    /**
     * OnClick
     * Gestiona totes funcions quan es fa clic
     */
    private void OnClick() {
        bt_gestionarSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, OficinesAdmin.class);
                intent.putExtra("codiAcces", codiAcces);
                intent.putExtra("url", url);
                Admin.this.startActivity(intent);
            }
        });

        bt_gestionarReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, ReservesAdmin.class);
                intent.putExtra("codiAcces", codiAcces);
                intent.putExtra("url", url);
                intent.putExtra("rol", rol);
                Admin.this.startActivity(intent);
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {

            /**
             * OnClick (logout)
             * @param v
             */
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences(MyPref, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                logoutApi.logout();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Admin.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Admin.this.startActivity(intent);
                finish();
            }
        });
        bt_canvi.setOnClickListener(new View.OnClickListener() {

                                        /**
                                         * OnClick (canviPass)
                                         * @param v View
                                         */
                                        @Override
                                        public void onClick(View v) {
                                            et_canvi.setVisibility(View.VISIBLE);
                                            et_confirmarCanvi.setVisibility(View.VISIBLE);
                                            bt_enviar.setVisibility(View.VISIBLE);
                                            bt_cancelar.setVisibility(View.VISIBLE);
                                            tv_nova_pass.setVisibility(View.VISIBLE);
                                            tv_inici.setVisibility(View.INVISIBLE);
                                            bt_gestionarSales.setVisibility(View.INVISIBLE);
                                            bt_gestionarReserves.setVisibility(View.INVISIBLE);
                                            bt_facturacio.setVisibility(View.INVISIBLE);
                                            bt_logout.setVisibility(View.INVISIBLE);
                                            bt_sortir.setVisibility(View.INVISIBLE);
                                        }
                                    }
        );
        bt_enviar.setOnClickListener(new View.OnClickListener() {


            /**
             * On click. (enviar la nova contrasenya)
             *
             * @param v the View
             */
            @Override
            public void onClick(View v) {
                if(et_canvi.getText().toString().trim().equals(et_confirmarCanvi.getText().toString().trim())){
                    pass = et_canvi.getText().toString().trim();
                }else {
                    et_confirmarCanvi.setError("La contrasenya no coincideix");
                }

                if (pass.length() >= 1) {
                    CanviPassApi canviPass = new CanviPassApi(Admin.this, url, codiAcces, pass);
                    canviPass.Canvi();
                    et_canvi.setVisibility(View.INVISIBLE);
                    et_confirmarCanvi.setVisibility(View.INVISIBLE);
                    bt_enviar.setVisibility(View.INVISIBLE);
                    bt_cancelar.setVisibility(View.INVISIBLE);
                    tv_nova_pass.setVisibility(View.INVISIBLE);
                    tv_inici.setVisibility(View.VISIBLE);
                    bt_gestionarSales.setVisibility(View.VISIBLE);
                    bt_gestionarReserves.setVisibility(View.VISIBLE);
                    bt_facturacio.setVisibility(View.VISIBLE);
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
                bt_gestionarSales.setVisibility(View.VISIBLE);
                bt_gestionarReserves.setVisibility(View.VISIBLE);
                bt_facturacio.setVisibility(View.VISIBLE);
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