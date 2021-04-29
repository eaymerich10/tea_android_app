package aymerich.ioc.cat.tea2_clientm_aymerichs.network.reserves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves.LlistaReservesAdmin;
import aymerich.ioc.cat.tea2_clientm_aymerichs.activities.reserves.LlistatReservesUser;
import aymerich.ioc.cat.tea2_clientm_aymerichs.models.Reserva;

import static androidx.core.content.ContextCompat.startActivity;
import static org.junit.Assert.*;

public class LlistarReservesApiTestAdministrador {

    private CountDownLatch signal = new CountDownLatch(1);
    private String url;
    private String codiAcces;
    private String rol;
    private int codi = 0;
    private Context context;
    private Bundle bundle = new Bundle();
    ArrayList<String> reservesFinal = new ArrayList<String>();
    ArrayList<String> reservesString = new ArrayList<String>();
    ArrayList<String> idReserves = new ArrayList<String>();

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        url = "http://16974edc-0b62-457b-a609-090c3527e37b.mock.pstmn.io/llistarreservesadministrador";
        codiAcces = "codiMock";
        rol = "ADMINISTRADOR";
    }

    @Test
    public void llistar() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url + "reserves/" + codiAcces,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("LOG_RESPONSE", response.toString());
                        signal.countDown();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jresponse = response.getJSONObject(i);
                                Reserva reserva = new Reserva(null, null, null);
                                reserva.setIdReserva(jresponse.getString("idReserva"));
                                reserva.setDataIniciReserva(jresponse.getString("dataIniciReserva"));
                                reserva.setDataFinalReserva(jresponse.getString("dataFiReserva"));
                                reserva.setIdOficina(jresponse.getJSONObject("idOficina").getString("idOficina"));
                                reserva.setIdUsuari(jresponse.getJSONObject("idUsuari").getString("idUsuari"));
                                reservesString.add(jresponse.toString());
                                idReserves.add(reserva.getIdReserva());
                                reservesFinal.add("ID FacturaDetailFragment " + reserva.getIdReserva() + " ID Oficina " + reserva.getIdOficina() + " ID Usuari " + reserva.getIdUsuari());
                            }
                            if (rol.equals("ADMINISTRADOR")) {
                                Intent intent = new Intent(context, LlistaReservesAdmin.class);
                                intent.putExtra("url", url);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putStringArrayListExtra("reservesFinal", reservesFinal);
                                intent.putStringArrayListExtra("idReserves", idReserves);
                                intent.putStringArrayListExtra("reservesString", reservesString);
                                startActivity(context, intent, bundle);
                            } else {
                                Intent intent = new Intent(context, LlistatReservesUser.class);
                                intent.putExtra("url", url);
                                intent.putExtra("codiAcces", codiAcces);
                                intent.putStringArrayListExtra("reservesFinal", reservesFinal);
                                intent.putStringArrayListExtra("idReserves", idReserves);
                                intent.putStringArrayListExtra("reservesString", reservesString);
                                startActivity(context, intent, bundle);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG_RESPONSE", error.toString());
                Toast.makeText(context, "Error",
                        Toast.LENGTH_SHORT).show();
            }
        }) {


        };
        requestQueue.add(jsonArrayRequest);
        try {
            signal.await(10, TimeUnit.SECONDS); // Espera la resposta del servidor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("Tot Correcte", 200, codi);

    }
}