package aymerich.ioc.cat.tea2_clientm_aymerichs.activities.users;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import aymerich.ioc.cat.tea2_clientm_aymerichs.R;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.MainActivity;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves.ReservesUser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.oficines.BuscarOficines;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.users.CanviPassApi;
import aymerich.ioc.cat.tea2_clientm_aymerichs.network.users.LogoutApi;

/**
 * Classe User
 * Gestiona totes les funcions de la pantalla d'usuari
 */
public class User extends AppCompatActivity {

    Button bt_reservarOficines;
    Button bt_veureReserves;
    Button bt_factures;
    Button bt_sortir;
    Button bt_logout;
    Button bt_enviar;
    Button bt_cancelar;
    TextView tv_nova_pass;
    TextView tv_inici;
    ImageView iv_canviarPass;
    EditText et_canvi, et_confirmarCanvi;
    SharedPreferences sharedPreferences;
    String codiAcces = "";
    String url = "";
    String pass = "";
    String rol = "";
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
        bt_reservarOficines = (Button) findViewById(R.id.b_reservar_sales_user);
        bt_factures = (Button) findViewById(R.id.b_veure_factures);
        bt_sortir = (Button) findViewById(R.id.b_sortida_user);
        bt_logout = (Button) findViewById(R.id.b_logout_user);
        iv_canviarPass = (ImageView) findViewById(R.id.iv_canviar_contrasenya_user);
        bt_enviar = (Button) findViewById(R.id.bt_enviar_user);
        bt_cancelar = (Button) findViewById(R.id.bt_cancelar_user);
        et_canvi = (EditText) findViewById(R.id.et_canvi_pass_user);
        et_confirmarCanvi = (EditText) findViewById(R.id.et_canvi_pass_confirmar_user);
        tv_nova_pass = (TextView) findViewById(R.id.introduex_pass_tv_user);
        tv_inici = (TextView) findViewById(R.id.reserves_tv);
        Intent i = getIntent();
        codiAcces = i.getStringExtra("codiAcces");
        url = i.getStringExtra("url");
        rol = i.getStringExtra("rol");
        logoutApi = new LogoutApi(User.this, url + "/logout/", codiAcces);
        OnClick();
    }

    /**
     * OnClick. Gestiona els Onclick de l'activity
     */
    private void OnClick() {
        bt_veureReserves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, ReservesUser.class);
                intent.putExtra("codiAcces", codiAcces);
                intent.putExtra("url", url);
                intent.putExtra("rol", rol);
                User.this.startActivity(intent);
            }
        });
        bt_reservarOficines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this, BuscarOficines.class);
                intent.putExtra("codiAcces", codiAcces);
                intent.putExtra("url", url);
                intent.putExtra("rol", rol);
                User.this.startActivity(intent);
            }
        });

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
        iv_canviarPass.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            et_canvi.setVisibility(View.VISIBLE);
                                            et_confirmarCanvi.setVisibility(View.VISIBLE);
                                            bt_enviar.setVisibility(View.VISIBLE);
                                            bt_cancelar.setVisibility(View.VISIBLE);
                                            tv_nova_pass.setVisibility(View.VISIBLE);
                                            tv_inici.setVisibility(View.INVISIBLE);
                                            bt_veureReserves.setVisibility(View.INVISIBLE);
                                            bt_reservarOficines.setVisibility(View.INVISIBLE);
                                            bt_factures.setVisibility(View.INVISIBLE);
                                            bt_logout.setVisibility(View.INVISIBLE);
                                            bt_sortir.setVisibility(View.INVISIBLE);
                                        }
                                    }
        );
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_canvi.getText().toString().trim().equals(et_confirmarCanvi.getText().toString().trim())){
                    pass = et_canvi.getText().toString().trim();
                }else {
                    et_confirmarCanvi.setError("La contrasenya no coincideix");
                }
                if (pass.length() >= 1) {
                    CanviPassApi canviPass = new CanviPassApi(User.this, url, codiAcces, pass);
                    canviPass.Canvi();
                    et_canvi.setVisibility(View.INVISIBLE);
                    et_confirmarCanvi.setVisibility(View.INVISIBLE);
                    bt_enviar.setVisibility(View.INVISIBLE);
                    bt_cancelar.setVisibility(View.INVISIBLE);
                    tv_nova_pass.setVisibility(View.INVISIBLE);
                    tv_inici.setVisibility(View.VISIBLE);
                    bt_reservarOficines.setVisibility(View.VISIBLE);
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
                et_confirmarCanvi.setVisibility(View.INVISIBLE);
                bt_enviar.setVisibility(View.INVISIBLE);
                bt_cancelar.setVisibility(View.INVISIBLE);
                tv_nova_pass.setVisibility(View.INVISIBLE);
                tv_inici.setVisibility(View.VISIBLE);
                bt_reservarOficines.setVisibility(View.VISIBLE);
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
